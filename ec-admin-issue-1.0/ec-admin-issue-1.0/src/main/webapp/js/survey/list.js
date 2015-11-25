(function(){
	var view={
		init:function(){
			this.manageEL = $(".survey_box");
			if (!!this.manageEL) {
				this.manageEL.dialog({
					title:'添加调查表',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:250,
					maxHeight:400,
					height: 250
				});
				this.manageEL.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}

			this.addSurveyBtn = $("button[name='addSurvey']");
			this.manageSurveyBtn = $("button[name='manageSurvey']");
			this.snameItem = this.manageEL.find("input[name='sname']");
			this.editSurveyLink = $("a[bind='editSurvey']");
			this.deleteSurveyLink = $("a[bind='delSurvey']");
			this.bind();
		},
		bind:function(){
			this.addSurveyBtn.click(view.show);
			this.manageSurveyBtn.click(view.manageSurveyAction);
			this.editSurveyLink.click(view.editSurveyAction);
			this.deleteSurveyLink.click(view.deleteSurveyAction);
		},
		deleteSurveyAction:function(){
			if(!window.confirm("确认要删除该调查表吗？")){
				return false;
			}
			var delId = $(this).attr("delId");
			var delUrl = '/survey/del?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : delUrl,
				data: "deleteId="+delId,
				error : function() {//请求失败处理函数
					alert('删除失败，请联系技术人员');
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
		editSurveyAction:function(){
			var editId = $(this).attr("editId");
			window.open("/survey/"+editId);
		},
		closeAction:function(event, ui){
			window.location.reload();
		},
		manageSurveyAction:function(){
			var name = view.snameItem.val();
			if(!view.checkEL(name)){
				alert("调查名不能为空");
				return false;
			}
			var addUrl = '/survey/add?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : addUrl,
				data: "title="+name,
				error : function() {//请求失败处理函数
					alert('添加调查表失败');
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
		checkEL:function(val){
			if ($.trim(val).length == 0) {
				return false;
			};
			return true;
		},
		show:function(){
			view.manageEL.dialog("open");
		}
	};
	view.init();
})();
