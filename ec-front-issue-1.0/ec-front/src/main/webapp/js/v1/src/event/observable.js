/**
 * 
 * 抽象化的observable接口实现，
 * model&view 只需简单继承此抽象化对象即可使对象可观查化可侦听化
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */

define(function(require){
	
	var
	 
	$=require("jQuery"),
	
	/**
	 * 在第一版的设计定义，暂时采用对jQuery的原生方法进行proxy处理
	 * 其它解决方案也正在验证中
	 * 
	 * 通过method.call(obj,args...), 或者method.apply(obj,[args])方式，
	 * 读起来总感觉很怪异
	 * 
	 */
	
	AbstractObservable={
		
		/**
		 * 事件触发实现
		 */
		
		trigger:function(){
		
			var proxy=$(this);
			proxy.trigger.apply(proxy,arguments);
		
			return this;
		},
		
		/**
		 * 事件捕获实现
		 */
		
		on:function(){
			
			var proxy=$(this);
				proxy.on.apply(proxy,arguments);
				
			return this;
		}
	};
	
	return AbstractObservable;
});