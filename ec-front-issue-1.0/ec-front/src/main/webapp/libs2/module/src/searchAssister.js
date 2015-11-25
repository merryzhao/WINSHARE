define(function(require){
	var $=require("jQuery").sub(),
		conf=require("config");
		require("./auto-complete")($);
		function Assister(cfg){
			this.defOpt={
				form:document.searchForm,
				input:"input[name='keyword']",
				header:"div.header",
				action:conf.searchServer+"/autoCom.jsonp",
				tipText:"",
				isForm:true,
				isReload:true,
				width:470,
				paramName:"keyword",
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
				var assister=this;
				this.header=$(this.defOpt.header);
				this.form=$(this.defOpt.form);
				this.input=this.form.find(this.defOpt.input);
				this.input.autocomplete(this.defOpt.action,{
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
				});
				this.hotKeyword();
				this.bind();
			},
			setDefaultValue:function(){
				var value=this.input.val(),
					keyword=this.getParameter(this.defOpt.paramName);
				if(!this.defOpt.isReload&&keyword&&keyword.length>0){
					this.input.val(keyword);		
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
				if(this.defOpt.isForm){
					this.form.submit(function(){return assister.validate();});					
				}
			},
			
			validate:function(){
				var value=$.trim(this.input.val()),
					msg=this.defOpt.message;
				if(value.length==0){
					alert(msg.notNull);
					return false;
				}else if(value.length>24){
					alert(msg.maxLength);
					return false;
				}
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
						$(html.join("")).appendTo(assist.header.find("p.hot_search"));
					}
				});
			}
		};
	return Assister;
});
