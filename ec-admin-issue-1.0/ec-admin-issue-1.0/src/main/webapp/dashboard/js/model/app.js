define(function(require){
    var $ = require("jQuery"), Observable = require("../event/observable"), AppEvent = require("../event/app"), DayTarget = require("../mod/target"), StatusBar = require("../mod/status-bar"), config = require("../etc/app-config");
    
    function Queue(){
        this.hashMap = {};
        this.length = 0;
    };
    
    Queue.prototype = {
    
        regist: function(list){
            var queue = this;
            $.each(list, function(){
                if (this.orderId && this.statusId) {
                    queue.hashMap[this.orderId + this.statusId] = this;
                }
            });
        },
        
        isExist: function(item){
            var key = item.data.orderId + item.data.statusId, old = this.hashMap[key];
            if (!old) {
                return false;
            }
            return true;
        },
        
        put: function(item){
            var time, key;
            if (!this.isExist(item)) {
                time = Date.parse(item.data.lastProcessTime.replace(/-/g, "/"));
                var random = Math.floor(Math.random() * (60)) * 1000;
                key = "t" + (time + (60 * 1000) + random);
                //console.info("putkey:"+key);
                if (!this[key]) {
                    this[key] = [item];
                }
                else {
                    this[key].push(item);
                }
                this.hashMap[item.data.orderId + item.data.statusId] = item;
            }
        }
    };
    
    function AppModel(){
    
        this.queue = new Queue;
        this.apiPollUrl = config["poll-data-url"];
        this.pooDataDevurl = config["poo-data-devurl"];
        this.apiSummaryUrl = config["summary-data-url"];
        this.apiSummaryDataType = config["summary-data-type"];
        this.apiPollDataType = config["poll-data-type"];
        this.globalParams = config["global-param"];
        this.dayTarget = new DayTarget();
        this.lastUpdateTime = null;
        $.extend(this, Observable);
        this.init();
    };
    
    AppModel.EVENT_TYPE = {
        "8002": AppEvent.ORDER_STATUS_WAIT,
        "8003": AppEvent.ORDER_STATUS_DIST,
        "8004": AppEvent.ORDER_STATUS_SENT,
        "8005": AppEvent.ORDER_STATUS_COMPLETE,
        "8011": AppEvent.ORDER_STATUS_PART
    };
    
    AppModel.prototype = {
    
        init: function(){
            this.pollTimer = null;
            this.broadcastTimer = null;
        },
        
        start: function(){
           this.initSummary();
		    this.devSend();
            //this.poll();
            //this.broadcast();   
           
           
        },
        
        broadcast: function(){
            var model = this, items;
            if (this.serverTime) {
                this.serverTime = this.serverTime + 1000;
                //console.info("getkey:t"+this.serverTime);
                items = this.queue["t" + this.serverTime];
                if (items) {
                    $.each(items, function(){
                        model.trigger({
                            type: this.type,
                            eventData: this.data
                        });
                    });
                }
            }
            clearTimeout(this.broadcastTimer);
            this.broadcastTimer = setTimeout(function(){
                model.broadcast()
            }, 1000);
        },
        
        devSend: function(){
            var model = this;
            $.ajax({
                method: "get",
                url: "http://console.winxuan.com/dashboard/summary?format=json&start=2012-11-11%2000:00:00",
                success: function(data){
					 model.trigger({
                            type: AppEvent.REFRESH_SUMMARY,
							eventData:data
                        });
                },
                error: function(xhr){
                    console.info(xhr.state);
                },
                complete: function(){
                    clearTimeout(this.pollerTimer);
                    this.pollTimer = setTimeout(function(){
                        model.devSend()
                    }, 10000);
                },
                dataType: "json"
            });
            
        },
        poll: function(){
            return;
            var model = this;
            $.ajax({
            
                url: this.apiPollUrl,
                
                data: this.getPollParams(),
                
                success: function(data){
                    model.update(data);
                },
                
                error: function(xhr){
                
                    if (xhr.status === 500) {
                        model.trigger({
                            type: AppEvent.SERVER_ERROR
                        });
                    }
                    else {
                        model.trigger({
                            type: AppEvent.UNKNOW_ERROR
                        });
                    }
                },
                
                complete: function(){
                    //clearTimeout(this.pollerTimer);
                    //this.pollTimer=setTimeout(function(){model.poll()},10000);
                },
                
                dataType: this.apiPollDataType
            
            });
        },
        
        getPollParams: function(){
            var params = this.getChannelParams(), scope = this.globalParams["date-scope"];
            if (this.lastUpdateTime) {
                params.push("lastUpdateTime=" + this.lastUpdateTime);
            }
            else {
                params.push("start=" + encodeURIComponent(scope.start));
                // params.push("status=8002");
                // params.push("status=8003");
            };
            params.push("status=8004");
            params.push("status=8011");
            return params.join("&");
        },
        
        getChannelParams: function(){
            var channel = this.globalParams.channel, params = [];
            
            $.each(channel, function(){
                params.push("channel=" + this);
            });
            return params;
        },
        
        getSummaryParams: function(){
        
            var params = this.getChannelParams(), scope = this.globalParams["date-scope"];
            
            params.push("start=" + encodeURIComponent(scope.start));
            params.push("end=" + encodeURIComponent(scope.end));
            
            return params.join("&");
        },
        
        initSummary: function(){
            var model = this;
            $.ajax({
            
                url: this.apiSummaryUrl,
                
                data: this.getSummaryParams(),
                
                success: function(data){
                    if (data.result === 1) {
                        model.trigger({
                            type: AppEvent.INIT_SUMMARY,
                            eventData: data
                        });
                    }
                    else {
                        model.trigger({
                            type: AppEvent.SERVER_ERROR
                        });
                    }
                    
                },
                
                error: function(xhr){
                    if (xhr.status === 500) {
                        model.trigger({
                            type: AppEvent.SERVER_ERROR
                        });
                    }
                    else {
                        model.trigger({
                            type: AppEvent.UNKNOW_ERROR
                        });
                    }
                },
                
                dataType: this.apiSummaryDataType
            });
        },
        
        update: function(data){
            if (!this.lastUpdateTime) {
                this.trigger({
                    type: AppEvent.INIT_SYSTEM,
                    eventData: data
                });
                this.serverTime = Date.parse(data.lastUpdateTime.replace(/-/g, "/"));
                console.info("getServerTime:" + this.serverTime);
                this.serverTime = this.serverTime;
                this.loaded = true;
                this.regist(data);
            }
            else {
                if (this.lastUpdateTime != data.lastUpdateTime) {
                    this.processQueue(data);
                }
            }
            this.lastUpdateTime = data.lastUpdateTime;
            
        },
        
        processQueue: function(data){
            var model = this;
            $.each(data.orderDetailList, function(){
                model.queue.put({
                    type: AppModel.EVENT_TYPE[this.statusId],
                    data: this
                });
            });
        },
        
        regist: function(data){
            if (data && data.orderDetailList) {
                this.queue.regist(data.orderDetailList);
            }
        }
    };
    
    return AppModel;
});
