$(document).ready(function() {
	MrSubMcCategory.initEvent();
});
(
	function() {
		window.MrSubMcCategory = {};
		
		//初始化事件
		MrSubMcCategory.initEvent = function() {
			
			//初始化删除按钮
			$("#deleteSubMcCategory").click(function() {
				var length = $("input:checked").length;
				if(length < 1) {
					alert("请勾选需要删除的MC二次分类数据！");
					return false;
				}
				var id = $("input:checked").attr("id").slice(9);
				if (!id) {
					$("#message").text("请重新勾选需要删除的MC二次分类数据！")
					return false;
				}
				if(!window.confirm("您确定要删除该MC二次分类数据吗？")){
					return false;
				} 
				$("#message").text("系统正在处理，请耐心等待！……")
				$.ajax({
					url : "/replenishment/deleteSubMcCategory",
					type : "post",
					async : false,
					data :{id :id},
					dataType : "html",
					success : function() {
						window.location.href="/replenishment/mrSubMcCategory?msg="+new Date().getTime();
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
				
			});
			
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
	}	
)();