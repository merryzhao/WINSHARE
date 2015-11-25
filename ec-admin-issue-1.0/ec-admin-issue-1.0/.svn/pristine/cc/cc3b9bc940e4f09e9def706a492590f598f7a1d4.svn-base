
$(document).ready(
		function() {
			$("#roadmap-show").dialog({
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width:500
			});	
			$(".roadmapinfo")
			.bind(
					"click",
					function() { 
                        $("#show").html($(this).parent().find("span").html());// 把值设置到dialog中
						$("#roadmap-show").dialog("open");
 					});
		});
			