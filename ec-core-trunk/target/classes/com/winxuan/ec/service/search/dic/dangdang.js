function analyze(text){
	var target = "name=\"keywords\"";
	if(text.indexOf(target)== -1){
		return null;
	}else{
		var suffex = "</a></td>";
		text = text.substring(0,text.length-suffex.length);
		var index = text.lastIndexOf(">");
		return text.substring(index+">".length);
	}
}