seajs.use(["jQuery","favorite","login","ui","cart"],function($,favorite,login,ui,cart){
	var el=$(".right_box");
	var favCheckBoxEl = el.find("table input[name='checkbox']");
	$(favorite).bind(favorite.REMOVE_EVENT,function(e,data){view.remove(data);})
	.bind(favorite.REMOVE_TAG_EVENT,function(e,data){view.removeTag(data);})
	.bind(favorite.ADD_TAG_EVENT,function(e,data){view.addTag(data);})
	.bind(favorite.EDIT_TAG_EVENT,function(e,data){view.editTag(data);})
	.bind(favorite.TAG_EVENT,function(e,data){view.favoriteAddTag(data);})
	.bind(favorite.REMOVE_BATCH_EVENT,function(e,data){view.removeBatch(data);})
	;
	$(cart).bind(cart.BATCH_ADD_EVENT,function(e,data){view.favoriteToCart(data);})
	;

	
	function getUrl(param,value){
		var url = window.location.search.substring(1).split("&");
		var reUrl = "";
		if(url != ''){
			for (var i=0; i<url.length; i++) {
				var p = url[i].split("=");
				if(p[0] != param){
					reUrl = reUrl == "" ? reUrl : reUrl + "&";
					reUrl = reUrl + p[0] + "=" + p[1]; 
				}
			};
		}
		reUrl = (reUrl == "" ? reUrl : reUrl + "&");
		reUrl = reUrl + param + "=" + value;
		return reUrl;
	};
	
	var view = {
		tagContent:function(tagName , count){
			return '<td><p id="tag_name" class="txt_left">'+tagName+'</p></td><td id="count">'+count+'</td>'
							+ '<td><a id="edit_tag_edit" class="gray" href="javascript:;">编辑</a> | <a bind="removeTag" class="gray" href="javascript:;">删除</a>'
							+ '</td>';
		},
		favoriteAddTagContent:function(oldTags){
			return '<p class="new_sort have_border">'
							+'<span class="fr"><input type="button" value="保存" name="pageTagSave"'
								+'class="addnew_but"> <input type="button" value="取消" name="pageTagCancel"'
								+'class="addnew_but">'
							+'</span>标签：<input type="text" oldTags="'+ oldTags +'" value="'+ oldTags +'"></p>';}
		,
		favoriteTagContent:function(tags){
			return '标签：<span name="favgoods_info_tag_values">'+ tags +'</span> [<a class="link1" name="favoriteAddTag" href="javascript:;">编辑</a>] ';
						        
		},
		init:function(){
			
			seajs.use(["jQuery","toolkit"],function($,ToolKit){new ToolKit();});

			el.find('td a[bind="remove"]').live("click",function(){
				favorite.remove($(this).attr("productSaleId"),this);
			});
			$("#edit_tag").live("click",function(){
				$("#edit_tag_el").show();
				view.light=ui.highlight({
						el:$("#edit_tag_el")
					});
				}
			);
			$("#edit_tag_colse").live("click",function(){
					$("#edit_tag_el").hide();
					view.light.close();
					window.location.reload();
				}
			);
			$("#edit_tag_edit").live("click",function(){
					var tr = $(this).parent().parent();
					var tagName = tr.find("#tag_name").html();
					var count = tr.find("#count").html();
					var html = '<td count="'+ count +'" tagName="'+ tagName +'" colspan="3" class="no_pad"><p class="new_sort have_border"> <span class="fr"><input type="button" value="保存" name="" class="addnew_but"> <input id="edit_tag_cancel" type="button" value="取消" class="addnew_but">	</span>标签：<input type="text" name="">	</p></td>';
					tr.html(html);
					tr.find("input[type=text]").val(tagName);
					
				}	
			);
			$("#edit_tag_cancel").live("click",function(){
					var tr = $(this).parent().parent().parent().parent();
					var tagName = tr.find("td").attr("tagName");
					var count = tr.find("td").attr("count");
					var html = view.tagContent(tagName , count);
					tr.html(html);
				}	
			);
			$("#edit_tag_el a[bind=remove]").live("click",function(){
				favorite.remove($(this).attr("productSaleId"),this);
			});
			$('table a[bind="removeTag"]').live("click",function(){
				var tagId = $(this).parent().parent().attr("tagId");
				favorite.removeTag(tagId,this);
			});
			$('.new_sort input[name="add_new_but_submit"]').live("click",function(){
				var tagName = $(this).parent().find("input[type=text]").val();
				favorite.addTag(tagName,this);
			});
			$('.sorts_box input[value="保存"]').live("click",function(){
				var tr = $(this).parent().parent().parent().parent();
				var tagId = tr.attr("tagId");
				var count = tr.find("td").attr("count");
				var tagName = $(this).parent().parent().find("input[type='text']").val();
				favorite.editTag(tagId,tagName,count,this);
			});
			$("a[name='favoriteAddTag']").live("click",function(){
				var div = $(this).parent();
				var oldTags = $.trim(div.find("span[name='favgoods_info_tag_values']").html());
				div.html(view.favoriteAddTagContent(oldTags));
			});
			$(".new_sort input[name='pageTagCancel']").live("click",function(){
				var oldTags = $(this).parent().parent().find("input[type='text']").attr("oldTags");
				var html = view.favoriteTagContent(oldTags);
				$(this).parent().parent().parent().html(html);
			});
			$(".new_sort input[name='pageTagSave']").live("click",function(){
				var newTags = $.trim($(this).parent().parent().find("input[type='text']").val());
				var tags = newTags.split(" ");
				var pid = $(this).parent().parent().parent().parent().parent().parent().attr("productSaleId");
				favorite.tag(pid , tags);
			});
			el.find("a[bind='fav_buy']").live("click",function(){
				var productSaleId = $(this).parent().parent().attr("productSaleId");
				cart.add(productSaleId);
			});
			el.find("a[bind='batchAddToCart']").live("click",function(){
				var pids = el.find("input[name='checkbox']"),
					param=[];
					pids.each(function(){
						var ell=$(this);
						if(ell.attr("checked")){
							param.push("p=" + ell.parent().parent().attr("productsaleid"));
						}
					});
				if(param.length == 0){
					alert("请选择要购买的商品");
					return;
				}
			});
			$("a[bind='fav_batch_delete']").live("click",function(){
				var pids = el.find("input[name='checkbox']"),
					param=[];
					pids.each(function(){
						var ell=$(this);
						if(ell.attr("checked")){
							param.push(ell.parent().parent().attr("productsaleid"));
						}
					});
				if(param.length == 0){
					alert("请选择要删除的收藏");
					return;
				}
				favorite.removeBatch(param);
			});
			el.find("select[bind='fav_order']").live("change",function(){
				var param = "order";
				var value = $(this).val();
				var url = getUrl(param , value);
				window.location = window.location.pathname + "?" + url;
			});
		},
		remove:function(data){
			window.location.reload();
		},
		removeTag:function(data){
			if(data.status != 1){
				alert("删除失败")
				return;
			}
			var el = $("tr[tagId='"+ data.tagId +"']");
			el.remove();
		},
		addTag:function(data){
			if(data.status != 1){
				alert("此标签已经存在");
				return;
			}
			alert("添加成功")
			var html = "<tr tagId='" + data.tagId + "'>" + view.tagContent(data.tagName , 0) + "</tr>";
			$(".sorts_box table tbody").append(html);
		},
		editTag:function(data){
			if(data.status != 1){
				alert("此标签已经存在");
				return;
			}
			var tagName = data.tagName;
			var tagId = data.tagId;
			var count = data.count;
			var tr = $(".sorts_box tr[tagId='"+ tagId +"']");
			var html = view.tagContent(tagName , count);
			tr.html(html);
		},
		favoriteAddTag:function(data){
			var newTags = data.tags;
			var pid = data.pid;
			var html = view.favoriteTagContent(newTags);
			$(".favorite_goods tr[productSaleId='"+ pid +"'] span[name='favgoods_info_tag']").html(html);
		},
		favoriteToCart:function(data){
			favCheckBoxEl.removeAttr("checked");
		},
		removeBatch:function(data){
			if(data.status != 1){
				alert("删除失败");
			}
			else{
				el.find("tr input[name='checkbox']").removeAttr("checked");
				window.location.reload();
			}
		}
	};
	view.init();
});