define(function(require){

	var 
		$=require("jQuery"),
		
		MSG_TYPE={
			DIST:"已付款",
			SENT:"已发出",
			COMPLETE:"交易完成",
			PART:"部份发货"
		};


	function StatusBar(opt){
		this.cfg=$.extend({
			el:"footer div"
		},opt)
	};

	StatusBar.prototype={
		init:function(){
			this.el=$(this.cfg.el);
		},

		dist:function(data){
			this.el.html(data.orderId+MSG_TYPE.DIST);
		},

		sent:function(data){
			this.el.html(data.orderId+MSG_TYPE.SENT);
		},

		complete:function(data){
			this.el.html(data.orderId+MSG_TYPE.COMPLETE);
		},

		part:function(data){
			this.el.html(data.orderId+MSG_TYPE.PART);
		}
	};

	return StatusBar;
});