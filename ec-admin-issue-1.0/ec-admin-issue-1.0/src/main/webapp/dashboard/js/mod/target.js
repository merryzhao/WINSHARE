define(function(require){

	var 
		View=require("../view/day-target"),
		Utils=require("../util/common");

	function TargetList(){
		this.view=new View();
		this.hashMap={};
	};

	TargetList.prototype={
		init:function(data){
			var model=this;
			this.list=data.deliveryStatistics;
			this.view.load(this.list);
			this.view.on("history:refresh",function(){
				model.view.load(model.list);
			});
		},

		add:function(item){
			var old=this.hashMap[item.orderId];

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

	return TargetList;
});