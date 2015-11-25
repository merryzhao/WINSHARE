define(function(require){
	var $=require("jQuery"),
		conf=require("config");
		
	function Category(cfg){
		this.opt={
			context:$(document),
			dataProvider:conf.consoleServer+"/category/tree?manage=1&type=1",
			parent:$(document.body),
			load:function(){}
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	
	Category.prototype={
		init:function(){
			this.el=this.opt.context;
			this.input=this.el.find("input[name='category']");
			this.arrow=this.el.find("span.down-arrow");
			this.button=this.el.find("button");
			this.list=$("<div class='treeList'></div>");
			this.scrollTop=0;
			this.scrollLeft=0;
			var offset=this.input.offset();
			this.list.width(this.input.outerWidth());
			this.bind();
			this.load();
		},
		bind:function(){
			var instance=this;
			this.input.click(function(){instance.toggle();}).mouseover(function(){instance.over();}).mouseout(function(){instance.out();});
			this.arrow.click(function(){instance.toggle();});
			this.list.scroll(function(){instance.scroll();});
			this.list.delegate("li label","hover",function(e){$(this).toggleClass("hover");});
			this.list.delegate("li","click",function(e){$(this).removeClass("hover");instance.get($(this));e.preventDefault();e.stopPropagation();});
			this.list.mouseover(function(){instance.over();}).mouseout(function(){instance.out();});
			this.button.click(function(){instance.callback();});
		},
		callback:function(){
			var data={
					code:this.input.attr("code"),
					sort:this.input.attr("sort")
				};
			this.opt.load(data,this.input.val());
		},
		over:function(){
			if(!this.shown){
				this.shown=!this.shown;
				this.list.show();
			}
			this.list.scrollTop(this.scrollTop);
			this.list.scrollLeft(this.scrollLeft);
		},
		out:function(){
			if(this.shown){
				this.shown=!this.shown;
				this.list.hide();	
			}
		},
		get:function(el){
			var id=el.attr("cid"),label=el.find("label").html(),instance=this;
			if(((!el.attr("loaded"))||el.attr("loaded").length==0)&&el.attr("loading")!="loading"){
				el.html("<label>"+label+" loading...</label>");
				el.attr("loading","loading");
				$.ajax({
					url:this.opt.dataProvider,
					type:"post",
					data:{id:id},
					success:function(data){
						try{
							data=eval("("+data+")");
							if(data&&data.length>0){
								var html=["<ul>"];
								$.each(data,function(){
									if(this.checked){
										html.push("<li cid='"+this.id+"'><label>"+this.name+"</label></li>");									
									}
								});
								html.push("</ul>");
								el.html("<label>"+label+"</label>"+html.join(""));
							}else{
								el.html("<label>"+label+"</label>");
							}
							el.attr("loaded","loaded");
							el.remove("loading");
							instance.lock();
						}catch(e){
							throw new Error("NOT LOGGED IN");							
						}
					},
					error:function(xhr,status,e){
						throw new Error(e);
					}
				});
			}else{
				el.toggleClass("packup");
			}
			this.lock();
			this.input.attr("code",this.getValue(el));
			this.input.data("categoryId",id);
			this.input.val(label);
		},
		lock:function(){
			this.list.height("auto");
			var height=this.list.height(),
				offset=this.list.position(),
				parentHeight=this.opt.parent.height();
			if(height>(parentHeight-offset.top-10)){
				this.list.height(parentHeight-offset.top-10);
				return;
			}
		},
		load:function(){
			var instance=this;
			$.ajax({
				url:this.opt.dataProvider,
				type:"POST",
				success:function(data){
					data=eval("("+data+")");
					var html=["<ul>"];
					$.each(data,function(){
						if(this.checked){
							html.push('<li cid="'+this.id+'"><label>'+this.name+'</label></li>');							
						}
					});
					html.push("</ul>");
					instance.list.html(html.join(""));
					instance.list.appendTo(instance.opt.parent);
				},
				error:function(xhr,status,e){
					throw new Error(e);
				}
			});
		},
		getValue:function(el,value){
			value=value||[];
			if(!!el.attr("cid")){
				value.push(el.attr("cid"));				
			}
			var parents=el.parents().filter("li[cid]");
				parents.each(function(){
					value.push($(this).attr("cid"));
				});
			return value.reverse().join(".");
		},
		toggle:function(){
			this.shown=!this.shown;
			this.list.show();
		},
		scroll:function(){
			this.scrollTop=this.list.scrollTop();
			this.scrollLeft=this.list.scrollLeft();
		}
	};
	return Category;
});
