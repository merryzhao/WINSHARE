function getSynonymUrl(word){
	var encoded = java.net.URLEncoder.encode(word,getCharsetName());
	return "http://dic.eqie.com/sc/search11.asp?keyword="+encoded+"&submit=%B2%E9%D1%AF";
}

function analyzeSynonym(text){
	var temp = "同义词是：";
	var index = text.indexOf(temp);
	if(index == -1){
		return null;
	}else{
		return text.substring(index+temp.length);
	}
}

function getCharsetName(){
	return "GBK";
}
