package textmanipulation;

import java.util.ArrayList;

import textmanipulation.miniprosessing.LabelReplacement;


/**
 * 前処理や後処理用のクラス
 * @author Okuya
 */
public class PrePostProssesing {


	/**
	 * 文章中のラベル<.+>を記号に変換する処理
	 * @param 対象となる文章配列
	 * @return 変換済み文章配列
	 */
	public static ArrayList<String> ReplaceLabeltoSymbol(ArrayList<String> TargetTextList){

		for(int i=0; i<TargetTextList.size(); i++){
			TargetTextList.set(i, LabelReplacement.ReplaceLabeltoSymbol(TargetTextList.get(i)));
		}

		return TargetTextList;
	}





	/**
	 * * 文章中の記号をラベル<.+>に変換する処理
	 * @param 対象となる文章配列
	 * @return 変換済み文章配列
	 */
	public static ArrayList<String> ReplaceSymboltoLabel(ArrayList<String> TargetTextList){

		for(int i=0; i<TargetTextList.size(); i++){
			TargetTextList.set(i, LabelReplacement.ReplaceSymboltoLabel(TargetTextList.get(i)));
		}

		return TargetTextList;
	}
}
