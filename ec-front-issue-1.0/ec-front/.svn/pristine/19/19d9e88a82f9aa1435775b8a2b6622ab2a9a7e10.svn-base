seajs.use(["jQuery","score","login","toolkit", "tips","eventTrack","combBuy"],function($,score,login,ToolKit, tips, EventTrack, CombBuy){
	tips($);
	//如果代码中出现productSaleId == 0 的情况表示,针对店铺的逻辑
	var productSaleId = $(".productSaleId").attr("value");
	var shopId = $(".shopId").attr("value");
	var instance=score({
			action:"/product/"+productSaleId+"/rank?format=json",
			callback:function(data){ 
				alert(data.message);
			}
		});
	$('a[bind="useful"]').click(function(){
		var id=$(this).attr("param");
		instance.isuseful(id,$(this));
	});
	$('a[bind="useless"]').click(function(){
		var id=$(this).attr("param"); 
		instance.isuseless(id,$(this));
	}); 
	$('div.question_content').delegate('button[bind="reply"]','click',function(){
		var url = "http://www.winxuan.com/customer/status?mode=2&format=jsonp&callback=?",
			floor = $(this).data("field");;
		$.getJSON(url,function(data){
			if(!!data.visitor){
				$('div#replyTable_' + floor).toggle();
			}else{
				view.login();
			}
		});
	});
	function showTable(name){
		if(name == "tabcontent01"){
			$.each(tabArray,function(){
				$("."+this).show();
			});
			$(".comment_tit").show();
		} else {
			$(".comment_tit").hide();
			$.each(tabArray,function(){
				$("."+this).hide();
			});
			$('.'+name).show();
		}
	};
	
	var tab=$("ul.infor_tab"),
		tabArray=["tabcontent01","tabcontent02","tabcontent03","tabcontent04"],
		rating_results=$(".rating_results"),
		question = $(".question_content"),
		html ="";
	tab.find("li").click(function(){
		showTable($(this).attr("target"));
		tab.find("li").removeClass("current_info");
		$(this).addClass("current_info");
	});
	function reload(data){	
		var customerName="游客",
			html="",
			questionCount =$(".questionCount"),
		 	count = data.count,
			index = 1,
			customerAvatar = null;
		$.each(data.customerQuestions,function(){
			if(this.customer !=null){
				customerName = this.customer.protectionName;
				if(customerName.length > 8){customerName = customerName.substring(0,8) + '...';}
				customerAvatar = this.customer.avatar;
			}
			if(customerAvatar == null){
				customerAvatar = "../goods/comment_true.jpg";					
			}	
			html = html+"<div class='comment_list'><div class='user_info fl'>";
			html = html+"<ul><li><span class='u_photo'><img src='" + customerAvatar +"' alt='头像' /></span></li>";
			html = html+"<li><span class='u_name'>" + customerName +"</span></li>";
			if(this.customer != null){
				if(this.customer.grade==0){
					html = html+"<li><span class='u_level gray'>普通会员</span></li>";
				}else if(this.customer.grade==1){
					html = html+"<li><span class='u_level orange'>银牌会员</span></li>";
				}else if(this.customer.grade==2){
					html = html+"<li><span class='u_level red'>金牌会员</span></li>";
				}
			}
			html = html+"</ul></div><div class='comment_info'><div class='reviewer'><span class='fr'>"+ this.askTime.toString().substring(0,10) +" </span> <a class='fb name_link' href='/product/question/"+this.id+"'>"+this.title+"</a></div>";
			html = html+"<p>"+this.content.replace(/\</g,"&lt;").replace(/\>/g,"&gt;")+"</p><div id='questionReply_" + index + "' class='reply'>";
			if(this.replyList!=null && this.replyList.length>0){
				for(var i =0;i<this.replyList.length;i++){
					var replierName = this.replyList[i].replier.protectionName;
					if(replierName.length>8){
						replierName = replierName.substring(0,8) + '...';
					}
					html = html + "<div class='reply_item'><h4><span class='u_name link1'>"+ replierName +"</span> 回复说：<b style='float:right;'>"+this.replyList[i].replyTime.substring(0,10)+"</b></h4><div>" + this.replyList[i].content.replace(/\</g,"&lt;").replace(/\>/g,"&gt;") + "</div></div>";
				}
			}else{
				//<p align="right"><button bind="reply" data-field="${status.count }" class="reply_btn">回复</button></p>
				//html = html + "<p align='right'><button bind='reply' data-field='${status.count }' class='reply_btn'>回复</p>";
				//html = html +"<p>暂时没有回复！</p>";
			}
			
			html = html + "</div><p align='right'><button bind='reply' data-field='" + index + "' class='reply_btn'>回复</button></p>";
			html = html + "<div id='replyTable_" + index + "' style='display:none' class='replyTable' >";
			html = html + "<input type='hidden' value='" + this.id + "' id='customerQuestionId_" + index + "' />"
			html = html + "<table><tr>";
			html = html + "<td><textarea rows='6' cols='40' bind='replyContent_" + index + "' class='reply_txt'></textarea></td>"
			html = html + "</tr><tr>";
			html = html + "<td><button bind='replySubmit' data-field='" + index + "' class='useful_but'>回复</button></td>";
			html = html + "</tr></table></div>";			
			html = html+"</div></div>"
			index++;
		});	
		question.html(html);
		question.show(); 
		questionCount.html(count);
	};
	var canPost = true;
	$("#newquestion").click(function(){
		if(!canPost){
			alert("你已提交过了, 请勿多次操作!");
			return;
		}
		var title =$("#title").val(),
		    content =$("#content").val(), 
			verifyCode=$("#verifyCode").val(),
		param={"title":title,"content":content,"verifyCode":verifyCode};
		
		var url = "/product/"+productSaleId + "/question?format=json";
		if(productSaleId == 0){
			//对店铺进行平评论....
		   url = "/shop/"+shopId + "/question?format=json";
		}
		if (check(title,content)) {
			canPost = false;
			$.ajax({
				type: "POST",
				dataType: "json",
				url: url,
				data: param,
				success: function(data){
					if (data.status) {
						reload(data);
						$("#title").val("");
						$("#content").val("");
						$("#verifyCode").val("");
						var srcUrl = $(".verifyCodeImg").attr("src");
						$(".verifyCodeImg").attr("src",srcUrl+"?d="+Math.random);
					}else {
						alert(data.message);
						canPost = true;
					}
				}, 
				error: function(){
					canPost = true;
				}
			});
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
	$('div.question_content').delegate("button[bind='replySubmit']","click",function(){
		var floor = $(this).data("field"),
			questionId=$("#customerQuestionId_" + floor).val(),
			content=$("textarea[bind='replyContent_" + floor + "']").val(),
			param={'content':content};
		if(trim(content).length>500){
			alert("咨询回复内容不能超过500 字");
			return ;
		}
		if(trim(content).length==0){
			alert("咨询回复内容不能为空");
			return ;
		}
		$.ajax({
			type:"POST",
			dataType:"json",
			data:param,
			url:"/product/question/reply/"+questionId+"?format=json",
			success:function(data){
				if(data.status){
					if(data.replyList.length>0){
						loadReplyData(data.replyList,floor);
					}
					$('div#replyTable_' + floor).hide();
				}else{
					//$('div#replyTable_' + floor).hide();
					alert(data.message);
				}
			}
		});
	});
	function loadReplyData(reply_list, floor){
		var html="",
			current_reply=reply_list[reply_list.length-1],
			replyContent=$("#questionReply_"+floor);
		if(current_reply.content){
//			html+="<p class='reply'>"+current_reply.content+"</p>";
//			html+="<p class='response_time txt_right'>"+current_reply.replier.name+"回复于"+current_reply.replyTime+"</p>";
			replier_Name = current_reply.replier.protectionName;
			if(replier_Name.length>8){
				replier_Name = replier_Name.toString().substring(0,8) + '...';
			}
			html+="<div class='reply_item'><h4><span class='u_name link1'>" + replier_Name + "</span> 回复说：<b style='float:right;'>" + current_reply.replyTime.substring(0,10) + "</b></h4><div>" + current_reply.content + "</div></div>";
		}
		replyContent.append(html);
		replyContent.show();
	}
	$(".ilike").click(function(){
		var dig = $("b[dig='dig']");
		$.ajax({
		   type: "get",
		   dataType : "json",
		   url : "/product/"+productSaleId+"/digging?format=json",
		   success:function(data){
		   	 if(data.status){
			 	dig.html(data.count);
			 }	
		   }
		   })});
	
	var view ={
		login:function(el){
				login.callbackUrl="http://www.winxuan.com/wxdoor.jsp?callback=miniLoginWindowCallBack";
				window.miniLoginWindowCallBack=function(status,msg){
					login.callback(status,msg);
				};
				login.show(el);
		},
		refresh:function(){
			var loginmessage = $("b[bind='loginmessage']"),
			 	username = $("#username"),
			 	checkUrl = "http://www.winxuan.com/customer/status?mode=2&format=jsonp&callback=?";
			$.getJSON(checkUrl,function(data){
				if(!!data.visitor){
					loginmessage.html("");
					username.val(data.visitor);
					}
				});
			}
	};
	$("a[bind='login']").bind("click",function(){
		view.login(this);
	});
	
	$(login).bind(login.LOGINED_EVENT,function(){
		view.refresh();
	});
  $("a[bind='changeVerifyCode']").click(function(){
  		var srcUrl = $(".verifyCodeImg").attr("src");
		$(".verifyCodeImg").attr("src",srcUrl+"?d="+Math.random);
  });
    $(".verifyCodeImg").click(function(){
  		var srcUrl = $(".verifyCodeImg").attr("src");
		$(".verifyCodeImg").attr("src",srcUrl+"?d="+Math.random);
  });
  
  var performance ={ 
  	init:function(){	
	var totalSale =$("#totalSale"),
		totalComment =$("#totalComment"),
		totalFavorite =$("#totalFavorite"),
		totalDigging =$("#totalDigging");
	   if(productSaleId == 0){
		   return;
	   }
	
	 	$.ajax({
		   type: "GET",
		   dataType : "json",
		   url : "/product/"+productSaleId+"/performance?format=json&t="+new Date().getTime(),
		   success:function(data){
			   
			   if((data.performance.totalSale)>=20){
					totalSale.find("b").html(data.performance.totalSale);	
					totalSale.show();
			   }			   		   	 
				//totalComment.html(data.performance.totalComment);	
				totalFavorite.html(data.performance.totalFavorite);	
				totalDigging.html(data.performance.totalDigging);	
		   }
		   })
	 }
	 };
	performance.init();	
	var otherProdcuct = {	
		otherEl : $("dl[bind='otherProduct']"),
		authorEl : $("div[bind='authorEl']"),
		init:function(){
			otherProdcuct.load();
		},
		load:function(){
			var author = otherProdcuct.otherEl.attr("param");
			if (author == null || author.length == 0) {
				otherProdcuct.authorEl.hide();	
			}else {
				$.ajax({
					url: "http://search.winxuan.com/author?format=jsonp&authorName=" + author + "&callback=?",
					async: false,
					success: function(data){
						otherProdcuct.fillData(data);
					},
					error: function(xhr, status){
						otherProdcuct.otherEl.html("正在加载......");
					},
					dataType: "jsonp"
				});
			}
		},
		fillData:function(data){
			html = "";
			if(data&&data.searchResult&&data.searchResult.commodities){
				var div = $("<div></div>"),name;				
				$.each(data.searchResult.commodities,function(){
					if (this.id != productSaleId) {
						div.html(this.name);
						name = div.html();		
						html = html + "<dt><a class='otherProduct' target='_blank' href=\"" + this.url + "\"><img title =\"" + this.name + "\"  alt=\"" + this.name + "\" src=\"" + this.coverPath + "\"/></a>"+otherProdcuct.ebookIco(this)+"<a target='_blank' class=\"link4\" href=\"" +encodeURI(this.url)+ "\" title=\"" + this.name + "\">" + otherProdcuct.subString(name)+ "</a><br>" +
						"文轩价：<span class=\"fb red\">￥" +
						this.saleprice.toFixed(2) +
						"</span></dt>";
					}
				});
				otherProdcuct.otherEl.html(html);
			}
		},	
		subString:function(str){
			return str.length>=17 ? str.substring(0,17)+"..." : str;
		},
        ebookIco:function(product){
      	  var str = '<div class="ebook_mini_ico"></div>';
      	 if(product.storageType.id==6004){
      		 return str; 
      	 }
      	 return "";
        }
	};
	otherProdcuct.init();
	
	
	var ebookProduct={
		init:function(){
			this.bind();
			
		},
		bind:function(){
			var obj = this;
			$("button[bind='addebook']").click(function(){obj.addEbook($(this))});
			
		},
		addEbook:function(el){
			var actionUrl = "http://ebook.winxuan.com/shoppingcart?opt=add&p="+el.attr("data-id");
			
			alert("建设中...");
		}
	};
	ebookProduct.init();
	function check(title,comment) {
	if(trim(title).length==0){
		alert("请输入标题名");
		return false;
	}
	if(trim(comment).length==0){
		alert("请输入提问内容");
		return false;
	}
	if(trim(title).length>25) {
		alert("输入标题不能超过 25 字");
		return false;
	}
	if(trim(comment).length>3000) {
		alert("输入内容不能超过3000 字");
		return false;
	}
	return true;
}
 function trim(str){
 	return str.replace(/(^\s*)|(\s*$)/g,"");
 }

new ToolKit();
EventTrack({
	trackbtn:["[bind='addToCart']"],
	tracktab:["ul.infor_tab>li","ul.inner-blend-tab>li"],
	productId:productSaleId
}).init();
CombBuy({productId:productSaleId,context:".goods_info"}).init();
var dciInfo={
    el:$("a.dci_data"),
    init:function(){
        this.bind();
    },
    bind:function(){
        this.el.mouseover(dciInfo.showTip);
        $(document).mouseover(dciInfo.hideTip);
    },
    showTip:function(e){
        dciInfo.el.find(".dci-info").show();
        if(!dciInfo.loading){
            dciInfo.loading=true;
            dciInfo.post();
        }
        e.stopPropagation();
    },
    hideTip:function(){
        dciInfo.el.find(".dci-info").hide();
        if(dciInfo.loading){
            dciInfo.loading=false;
        }
    },
    post:function(){
        var bookid=this.el.data("bookid");
        $.getJSON("http://www.winxuan.com/product/dciLog?bookId="+bookid+"&src=2&format=jsonp&callback=?",function(data){
            if(data.result=="success"){
                console.log("count+");
            }
        });
    }
};
dciInfo.init();
(function(){
	var more=$("div.morereader"),
		content=more.find("div.content");
		content.each(function(){
			var el=$(this),
				height=this.scrollHeight,
				domnum=el.children().length,
				imgheight=el.find("img").first().height(),
				isimage=el.children(0).is("img");
			if(domnum&&isimage){
				if(imgheight&&domnum==1){
					el.css({height:imgheight});
				}else if(imgheight&&domnum>1){
					setContHeight(imgheight,el);
				}else if(!imgheight&&domnum==1){
					el.css({height:268});
				}else if(!imgheight&&domnum>1){
					setContHeight(268,el);
				}
			}else{
				if(height>168&&height<=268){
					el.css({height:height});
				}else if(height>268){
					setContHeight(168,el);
				}
			}
		});
	function setContHeight(height,el){
		el.css({height:height});
		el.data("srcheight", height);
		el.parent().find("a.read").show().click(function(){
			$(this).hide();
			el.parent().find("a.hide").show();
			el.animate({
				height:el[0].scrollHeight
			});
		});
		el.parent().find("a.hide").hide().click(function(){
			$(this).hide();
			el.animate({
				height:el.data("srcheight")
			});
			el.parent().find("a.read").show();
		});
	}
})();

});