$(function(){
	window.dc = {
		edit : function(){
			var id = $('input[name=id]').val();
			var priority = $('select[name=priority]').val();
			var countrywide = $('select[name=countrywide]').val();
			var available = $('select[name=available]').val();
			var address = $('textarea[name=address]').val();
			var description = $('textarea[name=description]').val();
			$.ajax({
				dataType : "json",
				type : "POST",
				url : "/dc/"+id,
				data : {
					"id" : id,
					"priority" : priority,
					"countrywide" : countrywide,
					"available" : available,
					"address" : address,
					"description" : description,
					"format" : 'json'
				},
				success : function(msg){
					if(msg.result == 1){
						alert("修改成功");
					}else{
						alert("修改失败");
					}
				},
				error : function(msg){
					alert("修改失败");
				}
			});
		},
		checked : function(areaId){
			if($('input[value='+areaId+']').attr('checked')==undefined){
				$('input[value='+areaId+']').attr('checked', 'checked');
			}else{
				$('input[value='+areaId+']').removeAttr('checked', 'checked');
			}
		}
	}
})

$(document).ready(
	function(){
		$('#add').hide();
		$('.used-area-title').hide();
		$('.used-area-content').hide();
		$('#delete_confirm').hide();
		$('#add').click(function(){
			$('#delete').show();
			$('#add').hide();
			$('.used-area-title').hide();
			$('.used-area-content').hide();
			$('.unused-area-title').show();
			$('.unused-area-content').show();
			$('#delete_confirm').hide();
			$('#add_confirm').show();
			$('#area-form').attr('action', '/dc/area/add');
			$('#area-form').attr('method', 'post');
		});
		$('#delete').click(function(){
			$('#delete').hide();
			$('#add').show();
			$('.used-area-title').show();
			$('.used-area-content').show();
			$('.unused-area-title').hide();
			$('.unused-area-content').hide();
			$('#delete_confirm').show();
			$('#add_confirm').hide();
			$('#area-form').attr('action', '/dc/area/delete');
			$('#area-form').attr('method', 'get');
		});
	}
);
