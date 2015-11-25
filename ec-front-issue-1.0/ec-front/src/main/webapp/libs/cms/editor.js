define(function(require){
	var $=require("jQuery").sub(),
		conf=require("config"),
		Category=require("category"),
		Assister=require("searchAssister"),
		template={
			frame:'<div class="widget:window"><div class="widget:wrap"><div class="widget:title"><label></label><div><a href="javascript:;" bind="close">关闭</a></div></div><div class="widget:content"><p class="pre-loading">loading...</p></div></div></div>',
			product:'<div class="ed:product"><div class="ed:menu"><div class="left"><label>请选择数据来源</label><select name="ruleType"><option value="1">自选商品</option><option value="2">销量排序</option><option value="3">上架时间</option><option value="4">限时抢购</option><option value="5">未开始的抢购</option></select></div><div class="right"><ul><li class="search item"><input name="keyword" type="text"/><button>搜索</button></li><li class="category item"><label>分类</label><input type="text" name="category" rule="2" readonly="readonly" value="" sort="7"/><span class="down-arrow"></span><button>自动更新</button></li><li class="category item"><label>分类</label><input type="text" name="category" rule="3" readonly="readonly" value="" sort="12"/><span class="down-arrow"></span><button>自动更新</button></li></ul></div><a class="lArrow" href="javascript:;" title="左">&lt;</a><a class="rArrow" href="javascript:;" title="右">&gt;</a></div><div class="dataList"><div class="column" databind="optional"></div><div class="column" databind="current"></div><div class="button"><button bind="save">保存当前商品</button></div></div></div>',
			item:'<tr><td class="img"><a href="{url}" target="_blank"><img src="{imageUrl}"/></a></td><td class="detail"><ul><li class="title"><a href="{url}" target="_blank">{name}</a></li><li><span title="销量">月:{monthSale} 周:{weekSale}</span><span title="库存">&nbsp{avalibleQuantity}</span><span class="dateTime" title="上架时间:{lastOnShelfTime}">{lastOnShelfTime}</span></li></ul></td><td class="other">{action}</td></tr>',
			link:'<div class="ed:link"><table><caption>当前链接列表</caption><thead><tr><th class="name">名称</th><th class="title">标题</th><th class="image">图片地址</th><th class="href">链接地址</th><th class="desc">描述</th><th class="action">操作</th></tr></thead><tbody><tr><td colspan="5"><div class="nocontent">暂无数据</div></td></tr></tbody></table><form name="linkForm" action="javascript:;"><input type="hidden" name="id" value=""/><fieldset><legend>编辑链接</legend><p><label>链接名称</label><input type="text" name="name" value="" class="short"/></p><p><label>链接标题</label><input type="text" name="title" value="" class="short"/></p><p><label>链接地址</label><input type="text" name="href" value="" class="long"/></p><p><label>图片地址</label><input type="text" name="src" value="" class="long"/></p><p><label>描述</label><input type="text" name="desc" value="" class="long"/></p><div><button>保存</button></div><a href="javascript:;">+添加新的链接</a></fieldset></form><div class="button"><button>保存链接列表</button></div></div>',
			linkItem:'<tr param="{id}"><td><div class="name">{name}</div></td><td><div class="title">{title}</div></td><td><div class="image">{image}</div></td><td><div class="href">{href}</div></td><td><div class="desc">{desc}</div></td><td class="action"><a href="javascript:;" param="{id}" class="delete">删除</a><a href="javascript:;" param="{id}" class="edit">编辑</a><a href="javascript:;" param="{id}" class="topmost">置顶</a></td></tr>',
			text:'<div class="ed:text"><textarea name="content"></textarea><div class="button"><button>保存当前文本</button></div></div>'
		},
		provider=conf.searchServer+"/search.jsonp?callback=?",
		refUrl=(function(name) {var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");var r = window.location.search.substr(1).match(reg);if(r!=null){return decodeURIComponent(r[2]); }return null;})("url");
		require("jQuery-drag")($);
		require("wysiwyg")($);
	
	
	Editor={
		type:{
			"1":"Product",
			"2":"News",
			"3":"Link",
			"4":"Text",
			"5":"Product"
		}
	};
	
	Editor.build=function(cfg){
		return new Frame(cfg);
	};
	
	Editor.Product=function(data,frame){
		this.data=data;
		this.frame=frame;
		this.pageSize=5;
		this.init();
	};
	Editor.Product.prototype={
		init:function(){
			this.el=$(template.product);
			this.select=this.el.find("select[name='ruleType']");
			this.optional=this.el.find("div[databind='optional']");
			this.current=this.el.find("div[databind='current']");
			this.frame.setContent(this.el);
			var instance=this;
			this.menu=new Menu({context:this.el,parent:this.frame.wrap,load:function(data,label){instance.load(data,label);}});
			this.menu.setDefaultValue(this.data.fragment);
			this.bind();
			this.render();
		},
		bind:function(){
			var instance=this;
			this.select.change(function(){
				instance.menu.setValue(this.selectedIndex);
			});
			$(this.menu).bind(Menu.CHANGED_EVENT,function(e,idx){instance.select[0].selectedIndex=idx;});
			this.optional.delegate(".pages a","click",function(){instance.optional.html(instance.getOptionalHtml(instance.optional.cacheData,parseInt($(this).html())))});
			this.current.delegate(".pages a","click",function(){instance.current.html(instance.getCurrentHtml(instance.current.cacheData,parseInt($(this).html())))});
			this.optional.delegate("div.top-action a[bind='replaceAll']","click",function(){instance.replaceAll();});
			this.optional.delegate("a[bind='moveToLeft']","click",function(){instance.moveToLeft(this)});
			this.current.delegate("a[bind='topmost']","click",function(){instance.topmost(this);});
			this.current.delegate("a[bind='remove']","click",function(){instance.remove(this);});
			this.current.delegate("div.top-action a[bind='empty']","click",function(){instance.empty();});
			this.el.find("button[bind='save']").click(function(){instance.save();});
		},
		topmost:function(el){
			var id=$(el).data("id"),item;
			item=this.getItem(this.data.contentList,id);
			if(!!item){
				this.deleteItem(this.data.contentList,id);
				this.data.contentList.unshift(item);
				this.render();				
			}
		},
		remove:function(el){
			var id=$(el).data("id");
			this.deleteItem(this.data.contentList,id);
			this.render();
		},		
		moveToLeft:function(el){
			var id=$(el).data("id"),item;
			item=this.getItem(this.optional.cacheData,id);
			if(!!item){
				item=this.convert(item);
				if(!this.data.contentList){
					this.data.contentList=[];
				}
				this.data.contentList.unshift(item);
				this.render();
			}
		},
		empty:function(){
			this.data.contentList=[];
			this.render();
		},
		replaceAll:function(){
			var data=this.data.contentList=[],
				instance=this;
			$.each(this.optional.cacheData,function(){
				data.push(instance.convert(this));
			});
			this.render();
		},
		convert:function(obj){
			var item={
					"avalibleQuantity": obj.avalibleQuantity,
		            "id": obj.id,
		            "imageUrl":obj.coverPath||"",
		            "listPrice": obj.listprice,
		            "name": obj.name,
		            "performance": {
		                "lastOnShelfTime": obj.lastOnShelfTime,
		                "monthSale": obj.monthSale,
		                "totalVisit": obj.monthVisit,
		                "weekSale": obj.weekSale
		            },
		            "salePrice": obj.saleprice,
		            "url": "http://www.winxuan.com/product/"+obj.id
				};
			return item;
		},
		getOptionalAction:function(item){
			var html='<ul><li><a href="javascript:;" bind="moveToLeft" data-id="{id}">左移</a></li><li>&nbsp;</li><li><b class="red fb">￥{salePrice}</b></li></ul>';
				html=html.replace(/{id}/g,item.id);
				html=html.replace(/{salePrice}/g,item.saleprice.toFixed(2));
			return html;
		},
		getOptionalHtml:function(data,page){
			var html=["<table><caption>可选商品 <span>("+this.optional.cacheLabel+")</span></caption>"],start,end,instance=this;
			this.optional.selectedPage=page||1;
				start=this.pageSize*(this.optional.selectedPage-1);
				end=start+this.pageSize;
				$.each(data,function(i){
					var item=template.item;
					if(start<=i&&i<end){
						item=item.replace(/{url}/g,conf.portalServer+"/product/"+this.id);
						item=item.replace(/{imageUrl}/g,this.coverPath||"");
						item=item.replace(/{listPrice}/g,this.listprice.toFixed(2));
						item=item.replace(/{name}/g,this.name);
						item=item.replace(/{salePrice}/g,this.saleprice.toFixed(2));
						item=item.replace(/{weekSale}/g,this.weekSale);
						item=item.replace(/{monthSale}/g,this.monthSale);
						item=item.replace(/{lastOnShelfTime}/g,this.lastOnShelfTime||"");
						item=item.replace(/{avalibleQuantity}/g,this.avalibleQuantity||0);
						item=item.replace(/{action}/g,instance.getOptionalAction(this));
						html.push(item);							
					}
				});
				html.push('</table><div class="top-action"><a href="javascript:;" bind="replaceAll">替换当前商品</a></div><div class="bottom-action">'+this.page(this.optional.cacheData,"optional")+'</div>');
			return html.join("");
		},
		load:function(data,label){
			var instance=this;
			this.optional.paramData=data; //cache this parameter;
			this.optional.html("<span>loading...</span>");
			$.getJSON(provider,data,function(data){
				if(data.searchResult.commodities&&data.searchResult.commodities.length>0){
					instance.optional.cacheData=data.searchResult.commodities;
					instance.optional.cacheLabel=label||"全部";
					instance.optional.html(instance.getOptionalHtml(instance.optional.cacheData));				
				}else{
					instance.optional.html("<span>暂无数据</span>");
				}
			});
		},
		page:function(arr,type){
			if(!arr||arr.length==0){
				return "&nbsp;";				
			}
			var html=["<ul class='pages'>"];
			this[type].pageCount=this.size(arr.length);
			for(var i=1;i<=this[type].pageCount;i++){
				if(this[type].selectedPage==i){
					html.push("<li>"+i+"</li>");
				}else{
					html.push("<li><a href='javascript:;'>"+i+"</a></li>");
				}
			}
			html.push("</ul>");
			return html.join("");
		},
		size:function(count){
			if(count%this.pageSize){
				return parseInt(count/this.pageSize)+1;
			}else{
				return count/this.pageSize;
			}
		},
		getCurrentAction:function(item){
			var html='<ul><li><a href="javascript:;" bind="topmost" data-id="{id}">置顶</a></li><li><a href="javascript:;" bind="remove" data-id="{id}">移除</a></li><li><b class="red fb">￥{salePrice}</b></li></ul>';
				html=html.replace(/{id}/g,item.id);
				html=html.replace(/{salePrice}/g,item.salePrice.toFixed(2));
			return html;
		},
		getCurrentHtml:function(data,page){
			var html=["<table><caption>当前商品</caption>"],start,end,instance=this;
				this.current.selectedPage=page||1;
				start=this.pageSize*(this.current.selectedPage-1);
				end=start+this.pageSize;
				$.each(data,function(i){
					if(start<=i&&i<end){
						var item=template.item;
							item=item.replace(/{url}/g,this.url).replace(/{id}/g,this.id);
							item=item.replace(/{imageUrl}/g,this.imageUrl);
							item=item.replace(/{listPrice}/g,this.listPrice.toFixed(2));
							item=item.replace(/{name}/g,this.name);
							item=item.replace(/{salePrice}/g,this.salePrice.toFixed(2));
							item=item.replace(/{avalibleQuantity}/g,this.avalibleQuantity||0);
							if(this.performance){
								item=item.replace(/{monthSale}/g,this.performance.monthSale||0);
								item=item.replace(/{weekSale}/g,this.performance.weekSale||0);
								item=item.replace(/{lastOnShelfTime}/g,this.performance.lastOnShelfTime||"");						
							}else{
								item=item.replace(/{monthSale}/g,0);
								item=item.replace(/{weekSale}/g,0);
								item=item.replace(/{lastOnShelfTime}/g,"");
							}
							item=item.replace(/{action}/g,instance.getCurrentAction(this));
							html.push(item);
					}
				});
			html.push('</table><div class="top-action"><a href="javascript:;" bind="empty">清空当前列表</a></div><div class="bottom-action">'+this.page(this.current.cacheData,"current")+'</div>');
			return html.join("");
		},
		render:function(){
			if(this.data.contentList&&this.data.contentList.length>0){
				this.current.cacheData=this.data.contentList;
				this.current.html(this.getCurrentHtml(this.current.cacheData));
			}else{
				this.current.html("<table><caption>当前商品</caption><tr><td>暂无相关商品，请在上方选择商品</td></tr></table>");
			}
		},
		getPostData:function(){
			var data={};
			data.ruleId=this.menu.getRule();
			if(data.ruleId!="1"){
				data.categoryId=this.menu.getCategory();
			}
			data.contentIds=[];
			if(this.data.contentList&&this.data.contentList.length>0){
				$.each(this.data.contentList,function(){
					data.contentIds.push(this.id);
				});
			}
			data.url=refUrl;
			data.cachekey = this.frame.cachekey;
			return data;
		},
		save:function(){
			var url=conf.consoleServer+"/fragment/"+this.data.fragment.id,
				instance=this;
			this.frame.lock("正在保存并更新数据");
			$.post(url,$.param(this.getPostData(),true),function(data){
				instance.frame.opt.save($(data));
				instance.frame.unlock();
			});
		},
		getItem:function(list,id){
			var item;
			for(var i=0;i<list.length;i++){
				item=list[i];
				if(item.id==id){
					return item;
				}
			}
			return null;
		},
		deleteItem:function(list,id){
			var item=this.getItem(list,id),index;
			if(!!item){
				index=list.indexOf(item);
				if(index>=0){
					list.splice(index,1);
					return true;
				}
				return false;
			}
		}
	};
	Editor.News=function(data){
		alert("TODO news editor");
	};
	Editor.Link=function(data,frame){
		this.data=data;
		this.frame=frame;
		this.init();
	};
	Editor.Link.prototype={
		init:function(){
			this.el=$(template.link);
			this.body=this.el.find("table tbody");
			this.form=this.el.find("form");
			this.href=this.el.find("input[name='href']");
			this.src=this.el.find("input[name='src']");
			this.render();
			this.frame.setContent(this.el);
			this.bind();
		},
		render:function(){
			var html=[];
			if(this.data.contentList&&this.data.contentList.length>0){
				$.each(this.data.contentList,function(){
					var item=template.linkItem;
					    item=item.replace(/{desc}/g,this.desc||"&nbsp;");
						item=item.replace(/{href}/g,this.href);
						item=item.replace(/{id}/g,this.id||"");
						item=item.replace(/{name}/g,this.name||"&nbsp;");
						item=item.replace(/{image}/g,this.src||"&nbsp;");
						item=item.replace(/{title}/g,this.title||"&nbsp;");
					html.push(item);
				});
				this.body.html(html.join(""));
			}else{
				this.body.html("<tr><td colspan='5'><div class='nocontent'>暂无相关数据</div></td></tr>");
			}
			this.body.find("tr:even").addClass("even");
		},
		bind:function(){
			var instance=this;
			this.el.delegate("a.delete","click",function(){instance.del(this);});
			this.el.delegate("a.edit","click",function(){instance.edit(this);});
			this.el.delegate("a.topmost","click",function(){instance.topmost(this);});
			this.el.delegate("tr","hover",function(){$(this).toggleClass("hover");});
			this.el.find("div.button button").click(function(){instance.update();});
			this.form.find("button").click(function(){instance.save();});
			this.form.find("a").click(function(){instance.reset();});
		},
		del:function(el){
			var id=$(el).attr("param");
			this.body.find("tr[param='"+id+"']").remove();
			if(!this.remove(id)){
				throw new Error("Can't remove element. ID's: "+id);
			};
		},
		remove:function(id){
			var item=this.get(id),index;
			if(!!item){
				index=this.data.contentList.indexOf(item);
				if(index>=0){
					this.data.contentList.splice(index,1);
					return true;
				}
			}
			return false;
		},
		topmost:function(el){
			var id=$(el).attr("param"),item;
			item=this.get(id);
			if(!!item){
				this.remove(id);
				this.data.contentList.unshift(item);
				this.render();				
			}			
		},
		get:function(id){
			var item;
			for(var i=0;i<this.data.contentList.length;i++){
				item=this.data.contentList[i];
				if(item.id==id){
					return item;
				}
			}
			return null;
		},
		edit:function(el){
			var item=this.get($(el).attr("param"));
			if(!!item){
				this.fill(item);				
			}else{
				throw new Error("edit link error! can't found object. ID: "+$(el).attr("param"));
			}
		},
		fill:function(item){
			this.form.find(":input").each(function(){
				var el=$(this),name=el.attr("name");
				if(name&&item[name]){
					el.val(item[name]);
				}
			});
		},
		validate:function(){   
            var href=this.href.val(),
			    src=this.src.val(),
			    regUrl="^((https|http|ftp|rtsp|mms)://)(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\.[a-zA-Z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$",
			    patern=new RegExp(regUrl);
			if(href&&!patern.test(href)){		    
                alert("你的链接地址不符合规范，注意请以http://开头！");
			    return false;
			}
			if(src&&!patern.test(src)){
			    alert("你的图片地址不符合规范，注意请以http://开头！");
			    return false;
			}
			return true;
		},
		reset:function(){
			this.form.find(":input").val("");
		},
		save:function(){
			var url,instance=this,id=instance.form.find("input[name='id']").val();
			if(this.validate()){
				instance.frame.lock("正在保存链接...");
				url=conf.consoleServer+"/fragment/link?format=json"
				$.post(url,this.form.serialize(),function(data){
					if(data.result=="1"){
						if(id&&id.length>0){
							var item=instance.get(id);
							$.extend(item,data.link);
						}else{
							if(!instance.data.contentList){
								instance.data.contentList=[];
							}
							instance.data.contentList.push(data.link);							
						}
						instance.render();
						instance.reset();
						instance.frame.unlock();
					}else{
						throw new Error("save link object has failed!");
					}
				});				
			}
		},
		update:function(){
			
			var url,instance=this,data,contentIds=[];
				url=conf.consoleServer+"/fragment/"+this.data.fragment.id;
				cachekey = this.frame.cachekey;
			if(this.data.contentList&&this.data.contentList.length>0){
				$.each(this.data.contentList,function(){
					contentIds.push(this.id);
				});
			}
			data={
				ruleId:this.data.fragment.rule,
				categoryId:(this.data.fragment.category?this.data.fragment.category.id:""),
				contentIds:contentIds,
				url:refUrl,
				cachekey:cachekey
			};	
			instance.frame.lock("正在保存并更新页面...");
			$.post(url,$.param(data,true),function(data){
				instance.frame.opt.save($(data));
				instance.frame.unlock();
			});
		}
	};
	Editor.Text=function(data,frame){
		this.data=data;
		this.frame=frame;
		this.init();
	};
	Editor.Text.prototype={
		init:function(){
			this.el=$(template.text);
			this.textarea=this.el.find("textarea");
			if(this.data.contentList.length>0){
				this.textarea.val(this.data.contentList[0].content);				
			}
			this.button=this.el.find("div.button button");
			this.frame.setContent(this.el);
			this.textarea.wysiwyg({
				controls:{
					html:{visible:true},
					insertImage:{visible:false},
					insertTable:{visible:false},
					code:{visible:false},
					h1:{visible:false},
					h2:{visible:false},
					h3:{visible:false}
				}
			});
			this.bind();
		},
		bind:function(){
			var instance=this;
			this.button.click(function(){instance.save()});
		},
		save:function(){
			var url=conf.consoleServer+"/fragment/text?format=json",
				instance=this;
				this.frame.lock("正在保存数据...");
			$.post(url,{
					id:this.data.contentList[0].id,
					name:this.data.contentList[0].name,
					content:this.textarea.val()
				},function(data){
					instance.frame.unlock();
					if(data.result=="1"){
						$.extend(instance.data.contentList[0],data.text);
						instance.update();
					}
			});
		},
		update:function(){
			var instance=this,
				url=conf.consoleServer+"/fragment/"+this.data.fragment.id,
				contentIds=[this.data.contentList[0].id],
				data={
					ruleId:this.data.fragment.rule,
					categoryId:(this.data.fragment.category?this.data.fragment.category.id:""),
					contentIds:contentIds,
					cachekey:this.frame.cachekey,
					url:refUrl
				};
				this.frame.lock("正在更新页面...");
				$.post(url,$.param(data,true),function(data){
					instance.frame.opt.save($(data));
					instance.frame.unlock();	
				});
		}
	};
	
	function Menu(cfg){
		this.opt={
			context:$(document),
			defaultValue:0,
			parent:$(document.body),
			load:function(){}
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	Menu.CHANGED_EVENT="changed";
	Menu.prototype={
		init:function(){
			this.value=this.opt.defaultValue;
			this.leftArrow=this.opt.context.find("a.lArrow");
			this.rightArrow=this.opt.context.find("a.rArrow");
			this.list=this.opt.context.find("div.right>ul");
			this.items=this.list.find(">li");
			this.max=this.items.length-1;
			this.offset=this.items.width();
			this.ruleType=this.opt.context.find("select[name='ruleType']");
			var instance=this;
			this.list.find(">li.category").each(function(){
				new Category({context:$(this),parent:instance.opt.parent,load:instance.opt.load});
			});
			this.search=this.list.find(">li.search");
			this.keyword=this.search.find("input[name='keyword']");
			this.button=this.search.find("button");
			new Assister({
				form: this.search,
				tipText: "",
				isForm: false,
				width: this.keyword.innerWidth(),
				resultsClass: "ac_results_nomargin"
			});
			this.bind();
		},
		getRule:function(){
			return this.ruleType.val();
		},
		getCategory:function(){
			var rule=this.getRule();
			if(rule=="1"){
				return null;
			}
			return this.list.find(">li.category input[rule='"+rule+"']").data("categoryId");
		},
		bind:function(){
			var menu=this;
			this.leftArrow.click(function(){menu.prev();});
			this.rightArrow.click(function(){menu.next();});
			this.button.click(function(){menu.load();});
			this.keyword.focus(function(){
				$(this).keyup(function(e){if(e.keyCode=="13"){menu.load();menu.keyword.trigger("blur");}});
			}).blur(function(){$(this).unbind("keyup");});
		},
		isDigit:function(num){
			var patrn=/^[0-9]{1,20}$/;
            if (!patrn.exec(num)) 
                return false
            return true
		},
		load:function(){
			var keyword=this.keyword.val();
			if(keyword.length>0){
				var para;
				if(this.isDigit(keyword)){
					para = {pid:keyword,sort:"1"};
				}else{
					para = {keyword:keyword,sort:"1"};
				}
				//根据外部id
				var outer = $("input[name='outer']");
				//电子书
				var ebook = $("input[name='ebook']");
				var searchmall = $("input[name='mall']");
				var searchmedia = $("input[name='media']");
					if(outer.attr("checked")=="checked"){
					   para = {outerid:keyword,sort:"1"};
					}
					if(ebook.attr("checked")=="checked"){
				    	para.onlyEBook=true;
					}
				    if(searchmall.attr("checked")=="checked"){
				    	para.type=11003;
					}
				    if(searchmedia.attr("checked")=="checked"){
				    	para.type=11002;
					}
				this.opt.load(para,"关键字:"+keyword);
			}
		},
		prev:function(){
			this.change(-1);
			$(this).trigger(Menu.CHANGED_EVENT,[this.value]);
			this.move();
		},
		next:function(){
			this.change(+1);
			$(this).trigger(Menu.CHANGED_EVENT,[this.value]);
			this.move();
		},
		change:function(op){
			if(this.value==0){
				if(op==-1){
					this.value=this.max;
				}else if(op==+1){
					this.value++;
				}
			}else if(this.value==this.max){
				if(op==+1){
					this.value=0;					
				}else if(op==-1){
					this.value--;
				}
			}else{
				this.value=this.value+op;
			}
		},
		move:function(){
			this.list.animate({
				marginLeft:-(this.offset*this.value)
			});
		},
		setValue:function(val){
			this.value=val;
			this.move();
		},
		getValue:function(){
			return this.value;
		},
		setDefaultValue:function(cfg){
			this.ruleType.val(cfg.rule);
			if(this.ruleType.length>0){
				this.setValue(this.ruleType[0].selectedIndex);				
			}else{
				throw new Error("Can't found element of menu's ruleTyple ");
			}
			if(cfg.category){
				this.list.find(">li.category input[rule='"+cfg.rule+"']").
				val(cfg.category.name).
				data("categoryId",cfg.category.id).
				attr("code",cfg.category.code);	
			}
		}
	};
	
	function Frame(cfg){
		this.opt={
			dock:null,
			type:1,
			title:"",
			width:"auto"
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	Frame.prototype={
		init:function(){
			this.el=$(template.frame);
			this.wrap=this.el.find("div.widget\\:wrap");
			this.title=this.el.find("div.widget\\:title label");
			this.close=this.el.find("div.widget\\:title a[bind='close']");
			this.content=this.el.find("div.widget\\:content");
			this.title.html(this.opt.title);
			this.el.appendTo(document.body);
			this.locate();
			this.bind();
			this.el.css({width:this.opt.width});
			this.el.show();
			this.opt.module.editing=true;
			this.load();
		},
		bind:function(){
			var frame=this;
			this.close.click(function(){frame.remove();});
			this.el.delegate("a[bind='editName']","click",function(){
				frame.editName(this);
			}).
			delegate("button[bind='saveName']","click",function(e){
				frame.saveName();
				e.preventDefault();
				return false;
			}).
			delegate("button[bind='saveQuantity']","click",function(e){
				frame.saveQuantity();
				e.preventDefault();
				return false;
			});
			this.el.delegate("form","mousemove",function(e){e.preventDefault();e.stopPropagation();return false;});
			this.el.easydrag();
			this.el.setHandler(this.title.parent());
		},
		editName:function(el){
			var name=this.title.text();
			this.title.replaceWith("<form action='javascript:;'><input name='fragmentName' value='"+name+"'/><button bind='saveName'>保存</button></form>");
			$(el).remove();
		},
		saveName:function(){
			var name=this.el.find("input[name='fragmentName']").val(),
				url=conf.consoleServer+"/fragment/"+this.id+"/save?format=json";
				$.post(url,{name:name});
			this.title=$("<label>"+name+"</label>");
			this.el.find("div.widget\\:title form").replaceWith(this.title);
			$("<a href='javascript:;' bind='editName'>[编辑]</a>&nbsp;<span>编号:"+frame.id+"</span>&nbsp;数量<input type='text' name='quantity' value="+frame.quantity+"><button bind='saveQuantity'>保存</button>").appendTo(this.title.parent());
		},
		saveQuantity:function(){
			var quantity=this.el.find("input[name='quantity']").val(),
				url=conf.consoleServer+"/fragment/"+this.id+"/quantity?format=json";
				$.post(url,{q:quantity});
		},
		locate:function(){
			var top=$(window).height()/2-this.el.height()/2+$(document).scrollTop(),
				left=$(window).width()/2-this.el.width()/2;
				
				top=top<0?0:top;
				left=left<0?0:left;

			this.el.css({top:top,left:left});
		},
		load:function(){
			var frame=this;
			$.getJSON(this.opt.dataUrl,function(data){
				frame.title.html(data.fragment.name);
				frame.id=data.fragment.id;
				frame.quantity=data.fragment.quantity;
				frame.cachekey = frame.opt.module.cachekey;
				//$("<a href='javascript:;' bind='editName'>[编辑]</a>&nbsp;<span>编号:"+frame.id+"</span>&nbsp;数量<input type='text' name='quantity' value="+frame.quantity+"><button bind='saveQuantity'>保存</button>").appendTo(frame.title.parent());
				$("<a href='javascript:;' bind='editName'>[编辑]</a>&nbsp;<span>编号:"+frame.id+"</span>&nbsp;&nbsp;&nbsp;外部id<input style='width:20px;' type='checkbox' name='outer'/>&nbsp;&nbsp;&nbsp;电子书<input style='width:20px;' type='checkbox' name='ebook'/>&nbsp;&nbsp;&nbsp;百货<input style='width:20px;' type='checkbox' name='mall'/>&nbsp;&nbsp;&nbsp;音像<input style='width:20px;' type='checkbox' name='media'/>").appendTo(frame.title.parent());
				var type=Editor.type[data.fragment.type];
				this.editor=new Editor[type](data,frame);
			});
		},
		remove:function(){
			this.el.remove();
			this.opt.module.editing=false;
		},
		setContent:function(content){
			this.content.html(content);
			this.locate();
		},
		lock:function(msg){
			this.overlay=$("<div class='widget:overlay'><p>"+msg+"</p></div>");
			this.overlay.css({
				height:this.wrap.height(),
				width:this.wrap.width()
			});
			this.overlay.appendTo(this.wrap);
		},
		show:function(){
			if(!this.showing){
				this.el.appendTo(document.body);
				this.bind();
			}
		},
		unlock:function(){
			this.overlay.remove();
		}
	};
	return Editor;
});
