define(function(require){
    var $ = require("jQuery"), conf = require("config"), cacheManager = require("cms-cachemanager"), indexurl = conf.portalServer, bookurl = conf.portalServer + "/book", media = conf.portalServer + "/media", urlArray = [];
    flushCache = {
        bindUrl: function(){
			urlArray=[];
            urlArray.push(indexurl);
            urlArray.push(indexurl + "/");
            urlArray.push(bookurl);
            urlArray.push(bookurl + "/");
            urlArray.push(media);
            urlArray.push(media + "/");
        },
        flush: function(){
            this.bindUrl();
            var length = urlArray.length;
            for (var i = 0; i < length; i++) {
				cacheManager.flushPageCache(urlArray[i]);                
            }
			alert("刷新完成");
        }
        
    }
    return flushCache;
})
