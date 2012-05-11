package main;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.createLabel.CreateLabel_InactIngre;

/**
 * �g���̋L�q���Y�����̋L�q�p�^�[�����쐬����N���X
 */
public class CreatePatternList_Inact {


	//�g���e�L�X�g�������Ă���t�@�C����
	static String TextFileName = "HOT9YJ_CompotisionTextList.txt";
	//�f�[�^�t�H���_�p�X
	static String DataFolderPath = "Files/Data/";

	//�o�͗p�t�@�C����
	static String OutputFileName = "PatternList_Inact.txt";
	//�o�͗p�t�H���_�̃p�X
	static String Path = "Files/CP_I/";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

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
