seajs.use("jQuery",function($){
	var prices = document.getElementsByName("price");
var quantitys = document.getElementsByName("quantity");
var cNames = document.getElementsByName("cname");
var sorts = document.getElementsByName("sort");
var vendors =  document.getElementsByName("vendor");
var agent = document.getElementById("agent").value;
var goods = "GOODS/";
var merchandise = "baihuo/"
var params = "";
var b = false;
for (i = 0; i < prices.length; i++) {
    if (b) {
        params+="/:";
    }
    if(sorts[i].value=="3" || vendors[i].value !="1")
        params+=merchandise;
    else
        params+=goods;
    params+=prices[i].value+"/";
    params+=quantitys[i].value+"/";
    params+=encodeURI(cNames[i].value);
    b = true;
}
var url ="http://count.chanet.com.cn/ec.cgi?t=3295"+"&i="+orderid+"&u="+registername+"&o="+params+"";
if (agent.value != 6) {
	(new Image()).src=url;
}

});
