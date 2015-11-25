define(function(require){

	var 
		$=require("jQuery"),
		Observable=require("../event/observable"),
		tmpl=require("../tmpl/history");

	function View(opt){
		this.cfg=$.extend({
			el:"aside .history-list",
			item:".item",
			limit:5
		},opt);
		$.extend(this,Observable);
		this.init();
	};

	View.prototype={
		init:function(){
			this.el=$(this.cfg.el);
		},

		load:function(data){
			var html=[],
				length=(data.length>this.cfg.limit)?this.cfg.limit:data.length;

			for(var i=0;i<length;i++){
				html.push(tmpl.render(data[i]));
			}
			
			this.el.html(html.join(""));
		},

		add:function(item){
			var html=tmpl.render(item),
				last,
				el=this.el.find(".item[data-id='"+item.orderId+"']");
			if(el.length===0){
				this.el.find(".item:last-child").remove();
				$(html).insertBefore(this.el.find(".item:first-child"));
			}
		},

		remove:function(item){
			var el=this.el.find(".item[data-id='"+item.orderId+"']"),
				view=this;
			if(el.length>0){
				el.remove();
				this.trigger({
					type:"history:refresh"
				});
			}else{
				// this.add(item);
				// setTimeout(function(){view.remove(item)},3000);
			}
		}
	};

	return View;
});