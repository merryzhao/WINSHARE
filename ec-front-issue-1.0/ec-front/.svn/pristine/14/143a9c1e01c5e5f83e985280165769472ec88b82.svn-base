define(function(require){
	var $=require("jQuery"),
		data=require("./media-data");
	function MediaMenu(cfg){
		this.opt={
			menu:"div.music_leftmenu",
			menuItem:">ul>li",
			itemList:"ul.pop",
			hover:"hover"
		};
		$.extend(this.opt,cfg);
		this.init();	
	};
	MediaMenu.prototype={
		init:function(){
			this.menu=$(this.opt.menu);
			this.menuItem=this.menu.find(this.opt.menuItem);
			this.bind();
		},
		bind:function(){
			var obj=this;
			this.menuItem.mouseover(function(e){obj.over(this);e.stopPropagation();});
			this.menu.mouseover(function(e){e.stopPropagation();});
			$(document).mouseover(function(e){obj.hide(e);});
		},
		over:function(el){
			var el=$(el),
				id=el.data("id");
			if(!el.data("showing")){
				this.menuItem.removeClass(this.opt.hover);
				el.addClass(this.opt.hover);
				this.menuItem.find(this.opt.itemList).remove();
				this.menuItem.data("showing",false);				
				this.load(el,data[id]);
				el.data("showing",true);
				this.showing=true;
			}			
		},
		hide:function(e){
			if(this.showing){
				this.menuItem.data("showing",false).
				removeClass(this.opt.hover).
				find(this.opt.itemList).remove();
				this.showing=false;
			}
		},
		load:function(el,item){
			if(item&&item.items&&item.items.length>0){
				var html=["<ul class='pop'>"];
				$.each(item.items,function(){
					html.push("<li><a href='"+this.href+"' title='"+this.title+"'>"+this.name+"</a>");
				});
				html.push("</ul>");
				el.append(html.join(""));
			}
		}
	};
	return MediaMenu;
});
