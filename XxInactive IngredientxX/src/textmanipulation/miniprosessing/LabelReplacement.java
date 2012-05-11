package textmanipulation.miniprosessing;



/**
 *添付文書上の文字の置換処理<br>
 *
 *@author Okuya
 */
public class LabelReplacement {


	//ラベル←→記号変換テーブル
	static String[][] LabelSymbolTable = {	{"<BR>", 			"＃"},
											{"<BrandName>",		"※"},
											{"<ActIngreName>",	"￥"},
											{"<Number>",		"÷"},
											{"<Unit>",			"＆"}};


	/**
	 * 文章中のラベル<.+>を記号に変換する処理
	 * @param 対象となる文章
	 * @return 変換済み文章<br>
	 */
	public static String ReplaceLabeltoSymbol(String TargetText){

		for(int i=0; i<LabelSymbolTable.length; i++){
			TargetText = TargetText.replaceAll(LabelSymbolTable[i][0], LabelSymbolTable[i][1]);
		}

		return TargetText;

	}
	/**
	 * 文章中の記号をラベル<.+>に変換する処理
	 * @param 対象となる文章
	 * @return 変換済み文章<br>
	 * TargetText
	 */
	public static String ReplaceSymboltoLabel(String TargetText){

		for(int i=0; i<LabelSymbolTable.length; i++){
			TargetText = TargetText.replaceAll(LabelSymbolTable[i][1], LabelSymbolTable[i][0]);
		}

		return TargetText;

	}






}
