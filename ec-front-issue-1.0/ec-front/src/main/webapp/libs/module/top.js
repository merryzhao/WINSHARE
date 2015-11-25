
define(function(require){
    var $=require("jQuery"),
        login=require("login"),
        conf=require("config"),
        qqHead=$('<div class="qqHead"><div class="qq_wrap"><span class="cb_logo"></span><span class="cb_headshow"></span><div class="cb_jifen"><span class="cb_showmsg"></span><span class="cb_jifenurl"><a target="_blank" href="http://cb.qq.com/my/my_jifen_source.html">我的积分</a></span></div></div></div>'),
        webApp='<div class="top_mobileTips">亲爱的用户，文轩网推出<span class="version">{os}</span>啦！欢迎你下载体验<a href="{address}" class="download" _target="blank">立即安装</a></div>',
        userPwd = '<div id="customerUpdatePwd" class="customerUpdatePwd" >尊敬的用户:您的密码目前存在安全隐患,为了保证您的账号安全，请立即<a href="http://www.winxuan.com/customer/updatePassword">修改密码</a></div>',
        top={
            selector:{
                top:".top_menu",
                sign:".signin_up",
                welcome:"p.fr",
                rightMenu:"ul.top_right_menu",
                menuList:"dl.user_menus",
                favorite:".add_favorite",
                mobileCode:"li.app_mobile",
                codeList:".td_code_list",
                androidUrl:"a.android_d",
                iosUrl:"a.app_d",
                download:"a.download"
            },
            cfg:{
                checkUrl:conf.portalServer+"/customer/status?mode=1&format=jsonp&callback=?"
            },
            escapeHTML:function (str) {                                       
              return str.replace(/&/g,'&amp;').replace(/>/g,'&gt;').replace(/</g,'&lt;').replace(/"/g,'&quot;');
            },
            check:function(){
                var visitor=this.getVisitor();
                if(!!visitor){
                    top.welcome(visitor,false);
                }
                top.tip(!!visitor);
                var qqHeadShow=this.getCookie("qqHeadShow");
                if(qqHeadShow){
                    top.loadHeadShow(qqHeadShow);
                }
            },
            loadHeadShow:function(msg){
                $(function(){
                    qqHead.find(".cb_headshow").html(msg);
                    qqHead.find(".cb_showmsg").html(top.getVisitor());
                    $("body>div:first").before(qqHead);
                });
            },
            loadWebApp:function(){
                var userAgent=navigator.userAgent.toLowerCase();
                if(userAgent.indexOf("android")>0){
                    webApp=webApp.replace(/\{os\}/g, "Android版");
                    webApp=webApp.replace(/\{address\}/g, conf.androidAPK);
                    this.addAd($("body>div:first"));
                }else if(userAgent.indexOf("iphone")>0||userAgent.indexOf("ipad")>0){
                    webApp=webApp.replace(/\{os\}/g, "IOS版");
                    webApp=webApp.replace(/\{address\}/g, conf.iosAPK);
                    this.addAd($("body>div:first"));
                }
            },
            addAd:function(div){
            	if(div.attr('class')=='wrap')
            		$('.site_notice').after(webApp);
             	else
             		div.before(webApp);
            },
            loadUserPwd:function(){
                $.getJSON(this.cfg.checkUrl,function(data){
                      if(data.isUpdate){
                          var customerUpdatePwd = $("div.customerUpdatePwd"); 
                          customerUpdatePwd.show();  
                      }
                });
                
            },
            reload:function(){
                $.getJSON(this.cfg.checkUrl,function(data){
                    if(!!data.visitor){
                        top.welcome(data.visitor,data.isLogin);
                    }
                    top.tip(!!data.visitor);
                });
            },
            getCookie:function(name,isEscape){
                var cookies=document.cookie.split(";"),
                    temp,
                    isEscape=isEscape||false;
                for(var i=0;i<cookies.length;i++){
                    temp=cookies[i].split("=");
                    if($.trim(temp[0])==name){
                        if(isEscape){
                            return this.escapeHTML(decodeURIComponent(temp[1]).replace(/\+/g," ")); 
                        }else{
                            return decodeURIComponent(temp[1]).replace(/\+/g," ");
                        }
                    }
                }
                return null;
            },
            setCookie:function(name,value){
                var Days = 30;
                var exp = new Date();
                var path="/";
                var domain=".winxuan.com";
                exp.setTime(exp.getTime() + Days*24*60*60*1000);
                document.cookie = name + "="+ encodeURIComponent(value) + ";expires=" + exp.toGMTString()
                                  + "; path=" + path+";domain="+domain;
            },
            getVisitor:function(){
                var visitor=this.getCookie("v");
                if(!!visitor)
                {
                    return visitor;
                }
                else
                {
                    top.getorthercookie();
                }
                
            },
            getUserId:function(){
                var uid=this.getCookie("p"),uids;
                if(uid){
                    uids=uid.split("&");
                    return uids[0];
                }    
            },
            welcome:function(name,isLogin){
                var el=this.el.find(this.selector.welcome),
                    txt=el.text(),vsName=el.find("[bind=vsName]"),
                    html=[],name=name.length<10?name:name.slice(0,8)+"...";
                    if(vsName.length==0){
                        html.push("<a href='");
                        html.push(conf.portalServer+"/customer");
                        html.push("' target='_blank' bind='vsName'>"+name+"</a>");
                        html.push(txt);
                        el.html(html.join(""));
                    }else{
                        vsName.html(name);
                    }
            },
            tip:function(isLogin){
                if(isLogin){
                    var el=this.el.find(this.selector.sign),
                    html=[];
                    html.push("<li class='fl'>[<a href='");
                    html.push(conf.passportServer+"/logout");
                    html.push("'>退出登录</a>]</li>");
                    el.html(html.join(""));
                }
            },
            getorthercookie:function()
            {
              $.getScript('http://www.9yue.com/getCookie.html', function(){
                    var pp = usercookie.pp||"";
                    var _nk_ = usercookie._nk_||"";
                    if(pp!='')
                   {
                    top.setCookie("p",pp);
                    top.setCookie("v",decodeURIComponent(_nk_));
                    top.check();
                    }
                });
            
            },
            init:function(){
                this.el=$(this.selector.top);
                this.menu=this.el.find(this.selector.rightMenu);
                this.list=this.el.find(this.selector.menuList);
                this.favorite=this.el.find(this.selector.favorite);
                this.mobileCode=this.el.find(this.selector.mobileCode);
                this.codeList=this.el.find(this.selector.codeList);
                this.download=this.el.find(this.selector.download);
                this.loadWebApp();
                this.loadUserPwd();
                this.check();
                this.bind();
            },
            bind:function(){
                this.menu.find("[bind]").mouseover(top.menuShow);
                this.list.mouseover(function(){$(this).show()}).
                mouseout(function(){$(this).hide()});
                this.mobileCode.mouseover(function(){$(top.codeList).show()}).
                mouseout(function(){$(top.codeList).hide()});
                this.download.click(function(){$(top.codeList).hide()});
                this.favorite.click(function(){top.addToFavorite("http://www.winxuan.com","文轩网")});
            },
            menuShow:function(){
                var el=$(this),
                    cn=el.attr("bind"),
                    list;
                if((!!cn)&&cn.length>0){
                    list=top.el.find("."+cn);
                    list.css({left:el.position().left});
                    list.show();
                }
            },
            addToFavorite:function(sURL, sTitle){
                 try{  
                       window.external.addFavorite(sURL, sTitle);  
                   }  
                   catch (e){  
                       try{  
                           window.sidebar.addPanel(sTitle, sURL, "");  
                       }  
                       catch (e){  
                           alert("加入收藏失败，请使用Ctrl+D进行添加");  
                       }  
                
                   }
            }
        };
        $(login).bind(login.LOGINED_EVENT,function(){
            top.reload();
        });
        
    return top;
});
