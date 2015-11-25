$(document).ready(function() {
	Supply.initEvent();
});
(
	function() {
		window.Supply = {};
		
		Supply.saveSupply={
				add:false,
				modity:false
		};
		//发货仓库dc信息
		Supply.dc= {
				
		};
		//初始化事件
		Supply.initEvent = function() {
			Supply.initDcInfo();
			//初始化添加按钮
			$("#addSupply").click(function(){
				Supply.saveSupply.add = true;
				Supply.saveSupply.modity = false;
				$(".dc").val("");
				$("#grade").val("");
				$("#weights").val("");
				
				$( "#add_supply" ).dialog({
					height:160,
					width:500,
					title:"团购销售数据影响权重新增界面",
					modal: true
				});
				
			});
			
			//初始化修改按钮
			$("#updateSupply").click(function(){
				var length = $("input:checked").length;
				if(length < 1) {
					alert("请勾选需要修改的团购权重配置！");
					return false;
				} 
				var id = $("input:checked").attr("id").slice(9);
				var dcText = $("#tr_list"+id).find("td").eq(1).text();
				var grade = $("#tr_list"+id).find("td").eq(2).text();
				var weightsText = $("#tr_list"+id).find("td").eq(3).text();
				var leng = weightsText.length;
				var weights = parseInt(weightsText.slice(0, leng-1))/100;
				
				Supply.saveSupply.add = false;
				Supply.saveSupply.modity = true;
				$(".dc").val(Supply.dc[dcText]);
				$("#grade").val(grade);
				$("#weights").val(weights)
				$("#id").val(id);
				$( "#add_supply" ).dialog({
					height:160,
					width:500,
					title:"团购销售数据影响权重修改界面",
					modal: true
				});
				
			});
			
			//初始化删除按钮
			$("#deleteSupply").click(function() {
				var length = $("input:checked").length;
				if(length < 1) {
					alert("请勾选需要删除的团购权重配置！");
					return false;
				}
				var id = $("input:checked").attr("id").slice(9);
				if (!id) {
					$("#message").text("请重新勾选需要删除的团购权重配置！")
					return false;
				}
				if(!window.confirm("您确定要删除该团购权重吗？")){
					return false;
				} 
				$("#message").text("系统正在处理，请耐心等待！……")
				$.ajax({
					url : "/replenishment/deleteSupply",
					type : "post",
					async : false,
					data :{id :id},
					dataType : "html",
					success : function() {
						window.location.href="/replenishment/showMrSupply?msg="+new Date().getTime();
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
				
			});
			
			
			$("#form").submit( function () {
				//新增保存
				if(Supply.saveSupply.add){
					if(!Supply.validateForm()){
						return false;
					}
					//alert("添加成功！");
					Supply.closeDialog();
				}
				if(Supply.saveSupply.modity){
					if(!Supply.validateForm()){
						return false;
					}
					//alert("修改成功！");
					Supply.closeDialog();
				}
			} );
			
			//处理复选框只能选择一个
			$(".checkbox_list").click(function() {
				if($(this).attr("checked")) {
					var id = $(this).attr("id");
					var checkboxList = $(".checkbox_list");
					var length = checkboxList.length;
					for ( var i = 0; i < length; i++) {
						if (id != checkboxList.eq(i).attr("id")  && checkboxList.eq(i).attr("checked")) {
							checkboxList.eq(i).attr("checked", false);
						}
					}
				}
				
			
			});
			
		};
		//表单提交验证
		Supply.validateForm = function() {
			if($("#grade").val().replace(/^(\s+)|(\s+)$/g, "")==""){
				alert("销售级别不能为空！");
				$("#grade").focus();
				return false;
			} else {
				if(!(/^[a-zA-Z]{1,1}[+-]{0,1}$/).test($("#grade").val())) {
					alert("销售级别只能是字母或者'+','-'号！");
					$("#grade").focus();
					return false;
				}
			}
			if($("#weights").val().replace(/^(\s+)|(\s+)$/g, "")==""){
				alert("权重数值不能为空！");
				$("#weights").focus();
				return false;
			} else {
				if(!(/^0\.[0-9]{0,2}$/).test($("#weights").val())) {
					alert("权重数值只能填写0到1之间的有效小数！");
					$("#weights").focus();
					return false;
				}
			}
			return true;
		};
		Supply.closeDialog = function() {
			$( "#add_supply").dialog("close"); 
		};
		//初始化dc信息
		Supply.initDcInfo = function() {
			var dc = $(".dc option");
			var len = dc.length;
			for ( var i = 0; i < len; i++) {
				var value = dc.eq(i).val();
				var text = dc.eq(i).text();
				Supply.dc[text] = value;
			}
		};
		
	}	
)();