(function(){
		var view={
		init:function(){
			this.queryBtn = $("button[name='query']");
			this.queryItem = $("input[name='queryWord']");			
			this.bind();
		},
		bind:function(){
			this.queryBtn.click(view.queryAction);
		},
		queryAction:function(){
			var queryWord = view.queryItem.val();
			var url = window.location.pathname;
			url = url + "?queryWord="+encodeURIComponent(queryWord);
			window.location = url;
		}
	};
	view.init();
})();
