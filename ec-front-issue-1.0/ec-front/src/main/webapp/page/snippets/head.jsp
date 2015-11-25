<%@page pageEncoding="UTF-8"%>
<div class="top_menu" id="wx-nav-bar">
    <div class="wid990">
        <p class="fl" id="wx-nav-welcome">
           		 您好，欢迎来文轩网！
        </p>
        <ul class="signin_up fl" id="wx-nav-signbar">
            <li class="fl sign_in">
              <a  href="http://passport.winxuan.com/signin" title="登录">登录</a>
            </li>
            <li class="fl sign_up">
                <a  href="http://passport.winxuan.com/signup" title="免费注册">免费注册</a>
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