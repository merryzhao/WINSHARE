<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看可货到付款的地区</title>
</head>
<body>
 --%>
	<div>
		<div>
			<span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：</span>
	            <select areaLevel ="country" name="country.id"></select>
	            <select areaLevel ="province" name="province.id"><option value="-1">请选择省份</option></select>
	            <select areaLevel="city" name="city.id"><option value="-1">请选择城市</option></select>
	            <select areaLevel="district" name="district.id" class="district"><option value="-1">请选择区县</option></select>
		</div>
		<div id="view"></div>
	</div>
	
<script src="http://static.winxuancdn.com/javascript/jquery1.5.1.js"></script>
<script src="http://static.winxuancdn.com/js/jquery.validate.min.js"></script>
<script src="http://static.winxuancdn.com/js/areadata.js"></script>
<script src="http://static.winxuancdn.com/js/areaevent.js"></script>
<script type="text/javascript">
// @TODO 以下代码COPY自js/checkout_new.js的前面开始部分, 请重构那里的时候, 处理一下这里
$(function(){
	initArea(23,-1,-1,-1);
	$.ajax({
		type:'GET',
		url:'http://fw.qq.com/ipaddress',
		success:function(){
			if(IPData[1].length==0&&IPData[2].length>0){
				var province=$(areaConstant.province),city=$(areaConstant.city);
				province.find("option").each(function(){
					var el=$(this);
					if(el.text()==IPData[2]){
						province.val(el.val());
						province.trigger("change");
					}
				});
				if(IPData[3].length>0){
					city.find("option").each(function(){
						var el=$(this);
						if(el.text()==IPData[3]){
							city.val(el.val());
							city.trigger("change");
						}
					});
				}
			}else{
				var country=$(areaConstant.country);
					country.find("option").each(function(){
						var el=$(this);
						if(el.text()==IPData[1]){
							country.val(el.val());
							country.trigger("change");
						}
					});
			}
		},
		dataType:"script",
		scriptCharset:"GB2312"
	});	
});
</script>

<script type="text/javascript">

$(function(){
	//根据区县取得运送方式
	$(areaConstant.district).change(function(){
		var di = $(this).val();
		var url = "http://www.winxuan.com/deliveryinfo/area/" + di + ".json";//"http://console.winxuan.com/area/" + di + "/deliveryinfo";
		$.ajax({
			type : 'get', 
			url : url,//这里是接收数据的程序
			data : '', 
			dataType : 'json', 
			success : function(msg) {
				var html = "";
				html += "<table class='delivery_mode'><tr><th>送货方式</th><th>运费收取方式</th><th>运费收取规则</th><th>备注</th></tr>";
				for(var i in msg.deliveryInfo){
					var item = msg.deliveryInfo[i];
					html += "<tr><td>";
					html += item.deliveryType.name;
					html += "</td><td>";
					html += item.deliveryFeeType.name ? item.deliveryFeeType.name : "&nbsp;";
					html += "</td><td>";
					
					var rule = item.deliveryFeeRule.split(',');
					if(rule.length == 1){
						html += "每单"+rule[0]+"元";
					} else {
						html += "按码洋"+rule[0]*100+"%收取， 最高"+rule[1]+"元，最低"+rule[2]+"元";
					}
					html += "</td><td>";
					html += item.description ? item.description : "&nbsp;";
					html += "</td></tr>";
				}
				html += "</table>";
				$('#view').html(html);
			}
		})
		//alert(di);
	});
});
</script>
<%-- 
</body>
</html>
 --%>