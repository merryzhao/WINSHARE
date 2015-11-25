$(document).ready(function() {
	ArtificialLimit.initEvent();
});
(
	function() {
		window.ArtificialLimit = {};
		
		ArtificialLimit.saveArtificialLimit={
				add:false,
				modity:false
		};
		//初始化事件
		ArtificialLimit.initEvent = function() {
			$("#endTime").datepicker({changeYear:true});
			//初始化添加按钮
			$("#addLimit").click(function(){
				ArtificialLimit.saveArtificialLimit.add = true;
				ArtificialLimit.saveArtificialLimit.modity = false;
				$("#grade").val("");
				$("#weights").val("");
				
				$( "#add_limit" ).dialog({
					height:150,
					width:400,
					title:"人工冻结补货新增界面",
					modal: true
				});
				
			});
			

			//初始化修改按钮
			$("#updateArtificialLimit").click(function(){
				var length = $("input:checked").length;
				if(length < 1) {
					alert("请勾选需要修改的SAP编码！");
					return false;
				} 
				var id = $("input:checked").attr("id").slice(9);
				
				var productSaleId = $("#tr_list_"+id).find("td").eq(1).text();
				ArtificialLimit.saveArtificialLimit.add = false;
				ArtificialLimit.saveArtificialLimit.modity = true;
				
				$("#productSaleId").focus();
				$("#productSaleId").val(productSaleId);
				$("#id").val(id);
				$( "#add_ArtificialLimit" ).dialog({
					height:115,
					width:400,
					title:"人工冻结补货修改界面",
					modal: true
				});
				
			});
			


			$("#form").submit( function () {
				//新增保存
				if(ArtificialLimit.saveArtificialLimit.add){
					if(!ArtificialLimit.validateForm()){
						return false;
					}
					ArtificialLimit.closeDialog();
					$("#messsage").text("添加成功！")
				}
				if(ArtificialLimit.saveArtificialLimit.modity){
					if(!ArtificialLimit.validateForm()){
						return false;
					}
					ArtificialLimit.closeDialog();
					$("#message").text("修改成功！")
				}
			});
			

			//初始化删除按钮
			$("#deleteArtificialLimit").click(function() {
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
				if(!window.confirm("您确定要删除的冻结补货的EC编码？")){
					return false;
				} 
				$("#message").text("系统正在处理，请耐心等待！……");
				
				$.ajax({
					url : "/replenishment/deleteArtificialFreeze",
					type : "post",
					async : false,
					data :{ids :ids},
					dataType : "html",
					success : function() {
						window.location.href="/replenishment/showMrArtificialProductFreeze?msg="+new Date().getTime();
						$("#message").text("删除成功！");
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
			
			});
			
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
		ArtificialLimit.validateForm = function() {
			
			if($("#productSaleId").val().replace(/^(\s+)|(\s+)$/g, "")==""){
				alert("productSaleId不能为空！");
				$("#productSaleId").focus();
				return false;
			} else {
				if(!(/^[0-9]*$/).test($("#productSaleId").val())) {
					alert("productsaleId只能是数字的！");
					$("#productSaleId").focus();
					return false;
				}
			}
			
			return true;
		};
		ArtificialLimit.closeDialog = function() {
			$( "#add_artificiallimit").dialog("close"); 
		};

		//表单提交验证
		ArtificialLimit.validateForm = function() {
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
	
		ArtificialLimit.closeDialog = function() {
			$( "#add_limit").dialog("close"); 
		};
		
		//表单提交验证
		ArtificialLimit.validateForm = function(){
			if($("#productSaleId").val().replace(/^(\s+)|(\s+)$/g, "")==""){
				alert("库位dc不能为空！");
				$("#productSaleId").focus();
				return false;
			}else{
				if(!(/^[0-9]*$/).test($("#productSaleId").val())) {
					alert("库位dc只能是数字的！");
					$("#productSaleId").focus();
					return false;
				}
			}
			return true;
		};
		
		ArtificialLimit.closeDialog = function(){
			$("add_limit").dialog("close");
		}
	}
	
)();