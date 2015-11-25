<%@ page language="java"
	import="java.text.*,java.util.Calendar,java.io.File"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%

	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	StringBuilder msg = new StringBuilder();
	
	String[] fullPath = new String[]{"/export/feed/etao/FullIndex.xml","/export/feed/douban/FullIndex.xml","/export/feed/weigou/FullIndex.xml"};
	String[] incrementPath = new String[]{"/export/feed/etao/IncrementIndex.xml","/export/feed/weigou/IncrementIndex.xml"};
	
	Calendar tt = Calendar.getInstance();
	tt.set(Calendar.HOUR_OF_DAY, 0);
	tt.set(Calendar.MINUTE, 0);
	tt.set(Calendar.SECOND, 0);
	long fullTime = tt.getTime().getTime();
	
	tt = Calendar.getInstance();
	tt.add(Calendar.MINUTE, -120);
	long incrementTime = tt.getTime().getTime();
	
	for(String path : fullPath){
		
		File file = new File(path);
		if(file==null || !file.exists()){
			msg.append(path + " not exists;");
			break;
		}
		
		if( file.lastModified()<fullTime ){
			msg.append(path + " last modified is " + format.format(file.lastModified())+";");
			break;
		}
	}
	
	for(String path : incrementPath){
		
		File file = new File(path);
		if(file==null || !file.exists()){
			msg.append(path + " not exists;");
			break;
		}
		
		if( file.lastModified()<incrementTime ){
			msg.append(path + " last modified is " + format.format(file.lastModified())+";");
			break;
		}
	}
	
	out.print("<pre>\n");
	if(msg.length()==0){
		out.print("status:true\n");
	}else{
		out.print("status:false\n");
		out.print("msg:"+msg+"\n");
	}
	out.print("</pre>");
	
%>