define(function(require){
	var $=require("jQuery");
	function score(config){
		if(window==this){
			return new score(config);
		};
		this.cfg=$.extend({
			defaultSend:1,
			score:"div #do_score", //默认选择器
			defValue:5, //默认值
			action:"http://www.winxuan.com/someActionInHere?callback=?", // 保存值时的url
			paramName:"score",
			callback:function(){}, // 回调参数 
			showTip:function(html){
				$("label[bind='scoring']").html(html);
			},
			msg:{
				"1":"很差",
				"2":"较差",
				"3":"还行",
				"4":"推荐",
				"5":"力荐"
			}
		},config);
		this.init();
	};
	score.UPDATE_EVENT="update_event";
	score.LOGIN_EVENT="login_event";
	score.prototype = {
		init: function(){
			this.el = $(this.cfg.score);
            this.defaultSend = this.el.attr("param");
			this.defClass = this.el.attr("class");
			this.value = this.cfg.defValue;
			this.callback = this.cfg.callback;
			this.showTip = this.cfg.showTip;
			this.el.addClass("selected" + this.value);
			this.el.find("b").each(function(i){
				this.value = i + 1;
			});
			this.bind();
		},
		bind: function(){
			var obj = this;
			this.el.mouseout(function(){
				var el = $(this);
				el.attr("class", obj.defClass);
				el.addClass("selected" + obj.value);
			});
			this.el.find("b").hover(function(){
				obj.mouseover(this);
			}, function(){
				obj.mouseout(this);
			}).click(function(){
				obj.click(this,obj.defaultSend);
			});
		},
		disabled: function(){
			this.el.unbind();
			this.el.find("b").unbind();
		},
		doAction: function(val){
			var obj = this, data = {};
			data[obj.cfg.paramName] = val;
			$.ajax({
				url: obj.cfg.action,
				data: data,
				success: function(data){
					obj.disabled();
					obj.callback(data);
					$(obj).trigger(score.UPDATE_EVENT, [data]);
				},
				error: function(xhr, status){
					if (xhr.status == "401") {
						$(obj).trigger(score.LOGIN_EVENT, []);
					}
				},
				dataType: "json"
			});
			this.showTip(this.cfg.msg[val]);
		},
		click: function(b,param){
			var val = $(b).attr("value");
			if (this.value != val) {
				this.value = val;
				this.el.attr("class", this.defClass);
				this.el.addClass("selected" + val);
			}
			
			if (parseInt(param)==1) {
				this.doAction(val);
			}else{
				$("input[name=rank]").attr("value",val);	
			}
		},
		mouseover: function(b){
			var val = $(b).attr("value");
			if (this.value != val) {
				this.el.attr("class", this.defClass);
				this.el.addClass("selected" + val);
			}
			this.showTip(this.cfg.msg[val]);
		},
		mouseout: function(b){
			var val = $(b).attr("value");
			if (this.value != val) {
				this.el.removeClass("selected" + val);
			}
			this.showTip("");
		},
		isuseful: function(id, c){
			var obj = this, data = {
				'isUseful': true
			};
			$.getJSON('http://www.winxuan.com/product/comment/' + id + '/edit?format=json', data, function(data){
				$(c).unbind();
				$(c).find(".useful").html(data.useful);
			});
		},
		isuseless: function(id, c){
			var obj = this, data = {
				'isUseful': false
			};
			$.getJSON('http://www.winxuan.com/product/comment/' + id + '/edit?format=json', data, function(data){
				$(c).unbind();
				$(c).find(".useless").html(data.useless);
			});
		}
	};
	return score;
});
