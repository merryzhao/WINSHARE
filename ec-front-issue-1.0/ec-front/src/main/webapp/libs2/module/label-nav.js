define(function(require){
	var $=require("jQuery"),
		mainNav={
			selector:{
				head:"div.header_redbg",
				nav:"#head-nav",
				items:"li",
				selected:"li.nav_on",
				services:"#spe-services",
				leftMenu:"div.left_menu",
				menuTitle:"h3",
				menuList:"dl"
			},
			cfg:{
				hoverClass:"hover",
				menuTitleClass:"unfold"
			},
			init:function(){
				this.el=$(this.selector.nav);
				this.head=$(this.selector.head);
				this.selected=this.el.find(this.selector.selected);
				//this.category=this.head.find("a.category_menu");
				//this.leftMenu=this.head.find(this.selector.leftMenu);
				//this.menuTitle=this.leftMenu.find(this.selector.menuTitle);
				//this.menuList=this.leftMenu.find(this.selector.menuList);
				//this.menu=this.head.find("ul.left_menu");
				this.bind();
			},
			bind:function(){
				var items=this.el.find(this.selector.items);
				items.hover(function(){mainNav.handleIn(this)},function(){mainNav.handleOut(this)});
				/*
				if(this.category.attr("expandable")){
						this.category.hover(function(){mainNav.expand();},function(){mainNav.packup();});	
						this.menu.hover(function(){mainNav.expand();},function(){mainNav.packup();});
				}
				*/
				/*
if(this.menuList.length>0&&this.menuList[0].style.display=="none"){
					this.menuTitle.hover(mainNav.expand,mainNav.packup);
					this.menuList.hover(mainNav.expand,mainNav.packup);
				}
*/
			}
			/*
expand:function(){
				mainNav.menuTitle.find("a").addClass(mainNav.cfg.menuTitleClass);
				mainNav.menuList.show();
			},
			packup:function(){
				mainNav.menuTitle.find("a").removeClass(mainNav.cfg.menuTitleClass);
				mainNav.menuList.hide();
			},
			handleIn:function(li){
				var el=$(li);
				if(!el.hasClass(this.cfg.hoverClass)){
					el.attr("hover","1");
					el.addClass(this.cfg.hoverClass);
				}
				// TODO show content of context
			},
			handleOut:function(li){
				var el=$(li);
				if(el.attr("hover")=="1"){
					$(li).removeClass(this.cfg.hoverClass);					
				}
				// TODO show content of context
			}
*/
		};
	return mainNav;
});
