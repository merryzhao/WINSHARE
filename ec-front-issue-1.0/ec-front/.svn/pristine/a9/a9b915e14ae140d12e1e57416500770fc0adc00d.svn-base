<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="top_menu" id="wx-nav-bar">
    <div class="wid990">
        <p class="fl" id="wx-nav-welcome">
           		 您好，欢迎来文轩网！
        </p>
        <ul class="signin_up fl" id="wx-nav-signbar">
            <li class="fl sign_in">
                <a href="http://passport.winxuan.com/signin" title="登录">登录</a>
            </li>
            <li class="fl sign_up">
                <a href="http://passport.winxuan.com/signup" title="免费注册">免费注册</a>
            </li>
        </ul>
        <ul class="top_right_menu fr">
            <li class="first">
                <a href="http://www.winxuan.com/" title="文轩网首页">文轩网首页</a>
            </li>
            <li>
                <a href="http://www.winxuan.com/customer" title="我的帐户" bind="homePage">我的帐户</a>
            </li>
            <li class="have_more red" id="wx-bar-shopping-cart">
                <span class="cart-mouse-hot">购物车<b id="cart-count">0</b>件</span>
                <div id="mini-cart">
                </div>
            </li>
            <li class="have_more">
                <span>收藏夹</span>
            </li>
            <li class="have_more">
                <span>网站导航</span>
            </li>
        </ul>
    </div>
</div>
<script>
;(function(m, o, d, u, l, a, r) {
	  if(m[d]) return;
	  function f(n, t) { return function() { r.push(n, arguments); return t; } }
	  m[d] = a = { args: (r = []), config: f(0, a), use: f(1, a) };
	  m.define = f(2);
	  u = o.createElement('script');
	  u.id = d + 'node';
	  u.src = 'http://static.winxuancdn.com/libs/sea.js';
	  l = o.getElementsByTagName('head')[0];
	  l.insertBefore(u, l.firstChild);
	})(window, document, 'seajs');
	seajs.config({
		"base":"http://static.winxuancdn.com/libs/",
		alias:{
			"jQuery":"jQuery/1.6.2/jQuery",
			"cart":"core/cart",
			"favorite":"core/favorite",
			"config":"module/config",
			"widgets":"module/portal-widgets",
			"winxuan-bar":"module/winxuan-bar",
			"widgets.css":"http://static.winxuancdn.com/css/widgets.css"
		}
	});
	seajs.use("widgets",function(widgets){
		widgets.init();
	});
</script>
<div class="header">
    <div class="logo_box"><a title="文轩网,新华书店" href="http://www.winxuan.com/"><img alt="文轩网" src="${serverPrefix}images/logo.gif" width="188" height="46"></a></div>
    <div class="header_redbg">
    
    <form action="http://search.winxuan.com/search" method="GET" id="search_form">
        <input id="keyword" class="search_words" name="keyword" type="text" value="${param.keyword }">
        <input type="submit" class="search_but">
    </form>
        <p class="hot_search"><a href="#">高级搜索</a> | <a href="http://search.winxuan.com">热搜：</a> <a class="haveline" href="#">江山</a> <a class="haveline" href="#">厚黑需</a> <a class="haveline" href="#">说文解字</a> <a class="haveline" href="#">西方</a></p>
        <ul class="left_menu" style="display:none;">
            <li><strong class="lm1"><a href="#" title="小说">小说</a></strong></li>
            <li class="hover_on"><strong class="lm2"><a href="#" title="文化">文化</a></strong></li>
            <li><strong class="lm3"><a href="#" title="科技">科技</a></strong></li>
            <li class="hover_on"><strong class="lm4"><a href="#" title="少儿">少儿</a></strong></li>
            <li><strong class="lm5"><a href="#" title="人文社科">人文社科</a></strong></li>
            <li class="hover_on"><strong class="lm6"><a href="#" title="管理">管理</a></strong></li>
            <li><strong class="lm7"><a href="#" title="生活">生活</a></strong></li>
            <li class="hover_on"><strong class="lm8"><a href="#" title="文化教育">文化教育</a></strong></li>
            <li><strong class="lm9"><a href="#" title="音像">音像</a></strong></li>
            <li class="hover_on"><strong class="lm10"><a href="#" title="百货">百货</a></strong></li>
            <li><strong><a href="#" title="全部分类">全部分类</a></strong></li>
        </ul>
    </div>
    <ul class="mainmenu">
        <li class="nav_on"><span>首页</span></li>
        <li><span>图书</span></li>
        <li><span>音像</span></li>
        <li><span>百货</span></li>
        <li><span>电子书</span></li>
    </ul>
</div>