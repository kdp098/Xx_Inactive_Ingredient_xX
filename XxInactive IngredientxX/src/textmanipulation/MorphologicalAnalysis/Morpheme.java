package textmanipulation.MorphologicalAnalysis;

/**
 * 形態素クラス
 * @author nabeta,okuya
 * @version 2.0
 */
public class Morpheme {
	public int STATEMENT_ID;				// 文番号
	public int SEGMENT_ID;					// 分節番号
	public int MORPHEME_ID;					// 形態素番号
	public String SURFACE_FORM;				// 表層形
	public String READING;					// 読み
	public String PRONUNCIATION;			// 発音
	public String BASE_FORM;				// 原形
	public String PART_OF_SPEECH;			// 品詞
	public String PART_OF_SPEECH01;			// 品詞細分類1
	public String PART_OF_SPEECH02;			// 品詞細分類2
	public String PART_OF_SPEECH03;			// 品詞細分類3
	public String CONJUGATION_TYPE;			// 活用型
	public String CONJUGATION_FORM;			// 活用形
//	public String PROPER_REPRESENTATION;	// 固有表現

	//出力フォーマット
	//表層形\t品詞,品詞細分類1,品詞細分類2,品詞細分類3,活用形,活用型,原型,読み,発音


	/**
	 * @param arg Cabocahの出力結果
	 * @param statement_id 文番号
	 * @param segment_id 分節番号
	 * @param morpheme_id 形態素番号
	 */
	Morpheme(String arg)
	{
		String[] temp01 = arg.split("\t");
		if(temp01.length == 2){
			SURFACE_FORM = temp01[0];
			String[] temp02 = temp01[1].split(",");

			if(temp02.length == 9){
				PART_OF_SPEECH		= temp02[0];
				PART_OF_SPEECH01	= temp02[1];
				PART_OF_SPEECH02	= temp02[2];
				PART_OF_SPEECH03	= temp02[3];
				CONJUGATION_FORM	= temp02[4];
				CONJUGATION_TYPE	= temp02[5];
				BASE_FORM			= temp02[6];
				READING 			= temp02[7];
				PRONUNCIATION 		= temp02[8];

			}
			//以下未知語用
			else if(temp02.length == 7){
				PART_OF_SPEECH		= temp02[0];
				PART_OF_SPEECH01	= temp02[1];
				PART_OF_SPEECH02	= temp02[2];
				PART_OF_SPEECH03	= temp02[3];
				CONJUGATION_FORM	= temp02[4];
				CONJUGATION_TYPE	= temp02[5];
				BASE_FORM			= temp02[6];
			}
		}
	}

	/**
	 * @param arg Cabocahの出力結果
	 * @param statement_id 文番号
	 * @param segment_id 分節番号
	 * @param morpheme_id 形態素番号
	 */
	Morpheme(String arg, int statement_id, int segment_id, int morpheme_id)
	{
		STATEMENT_ID = statement_id;
		SEGMENT_ID = segment_id;
		MORPHEME_ID = morpheme_id;

		String[] temp01 = arg.split("\t");
		if(temp01.length == 3){
			SURFACE_FORM = temp01[0];
			String[] temp02 = temp01[1].split(",");

			if(temp02.length == 9){
				PART_OF_SPEECH		= temp02[0];
				PART_OF_SPEECH01	= temp02[1];
				PART_OF_SPEECH02	= temp02[2];
				PART_OF_SPEECH03	= temp02[3];
				CONJUGATION_FORM	= temp02[4];
				CONJUGATION_TYPE	= temp02[5];
				BASE_FORM			= temp02[6];
				READING 			= temp02[7];
				PRONUNCIATION 		= temp02[8];

			}
		}
	}
}
