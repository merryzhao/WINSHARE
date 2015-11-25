$(document).ready(function() {
	Update.initEvent();
});
(
	function() {
		window.Update = {};
		
		Update.saveUpdate={
				add:false,
				modity:false
		};
		//初始化事件
		Update.initEvent = function() {

			$(".td_click").click(
				Update.tdClickEvent
			);
		};
		
		//表格的单击事件
		Update.tdClickEvent = function() {
			var td = $(this);
			var id = td.attr("id").slice(3);
			var text = $.trim(td.find("a").text());
			td.html("");
			var input = $("<input type='text' style='width: 65px;' />");
			td.append(input);
			td.unbind("click");
			input.focus();
			input.attr("value",text);
			td.find("input").focusout(function() {
				var newText = $.trim(td.find("input").val());
				if(!(newText === "")){
					if(newText == 0) {
						alert("发票金额不能为0！");
						td.find("input").focus();
						return false;
					}
				}
				else{
					alert("发票金额不能为空");
					td.find("input").focus();
					return false;
				}
				td.html("<a href='javascript:void(0);'>"+newText+"</a>");
				$.ajax({
					url : "/orderinvoice/updateOrderInvoiceMoney",
					type : "post",
					async : false,
					data :{
							id : $.trim(id),
							money : $.trim(newText)
						},
					dataType : "html",
					success : function(data) {
							$("#messsage").text("修改发票金额成功！");
							td.bind("click",Update.tdClickEvent);
					}
				});
			});
		};
		
	}	
)();