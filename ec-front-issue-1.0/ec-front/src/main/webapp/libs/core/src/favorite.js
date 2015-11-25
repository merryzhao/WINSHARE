define(function(require){
	var $=require("jQuery"),
		ui=require("ui"),
		config=require("config"),
		cart=require("cart"),
		base=config.portalServer+"/customer/favorite",
		favorite={
			ADD_EVENT:"add_event",
			ADD_ITEMS_EVENT:"add_items_event",
			TAG_EVENT:"tag_event",
			GET_EVENT:"get_event",
			LOAD_EVENT:"load_event",
			CART_EVENT:"cart_event",
			LOGIN_EVENT:"login_event",
			REMOVE_EVENT:"remove_event",
			REMOVE_TAG_EVENT:"remove_tag_event",
			ADD_TAG_EVENT:"add_tag_event",
			EDIT_TAG_EVENT:"edit_tag_event",
			REMOVE_BATCH_EVENT:"remove_batch_event",
			message:{"1":"已成功加入收藏！","2":"此商品已收藏过，成功更新收藏时间！"},
			template:'<div class="favorite-result"><p>{result}</p><p><a href="http://www.winxuan.com/customer/favorite" target="myfavorite" bind="viewFavorite">查看我的收藏</a></p></div><div class="favorite-form"><p><label for="tag">自定义标签</label><input type="text" name="tag"/><span>空格隔开，最多3个标签</span></p><div><p><label for="recommend">推荐标签</label><div>{tags}</p></div><div class="button-wrap"><button bind="saveTag" p="{productSaleId}">保存标签</button></div></div>',
			container:'<div class="widgets-window favorite" id="favorite"><div class="widgets-title">加入收藏 <a href="javascript:;">X</a></div><div class="widgets-content"></div></div>',
			defOpt:{
				index:0,
				page:1,
				size:30
			},
			init:function(el){
				if(!this.el){
					$(this.container).appendTo(document.body);
					this.el=$("#favorite");
					this.bind();
				}
				this.el.find("div.widgets-content").html("<div class='loading'><p>正在更新收藏夹，请稍候...</p></div>");
				this.el.delegate("a[bind='viewFavorite']","click",function(){favorite.hide();});
				this.el.show();
				this.locate(el);
			},
			add:function(id,el){
				this.init(el);
				var url=[base+"/add?p="+id];
					url.push("format=jsonp");
					url.push("callback=?");
				$.jsonp({
					url:url.join("&"),
					success:function(data){
						$(favorite).trigger(favorite.ADD_EVENT,[data,el]);
					},
					error:function(xhr,status){
						$(favorite).trigger(favorite.LOGIN_EVENT,[id,el]);
					}
				});
			},
			addItems:function(params,el){
				this.init(el);
				var url=[base+"/add?format=jsonp"];
					url.push(params.join("&"));
					url.push("callback=?");
					$.jsonp({
						url:url.join("&"),
						success:function(data){
							$(favorite).trigger(favorite.ADD_ITEMS_EVENT,[data,el]);
						},
						error:function(xhr,status){
							$(favorite).trigger(favorite.LOGIN_EVENT,[params,el]);
						}
					});
			},
			addFormCart:function(id){
				var url=[base+"/add?p="+id];
					url.push("format=jsonp");
					url.push("callback=?");
					$.ajax({
						url:url.join("&"),
						success:function(data){
							$(favorite).trigger(favorite.ADD_EVENT,[data,id]);
						},
						error:function(xhr,status){
							$(favorite).trigger(favorite.LOGIN_EVENT,[id]);
						},
						global:false,
						dataType:"jsonp"
					});
			},
			tag:function(id,tags){
				tags = tags.join(" ");
				var url=[base+"/tag?p="+id];
					url.push($.param({tag:tags}));
					url.push("format=jsonp");
					url.push("callback=?");
					$.ajax({
						url:url.join("&"),
						success:function(data){
							data.tags = tags;
							data.pid = id;
							$(favorite).trigger(favorite.TAG_EVENT,[data]);
						},
						dataType:"json"
					});
			},
			get:function(opt){
				var option=$.extend(this.defOpt,opt),
					url=base+"?"+$.param(option)+"&format=jsonp";
				$.ajax({
					url:url,
					success:function(data){
						$(favorite).trigger(favorite.LOAD_EVENT,[data]);
					},
					error:function(xhr,status){
						if(xhr.status=="401"){
							$(favorite).trigger(favorite.LOGIN_EVENT,[]);
						}
					},
					global:false,
					dataType:"jsonp"
				});
			},
			remove:function(id,el){
				ui.confirm({
					message:"您确定要移除此条数据？",
					dock:el,
					callback:function(){
						$.getJSON(base+"/remove?p="+id+"&callback=?",{format:"jsonp"},function(data){
							data.id=id;
							$(favorite).trigger(favorite.REMOVE_EVENT,[data]);
						});
					}
				});
			},
			removeBatch:function(pids){
				var pidsString = "p=" + pids.join("&p=");
				ui.confirm({
					message:"确定删除？",
					callback:function(){
						$.ajax({
						   type: "POST",
						   url: base + "/removeBatch",
						   data: pidsString + "&_method=delete&format=json",
						   success: function(data){
						   	 data.pids = pids;
						     $(favorite).trigger(favorite.REMOVE_BATCH_EVENT,[data]);
						   }
						}); 
					}
				});
			},
			removeTag:function(id,el){
				ui.confirm({
					message:"您确定要移除此条数据？",
					dock:el,
					callback:function(){
						$.ajax({
						   type: "POST",
						   url: base + "/tag",
						   data: "tag=" + id + "&_method=delete&format=json",
						   success: function(data){
						   	 data.tagId = id;
						     $(favorite).trigger(favorite.REMOVE_TAG_EVENT,[data]);
						   }
						}); 
					}
				});
			},
			addTag:function(tagName,el){
				var tagName = $.trim(tagName);
				if(!tagName){
					alert("请输入标签名");
					return;
				}
				$.ajax({
				   type: "POST",
				   url: base + "/tag",
				   data: "tagName=" + tagName + "&format=json",
				   success: function(data){
				     data.tagName =  tagName ;
				     $(favorite).trigger(favorite.ADD_TAG_EVENT,[data]);
				   }
				}); 
			},
			editTag:function(tagId,tagName,count,el){
				var tagName = $.trim(tagName);
				if(!tagName){
					alert("请输入标签名");
					return;
				}
				$.ajax({
				   type: "POST",
				   url: base + "/tag",
				   data: "tagName=" + tagName + "&tagId=" + tagId + "&format=json&_method=put",
				   success: function(data){
				   	 data.tagId = tagId;
				     data.tagName =  tagName ;
					 data.count = count;
				     $(favorite).trigger(favorite.EDIT_TAG_EVENT,[data]);
				   }
				}); 
			},
			moveItemToCart:function(id){
				cart.add(id);
			},
			show:function(html,el){
				this.el.find("div.widgets-content").html(html);
				this.locate(el);
			},
			hide:function(){
				if(this.el && this.el.length > 0){
					this.el.hide();	
				}
			},
			bind:function(){
				this.el.find(".widgets-title a").click(function(){favorite.hide();});
				this.el.find("button[bind='saveTag']").live("click",function(){favorite.saveTag(this);});
				this.el.find(".favorite-form a").live("click",function(){favorite.fillForm(this);});
				this.el.find("input[name='tag']").live("focus",function(){favorite.showHint(this);}).live("blur",function(){favorite.hideHint(this);});
			},
			showHint:function(el){
				$(el).parent().addClass("hint");
			},
			hideHint:function(el){
				$(el).parent().removeClass("hint");
			},
			fillForm:function(el){
				var input=this.el.find("input[name='tag']");
				if(input.val().length>0){
					input.val(input.val()+" "+$(el).text());
				}else{
					input.val($(el).text());
				}
			},
			saveTag:function(el){
				var tags=this.el.find("input[name='tag']").val(),
					productSaleId=$(el).attr("p"),
					url=base+"/tag?callback=?";
				if(tags&&tags.length>0){
					$.getJSON(url,
						{p:productSaleId,tag:tags,format:"jsonp"},
						function(data){
							$(favorite).trigger(favorite.TAG_EVENT,[data]);
					});
				}
			},
			locate:function(el){
				if(!!el){
					var offset=$(el).offset();
					this.el.css({
						"top":offset.top-$(el).height()-this.el.height(),
						"left":offset.left-(this.el.width())+$(el).width()
					});
				}	
			}
		};
		$(cart).bind(cart.UPDATE_EVENT,function(e,data,id){
			$(favorite).trigger(favorite.CART_EVENT,[data,id]);
		});
	return favorite;
});
