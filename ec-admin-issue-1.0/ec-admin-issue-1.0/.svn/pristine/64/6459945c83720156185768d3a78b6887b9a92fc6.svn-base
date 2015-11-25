define(function(require){

	function getTime(dateTime){
		var time=dateTime.split(" ")[1],
			idx=time.lastIndexOf(":");			
		return time.substring(0,idx);
	}

	var Template={
		ITEM:'<div class="item" data-id="{orderId}"><ul><li class="order">{orderId}</li><li class="time">{time}</li><li class="price">&yen;{price}</li></ul><address><label>{name}</label><p>{province}{city}</p></address></div>',
		render:function(data){
			var html=this.ITEM.replace(/\{orderId\}/g,data.orderId).
				replace(/\{city\}/g,data.city).
				replace(/\{name\}/g,data.consignee).
				replace(/\{province\}/g,data.province).
				replace(/\{price\}/g,data.saleprice.toFixed(2)).
				replace(/\{time\}/g,getTime(data.lastProcessTime));

			return html;
		}
	};

	return Template;
});