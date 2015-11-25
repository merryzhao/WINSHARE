<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/cache" prefix="cache"%>
<div class="top_menu">
	<div class="wid990">
		<p class="fl top_favorite"><b></b><a href="javascript:void(0);" class="add_favorite link4">收藏文轩网</a></p>
		<ul class="top_right_menu fr">
			<%--<li class=""><a class="link4" href="javascript:;" title="购物记录">购物记录</a></li>--%>
			<li class="first"><a class="link4"
				href="http://www.winxuan.com/customer/order" title="我的订单" target="_blank">我的订单</a></li>
			<li bind="myaccount"><a class="link4"
				href="http://www.winxuan.com/customer" title="我的文轩" target="_blank">我的文轩</a></li>
			<li bind="helps"><a class="link4"
				href="http://www.winxuan.com/help/help_center.html" title="帮助中心"
				target="_blank">帮助中心</a></li>
			<li><span class="ser_phone fb">400-702-0808</span></li>
			<%--<li class="last app_mobile"><p class="top_mobile">手机文轩</p>
				<ul class="td_code_list">
					<li class="first">
						<b class="android">Android版</b>
						<a href="${attrList.android.updateAddress }" class="android_d download">立即下载</a>
						<p>手机登录winxuan.com下载</p>
						<p class="tal">二维码下载：</p>
						<a href="${attrList.android.updateAddress }" class="td_code"><img data-lazy="false" src="http://static.winxuancdn.com/upload/version/android.png" width="90" height="90" style="margin: 0;border: none" /></a>
					</li>
					<li>
						<b class="iphone">iPhone版</b>
						<a href="${attrList.ios.updateAddress }" class="app_d download" target="_blank">App Store下载</a>
						<p>手机登录winxuan.com下载</p>
						<p class="tal">二维码下载：</p>
						<a href="${attrList.ios.updateAddress }" class="td_code" target="_blank"><img data-lazy="false" src="http://static.winxuancdn.com/upload/version/ios.png" width="90" height="90" style="margin: 0;border: none" /></a>
					</li>
				</ul>
			</li>--%>
		</ul>
		<ul class="signin_up fr">
			<li class="fl"><a href="https://passport.winxuan.com/signin"
				title="登录" target="_blank">登录</a></li>
			<li class="fl"><a href="https://passport.winxuan.com/signup"
				title="免费注册" target="_blank">免费注册</a></li>
		</ul>
		<p class="fr">您好，欢迎光临文轩网！</p>
		<dl class="user_menus helps">
			<dt>
				<a href="http://www.winxuan.com/help/help_center.html" target="help">帮助中心</a>
			</dt>
			<dd>
				<a
					href="http://www.winxuan.com/help/delivery_info_dynamic_query.html"
					target="help">配送时间</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/help/payment_online.html"
					target="help">支付方式</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/returnorder"
					target="help">我要退换货</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/complaint"
					target="help">投诉中心</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/question"
					target="help">文轩客服</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/order"
					target="help">订单查询</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/help/invoice.html" target="help">发票制度</a>
			</dd>
			<dd>
				<a class="link4" href="http://www.winxuan.com/giftcard" target="_blank">礼品卡</a>
			</dd>
		</dl>
		<dl class="user_menus myaccount">
			<dt>
				<a href="http://www.winxuan.com/customer" target="_blank">我的文轩</a>
			</dt>
			<dd>
				<a href="http://www.winxuan.com/customer/order" target="_blank">我的订单</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/favorite" target="_blank">我的收藏</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/question" target="_blank">我的咨询</a>
			</dd>
			<dd>
				<a href="http://www.winxuan.com/customer/comment/my" target="_blank">我的评价</a>
			</dd>
		</dl>
	</div>
</div>
<!--
<div id="bn_index" style="width:990px; margin:2px auto;">
	<a href="http://www.winxuan.com/topic/subject/yiyuecuxiao/zhuhuichang/index.html" target="_blank" style="display: block; height: 56px;">
		<img src="http://static.winxuancdn.com/topic/subject/yiyuecuxiao/dingbu/banner-1.jpg" width="990" height="56" style="margin: 0;border: none" />
	</a>
</div>
-->
<div id="customerUpdatePwd" class="customerUpdatePwd" style="display:none;">尊敬的用户:您的密码目前存在安全隐患,为了保证您的账号安全，请立即<a href="http://www.winxuan.com/customer/updatePassword">修改密码</a></div>

<div class="header">

	<div class="logo_box">
		<a title="文轩网,新华书店" href="http://www.winxuan.com/?analytics=index_one"><img alt="文轩网"
			src="http://static.winxuancdn.com/css2/images/logo.png"
			data-lazy="false"> </a>
	</div>
	<div class="other_menu">
		<div class="shop_cart">
			<ul>
				<li><a class="split_line link4"
					href="http://www.winxuan.com/shoppingcart" target="_blank">去结算</a>
				</li>
				<li><span class="down_arrow">购物车（<b class="red">0</b>）件</span>
				</li>
				<li class="first"></li>
			</ul>
		</div>
		<cache:fragmentPageCache idleSeconds="86400"	key="FRAGMENT_51">
			<c:import url="/fragment/51" >
				<c:param value="${cacehKey}" name="cacheKey"/>
			</c:import>
		</cache:fragmentPageCache>
	</div>
	<div id="nav_mini_cart" style="display: none;">
		<div class="cart-header"></div>
		<div class="mini_cart">
			<p class="empty_tip">正在更新您的购物车...</p>
		</div>
	</div>
	<ul class="mainmenu ${param.label}" id="head-nav">
		<li class="nav:home"><a class="link4"
			href="http://www.winxuan.com/?analytics=index_one">首页</a></li>
		<li class="nav:book"><a class="link4"
			href="http://www.winxuan.com/book/?analytics=index_book">图书</a></li>
		<li class="nav:media"><a class="link4"
			href="http://www.winxuan.com/media/?analytics=index_media">音像</a></li>
		<li class="nav:mall"><a class="link4"
			href="http://www.winxuan.com/mall/?analytics=index_mall">百货</a></li>
		<li class="last_menu nav:ebook"><a class="link4"
			href="http://ebook.winxuan.com">电子书</a></li>
	</ul>
    <div class="header_quickmark">
        <div class="header_quickmark_wx">
            <a href="http://www.winxuan.com/help/quickmark_wenxuan.html" target="_blank"><img src="http://static.winxuancdn.com/help/images/quickmark_wenxuan.jpg" data-lazy="false"/><span>文轩手机购物</span></a>
        </div>
        <div class="header_quickmark_jy">
            <a href="http://ebook.winxuan.com/cms/download" target="_blank"><img src="http://static.winxuancdn.com/help/images/quickmark_jiuyue.jpg" data-lazy="false"/><span>电子书客户端</span></a>
        </div>
    </div>
	<div class="header_redbg">
		<form name="searchForm" action="http://search.winxuan.com/search"
			method="get">
			<div class="select">
				<span class="select_category_name">全部分类</span>
				<div class="select_pop">
					<a href="javascript:void(0);" data-key="">全部分类</a>
					<a href="javascript:void(0);" data-key="11001">图书</a>
					<a href="javascript:void(0);" data-key="11002">音像</a>
					<a href="javascript:void(0);" data-key="11004">电子书</a>
					<a href="javascript:void(0);" data-key="11003">百货</a>
				</div>
			</div>
			<input class="search_words" name="keyword" type="text" value=""
				autocomplete="off" />
			<input type="hidden" name="type" value="" />
			<button class="search_but" type="submit">搜索</button>
		</form>
		<p class="hot_search">
			<a href="http://search.winxuan.com/advanceSearch">高级搜索</a><a
				href="http://search.winxuan.com/">热搜</a>：
		</p>
	</div>
</div>
<div class="menuWrap">
	<div class="left_menu">
		<c:set var="expandable" value="true" />
		<c:if test='${param.expandable=="0"}'>
			<c:set var="expandable" value="false" />
		</c:if>
		<c:set var="menuTitleClass" value="" />
		<c:set var="styleDisplay" value="" />
		<c:if test="${expandable}">
			<c:set var="menuTitleClass" value="class='unfold'" />
			<c:set var="styleDisplay" value="style='display:none;'" />
		</c:if>
		<h3>
			<a ${menuTitleClass} href="javascript:;">全部商品分类</a>
		</h3>
		<div class="menu_content">内容继续完善中...</div>
		<dl ${styleDisplay} data-expandable="${expandable}">
	        <c:import url="http://www.winxuan.com/category/book/nav"/>
			<dd data-index="9">
				<h2>
					<a href="http://list.winxuan.com/7786">音像</a>
				</h2>
				<p>
					<a href="http://list.winxuan.com/7791" title="音乐">音乐</a><a
						href="http://list.winxuan.com/7788" title="影视">影视</a><a
						href="http://list.winxuan.com/7790" title="软件">软件</a><a
						href="http://list.winxuan.com/7789" title="教学">教学</a>
				</p>
			</dd>
			<dd data-index="11">
				<h2>
					<a href="http://list.winxuan.com/1?onlyEBook=true">电子书</a>
				</h2>
				<p>
					<a href="http://list.winxuan.com/505?onlyEBook=true" target="_blank" title="历史">历史</a><a
						href="http://list.winxuan.com/4429?onlyEBook=true" target="_blank" title="文学">文学</a><a
						href="http://list.winxuan.com/4098?onlyEBook=true" target="_blank" title="互联网">互联网</a><a
						href="http://list.winxuan.com/3676?onlyEBook=true" target="_blank" title="传记">传记</a>
				</p>
			</dd>
			<dd data-index="10">
				<h2>
					<a href="http://list.winxuan.com/7787/">百货</a>
				</h2>
				<p>
					<a href="http://list.winxuan.com/7971" title="家电">家电</a><a
						href="http://list.winxuan.com/8000" title="美妆">美妆</a><a
						href="http://list.winxuan.com/7974" title="家居">家居</a><a
						href="http://list.winxuan.com/8004" title="母婴">母婴</a>
				</p>
			</dd>
			<dt>
				<a href="http://www.winxuan.com/catalog_book.html" target="_blank">全部图书</a>/<a
					href="http://www.winxuan.com/catalog_media.html" target="_blank">音像</a>/<a
					href="http://www.winxuan.com/catalog_ebook.html" target="_blank">电子书</a>/<a
					href="http://www.winxuan.com/catalog_mall.html" target="_blank">百货分类</a>
				&gt;&gt;
			</dt>
		</dl>
	</div>
</div>

<c:import url="http://www.winxuan.com/fragment/3003"/>
<script>
	seajs.use("header", function(header) {
		header.init();
	})
</script>