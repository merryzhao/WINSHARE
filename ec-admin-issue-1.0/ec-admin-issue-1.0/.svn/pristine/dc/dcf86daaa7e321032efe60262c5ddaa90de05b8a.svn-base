$(document).ready(function() {
	Limit.initEvent();
});
(
	function() {
		window.Limit = {};
		
		Limit.saveLimit={
				add:false,
				modity:false
		};
		//初始化事件
		Limit.initEvent = function() {
			//初始化添加按钮
			$("#addLimit").click(function(){
				Limit.saveLimit.add = true;
				Limit.saveLimit.modity = false;
				$("#grade").val("");
				$("#weights").val("");
				
				$( "#add_limit" ).dialog({
					height:115,
					width:400,
					title:"限制补货新增界面",
					modal: true
				});
				
			});
			
			
			//初始化删除按钮
			$("#deleteLimit").click(function() {
				var trCheckBoxs = $(".js-tr-list").find("input:checked");
				var length = trCheckBoxs.length;
				if(length < 1) {
					alert("请勾选需要删除的限制补货的EC编码！");
					return false;
				}
				
				var ids = "";
				for ( var i = 0; i < length; i++) {
					var id = trCheckBoxs.eq(i).attr("id").slice("checkbox_".length);
					if(i != length-1) {
						ids += id +"|";
					} else {
						ids += id;
					}
				}
				
				
				if (!ids) {
					$("#message").text("请重新勾选需要删除的限制补货的EC编码！")
					return false;
				}
				
				if(!window.confirm("您确定要删除的限制补货的EC编码？")){
					return false;
				} 
				$("#message").text("系统正在处理，请耐心等待！……");
				$.ajax({
					url : "/replenishment/deleteFreeze",
					type : "post",
					async : false,
					data :{ids :ids},
					dataType : "html",
					success : function() {
						window.location.href="/replenishment/showMrProductFreeze?msg="+new Date().getTime();
						$("#message").text("删除成功！");
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
			});
			
			
			$("#form").submit( function () {
				//新增保存
				if(Limit.saveLimit.add){
					if(!Limit.validateForm()){
						return false;
					}
					Limit.closeDialog();
					$("#messsage").text("添加成功！")
				}
				if(Limit.saveLimit.modity){
					if(!Limit.validateForm()){
						return false;
					}
					Limit.closeDialog();
					$("#message").text("修改成功！")
				}
			} );
			
			//实现复选框全选
			$("#select_checkbox").click(function(){
				if($(this).attr("checked")){
					$(".checkbox_list").attr("checked",true);
				}else{
					$(".checkbox_list").attr("checked",false);
				}
			
			});
			//反选
			$("#invert_select_checkbox").click(function(){
				var checkboxList = $(".checkbox_list");
				var length = checkboxList.length;
				if($(this).attr("checked")){
					for ( var i = 0; i < length; i++) {
						var checkbox = $(".checkbox_list").eq(i);
						var checkState = checkbox.attr("checked");
						if (checkState) {
							checkbox.attr("checked",false);
						} else {
							checkbox.attr("checked",true);
						}
					}
				}else{
					for ( var i = 0; i < length; i++) {
						var checkbox = $(".checkbox_list").eq(i);
						var checkState = checkbox.attr("checked");
						if (!checkState) {
							checkbox.attr("checked",true);
						} else {
							checkbox.attr("checked",false);
						}
					}
				}
			
			});
			
		};
		//表单提交验证
		Limit.validateForm = function() {
			
			if($("#productSaleId").val().replace(/^(\s+)|(\s+)$/g, "")==""){
				alert("EC编码不能为空！");
				$("#productSaleId").focus();
				return false;
			} else {
				if(!(/^[0-9]*$/).test($("#productSaleId").val())) {
					alert("EC编码只能是数字的！");
					$("#productSaleId").focus();
					return false;
				}
			}
			
			return true;
		};
		Limit.closeDialog = function() {
			$( "#add_limit").dialog("close"); 
		};
		
	}	
)();