define(function(require){
	require("widgets-css");
	var $=require("jQuery"),
		conf=require("config");
		data={
		prefix:conf.imgServer+"/images/portrait/pic_head_",
		male:[
		 {label:"",small:"1_s.gif",normal:"1.gif"},
		 {label:"",small:"2_s.gif",normal:"2.gif"},
		 {label:"",small:"3_s.gif",normal:"3.gif"},
		 {label:"",small:"4_s.gif",normal:"4.gif"},
		 {label:"",small:"5_s.gif",normal:"5.gif"},
		 {label:"",small:"6_s.gif",normal:"6.gif"},
		 {label:"",small:"7_s.gif",normal:"7.gif"},
		 {label:"",small:"8_s.gif",normal:"8.gif"},
		 {label:"",small:"9_s.gif",normal:"9.gif"},
		 {label:"",small:"10_s.gif",normal:"10.gif"}
		],
		female:[
		 {label:"",small:"11_s.gif",normal:"11.gif"},
		 {label:"",small:"12_s.gif",normal:"12.gif"},
		 {label:"",small:"13_s.gif",normal:"13.gif"},
		 {label:"",small:"14_s.gif",normal:"14.gif"},
		 {label:"",small:"15_s.gif",normal:"15.gif"},
		 {label:"",small:"16_s.gif",normal:"16.gif"},
		 {label:"",small:"17_s.gif",normal:"17.gif"},
		 {label:"",small:"18_s.gif",normal:"18.gif"},
		 {label:"",small:"19_s.gif",normal:"19.gif"},
		 {label:"",small:"20_s.gif",normal:"20.gif"}
		],
		animal:[
		 {label:"",small:"21_s.gif",normal:"21.gif"},
		 {label:"",small:"22_s.gif",normal:"22.gif"},
		 {label:"",small:"23_s.gif",normal:"23.gif"},
		 {label:"",small:"24_s.gif",normal:"24.gif"},
		 {label:"",small:"25_s.gif",normal:"25.gif"},
		 {label:"",small:"26_s.gif",normal:"26.gif"},
		 {label:"",small:"27_s.gif",normal:"27.gif"},
		 {label:"",small:"28_s.gif",normal:"28.gif"},
		 {label:"",small:"29_s.gif",normal:"29.gif"},
		 {label:"",small:"30_s.gif",normal:"30.gif"}
		],
		personal:[
		 {label:"",small:"31_s.gif",normal:"31.gif"},
		 {label:"",small:"32_s.gif",normal:"32.gif"},
		 {label:"",small:"33_s.gif",normal:"33.gif"},
		 {label:"",small:"34_s.gif",normal:"34.gif"},
		 {label:"",small:"35_s.gif",normal:"35.gif"},
		 {label:"",small:"36_s.gif",normal:"36.gif"},
		 {label:"",small:"37_s.gif",normal:"37.gif"},
		 {label:"",small:"38_s.gif",normal:"38.gif"},
		 {label:"",small:"39_s.gif",normal:"39.gif"},
		 {label:"",small:"40_s.gif",normal:"40.gif"}
		]
	},
	template='<div class="portrait"><div class="selector"><ul class="list-label"><li><label bind="male">酷男</label></li><li><label bind="female">靓女</label></li><li><label bind="animal">可爱动物</label></li><li><label bind="personal">个性</label></li></ul><div class="icon-list"></div></div><div class="preview"><img src=""/><br/><span>我的头像</span></div><div class="button"><button>保存头像</button><a href="javascript:;" bind="close">关闭此窗口</a></div></div>';
	
	function portrait(cfg){
		if(window==this)return new portrait(cfg);
		this.opt={
			action:"",
			dock:null,
			callback:function(){},
			paramName:"avatar"
		};
		$.extend(this.opt,cfg);
		this.init();		
	};
	
	portrait.prototype={
		init:function(){
			this.el=$(template);
			this.head=this.el.find(".list-label");
			this.labels=this.head.find("label");
			this.list=this.el.find(".icon-list");
			this.preview=this.el.find(".preview");
			this.button=this.el.find("button");
			this.closeLink=this.el.find("a[bind='close']");
			this.labels.eq(0).addClass("selected");
			this.render(data.male);
			this.bind();
			this.el.appendTo(document.body);
			this.dock();
			if(this.opt.dock){
				this.value=this.opt.dock.find("img").attr("src");
			}
			this.view();
		},
		bind:function(){
			var obj=this;
			this.labels.mouseover(function(){obj.show($(this));});
			this.button.click(function(){obj.button.attr("disabled","disabled");obj.save()});
			this.closeLink.click(function(){obj.close();})
		},
		show:function(el){
			var type=el.attr("bind"),icons=data[type];
			this.labels.removeClass("selected");
			el.addClass("selected");
			this.render(icons);			
		},
		select:function(el){
			this.value=data.prefix+el.attr("value");
			this.view();
			this.list.find("a").removeClass("selected");
			el.addClass("selected");
		},
		save:function(){
			var obj=this,
				param={
					format:"jsonp"
				};
				param[obj.opt.paramName]=obj.value;
			if(obj.opt.action){
				$.getJSON(obj.opt.action,param,function(data){
					if(data.result=="1"){
						data[obj.opt.paramName]=obj.value;
					
					}else{
						obj.button.removeAttr("disabled");
					}
					obj.opt.callback(data);
				});
			}
			
		},
		close:function(){
			this.el.remove();
		},
		view:function(){
			var img=this.preview.find("img");
				img.attr("src",this.value);
		},
		render:function(icons){
			var html=["<ul class='list-wrap'>"],obj=this;
			$.each(icons,function(){
				html.push("<li><a href='javascript:' value='");
				html.push(this.normal);
				html.push("'><img src='");
				html.push(data.prefix);
				html.push(this.small);
				html.push("'/></a></li>");
			});
			html.push("</ul>");
			this.list.html(html.join(""));
			this.list.find("a").click(function(){obj.select($(this));});
		},
		dock:function(){
			var dock=this.opt.dock,
				offset,left,top;
			if(!!dock){
				this.value=dock.find("img").attr("src");
				offset=dock.offset();
				top=offset.top;
				left=offset.left+dock.width()-this.el.outerWidth();
				this.el.css({top:top,left:left});
			}
		}
	};
	return portrait;
});
