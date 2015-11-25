/**
 * 
 * 购物车模型沙箱
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */

define(function(require){
	/**
	 * import 相关依赖
	 */
	
	var $=require("jQuery"),
		Observable=require("../event/observable"),
		Config=require("../../etc/config"),
		SandboxEvent=require("../event/sandbox");
	
	function CartModel(){
		this.apiUrl=Config.apiServer+"/shoppingcart";
		this.format="format=jsonp";
		this.option="opt=update";
		this.callback="callback=?";
		this.removeParam="qty=0";
		$.extend(this,Observable);
	};
	
	CartModel.ACTION={
		UPDATE:"update",
		REMOVE:"remove",
		APPEND:"append",
		QUERY:"query",
		MERGE:"merge"
	};
	
	CartModel.prototype={
		
		update:function(id,quantity,refresh){
			var url=[this.apiUrl+"/modify?p="+id],
				model=this;
				
			url.push(this.format);
			url.push(this.option);
			url.push(this.callback);
			url.push("qty="+quantity);
				
			$.getJSON(url.join("&"),function(data){
				model.trigger({
					type:SandboxEvent.NOTIFY,
					action:CartModel.ACTION.UPDATE,
					eventData:{
						sourceId:id,
						quantity:quantity,
						refresh:refresh,
						result:data
					}
				});
			});			
		},
		
		remove:function(list){
			var url=this.apiUrl+"/modify?",
				model=this,
				param=[];
				
			if(list.length>0){
				for(var i=0;i<list.length;i++){
					param.push("p="+list[i]);
					param.push(this.removeParam);
				}
				param.push(this.format);
				param.push(this.callback);
				param.push(this.option);
				$.getJSON(url+param.join("&"),function(data){
					model.trigger({
						type:SandboxEvent.NOTIFY,
						action:CartModel.ACTION.REMOVE,
						eventData:{
							itemList:list,
							result:data
						}
					});
				});
			}else{
				throw new Error("Parameter list can not be empty or null!");
			}
		},
		
		append:function(id,quantity){
			var quantity=quantity||1,model=this,
				url=[this.apiUrl+"/modify?p="+id];
				
				url.push(this.format);
				url.push(this.callback);
				url.push("qty="+quantity);
				
			$.getJSON(url.join("&"),function(data){
				model.trigger({
					type:SandboxEvent.NOTIFY,
					action:CartModel.ACTION.APPEND,
					eventData:{
						sourceId:id,
						result:data
					}
				});
			});
		},
		
		query:function(){
		    var model=this,
                url=[this.apiUrl+"/queryShoppingcart?"];
             
                url.push(this.format);
                url.push(this.callback);
                
            $.getJSON(url.join("&"),function(data){
                model.trigger({
                    type:SandboxEvent.NOTIFY,
                    action:CartModel.ACTION.QUERY,
                    eventData:{
                        result:data
                    }
                });
            });
		},
        
        merge:function(action){
            var model=this,
                url=[this.apiUrl+"/merge?"];
                url.push("opt="+action);
                url.push(this.format);
                url.push(this.callback);
                
            $.getJSON(url.join("&"),function(data){
                model.trigger({
                    type:SandboxEvent.NOTIFY,
                    action:CartModel.ACTION.MERGE,
                    eventData:{
                        result:data
                    }
                });
            });
        }
	};
	return CartModel;
});
