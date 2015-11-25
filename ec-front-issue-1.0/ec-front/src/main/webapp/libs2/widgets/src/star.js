/**
 * 星星评分
 * 使用方式:
 * seajs.use(["jQuery", "star"], function($){
 *		$("#star1").star({baseStyle:"baseStyle",checked:"checked", unchecked:"unchecked", handle:function(index){
 *			$("#hidden").val(index);
 *		}});
 *	});
 */
define(function(require){
	var $ = require("jQuery");
	(function($){
		
		var conf = {
			icon: "",//图标
			count: 5,//数量
			baseStyle: "",//基本的样式信息
			checked:"",//选中后的样式
			unchecked: "",//未选中的样式
			handle: function(index){},//选中后的回调函数,如将选中的索引值添加到页面hidden中
			tip: function(index){},//提示的处理回调, 当鼠标移动到一个星星上时会调用
			selectItem: 0//默认选中第几个星星
		};
		
		function star(config){
			var self = this;
			$.extend(this, {
				init: function(){
					var item = $("<div class='winxuanStar'></div>");
					var items = "";
					for(var i = 0; i < config.count; i++){
						items += "<i data-index='"+(i + 1)+"'></i>";
					}
					items = $(items);
					items.addClass(config.baseStyle);
					
					var tipspan = $("<span>asdf</span>");
					
					config.html = {};
					config.html.item = item;
					config.html.items = items;
					config.html.tipspan = tipspan;
					
					item.append(items);
					item.append(tipspan);
					
					config.item.append(item);
					self.bind();
					if(config.selectItem != 0){
						config.handle(config.selectItem);
					}
				},
				bind:function(){
					config.html.item.mouseout(self.moveout)//.live("mouseout", this.moveout);
					config.html.items.mouseover(self.movein);//.live("mouseover", this.movein);
					config.html.items.click(self.click);//.live("click", this.click);
					self.show(config.selectItem);
				},
				movein: function(){
					var index = $(this).data("index");
					self.show(index);
				},
				moveout: function(){
					self.show(config.selectItem);
				},
				click: function(){
					config.selectItem = $(this).data("index");
					config.handle(config.selectItem);
				},
				show: function(selectItem){
					config.html.item.children().filter("i").addClass(config.unchecked);
					var i = config.html.items.slice(0, selectItem);
					i.removeClass(config.unchecked);
					i.addClass(config.checked);
					config.html.tipspan.html(config.tip(selectItem));
				}
			});
			self.init();
		}
		$.fn.star = function(config){
			config = $.extend(true, {item: $(this)}, conf, config);
			return new star(config);
		};
	})($);
});
