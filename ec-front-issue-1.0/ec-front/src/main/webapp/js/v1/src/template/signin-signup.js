define({
	BASE:'<div id="mini-signin"><div class="wrap">'+
		'<ul class="nav-tab"><li class="selected" tabindex="1">登录</li><li tabindex="2">注册</li></ul>'+
		'<div class="panels"></div>'+
		'<div class="foot-panel">&nbsp;</div>'+
		'<div class="close"><a href="javascript:;">关闭</a></div>'+
		'</div></div>',
	SIGNIN:'<div class="signin-panel"><form name="signin" action="http://passport.winxuan.com/signin" method="GET">'+
		'<fieldset><div><label>帐户</label><input name="name" type="text" class="placeholder" placeholder="帐户名或Email地址"/></div>'+
		'<p class="name tip">请在上方输入您的帐户名/Email</p>'+
		'<div><label>密码</label><input name="password" type="password" class="placeholder" placeholder="您的文轩网密码"/><a href="javascript:;">忘记密码了？</a></div>'+
		'<p class="password tip">请在上方输入您的密码</p></fieldset>'+
		'<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signin_submit">登录</button>'+
		'<b>新用户？</b><a href="javascript:;" bind="user_signup">立即注册</a></div>'+
		'</form><div class="other">'+
		'<p>使用下面合作网站帐号登录文轩网</p>'+
		'<ul><li class="qq"><a href="javascript:;">QQ</a></li>'+
		'<li class="alipay"><a href="javascript:;">支付宝</a></li>'+
		'<li class="sina"><a href="javascript:;">新浪微博</a></li>'+
		'<li class="douban"><a href="javascript:;">豆瓣</a></li></ul>'+
		'</div></div>',
	SIGNUP:'<div class="signup-panel"><form name="signup" action="http://passport.winxuan.com/signup" method="POST"><fieldset>'+
		'<div><label>邮箱</label><input name="name" type="text" class="placeholder" placeholder="电子邮箱/Email地址"/></div>'+
		'<p class="name tip">请输入您的Email地址，作为您在文轩网的登录帐户名</p>'+
		'<div><label>密码</label><input name="password" class="placeholder" type="password" placeholder="请输入您的密码"/></div>'+
		'<p class="password tip">请输入您的文轩网密码</p>'+
		'<div><label>确认密码</label><input name="confirm" class="placeholder" type="password" placeholder="请重复输入上方的密码"/></div>'+
		'<p class="confirm tip">请重复输入上方的登录密码</p>'+
		'<div class="verify"><label>验证码</label><input type="text" name="verify"/><img name="verify"/><a href="javascript:;" bind="reload_verify">换一张</a></div>'+
		'<p class="verify tip">请输入右上方图片中的数字</p>'+
		'<div class="agreement"><input type="checkbox" name="agreement"/><p>我已阅读并同意 <a href="http://www.winxuan.com/help/terms.html">《文轩网交易条款》</a></p></div></fieldset>'+
		'<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signup_submit">立即注册</button></div>'+
		'</form></div>'	
	
});
