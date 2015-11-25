

function check(){
	var file = $("#file").val();
	if(file==""||file==null){
		$("#message").html("请选择一个Excel文件");
		return false;
	}else{
		return true;
	}
	
}
function checkTextArea(){
	var check = $("#presentcardIds").val();
	if (check.search("^[\\s*\\d\\s]*$") != 0) {
		$("#textMessage").html("只能为数字");
		return false;
	} else {
		var ids = $("#presentcardIds").val().split('\n');
		var idsStr = "";
		for ( var i = 0; i < ids.length; i++) {
			idsStr += ids[i].replace(/(^\s*)|(\s*$)/g, "") + ",";
		}
		$("#presentCardIdstr").val(idsStr);
		return true;
	}
}
