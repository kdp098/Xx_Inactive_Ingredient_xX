package textmanipulation.createLabel;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.miniprosessing.SortStringList;
import textmanipulation.miniprosessing.StringConv;


/**
 *
 *
 * @author Okuya
 *
 */
public class CreateLabel_InactIngre {


	////////////////////////////////////////////////////
	//各添付文書項目に応じたラベル作成に用いるファイル名
	////////////////////////////////////////////////////

	//添加物リストを格納しているファイルの名称
	static String InactiveIngredientListFileName01 = "添加物リスト.txt";
	static String InactiveIngredientListFileName02 = "添加物リスト_手動.txt";
	//単位リストを格納しているファイルの名称
	private String UnitList01 = "単位リスト（～中）.csv";
	private String UnitList02 = "単位リスト（残り）.csv";
	private String UnitList03 = "単位リスト（日本薬局方）.csv";
	private String UnitList04 = "単位.txt";
	//置換用データが格納されているフォルダのパス
	private String FolderPath = "Files/Data/LabelLists/";

	////////////////////////////////////////////////////
	/////////////			ここまで		////////////
	////////////////////////////////////////////////////

	//リスト格納用配列
	private ArrayList<String> InactiveIngredientList = null;
	private ArrayList<String> UnitList = null;


	//削除対象記号・文字一覧
	private String[] DeleteSymbolList = {	"「」",
											"（）",
											"？",
											"Ｌ？",
											"※",
											"＊",
											"Ｌ―",
											"Ｌー",
											"Ｌ‐",
											"○",
											"注２",
											"注３",
											"注）",
										};

	private String[] DeletePhraseList = {	"日局",
											"日本薬局方",
											"薬局方",
											"局外生規",
											"局外規",
											"力価"
										};


	//置換対象テキスト配列
	private ArrayList<String> TargetTextArray = null;
	//置換対象テキストのYJList
	private ArrayList<String> TargetTextYJ = null;
	//置換後テキスト配列
	private ArrayList<String> ReplacementAfterTextArray = new ArrayList<String>();




	/**
	 * コンストラクタ実行時に置換用リスト作成
	 * @param TA 置換対象となるテキスト配列
	 */
	public CreateLabel_InactIngre(ArrayList<String> TA, ArrayList<String> TJ){

		//漢数字変換用
		String[] Kansu = {"○","一","二","三","四","五","六","七","八","九"};
		String[] Number = {"０","１","２","３","４","５","６","７","８","９"};


		this.TargetTextArray = TA;
		this.TargetTextYJ = TJ;

		//////////////
		//リスト作成//
		//////////////
		ArrayList<String> Temp = new ArrayList<String>();
		SortStringList SSL = null;

		//////////
		//添加物//
		//////////
		Temp.addAll(InOutFile.InputTextFile(FolderPath + InactiveIngredientListFileName01));
		Temp.addAll(InOutFile.InputTextFile(FolderPath + InactiveIngredientListFileName02));

		//漢数字の変換
		boolean ReplaceFlag;
		for(int i=0; i<Temp.size(); i++){
			ReplaceFlag = false;
			String S = Temp.get(i);
			for(int j=0; j<Kansu.length; j++){
				if(S.contains(Kansu[j])){
					ReplaceFlag = true;
					S = S.replaceAll(Kansu[j], Number[j]);
				}
			}
			if(ReplaceFlag){
				Temp.add(S);
			}
		}

		SSL = new SortStringList(Temp);
		SSL.ConvertFromHanToZen();
		SSL.SortLong();

		InactiveIngredientList = SSL.getTextList_DeepCopy();
		Temp.clear();
		////////
		//単位//
		////////
		Temp.addAll(InOutFile.InputTextFile(FolderPath + UnitList01));
		Temp.addAll(InOutFile.InputTextFile(FolderPath + UnitList02));
		Temp.addAll(InOutFile.InputTextFile(FolderPath + UnitList03));
		Temp.addAll(InOutFile.InputTextFile(FolderPath + UnitList04));

		SSL = new SortStringList(Temp);
		SSL.ConvertFromHanToZen();
		SSL.SortLong();

		UnitList = SSL.getTextList_DeepCopy();
		Temp.clear();

		//////////////////////
		//リスト作成ここまで//
		//////////////////////
	}


	//////////////////////////////
	//以下パターン作成用メソッド//
	//////////////////////////////


	/**
	 *添加物パターン作成
	 *対象<br>
	 *添加物　添加剤　賦形剤　基剤　安定剤　等張化剤　性状　備考　緩衝剤　添付溶解液<br>
	 *溶剤　溶解液　専用希釈液　免疫補助剤　矯味剤　張化剤　専用溶解液　添付溶剤
	 *
	 */
	public void CreatePatternActiveIngreandUnit(){



		//対象語句(添加物以外)
		String[] TargetWordList = {	"添加剤",		"賦形剤",		"基剤",			"安定剤",
									"等張化剤",		"緩衝剤",		"添付溶解液",
									"溶剤",			"溶解液",		"専用希釈液",	"免疫補助剤",	"矯味剤",
									"張化剤",		"専用溶解液",	"添付溶剤"
		};

		//添加物以外の情報である語句
		String[] OutTargetWordList = { "備考", "電解質量", "有効成分"

		};

		//テキスト1つ1つからパターンを作成
		for(int i=0; i<TargetTextArray.size(); i++){

			//テキスト抽出
			String Text = TargetTextArray.get(i);
			//YJコード
			String TargetYJ = TargetTextYJ.get(i);


			//テキスト部分を全角に置換
			Text = StringConv.eisukigoHanToZen(Text);

			//テキストを<<BR>>で分割
			String[] Temp = Text.split("＜＜ＢＲ＞＞", -1);

			//添加物の記述の範囲を検索するためのインデックス
			int StartIndex = 999;
			int FinishIndex = Temp.length;



			//分割した各テキストに添加物に相当する語句が格納されているか確認
			for(int j=0; j<Temp.length; j++){
//				for(int k=0; k<TargetWordList.length; k++){
//					if(Temp[j].contains(TargetWordList[k])){
					if(Temp[j].contains("添加物")){

						//添加物が記述されている文字列の配列のインデックスを記憶
						StartIndex = j;
						int Index;
//						if((Index = Temp[j].indexOf(TargetWordList[k]))!=0){
						if((Index = Temp[j].indexOf("添加物"))!=0){
							Temp[j] = Temp[j].substring(Index, Temp[j].length());
						}
						break;
					}
//				}
				if(StartIndex!=999){
					break;
				}
			}

			//添加物の記述の範囲の終了部分を探す
			for(int j=StartIndex; j<Temp.length; j++){
				for(int k=0; k<OutTargetWordList.length; k++){
					if(Temp[j].contains(OutTargetWordList[k])){
						//添加物の情報が終了している部分のインデックスを記憶
						FinishIndex = j;
						break;
					}
				}
				if(FinishIndex!=Temp.length){
					break;
				}
			}

			//以降、記述パターン作成に使用する文字列変数
			String TargetText = "";
			//添加物の情報があれば、格納
			if(StartIndex!=999){
				//添加物の情報とその他の情報が同じdetailタグ内に書かれている場合に対応
				if(StartIndex==FinishIndex){
					TargetText = Temp[StartIndex];
				}
				else{
					for(int j=StartIndex; j<FinishIndex; j++){
						TargetText = TargetText + Temp[j] + "＜＜ＢＲ＞＞";
					}
				}
			}





			////////////
			//置換開始//
			////////////


			//Step.1
			//添加物の名称を<InactIngreName>に
			for(int j=0; j<InactiveIngredientList.size(); j++){
				TargetText = TargetText.replaceAll(InactiveIngredientList.get(j), "<InactIngreName>");
			}


			//Step.2
			//数字+単位を<Number><Unit>に
			String NumberRegex = "[０-９](([０-９]|[，．／～？×][０-９])+)?";
			boolean Replaceflag = false;
			if(TargetText.contains("億")){
				Replaceflag = true;
				TargetText = TargetText.replaceAll("億", "００００００００");
			}
			else if(TargetText.contains("万")){
				TargetText = TargetText.replaceAll("万", "００００");
				Replaceflag = true;
			}
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll(NumberRegex + UnitList.get(j), "<Number><Unit>");
			}
			NumberRegex = "約" + NumberRegex;
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll(NumberRegex + UnitList.get(j), "<Number><Unit>");
			}

			if(Replaceflag){
				TargetText = TargetText.replaceAll("００００００００", "億");
				TargetText = TargetText.replaceAll("００００", "万");
			}

			//Step.3
			//／＋単位を／<Unit>に
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll("／" + UnitList.get(j), "／<Unit>");
			}
			//単位／単位を<Unit>に
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll(UnitList.get(j) + "<Unit>", "／<Unit>");
			}

			//Step.4
			//（単位）を（<Unit>）に
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll("（" + UnitList.get(j) + "）", "（<Unit>）");
			}


			//Step.5
			//調整
			TargetText = TargetText.replaceAll("<Number><Unit>／<Number><Unit>", "<Number><Unit>");
			TargetText = TargetText.replaceAll("<Unit>／<Unit>", "<Unit>");
			TargetText = TargetText.replaceAll("…+", "…");
			TargetText = TargetText.replaceAll("・+", "・");

			//Step.6
			//数字を<Number>に
			NumberRegex = "[０-９](([０-９]|[，．／～？×][０-９])+)?";
			TargetText = TargetText.replaceAll(NumberRegex, "<Number>");

			//Step.7
			//不要記号・文字の削除
			for(int j=0; j<DeletePhraseList.length; j++){
				TargetText = TargetText.replaceAll(DeletePhraseList[j], "");
			}
			for(int j=0; j<DeleteSymbolList.length; j++){
				TargetText = TargetText.replaceAll(DeleteSymbolList[j], "");
			}

			//Step.8
			//区切り文字の削除
			TargetText = TargetText.replaceAll("、", "");
			TargetText = TargetText.replaceAll("。", "");
			TargetText = TargetText.replaceAll("，", "");
			TargetText = TargetText.replaceAll("・", "");
			TargetText = TargetText.replaceAll("￥ｎ", "");
			TargetText = TargetText.replaceAll("　", "");

			//Step.9
			//<<BR>>の調整
			TargetText = TargetText.replaceAll("^＜ＢＲ＞", "");
			TargetText = TargetText.replaceAll("＜ＢＲ＞$", "");
			TargetText = TargetText.replaceAll("^＜＜ＢＲ＞＞", "");
			TargetText = TargetText.replaceAll("＜＜ＢＲ＞＞$", "");


			if(!(TargetText.length()==0) && TargetText.indexOf("＜")==TargetText.length()-1){
				TargetText = TargetText.substring(0, TargetText.length()-1);
			}

			//タグ(<aaa>)をラベル([aaa])に置換
			TargetText = TargetText.replaceAll("<", "[");
			TargetText = TargetText.replaceAll(">", "]");

			//＜＜ＢＲ＞＞や＜ＢＲ＞を半角に
			TargetText = TargetText.replaceAll("＜＜ＢＲ＞＞", "<<BR>>");
			TargetText = TargetText.replaceAll("＜ＢＲ＞", "<BR>");



			//出力用に格納
			ReplacementAfterTextArray.add(TargetYJ + "\t" + TargetText);

			if(i%1000==0){
				System.out.println(i+":終了");
			}
			if(i==1682){
				System.out.println(i+":終了");
			}
		}
	}




	/**
	 * ラベル作成時の不要部分探索・削除用\n
	 * 添加物等の不要語句に対して、残りの<BR>部分までを削除するために、その部分を探索して部分文字列を作成し、削除
	 * @param 削除対象TEXT, 不要語句開始Index
	 * @return 不要部分削除済みTEXT
	 */
	private String SearchDeletePart(String TargetText, int fromindex){
		int lastindex;

		//該当部分の最後を探索
		//該当部分を探索 +2となっているのは開始部分と終わりの部分を一致させないため
		if((lastindex = TargetText.indexOf("＜＜ＢＲ＞＞", fromindex+2)) != -1){
			//＜＜ＢＲ＞＞も削除するために調整
			lastindex += 5;
		}
		//見つからなかったら、対象語句以降を全て削除するために、一番最後のIndex番号を取得
		else{
			lastindex = TargetText.length()-1;
		}

		//削除部分の文字列を取得
		String S = TargetText.substring(fromindex, lastindex);

		//削除
		TargetText = TargetText.replaceFirst(S, "");


		return TargetText;
	}

	/**
	 * get
	 */
	public ArrayList<String>getReplaceAfterTextArray(){
		return ReplacementAfterTextArray;
	}


}
