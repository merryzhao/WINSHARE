define(function(require){
	
	var Template={
		ITEM:'<div class="item" data-id="{orderId}"><ul><li class="date">{date}</li><li class="price">&yen;{target}</li><li class="price">&yen;{current}</li></ul></div>',
		render:function(data){
			var datastr = data.date.substring(0,10);
			var html=this.ITEM.replace(/\{orderId\}/g,datastr).
				replace(/\{date\}/g,datastr).
				replace(/\{target\}/g,data.totalListPrice).
				replace(/\{current\}/g,data.deliveryListPrice);

			return html;
		}
	};

	return Template;
});