package main;

import java.util.ArrayList;

import textmanipulation.InOutFile;
import textmanipulation.miniprosessing.SortStringList;


/**
 * ���{����c�̘A������J���Ă���Y�������̃f�[�^���L�ږ��݂̂̂𒊏o
 * @author Okuya
 *
 */
public class ExtractInactiveIngredient {



	//���̓t�@�C��_���J����Ă���Y�������
	static String InactiveFileName = "���i�Y���������̋L�ږ��̃��X�g.csv";
	//���̓t�@�C���̃t�H���_�p�X
	static String DataFilePath = "Files/Data/";

	//�o�̓t�@�C��_�Y�����L�ږ��̃��X�g
	static String InactiveIngredientListFileName = "InactiveIngredientList.txt";
	//�o�̓t�@�C���̃t�H���_�p�X
	static String OutputFilePath = "Files/EII/";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		//�t�@�C�����f�[�^����
		//���ږ�(No,�����R�[�h,�Y�������T��,���������ܗL,���ǎ���,�L�ږ���_������,�L�ږ���_�ʖ����͊ȗ���)
		ArrayList<String> FileData = InOutFile.InputTextFile(DataFilePath + InactiveFileName);

		//���o�p�̑O����(�_�u���R�[�e�[�V�������̌��͉��s���܂ނ���)
		ArrayList<String> SaveTemp = new ArrayList<String>();
		String TextTemp = "";
		boolean Flag = true;
		for(int i=0; i<FileData.size(); i++){
			//�f�[�^���o
			String Text = FileData.get(i);

			//�_�u���R�[�e�[�V�������܂܂�Ă�����A�_�u���R�[�e�[�V�������폜���A�t���O��ύX
			if(Text.contains("\"")){
				Text = Text.replaceAll("\"", "");
				Flag = !Flag;
			}

			//�e�L�X�g�̌���(�t���O��false�Ȃ��؂���ǉ�)
			if(Flag){
				TextTemp = TextTemp + Text;
			}
			else{
				TextTemp = TextTemp + Text + "_";
			}

			//�t���O��true�Ȃ�e�L�X�g���i�[����(��s�͔�΂�)
			if(Flag && TextTemp.length()!=0){
				SaveTemp.add(TextTemp);
				TextTemp = "";
			}
		}

		FileData = new ArrayList<String>(SaveTemp);



		//�o�̓t�@�C���pArrayList�̐錾
		ArrayList<String> Output_InactiveIngredientList = new ArrayList<String>();

		//�K�v�ȃf�[�^�𒊏o(�t�B����̍ŏ��ɋL�ڂ���Ă�������͑ΏۊO)
		for(int i=14; i<FileData.size(); i++){
			//�Ώە�����擾
			String TargetText = FileData.get(i);

			//CSV�`���Ȃ̂ŁA�J���}�ŕ���
			String[] SplitText = TargetText.split(",", -1);

			//�i�[�Ώۂ͋L�ږ��̂Ȃ̂ŁAindex = 5��6���i�[(�󕶎��͑ΏۊO)
			for(int j=5; j<SplitText.length; j++){
				//��؂蕶�����܂܂�Ă���ꍇ�͍X�ɕ���
				String[] Temp = SplitText[j].split("_");
				for(int k=0; k<Temp.length; k++){
					//��_�̋�؂�����݂���̂ōX�ɕ���
					String temp[] = Temp[k].split("�A", -1);
					for(int l=0; l<temp.length; l++){
						//�󔒂�\t�̏���
						temp[l] = temp[l].replaceAll(" ", "");
						temp[l] = temp[l].replaceAll("�@", "");
						temp[l] = temp[l].replaceAll("\t", "");
						Output_InactiveIngredientList.add(temp[l]);
					}
				}
			}
		}

		//�d���f�[�^�폜
		SortStringList SSL = new SortStringList(Output_InactiveIngredientList);
		SSL.DeleteOverlappingData();

		//�����񒷂Ń\�[�g
		SSL.SortLong();

		//�f�[�^�o��
		InOutFile.OutputText(InactiveIngredientListFileName, OutputFilePath, SSL.getTextList());
	}

}
