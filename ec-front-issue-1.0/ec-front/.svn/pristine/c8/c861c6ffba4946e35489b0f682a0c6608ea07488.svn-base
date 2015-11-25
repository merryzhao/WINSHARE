var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-6694194-2']);
_gaq.push(['_addOrganic', 'soso', 'w']);
_gaq.push(['_addOrganic', 'youdao', 'q']);
_gaq.push(['_addOrganic', 'sogou', 'query']);
_gaq.push(['_setAllowHash', 'false']);
_gaq.push(['_setDomainName', ".winxuan.com"]);
_gaq.push(['_trackPageview']);
var visits=null;
var _ozuid=null;

seajs.use(["jQuery","top"], function($,top){
    document.write = function(str){
        return;
    };
    $(function(){
		/* 监控宝代码 */		
		(function() {
		var jkb = document.createElement('script'); jkb.type = "text/javascript"; jkb.async = true;
		jkb.src = "http://exp.jiankongbao.com/loadtrace.php?host_id=9517&style=6&type=1";
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(jkb, s);
		})();
		
		var _bdhmProtocol = (("https:" == document.location.protocol) ? "https://" : "http://");
       // var s = (unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fcde2ca53bcf3a8674541df9912d3a49b' type='text/javascript'%3E%3C/script%3E"));
		//http://hm.baidu.com/h.js?cde2ca53bcf3a8674541df9912d3a49b
		var ba = document.createElement('script');
		ba.type = 'text/javascript';
		ba.async = true;
		ba.src = _bdhmProtocol+unescape("hm.baidu.com/h.js%3Fcde2ca53bcf3a8674541df9912d3a49b");
		var s = document.getElementsByTagName('script')[0];  
		s.parentNode.insertBefore(ba, s);
		/***99click-start***/
        _ozuid=top.getUserId();
		$("body").delegate("a","click",function(){
		    var url=$(this).attr("href"),param,toUrl;
            param = url?(new GetParam(url)):{};
            if(!!param['ad_tracker']){
                $(this).attr("name",param['ad_tracker']);
                toUrl = setLink(url,param);
                $(this).attr("href",toUrl);
            }
            return true;
        });
        function GetParam(url) {
           var str = url.split("?")[1],theParam; //获取url中"?"符后的字串
           theParam = new Object();
           if (str) {
              var strs = str.split("&");
              for(var i = 0; i < strs.length; i ++) {
                 theParam[strs[i].split("=")[0]]=decodeURIComponent(strs[i].split("=")[1]);
              }
           }
           return theParam;
        }
        function setLink(url,param){
            var link= url.split("?")[0],obj=param,paramArray=[],str;
            if(!!obj['ad_tracker']){
                delete obj.ad_tracker;
                for(var name in obj){
                    paramArray.push(name+"="+obj[name]);
                }
                str=paramArray.join("&");
                link=(!!str)?(link+"?"+str):link;
                return link;
            }
            return url;
        }
		$.getScript(_bdhmProtocol+"static.winxuancdn.com/js/o_code.js", function(){
		    var src=('https:' == document.location.protocol ? 'https://ssl' : 'http://www') +'.google-analytics.com/ga.js';
		    $.getScript(src);
		})
		/***99click-end***/
		

	 
    });
});


