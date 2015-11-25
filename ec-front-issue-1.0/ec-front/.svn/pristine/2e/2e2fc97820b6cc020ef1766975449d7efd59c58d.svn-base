define(function(require){
	var $=require("jQuery");
	
	function Slider(cfg){
		if(window==this)return new Slider(cfg);
		var opt={
			index:0,
			selector:{
				container:"div.shopads_box",
				pages:"ul.rotation2 li",
				ads:"ul.shop_ads",
				sort:"div.ads_sort",
				dl:"div.ads_sort dl",
				nav:"div.ads_sort dd",
				leftArrow:"a.left_arrow",
				rightArrow:"a.right_arrow"
			},
			className:{
				pageSelected:"current_ad",
				sortSelected:"now_theme"
			}
		};
		$.extend(this,opt,cfg);
		this.el=$(this.selector.container);
		if(!this.container){
			this.container=this.el.parent();			
		}
		this.init();
	};
	Slider.prototype={
		init:function(){
			this.pages=this.el.find(this.selector.pages);
			this.ads=this.el.find(this.selector.ads);
			this.height=this.ads.find("li").outerHeight();
			this.sort=this.container.find(this.selector.sort);
			this.navContainer=this.container.find(this.selector.dl)
			this.nav=this.container.find(this.selector.nav);
			this.navWidth=this.nav.innerWidth();
			this.navContainer.css({width:this.navWidth*this.nav.length});
			this.leftArrow=this.container.find(this.selector.leftArrow);
			this.rightArrow=this.container.find(this.selector.rightArrow);
			this.initList();
			this.bind();
			this.rotate();	
		},
		initList:function(){
			var list=this.ads.find("li");
			this.size=list.length;
			list.each(function(i){
				var el=$(this);
				el.attr("index",i);
			});
			if(this.nav.length>0){
				this.nav.each(function(i){
					$(this).attr("index",i);
				});					
			}
		},
		bind:function(){
			var slider=this;
			this.container.delegate(this.selector.leftArrow,"click",function(){slider.prev();});
			this.container.delegate(this.selector.rightArrow,"click",function(){slider.next();});
			this.container.delegate(this.selector.pages,"click",function(){slider.pageGo(this)});
			this.container.delegate(this.selector.nav,"mouseover",function(){slider.navGo(this);});
			this.container.delegate(this.selector.nav,"click",function(){slider.navGo(this);});
			this.container.mouseover(function(){slider.isHover=true;}).mouseout(function(){slider.isHover=false;});
			this.container.delegate(this.selector.sort,"mouseover",function(){slider.isHover=true;});
			this.container.delegate(this.selector.sort,"mouseout",function(){slider.isHover=false;});
			$(window).focus(function(){slider.focus();}).blur(function(){slider.blur();});
		},
		prev:function(){
			this.refresh(-1);
		},
		next:function(){
			this.refresh(+1);
		},
		pageGo:function(el){
			var index=this.pages.index(el);
			if(this.index!=index&&!this.locking){
				this.refresh(0,index);					
			}
		},
		navGo:function(el){
			var index=parseInt($(el).attr("index"));
			if(this.index!=index&&!this.locking){
				this.refresh(0,index);
			}
		},
		getIndex:function(op){
			if(this.index==0){
				if(op==-1){
					this.index=this.size-1;						
				}else if(op==+1){
					this.index++;
				}
			}else if(this.index==this.size-1){
				if(op==+1){
					this.index=0;
				}else if(op==-1){
					this.index--;	
				}
			}else{
				this.index=this.index+op;
			}
			return this.index;
		},
		refresh:function(op,idx){
			var index;
			if(typeof(idx)!="undefined"&&op==0){
				index=this.index=idx;
			}else{
				index=this.getIndex(op);
			}
			this.animate(index);
			this.changePage();
			this.changeNav();
		},
		animate:function(index){
			var slider=this;
			if(!isNaN(index)){
				this.container.find(this.selector.ads).
				animate({
					marginTop:-(this.height*index)
				},400,function(){
					slider.running=false;
				});					
			}
		},
		changePage:function(){
			var cn=this.className.pageSelected;
			this.container.find(this.selector.pages).removeClass(cn).eq(this.index).addClass(cn);
		},
		changeNav:function(){
			var slider=this,navContainer,nav;
			if(this.navContainer.length>0){
				var cn=this.className.sortSelected;
				nav=this.container.find(this.selector.nav);
				nav.removeClass(cn).eq(this.index).addClass(cn);
				page=parseInt((this.index+1)/3);
				if((this.index+1)%3>0){
					page=page+1;						
				}
				this.locking=true;
				navContainer=this.container.find(this.selector.dl);
				navContainer.css({width:this.navWidth*nav.length}).
				animate({
					marginLeft:-(this.navWidth*3*(page-1))
				},400,function(){
					slider.locking=false;
				});	
			}			
		},
		rotate:function(){
			var slider=this;
			if(!slider.timer){
				slider.timer=setInterval(function(){if((!slider.isHover)&&(!slider.locking)){slider.next();}},3000);				
			}
		},
		focus:function(){
			var slider=this;
			if(!slider.timer){
				slider.timer=setInterval(function(){if((!slider.isHover)&&(!slider.locking)){slider.next();}},3000);				
			}
		},
		blur:function(){
			if(this.timer){
				clearInterval(this.timer);
				this.timer=null;
			}
		}
	};
	return Slider;
});
