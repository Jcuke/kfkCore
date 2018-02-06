package com.jp.base.tool.doc;

import java.util.List;

public class Main {
	public	static	void	main(String[] args){
		try{
			
			String	config="classpath:/com/jp/unp/subject/**/*.class";
			if(args.length>0)
				config=args[0];
			testClasses(config);
			//testMethods();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void testClasses(String config)throws Exception{
		List<ClassInfo> classes=Scanner.scan(config);
		
		for(int i=0;i<classes.size();i++){
			ClassInfo c=classes.get(i);
			Scanner.scanClass(c);
			System.out.println(Desc.descClass(c, ""));
		}
	}
}
