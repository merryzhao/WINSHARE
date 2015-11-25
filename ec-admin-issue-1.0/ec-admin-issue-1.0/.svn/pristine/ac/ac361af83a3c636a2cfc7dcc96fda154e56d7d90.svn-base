/**
 * 
 */ 
$(function(){
    window.cycle = {
		updateStatus : function(id){
			$( "#save_cycle" ).dialog({
				height:155,
				width:600,
				title:"周期参数修改界面",
				modal: true
			});
			var mcId = $("#tr_list"+id).find("td").eq(1).text();
			var saleCycle = $("#tr_list"+id).find("td").eq(2).text();
			var replenishmentCycle = $("#tr_list"+id).find("td").eq(3).text();
			var transitCycle = $("#tr_list"+id).find("td").eq(4).text();
			
			$("#mcCategoryId").val(mcId);
			$("#saleCycle").val(saleCycle);
			$("#replenishmentCycle").val(replenishmentCycle);
			$("#transitCycle").val(transitCycle);
			$("#cycleId").val(id);
        },
        closeDialog : function() {
			$( "#save_cycle").dialog("close"); 
		},
        submit : function(){
            if (!/\d+/.test($("#saleCycle").val())){
                alert("输入销售周期,并且保证为正整数");return false;
            }
            if (!/\d+/.test($("#replenishmentCycle").val())){
                alert("输入补货周期,并且保证为正整数");return false;
            }
            if (!/\d+/.test($("#transitCycle").val())){
                alert("输入在途周期,并且保证为正整数");return false;
            }
            return true;
        },
        findStatus : function(){
        	var mc = $("#mc").val();
        	var regEmpty = /^$/; 
        	if(regEmpty.test(mc)){
        		window.location.href="/replenishment/showMrCycle";
        	}else{
        		window.location.href="/replenishment/findMrCycle?mc="+mc;
        	}
        },
        deleteStatus : function(id){
        	$.ajax({
				url : "/replenishment/deleteMrCycle",
				type : "post",
				async : false,
				data :{"cycleId" :id},
				dataType : "html",
				success : function() {
					window.location.href="/replenishment/showMrCycle";
				},
				error : function (XMLHttpRequest, textStatus, errorThrown) {
				    this;
				}
			});
        }
    };
});