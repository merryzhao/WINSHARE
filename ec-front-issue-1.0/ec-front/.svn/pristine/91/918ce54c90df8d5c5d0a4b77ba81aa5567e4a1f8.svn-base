seajs.use(["jQuery","score"],function($,score){
	var productSaleId = $(".productSaleId").attr("value"),
	     rating_results=$(".rating_results");
	$("#newquestion").click(function(){
		 var title =$("#title").val(),
		    content =$("#content").val(), 
			verifyCode=$("#verifyCode").val(),
			param={"title":title,"content":content,"verifyCode":verifyCode}; 
		var flag = inputCheck.check(title,content);
		if(!flag){return false};
		$.ajax({
			type: "POST",
			dataType: "json",
			url: $(".consulting").attr("action"),
			data: param,
			success: function(data){
				if (data.status) {
					alert("评论成功!")
					 $("#title").val("");
					 $("#content").val("");
					 $("#verifyCode").val("");
			        var tabRequest = new TabRequest({
						context:$("div.tabcontent03"),
						action:$("li[target=tabcontent03]").attr("action"),
						template:$("li[target=tabcontent03]").attr("type")
					});
			     tabRequest.init();
				}else {
					alert(data.message);
				}
			}
		});	
	});

var inputCheck={
	    		check:function(title,comment){
		    		if(title.trim().length==0){
		    			alert("请输入标题名");
		    			return false;
		    		}
		    		if(comment.trim().length==0){
		    			alert("请输入提问内容");
		    			return false;
		    		}
		    		if(title.trim().length>100) {
		    			alert("输入标题不能超过 100 字");
		    			return false;
		    		}
		    		if(comment.trim().length>500) {
		    			alert("输入内容不能超过500 字");
		    			return false;
		    		}
		    		return true;
		    	}	
	       };
	var instance=score({
			action:"/product/"+productSaleId+"/comment?format=json",
			callback:function(data){ 
				alert(data.message);
			}
		});
	$(instance).bind(score.UPDATE_EVENT,function(e,data){		
  		rating_results.find("#avgStarScore").html(data.productSale.avgStarScore);
		rating_results.find("#allScoreCount").html(data.productSale.rankList.length);
  		rating_results.find("#shareByFiveStar").css("width",data.productSale.shareByFiveStar*100+"%");
 		rating_results.find("#shareByFourStar").css("width",data.productSale.shareByFourStar*100+"%");
 		rating_results.find("#shareByThreeStar").css("width",data.productSale.shareByThreeStar*100+"%");
 		rating_results.find("#shareByTwoStar").css("width",data.productSale.shareByTwoStar*100+"%");
 		rating_results.find("#shareByOneStar").css("width",data.productSale.shareByOneStar*100+"%");
 		rating_results.find("#commentCountByFiveStar").html(data.productSale.rankCountByFiveStar);
 		rating_results.find("#commentCountByFourStar").html(data.productSale.rankCountByFourStar);
 		rating_results.find("#commentCountByThreeStar").html(data.productSale.rankCountByThreeStar);
 		rating_results.find("#commentCountByTwoStar").html(data.productSale.rankCountByTwoStar);
 		rating_results.find("#commentCountByOneStar").html(data.productSale.rankCountByOneStar);
 	});
	
//---------------------高头的敏哥哥要说不规范,先留的起,保全功能.------------------------------------------------------------------------------//	
	var template={
		comment:'<div class="comment_content"><div class="reviewer"><span class="fr">{customer.name} 发表于 {commentTime}</span> <div class="com_star fl"><b style="width:{rank.rank}%"></b></div><a class="fb name_link">{title}</a></div><p class="have_bot">{content}{replyList}</div>',
		question:'<div class="comment_content"><div class="reviewer"><span class="fr">{customer.name} 发表于 {askTime}</span><a class="fb name_link">{title}</a></div><p class="have_bot">{content}</p>{defaultReply}</div>'
	};
	var view={
		tab_class:"current_info",
		init:function(){
			this.tab=$("ul.infor_tab");
			this.tabArray=["tabcontent01","tabcontent02","tabcontent03","tabcontent04"];
			this.bind();
		},
		bind:function(){
			
			
			this.tab.find("li").click(function(){view.load(this);});
			
			 $("a[bind='changeVerifyCode']").click(function(){
			  		var srcUrl = $(".verifyCodeImg").attr("src");
					$(".verifyCodeImg").attr("src",srcUrl+"?d="+Math.random);
			  });
		},
		load:function(el){
			var el=$(el),
				url=el.attr("action");
			this.changeState(el);
			if(url&&url.length>0&&(!el.data("loaded"))){
				var tabRequest = 	new TabRequest({
						context:$("div."+el.attr("target")),
						action:url,
						template:template[el.attr("type")],
					});
					tabRequest.init();
					el.data("loaded",true);	
			}
		},
		changeState:function(el){
			this.tab.find("li").removeClass(view.tab_class);
			el.addClass(view.tab_class);
			$.each(this.tabArray,function(){
				$("."+this).hide();
			});
			$('div.'+el.attr("target")).show();
		}
	};
	
	function TabRequest(cfg){
		this.opt={
			selector:{
				page:"h5.view_all"
			}
		};
		$.extend(this.opt,cfg);
		//this.init();
	};
	
	TabRequest.prototype={
		init:function(data){
			this.el=this.opt.context.find(this.opt.selector.page);
			this.list=this.opt.context.find("div.page_list");
			this.pageWrap=this.opt.context.find("h5.view_all");
 			this.request(data);
			this.bind();
		},
		request:function(data){
			var obj=this,data=data||{page:1,size:10};
			$.ajax({
				type:"POST",
				dataType:"json",
				beforeSend:obj.beforeSend(),
				url:this.opt.action,
				data:data,
				success:function(data){
					obj.load(data);
				}
			});
		},
		beforeSend:function(){
			this.list.html("加载中...<img src=\"http://www.winxuan.com/css/images/loading.gif\" alt=\"加载中\"/>");
		    this.pageWrap.hide();
		},
		
		bind:function(){
			var obj=this;
			this.el.delegate("form[id='paginationForm'] a","click",function(){
				var el=$(this),
					page=el.attr("page"),
					size=el.attr("size");
				obj.request({page:page,size:size});
			});
		},
		load:function(data){
			
			if(!data.renderer){
				this.list.css("textAlign","center");
				this.list.html("没有数据");
				this.pageWrap.html("");
			    return false;	
			};
			
			if(data.renderer == "question"){
				this.list.html(this.parseQuest(data.customerQuestions,template.question));
			}else if(data.renderer == "comment"){
				this.list.html(this.paraseComment(data.customerComments,template.comment));
			};
		    var html=data.pageHtmlString;
			this.pageWrap.html(html);
		    this.pageWrap.show();
		},
		parseQuest:function(data,temp){
			var html=[];
			$(data).each(function(){
				var quest = temp;
				    quest = quest.replace(/{customer.name}/g,this.customer!=null?this.customer.protectionName:"游客");
				    quest = quest.replace(/{askTime}/g,this.askTime);
					quest = quest.replace(/{title}/g,this.title);
					quest = quest.replace(/{content}/g,this.content);
					if(this.defaultReply!=null){
				    quest = quest.replace(/{defaultReply}/g,'<p class=\"reply\">{defaultReply.content}<p class=\"response_time txt_right\">{defaultReply.replier.name} 发表于 {defaultReply.replyTime}</p></p>');
				    quest = quest.replace(/{defaultReply.content}/g,this.defaultReply.content);
					quest = quest.replace(/{defaultReply.replier.name}/g,this.defaultReply.replier.name);
					quest = quest.replace(/{defaultReply.replyTime}/g,this.defaultReply.replyTime);
					}else{
					quest = quest.replace(/{defaultReply}/g,"");	
					}
				    html.push(quest);
			});
          return html.join("");	
		},
		paraseComment:function(data,temp){
			var html=[];
			$(data).each(function(){
				var comment = temp;
				    comment = comment.replace(/{customer.name}/g,this.customer!=null?this.customer.protectionName:"游客");
				    comment = comment.replace(/{commentTime}/g,this.commentTime);	
					comment = comment.replace(/{rank.rank}/g,this.rank.rank*20);
					comment = comment.replace(/{title}/g,this.title);
					comment = comment.replace(/{content}/g,this.content);
					if(this.replyList.length>0){
						var replyListlength = this.replyList.length;
						$.each(this.replyList,function(i){
					      	if( i+1 == replyListlength){
								comment = comment.replace(/{replyList}/,'<p class="reply ">{content}</p><p class="response_time txt_right">{replier.name} 回复于 {replyTime}</p>');
							}else{
								comment = comment.replace(/{replyList}/,'<p class="reply ">{content}</p><p class="response_time txt_right">{replier.name} 回复于 {replyTime}</p>{replyList}');
							}
							comment = comment.replace(/{content}/g,this.content);
							comment = comment.replace(/{replier.name}/g,this.replier.name);
							comment = comment.replace(/{replyTime}/g,this.replyTime);
						});
					}else{
						comment  = comment.replace(/{replyList}/,"");
					}
					
				html.push(comment);
			});
			
			return html.join("");
		}
	}
	view.init();
});