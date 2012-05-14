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
	//�e�Y�t�������ڂɉ��������x���쐬�ɗp����t�@�C����
	////////////////////////////////////////////////////

	//�Y�������X�g���i�[���Ă���t�@�C���̖���
	static String InactiveIngredientListFileName01 = "�Y�������X�g.txt";
	static String InactiveIngredientListFileName02 = "�Y�������X�g_�蓮.txt";
	//�P�ʃ��X�g���i�[���Ă���t�@�C���̖���
	private String UnitList01 = "�P�ʃ��X�g�i�`���j.csv";
	private String UnitList02 = "�P�ʃ��X�g�i�c��j.csv";
	private String UnitList03 = "�P�ʃ��X�g�i���{��Ǖ��j.csv";
	private String UnitList04 = "�P��.txt";
	//�u���p�f�[�^���i�[����Ă���t�H���_�̃p�X
	private String FolderPath = "Files/Data/LabelLists/";

	////////////////////////////////////////////////////
	/////////////			�����܂�		////////////
	////////////////////////////////////////////////////

	//���X�g�i�[�p�z��
	private ArrayList<String> InactiveIngredientList = null;
	private ArrayList<String> UnitList = null;


	//�폜�ΏۋL���E�����ꗗ
	private String[] DeleteSymbolList = {	"�u�v",
											"�i�j",
											"�H",
											"�k�H",
											"��",
											"��",
											"�k�\",
											"�k�[",
											"�k�]",
											"��",
											"���Q",
											"���R",
											"���j",
										};

	private String[] DeletePhraseList = {	"����",
											"���{��Ǖ�",
											"��Ǖ�",
											"�ǊO���K",
											"�ǊO�K",
											"�͉�"
										};


	//�u���Ώۃe�L�X�g�z��
	private ArrayList<String> TargetTextArray = null;
	//�u���Ώۃe�L�X�g��YJList
	private ArrayList<String> TargetTextYJ = null;
	//�u����e�L�X�g�z��
	private ArrayList<String> ReplacementAfterTextArray = new ArrayList<String>();




	/**
	 * �R���X�g���N�^���s���ɒu���p���X�g�쐬
	 * @param TA �u���ΏۂƂȂ�e�L�X�g�z��
	 */
	public CreateLabel_InactIngre(ArrayList<String> TA, ArrayList<String> TJ){

		//�������ϊ��p
		String[] Kansu = {"��","��","��","�O","�l","��","�Z","��","��","��"};
		String[] Number = {"�O","�P","�Q","�R","�S","�T","�U","�V","�W","�X"};


		this.TargetTextArray = TA;
		this.TargetTextYJ = TJ;

		//////////////
		//���X�g�쐬//
		//////////////
		ArrayList<String> Temp = new ArrayList<String>();
		SortStringList SSL = null;

		//////////
		//�Y����//
		//////////
		Temp.addAll(InOutFile.InputTextFile(FolderPath + InactiveIngredientListFileName01));
		Temp.addAll(InOutFile.InputTextFile(FolderPath + InactiveIngredientListFileName02));

		//�������̕ϊ�
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
		//�P��//
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
		//���X�g�쐬�����܂�//
		//////////////////////
	}


	//////////////////////////////
	//�ȉ��p�^�[���쐬�p���\�b�h//
	//////////////////////////////


	/**
	 *�Y�����p�^�[���쐬
	 *�Ώ�<br>
	 *�Y�����@�Y���܁@���`�܁@��܁@����܁@�������܁@����@���l�@�ɏՍ܁@�Y�t�n���t<br>
	 *�n�܁@�n���t�@��p��߉t�@�Ɖu�⏕�܁@�����܁@�����܁@��p�n���t�@�Y�t�n��
	 *
	 */
	public void CreatePatternActiveIngreandUnit(){



		//�Ώی��(�Y�����ȊO)
		String[] TargetWordList = {	"�Y����",		"���`��",		"���",			"�����",
									"��������",		"�ɏՍ�",		"�Y�t�n���t",
									"�n��",			"�n���t",		"��p��߉t",	"�Ɖu�⏕��",	"������",
									"������",		"��p�n���t",	"�Y�t�n��"
		};

		//�Y�����ȊO�̏��ł�����
		String[] OutTargetWordList = { "���l", "�d������", "�L������"

		};

		//�e�L�X�g1��1����p�^�[�����쐬
		for(int i=0; i<TargetTextArray.size(); i++){

			//�e�L�X�g���o
			String Text = TargetTextArray.get(i);
			//YJ�R�[�h
			String TargetYJ = TargetTextYJ.get(i);


			//�e�L�X�g������S�p�ɒu��
			Text = StringConv.eisukigoHanToZen(Text);

			//�e�L�X�g��<<BR>>�ŕ���
			String[] Temp = Text.split("�����a�q����", -1);

			//�Y�����̋L�q�͈̔͂��������邽�߂̃C���f�b�N�X
			int StartIndex = 999;
			int FinishIndex = Temp.length;



			//���������e�e�L�X�g�ɓY�����ɑ��������傪�i�[����Ă��邩�m�F
			for(int j=0; j<Temp.length; j++){
//				for(int k=0; k<TargetWordList.length; k++){
//					if(Temp[j].contains(TargetWordList[k])){
					if(Temp[j].contains("�Y����")){

						//�Y�������L�q����Ă��镶����̔z��̃C���f�b�N�X���L��
						StartIndex = j;
						int Index;
//						if((Index = Temp[j].indexOf(TargetWordList[k]))!=0){
						if((Index = Temp[j].indexOf("�Y����"))!=0){
							Temp[j] = Temp[j].substring(Index, Temp[j].length());
						}
						break;
					}
//				}
				if(StartIndex!=999){
					break;
				}
			}

			//�Y�����̋L�q�͈̔͂̏I��������T��
			for(int j=StartIndex; j<Temp.length; j++){
				for(int k=0; k<OutTargetWordList.length; k++){
					if(Temp[j].contains(OutTargetWordList[k])){
						//�Y�����̏�񂪏I�����Ă��镔���̃C���f�b�N�X���L��
						FinishIndex = j;
						break;
					}
				}
				if(FinishIndex!=Temp.length){
					break;
				}
			}

			//�ȍ~�A�L�q�p�^�[���쐬�Ɏg�p���镶����ϐ�
			String TargetText = "";
			//�Y�����̏�񂪂���΁A�i�[
			if(StartIndex!=999){
				//�Y�����̏��Ƃ��̑��̏�񂪓���detail�^�O���ɏ�����Ă���ꍇ�ɑΉ�
				if(StartIndex==FinishIndex){
					TargetText = Temp[StartIndex];
				}
				else{
					for(int j=StartIndex; j<FinishIndex; j++){
						TargetText = TargetText + Temp[j] + "�����a�q����";
					}
				}
			}





			////////////
			//�u���J�n//
			////////////


			//Step.1
			//�Y�����̖��̂�<InactIngreName>��
			for(int j=0; j<InactiveIngredientList.size(); j++){
				TargetText = TargetText.replaceAll(InactiveIngredientList.get(j), "<InactIngreName>");
			}


			//Step.2
			//����+�P�ʂ�<Number><Unit>��
			String NumberRegex = "[�O-�X](([�O-�X]|[�C�D�^�`�H�~][�O-�X])+)?";
			boolean Replaceflag = false;
			if(TargetText.contains("��")){
				Replaceflag = true;
				TargetText = TargetText.replaceAll("��", "�O�O�O�O�O�O�O�O");
			}
			else if(TargetText.contains("��")){
				TargetText = TargetText.replaceAll("��", "�O�O�O�O");
				Replaceflag = true;
			}
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll(NumberRegex + UnitList.get(j), "<Number><Unit>");
			}
			NumberRegex = "��" + NumberRegex;
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll(NumberRegex + UnitList.get(j), "<Number><Unit>");
			}

			if(Replaceflag){
				TargetText = TargetText.replaceAll("�O�O�O�O�O�O�O�O", "��");
				TargetText = TargetText.replaceAll("�O�O�O�O", "��");
			}

			//Step.3
			//�^�{�P�ʂ��^<Unit>��
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll("�^" + UnitList.get(j), "�^<Unit>");
			}
			//�P�ʁ^�P�ʂ�<Unit>��
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll(UnitList.get(j) + "<Unit>", "�^<Unit>");
			}

			//Step.4
			//�i�P�ʁj���i<Unit>�j��
			for(int j=0; j<UnitList.size(); j++){
				TargetText = TargetText.replaceAll("�i" + UnitList.get(j) + "�j", "�i<Unit>�j");
			}


			//Step.5
			//����
			TargetText = TargetText.replaceAll("<Number><Unit>�^<Number><Unit>", "<Number><Unit>");
			TargetText = TargetText.replaceAll("<Unit>�^<Unit>", "<Unit>");
			TargetText = TargetText.replaceAll("�c+", "�c");
			TargetText = TargetText.replaceAll("�E+", "�E");

			//Step.6
			//������<Number>��
			NumberRegex = "[�O-�X](([�O-�X]|[�C�D�^�`�H�~][�O-�X])+)?";
			TargetText = TargetText.replaceAll(NumberRegex, "<Number>");

			//Step.7
			//�s�v�L���E�����̍폜
			for(int j=0; j<DeletePhraseList.length; j++){
				TargetText = TargetText.replaceAll(DeletePhraseList[j], "");
			}
			for(int j=0; j<DeleteSymbolList.length; j++){
				TargetText = TargetText.replaceAll(DeleteSymbolList[j], "");
			}

			//Step.8
			//��؂蕶���̍폜
			TargetText = TargetText.replaceAll("�A", "");
			TargetText = TargetText.replaceAll("�B", "");
			TargetText = TargetText.replaceAll("�C", "");
			TargetText = TargetText.replaceAll("�E", "");
			TargetText = TargetText.replaceAll("����", "");
			TargetText = TargetText.replaceAll("�@", "");

			//Step.9
			//<<BR>>�̒���
			TargetText = TargetText.replaceAll("^���a�q��", "");
			TargetText = TargetText.replaceAll("���a�q��$", "");
			TargetText = TargetText.replaceAll("^�����a�q����", "");
			TargetText = TargetText.replaceAll("�����a�q����$", "");


			if(!(TargetText.length()==0) && TargetText.indexOf("��")==TargetText.length()-1){
				TargetText = TargetText.substring(0, TargetText.length()-1);
			}

			//�^�O(<aaa>)�����x��([aaa])�ɒu��
			TargetText = TargetText.replaceAll("<", "[");
			TargetText = TargetText.replaceAll(">", "]");

			//�����a�q�����⁃�a�q���𔼊p��
			TargetText = TargetText.replaceAll("�����a�q����", "<<BR>>");
			TargetText = TargetText.replaceAll("���a�q��", "<BR>");



			//�o�͗p�Ɋi�[
			ReplacementAfterTextArray.add(TargetYJ + "\t" + TargetText);

			if(i%1000==0){
				System.out.println(i+":�I��");
			}
			if(i==1682){
				System.out.println(i+":�I��");
			}
		}
	}




	/**
	 * ���x���쐬���̕s�v�����T���E�폜�p\n
	 * �Y�������̕s�v���ɑ΂��āA�c���<BR>�����܂ł��폜���邽�߂ɁA���̕�����T�����ĕ�����������쐬���A�폜
	 * @param �폜�Ώ�TEXT, �s�v���J�nIndex
	 * @return �s�v�����폜�ς�TEXT
	 */
	private String SearchDeletePart(String TargetText, int fromindex){
		int lastindex;

		//�Y�������̍Ō��T��
		//�Y��������T�� +2�ƂȂ��Ă���̂͊J�n�����ƏI���̕�������v�����Ȃ�����
		if((lastindex = TargetText.indexOf("�����a�q����", fromindex+2)) != -1){
			//�����a�q�������폜���邽�߂ɒ���
			lastindex += 5;
		}
		//������Ȃ�������A�Ώی��ȍ~��S�č폜���邽�߂ɁA��ԍŌ��Index�ԍ����擾
		else{
			lastindex = TargetText.length()-1;
		}

		//�폜�����̕�������擾
		String S = TargetText.substring(fromindex, lastindex);

		//�폜
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
