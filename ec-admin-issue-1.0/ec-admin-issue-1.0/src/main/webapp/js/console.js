(function($){
	// 添加hover效果
	var Effects=[function(){$(this).addClass("hover");},function(){$(this).removeClass("hover");}];

	var mainFrame=$("frame-main-inne");
	(function(){
		$("table.list-table tr:gt(0)").live("mouseover",Effects[0]).live("mouseout",Effects[1]);
		$("div.frame-side li").hover(Effects[0],Effects[1]);
	})();
	

	// 显示loading
	function showLoading(msg){
		var message=msg||"数据处理中...　请稍候"
		$("div.loading").html(message).fadeIn('slow');
	}
	
	function hideLoading(){
		$("div.loading").fadeOut('slow');
	}

	(function(){
		var iframe=$("iframe.content-iframe"),doc;
		if (iframe.length > 0) {
			iframe.bind("load", function(){
				doc=this.contentWindow.document;
			});
			setInterval(function(){if(!!doc)if(doc.body.scrollHeight!=0){iframe.height(doc.body.scrollHeight)}else{iframe.height(doc.documentElement.scrollHeight)}},200);
		}
	})();
	
	
	(function(){
		/*
try{
			$("div.search-bar input[name='search']").live("click",function(){
				$(this).attr("defaultValue",$(this).val())
				.val("")
				.addClass("focused");
				}).live("blur",function(){
				var el=$(this),value=el.val();
				if((!value)||value.length==0){
					el.val(el.attr("defaultValue"))
					.removeClass("focused");
				}
			});
			$(".list-table input[type='checkbox'][name='selectAll']").live("click",function(){
				var list=$(".list-table input[type='checkbox'][name='item']");
				if(!!$(this).attr('checked')){
					list.attr("checked","checked");
				}else{
					list.removeAttr("checked");
				}
			});
			
			
			$(document.channelForm).submit(function(e){
				e.preventDefault();
				var form=this;
				$(form).append('<input type="hidden" name="format" value="json"/>');
				showLoading();
				closeForm();
				$.post(
					form.action,
					$(form).serialize(),
					function(data){
						showLoading(data.message);
						setTimeout(function(){hideLoading();},2000);
					},"json");
			});
			
			var editor=$("#channel-editor"),
				position=editor.position(),
				left=position.left;
			$(".menu-nav *[action] a").click(function(e){
				e.preventDefault();
			});
			$("li[binding='newChannel']").click(function(){
				editor.css({"left":left}).animate({
					"left":"+=1020"
				},400,function(){
					$(this).animate({
						"left":"-=220"
					},200);
				});
			});
			
			$("*[action='getItemList']").click(function(){
				var src=$("a",this).attr("href");
				showLoading();
				$("#content").load(src+" #content-result",function(){
					hideLoading();
				});
			});
			function closeForm(){
				editor.animate({
					left:"-=800"
				},400);
			};
			$("div.mini-editor-close").click(closeForm);
		}catch(e){}
*/
	})();
	
	(function(){
		setInterval(function(){
			var width=$(window).width();
			if(width<990){
				$("div.frame").css({"width":"990px"});				
			}else{
				$("div.frame").css({"width":"auto"});
			}
		},200);		
	})();
})(jQuery);
