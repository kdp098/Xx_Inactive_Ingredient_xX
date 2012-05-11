package textmanipulation.MorphologicalAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import textmanipulation.WordCount;


/**
 * 形態素解析を行って、結果を返すクラス
 * @author Ryo Okuya
 */
public class MorphologicalAnalysis {


	//形態素解析に用いるユーザー辞書のファイルPath
	static String UserDicFilePath = "E:\\okuya\\Data\\形態素解析\\user.dic";


	private String FileName;
	private ArrayList<Morpheme[]> MAList = new ArrayList<Morpheme[]>();

	/**
	 * コンストラクタ
	 * 対象のファイル内のテキストに対して形態素解析を行い、含まれている品詞と単語を格納
	 */
	public MorphologicalAnalysis(String FileName){

		this.FileName = FileName;

		//対象ファイルの絶対パスを取得
		String AbsoluteFilePath	= new File(this.FileName).getAbsolutePath();

		try{
			//MeCabのプロセス作成(ユーザー辞書指定)
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("mecab " + AbsoluteFilePath + " -u " + UserDicFilePath);
			System.out.println("mecab " + AbsoluteFilePath + " -u " + UserDicFilePath);

			//入力
			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			//一時格納用
			ArrayList<Morpheme> TempList = new ArrayList<Morpheme>();

			//読み込み
			String tmp;
			while((tmp = br.readLine()) != null){
				System.out.println(tmp);
				if(!tmp.equals("EOS")){
					//形態素解析
					Morpheme M = new Morpheme(tmp);
					TempList.add(M);
				}
				else{
					//NounListをString[]に直してListに格納
					Morpheme[] TEMP = new Morpheme[TempList.size()];


					for(int i=0; i<TEMP.length; i++){
						TEMP[i] = TempList.get(i);
					}
					MAList.add(TEMP);

					//一時格納Listの初期化
					TempList.clear();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 対象となる品詞を選び、集計して返す(原形)
	 * @param 品詞名
	 * @return CSV用集計表<br>CountList
	 */
	public ArrayList<String> CountWord_BaseForm(String PartofSpeech){

		ArrayList<String> TempList = new ArrayList<String>();

		for(int i=0; i<MAList.size(); i++){
			Morpheme[] MAArray = MAList.get(i);

			for(int j=0; j<MAArray.length; j++){

				String Word = MAArray[j].BASE_FORM;
				String Kind = MAArray[j].PART_OF_SPEECH;

				if(Kind.equals(PartofSpeech)){
					TempList.add(Word);
				}
			}
		}
		WordCount WC = new WordCount(TempList);
		WC.Desc();

		return WC.getWordCount();
	}

	/**
	 * 対象となる品詞を選び、集計して返す(表層形)
	 * @param 品詞名
	 * @return CSV用集計表<br>CountList
	 */
	public ArrayList<String> CountWord_SurfaceForm(String PartofSpeech){

		ArrayList<String> TempList = new ArrayList<String>();

		for(int i=0; i<MAList.size(); i++){
			Morpheme[] MAArray = MAList.get(i);

			for(int j=0; j<MAArray.length; j++){

				String Word = MAArray[j].SURFACE_FORM;
				String Kind = MAArray[j].PART_OF_SPEECH;

				if(Kind.equals(PartofSpeech)){
					TempList.add(Word);
				}
			}
		}
		WordCount WC = new WordCount(TempList);
		WC.Desc();

		return WC.getWordCount();
	}



	/**
	 * 対象となる品詞を選び、品詞が連続したものは結合し、集計して配列で返す
	 * @param 品詞名
	 * @return 各単語と出現頻度の配列<br>ReturnList
	 */
	public ArrayList<String> CountUnitWord(String PartofSpeech){

		ArrayList<String> TempList = new ArrayList<String>();

		for(int i=0; i<MAList.size(); i++){
			Morpheme[] MAArray = MAList.get(i);
			ArrayList<String> UnitStringList = new ArrayList<String>();

			for(int j=0; j<MAArray.length; j++){

				String Word = MAArray[j].SURFACE_FORM;
				String Kind = MAArray[j].PART_OF_SPEECH;

				if(Kind.equals(PartofSpeech)){
					UnitStringList.add(Word);
				}
				else{
					if(!UnitStringList.isEmpty()){
						String UnitString = "";
						for(int k=0; k<UnitStringList.size(); k++){
							UnitString += UnitStringList.get(k);
						}
						TempList.add(UnitString);
						UnitStringList.clear();
					}
				}

				if(j==MAArray.length-1){
					String UnitString = "";
					for(int k=0; k<UnitStringList.size(); k++){
						UnitString += UnitStringList.get(k);
					}
					TempList.add(UnitString);
					UnitStringList.clear();
				}
			}
		}
		WordCount WC = new WordCount(TempList);
		WC.Desc();

		return WC.getWordCount();
	}

	/**
	 * get
	 */
	public ArrayList<Morpheme[]> getMAList(){
		return MAList;
	}





}
