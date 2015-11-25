define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		Editor=require("cms-editor");
		template={
			standout:"<div class='ui:standout'><div class='ui:title'><label></label><div><a href='javascript:;' bind='edit'>[编辑]</a></div></div><div class='ui:content'></div></div>"
		};
	
	function StandOut(cfg) {
		this.opt= {
			el:null,
			title:"Title",
			edit:function(){}
		};
		$.extend(this.opt,cfg);
		this.init();
	};

	StandOut.prototype= {
		init: function() {
			if(this.opt.el) {
				this.target=this.opt.el;
				this.el=$(template.standout);
				this.loaded=false;
				this.title=this.el.find("div.ui\\:title label");
				this.edit=this.el.find("div.ui\\:title a[bind='edit']");
				this.content=this.el.find("div.ui\\:content");
				this.title.html(this.opt.title);
				this.bind();
			} else {
				throw new Error("standout el is required!");
			}
		},
		bind: function() {
			var obj=this;
			this.target.mouseover( function(){obj.show();}).mouseout(function(){obj.hide();});
			this.el.mouseover(function(){obj.show();}).mouseout(function(){obj.hide();});
			this.edit.click(function(){obj.opt.edit();});
			this.content.click(function(){obj.opt.edit();});
		},
		show: function() {
			if (!this.loaded) {
				this.el.appendTo(document.body);
				this.loaded=true;
			}
			if(!this.showing){
				this.showing=true;
				this.target.addClass("standout");
				this.el.show();
				this.locate();
			}
		},
		locate: function() {
			var el=this.target,
			offset=el.offset(),
			titleHeight=this.title.parent().height();
			//临时解决我的文轩热门活动的维护问题，可以考虑更好的的解决方案
			if(!offset.top&&!offset.left){
			    offset=el.parents(".master-user-box").offset();
			    offset.top+=150;
			    offset.left-=165;
			}
			//end
			this.content.css({
				height:el.height(),
				width:el.width()
			});
			this.el.css({
				top:offset.top-titleHeight,
				left:offset.left
			});
		},
		hide: function() {
			if(this.showing){
				this.showing=false;
				if(this.status!="editing"){
					this.el.hide();
					this.target.removeClass("standout");				
				}
			}
		},
		remove:function(){
			this.el.remove();
		}
	};
	
	function Module(el){
		this.el=$(el);
		this.id=this.el.attr("fragment");
		this.cachekey = this.el.attr("cachekey");
		this.init();
	}
	Module.prototype={
		init:function(){
			if(this.el.length>0){
				var module=this;
				this.outline=new StandOut({
					el:this.el,
					title:"",
					edit:function(){module.edit(this);}
				});
			}else{
				throw new Error("can't init module when module id is null");
			}
		},
		edit:function(out){
			var instance=this;
			if(!this.editing){
				this.frame=Editor.build({
					dataUrl:conf.portalServer+"/fragment/"+this.id+"?format=jsonp&callback=?",
					save:function(data){
						if(data.attr("fragment")!=instance.id){
							var el=data.find("[fragment='"+instance.id+"']");
							if(el.length>0){
								instance.el.html(data.find("[fragment='"+instance.id+"']").html());								
							}else{
								instance.el.html(data.filter("[fragment='"+instance.id+"']").html());
							}
						}else{
							instance.el.html(data.html());
						}
						instance.el.trigger("refresh");
					},
					module:instance
				});
			}
		}
	};
	return Module;
});
