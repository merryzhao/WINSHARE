(function(){
		var view={
		init:function(){
			this.wordEL=$("div.word");
			this.wordEL.dialog({
				title:'词典管理',
				autoOpen:false,
				bgiframe:false,
				modal:true,
				width:400,
				maxHeight:400,
				height: 250
			});
			this.wordEL.bind("dialogclose",function(event, ui){
				view.closeAction(event, ui);
			});
			this.submitBtn = this.wordEL.find("button[name='manage_word_but_submit']");
			this.addWordLink = $("button[name='addNewWord']");
			this.wordItem = this.wordEL.find("input[name='wordItem']");
			this.synonymItem = this.wordEL.find("input[name='synonymItem']");
			this.recommendItem = this.wordEL.find("input[name='recommendItem']");
			this.removeWordLink = $("a[bind='removeWord']");
			this.queryBtn = $("button[name='query']");
			this.queryItem = $("input[name='queryWord']");
			this.bind();
		},
		bind:function(){
			this.addWordLink.click(view.show);
			this.submitBtn.click(view.saveWordAction);
			this.removeWordLink.click(view.removeWordAction);
			this.wordItem.bind("focusout",function(){
				view.grabInfo();
			});
			this.queryBtn.click(view.queryAction);
		},
		queryAction:function(){
			var queryWord = view.queryItem.val();
			var url = window.location.pathname;
			url = url + "?queryWord="+encodeURIComponent(queryWord);
			window.location = url;
		},
		grabInfo: function(){
			var word = view.wordItem.val();
			if(word.length == 0){
				return false;
			}
			var auditUrl = '/dic/grabInfo?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : auditUrl,
				data: "word="+word, 
				error : function() {//请求失败处理函数
					;//不做任何处理
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						view.synonymItem.val(data.synonymWord);
						view.recommendItem.val(data.recommendWord);
					}else{
						;//不做任何处理
					}
				}
			});
		},
		removeWordAction:function(){
			if(!window.confirm("删除该词语？")){
				return false;
			}
			var auditUrl = '/dic/del?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : auditUrl,
				data: "id="+$(this).attr("removeWord"), 
				error : function() {//请求失败处理函数
					alert('删除词语失败');
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
		saveWordAction:function(){
			var word = view.wordItem.val();
			var synonymWord = view.synonymItem.val();
			var recommendWord = view.recommendItem.val();
			if(!view.checkEL(word)){
				alert("词语不能为空");
				return false;
			}
			var wordUrl = '/dic/manageWord?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : wordUrl,
				data: "word="+word+"&synonymWord="+synonymWord+"&recommendWord="+recommendWord,
				error : function() {//请求失败处理函数
					alert('编辑词典失败');
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
		closeAction:function(event, ui){
			window.location.href="/dic";
		},
		show:function(){
			view.wordItem.val("");
			view.synonymItem.val("");
			view.recommendItem.val("");
			view.wordEL.dialog("open");
		}
	};
	view.init();
})();
