define(function(require){
	var $ = require("jQuery").sub();
	var ui = require("jQueryUI");
	require("inputTip")($);
	require("star");
	require("widgets-css");
	wxui = require("ui");
	ui($);
	
	var conf = {
			id:-1,
			success: function(){},
			error: function(){}
			};
	
	var view = "<div class='tab_box' id='feedback_box'><h3><a class='close' href='javascript:;' id='feedback_box_close'>close</a><span>订单收货反馈</span></h3><div class='feed_box'><form action='#' method='post' abc='adf'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td colspan='2'>感谢您再文轩网购物!您的反馈将用以改善我们的服务.<br />完成反馈可获得10个积分</td></tr><tr><td width='20%'>请选择收货日期：</td><td width='80%'><input class='date' name='receiveTime' type='text' bind='datepicker'value='' /></td></tr><tr><td>送货速度：</td><td><div id='speedStar'><input type='hidden' name='expressSpeed_id' value=''></div></tr><tr><td>送货员服务态度：</td><td><div id='serveStar'><input type='hidden' name='expressManner_id' value=''></div></tr><tr><td>商品外包装：</td><td><div id='mannerStar'><input type='hidden' name='assess_id' value=''></div></tr><tr><td>其他意见建议：</td><td><textarea cols='40' rows='5' id='complaint' name='description'></textarea></td></tr><tr><td>&nbsp;</td><td><input type='button' id='feedback_submit' value='保存' name='' class='pas_save'></td></tr></table></form></div></div>";
	var msg = "<div class='activate_box'><h3><a href='javascript:;' class='close activate_box_close'>close</a>收货确认成功</h3><div class='coupon_box'><p class='txt_center input_cou'><b class='fb green f14'>恭喜您，收货确认成功！</b><br/><br/>再次感谢您！<br/><br/><input class='pas_save' name='' type='button' value='确定'></p></div></div>";
	var errormsg = "<div class='activate_box'><h3><a href='javascript:;' class='close activate_box_close'>close</a>收货确认失败</h3><div class='coupon_box'><p class='txt_center input_cou'><b class='fb f14'>收货确认操作失败！</b><br/><br/><br/><input class='pas_save' name='' type='button' value='确定'></p></div></div>";
	
	var path = "/customer/order/213/receive";
	var fbview = $(view);
	var msgbox = $(msg);
	var uihl;
	var feedback = {
		init:function(){
			fbview = $(view);
			
			var self = this;
			fbview.css("display", "none");
			fbview.css("height", "auto");
			
			$("body").append(fbview);
			//加载时间插件
			$("input[bind='datepicker']").datepicker({
		        regional: "zh-CN"
		    });
			var date = new Date();
			var month = date.getMonth().toString();
			var day = date.getDay().toString();
			month = month.length == 1 ? '0' + month : month;
			day = day.length == 1 ? '0' + day : day;
			 
			$("input[bind='datepicker']").val(date.getFullYear() + '-' + month + '-' + day);
			
			//加载星星			
			var config = {baseStyle:"baseStyle",checked:"checked", unchecked:"unchecked", count: 5, selectItem:0, tip:function(index){
				var tip = "";
				switch(index){
					case 5: tip = "很满意";break;
					case 4: tip = "基本满意";break;
					case 3: tip = "一般";break;
					case 2: tip = "差";break;
					case 1: tip = "很差";break;
				}
				return tip;
			}};
			$("#speedStar").star($.extend({}, config, {handle:function(index){
				$("#speedStar input[type=hidden]").val(fetchCode(index));
			}}));
			$("#serveStar").star($.extend({}, config, {handle:function(index){
				$("#serveStar input[type=hidden]").val(fetchCode(index));
			}}));
			$("#mannerStar").star($.extend({}, config, {handle:function(index){
				$("#mannerStar input[type=hidden]").val(fetchCode(index));
			}}));
			//加载提示
			$("#complaint").inputTip("最多允许200汉字");
			
			$("#feedback_box_close").click(function(){
				self.close();
			});
			
			//提交表单
			$("#feedback_submit").click(function(){
				var url = $("#feedback_box").find("form").attr("action");
				var isNull = fbview.find("textarea[name=description]").data("isNull");
				var desc = "";
				
				var espeed = fbview.find("input[name=expressSpeed_id]").val();
				if(!espeed){
					alert("请对送货速度进行评分!");
					return false;
				}
				var em = fbview.find("input[name=expressManner_id]").val();
				if(!em){
					alert("请对送货员服务态度进行评分!");
					return false;
				}
				var ass = fbview.find("input[name=assess_id]").val()
				if(!ass){
					alert("请对商品外包装进行评分!");
					return false;
				}
				
				if(!isNull){
					desc = fbview.find("textarea[name=description]").val();
				}
				$.post(url, {
						"format": "json",
						"receiveTime": fbview.find("input[name=receiveTime]").val(), 
						"expressSpeed.id": fbview.find("input[name=expressSpeed_id]").val(),
						"expressManner.id": fbview.find("input[name=expressManner_id]").val(),
						"assess.id": fbview.find("input[name=assess_id]").val(),
						"description": desc
					}, function(e){
						if(e.result == 1){
							self.close();
							self.showSuccess();
							conf.success();
						} else {
							self.close();
							self.showError();
							conf.error();
						}
//						self.close();
//						self.showMsg();
					});
			});
		},
		show : function(config){
			this.init();
			conf = $.extend({}, conf, config);
			var id = conf.id;
			$("#feedback_box").find("h3 span").html("订单 " + id + " 收货确认");
			var url = "/customer/order/" + id + "/receive";
			$("#feedback_box").find("form").attr("action", url);
			$("#feedback_box").show();
			uihl = wxui.highlight({el:$("#feedback_box")});
		}, 
		showSuccess:function(){
			msgbox = $(msg);
			this.showMsg();
		},
		showError: function(){
			msgbox = $(errormsg);
			this.showMsg();
		},
		showMsg : function(){
			var self = this;
			$("body").append(msgbox);
			msgbox.find("input[type=button]").click(function(){
				self.close();
			});
			msgbox.find(".activate_box_close").click(function(){
				self.close();
			})
			uihl = wxui.highlight({el:msgbox});
		},
		close : function(){
			$("#feedback_box").hide();
			fbview.remove();
			msgbox.remove();
			if(uihl){
				uihl.close();
				uihl.remove();
			}
		}
	};
	
	function fetchCode(index){
		switch(index){
		case 1:return 1385;
		case 2:return 1384;
		case 3:return 1383;
		case 4:return 1382;
		case 5:return 1381;
		}
	}
	return feedback;	
});