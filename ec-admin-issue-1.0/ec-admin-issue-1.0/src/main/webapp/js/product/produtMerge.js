$(document).ready(function(){
	
	var view={
			init:function(){
				this.merage = $("a.merage");
				this.notmerage = $("a.notmerage");
				this.ignore = $("a.ignore");
				this.prev = $("a.prev");
			    this.inputYuep =$("input[name='yuep']");
				this.bind();
				this.listenKeyboard();
				
			},
			bind:function(){
				var mode = this;
				this.merage.popover({trigger:'hover'});
				this.merage.click(function(){
					mode.marge($(this).attr('data'));
				});
				this.notmerage.popover({trigger:'hover'});
				this.notmerage.click(function(){
					mode.marge($(this).attr('data'));
				});
				this.ignore.popover({trigger:'hover'});
				this.prev.popover({trigger:'hover'});
				this.ignore.click(function(){
					mode.ignoreAction();
				});
				
				//this.confirm("abcde");
				
				
			},
			listenKeyboard:function(){
				var mode = this;
				$(document).keypress(function(event){
					if(event.keyCode==13||event.keyCode==112){
						mode.marge('true');
					}
					if(event.keyCode==113){
						mode.marge('false');
					}
					if(event.keyCode==115){
						mode.ignoreAction();
					}
					if(event.which==49&&mode.prev.length>0){
						window.location.href = mode.prev.attr("href");
					}
				});
			},
			ignoreAction:function(){
				$(document).unbind("keypress");
				 var mode = this;
				 var inputWinXuanP=$("input[name='winxuanP']:checked");
				 var yunWinXuanP =$("input[name='yuep']:checked");
				 var data = {
						 psId:inputWinXuanP.val(),
						 yuePsId:yunWinXuanP.val(),
						 isIgnore:true
						 
				     };
				$.post("/product/merge?format=json",data,function(d){
					mode.nextData();
				});
				
			},
			
			marge:function(msg){
			     var info;
				if(msg==='true'){
					info="确认合并?";
				}else{
					info="确认不合并?";
				}
				
				if(!window.confirm(info)){
					return;
				}
				
				$(document).unbind("keypress");
				 var inputWinXuanP=$("input[name='winxuanP']:checked");
				 var yunWinXuanP =$("input[name='yuep']:checked");
				 var mode = this;
				 var data = {
						 psId:inputWinXuanP.val(),
						 yuePsId:yunWinXuanP.val(),
						 isMerge:msg
				     };
				$.post("/product/merge?format=json",data,function(d){
					mode.nextData();
				});
			},
			nextData:function(){
				$("#modalbackdroptrue").modal({show:true,keyboard:false});
				var prevId = this.inputYuep.val();
				window.location.href = "/product/data?prevId="+prevId;
			},
			confirm:function(msg){
				var modelConfirm = $("#modelConfirm"); 
				var msgDiag = modelConfirm.find("div.msg");
				msgDiag.html(msg);
				modelConfirm.modal({show:true,keyboard:false});
				modelConfirm.on("hidden",function(event){
					
					console.info(event);
				});
			}
	};
	
	view.init();
	
});

