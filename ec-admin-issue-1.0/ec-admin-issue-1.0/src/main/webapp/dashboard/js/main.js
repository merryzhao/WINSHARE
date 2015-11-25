define(function(require, exports){

    var Summary = require("./mod/summary"), DayTarget = require("./mod/target"), ProgressBar = require("./mod/JProgress-bar"), AppModel = require("./model/app"), Map = require("./mod/map.js?201211131641"), OrderHistory = require("./mod/order-history"), StatusBar = require("./mod/status-bar"), AppEvent = require("./event/app");
    
    function App(){
        this.summary = new Summary();
	   this.dayTarget = new DayTarget();
        this.statusBar = new StatusBar();
        this.appModel = new AppModel();
        this.init();
    };
    
    App.prototype = {
    
        init: function(){
            this.start();
        },
        bind: function(){
            var app = this;
            this.appModel.on(AppEvent.INIT_SUMMARY, function(e){
                app.summary.init(e.eventData);
            });
            this.appModel.on(AppEvent.INIT_SYSTEM, function(e){
                app.reset(e.eventData);
            });
			this.appModel.on(AppEvent.REFRESH_SUMMARY, function(e){
				app.summary.reset(e.eventData)
				app.summary.refresh();
            });
			
            this.appModel.on(AppEvent.ORDER_STATUS_WAIT, function(e){
                app.wait(e.eventData);
            });
            this.appModel.on(AppEvent.ORDER_STATUS_DIST, function(e){
                app.dist(e.eventData);
            });
            this.appModel.on(AppEvent.ORDER_STATUS_SENT, function(e){
                app.sent(e.eventData);
            });
            this.appModel.on(AppEvent.ORDER_STATUS_COMPLETE, function(e){
                app.complete(e.eventData);
            });
            this.appModel.on(AppEvent.ORDER_STATUS_PART, function(e){
                app.part(e.eventData)
            });
            this.appModel.on(AppEvent.SERVER_ERROR, function(e){
                app.error("服务器无法连通");
            });
            this.appModel.on(AppEvent.UNKNOW_ERROR, function(e){
                app.error("未知错误~,请登录后台管理端")
            });
        },
        start: function(){
            this.bind();
            this.appModel.start();
        },
        wait: function(data){
            this.summary.addToWait(data);
            try {
                console.info("wait(orderId:" + data.orderId + " status:" + data.statusId + " listPrice:" + data.listprice + " time:" + data.lastProcessTime + ")");
            } 
            catch (e) {
            
            }
            
        },
        dist: function(data){
            this.statusBar.dist(data);
        },
        sent: function(data){
            this.summary.addToSent(data);
            console.info("sent(orderId:)" + data.orderId + " status:" + data.statusId + " listPrice:" + data.listprice + ")");
            this.statusBar.sent(data);
        },
        complete: function(data){
            this.dayTarget.remove(data);
            this.statusBar.complete(data);
        },
        part: function(data){
            this.dayTarget.remove(data);
            this.statusBar.part(data);
        },
        reset: function(data){
            this.statusBar.init(data);
        },
        error: function(msg){
            throw new Error(msg);
        }
    };
    exports = new App;
});
