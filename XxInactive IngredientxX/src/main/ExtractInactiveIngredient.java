package main;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.miniprosessing.SortStringList;


/**
 * 日本製薬団体連合会が公開している添加物名称データより記載名称のみを抽出
 * @author Okuya
 *
 */
public class ExtractInactiveIngredient {



	//入力ファイル_公開されている添加物情報
	static String InactiveFileName = "医薬品添加物成分の記載名称リスト.csv";
	//入力ファイルのフォルダパス
	static String DataFilePath = "Files/Data/";

	//出力ファイル_添加物記載名称リスト
	static String InactiveIngredientListFileName = "InactiveIngredientList.txt";
	//出力ファイルのフォルダパス
	static String OutputFilePath = "Files/EII/";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		//ファイルよりデータ入力
		//項目名(No,成分コード,添加物事典名,複数成分含有,日局収載,記載名称_成分名,記載名称_別名又は簡略名)
		ArrayList<String> FileData = InOutFile.InputTextFile(DataFilePath + InactiveFileName);

		//抽出用の前処理(ダブルコーテーション中の語句は改行を含むため)
		ArrayList<String> SaveTemp = new ArrayList<String>();
		String TextTemp = "";
		boolean Flag = true;
		for(int i=0; i<FileData.size(); i++){
			//データ抽出
			String Text = FileData.get(i);

			//ダブルコーテーションが含まれていたら、ダブルコーテーションを削除し、フラグを変更
			if(Text.contains("\"")){
				Text = Text.replaceAll("\"", "");
				Flag = !Flag;
			}

			//テキストの結合(フラグがfalseなら区切りも追加)
			if(Flag){
				TextTemp = TextTemp + Text;
			}
			else{
				TextTemp = TextTemp + Text + "_";
			}

			//フラグがtrueならテキストを格納する(空行は飛ばす)
			if(Flag && TextTemp.length()!=0){
				SaveTemp.add(TextTemp);
				TextTemp = "";
			}
		}

		FileData = new ArrayList<String>(SaveTemp);



		//出力ファイル用ArrayListの宣言
		ArrayList<String> Output_InactiveIngredientList = new ArrayList<String>();

		//必要なデータを抽出(フィあるの最初に記載されている説明は対象外)
		for(int i=14; i<FileData.size(); i++){
			//対象文字列取得
			String TargetText = FileData.get(i);

			//CSV形式なので、カンマで分割
			String[] SplitText = TargetText.split(",", -1);

			//格納対象は記載名称なので、index = 5と6を格納(空文字は対象外)
			for(int j=5; j<SplitText.length; j++){
				//区切り文字が含まれている場合は更に分割
				String[] Temp = SplitText[j].split("_");
				for(int k=0; k<Temp.length; k++){
					//句点の区切りも存在するので更に分割
					String temp[] = Temp[k].split("、", -1);
					for(int l=0; l<temp.length; l++){
						//空白や\tの除去
						temp[l] = temp[l].replaceAll(" ", "");
						temp[l] = temp[l].replaceAll("　", "");
						temp[l] = temp[l].replaceAll("\t", "");
						Output_InactiveIngredientList.add(temp[l]);
					}
				}
			}
		}

		//重複データ削除
		SortStringList SSL = new SortStringList(Output_InactiveIngredientList);
		SSL.DeleteOverlappingData();

		//文字列長でソート
		SSL.SortLong();

		//データ出力
		InOutFile.OutputText(InactiveIngredientListFileName, OutputFilePath, SSL.getTextList());
	}

}
