(function(){
	var view={
		init:function(){
			this.manageEL = $(".subject_box");
			if (!!this.manageEL) {
				this.manageEL.dialog({
					title:'专题管理',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:322,
					maxHeight:400,
					height: 270
				});
				this.manageEL.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.floorEL = $(".floor_box");
			if (!!this.floorEL) {
				this.floorEL.dialog({
					title:'楼层设置',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:250,
					maxHeight:400,
					height: 250
				});
				this.floorEL.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.addSubjectBtn = $("button[name='addSubject']");
			this.manageSubjectBtn = $("button[name='manageSubject']");
			this.delSubjectBtn = $("a[bind='delSubject']");
			this.floorSetBtn = $("button[name='floorSet']");
			this.snameItem = this.manageEL.find("input[name='sname']");
			this.stitleItem = this.manageEL.find("input[name='stitle']");
			this.selectItem = this.manageEL.find("select[name='select']");
			this.urlItem = this.manageEL.find("input[name='surl']");
			this.floorItem = this.floorEL.find("input[name='floor']");
			this.floorEditIdHiddenItem = this.floorEL.find("input[name='editIdHidden']");
			this.editSubjectLink = $("a[bind='editSubject']");
			this.bind();
		},
		bind:function(){
			this.addSubjectBtn.click(view.show);
			this.manageSubjectBtn.click(view.manageSubjectAction);
			this.delSubjectBtn.click(view.delSubjectAction);
			this.editSubjectLink.click(view.editSubjectAction);
			this.floorSetBtn.click(view.floorSetAction);
		},
		delSubjectAction:function(){
			if(!window.confirm("确认要删除该专题吗？")){
				return false;
			}
			var delId = $(this).attr("delId");
			var delUrl = '/subject/del?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : delUrl,
				data: "delId="+delId,
				error : function() {//请求失败处理函数
					alert('删除专题失败');
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
		floorSetAction:function(){
			var floor = view.floorItem.val();
			var editId = view.floorEditIdHiddenItem.val();
			if(!view.checkEL(floor)){
				alert("楼层数不能为空");
				return false;
			}
			var   newPar= /^\d+$/;
			if(!newPar.test(floor)){
				alert("必须为正整数");
				return false;
			}
			var floorUrl = '/subject/floor?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : floorUrl,
				data: "floor="+floor+"&editId="+editId,
				error : function() {//请求失败处理函数
					alert('楼层设置失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						view.floorEL.dialog("close");
						window.open("/cms?url="+encodeURIComponent("http://www.winxuan.com/subject/")+editId);
					}else{
						view.showFloor();
					}
				}
			});
		},
		editSubjectAction:function(){
			var editId = $(this).attr("editId");
			var queryUrl = '/subject/floor?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : queryUrl,
				data: "editId="+editId,
				error : function() {//请求失败处理函数
					alert('查询专题楼层失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result>=1){
						window.open("/cms?url="+encodeURIComponent("http://www.winxuan.com/subject/")+editId);
					}else{
						view.showFloor(editId);
					}
				}
			});
		},
		closeAction:function(event, ui){
			window.location.reload();
		},
		manageSubjectAction:function(){
			var name = view.snameItem.val();
			var title = view.stitleItem.val();
			var sort = view.selectItem.val();
			var tagurl = view.urlItem.val();
			if(!view.checkEL(name)){
				alert("专题名不能为空");
				return false;
			}
			if(!view.checkEL(title)){
				alert("专题标题不能为空");
				return false;
			}
			var addUrl = '/subject/add?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : addUrl,
				data: "name="+name+"&title="+title+"&sort="+sort+"&url="+tagurl,
				error : function() {//请求失败处理函数
					alert('添加专题失败');
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
		showFloor:function(editId){
			view.floorItem.val("");
			view.floorEditIdHiddenItem.val(editId);
			view.floorEL.dialog("open");
		},
		show:function(){
			view.manageEL.dialog("open");
		}
	};
	view.init();
})();
