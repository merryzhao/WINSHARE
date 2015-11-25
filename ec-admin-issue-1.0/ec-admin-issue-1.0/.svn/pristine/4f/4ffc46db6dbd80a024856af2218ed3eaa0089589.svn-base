$(document).ready(function(){
	window.delivery = {
//		formatDate : function(s){
//			var date = new Date();
//			date.setYear()
//			return date;
//		},
		formCheck : function(){
			
			var start = $('#startDeliveryTime').val();
			var end = $('#endDeliveryTime').val();
			
			
			if(start == '' || end == ''){
				alert('请选择日期');
				return false;
			}
			
			var startTime = new Date(start);
			var endTime = new Date(end);
			if(startTime > endTime) {
				alert("开始时间大于了结束时间");
				return false;
			}
			
			var offsetDay = (startTime.valueOf() - endTime.valueOf()) / 1000 / 60 / 60 / 24;
			if (Math.abs(offsetDay) > 31) {
				alert("将请日期控制在1个月以内,(31天)");
				return false;
			}

		},
		
		appendArea : function(){
			
			var areas = $("select:visible[areaLevel]").find(":selected[value!=-1]");
			if(areas.length == 0){return;}
			
			var areaRange = ['country','province','city','district'];
			var areaKey = areaRange[areas.length - 1];
			var areaId = $(areas[areas.length - 1]).val();
			
			
			var htmlText = '';
			for (var i = 0 ; i < areas.length - 1; i++ ){
				htmlText += $(areas[i]).text() + " >> ";
			}
			htmlText += $(areas[areas.length - 1]).text();
			var htmlVal = "<input type='hidden' name='" + areaKey + "' value='"+areaId+"' />";
			var htmlBtn = "<input type='button' value='X' onclick='delivery.deleteArea(this);' />";
			var html = '<li>' + htmlText + htmlVal + htmlBtn + "</li>";
			$('#areaList').append(html);
		},
		deleteArea : function(elm){
			$(elm).parent().remove();
		}
		
	};
	initArea(23);
});