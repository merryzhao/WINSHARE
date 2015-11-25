define(function(require){
	var $=require("jQuery").sub(),
		conf=require("config"),
		XSS=require("xss");
		require("./auto-complete")($);
		function Assister(cfg){
			this.defOpt={
				form:document.searchForm,
				input:"input[name='keyword']",
				el:"div.head",
				category:"input[name='type']",
				selectTriger:".search-select",
				selected:".select_category_name",
				selectPop:".search-select-pop",
				action:conf.searchServer+"/autoCom.jsonp",
				tipText:"",
				isForm:true,
				isReload:true,
				width:0,
				paramName:"keyword",
				paramCategory:"type",
				resultsClass: "ac_results",
				message:{
					notNull:"请输入搜索关键字",
					maxLength:"搜索关键字不能超过24个字符"
				}
			};
			$.extend(this.defOpt,cfg);
			this.init();
		};
		Assister.prototype={
			init:function(cfg){
				var assister=this,xss=new XSS({context:this.defOpt.form});
				this.el=$(this.defOpt.el);
				this.form=$(this.defOpt.form);
				this.input=this.form.find(this.defOpt.input);
				this.category=this.form.find(this.defOpt.category);
				this.selectTriger=this.form.find(this.defOpt.selectTriger);
				this.selected=this.form.find(this.defOpt.selected);
				this.selectPop=this.form.find(this.defOpt.selectPop);
				/*this.input.autocomplete(this.defOpt.action,{
					multipe:false,
					dataType: "jsonp",
					width:this.defOpt.width,
					resultsClass:this.defOpt.resultsClass,
					scroll:false,
					autoFill:false,
					selectFirst:false,
					extraParams:{
						keyword:function(){return encodeURIComponent(assister.input.val());}
					},
					parse:function(data){
						return $.map(data['autoComResult'],function(row){
							return {
								data:row,
								value:row.name,
								result:row.name
							};
						});
					},
					formatItem:function(item){
						return assister.format(item);
					}
				});*/
				this.hotKeyword();
				this.bind();
			},
			setDefaultValue:function(){
				var value=this.input.val(),
					keyword=this.getParameter(this.defOpt.paramName),
					type=this.getParameter(this.defOpt.paramCategory);
				if(!this.defOpt.isReload&&keyword&&keyword.length>0){
					this.input.val(keyword);	
					this.setSelected(type);	
				}else{
					this.input.val(this.defOpt.tipText);	
				}
			},
			getParameter:function(name){
				var pairs=location.search.substring(1).split("&");
				if(!pairs.length){
					return;
				}
				for(var i=0;i<pairs.length;i++){
					var parts=pairs[i].split("=");
					if(parts.length>1&&parts[0]==name){
						return decodeURIComponent(parts[1]).replace(/\+/g," ");
					}
				}
			},
			bind:function(){
				var el=this.input,assister=this;
					el.focus(function(){
						if($.trim(el.val())==assister.defOpt.tipText){
							el.val("");
						}
						el.css("color","black");
					}).blur(function(){
						if($.trim(el.val()).length==0){
							el.css("color","");
							el.val(assister.defOpt.tipText);
						}
					});
					el.autocomplete(assister.defOpt.action,{
					multipe:false,
					dataType: "jsonp",
					width:assister.defOpt.width,
					resultsClass:assister.defOpt.resultsClass,
					scroll:false,
					autoFill:false,
					selectFirst:false,
					extraParams:{
						keyword:function(){return encodeURIComponent(assister.input.val());}
					},
					parse:function(data){
						return $.map(data['autoComResult'],function(row){
							return {
								data:row,
								value:row.name,
								result:row.name
							};
						});
					},
					formatItem:function(item){
						return assister.format(item);
					}
				});									
				$(window).scroll(function(){				
					$("."+assister.defOpt.resultsClass).hide();			
					});
				if(this.defOpt.isForm){
					this.form.submit(function(){return assister.validate();});					
				}
				this.selectTriger.mouseover(function(){assister.selectPop.show();}).
                mouseout(function(){assister.selectPop.hide();});
                this.selectPop.delegate("a",'click',function(){assister.select(this);});
			},
			select:function(el){	
			    var el=$(el),
			    sort=el.text(),
                key=el.data("key");
                this.selected.text(sort);
                this.category.val(key);
                this.selectPop.hide();
			},
			setSelected:function(type){
			    var sort;
			    $.each(this.selectPop.find("a"),function(){
			        var el=$(this);
			        if(el.data("key")==type) sort=el.text();
			    });
			    if(!!sort){
			     this.selected.text(sort);
			     this.category.val(type);
			    }
			},
			validate:function(){
				var value=$.trim(this.input.val()),
					msg=this.defOpt.message;
				if(value.length==0){
					alert(msg.notNull);
					return false;
				}
				//去掉限制
//				else if(value.length>24){
//					alert(msg.maxLength);
//					return false;
//				}
				return true;
			},
			
			format:function(row){
				var html=[];
				html.push("<span>"+row.name+"</span>");
				html.push("<label class='search-count'>约");
				html.push(row.hits);
				html.push("条</label>");
				return html.join("");
			},
			hotKeyword:function(){
				var assist = this;
				var url=conf.searchServer+"/searchHistory/hot.jsonp?callback=?",
					href=conf.searchServer+"/search?keyword=";
				$.getJSON(url,function(data){
					if(data&&data.searchDefault){
						assist.defOpt.tipText = data.searchDefault;
						assist.setDefaultValue();
					}
					/**
					var html=[];
					if(data&&data.searchHot){
						$.each(data.searchHot,function(){
						    var datehref = "";
							if(this.href!=null){
								datehref  = this.href;
							}else{
								datehref = href+encodeURIComponent(this.keyword);
							}						
							html.push("<a href='"+datehref+"' target='_blank' title='"+this.keyword+"'>"+this.keyword+"</a>");
						});
					}
					if(html.length>0){
						$(html.join("")).appendTo(assist.el.find(".master-search-keywords"));
					}
					**/
				});
			}
		};
	return Assister;
});
