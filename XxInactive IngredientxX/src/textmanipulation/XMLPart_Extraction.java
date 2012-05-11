package textmanipulation;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 指定した記述部分を添付文書SGMLから取得するクラス
 *
 * @author Okuya
 *
 */
public class XMLPart_Extraction {


	/**
	 * 取得した文章群
	 */
	private ArrayList<String> TextArray = new ArrayList<String>();

	/**
	 * コンストラクタ
	 * 実行時に自動的にタグから文章群を抽出してTextArrayに格納
	 * @param ObjectFolder 対象SGMLが入っているフォルダ
	 * @param ObjectTagName 対象となるタグ
	 */
	public XMLPart_Extraction(String ObjectFolder, String ObjectTagName){


		//新しいフォルダの生成
		File NewFolder = new File(ObjectFolder + "/" + ObjectTagName);
		if(!NewFolder.exists()){
			NewFolder.mkdir();
		}

		//ファイルリストの作成
		File Folder = new File(ObjectFolder);
		File[] Files = Folder.listFiles();

		//Log
		ArrayList<String> Log = new ArrayList<String>();


		//必要なタグのみを抽出して、新たなTEXTファイルに保存
		for(int i=0; i<Files.length; i++){
			String[] Temp = Extraction(Files[i], ObjectTagName);

			if(Temp.length!=0){
				for(int j=0; j<Temp.length; j++){
					TextArray.add(Files[i].getName()+">>"+Temp[j]);
				}
			}
			else{
				Log.add(Files[i].getName());
			}
		}

		InOutFile.OutputText("NotCompositionLog.txt", "", Log);
	}

	/**
	 * SGMLのタグを指定し、その部分のテキストデータを抽出する
	 * @param タグの指定
	 * @return 特定の記述部分の配列
	 */
	public String[] Extraction(File FileName, String TagName){

		String[] TextList = {};

		try {
			//DOM生成
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(FileName);


			//指定したタグの取得
			Element root = doc.getDocumentElement();
			Element TargetNode = (Element)root.getElementsByTagName(TagName).item(0);
			//指定したタグの子ノード集合を取得
			NodeList ItemNodeList = TargetNode.getElementsByTagName("item");
			NodeList DetailNodeList = TargetNode.getElementsByTagName("detail");

			//中身のタグを判定して、テキストデータを抽出
			if(ItemNodeList.getLength() == 0 && DetailNodeList.getLength() == 0){
				NodeList NList = TargetNode.getChildNodes();
				int N = NList.getLength();
				TextList = new String[N];
				for(int i=0; i<N; i++){
					TextList[i] = NList.item(i).getTextContent();
				}
			}
			else if(ItemNodeList.getLength() == 0 && DetailNodeList.getLength() != 0){
				TextList = Detail(DetailNodeList);
			}
			else if(ItemNodeList.getLength() != 0 && DetailNodeList.getLength() == 0){
				TextList = Item(ItemNodeList);
			}
			else if(ItemNodeList.getLength() != 0 && DetailNodeList.getLength() != 0 && ItemNodeList.getLength() != DetailNodeList.getLength()){
				TextList = NotItemDetailLength(TargetNode);
			}
			else if(ItemNodeList.getLength() != 0 && DetailNodeList.getLength() != 0 && ItemNodeList.getLength() == DetailNodeList.getLength()){
				TextList = ItemDetail(ItemNodeList, DetailNodeList);
			}

		}
		catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		catch(NullPointerException e){
			e.printStackTrace();

		}

		return TextList;
	}

	/**
	 * 子のタグ名にitemタグとdetailタグが含まれている場合、
	 * それらのテキストデータを<BR>タグで結合して返す
	 * @param ItemNodeList, DetailNodeList
	 * @return 結合したテキストデータ配列
	 */
	public String[] ItemDetail(NodeList ItemNodeList, NodeList DetailNodeList){


		int N = ItemNodeList.getLength();
		String[] ReturnTextList = new String[N];
		for(int i=0; i<N; i++){
			String ItemText = ItemNodeList.item(i).getTextContent();
			String DetailText = DetailNodeList.item(i).getTextContent();

			ReturnTextList[i] = ItemText + "<BR>" + DetailText;
		}

		return ReturnTextList;
	}

	/**
	 * 子のタグがDetailタグのみである場合、
	 * それに含まれているテキストデータの配列を返す
	 * @param DetailNodeList
	 * @return テキストデータ配列
	 */
	public String[] Detail(NodeList DetailNodeList){

		int N = DetailNodeList.getLength();
		String[] ReturnTextList = new String[N];
		for(int i=0; i<N; i++){
			ReturnTextList[i] = DetailNodeList.item(i).getTextContent();
		}
		return ReturnTextList;
	}

	/**
	 * 子のタグがItemタグのみである場合、
	 * それに含まれているテキストデータの配列を返す
	 * @param DetailNodeList
	 * @return テキストデータ配列
	 */
	public String[] Item(NodeList ItemNodeList){

		int N = ItemNodeList.getLength();
		String[] ReturnTextList = new String[N];
		for(int i=0; i<N; i++){
			ReturnTextList[i] = ItemNodeList.item(i).getTextContent();
		}
		return ReturnTextList;
	}

	/**
	 * 子のタグのItemタグとDetailタグの数が一致しないときに、
	 * 分別して、2つともあるものを結合してテキストデータの配列を返す。
	 * @param TargetNode
	 * @return テキストデータの配列
	 */
	public String[] NotItemDetailLength(Element TargetNode){

		NodeList TargetNodeList = TargetNode.getChildNodes();
		ArrayList<String> ReturnTextList = new ArrayList<String>();
		for(int i=0; i<TargetNodeList.getLength(); i++){
			String Text = "";
			Node Target = TargetNodeList.item(i);
			if(Target.getNodeName().equals("detail")){
				if(i != 0){
					Node PreviousNode = TargetNodeList.item(i-1);
					if(PreviousNode.getNodeName().equals("item")){
						Text = PreviousNode.getTextContent() + "<BR>" + Target.getTextContent();
					}
					else{
						Text = Target.getTextContent();
					}
				}
				else{
					Text = Target.getTextContent();
				}
			}
			else if(!Target.getNodeName().equals("item")){
				Text = Target.getTextContent();
			}

			ReturnTextList.add(Text);

		}

		while(ReturnTextList.remove("")){

		}

		return (String[])ReturnTextList.toArray(new String[0]);

	}

	/**
	 * Get
	 */
	public ArrayList<String>getTextArray(){
		return TextArray;
	}



}
