(function(){
		var view={
		init:function(){
			this.auditLink = $("a[bind='audit']");
			this.unauditedLink = $("a[bind='unaudited']");
			this.auditBatchLink = $("a[bind='auditBatch']");
			this.unauditedBatchLink = $("a[bind='unauditedBatch']");
			this.selectAll = $("input[bind='selectAll']");
			this.selectItem = $("input[bind='item']");
			this.queryBtn = $("button[name='query']");
			this.queryItem = $("input[name='queryWord']");			
			this.bind();
		},
		bind:function(){
			this.auditLink.click(view.audit);
			this.unauditedLink.click(view.unaudited);
			this.selectAll.click(view.selectAllChecked);
			this.auditBatchLink.click(view.auditBatch);
			this.unauditedBatchLink.click(view.unauditedBatch);
			this.queryBtn.click(view.queryAction);
		},
		queryAction:function(){
			var queryWord = view.queryItem.val();
			var url = window.location.pathname;
			url = url + "?queryWord="+encodeURIComponent(queryWord);
			window.location = url;
		},
		auditBatch:function(){
			var str = view.beforeDoBatch();
			if(!window.confirm("审核通过？")){
				return false;
			}
			var auditUrl = '/dic/auditBatch?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : auditUrl,
				data: "ids="+str, 
				error : function() {//请求失败处理函数
					alert('审核通过失败');
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
		unauditedBatch:function(){
			var str = view.beforeDoBatch();
			if(!window.confirm("审核不通过？")){
				return false;
			}
			var auditUrl = '/dic/unauditedBatch?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : auditUrl,
				data: "ids="+str, 
				error : function() {//请求失败处理函数
					alert('审核不通过失败');
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
		beforeDoBatch:function(){
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
			return str;
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
		audit:function(){
			if(!window.confirm("审核通过？")){
				return false;
			}
			var auditUrl = '/dic/audit?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : auditUrl,
				data: "id="+$(this).attr("audit"), 
				error : function() {//请求失败处理函数
					alert('审核通过失败');
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
		unaudited:function(){
			if(!window.confirm("审核不通过？")){
				return false;
			}
			var unauditedUrl = '/dic/unaudited?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : unauditedUrl,
				data: "id="+$(this).attr("unaudited"), 
				error : function() {//请求失败处理函数
					alert('审核不通过失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});
		}
	};
	view.init();
})();
