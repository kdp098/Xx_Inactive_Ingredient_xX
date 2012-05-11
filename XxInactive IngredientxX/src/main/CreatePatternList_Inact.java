package main;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.createLabel.CreateLabel_InactIngre;

/**
 * 組成の記述より添加物の記述パターンを作成するクラス
 */
public class CreatePatternList_Inact {


	//組成テキストが入っているファイル名
	static String TextFileName = "HOT9YJ_CompotisionTextList.txt";
	//データフォルダパス
	static String DataFolderPath = "Files/Data/";

	//出力用ファイル名
	static String OutputFileName = "PatternList_Inact.txt";
	//出力用フォルダのパス
	static String Path = "Files/CP_I/";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		ArrayList<String[]> Lists = InOutFile.InputTextFilePlusSplit(DataFolderPath + TextFileName, ",");
		ArrayList<String> YJList = new ArrayList<String>();
		ArrayList<String> TextList = new ArrayList<String>();
		for(int i=0; i<Lists.size(); i++){
			String[] Temp = Lists.get(i);
			YJList.add(Temp[1]);

			String temp = Temp[2];
			for(int j=3; j<Temp.length; j++){
				temp = temp + "," + Temp[j];
			}
			TextList.add(temp);
		}
		Lists = null;
		CreateLabel_InactIngre CL_II = new CreateLabel_InactIngre(TextList, YJList);
		CL_II.CreatePatternActiveIngreandUnit();
		ArrayList<String> OutPut = CL_II.getReplaceAfterTextArray();

		InOutFile.OutputText(OutputFileName, Path, OutPut);


	}

}
