define(function(require){
	var $=require("jQuery"),
		data=require("http://www.winxuan.com/category/navdata?20120414");
	function Menu(cfg){
		this.opt={
			menu:"div.interaction-menu",
			menuList:".master-menu-pop",
			menuItem:".item:not(:last)",
			menuTitle:"h2",
			itemHoverClass:"over",
			content:"div.item-pop"
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	
	Menu.prototype={
		init:function(){
			this.menu=$(this.opt.menu);
			this.menuList=this.menu.find(this.opt.menuList);
			this.menuItem=this.menu.find(this.opt.menuItem);
			this.menuTitle=this.menu.find(this.opt.menuTitle);
			this.content=this.menu.find(this.opt.content);
			this.expandable=this.menuList.data("expandable");
			this.bind();
			this.parse();
		},
		bind:function(){
			var obj=this;
			this.menuTitle.mouseover(function(e){obj.list();});
			this.menuItem.mouseover(function(e){obj.show(this,e);});
			this.menu.mouseover(function(e){e.stopPropagation();});
			$(document).mouseover(function(e){obj.hide(e);});
		},
		show:function(el,e){
			if((!this.showing)||this.currentItemEl!=el){
				this.currentItemEl=el;
				this.showing=true;
				var obj=this;
				obj.timer=setTimeout(function(){
					if(obj.showing){
						obj.menuItem.removeClass(obj.opt.itemHoverClass);
						$(obj.currentItemEl).addClass(obj.opt.itemHoverClass);
						obj.content.html(obj.contentMap[$(obj.currentItemEl).data("index")]);
						obj.locate(obj.currentItemEl);
					}
				},100);
			}
		},
		locate:function(el){
			var el=$(el),position=el.position();
			if(position&&position.top){
				this.content.css({top:position.top+39}); 				
			}
			this.content.show();
		},
		hide:function(e){
			if(this.showing){
				if(this.expandable){
					this.menuList.hide();
					this.content.hide();
				}else{
					this.content.hide();
				}
				this.menuItem.removeClass(this.opt.itemHoverClass);
				this.showing=false;
				this.currentItemEl=null;
			}
		},
		list:function(e){
			if(this.expandable){
				this.menuList.show();
			}
			this.showing=true;
		},
		log:function(e){
			if(typeof(console)!="undefined"&&console.log){
				console.log("tagName:"+e.target.tagName+" type"+e.type+" status:"+this.showing);
			}
		},
		parse:function(){
			var obj=this;
				obj.contentMap={};
			$.each(data,function(i){				
				obj.contentMap[i]=obj.toHtml(this);
			});
		},
		toHtml:function(data){
			var html=[];
			$.each(data,function(j){
				html.push('<div class="base-nav cf">');if(this.items&&this.items.length>0){
					$.each(this.items,function(i){
						if(i>3){
							if(this.href!="javascript:;"){
							if (this.target) {
								html.push("<a href='" + this.href + "' title='" + this.title + "' target='" + this.target + "'>" + this.title + "</a>");
							}else {
								html.push("<a href='" + this.href + "' title='" + this.title + "'>" + this.name + "</a>");
							}					
						  }
						}
						
					});
				}
				html.push('</div>');
			});
			return html.join("");
		}
	};
	return Menu;
});
