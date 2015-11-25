define(function(require){

	var 
		View=require("../view/history"),
		Utils=require("../util/common");

	function OrderHistory(){
		this.view=new View();
		this.hashMap={};
	};

	OrderHistory.prototype={
		init:function(data){
			var model=this;
			this.list=data.orderDetailList;
			this.view.load(this.list);

			this.view.on("history:refresh",function(){
				model.view.load(model.list);
			});
		},

		add:function(item){
			var old=this.hashMap[item.orderId];
console.info(item);
			if(!old){
				this.view.add(item);
				this.hashMap[item.orderId]=item;
			}
		},

		remove:function(item){
			Utils.arrayRemove(this.list,item);
			this.view.remove(item);
		}
	};

	return OrderHistory;
});