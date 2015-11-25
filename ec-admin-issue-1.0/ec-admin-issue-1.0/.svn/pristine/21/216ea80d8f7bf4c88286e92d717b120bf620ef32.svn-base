(function(){
		var view={
		init:function(){
			this.removeConfig = $("a[bind='removeConfig']");	
			this.addConfigLink = $("button[name='addNewConfig']");		
			this.configEL=$("div.config");
			this.configEL.dialog({
				title:'添加配置信息',
				autoOpen:false,
				bgiframe:false,
				modal:true,
				width:400,
				maxHeight:400,
				height: 250
			});
			this.configEL.bind("dialogclose",function(event, ui){
				view.closeAction(event, ui);
			});
			this.address = this.configEL.find("input[name='address']");
			this.analyseScript = this.configEL.find("input[name='analyseScript']");
			this.charsetFormat = this.configEL.find("input[name='charsetFormat']");
			this.addConfigBtn = this.configEL.find("button[name='addConfig']");
			this.bind();
		},
		bind:function(){
			this.removeConfig.click(view.removeConfigAction);
			this.addConfigLink.click(view.show);
			this.addConfigBtn.click(view.addConfigAction);
		},
		checkEL:function(val){
			if ($.trim(val).length == 0) {
				return false;
			};
			return true;
		},
		addConfigAction:function(){
			var address = view.address.val();
			var analyseScript = view.analyseScript.val();
			var charsetFormat = view.charsetFormat.val();
			if(!view.checkEL(address)){
				alert("抓取地址不能为空");
				return false;
			}
			if(!view.checkEL(analyseScript)){
				alert("解析脚本不能为空");
				return false;
			}
			if(!view.checkEL(charsetFormat)){
				alert("编码格式不能为空");
				return false;
			}
			var wordUrl = '/dic/addConfig?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : wordUrl,
				data: "address="+address+"&analyseScript="+analyseScript+"&charsetFormat="+charsetFormat,
				error : function() {//请求失败处理函数
					alert('添加配置信息失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.href="/dic/configList";
					}else{
						alert(data.message);
					}
				}
			});
		},
		closeAction:function(event, ui){
			window.location.href="/dic/configList";
		},
		show:function(){
			view.address.val("");
			view.analyseScript.val("");
			view.charsetFormat.val("");
			view.configEL.dialog("open");
		},
		removeConfigAction:function(){
			if(!window.confirm("删除该配置信息？")){
				return false;
			}
			var auditUrl = '/dic/configDel?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : auditUrl,
				data: "id="+$(this).attr("removeConfig"), 
				error : function() {//请求失败处理函数
					alert('删除配置信息失败');
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
