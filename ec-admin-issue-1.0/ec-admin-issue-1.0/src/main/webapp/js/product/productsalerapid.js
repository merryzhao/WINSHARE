$(function(){
    window.rapid = {
    		edit:function(){
    			window.location.href="/productsalerapid/edit/"+$("#pid").val();
    		},
            submit : function(){
                rapid.checkcheckProductSaleId();
                if (!/\d+/.test($("input[name=amount]").val())){
                	alert("输入采购量,并且保证为正整数");
                	return false;
                }
                return true;
            },
            searchSubmit : function(){
            	if(!rapid.checkProductSaleId()){
            		return false;
            	}
                return true;
            },
            checkProductSaleId : function(){
            	if (!/\d+/.test($("input[name=productSaleId]").val())) {
                    alert("输入正确格式的商品ID");
                    return false;
                }
            	return true;
            }
    };
});