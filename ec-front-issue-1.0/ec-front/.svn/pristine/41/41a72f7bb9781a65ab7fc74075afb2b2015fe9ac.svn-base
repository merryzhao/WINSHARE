seajs.use(["jQuery","table","lazyimg"],function($,table,lazyimg){
	var $=$.sub();
	$("div[table]").each(function(){
		var el=$(this);
		table({
			context:el,
			label:"*[label='"+el.attr("table")+"']",
			className:el.attr("cn"),
			content:"*[content='"+el.attr("table")+"']"
		});
	});
       
	var body = $("body");
	var sort = body.attr("class");
	if(sort=="mall"||sort=="shopIndex"){
		return false;
	}
	
	var layout = $("div.layout");
	var header = body.find("div.header");
	var headerbg = body.find("div.header_redbg");
	var temp_ads_index = body.find("div#temp_ads");
	if(temp_ads_index.attr("available")=='false'){
		return false;
	}
	var html = temp_ads_index;    
  /*  if(body.attr("class")=="index"){*/
		 headerbg.before(html);
		 temp_ads_index.attr("class","temp_ads_index");
				 var height = temp_ads_index.height();
				 header.height(header.height()+height);	
				 headerbg.css("top","170px");
	/*}else{
		 layout.before(html);
		 temp_ads_index.attr("class","temp_ads_other");
	}  */
	temp_ads_index.show(); 
});