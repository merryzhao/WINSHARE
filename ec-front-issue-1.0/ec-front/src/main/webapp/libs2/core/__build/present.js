define("http://static.winxuancdn.com/libs/core/present.js",["jQuery","config"],function(a){var b=a("jQuery"),c=a("config"),d={ACTIVE_SUCCESS_EVENT:"success",ACTIVE_ERROR_EVENT:"error",msgMap:{0:"输入的激活码不存在，请确认后重新输入",1:"激活成功",2:"服务器内部错误",3:"警告"},active:function(a){var e=[c.portalServer+"/customer/present/active?format=jsonp"];e.push("callback=?"),e.push("code="+a),b.ajax({url:e.join("&"),success:function(a){a.result=="1"?b(d).trigger(d.ACTIVE_SUCCESS_EVENT,[a]):b(d).trigger(d.ACTIVE_ERROR_EVENT,[d.msgMap[a.result]])},error:function(a,c){b(d).trigger(d.ACTIVE_ERROR_EVENT,[c])},dataType:"jsonp"})}};return d});