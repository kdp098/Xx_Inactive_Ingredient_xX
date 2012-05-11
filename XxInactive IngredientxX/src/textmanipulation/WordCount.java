package textmanipulation;

import java.util.ArrayList;



/**
 * 語句を集計するクラス
 */
public class WordCount {


	private ArrayList<String> WordList = new ArrayList<String>();
	private ArrayList<Integer> CountList = new ArrayList<Integer>();

	/**
	 * 語句を集計してCountListに格納するコンストラクタ
	 */
	public WordCount(ArrayList<String> AllWordList){

		//集計
		for(int i=0; i<AllWordList.size(); i++){

			String TargetWord = AllWordList.get(i);
			int Index;
			//既にあるパターン
			if((Index = WordList.indexOf(TargetWord)) != -1){
				CountList.set(Index, CountList.get(Index) + 1);
			}
			//まだ無いパターン
			else{
				WordList.add(TargetWord);
				CountList.add(1);
			}
		}
	}

	/**
	 * 集計結果を昇順にする
	 */
	public void Asc(){

		int size = WordList.size();
		//バブルソート
		for(int i=0; i<size-1; i++){
			for(int j=size-1; j>0; j--){

				int Count01 = CountList.get(j-1);
				int Count02 = CountList.get(j);

				if(Count01>Count02){

					CountList.set(j-1, Count02);
					CountList.set(j, Count01);

					String Temp = WordList.get(j-1);
					WordList.set(j-1, WordList.get(j));
					WordList.set(j, Temp);
				}
			}
		}
	}

	/**
	 * 集計結果を降順にする
	 */
	public void Desc(){

		int size = WordList.size();
		//バブルソート
		for(int i=0; i<size-1; i++){
			for(int j=size-1; j>0; j--){

				int Count01 = CountList.get(j-1);
				int Count02 = CountList.get(j);

				if(Count01<Count02){

					CountList.set(j-1, Count02);
					CountList.set(j, Count01);

					String Temp = WordList.get(j-1);
					WordList.set(j-1, WordList.get(j));
					WordList.set(j, Temp);
				}
			}
		}
	}


	/**
	 * 集計結果をCSV形式にして返す
	 * @return CSV形式のString配列<br>ReturnList
	 */
	public ArrayList<String> getWordCount(){

		ArrayList<String> ReturnList = new ArrayList<String>();

		for(int i=0; i<WordList.size(); i++){
			ReturnList.add(WordList.get(i) + "," + CountList.get(i));
		}

		return ReturnList;
	}
}
