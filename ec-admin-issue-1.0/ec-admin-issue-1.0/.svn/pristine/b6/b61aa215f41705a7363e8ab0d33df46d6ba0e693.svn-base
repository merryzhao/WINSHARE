$(document).ready(function() {
	UpdateApp.initEvent();
	UpdatePhone.initEvent();
	UpdateThreshold.initEvent();
	UpdateTimeout.initEvent();
});
(
	function() {
		window.UpdateApp = {};
		window.UpdatePhone = {};
		window.UpdateThreshold = {};
		window.UpdateTimeout = {};
		
		UpdateApp.saveUpdate={
				add:false,
				modity:false
		};
		UpdatePhone.saveUpdate={
				add:false,
				modity:false
		};
		UpdateThreshold.saveUpdate={
				add:false,
				modity:false
		};
		UpdateTimeout.saveUpdate={
				add:false,
				modity:false
		};
		
		//初始化事件
		UpdateApp.initEvent = function() {

			$(".td_click_app").click(
				UpdateApp.tdClickEvent
			);
		};
		UpdatePhone.initEvent = function() {

			$(".td_click_phone").click(
				UpdatePhone.tdClickEvent
			);
		};
		UpdateThreshold.initEvent = function() {

			$(".td_click_threshold").click(
				UpdateThreshold.tdClickEvent
			);
		};
		UpdateTimeout.initEvent = function() {

			$(".td_click_timeout").click(
				UpdateTimeout.tdClickEvent
			);
		};
		
		//表格的单击事件
		UpdateApp.tdClickEvent = function() {
			var td = $(this);
			var appkey = td.attr("appkey").slice(3);
			var hostname = td.attr("hostname").slice(3);
			var text = $.trim(td.find("a").text());
			td.html("");
			var input = $("<input type='text' style='width: 100px;' />");
			td.append(input);
			td.unbind("click");
			input.focus();
			input.attr("value",text);
			td.find("input").focusout(function() {
				var newText = $.trim(td.find("input").val());
				if(newText == ""){
					alert("填写内容不能为空");
					td.find("input").focus();
					return false;
				}
				td.html("<a href='javascript:void(0);'>"+newText+"</a>");
				$.ajax({
					url : "/heartbeat/updateBeatApp",
					type : "post",
					async : false,
					data :{
							appkey : $.trim(appkey),
							hostname : $.trim(hostname),
							app : $.trim(newText)
						},
					dataType : "html",
					success : function(data) {
							$("#messsage").text("修改应用配置信息成功！");
							td.bind("click",UpdateApp.tdClickEvent);
					}
				});
			});
		};		
		
		UpdatePhone.tdClickEvent = function() {
			var td = $(this);
			var appkey = td.attr("appkey").slice(3);
			var hostname = td.attr("hostname").slice(3);
			var text = $.trim(td.find("a").text());
			td.html("");
			var input = $("<input type='text' style='width: 200px;' />");
			td.append(input);
			td.unbind("click");
			input.focus();
			input.attr("value",text);
			td.find("input").focusout(function() {
				var newText = $.trim(td.find("input").val());
				if(newText == ""){
					alert("填写内容不能为空,各号码之间用逗号隔开");
					td.find("input").focus();
					return false;
				}
				td.html("<a href='javascript:void(0);'>"+newText+"</a>");
				$.ajax({
					url : "/heartbeat/updateBeatPhone",
					type : "post",
					async : false,
					data :{
							appkey : $.trim(appkey),
							hostname : $.trim(hostname),
							phone : $.trim(newText)
						},
					dataType : "html",
					success : function(data) {
							$("#messsage").text("修改应用配置信息成功！");
							td.bind("click",UpdatePhone.tdClickEvent);
					}
				});
			});
		};
		
		UpdateThreshold.tdClickEvent = function() {
			var td = $(this);
			var appkey = td.attr("appkey").slice(3);
			var hostname = td.attr("hostname").slice(3);
			var text = $.trim(td.find("a").text());
			td.html("");
			var input = $("<input type='text' style='width: 100px;' />");
			td.append(input);
			td.unbind("click");
			input.focus();
			input.attr("value",text);
			td.find("input").focusout(function() {
				var newText = $.trim(td.find("input").val());
				if(!(newText === "")){
					if(newText == 0) {
						alert("发送短信的次数不能为0！");
						td.find("input").focus();
						return false;
					}
					if(!(/^\d+$/).test(newText)) {
						alert("发送短信的次数只能为整数！");
						td.find("input").focus();
						return false;	
					}
				}
				else{
					alert("填写内容不能为空");
					td.find("input").focus();
					return false;
				}
				td.html("<a href='javascript:void(0);'>"+newText+"</a>");
				$.ajax({
					url : "/heartbeat/updateBeatThreshold",
					type : "post",
					async : false,
					data :{
							appkey : $.trim(appkey),
							hostname : $.trim(hostname),
							threshold : $.trim(newText)
						},
					dataType : "html",
					success : function(data) {
							$("#messsage").text("修改应用配置信息成功！");
							td.bind("click",UpdateThreshold.tdClickEvent);
					}
				});
			});
		};
		
		UpdateTimeout.tdClickEvent = function() {
			var td = $(this);
			var appkey = td.attr("appkey").slice(3);
			var hostname = td.attr("hostname").slice(3);
			var text = $.trim(td.find("a").text());
			td.html("");
			var input = $("<input type='text' style='width: 100px;' />");
			td.append(input);
			td.unbind("click");
			input.focus();
			input.attr("value",text);
			td.find("input").focusout(function() {
				var newText = $.trim(td.find("input").val());
				if(!(newText === "")){
					if(newText == 0) {
						alert("超时时间不能为0！");
						td.find("input").focus();
						return false;
					}
					if(!(/^\d+$/).test(newText)) {
						alert("超时时间只能为整数！");
						td.find("input").focus();
						return false;	
					}
				}
				else{
					alert("超时时间不能为空");
					td.find("input").focus();
					return false;
				}
				td.html("<a href='javascript:void(0);'>"+newText+"</a>");
				$.ajax({
					url : "/heartbeat/updateBeatTimeout",
					type : "post",
					async : false,
					data :{
							appkey : $.trim(appkey),
							hostname : $.trim(hostname),
							timeout : $.trim(newText)
						},
					dataType : "html",
					success : function(data) {
							$("#messsage").text("修改应用配置信息成功！");
							td.bind("click",UpdateTimeout.tdClickEvent);
					}
				});
			});
		};
	}	
)();
