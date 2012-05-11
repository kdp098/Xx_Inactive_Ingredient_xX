package textmanipulation;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML_Processing {



	/**
	 * そのノードと子ノードの全てを表示(最初に呼び出すときは0を入れる)
	 * @param N
	 * @param IndentN
	 */
	public static void showSelfandChild(Node N, int IndentN){


		//インデントの数を調整して表示
		String IndentS = "";
		for(int i=0; i<IndentN; i++){
			IndentS += "\t";
		}
		System.out.print(IndentS);


		//nodeNameを取得
		String Name = N.getNodeName();

		//表示
		if(!Name.equals("#document") && !Name.equals("#text")){
			System.out.print("<" + Name);

			//属性の有無
			if(N.hasAttributes()){
				NamedNodeMap NNM = N.getAttributes();
				for(int i=0; i<NNM.getLength(); i++){
					System.out.print(" " + NNM.item(i).getNodeName() + " = " + NNM.item(i).getNodeValue());
				}
			}

			System.out.print(">");
		}
		else if(Name.equals("#text")){
			System.out.println(N.getNodeValue());
		}


		//子ノードがあれば再呼び出し
		if(N.hasChildNodes()){
			System.out.println("");
			NodeList Nlist = N.getChildNodes();
			for(int i=0; i<Nlist.getLength(); i++){
				showSelfandChild(Nlist.item(i), IndentN+1);
			}
			System.out.print(IndentS);
		}


		//終了タグの表示
		if(!Name.equals("#document") && !Name.equals("#text")){
			System.out.println("</" + Name + ">");
		}
	}





	/**
	 * StringをDocumentに変換
	 * @param XMLString
	 * @return Documentクラス
	 */
	public static Document StringtoDocument(String XMLString){


		Document doc = null;
		try {
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
			doc = docbuilder.parse(new ByteArrayInputStream(XMLString.getBytes("UTF-8")));
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return doc;


	}







	/**
	 * Documentクラスとして展開しているDOMを保存
	 * @param SaveFileName
	 * @param XMLDocument
	 */
	public static void SaveXMLDocument(String SaveFileName, Document XMLDocument){

		try {
			TransformerFactory TF = TransformerFactory.newInstance();
			Transformer T = TF.newTransformer();

			File F = new File(SaveFileName);
			T.transform(new DOMSource(XMLDocument), new StreamResult(F));

			System.out.println("Success Save");

		} catch (TransformerConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 対象となるノードの子ノードに改行やインデントのみが含まれていたらその子ノードを削除
	 * @param N
	 */
	public static void DeleteEmptyNode(Node N){

		//子ノードを取得
		NodeList NList = N.getChildNodes();
		for(int i=0; i<NList.getLength(); i++){

			Node ObjectNode = NList.item(i);
			//ノードのタイプがテキストのとき
			if(ObjectNode.getNodeType()==3){

				String TextValue = ObjectNode.getNodeValue();

				//子ノードのテキストが改行とインデックスのみなら削除
				if(TextValue.indexOf("\n") != -1){
					TextValue = TextValue.replaceAll("\n", "");
				}
				if(TextValue.indexOf("\t") != -1){
					TextValue = TextValue.replaceAll("\t", "");
				}
				//削除
				if(TextValue.length()==0){
					N.removeChild(ObjectNode);
				}
			}
			//子ノードの子ノードがあれば、同メソッドを呼ぶ
			if(ObjectNode.hasChildNodes()){
				Delete_UnwantedTag(ObjectNode);
			}
		}
	}

	/**
	 * 添付文書中の不要な要素とその中身を削除
	 * @param N
	 */
	static public void Delete_UnwantedTag(Node N){


		//不要要素名
		String[] DeleteTagNames = {	"variablelabel", "serialno"};

		//子ノードを取得
		NodeList NList = N.getChildNodes();
		for(int i=0; i<NList.getLength(); i++){

			Node ObjectNode = NList.item(i);

			for(int j=0; j<DeleteTagNames.length; j++){

				//子ノードの要素名が不要要素名であれば削除
				if(ObjectNode.getNodeName().equals(DeleteTagNames[j])){
					N.removeChild(ObjectNode);
				}
				//子ノードの子ノードがあれば、同メソッドを呼ぶ
				else if(ObjectNode.hasChildNodes()){
					Delete_UnwantedTag(ObjectNode);
				}
			}
		}
	}



	/**
	 * ファイル名を引数として、XML文章中の改行やインデントをなくす前処理(新たに作成したXML形式のファイル名がリターン)
	 * @param FileName
	 * @return
	 */
	public static String ReplaceProcessing(String FileName){

		//書き換える文字列の設定(置換対象:置換後の文字)
		String[] ReplaceString = {	"\n",	"",
									"\t",	""
		};



		String NewFileName = "";
		String[] Temp = FileName.split("/", -1);
		for(int i=0; i<Temp.length; i++){
			if(i!=Temp.length-1){
				NewFileName += Temp[i] + "/";
			}
			else{
				NewFileName += "New" + Temp[i];
			}
		}

		try {
			//XMLファイルの入出力の設定
			FileInputStream fis = new FileInputStream(FileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			FileOutputStream fos = new FileOutputStream(NewFileName);
			OutputStreamWriter osr = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osr);

			String temp;
			while((temp = br.readLine()) != null){

				//置換
				for(int i=0; i<ReplaceString.length; i+=2){
					temp = temp.replaceAll(ReplaceString[i], ReplaceString[i+1]);
				}

				bw.write(temp);
			}

			//クローズ
			bw.close();
			osr.close();
			fos.close();
			br.close();
			isr.close();
			fis.close();


		}
		catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return NewFileName;


	}
	/**
	 * フォルダ中のXML文章の改行やインデントをなくし、新しいフォルダに保存
	 * @param FolderName
	 */
	public static void ReplaceProcessing_and_MakeFolder(String FolderName){

		//書き換える文字列の設定(置換対象:置換後の文字)
		String[] ReplaceString = {	"\n",	"",
									"\t",	"",
									"　",	"",
									" ",	""

		};

		String NewFolderName = "ImprovementXML/";
		File NewFolder = new File(FolderName + NewFolderName);
		if(!NewFolder.exists()){
			NewFolder.mkdir();
		}


		File Folder = new File(FolderName);
		String[] FileNameList = Folder.list();

		for(int i=0; i<FileNameList.length; i++){
			String NewFileName = "";
			String[] Temp = FileNameList[i].split("/", -1);
			for(int j=0; j<Temp.length; j++){
				if(j!=Temp.length-1){
					NewFileName += Temp[j] + "/";
				}
				else{
					NewFileName += NewFolderName + "Improvement_" + Temp[j];
				}
			}

			try {
				//XMLファイルの入出力の設定
				FileInputStream fis = new FileInputStream(FolderName + FileNameList[i]);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				FileOutputStream fos = new FileOutputStream(FolderName + NewFileName);
				OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
				BufferedWriter bw = new BufferedWriter(osr);

				String temp;
				while((temp = br.readLine()) != null){

					//置換
					for(int j=0; j<ReplaceString.length; j+=2){
						temp = temp.replaceAll(ReplaceString[j], ReplaceString[j+1]);
						if(j==ReplaceString.length-2){
							temp = temp.replaceAll("version", " version");
							temp = temp.replaceAll("encoding", " encoding");
							temp = temp.replaceAll("standalone", " standalone");
						}
					}

					bw.write(temp);
				}

				//クローズ
				bw.close();
				osr.close();
				fos.close();
				br.close();
				isr.close();
				fis.close();


			}
			catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}



	}

}
