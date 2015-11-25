define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		css=require("cms-css"),
		Model=require("cms-module"),
		banner=require("cms-banner"),
		flushcache=require("cms-flushCache"),
		template={
		tip:"<div class='ui:float:tip'><a href='javascript:;' name='editnow'>编辑当前页面</a>--<a href='javascript:;' name='creatnews'>新闻发布</a></div><div class='ui:banner:tip'><a href='javascript:;' name='editbanner'>banner管理</a></div><div class='ui:flushcache:tip'><a href='javascript:;' title='目前只是刷新首页,音像,图书' name='flushCache'>刷新缓存</a></div>",
		standout:"<div class='ui:standout'><div class='ui:title'><label></label><div><a href='javascript:;' bind='edit'>[编辑]</a></div></div><div class='ui:content'></div></div>",
		editorWindow:'<div class="widget:window"><div class="widget:wrap"><div class="widget:title"><label></label><div><a href="javascript:;" bind="close">关闭</a></div></div><div class="widget:content"></div></div></div>',
			editor:{
				book:'<div class="book-editor"><div class="wrap"><div class="editor-title"><ul><li>图书推荐</li><li>其它设置</li></ul><div class="search-box"><input type="text" name="keyword" value=""/><button>搜索</button></div></div><div class="editor-content"><div class="column"><h4>新书推荐</h4><div class="list" dataBind="provider"><div>Loading...</div></div></div><div class="column"><h4>当前书目</h4><div class="list" dataBind="current"><div>Loading...</div></div></div></div></div><div style="text-align:center;"><button bind="save">保存更改</button></div></div>'
			}
		},
		
		maintainUrl="http://console.winxuan.com/cms",
		
		CMS={
			init:function(){
				this.tip=$(template.tip);
				this.tip.appendTo(document.body);
				//body下移,顶部的片段编辑按钮无法使用
				$("body").css("margin-top","20px");
				this.bind();
				return this;
			},
			bind:function(){
				this.tip.find("a[name='editnow']").click(function(){CMS.convert();});
				$("a").click(this.hold);
				this.tip.find("a[name='creatnews']").click(function(){
					window.location = 'http://console.winxuan.com/news';
				});
				this.tip.find("a[name='editbanner']").click(function(){
					banner.init();
				});
				this.tip.find("a[name='flushCache']").click(function(){
					flushcache.flush();
				});
			
			},			
			hold:function(e){
				var anchor=$(this);
				if(anchor.attr("href")!="javascript:;"&&anchor.attr("href")!="#"){
					CMS.navigate(anchor.attr("href"));
					e.preventDefault();
					e.stopPropagation();
					return false;
				}
			},
			navigate:function(href){
				var param="";
				if(/(http|https|ftp|rtsp):\/\//i.test(href)){
					param=href;
				}else{
					var current=this.getCurrentUrl();
					if(current.lastIndexOf("/")==(current.lenght-1)){
						param=current+href;
					}else{
						if(current.lastIndexOf("/")<=7){
							param=current+"/"+href;
						}else{
							param=current.substring(0,current.lastIndexOf("/")+1)+href;
						}
					}
				}
				window.location=maintainUrl+"?url="+encodeURIComponent(param);
			},
			getCurrentUrl:function(){
				var vars=[],hash,
					hashes=window.location.href.slice(window.location.href.indexOf("?")+1).split("&");
					for(var i=0;i<hashes.length;i++){
						hash=hashes[i].split("=");
						vars.push(hash[0]);
						vars[hash[0]]=hash[1];
					}
				return decodeURIComponent(vars["url"]);
			},
			convert:function(){
				var modules=$("[fragment]");
				if(modules.length>0){
					this.build(modules);
					this.tip.hide();
				}else{
					//alert("TODO 当前页面无模块可供编辑");
					throw new Error("can't found editable module");
				}
			},
			build:function(modules){
				modules.each(function(){
					new Model(this);
				});
			}
		}
	return CMS.init();
});
