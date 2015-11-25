define(function(require){
	var $=require("jQuery"),
		ui=require("ui"),
		config=require("config"),
		cart=require("cart"),
		base=config.portalServer+"/customer/notify",
		notify={
			template:{
				"461003":"<div class='success'>操作成功!</div><ol><li>该商品到货后，我们会第一时间邮件通知您！</li><li>您可以进入“我的文轩-><a href='"+config.portalServer+"/customer/notify/arrival?hasStock=true' target='arrivalNotify'>到货通知</a>”查看是否到货；</li></ol>",
				"461004":"<div class='success'>操作成功!</div><ol><li>该商品降价后，我们会第一时间邮件通知您！</li><li>您可以进入“我的文轩-><a href='"+config.portalServer+"/customer/notify/priceReduce?reduced=true' target='arrivalNotify'>降价通知</a>”查看是否到货；</li></ol>"
			},
			REMOVE_EVENT:"remove_event",
			UPDATE_EVENT:"update_event",
			ADD_EVENT:"add_event",
			LOGIN_EVENT:"login_event",
			remove:function(pids,el,type){
				var pidsString = "p=" + pids.join("&p=");
				ui.confirm({
					message:"确定删除?",
					dock:el,
					callback:function(){
						$.ajax({
						   type: "POST",
						   url: base,
						   data: pidsString + "&type=" + type + "&_method=delete&format=json",
						   success: function(data){
						     $(notify).trigger(notify.REMOVE_EVENT,[data]);
						   }
						}); 
					}
				});
			},
			update:function(pid){
				$.ajax({
				   type: "POST",
				   url: base,
				   data: "p=" + pid + "&_method=put&format=json",
				   success: function(data){
				     $(notify).trigger(notify.UPDATE_EVENT,[data]);
				   }
				}); 
			},
			add:function(pid,type,el){
				var url=[base+"/add?p="+pid];
					url.push("format=jsonp");
					url.push("callback=?");
					url.push("type="+type);
				$.jsonp({
					url:url.join("&"),
					success:function(data){
						$(notify).trigger(notify.ADD_EVENT,[data,type,el]);
					},
					error:function(xhr,status){
						$(notify).trigger(notify.LOGIN_EVENT,[type,el]);
					}
				});
			}
		};
	return notify;
});
