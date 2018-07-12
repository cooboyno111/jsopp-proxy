import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Vector;

public class jsprocess {
	public  void instovec(Vector <Integer> v,int val){
		if(val!=-1){
			v.addElement(val);
		}
	}
	class LineProp{
		public LineProp(){}
		String prop;
		int st;
		int end;
	}
	public boolean checkfuzhiop(String str)
	{
		String oprt[]={"+=","-=","*=","/=","%=","if"};
		Vector <Integer> v=new Vector<Integer>();
		for(int i=0;i<oprt.length;i++){
			int st1=str.indexOf(oprt[i], 0);
			System.out.println("checkfuzhiop : "+st1+" oprt ="+oprt[i]);
			instovec(v, st1);
		}
		System.out.println("size="+v.size());
		if(v.size()>=0){
			return true;
		}else
		{
			return false;
		}
	}
	public LineProp getProp(String str,String op,boolean haseq)
	{
		//String oprt[]={")",",",";","\r"};
		String oprt[]={")",",",";","==",">=","<=","!=","+","-","++","--","||","&&","*","/","%",">","<","\r"};
		//������ ->��ʼ ������)���� ������ ,���� ������ ;����  ������=���� �ҵ��Ǹ���С�ļ���
		int st = str.indexOf(op)+op.length();
//		int st1=str.indexOf(")", st);
//		int st2=str.indexOf(",", st);
//		int st3=str.indexOf(";", st);
//		int st5=str.indexOf("\r", st);
		Vector <Integer> v=new Vector<Integer>();
		for(int i=0;i<oprt.length;i++){
			int st1=str.indexOf(oprt[i], st);
			System.out.println("st1 ="+st1+" oprt ="+oprt[i]);
			instovec(v, st1);
		}
		if(haseq){
		if(op.equals("->")){
		int st4=str.indexOf("=", st);
		System.out.println("st1 ="+st4);
		instovec(v, st4);
		}}
		//��С��������
		Collections.sort(v);  
		System.out.println("v-size ="+v.size());
		int end=v.firstElement();
		System.out.println("end ="+end);
		String str2=str.substring(st,end);
		System.out.println("str2 ="+str2);
		LineProp pro=new LineProp();
		pro.prop=str2;
		pro.st=st;
		pro.end=end;
		return pro;
	}
	public LineProp getProp(String str,String op)
	{
		//String oprt[]={")",",",";","\r"};
		String oprt[]={")",",",";","==",">=","<=","!=","+","-","++","--","||","&&","*","/","%",">","<","\r"};
		//������ ->��ʼ ������)���� ������ ,���� ������ ;����  ������=���� �ҵ��Ǹ���С�ļ���
		int st = str.indexOf(op)+op.length();
//		int st1=str.indexOf(")", st);
//		int st2=str.indexOf(",", st);
//		int st3=str.indexOf(";", st);
//		int st5=str.indexOf("\r", st);
		Vector <Integer> v=new Vector<Integer>();
		for(int i=0;i<oprt.length;i++){
			int st1=str.indexOf(oprt[i], st);
			System.out.println("st1 ="+st1+" oprt ="+oprt[i]);
			instovec(v, st1);
		}
		if(op.equals("->")){
		int st4=str.indexOf("=", st);
		System.out.println("st1 ="+st4);
		instovec(v, st4);
		}
		//��С��������
		Collections.sort(v);  
		System.out.println("v-size ="+v.size());
		int end=v.firstElement();
		System.out.println("end ="+end);
		String str2=str.substring(st,end);
		System.out.println("str2 ="+str2);
		LineProp pro=new LineProp();
		pro.prop=str2;
		pro.st=st;
		pro.end=end;
		return pro;
	}
	public void process(String[] args) {
		try {
			Vector<String> sv = new Vector<String>();
			FileInputStream in = new FileInputStream(args[0]);
			//FileInputStream in = new FileInputStream("C:/Apkdb/tproxy.js");
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String str = null;
			int linenum=1;
			while ((str = br.readLine()) != null) {
				str=str+"\r";//Ϊ������ÿ��ĩβ��ӻ��з�\r
				int st = str.indexOf("->");
				if (st != -1) {
					//check st����һ���ո�ǰ����();
					int stt1=str.indexOf("()",st);
					if(stt1!=-1){
						System.out.println("Process line:"+linenum+" str:"+str);
						//���е�->��ʾһ����������ʹ�á�ȥ�滻����
						str=str.replace("->", ".");
					}else{
						System.out.println("Process line:"+linenum+" str:"+str);
						//�ж���get����set
						int stt2=str.indexOf("=",st);
						boolean stt3=checkfuzhiop(str);
						LineProp lp=getProp(str,"->");
						if(stt3){
							//����һ��get����
							//������prop��end ����һ��')
							lp=getProp(str,"->",false);
							StringBuilder  sb = new StringBuilder (str);
							sb.insert(lp.end, "')");
							str=sb.toString();
							//��->�滻Ϊ._get('
							str=str.replace("->", "._get('");
							//����ֵ����
							int indd1=str.indexOf("'");
							int indd2=str.lastIndexOf("'");
							if(indd1!=-1&&indd2!=-1){
							String midstr=str.substring(indd1+1, indd2);
							System.out.println("midstr:"+midstr);
							int indd3=midstr.indexOf("=");
							if(indd3!=-1){
								str=str.replace("_get", "_set");
								str=str.replace("=", "',");
								str=str.replace("')", ")");
							}
							}
							//����delete ������
							int del_ind=str.indexOf("delete");
							if(del_ind!=-1){
								str=str.replace("_get", "_del");
								str=str.replace("delete", "");
							}
						}else{
						if(stt2!=-1){
							//����һ��set����
							//������prop��end ����һ��'
							StringBuilder  sb = new StringBuilder (str);
							sb.insert(lp.end, "'");
							str=sb.toString();
							//��->�滻Ϊ._set('
							str=str.replace("->", "._set('");
							//�滻=֮ǰ����һ��linprop.end ����֮ǰ����һ��)
							LineProp lp2=getProp(str,"=");
							StringBuilder  sb2 = new StringBuilder (str);
							sb2.insert(lp2.end, ")");
							str=sb2.toString();
							//��=�滻Ϊ,
							str=str.replace("=", ",");
						}else{
							//����һ��get����
							//������prop��end ����һ��')
							StringBuilder  sb = new StringBuilder (str);
							sb.insert(lp.end, "')");
							str=sb.toString();
							//��->�滻Ϊ._get('
							str=str.replace("->", "._get('");
						}}
					}
					sv.add(str);
				}else{
					sv.add(str);
				}
				linenum++;
			}

			br.close();

			in.close();

			// write string to file
			//FileOutputStream out = new FileOutputStream("C:/Apkdb/tproxy3.js");
			FileOutputStream out = new FileOutputStream(args[1]);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8")); 
			for (int i = 0; i < sv.size(); i++) {
				bw.write(sv.elementAt(i) + "\n");
			}
			bw.close();

			out.close();

		}

		catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		catch (IOException e) {

			e.printStackTrace();

		}
	}
	public static void main(String[] args) {
		jsprocess proc=new jsprocess();
		proc.process(args);
		
	}
}
