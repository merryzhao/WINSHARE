$(document).ready(function(){
	
	  var view = {
	  	id: $("input[name='id']").val(),
	  	init:function(){
			this.update = $("a[bind='updatereply']");
			this.del = $("a[bind='deleteReply']");
			this.replayText = $("div.replayText");
			this.btnupdate = $("input[class='btnupdate']");
			this.btnsubmit = $("input[class='submit']");
			this.bind();
		},
		showtext:function(tag){
			var obj = $(tag);
			var  replayText = obj.parent();
		         replayText.find("div.replayText").show();
			
		},
		bind:function(){
			var obj = this;
			this.update.click(function(){obj.showtext(this)});
			this.del.click(function(){obj.delreply(this)});
			this.btnupdate.click(function(){obj.updatereply(this)});
			this.btnsubmit.click(function(){obj.checkform()});
		},
		delreply:function(tag){
			var obj = $(tag);		
			if(confirm("确认删除?")){
			var para = "id="+obj.attr("id")+"&ccid="+this.id;
			var url = '/complaint/deleteReply?format=json';
			$.ajax({
				type : 'GET',
				url : url,
				data: para,
				success : function(data) { 
					if(data.result==1){
						$(obj).parent().remove();
					}	 
				}
			 });
			}
		},
		updatereply:function(tag){
			var obj = $(tag);
			var el = this;
			var replayText = obj.parent().find("textarea[name='content']");
			var replaycontent = replayText.find("div.replaycontent");
			var content = replayText.val();
			if(content.length>500||content.length == 0){
				alert("长度保持在1-500");
			return false;
			}
			var para = "id="+obj.attr("id")+"&content="+content;
			var url = '/complaint/updatereply?format=json';
			$.ajax({
				type : 'POST',
				url : url,
				data: para,
				success : function(data) { 
					if(data.message>0){
						window.location.href = window.location;						
					}
				}
			 });
		},
		
	    reload:function(){
			
		},
		checkform:function(){
			if($("#replycontent").val()==""||$("#replycontent").val().length>500){
				alert("请认真填写回复!");
				return false;
			};
			$("form").submit();
		}
	  };
	  view.init();
	
});
