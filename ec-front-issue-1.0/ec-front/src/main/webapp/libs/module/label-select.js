/*
 * 会员中心TAB选择
 * TO：我的社区
 * */

define(function(require){
	var $ = require("jQuery"),
	conf = require("config"),
	labelSelector={
		selector:{
			rightBox:".right_box",
			tabs:".infor_tab",
			items:"li",
			content:".content",
			pageNav:".fav_pages"
		},
		attribute:{
			url:"url"
		},
		init:function(){
			this.el = $(this.selector.rightBox);
			this.tabs = $(this.selector.tabs);
			this.content = this.el.find(this.selector.content).html();
			this.bind();
		},
		bind:function(){
			var items = this.tabs.find(this.selector.items);
			items.each(function(){
				var el = $(this);
				el.bind("click",function(){
					labelSelector.changeTabSelect(items,el);
					labelSelector.activeTab(el);
				});
			});
		},
		changeTabSelect:function(items,el){
			items.each(function(){
				var other = $(this);
				if(other.html() != el.html()){
					other.removeClass("current_info");
				}
			});
			el.addClass("current_info");
		},
		activeTab:function(el){
			var url = el.attr(this.attribute.url);
			var content = this.el.find(this.selector.content);
			if(url != undefined){
				$.get(url,{}, function(data){
					content.html(data);
			  	}); 
			}else{
				content.html(this.content);
			}
		}
	}

	return labelSelector;

});