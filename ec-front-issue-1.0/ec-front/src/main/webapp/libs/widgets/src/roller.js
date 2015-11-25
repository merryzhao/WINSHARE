define(function(require){
	var $=require("jQuery");
	
	function Roller(cfg){
		if(window==this)return new Roller(cfg); 
		this.opt={
			context:$(document), // 默认上下文元素
			itemSize:4, // 默认显示元素个数
			page:2, // 默认分页数
			paging:false, //是否有分页标签关联
			auto:false,
			selector:{
				container:"", // 整个组件的容器
				box:"", // 遮罩层元素
				items:"", // 包裹整个列素元素的容器,被遮罩层元素
				prev:"", // 上一页元素
				next:"" // 下一页元素
			}
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	
	Roller.prototype={
		init:function(){
			if(!this.opt.context){
				throw new Error("Can't found roller's context ");
			}
			this.el=this.opt.context.find(this.opt.selector.container);
			this.box=this.el.find(this.opt.selector.box);
			this.items=this.box.find(this.opt.selector.item);
			if(this.opt.paging){
				this.page=this.opt.context.find(this.opt.selector.page);
			}
			this.nextEl=this.el.find(this.opt.selector.next);
			this.prevEl=this.el.find(this.opt.selector.prev);
			this.items=this.box.find(this.opt.selector.items);
			this.itemWidth=this.items.innerWidth();
			this.box.css({width:this.itemWidth*this.items.length});
			this.pageIndex=1;
			this.bind();
			this.auto();
		},
		bind:function(){
			var obj=this;
			this.el.delegate(this.opt.selector.next,"click",function(){obj.next();});
			this.el.delegate(this.opt.selector.prev,"click",function(){obj.prev();});
			if(this.opt.paging){
				/*
this.opt.context.delegate(this.opt.selector.page,"mouseover",function(){
					var el=$(this),cn=obj.opt.className.pageSelected;
					obj.page.removeClass(cn);
					el.addClass(cn);
					obj.pageGo(el.text());
					obj.isHover=true;
				}).
				delegate(this.opt.selector.page,"mouseout",function(){
						obj.isHover=false;
				});
*/
			}
			this.box.hover(function(){
				obj.isHover=true;
			},function(){
				obj.isHover=false;
			});
			this.opt.context.bind("refresh",function(){
				obj.reload();
			});
		},
		reload:function(){
			this.box=this.el.find(this.opt.selector.box);
		},
		refresh:function(){
			var obj=this;
			if(!obj.locking){
				obj.locking=true;
				obj.box.animate(
					{marginLeft:-(obj.pageIndex-1)*obj.itemWidth*obj.opt.itemSize},
					500,
					function(){
						obj.locking=false;
						if(obj.opt.paging){
							var el=obj.page.filter(':contains('+obj.pageIndex+')'),
								cn=obj.opt.className.pageSelected;
							obj.page.removeClass(cn);
							el.addClass(cn);
						}
						if(!obj.box.data("loaded")){
							var images=obj.box.find("img[data-original][loaded!=true]");
							if(images.length>0){
								images.trigger("scroll");
								images.trigger("imgload");
							}else{
								obj.box.data("loaded",true);
							}							
						}
					}
				);	
			}
		},
		next:function(){
			this.index(+1);
			this.refresh();
		},
		prev:function(){
			this.index(-1);
			this.refresh();
		},
		pageGo:function(page){
			this.index(page)
			this.refresh();
		},
		index:function(op){
			if(this.locking){
				return;
			}
			if(op==+1){
				if(this.pageIndex>=this.opt.page){
					this.pageIndex=1;
				}else{
					this.pageIndex++;
				}
			}else if(op==-1){
				if(this.pageIndex<=1){
					this.pageIndex=this.opt.page;
				}else{
					this.pageIndex--;
				}
			}else{
				this.pageIndex=parseInt(op);
			}
		},
		auto:function(){
			var obj=this;
			if(this.opt.auto){
				setInterval(function(){if(!obj.isHover)obj.next();},10000);				
			}
		}
	};
	return Roller;
});
