/**
 * 
 * 对shoppingcart返回的JSON对象进行包装
 * 提供一些实用的方法，避免上层应用进行调用时，
 * 需要对JSON对象属性值，进行过多的判断
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define(function(require){
	var $=require("jQuery");
	/**
	 * 构造函数
	 * @param {Object} data 原始返回的JSON包
	 * @param {Object} template 模板对象,将对应的返回状态转义为可读的文字描述模板
	 */
	
	function CartDataWrap(data,template){
		this.data=data;
		this.template=template||{};
		this.init();
	};
	
	/**
	 * 状态含义定义
	 */
	CartDataWrap.Status={
		"1":"success",
		"2":"error",
		"3":"unavailable",
		"4":"offshelf",
		"5":"number",
		"6":"param",
		"7":"limit"
	};
	
	/**
	 * 
	 * 下一版中，将使用模板引擎，在本次重构中，暂不引入
	 *  
	 */
	CartDataWrap.PromTemplate={
		"20004": {
			style:"save",
            promName: "<h3>以下活动商品已购满{minMoney}元，已减{amount}元现金 </h3><span class='highlight'>&yen;<em>{savePrice}</em></span>",
            hasMet: "",
            willBeMet: "<h3>再购买活动商品{needMoney}元，可减{moreSaveMoney}元现金</h3>"
        },
        "20005": {
			style:"send",
            promName: "本订单赠{amount}元礼券",
            hasMet: "",
            willBeMet: "再购买{needMoney}元商品，可获赠{moreSaveMoney}元礼券"
        },
        "20006": {
			style:"delivery",
            promName: "已免{amount}元运费",
            hasMet: "",
            willBeMet: "再购买{needMoney}元商品，可免{moreSaveMoney}元运费"
        },
		"20008":{
			style:"single",
			promName: "购买指定活动商品已满{minMoney}元，立减{amount}元现金",
            hasMet: "",
            willBeMet: "再购买指定活动商品{needMoney}元，可减{moreSaveMoney}元现金"
		}
	};
	
	/**
	 * 
	 * 静态方法
	 * 合并数据和模板
	 * 下次重构时，可移除此方法
	 * 
	 * @param {Object} prom
	 */
	CartDataWrap.getPromMessage=function(prom){
		var hasMet=false,html,msg=[],
			template=CartDataWrap.PromTemplate[prom.promType];
		if(prom.saveMoney>0){
			hasMet=true;
			html=template.promName.replace(/\{minMoney\}/g, prom.minMoney).
				replace(/\{amount\}/g, prom.saveMoney).
				replace(/\{savePrice\}/g,prom.saveMoney.toFixed(2));
				html += template.hasMet.replace(/\{saveMoney\}/g, prom.saveMoney);
			msg.push(html);
		}
		if(prom.needMoney>0){
			html=template.willBeMet.replace(/\{needMoney\}/g, prom.needMoney.toFixed(2));
            html = html.replace(/\{moreSaveMoney\}/g, prom.moreSaveMoney);
            msg.push(html);
		}
		return msg.join("");
	};
	
	/**
	 * 
	 */
	CartDataWrap.prototype={
		
		init:function(){
			this.id=this.data.sourceId;
			this.quantity=this.data.quantity;
			this.refresh=this.data.refresh;
		},
		
		getPoints:function(){
			if((!this.data)||(!this.data.result)){
				return null;
			}
			var list=this.data.result.shoppingcart.itemList,
				item;
			for(var i=0;i<list.length;i++){
				item=list[i];
				if(item.productSaleId==this.id){
					return item.points;
				}
			}
			return null;
		},
		
		getUpdateItem:function(){
			if(!this.id){
				return null;
			}
			var list=this.data.result.shoppingcart.itemList,
				item;
			for(var i=0;list.length;i++){
				item=list[i];
				if(item.productSaleId==this.id){
					return item;
				}
			}
			return null;
		},
		
		getCount:function(){
			if((!this.data)||(!this.data.result)){
				return null;
			}
			return this.data.result.shoppingcart.count;
		},
		
		getProms:function(shopId,supplyType){
			var promList=this.data.result.shoppingcart.proms,
				result=[],
				prom;
			if(arguments.length<2){
				result=promList;
			}else{
				if(promList){
					for(var i=0;i<promList.length;i++){
						prom=promList[i];
						if(prom.shopId==shopId&&prom.supplyTypeCode==supplyType){
							result.push(prom);
						}
					}
				}
			}
			return result;
		},
		
		getSaveMoney:function(){
			var money=0;
			if(this.data.result.shoppingcart.saveMoney){
				money=this.data.result.shoppingcart.saveMoney;
			}
			return money.toFixed(2);
		},
		getBriefPrice:function(){
			return this.data.result.shoppingcart.salePrice.toFixed(2);
		},
		getSalePrice:function(){
			return this.data.result.shoppingcart.salePrice.toFixed(2);
		},
		getCountPrice:function(){
			return (this.getSalePrice()-this.getSaveMoney()).toFixed(2);
		},
		getStatusString:function(){
			if((!this.data)||(!this.data.result)){
				return null;
			}
			return CartDataWrap.Status[this.data.result.status];
		},
		getStatusInt:function(){
			return this.data.result.status;
		},
		getAvailable:function(){
			return this.data.result.avalibleQuantity;
		},
		
		/**
		 * 
		 * 更新消息
		 * 
		 */
		getMessage:function(){
			var stString=this.getStatusString(),
				stInt=this.getStatusInt(),
				key=stString.toUpperCase(),
				msg=this.template[key];
			if(stInt===1){
				msg=msg.replace(/\{count\}/,this.getCount());
				msg=msg.replace(/\{salePrice\}/,this.getSalePrice());
			}else if(stInt===3){
				msg=msg.replace(/\{avalibleQuantity\}/,this.getAvailable());
			}
			return msg;
		},
		
		getHistoryList:function(){
		    if((!this.data)||(!this.data.result)){
                return null;
            }
            return this.data.result.itemList;
		},
		getContent:function(){
		    var quantity=0,list="",content=this.template.CONTENT,obj=this;
		    $.each(this.getHistoryList(), function(){
		        var item=obj.template.ITEM;
                item=item.replace(/\{url\}/g,this.url);
                item=item.replace(/\{name\}/g,this.name);
                item=item.replace(/\{sellName\}/g,this.sellName);
                item=item.replace(/\{imageUrl\}/g,this.imageUrl);
                item=item.replace(/\{salePrice\}/g,this.salePrice.toFixed(2));
                item=item.replace(/\{listPrice\}/g,this.listPrice.toFixed(2));
                quantity+=this.quantity;
                list+=item;
            });
		    content=content.replace(/\{quantity\}/g,quantity);
		    content=content.replace(/\{list\}/g,list);
		    return content;
		}
	};
	return CartDataWrap;
});
