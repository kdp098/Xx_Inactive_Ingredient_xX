package textmanipulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * ファイル入出力用のクラス
 * @author Okuya
 */
public class InOutFile {


	/**
	 * 対象ファイルから複数行入力して、String配列で返すメソッド
	 * @param 対象となるファイル名
	 * @return 対象ファイル内の文字列を格納した配列<br>
	 * TempList→String型配列
	 */
	public static ArrayList<String> InputTextFile(String TargetFileName){

		//一時格納用
		ArrayList<String> TempList = new ArrayList<String>();
		try{
			//入力
			FileInputStream	fis = new FileInputStream(TargetFileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			String Temp;
			while((Temp = br.readLine()) != null){
				TempList.add(Temp);
			}

			//クローズ
			br.close();
			isr.close();
			fis.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return TempList;
	}

	/**
	 * 対象ファイルから複数行入力し、指定した文字で分割した文字列をString二次元配列で返すメソッド
	 * @param 対象となるファイル名, 分割に使う文字
	 * @return 対象ファイル内の文字列を格納した配列<br>
	 * TempList→String型二次元配列
	 */
	public static ArrayList<String[]> InputTextFilePlusSplit(String TargetFileName, String SplitString){

		//一時格納用
		ArrayList<String[]> TempList = new ArrayList<String[]>();
		try{
			//入力
			FileInputStream	fis = new FileInputStream(TargetFileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			String Temp;
			while((Temp = br.readLine()) != null){
				//分割
				String[] SplitTemp = Temp.split(SplitString, -1);
				TempList.add(SplitTemp);
			}

			//クローズ
			br.close();
			isr.close();
			fis.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return TempList;
	}

	/**
	 * 対象ファイルから複数行入力して、特定のヘッダーの文字列をString配列で返すメソッド(CSVを想定)
	 * @param 対象となるファイル名,対象となるヘッダー
	 * @return 対象ファイル内の文字列を格納した配列<br>
	 * TempList<br>
	 */
	public static ArrayList<String> InputTextFileforHeader(String TargetFileName, String TargetHeader){


		//対象ファイルからの入力
		ArrayList<String[]> StringList = InputTextFilePlusSplit(TargetFileName, ",");

		//Return用配列
		ArrayList<String> TempList = new ArrayList<String>();


		//ヘッダー番号の特定
		String[] Temp = StringList.get(0);
		int Index = -1;
		for(int i=0; i<Temp.length; i++){
			if(Temp[i].equals(TargetHeader)){
				Index = i;
				break;
			}
		}

		//Return用配列に格納
		for(int i=0; i<TempList.size()-1; i++){
			TempList.set(i, StringList.get(i+1)[Index]);
		}

		return TempList;
	}



	/**
	 * 対象ファイルへString配列を書き込みするメソッド(書き込んだ日付と時間をファイル名の先頭に付ける)
	 * @param 対象となるファイル名, ファイルが格納されているフォルダへのパス, 書きこむString型配列
	 */
	public static void OutputText(String FileName, String Path, ArrayList<String> TargetArray){

		try{
			Date D = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_");
			String DateS = sdf.format(D);


			//出力
			FileOutputStream fos = new FileOutputStream(Path + DateS + FileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);


			for(int i=0; i<TargetArray.size(); i++){

				bw.write(TargetArray.get(i));
				bw.newLine();

			}



			//クローズ
			bw.close();
			osw.close();
			fos.close();


		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
