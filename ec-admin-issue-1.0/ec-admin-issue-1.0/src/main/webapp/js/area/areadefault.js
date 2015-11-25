$(document).ready(function(){
	var view=null;
	 view ={
		init:function(){
			initArea(23, 175, -1, -1, -1);
			$("#province,#city,#country").change(function() {
					$("b").replaceWith("<b></b>");
					view.town();
			});
			$("#district").change(function() {
					view.town();
			});
		},
		town:function(){
			var district = $("#district option:selected").val();
			if(district.trim()!="请选择区县"&&parseInt(district)!=-1){
				$.post("/default/town?format=json&id="+district,function(data){
					var town = data.areaTown;
					if(town!=null){
						$("b").replaceWith("<b>当前默认区域为："+town+"</b>");
					}else{
						$("b").replaceWith("<b>"+$("#province option:selected").text()+"-"
								+$("#city option:selected").text()+"-"
								+$("#district option:selected").text()+"当前没有默认区域</b>");
					}
				});
			}
		}
	};
	view.init();
});