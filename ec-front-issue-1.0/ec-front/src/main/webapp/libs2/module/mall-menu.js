define(function(require){
	var $=require("jQuery"),
		data=require("http://www.winxuan.com/category/mall/navdata?20120414");
		//data=require("./mall-data");
	function MallMenu(cfg){
		this.opt={
			menu:"dl.goods_leftmenu",
			menuItem:"dd",
			itemList:"div.subitem",
			hover:"hover"
		};
		$.extend(this.opt,cfg);
		this.init();	
	};
	MallMenu.prototype={
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
			var obj = this;
			if(item&&item.items&&item.items.length>0){
				var html=["<div class='subitem'>"];
				$.each(item.items,function(){
					html.push("<dl>");
					html.push("<dt>");
					html.push("<a  href='"+this.href+"' title='"+this.title+"'>"+this.name+"</a>")
					html.push("</dt>");
					html.push("<dd>");
					var child = $(this.items);
					if(child&&child.length>0){
						$.each(child,function(){
							html.push("<em>");
							html.push("<a  href='"+this.href+"' title='"+this.title+"'>"+this.name+"</a>");
						html.push("</em>");
						});
					}
					html.push("</dd>");
					html.push("</dl>");
				});
				html.push("</div>");
				el.append(html.join(""));
			}
		}
	};
	return MallMenu;
});
