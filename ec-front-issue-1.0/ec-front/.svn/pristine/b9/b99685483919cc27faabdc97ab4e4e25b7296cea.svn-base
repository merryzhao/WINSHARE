define(function(require){
    var $ = require("jQuery"), conf = require("config");
    cacheManager = {
        flushPageCache: function(url){
            var url = conf.consoleServer + "/removeCache/removeUrl?url=" + url;
            $.get(url, function(d){
               // window.location.href = window.location;
            });
        },
        flushFragmentCache: function(key){
            var url = conf.consoleServer + "/removeCache/removeKey?key=" + key;
            $.get(url, function(d){
                window.location.href = window.location;
            });
        },
        flushAll: function(){
            var url = conf.consoleServer + "/removeCache/flushAll";
            $.get(url, function(d){
                window.location.href = window.location;
            });
        }
    }
    
    return cacheManager;
    
});
