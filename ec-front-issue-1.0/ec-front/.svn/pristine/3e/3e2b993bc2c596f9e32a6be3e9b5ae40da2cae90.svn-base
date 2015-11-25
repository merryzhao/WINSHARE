<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
	限时模块使用注意事项:
	1. 在bind='limit'的元素须要加上current和end参数
	2. current为服务器滴当前时间 类型为Long型
	3. end为商品的结束时间 类型为Long型
	4. 未完的工作如： 某商品时间到期后限时模块需重新加载
--%>
<div class="limit_buy">
   <div class="fl current_goods">
        <dl class="buy_top">
            <dt>限时抢购</dt>
            <dd><a class="current_no">1</a><a>2</a><a>3</a></dd>
        </dl>
        <a class="pre_page" href="javascript:;"></a><a class="next_page" href="javascript:;"></a>
        <c:import url="/fragment/55"/>
    </div>
    <c:import url="/fragment/56"/>
</div>
<script type="text/javascript" charset="utf-8">
    seajs.use(['jQuery','roller','limit'],function($,Roller,Limit){
    	var context=$("div.limit_buy");
    	Roller({
    		context:context,
    		paging:true,
    		page:3,
    		auto:true,
    		selector:{
    			container:"div.current_goods",
    			page:"dl.buy_top a",
    			next:"a.next_page",
    			prev:"a.pre_page",
    			box:"div.goods_list",
    			items:"dl"
    		},
    		className:{pageSelected:"current_no"}
    	});
    	Limit({context:context});
    	var list=context.find("div.other_pro dd");
    	list.mouseover(function(){list.removeClass("dt");$(this).addClass("dt");});
    });
</script>
