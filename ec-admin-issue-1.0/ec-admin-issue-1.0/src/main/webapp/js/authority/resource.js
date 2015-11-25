(function(){
		var view={
		init:function(){
			this.editGroup=$("#edit_group");
			this.groupEL=$("div.group_box");
			this.groupEL.dialog({
				title:'资源组管理',
				autoOpen:false,
				bgiframe:false,
				modal:true,
				width:650,
				maxHeight:600,
				height: 550
			});
			this.groupEL.bind("dialogclose",function(event, ui){
				view.closeAction(event, ui);
			});
			this.submitBtn = this.groupEL.find("button[name='add_group_but_submit']");
			this.delLink = this.groupEL.find("a[bind='removeGroup']");
			this.manageLink = $("a[bind='manageRelation']");
			this.manageEL = $(".manageRelation_box");
			if (!!this.manageEL) {
				this.manageEL.dialog({
					title:'资源资源组关系管理',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:650,
					maxHeight:600,
					height: 550
				});
				this.manageEL.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
				this.manageEL.bind("dialogopen",function(event, ui){
					view.openAction(event, ui);
				});
			}
			this.selectAll = $("input[bind='selectAll']");
			this.selectItem = $("input[bind='item']");
			this.manageRelationbtn = $("button[name='manageRelationAction']");
			this.removeResourceLink = $("a[bind='removeResource']");
			this.editResourceLink = $("a[bind='editResource']");
			this.editResourceEL = $(".edit_resource_box");
			if (!!this.editResourceEL) {
				this.editResourceEL.dialog({
					title:'资源管理',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:550,
					height: 350
				});
				this.editResourceEL.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.editResourceBtn = null;
			this.bind();
		},
		bind:function(){
			this.editGroup.click(view.show);
			this.submitBtn.click(view.submit);
			this.delLink.click(view.delAction);
			this.manageLink.click(view.manageRelationShow);
			this.selectAll.click(view.selectAllChecked);
			this.manageRelationbtn.click(view.manageRelationAction);
			this.removeResourceLink.click(view.removeResourceAction);
			this.editResourceLink.click(view.editResourceShow);
		},
		editResourceShow:function(){
			var editResourceUrl = '/resource/editResource?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : editResourceUrl,
				data: "resourceId="+$(this).attr("editResource"),
				error : function() {//请求失败处理函数
					alert('获取资源失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						var code = data.resource.code;
						var value = data.resource.value;
						var description = data.resource.description==null?"":data.resource.description;
						var id = data.resource.id;
						var html = "<div>"+
						"<p class='text_space'><span>资源编码：</span><textarea disabled='disabled' class='textarea_txt' bind='code' >"+code+"</textarea></p>"+
						"<p class='text_space'><span>资源值：</span><textarea class='textarea_txt' bind='value'>"+value+"</textarea></p>"+
						"<p class='text_space'><span>资源描述：</span><textarea class='textarea_txt' bind='description'>"+description+"</textarea></p>"+
						"</div><center ><button class='manage_relation' name='editResourceAction' editResource='"+id+"'>保		存</button></center>";
						view.editResourceEL.html(html);
						view.editResourceBtn = $("button[name='editResourceAction']");
						view.editResourceBtn.bind("click",function(){
							view.editResourceAction(id);
						});
						view.editResourceEL.dialog("open");
					}else{
						alert(data.message);
					}
				}
			});
		},
		editResourceAction:function(id){
			var code = view.editResourceEL.find("textarea[bind='code']").val();
			var value = view.editResourceEL.find("textarea[bind='value']").val();
			if(!view.checkEL(code)){
				alert("资源编码不能为空");
				return false;
			}
			if(!view.checkEL(value)){
				alert("资源值不能为空");
				return false;
			}
			var description = view.editResourceEL.find("textarea[bind='description']").val();
			var editResourceUrl = '/resource/editResource?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : editResourceUrl,
				data: "resourceId="+id+"&code="+code+"&value="+value+"&description="+description,
				error : function() {//请求失败处理函数
					alert('编辑资源失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});
		},
		removeResourceAction:function(){
			
			if(!window.confirm("确认要删除该资源吗？")){
				return false;
			}
			var delResourceUrl = '/resource/delResource?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : delResourceUrl,
				data: "resourceId="+$(this).attr("removeResource"), 
				error : function() {//请求失败处理函数
					alert('删除资源失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						var el = $("tr[resourceId='"+ data.resourceId +"']");
						el.remove();
					}else{
						alert(data.message);
					}
				}
			});
		},
		manageRelationAction:function(){
			if(!view.hasChecked(view.selectItem)){
				alert("请至少选中一个");
				return false;
			}
			var str="";
			var boo = false;
			var selectItem = view.selectItem;
			for (var i = 0; i < selectItem.length; i++) {
				var el = selectItem[i];
				if(el.checked){
					if(!boo){
						str += $(el).attr("value");
						boo = true;
					}else{
						str += "," + $(el).attr("value");
					}
				}
			}
			var resourceId = view.manageEL.find("div[class='segment']").html();
			view.doManageRelation(resourceId,str);
		},
		doManageRelation:function(resourceId,str){
			var delGroupUrl = '/resource/relation?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : delGroupUrl,
				data: "resourceId="+resourceId+"&groupIds="+str, 
				error : function() {//请求失败处理函数
					alert('资源资源组关系维护失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});			
		},
		hasChecked:function(selectItem){
			for(var i = 0; i< selectItem.length; i++){
				var val = selectItem[i];
				if(val.checked){
					return true;
				}
			}
			return false;
		},
		openAction:function(event,ui){
			var strs = view.manageEL.attr("resourceGroupIds");
			var selectItem = view.selectItem;
			for (var i = 0; i < selectItem.length; i++) {
				var el = selectItem[i];
				var strArray = strs.split(",");
				for(var k=0;k<strArray.length;k++){
					if(strArray[k]==$(el).attr("value")){
						$(el).attr("checked", "checked");
						break;
					}
				}
			}
		},
		selectAllChecked:function(){
			if (!!view.selectAll.attr("checked")) {
				view.selectItem.attr("checked", "checked");
				view.selectAll.attr("checked", "checked");
			}else{
				view.selectItem.removeAttr("checked");
				view.selectAll.removeAttr("checked");
			}
		},
		manageRelationShow:function(){
			if (!!view.manageEL) {
				var segment = "<div style='display:none' class='segment'>"+$(this).attr("resourceId")+"</div>";
				view.manageEL.append(segment);
				view.manageEL.attr("resourceId",$(this).attr("resourceid"));
				var resourceId = $(this).attr("resourceid");
				var els = $(this.parentNode).find("font[bind="+resourceId+"]");
				var str="";
				for(var i=0;i<els.length;i++){
					str += $(els[i]).attr("rgId")+",";
				}
				view.manageEL.attr("resourceGroupIds",str);
				view.manageEL.dialog("open");
			}else{
				alert("请先创建资源组");
				return;
			}
		},
		delAction:function(){
			if(!window.confirm("确认要删除该资源组吗？")){
				return false;
			}
			var delGroupUrl = '/resource/delGroup?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : delGroupUrl,
				data: "groupId="+$(this).attr("delGroupId"), 
				error : function() {//请求失败处理函数
					alert('删除资源组失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						var el = $("tr[groupId='"+ data.groupId +"']");
						el.remove();
					}else{
						alert(data.message);
					}
				}
			});
		},
		closeAction:function(event, ui){
			window.location.href="/resource";
		},
		submit:function(){
			var groupCode = view.groupEL.find("input[name='groupCode']").val();
			var groupValue = view.groupEL.find("input[name='groupValue']").val();
			var groupDes = view.groupEL.find("input[name='groupDes']").val();
			if (!view.checkEL(groupCode)) {
				alert("资源组编码不能为空");
				return false;
			}
			if (!view.checkEL(groupValue)) {
				alert("资源组值不能为空");
				return false;
			}
			view.addGroup(groupCode,groupValue,groupDes);
		},
		addGroup:function(groupCode,groupValue,groupDes){
			var addGroupUrl = '/resource/addGroup?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : addGroupUrl,
				data: "groupCode="+groupCode+"&groupValue="+groupValue+"&groupDes="+groupDes, 
				error : function() {//请求失败处理函数
					alert('添加资源组失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						var html = view.groupContent(groupCode,groupValue,groupDes,data.groupId);
						$(".groups table tbody ").prepend(html);
						view.delLink.unbind("click");//重复bind的情况下先解绑
						view.delLink = view.groupEL.find("a[bind='removeGroup']");//重新绑定一下删除事件
						view.delLink.click(view.delAction);
						view.groupEL.find("input[name='groupCode']").val("");
						view.groupEL.find("input[name='groupValue']").val("");
						view.groupEL.find("input[name='groupDes']").val("");
					}else{
						alert(data.message);
					}
				}
			});
		},
		groupContent:function(groupCode,groupValue,groupDes,groupId){
			/*return "<tr groupId='" + groupId + "'>" + 
						"<td>"+groupCode+"</td><td>"+groupValue+"</td><td>"+groupDes+"</td><td>"+
						"<a editGroupId='"+groupId+"' href='javascript:;' class='gray' id='edit_tag_edit'>编辑</a> |" +
						" <a delGroupId='"+groupId+"' href='javascript:;' class='gray' bind='removeGroup'>删除</a>"
						+"</td></tr>";*/
			return "<tr groupId='" + groupId + "'>" + 
						"<td>"+groupCode+"</td><td>"+groupValue+"</td><td>"+groupDes+"</td><td>"+
						" <a delGroupId='"+groupId+"' href='javascript:;' class='gray' bind='removeGroup'>删除</a>"
						+"</td></tr>";
		},
		checkEL:function(val){
			if ($.trim(val).length == 0) {
				return false;
			};
			return true;
		},
		show:function(){
			view.groupEL.dialog("open");
		}
	};
	view.init();
})();
