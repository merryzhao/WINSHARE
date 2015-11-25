$(document).ready(function(){
	var channel=null;
	channel ={
		init:function(){
			channel.lower();
			$("select[lower=channel]").change(function(){
				channel.lower();
			});
		},
		lower:function(){
			var channelId = $("select[lower=channel] option:selected").val();
			$.ajax({
	            type: 'post', 
	            url: "/channel/"+channelId+"?format=json", 
	            dataType: 'json', 
	            success: function(data) { 
	            	var channelData = data.channelList;
	            	$("select[lower=lower]").empty();
	            	$(channelData).each(function(i,c){
	            		var s="";
	            		if(channelData[i].id==$("input[for=lowerId]").val()){
	            			 s = "selected=selected";
	            		}else{
	            			s="";
	            		}
	            		$("select[lower=lower]").append("<option value='"+channelData[i].id+"' "+s+">"+channelData[i].name+"</option>");
	            	});
	            },
	            error: function() {
	            	alert('请求失败');
	            }
	        });
		}
	};
	channel.init();
});