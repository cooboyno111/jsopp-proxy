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
//js proxy -> 编译为ES5/ES6模式
public class jsprocess {
	public String head[]={
			"function new_Proxy(obj,proxy){",
			"if(proxy.get==undefined){",
			"obj.pget=function(target,prop){return target[prop];};",
			"}else{obj.pget=proxy.get;}",
			"if(proxy.deleteProperty==undefined){",
			"obj.pdel=function(target,prop){delete target[prop];};",
			"}else{obj.pdel=proxy.deleteProperty;}",
			"if(proxy.set==undefined){",
			"obj.pset=function(target,prop,value){target[prop]=value};",
			"}else{obj.pset=proxy.set;}",
			"obj._get=function(key){return this.pget(this,key)};",
			"obj._del=function(key){ this.pdel(this,key)};",
			"obj._set=function(key,val){return this.pset(this,key,val)};",
			"return obj;}",
			"function aop(obj,key,op,val){var a=obj._get(key); if(op==='+='){a+=val;} else if(op==='-='){a-=val;} else if(op==='*='){a*=val;} else if(op==='/='){a/=val;} else if(op==='%='){a%=val;} return a}"
	};
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
		//功能以 ->开始 可能以)结束 可能以 ,结束 可能以 ;结束  可能以=结束 找到非负最小的即可
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
		//从小到大排序
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
		//功能以 ->开始 可能以)结束 可能以 ,结束 可能以 ;结束  可能以=结束 找到非负最小的即可
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
		//从小到大排序
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
	public void process_es6(String[] args) {
		System.out.println("process_es6");
		try {
			Vector<String> sv = new Vector<String>();
			FileInputStream in = new FileInputStream(args[0]);
			//FileInputStream in = new FileInputStream("C:/Apkdb/tproxy0.js");
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String str = null;
			int linenum=1;
			while ((str = br.readLine()) != null) {
				str=str+"\r";//为处理方便每行末尾添加换行符\r
				int st = str.indexOf("->");
				if (st != -1) {
					str=str.replace("->", ".");
					sv.add(str);
				}else{
					sv.add(str);
				}
				linenum++;
			}

			br.close();

			in.close();

			// write string to file
			//FileOutputStream out = new FileOutputStream("C:/Apkdb/tproxy_es6.js");
			FileOutputStream out = new FileOutputStream(args[1]);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			// write other
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
	public void process_es5(String[] args) {
		System.out.println("process_es5");
		try {
			Vector<String> sv = new Vector<String>();
			FileInputStream in = new FileInputStream(args[0]);
			//FileInputStream in = new FileInputStream("C:/Apkdb/tproxy0.js");
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String str = null;
			int linenum=1;
			while ((str = br.readLine()) != null) {
				str=str+"\r";//为处理方便每行末尾添加换行符\r
				int st = str.indexOf("->");
				if (st != -1) {
					//check st到下一个空格前有无();
					int stt1=str.indexOf("()",st);
					if(stt1!=-1){
						System.out.println("Process line:"+linenum+" str:"+str);
						//这行的->表示一个方法调用使用。去替换即可
//						str=str.replace("->", ".");
						int stt0=str.indexOf("->");
						String midstr=str.substring(stt0,stt1);
						str=str.replace("->", "._get('");
						str=str.replace("()", "')()");
						
					}else{
						System.out.println("Process line:"+linenum+" str:"+str);
						//判断是get还是set
						int stt2=str.indexOf("=",st);
						boolean stt3=checkfuzhiop(str);
						LineProp lp=getProp(str,"->");
						if(stt3){
							//这是一个get操作
							//首先向prop的end 插入一个')
							lp=getProp(str,"->",false);
							StringBuilder  sb = new StringBuilder (str);
							sb.insert(lp.end, "')");
							str=sb.toString();
							//将->替换为._get('
							str=str.replace("->", "._get('");
							//后处理赋值操作
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
							//后处理delete 操作符
							int del_ind=str.indexOf("delete");
							if(del_ind!=-1){
								str=str.replace("_get", "_del");
								str=str.replace("delete", "");
							}
							//后处理+=操作符
							int plusset=str.indexOf(")+=");
							if(plusset!=-1){
								String objname=str.substring(0,str.indexOf("."));
								String midstr=str.substring(indd1+1, indd2);
								int ed=str.indexOf(";");
								if(ed==-1){ed=str.length();}
								String value=str.substring(plusset+3,ed);
								str=str.replace("_get", "_set");
								str=str.substring(0,plusset);
								str=str+",aop("+objname+","+"'"+midstr+"'"+","+"'+=',"+value+"));";
							}
							//后处理-=操作符
							int subset=str.indexOf(")-=");
							if(subset!=-1){
								String objname=str.substring(0,str.indexOf("."));
								String midstr=str.substring(indd1+1, indd2);
								int ed=str.indexOf(";");
								if(ed==-1){ed=str.length();}
								String value=str.substring(subset+3,ed);
								str=str.replace("_get", "_set");
								str=str.substring(0,subset);
								str=str+",aop("+objname+","+"'"+midstr+"'"+","+"'-=',"+value+"));";
							}
							//后处理*=操作符
							int mulset=str.indexOf(")*=");
							if(mulset!=-1){
								String objname=str.substring(0,str.indexOf("."));
								String midstr=str.substring(indd1+1, indd2);
								int ed=str.indexOf(";");
								if(ed==-1){ed=str.length();}
								String value=str.substring(mulset+3,ed);
								str=str.replace("_get", "_set");
								str=str.substring(0,mulset);
								str=str+",aop("+objname+","+"'"+midstr+"'"+","+"'*=',"+value+"));";
							}
							//后处理/=操作符
							int divset=str.indexOf(")/=");
							if(divset!=-1){
								String objname=str.substring(0,str.indexOf("."));
								String midstr=str.substring(indd1+1, indd2);
								int ed=str.indexOf(";");
								if(ed==-1){ed=str.length();}
								String value=str.substring(divset+3,ed);
								str=str.replace("_get", "_set");
								str=str.substring(0,divset);
								str=str+",aop("+objname+","+"'"+midstr+"'"+","+"'/=',"+value+"));";
							}
							//后处理%=操作符
							int modset=str.indexOf(")%=");
							if(modset!=-1){
								String objname=str.substring(0,str.indexOf("."));
								String midstr=str.substring(indd1+1, indd2);
								int ed=str.indexOf(";");
								if(ed==-1){ed=str.length();}
								String value=str.substring(modset+3,ed);
								str=str.replace("_get", "_set");
								str=str.substring(0,modset);
								str=str+",aop("+objname+","+"'"+midstr+"'"+","+"'%=',"+value+"));";
							}
						}else{
						if(stt2!=-1){
							//这是一个set操作
							//首先向prop的end 插入一个'
							StringBuilder  sb = new StringBuilder (str);
							sb.insert(lp.end, "'");
							str=sb.toString();
							//将->替换为._set('
							str=str.replace("->", "._set('");
							//替换=之前再找一下linprop.end 在它之前插入一个)
							LineProp lp2=getProp(str,"=");
							StringBuilder  sb2 = new StringBuilder (str);
							sb2.insert(lp2.end, ")");
							str=sb2.toString();
							//将=替换为,
							str=str.replace("=", ",");
						}else{
							//这是一个get操作
							//首先向prop的end 插入一个')
							StringBuilder  sb = new StringBuilder (str);
							sb.insert(lp.end, "')");
							str=sb.toString();
							//将->替换为._get('
							str=str.replace("->", "._get('");
						}
					  }
					}
					sv.add(str);
				}else{
					//后处理替换new Proxy
					int del_ind=str.indexOf("new Proxy");
					if(del_ind!=-1){
						str=str.replace("new Proxy", "new_Proxy");
					}
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
			// write head to file
			for (int i = 0; i < head.length; i++) {
				bw.write(head[i] + "\n");
			}
			// write other
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
		proc.process_es5(args);
		if(args[2].equals("ES5")){
		   proc.process_es5(args);
		   System.out.println("process in es5 mode infile="+args[0]+" outfile="+args[1]);
		}else
		{
			proc.process_es6(args);
			System.out.println("process in es6 mode infile="+args[0]+" outfile="+args[1]);
		}
	}
}
