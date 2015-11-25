
(function(){
	var view={
		init:function(){
			this.manageEL = $(".manageRelation_box");
			if (!!this.manageEL) {
				this.manageEL.dialog({
					title:'用户权限设置',
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
			}
			this.selectAll = $("input[bind='selectAll']");
			this.selectItem = $("input[bind='item']");
			this.manageLink = $("a[bind='manageRelation']");
			this.manageRelationbtn = $("button[name='manageRelationAction']");
			this.availableLink = $("a[bind='available']");
			this.bind();
		},
		bind:function(){
			this.manageLink.click(view.manageRelationShow);
			this.selectAll.click(view.selectAllChecked);
			this.manageRelationbtn.click(view.manageRelationAction);
			this.availableLink.click(view.availableAction);
		},
		availableAction:function(){
			var isAvalible = $(this).attr("available");
			if(isAvalible){
				if(!window.confirm("是否禁用该用户？"))return false;
			}else{
				if(!window.confirm("是否启用该用户？"))return false;
			}
			view.doAvailableAction($(this).attr("availableId"));
		},
		doAvailableAction:function(employeeId){
			var availableUrl = '/authority/available?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : availableUrl,
				data: "employeeId="+employeeId, 
				error : function() {//请求失败处理函数
					alert('启用禁用失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						var el = $("a[availableId='"+ data.employeeId +"']");
						if(data.available == true){
							el.html("禁用");
						}else{
							el.html("启用");
						}
						
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
			var employeeId = view.manageEL.find("div[class='segment']").html();
			view.doManageRelation(employeeId,str);
		},
		doManageRelation:function(employeeId,str){
			var manageUrl = '/authority/relation?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : manageUrl,
				data: "employeeId="+employeeId+"&groupIds="+str, 
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
		selectAllChecked:function(){
			if (!!view.selectAll.attr("checked")) {
				view.selectItem.attr("checked", "checked");
				view.selectAll.attr("checked", "checked");
			}else{
				view.selectItem.removeAttr("checked");
				view.selectAll.removeAttr("checked");
			}
		},
		closeAction:function(event, ui){
			var form = document.forms[0];
			form.action = "/authority/authority";
			form.method="get";
			form.submit(); 
		},
		manageRelationShow:function(){
			if (!!view.manageEL) {
				var segment = "<div style='display:none' class='segment'>"+$(this).attr("employeeId")+"</div>";
				view.manageEL.append(segment);
				view.manageEL.dialog("open");
			}else{
				alert("请先创建资源组");
				return;
			}
		}
	};
	
	
	

	view.init();
})();
