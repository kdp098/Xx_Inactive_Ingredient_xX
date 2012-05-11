package textmanipulation;

import java.util.ArrayList;
import textmanipulation.MorphologicalAnalysis.Morpheme;
import textmanipulation.MorphologicalAnalysis.MorphologicalAnalysis;
import textmanipulation.miniprosessing.StringConv;

/**
 * 単語文書行列生成クラス
 * @author Okuya
 */
public class TermDocumentMatrix{


	//単語文書行列に使用する語句LIST
	ArrayList<String> WordList = null;
	//文書パターンLIST
	ArrayList<String> PatternList = null;
	//形態素解析の対象となる文書パターンが入ったファイルの名前
	String PatternFileName = null;
	//文書パターンに対して形態素解析を行った結果を格納するLIST
	ArrayList<Morpheme[]> MorphemeList = new ArrayList<Morpheme[]>();
	//単語文章行列
	ArrayList<ArrayList<Integer>> TDM = new ArrayList<ArrayList<Integer>>();

	/**
	 * 対象ファイルから語句リストと文書パターンリストを格納するコンストラクタ
	 * @param WordListFileName 語句リストが格納されているファイル名
	 * @param PatternListFileName 文書パターンリストが格納されているファイル名
	 */
	public TermDocumentMatrix(String WordListFileName, String PatternListFileName){
		this.WordList = InOutFile.InputTextFile(WordListFileName);
		System.out.println("ファイルから単語リストを生成しました");
		this.PatternFileName = PatternListFileName;
		this.PatternList = InOutFile.InputTextFile(PatternListFileName);
		System.out.println("ファイルから文書パターンリストを生成しました");
	}



	/**
	 * 文書パターンリストに対して形態素解析を行い結果を格納
	 */
	public void CreateMorphemeList(){
		MorphologicalAnalysis MA = new MorphologicalAnalysis(PatternFileName);
		MorphemeList = MA.getMAList();
	}


	/**
	 * 語句リストから文章の単語ベクトルを生成(頻度カウント)
	 * @param 対象文章
	 * @return 単語出現行列<br>
	 * CountList
	 */
	public ArrayList<Integer> WordAppearanceCount(Morpheme[] MorphemeList){

		//単語出現行列生成
		ArrayList<Integer> CountList = new ArrayList<Integer>();


		for(int i=0; i<WordList.size(); i++){
			//対象語句を取得
			String Word = WordList.get(i);
			//正規表現に引っかからないよう、全角文字に変換
			Word = StringConv.eisukigoHanToZen(Word);

			//出現頻度をカウント
			int counter = 0;
			for(int j=0; j<MorphemeList.length; j++){
				String Noun = StringConv.eisukigoHanToZen(MorphemeList[j].SURFACE_FORM);

				if(Noun.equals(Word)){
					counter++;
				}
			}
			CountList.add(counter);
		}



		return CountList;
	}

	/**
	 * 単語文書行列作成
	 */
	public void CreateTermDocumentMatrixCount(){

		//先に形態素解析を行っていない場合、エラーメッセージとして表示
		if(MorphemeList.isEmpty()){
			System.out.println(	"形態素解析を先に行ってください。\n" +
								"Method:CreateMorphemeList");
			}
		else{
			//各パターンの単語ベクトルを作成
			for(int i=0; i<MorphemeList.size(); i++){
				TDM.add(WordAppearanceCount(MorphemeList.get(i)));
				if(i%1000==0){
					System.out.println("単語ベクトル作成完了:"+ i);
				}
			}
			System.out.println("単語文書行列を生成しました");
		}
	}


	/**
	 * 語句リストから文章の単語ベクトルを生成(出現の有無)
	 * @param 対象文章
	 * @return 単語出現行列<br>
	 * CountList
	 */
	public ArrayList<Integer> WordAppearance(Morpheme[] MorphemeList){

		//単語出現行列生成
		ArrayList<Integer> CountList = new ArrayList<Integer>();


		for(int i=0; i<WordList.size(); i++){
			//対象語句を取得
			String Word = WordList.get(i);
			//正規表現に引っかからないよう、全角文字に変換
			Word = StringConv.eisukigoHanToZen(Word);

			//出現頻度をカウント
			int counter = 0;
			for(int j=0; j<MorphemeList.length; j++){
				String Noun = StringConv.eisukigoHanToZen(MorphemeList[j].SURFACE_FORM);

				if(Noun.equals(Word)){
					counter = 1;
				}
			}
			CountList.add(counter);
		}



		return CountList;
	}

	/**
	 * 単語文書行列作成(出現の有無)
	 */
	public void CreateTermDocumentMatrixAppearance(){

		//先に形態素解析を行っていない場合、エラーメッセージとして表示
		if(MorphemeList.isEmpty()){
			System.out.println(	"形態素解析を先に行ってください。\n" +
								"Method:CreateMorphemeList");
			}
		else{
			//各パターンの単語ベクトルを作成
			for(int i=0; i<MorphemeList.size(); i++){
				TDM.add(WordAppearance(MorphemeList.get(i)));
				if(i%1000==0){
					System.out.println("単語ベクトル作成完了:"+ i);
				}
			}
			System.out.println("単語文書行列を生成しました");
		}
	}








	/**
	 * 特定のワード中が文書パターン中に指定した出現回数だけ出現したらその文書パターンを削除
	 * @param 対象となる語句 DWord
	 * @param 出現回数 Count
	 */
	public void DeleteWord(String DWord, int Count){

		//ワードの出現位置
		int Index = WordList.indexOf(DWord);

		for(int i=0; i<TDM.size(); i++){
			ArrayList<Integer> TDMLine = TDM.get(0);
			String Pattern = PatternList.get(0);
			TDM.remove(0);
			PatternList.remove(0);

			//出現回数判定(異なったら後ろに追加)
			if(TDMLine.get(Index) != Count){
				TDM.add(TDMLine);
				PatternList.add(Pattern);
			}
		}
	}

	/**
	 * 特定のワード中が文書パターン中に指定した出現回数以上出現したらその文書パターンを削除
	 * @param 対象となる語句 DWord
	 * @param 出現回数 Count
	 */
	public void DeleteWordCountOver(String DWord, int Count){

		//ワードの出現位置
		int Index = WordList.indexOf(DWord);

		for(int i=0; i<TDM.size(); i++){
			ArrayList<Integer> TDMLine = TDM.get(0);
			String Pattern = PatternList.get(0);
			TDM.remove(0);
			PatternList.remove(0);

			//出現回数判定(Countより小さかったら後ろに追加)
			if(TDMLine.get(Index) < Count){
				TDM.add(TDMLine);
				PatternList.add(Pattern);
			}
		}
	}
	/**
	 * 特定のワード中が文書パターン中に指定した出現回数以下出現したらその文書パターンを削除
	 * @param 対象となる語句 DWord
	 * @param 出現回数 Count
	 */
	public void DeleteWordCountUnder(String DWord, int Count){

		//ワードの出現位置
		int Index = WordList.indexOf(DWord);

		for(int i=0; i<TDM.size(); i++){
			ArrayList<Integer> TDMLine = TDM.get(0);
			String Pattern = PatternList.get(0);
			TDM.remove(0);
			PatternList.remove(0);

			//出現回数判定(Countより大きかったら後ろに追加)
			if(TDMLine.get(Index) > Count){
				TDM.add(TDMLine);
				PatternList.add(Pattern);
			}
		}
	}





	/**
	 * 単語文書行列をタイトルをつけた表にしてCSV形式に直した上で出力
	 */
	public ArrayList<String> OutputTermDocumentMatrix(){

		ArrayList<String> ReturnList = new ArrayList<String>();

		System.out.println("単語文書行列作成開始");

		//ヘッダーの書き込み
		String Header = "";
		for(int i=0; i<WordList.size(); i++){
			Header += "," + WordList.get(i);
		}
		ReturnList.add(Header);

		//距離行列の書き込み
		for(int i=0; i<TDM.size(); i++){
			//パターン
			String temp = PatternList.get(i);
			for(int j=0; j<TDM.get(i).size(); j++){
				temp += "," + TDM.get(i).get(j);
			}

			ReturnList.add(temp);
			if(i%1000==0){
				System.out.println("書き込み完了:" + i);
			}
		}

		return ReturnList;
	}






	/**
	 * 文字長の大きい純に並び替える
	 * @param 対象文字配列
	 * @return 並び替え済み文字配列
	 */
	public String[] LengthSort(String[] TargetArray){

		int size = TargetArray.length;
		//バブルソート
		for(int i=0; i<size-1; i++){
			for(int j=size-1; j>0; j--){

				int Length01 = TargetArray[j].length();
				int Length02 = TargetArray[j].length();

				if(Length01<Length02){
					String Temp = TargetArray[j-1];
					TargetArray[j-1] = TargetArray[j];
					TargetArray[j] = Temp;;
				}
			}
		}
		return TargetArray;
	}




}
