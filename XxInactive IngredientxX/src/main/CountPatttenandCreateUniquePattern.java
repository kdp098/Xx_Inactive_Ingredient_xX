package main;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.WordCount;

public class CountPatttenandCreateUniquePattern {

	//�p�^�[�����X�g
	static String PatternListFileName = "PatternList_Inact.txt";
	//�p�X
	static String Path = "Files/Data/";

	//�o�̓t�@�C����
	static String OutputFileName01 = "CountPattern_Inact.csv";
	static String OutputFileName02 = "UniquePatternList_Inact.txt";
	//�o�̓t�H���_�p�X
	static String OutputPath = "Files/CPandCUP/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		//�L�q�p�^�[���̓���
		ArrayList<String> PatternList = InOutFile.InputTextFile(Path + PatternListFileName);
		//�L�q�p�^�[���̃e�L�X�g�����݂̂��i�[����z��
		ArrayList<String> PatternList_Text = new ArrayList<String>();
		//�d�������ɋL�q�p�^�[�����i�[����z��
		ArrayList<String> UniquePatternList = new ArrayList<String>();


		//�L�q�p�^�[������YJ�R�[�h�����O
		for(int i=0; i<PatternList.size(); i++){
			String Text = PatternList.get(i).split("\t", -1)[1];
			PatternList_Text.add(Text);
		}
		//�L�q�p�^�[�����J�E���g���āA�~���ɕ��ׂ�
		WordCount WC = new WordCount(PatternList_Text);
		WC.Desc();

		//�o��
		ArrayList<String> Output01 = WC.getWordCount();
		InOutFile.OutputText(OutputFileName01, OutputPath, Output01);

		//���j�[�N�ȋL�q�p�^�[�����o��
		for(int i=0; i<Output01.size(); i++){
			UniquePatternList.add(Output01.get(i).split(",")[0]);
		}
		InOutFile.OutputText(OutputFileName02, OutputPath, UniquePatternList);


	}

}
