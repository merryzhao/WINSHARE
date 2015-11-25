function analyze(text){
	if(text.indexOf("class=\"key\"")== -1){
		return null;
	}else{
		var target = "target=\"_blank\">";
		var suffex = "</a></td>";
		var index = text.indexOf(target);
		return text.substring(index+target.length,text.length-suffex.length);
	}
}