<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>货到付款-文轩网</title>
		<jsp:include page="/page/snippets/v2/meta.jsp"><jsp:param value="help" name="type"/></jsp:include>
	</head>
	<body>
	<div class="wrap">
		<jsp:include page="/page/snippets/v2/header.jsp"></jsp:include>
		<jsp:include page="/page/snippets/v2/navigation.jsp">
	 		<jsp:param value="false" name="index" />
		</jsp:include>
			<div class="layout">
			<jsp:include page="help_menu.jsp">
			  	<jsp:param value="help_6" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>货到付款</h3>
      <dl>
      	<h4>支持货到付款地区：</h4>
        <dt><h5>          现金支付</h5>
        <dd><table width="100%" border="0" cellpadding="5" cellspacing="1" class="table">
  <tr>
    <td class="table_td"><span class="fontbold">华东</span></td>
  </tr>
  <tr>
    <td class="table_td2" style="line-height:22px;">
  江苏 南京市 鼓楼区 下关区 雨花台区 栖霞区 白下区 秦淮区 江宁区 <br>
上海 黄浦区 长宁区 闸北区 杨浦区 嘉定区 浦东新区 青浦区 卢湾区 静安区 虹口区 宝山区 金山区 南汇区 徐汇区 普陀区 闵行区 松江区 奉贤区 <br>

  </tr>
</table>
<br>
        </dd>
        <dd>
          <table width="100%" border="0" cellpadding="5" cellspacing="1" class="table">
            <tr>
              <td class="table_td"><span class="fontbold">华北</span></td>
            </tr>
            <tr>
              <td class="table_td2" style="line-height:22px;">
              天津：和平区 南开区 河东区 河西区 河北区 <br>
北京：朝阳区 丰台区 通州区 大兴区 西城区 宣武区  石景山区 门头沟区 昌平区 怀柔区 东城区
崇文区 海淀区 房山区 平谷区<br></td>
            </tr>
          </table>



<br>

        </dd>
        <dd>          <table width="100%" border="0" cellpadding="5" cellspacing="1" class="table">
            <tr>
              <td class="table_td"><span class="fontbold">西南</span></td>
            </tr>
            <tr>
              <td class="table_td2" style="line-height:22px;">
四川省 泸州市 江阳区 龙马潭区 纳溪区<br>
四川省 乐山市 市中区 沙湾区 五通桥区 金口河区<br>
四川省 自贡市 贡井区 大安区 自流井区 <br>
四川省 隧宁市 船山区 安居区 <br>
四川省 雅安市 雨城区 <br>
四川省 成都市 金牛区 成华区 龙泉驿区 都江堰市 郫县 锦江区 武侯区 青白江区 彭州市 双流县 金堂县 青羊区 高新区 新都区 崇州市 大邑县  温江区<br>
四川省 眉山市 东坡区<br>
四川省 宜宾市 翠屏区 <br>
四川省 内江市 市中区<br>
四川省 资阳市 简阳市<br>
四川省 德阳市 什邡市 广汉市 绵竹市 旌阳区<br>
四川省 广元市 元坝区 利州区 朝天区<br>
四川省 绵阳市 江油市 高新区 涪城区 游仙区<br>
四川省 广安市 广安区 市辖区<br>
            </tr>
          </table>
<br>
        </dd>
        <dd>          <table width="100%" border="0" cellpadding="5" cellspacing="1" class="table">
            <tr>
              <td class="table_td"><span class="fontbold">西北</span></td>
            </tr>
            <tr>
              <td class="table_td2" style="line-height:22px;">陕西省 西安市 碑林区 雁塔区</td>
            </tr>
          </table>
<br>
        </dd>
        <dd>          <table width="100%" border="0" cellpadding="5" cellspacing="1" class="table">
            <tr>
              <td class="table_td"><span class="fontbold">中南</span></td>
            </tr>
            <tr>
              <td class="table_td2" style="line-height:22px;">
   广州市：越秀区 白云区 黄埔区 南沙区 东山区 天河区 番禺区 萝岗区 芳村区 荔湾区 海珠区 花都区<br>
海南省 海口市 秀英区 美兰区 琼山区 龙华区<br>
</td>
            </tr>
          </table>
          
<br>
        </dd>
</dl>
<dl class="worm_word">
  <span class="fontbold">温馨提示</span><br>
签收时，请您仔细核兑款项、商品，务必作到货款两清，若事后发现款项错误，我们将无法再核实确认。
</dl>
      </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>