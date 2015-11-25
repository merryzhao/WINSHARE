/**
 * 
 * 收藏夹模型沙箱
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define(function(require){
	/**
	 * import 声明
	 */
	var $=require("jQuery"),
		Observable=require("../event/observable"),
		SandboxEvent=require("../event/sandbox"),
		SecurityEvent=require("../event/security"),
		Config=require("../../etc/config");
	
	/**
	 * 构造函数
	 * 配置对象CFG, 只需配置分页参数即可，也可以通过其它方式传递进来
	 * 例如：在页面的DOM元素上配置特定的data-config对象
	 * @param {Object} cfg
	 */
		
	function FavoriteModel(cfg){
		this.apiUrl=Config.apiServer+"/customer/favorite";
		this.opt={
			index:0,
			page:1,
			size:6,
			format:"jsonp"
		};
		$.extend(this.opt,cfg);
		$.extend(this,Observable);
	};
	
	/**
	 * 模型沙箱的动作定义
	 * 
	 * 在事件notify处理中，通过Action的定义，路由至view的特定方法去进行处理
	 * 
	 */
	FavoriteModel.ACTION={
		UPDATE:"update",
		REMOVE:"remove",
		APPEND:"append",
		LIST:"list"
	};
	
	FavoriteModel.prototype={
		
		/**
		 * 收藏夹新增商品
		 * @param {Object} list
		 */
		
		append:function(list){
			var url=[this.apiUrl+"/add?"],
				model=this;
			$.each(list,function(){
				url.push("p="+this);
			});
			url.push($.param(this.opt));
			url.push("callback=?");
			
			$.ajax({
				url:url.join("&"),
					
				success:function(data){
					model.trigger({
						type:SandboxEvent.NOTIFY,
						action:FavoriteModel.ACTION.APPEND,
						eventData:{
							list:list,
							result:data
						}
					});
				},
				/**
				 * 错误处理
				 * @param {Object} xhr
				 */
				error:function(xhr){
					if(xhr.status==401){
						model.trigger({
							type:SecurityEvent.RESOURCES_PROTECTED
						});
					}
				},
				
				dataType:"jsonp"
			});
		},
		
		/**
		 * 更新收藏夹标识，在购物车重构中，不会使用此方法，暂未实现
		 */
		update:function(){
			throw new Error("TODO FavoriteModel.update()");
		},
		
		/**
		 * 移除收藏商品，与update类似，暂未实现
		 */
		
		remove:function(){
			throw new Error("TODO FavoriteModel.remove()");
		},
		
		/**
		 * 取得收藏夹列表
		 * @param {Object} pageOpt
		 */
		
		list:function(pageOpt){
			var url=[],model=this,
				pageOpt=$.extend(this.opt,pageOpt);
				url.push($.param(pageOpt));
				url.push("callback=?");
				
			$.ajax({
				url:this.apiUrl+"?"+url.join("&"),
				success:function(data){
					model.trigger({
						type:SandboxEvent.NOTIFY,
						action:FavoriteModel.ACTION.LIST,
						eventData:{
							result:data
						}
					});
				},
				error:function(xhr){
					if(xhr.status==401){
						model.trigger({
							type:SecurityEvent.RESOURCES_PROTECTED
						});
					}
				},
				dataType:"jsonp"
			});
		}
	};
	return FavoriteModel;
});
