package main;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.WordCount;

public class CountPatttenandCreateUniquePattern {

	//パターンリスト
	static String PatternListFileName = "PatternList_Inact.txt";
	//パス
	static String Path = "Files/Data/";

	//出力ファイル名
	static String OutputFileName01 = "CountPattern_Inact.csv";
	static String OutputFileName02 = "UniquePatternList_Inact.txt";
	//出力フォルダパス
	static String OutputPath = "Files/CPandCUP/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		//記述パターンの入力
		ArrayList<String> PatternList = InOutFile.InputTextFile(Path + PatternListFileName);
		//記述パターンのテキスト部分のみを格納する配列
		ArrayList<String> PatternList_Text = new ArrayList<String>();
		//重複せずに記述パターンを格納する配列
		ArrayList<String> UniquePatternList = new ArrayList<String>();


		//記述パターンからYJコードを除外
		for(int i=0; i<PatternList.size(); i++){
			String Text = PatternList.get(i).split("\t", -1)[1];
			PatternList_Text.add(Text);
		}
		//記述パターンをカウントして、降順に並べる
		WordCount WC = new WordCount(PatternList_Text);
		WC.Desc();

		//出力
		ArrayList<String> Output01 = WC.getWordCount();
		InOutFile.OutputText(OutputFileName01, OutputPath, Output01);

		//ユニークな記述パターンを出力
		for(int i=0; i<Output01.size(); i++){
			UniquePatternList.add(Output01.get(i).split(",")[0]);
		}
		InOutFile.OutputText(OutputFileName02, OutputPath, UniquePatternList);


	}

}
