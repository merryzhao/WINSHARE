/**
 * 
 * ShoppingCart 应用单元，应该不依赖基础库(像jQuery之类的)，只依赖模块
 * 
 * 这里直接返回新的ShoppingCartApp实例即可
 * 
 * html中或者脚本中直接通过
 * app.start()启动即可
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/app/shoppingcart-debug", [ "../view/shoppingcart-debug", "jQuery-debug", "../event/observable-debug", "../model/cart-data-wrap-debug", "../event/shoppingcart-debug", "../event/product-debug", "../util/ui-debug", "../model/shoppingcart-debug", "../../etc/config-debug", "../event/sandbox-debug", "../event/security-debug", "../model/product_notice-debug", "../wml/shoppingcart-combo-debug", "../model/favorite-model-debug", "../event/pagination-debug", "../wml/signin-signup-debug", "../view/signin-signup-debug", "../event/user-debug", "../model/signin-signup-debug", "../view/shoppingcart-combo-debug" ], function(require, exports) {
    /**
	 * import 相关依懒与java中类似，只是关键字不同，仅此而已
	 * 
	 */
    var CartView = require("../view/shoppingcart-debug"), CartModel = require("../model/shoppingcart-debug"), CartEvent = require("../event/shoppingcart-debug"), SandboxEvent = require("../event/sandbox-debug"), ProductEvent = require("../event/product-debug"), SecurityEvent = require("../event/security-debug"), NoticeModel = require("../model/product_notice-debug"), ComboModule = require("../wml/shoppingcart-combo-debug"), SigninModule = require("../wml/signin-signup-debug");
    /**
	 * route常量定义
	 */
    var ROUTE = {
        FAVORITE: "#favorite"
    };
    /**
	 * 构造函数
	 */
    function CartApp() {
        this.view = new CartView();
        this.model = new CartModel();
        this.noticeModel = new NoticeModel();
        this.comboModule = new ComboModule(this.model);
        this.isMerged = false;
    }
    /**
	 * 原型声明
	 */
    CartApp.prototype = {
        /**
		 * app 启动方法，初始化相关工作
		 */
        start: function() {
            if (!this.started) {
                this.bindView();
                this.bindModel();
                this.comboModule.start();
                this.started = true;
            }
            return this;
        },
        /**
		 * 初始绑定视图，view对象直接proxy所有dom事件
		 */
        bindView: function() {
            var view = this.view, app = this;
            view.on(CartEvent.REMOVE_SELECT, function(e) {
                app.removeSelect(e);
            }).on(CartEvent.REMOVE, function(e) {
                app.remove(e);
            }).on(CartEvent.INCREASE, function(e) {
                app.increase(e);
            }).on(CartEvent.REDUCE, function(e) {
                app.reduce(e);
            }).on(CartEvent.FAVORITE_SELECT, function(e) {
                app.addSelectedToFavorite(e);
            }).on(CartEvent.FAVORITE_ITEM, function(e) {
                app.addToFavorite(e);
            }).on(CartEvent.UPDATE, function(e) {
                app.update(e);
            }).on(CartEvent.RESTORE, function(e) {
                app.restore(e);
            }).on(CartEvent.OPEN_FAVORITE, function(e) {
                app.openFavorite(e);
            }).on(CartEvent.BALANCE, function(e) {
                app.balance(e);
            }).on(CartEvent.MERGE, function(e) {
                app.merge(e);
            }).on(ProductEvent.ARRIVAL_NOTICE, function(e) {
                app.arrivalNotice(e);
            });
        },
        /**
		 * 
		 * 初始绑定模型事件，在v0.1版本中所有的model都认为是一个sandbox
		 * 
		 * 简化后的Sandbox 执行成功后只有一个事件哪就是 SandboxEvent.NOTIFY这事件
		 *  
		 */
        bindModel: function() {
            var model = this.model, noticeModel = this.noticeModel, app = this;
            model.on(SandboxEvent.NOTIFY, function(e) {
                app.notify(e);
            });
            noticeModel.on(SandboxEvent.NOTIFY, function(e) {
                app.notify(e);
            }).on(SecurityEvent.RESOURCES_PROTECTED, function(e) {
                app.view.closeTip();
                app.showLogin();
            });
        },
        /**
		 * SandboxEvent.NOTIFY 事件回调方法
		 * 
		 * 使用类似Java中的反射原理，调用view中的方法进行数据处理
		 * 
		 * @param {Object} e
		 */
        notify: function(e) {
            var method = this.view[e.action];
            if (typeof method == "function") {
                method.call(this.view, e.eventData);
            }
        },
        /**
		 * 删除选中的条目
		 * @param {Object} e
		 */
        removeSelect: function(e) {
            var items = this.view.getSelectedItem(), app = this;
            /*app.view.lock();*/
            if (items.length > 0) {
                app.view.confirm({
                    dock: e.source,
                    message: "从购物车中删除选中的商品？",
                    confirm: function() {
                        app.view.showTip({
                            dock: e.source
                        });
                        app.model.remove(items);
                    }
                });
            } else {
                app.view.showTip({
                    dock: e.source,
                    message: "<p>请选择您要删除的商品！</p>",
                    delayClose: 3e3
                });
            }
        },
        /**
		 * 增加商品数量
		 * @param {Object} e
		 */
        increase: function(e) {
            var quantity = this.view.getItemQuantity(e.eventData.id);
            quantity++;
            this.view.showTip({
                dock: this.view.getItemDock(e.eventData.id)
            });
            this.model.update(e.eventData.id, quantity, true);
        },
        /**
		 * 从购物车中移除商品
		 * 
		 * @param {Object} e
		 */
        remove: function(e) {
            var app = this, dock = this.view.getItemDock(e.eventData.id);
            this.view.confirm({
                dock: dock,
                confirm: function() {
                    app.model.remove([ e.eventData.id ]);
                    app.view.showTip({
                        dock: dock
                    });
                },
                cancel: function() {
                    if (dock.is(":input")) {
                        if (dock.val() != e.original) {
                            dock.val(e.original);
                            dock.data("value", e.original);
                        }
                    }
                }
            });
        },
        /**
		 * 减少商品数量
		 * @param {Object} e
		 */
        reduce: function(e) {
            var app = this, original = this.view.getItemQuantity(e.eventData.id), quantity = original - 1;
            e.original = original;
            if (quantity <= 0) {
                app.remove(e);
            } else {
                this.view.showTip({
                    dock: this.view.getItemDock(e.eventData.id)
                });
                this.model.update(e.eventData.id, quantity, true);
            }
        },
        /**
		 * 更新商品数量为指定值
		 * @param {Object} e
		 */
        update: function(e) {
            var app = this, quantity = app.view.getItemQuantity(e.eventData.id);
            if (isNaN(quantity)) {
                app.view.reset(e.eventData.id);
            } else {
                if (quantity != 0) {
                    app.view.showTip({
                        dock: this.view.getItemDock(e.eventData.id)
                    });
                    app.model.update(e.eventData.id, quantity, false);
                } else {
                    app.remove(e);
                }
            }
        },
        /**
		 * 
		 * 添加选定的商品到收藏
		 * 
		 * @param {Object} e
		 */
        addSelectedToFavorite: function(e) {
            var app = this, items = this.view.getSelectedItem(), msg = "";
            if (items.length > 0) {
                app.comboModule.show();
                app.comboModule.addToFavorite(items);
                if (app.comboModule.isLogin) {
                    msg = "<p>" + items.length + "件商品已加入您的收藏</p>";
                } else {
                    msg = "<p>请先登录！</p>";
                }
                app.view.showTip({
                    dock: e.source,
                    message: msg,
                    delayClose: 3e3
                });
            } else {
                app.view.showTip({
                    dock: e.source,
                    message: "<p>请选择您要收藏的商品！</p>",
                    delayClose: 3e3
                });
            }
        },
        /**
		 * 添加单个商品至收藏
		 * @param {Object} e
		 */
        addToFavorite: function(e) {
            this.comboModule.show();
            if (e.eventData.id) {
                this.comboModule.addToFavorite([ e.eventData.id ]);
            }
        },
        /**
		 * 打开用户的收藏
		 * @param {Object} e
		 */
        openFavorite: function(e) {
            this.comboModule.show();
        },
        /**
		 * 还原历史操作条目, 暂没做批量还原
		 * @param {Object} e
		 */
        restore: function(e) {
            var app = this;
            app.view.showTip({
                dock: e.source
            });
            app.model.append(e.eventData.id, e.eventData.quantity);
        },
        next: function() {
            var url = "http://" + document.domain + "/shoppingcart/select";
            try {
                open(url, "_self");
            } catch (e) {
                document.location.href = url;
            }
        },
        arrivalNotice: function(e) {
            var app = this, id = e.eventData.id;
            if (app.comboModule.isLogin) {
                app.view.showTip({
                    dock: e.source
                });
                app.noticeModel.arrivalNotice([ id ]);
            } else {
                new SigninModule({
                    signin: function() {
                        app.noticeModel.arrivalNotice([ id ]);
                    }
                });
            }
        },
        showLogin: function() {
            new SigninModule();
        },
        /**
		 * 结算事件
		 */
        balance: function(e) {
            var app = this;
            if (this.view.valid()) {
                return false;
            }
            /*这里暂时借用combo模块的登录判断*/
            if (this.comboModule.isLogin) {
                if (this.isMerged) {
                    this.next();
                } else {
                    this.model.query();
                    this.isMerged = true;
                }
            } else {
                new SigninModule({
                    signin: function() {
                        app.comboModule.isLogin = true;
                        app.model.query();
                        app.isMerged = true;
                    },
                    footer: "<p class='sunshine'>阳光阅读用户购买请<a href='http://www.winxuan.com/shoppingcart/select' target='_self'>点击这里</a></p><p class='quick'>暂不登录，我想快速购买：<a href='http://www.winxuan.com/shoppingcart/select' target='_self'>继续结算&gt;&gt;</a></p>",
                    referrer: "http://www.winxuan.com/shoppingcart/select"
                }).show();
            }
        },
        /*
		 * 合并购物车
		 */
        merge: function(e) {
            this.model.merge(e.action);
        }
    };
    exports = new CartApp().start();
});
/**
 * 
 * 抽象化的observable接口实现，
 * model&view 只需简单继承此抽象化对象即可使对象可观查化可侦听化
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/event/observable-debug", [ "jQuery-debug" ], function(require) {
    var $ = require("jQuery-debug"), /**
	 * 在第一版的设计定义，暂时采用对jQuery的原生方法进行proxy处理
	 * 其它解决方案也正在验证中
	 * 
	 * 通过method.call(obj,args...), 或者method.apply(obj,[args])方式，
	 * 读起来总感觉很怪异
	 * 
	 */
    AbstractObservable = {
        /**
		 * 事件触发实现
		 */
        trigger: function() {
            var proxy = $(this);
            proxy.trigger.apply(proxy, arguments);
            return this;
        },
        /**
		 * 事件捕获实现
		 */
        on: function() {
            var proxy = $(this);
            proxy.on.apply(proxy, arguments);
            return this;
        }
    };
    return AbstractObservable;
});
/**
 * 
 * 分页事件定义
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/event/pagination-debug", [], {
    /**
	 * 泛化的翻页事件
	 */
    PAGE_TURNING: "page_turning",
    /**
	 * 下一页
	 */
    NEXT_PAGE: "next_page",
    /**
	 * 上一页
	 */
    PREVIEW_PAGE: "preview_page"
});
/**
 * 
 * 产品事件定义
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/event/product-debug", [], {
    /**
	 * 到货通知
	 */
    ARRIVAL_NOTICE: "arrival_notice"
});
/**
 * 
 * 业务模型沙箱事件定义
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/event/sandbox-debug", [], {
    /**
	 * 业务模型沙箱调用成功通知事件
	 */
    NOTIFY: "notify",
    /**
	 * 
	 * 业务模型沙箱调用失败处理事件
	 *  
	 */
    ERROR: "error"
});
/**
 * 与系统安全相关的事件定义
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 */
define("v1/src/event/security-debug", [], {
    /**
	 * 资源访问受限
	 */
    RESOURCES_PROTECTED: "resources_protected",
    /**
	 * 帐号已被禁用
	 */
    ACCOUNT_DISABLED: "account_has_been_disabled"
});
/**
 * 
 * 购物车事件定义
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/event/shoppingcart-debug", [], {
    /**
	 * 移除所选商品
	 */
    REMOVE_SELECT: "remove_select",
    /**
	 * 商品数量减少
	 */
    REDUCE: "item_reduce",
    /**
	 * 商品数量增加
	 */
    INCREASE: "item_increase",
    /**
	 * 移除单个商品 
	 */
    REMOVE: "item_remove",
    /**
	 * 选择所有列表项
	 */
    SELECT_ALL: "select_all",
    /**
	 * 收藏所选
	 */
    FAVORITE_SELECT: "favorite_select",
    /**
	 * 还原单个移除的商品
	 */
    RESTORE: "restore_item",
    /**
	 * 添加收藏条目
	 */
    FAVORITE_ITEM: "favorite_item",
    /**
	 * 打开收藏夹
	 */
    OPEN_FAVORITE: "open_favorite",
    /**
	 * 商品数量更新
	 */
    UPDATE: "item_update",
    /**
	 * 添加商品至购物车
	 */
    ADD: "add_to_cart",
    /**
	 * 结算事件 
	 */
    BALANCE: "cart_to_balance",
    /**
     * 合并购物车 
     */
    MERGE: "merge_cart"
});
/**
 * 
 * 用户事件定义
 * 
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/event/user-debug", [], {
    /**
		 * 用户登录
		 */
    SIGNIN: "user_signin",
    /**
		 * 用户登录提交
		 */
    SIGNIN_SUBMIT: "signin_submit",
    /**
		 * 用户注册
		 */
    SIGNUP: "user_signup",
    /**
		 * 用户注册提交
		 */
    SIGNUP_SUBMIT: "signup_submit",
    /**
		 * 刷新验证码
		 */
    RELOAD_VERIFY: "reload_verify"
});
/**
 * 
 * 对shoppingcart返回的JSON对象进行包装
 * 提供一些实用的方法，避免上层应用进行调用时，
 * 需要对JSON对象属性值，进行过多的判断
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/model/cart-data-wrap-debug", [ "jQuery-debug" ], function(require) {
    var $ = require("jQuery-debug");
    /**
	 * 构造函数
	 * @param {Object} data 原始返回的JSON包
	 * @param {Object} template 模板对象,将对应的返回状态转义为可读的文字描述模板
	 */
    function CartDataWrap(data, template) {
        this.data = data;
        this.template = template || {};
        this.init();
    }
    /**
	 * 状态含义定义
	 */
    CartDataWrap.Status = {
        "1": "success",
        "2": "error",
        "3": "unavailable",
        "4": "offshelf",
        "5": "number",
        "6": "param",
        "7": "limit"
    };
    /**
	 * 
	 * 下一版中，将使用模板引擎，在本次重构中，暂不引入
	 *  
	 */
    CartDataWrap.PromTemplate = {
        "20004": {
            style: "save",
            promName: "<h3>以下活动商品已购满{minMoney}元，已减{amount}元现金 </h3><span class='highlight'>&yen;<em>{savePrice}</em></span>",
            hasMet: "",
            willBeMet: "<h3>再购买活动商品{needMoney}元，可减{moreSaveMoney}元现金</h3>"
        },
        "20005": {
            style: "send",
            promName: "本订单赠{amount}元礼券",
            hasMet: "",
            willBeMet: "再购买{needMoney}元商品，可获赠{moreSaveMoney}元礼券"
        },
        "20006": {
            style: "delivery",
            promName: "已免{amount}元运费",
            hasMet: "",
            willBeMet: "再购买{needMoney}元商品，可免{moreSaveMoney}元运费"
        },
        "20008": {
            style: "single",
            promName: "购买指定活动商品已满{minMoney}元，立减{amount}元现金",
            hasMet: "",
            willBeMet: "再购买指定活动商品{needMoney}元，可减{moreSaveMoney}元现金"
        }
    };
    /**
	 * 
	 * 静态方法
	 * 合并数据和模板
	 * 下次重构时，可移除此方法
	 * 
	 * @param {Object} prom
	 */
    CartDataWrap.getPromMessage = function(prom) {
        var hasMet = false, html, msg = [], template = CartDataWrap.PromTemplate[prom.promType];
        if (prom.saveMoney > 0) {
            hasMet = true;
            html = template.promName.replace(/\{minMoney\}/g, prom.minMoney).replace(/\{amount\}/g, prom.saveMoney).replace(/\{savePrice\}/g, prom.saveMoney.toFixed(2));
            html += template.hasMet.replace(/\{saveMoney\}/g, prom.saveMoney);
            msg.push(html);
        }
        if (prom.needMoney > 0) {
            html = template.willBeMet.replace(/\{needMoney\}/g, prom.needMoney.toFixed(2));
            html = html.replace(/\{moreSaveMoney\}/g, prom.moreSaveMoney);
            msg.push(html);
        }
        return msg.join("");
    };
    /**
	 * 
	 */
    CartDataWrap.prototype = {
        init: function() {
            this.id = this.data.sourceId;
            this.quantity = this.data.quantity;
            this.refresh = this.data.refresh;
        },
        getPoints: function() {
            if (!this.data || !this.data.result) {
                return null;
            }
            var list = this.data.result.shoppingcart.itemList, item;
            for (var i = 0; i < list.length; i++) {
                item = list[i];
                if (item.productSaleId == this.id) {
                    return item.points;
                }
            }
            return null;
        },
        getUpdateItem: function() {
            if (!this.id) {
                return null;
            }
            var list = this.data.result.shoppingcart.itemList, item;
            for (var i = 0; list.length; i++) {
                item = list[i];
                if (item.productSaleId == this.id) {
                    return item;
                }
            }
            return null;
        },
        getCount: function() {
            if (!this.data || !this.data.result) {
                return null;
            }
            return this.data.result.shoppingcart.count;
        },
        getProms: function(shopId, supplyType) {
            var promList = this.data.result.shoppingcart.proms, result = [], prom;
            if (arguments.length < 2) {
                result = promList;
            } else {
                if (promList) {
                    for (var i = 0; i < promList.length; i++) {
                        prom = promList[i];
                        if (prom.shopId == shopId && prom.supplyTypeCode == supplyType) {
                            result.push(prom);
                        }
                    }
                }
            }
            return result;
        },
        getSaveMoney: function() {
            var money = 0;
            if (this.data.result.shoppingcart.saveMoney) {
                money = this.data.result.shoppingcart.saveMoney;
            }
            return money.toFixed(2);
        },
        getBriefPrice: function() {
            return this.data.result.shoppingcart.salePrice.toFixed(2);
        },
        getSalePrice: function() {
            return this.data.result.shoppingcart.salePrice.toFixed(2);
        },
        getCountPrice: function() {
            return (this.getSalePrice() - this.getSaveMoney()).toFixed(2);
        },
        getStatusString: function() {
            if (!this.data || !this.data.result) {
                return null;
            }
            return CartDataWrap.Status[this.data.result.status];
        },
        getStatusInt: function() {
            return this.data.result.status;
        },
        getAvailable: function() {
            return this.data.result.avalibleQuantity;
        },
        /**
		 * 
		 * 更新消息
		 * 
		 */
        getMessage: function() {
            var stString = this.getStatusString(), stInt = this.getStatusInt(), key = stString.toUpperCase(), msg = this.template[key];
            if (stInt === 1) {
                msg = msg.replace(/\{count\}/, this.getCount());
                msg = msg.replace(/\{salePrice\}/, this.getSalePrice());
            } else if (stInt === 3) {
                msg = msg.replace(/\{avalibleQuantity\}/, this.getAvailable());
            }
            return msg;
        },
        getHistoryList: function() {
            if (!this.data || !this.data.result) {
                return null;
            }
            return this.data.result.itemList;
        },
        getContent: function() {
            var quantity = 0, list = "", content = this.template.CONTENT, obj = this;
            $.each(this.getHistoryList(), function() {
                var item = obj.template.ITEM;
                item = item.replace(/\{url\}/g, this.url);
                item = item.replace(/\{name\}/g, this.name);
                item = item.replace(/\{sellName\}/g, this.sellName);
                item = item.replace(/\{imageUrl\}/g, this.imageUrl);
                item = item.replace(/\{salePrice\}/g, this.salePrice.toFixed(2));
                item = item.replace(/\{listPrice\}/g, this.listPrice.toFixed(2));
                quantity += this.quantity;
                list += item;
            });
            content = content.replace(/\{quantity\}/g, quantity);
            content = content.replace(/\{list\}/g, list);
            return content;
        }
    };
    return CartDataWrap;
});
/**
 * 
 * 收藏夹模型沙箱
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/model/favorite-model-debug", [ "jQuery-debug", "../event/observable-debug", "../event/sandbox-debug", "../event/security-debug", "../../etc/config-debug" ], function(require) {
    /**
	 * import 声明
	 */
    var $ = require("jQuery-debug"), Observable = require("../event/observable-debug"), SandboxEvent = require("../event/sandbox-debug"), SecurityEvent = require("../event/security-debug"), Config = require("../../etc/config-debug");
    /**
	 * 构造函数
	 * 配置对象CFG, 只需配置分页参数即可，也可以通过其它方式传递进来
	 * 例如：在页面的DOM元素上配置特定的data-config对象
	 * @param {Object} cfg
	 */
    function FavoriteModel(cfg) {
        this.apiUrl = Config.apiServer + "/customer/favorite";
        this.opt = {
            index: 0,
            page: 1,
            size: 6,
            format: "jsonp"
        };
        $.extend(this.opt, cfg);
        $.extend(this, Observable);
    }
    /**
	 * 模型沙箱的动作定义
	 * 
	 * 在事件notify处理中，通过Action的定义，路由至view的特定方法去进行处理
	 * 
	 */
    FavoriteModel.ACTION = {
        UPDATE: "update",
        REMOVE: "remove",
        APPEND: "append",
        LIST: "list"
    };
    FavoriteModel.prototype = {
        /**
		 * 收藏夹新增商品
		 * @param {Object} list
		 */
        append: function(list) {
            var url = [ this.apiUrl + "/add?" ], model = this;
            $.each(list, function() {
                url.push("p=" + this);
            });
            url.push($.param(this.opt));
            url.push("callback=?");
            $.ajax({
                url: url.join("&"),
                success: function(data) {
                    model.trigger({
                        type: SandboxEvent.NOTIFY,
                        action: FavoriteModel.ACTION.APPEND,
                        eventData: {
                            list: list,
                            result: data
                        }
                    });
                },
                /**
				 * 错误处理
				 * @param {Object} xhr
				 */
                error: function(xhr) {
                    if (xhr.status == 401) {
                        model.trigger({
                            type: SecurityEvent.RESOURCES_PROTECTED
                        });
                    }
                },
                dataType: "jsonp"
            });
        },
        /**
		 * 更新收藏夹标识，在购物车重构中，不会使用此方法，暂未实现
		 */
        update: function() {
            throw new Error("TODO FavoriteModel.update()");
        },
        /**
		 * 移除收藏商品，与update类似，暂未实现
		 */
        remove: function() {
            throw new Error("TODO FavoriteModel.remove()");
        },
        /**
		 * 取得收藏夹列表
		 * @param {Object} pageOpt
		 */
        list: function(pageOpt) {
            var url = [], model = this, pageOpt = $.extend(this.opt, pageOpt);
            url.push($.param(pageOpt));
            url.push("callback=?");
            $.ajax({
                url: this.apiUrl + "?" + url.join("&"),
                success: function(data) {
                    model.trigger({
                        type: SandboxEvent.NOTIFY,
                        action: FavoriteModel.ACTION.LIST,
                        eventData: {
                            result: data
                        }
                    });
                },
                error: function(xhr) {
                    if (xhr.status == 401) {
                        model.trigger({
                            type: SecurityEvent.RESOURCES_PROTECTED
                        });
                    }
                },
                dataType: "jsonp"
            });
        }
    };
    return FavoriteModel;
});
define("v1/src/model/product_notice-debug", [ "jQuery-debug", "../event/sandbox-debug", "../../etc/config-debug", "../event/observable-debug", "../event/security-debug" ], function(require) {
    var $ = require("jQuery-debug"), SandboxEvent = require("../event/sandbox-debug"), Config = require("../../etc/config-debug"), Observable = require("../event/observable-debug"), SecurityEvent = require("../event/security-debug");
    function NoticeModel() {
        this.apiUrl = Config.apiServer + "/customer/notify";
        this.opt = {
            format: "jsonp"
        };
        $.extend(this, Observable);
    }
    NoticeModel.ACTION = {
        ARRIVAL_NOTICE: "arrivalNotice"
    };
    NoticeModel.prototype = {
        arrivalNotice: function(list) {
            var url = this.apiUrl + "/add", params = [], model = this;
            $.each(list, function() {
                params.push("p=" + this);
            });
            params.push("callback=?");
            params.push("type=461003");
            params.push($.param(this.opt));
            $.ajax({
                url: url,
                data: params.join("&"),
                success: function(data) {
                    model.trigger({
                        type: SandboxEvent.NOTIFY,
                        action: NoticeModel.ACTION.ARRIVAL_NOTICE,
                        eventData: {
                            list: list,
                            result: data
                        }
                    });
                },
                error: function(xhr) {
                    if (xhr.status == 401) {
                        model.trigger({
                            type: SecurityEvent.RESOURCES_PROTECTED
                        });
                    }
                },
                dataType: "jsonp"
            });
        }
    };
    return NoticeModel;
});
/**
 * 
 * 购物车模型沙箱
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/model/shoppingcart-debug", [ "jQuery-debug", "../event/observable-debug", "../../etc/config-debug", "../event/sandbox-debug" ], function(require) {
    /**
	 * import 相关依赖
	 */
    var $ = require("jQuery-debug"), Observable = require("../event/observable-debug"), Config = require("../../etc/config-debug"), SandboxEvent = require("../event/sandbox-debug");
    function CartModel() {
        this.apiUrl = Config.apiServer + "/shoppingcart";
        this.format = "format=jsonp";
        this.option = "opt=update";
        this.callback = "callback=?";
        this.removeParam = "qty=0";
        $.extend(this, Observable);
    }
    CartModel.ACTION = {
        UPDATE: "update",
        REMOVE: "remove",
        APPEND: "append",
        QUERY: "query",
        MERGE: "merge"
    };
    CartModel.prototype = {
        update: function(id, quantity, refresh) {
            var url = [ this.apiUrl + "/modify?p=" + id ], model = this;
            url.push(this.format);
            url.push(this.option);
            url.push(this.callback);
            url.push("qty=" + quantity);
            $.getJSON(url.join("&"), function(data) {
                model.trigger({
                    type: SandboxEvent.NOTIFY,
                    action: CartModel.ACTION.UPDATE,
                    eventData: {
                        sourceId: id,
                        quantity: quantity,
                        refresh: refresh,
                        result: data
                    }
                });
            });
        },
        remove: function(list) {
            var url = this.apiUrl + "/modify?", model = this, param = [];
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    param.push("p=" + list[i]);
                    param.push(this.removeParam);
                }
                param.push(this.format);
                param.push(this.callback);
                param.push(this.option);
                $.getJSON(url + param.join("&"), function(data) {
                    model.trigger({
                        type: SandboxEvent.NOTIFY,
                        action: CartModel.ACTION.REMOVE,
                        eventData: {
                            itemList: list,
                            result: data
                        }
                    });
                });
            } else {
                throw new Error("Parameter list can not be empty or null!");
            }
        },
        append: function(id, quantity) {
            var quantity = quantity || 1, model = this, url = [ this.apiUrl + "/modify?p=" + id ];
            url.push(this.format);
            url.push(this.callback);
            url.push("qty=" + quantity);
            $.getJSON(url.join("&"), function(data) {
                model.trigger({
                    type: SandboxEvent.NOTIFY,
                    action: CartModel.ACTION.APPEND,
                    eventData: {
                        sourceId: id,
                        result: data
                    }
                });
            });
        },
        query: function() {
            var model = this, url = [ this.apiUrl + "/queryShoppingcart?" ];
            url.push(this.format);
            url.push(this.callback);
            $.getJSON(url.join("&"), function(data) {
                model.trigger({
                    type: SandboxEvent.NOTIFY,
                    action: CartModel.ACTION.QUERY,
                    eventData: {
                        result: data
                    }
                });
            });
        },
        merge: function(action) {
            var model = this, url = [ this.apiUrl + "/merge?" ];
            url.push("opt=" + action);
            url.push(this.format);
            url.push(this.callback);
            $.getJSON(url.join("&"), function(data) {
                model.trigger({
                    type: SandboxEvent.NOTIFY,
                    action: CartModel.ACTION.MERGE,
                    eventData: {
                        result: data
                    }
                });
            });
        }
    };
    return CartModel;
});
define("v1/src/model/signin-signup-debug", [ "jQuery-debug", "../../etc/config-debug", "../event/sandbox-debug", "../event/observable-debug" ], function(require) {
    var $ = require("jQuery-debug"), Config = require("../../etc/config-debug"), SandboxEvent = require("../event/sandbox-debug"), Observable = require("../event/observable-debug");
    function Model() {
        this.apiUrl = Config.passportServer;
        $.extend(this, Observable);
    }
    Model.ACTION = {
        SIGNIN: "signin"
    };
    Model.prototype = {
        signin: function(data) {
            var url = this.apiUrl.replace("http:", "https:") + "/verify?format=jsonp&callback=?", model = this;
            $.getJSON(url + "&" + $.param(data), function(data) {
                model.trigger({
                    type: SandboxEvent.NOTIFY,
                    action: Model.ACTION.SIGNIN,
                    eventData: data
                });
            });
        }
    };
    return Model;
});
define("v1/src/template/signin-signup-debug", [], {
    BASE: '<div id="mini-signin"><div class="wrap">' + '<ul class="nav-tab"><li class="selected" tabindex="1">登录</li><li tabindex="2">注册</li></ul>' + '<div class="panels"></div>' + '<div class="foot-panel">&nbsp;</div>' + '<div class="close"><a href="javascript:;">关闭</a></div>' + "</div></div>",
    SIGNIN: '<div class="signin-panel"><form name="signin" action="http://passport.winxuan.com/signin" method="GET">' + '<fieldset><div><label>帐户</label><input name="name" type="text" class="placeholder" placeholder="帐户名或Email地址"/></div>' + '<p class="name tip">请在上方输入您的帐户名/Email</p>' + '<div><label>密码</label><input name="password" type="password" class="placeholder" placeholder="您的文轩网密码"/><a href="javascript:;">忘记密码了？</a></div>' + '<p class="password tip">请在上方输入您的密码</p></fieldset>' + '<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signin_submit">登录</button>' + '<b>新用户？</b><a href="javascript:;" bind="user_signup">立即注册</a></div>' + '</form><div class="other">' + "<p>使用下面合作网站帐号登录文轩网</p>" + '<ul><li class="qq"><a href="javascript:;">QQ</a></li>' + '<li class="alipay"><a href="javascript:;">支付宝</a></li>' + '<li class="sina"><a href="javascript:;">新浪微博</a></li>' + '<li class="douban"><a href="javascript:;">豆瓣</a></li></ul>' + "</div></div>",
    SIGNUP: '<div class="signup-panel"><form name="signup" action="http://passport.winxuan.com/signup" method="POST"><fieldset>' + '<div><label>邮箱</label><input name="name" type="text" class="placeholder" placeholder="电子邮箱/Email地址"/></div>' + '<p class="name tip">请输入您的Email地址，作为您在文轩网的登录帐户名</p>' + '<div><label>密码</label><input name="password" class="placeholder" type="password" placeholder="请输入您的密码"/></div>' + '<p class="password tip">请输入您的文轩网密码</p>' + '<div><label>确认密码</label><input name="confirm" class="placeholder" type="password" placeholder="请重复输入上方的密码"/></div>' + '<p class="confirm tip">请重复输入上方的登录密码</p>' + '<div class="verify"><label>验证码</label><input type="text" name="verify"/><img name="verify"/><a href="javascript:;" bind="reload_verify">换一张</a></div>' + '<p class="verify tip">请输入右上方图片中的数字</p>' + '<div class="agreement"><input type="checkbox" name="agreement"/><p>我已阅读并同意 <a href="http://www.winxuan.com/help/terms.html">《文轩网交易条款》</a></p></div></fieldset>' + '<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signup_submit">立即注册</button></div>' + "</form></div>"
});
define("v1/src/util/ui-debug", [ "jQuery-debug" ], function(require) {
    var $ = require("jQuery-debug"), UI = {
        center: function(target) {
            var top = $(window).height() / 2 - target.height() / 2 + $(document).scrollTop(), left = $(window).width() / 2 - target.width() / 2;
            top = top < 0 ? 0 : top;
            left = left < 0 ? 0 : left;
            target.css({
                top: top,
                left: left
            });
        },
        build: function(type) {
            if (!!type) {
                if (typeof UI[type] != "undefined") {
                    return new UI[type]();
                } else {
                    throw new Error("UI build method: can not found type - " + type);
                }
            } else {
                throw new Error('UI build method: "type" can not be null!');
            }
        }
    };
    UI.Mask = function() {
        this.el = $('<div class="ui:mask"><div>');
        this.isInDOM = false;
        this.init();
    };
    UI.Mask.prototype = {
        init: function() {
            this.resize();
            this.bind();
        },
        resize: function() {
            var dh = $(document).height(), ww = $(window).width(), dw = $(document).width(), wh = $(window).height();
            if (dh < wh) {
                this.el.css({
                    height: wh
                });
            } else {
                this.el.css({
                    height: dh
                });
            }
            if (dw < ww) {
                this.el.css({
                    width: ww
                });
            } else {
                this.el.css({
                    width: dw
                });
            }
        },
        bind: function() {},
        showIn: function(target) {
            var target = target || document.body;
            if (!this.isInDOM) {
                this.el.appendTo(target);
                this.el.show();
                this.isInDOM = true;
            } else {
                this.resize();
                this.el.show();
            }
        },
        show: function() {
            this.showIn(document.body);
        },
        remove: function() {
            this.el.remove();
        },
        hide: function() {
            this.el.hide();
        }
    };
    return UI;
});
/**
 * 
 * 购物车下方的组合框view
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/view/shoppingcart-combo-debug", [ "jQuery-debug", "../event/observable-debug", "../event/shoppingcart-debug", "../event/security-debug", "../event/pagination-debug" ], function(require) {
    var $ = require("jQuery-debug"), Observable = require("../event/observable-debug"), CartEvent = require("../event/shoppingcart-debug"), SecurityEvent = require("../event/security-debug"), PaginationEvent = require("../event/pagination-debug");
    /**
	 * 抽象化的Panel
	 * 组合框可放置各种Panel，此对象可扩展配置
	 */
    function Panel() {
        this.el = $(".panel");
        this.listEl = this.el.find(".list");
        this.pageEl = this.el.find(".page");
        this.resultKey = "favoriteList";
        this.pageKey = "pagination";
        this.loadingState = this.listEl.html();
        this.emptyState = '<p class="empty">您的收藏中，没有找到任何商品！</p>';
        this.loginState = '<p class="login">您还没有登录哟，点这里<a href="javascript:;" bind="login">登录文轩网</a></p>';
        this.init();
    }
    Panel.Template = {
        ITEM: '<ul><li class="image"><a href="{url}" target="_blank"><img src="{imgUrl}"/></a></li><li class="title"><a href="{url}" target="_blank">{name}</a></li>' + '<li class="list-price">&yen;<em>{price}</em></li><li class="button"><button data-id="{productSaleId}" bind="add_to_cart"{state}><b>{buttonText}</b></button></li></ul>'
    };
    Panel.prototype = {
        init: function() {
            $.extend(this, Observable);
            this.bind();
        },
        bind: function() {
            var view = this;
            function delegate(eventType, el) {
                view.trigger({
                    type: eventType,
                    source: el,
                    eventData: $(el).data()
                });
            }
            this.el.delegate("[bind='add_to_cart']", "click", function() {
                delegate(CartEvent.ADD, this);
            }).delegate("[bind='page_turning']", "click", function(e) {
                delegate(PaginationEvent.PAGE_TURNING, this);
            }).delegate("[bind='login']", "click", function(e) {
                delegate(SecurityEvent.RESOURCES_PROTECTED, this);
            }).delegate("[bind='add_to_cart']", "mouseover", function() {
                if (!this.disabled) {
                    $(this).addClass("hover");
                }
            }).delegate("[bind='add_to_cart']", "mouseout", function() {
                $(this).removeClass("hover");
            }).delegate("[bind='add_to_cart']", "click", function() {
                var button = $(this);
                if (button.data("state") != "loading") {
                    button.data("init-state-text", button.text());
                    button.data("state", "loading");
                    button.html("<b>添加中...</b>");
                }
            });
        },
        render: function(item) {
            var html, state = item.currentStatusCode == 13002 ? "" : " class='disabled' disabled='disabled'", buttonText = item.currentStatusCode == 13002 ? "加入购物车" : "" || item.currentStatusCode == 13001 ? "商品已下架" : "" || "商品已停用";
            html = Panel.Template.ITEM.replace(/\{url\}/g, item.url).replace(/\{imgUrl\}/g, item.imageUrl).replace(/\{name\}/g, item.name).replace(/\{sellName\}/g, item.sellName).replace(/\{price\}/g, item.salePrice.toFixed(2)).replace(/\{productSaleId\}/g, item.productSaleId).replace(/\{state\}/g, state).replace(/\{buttonText\}/g, buttonText);
            return html;
        },
        list: function(data) {
            var list = data.result[this.resultKey], page = data.result[this.pageKey], html = [], panel = this;
            if (!!list && list.length != 0) {
                $.each(list, function() {
                    html.push(panel.render(this));
                });
                this.renderPageBar(page);
            } else {
                html.push(this.emptyState);
            }
            this.listEl.html(html.join(""));
        },
        renderPageBar: function(page) {
            var html = [ "<p>" ];
            for (var i = 1; i <= page.pageCount; i++) {
                if (i == page.currentPage) {
                    html.push('<a href="javascript:;" class="selected">' + i + "</a>");
                } else {
                    html.push('<a href="javascript:;" bind="page_turning" data-page="' + i + '">' + i + "</a>");
                }
            }
            html.push("</p>");
            this.pageEl.html(html.join(""));
        },
        changeState: function(state) {
            if (state == "login") {
                this.listEl.html(this.loginState);
            } else {
                this.listEl.html(this.loadingState);
            }
        },
        restoreButton: function(id) {
            var btn = this.el.find("button[data-id='" + id + "']");
            btn.data("state", "normal");
            if (btn.data("init-state-text")) {
                btn.html("<b>" + btn.data("init-state-text") + "</b>");
            } else {
                btn.html("<b>加入购物车</b>");
            }
        }
    };
    function ComboView() {
        this.panels = [];
        this.el = $("#combo-tab");
        this.isShown = false;
        $.extend(this, Observable);
        this.init();
    }
    ComboView.PANEL = {
        FAVORITE: "favorite",
        HISTORY: "history"
    };
    ComboView.prototype = {
        init: function() {
            var cfg = this.el.data("config");
            if (!!cfg && typeof cfg == "string") {
                cfg = eval("(" + cfg + ")");
            } else {
                cfg = cfg || {};
            }
            this.opt = $.extend({
                index: 0,
                page: 1,
                size: 30
            }, cfg);
            this.panels.push(new Panel());
            this.bind();
        },
        bind: function() {
            var fav = this.panels[0], view = this;
            fav.on(CartEvent.ADD, function(e) {
                view.dispatch(e);
            }).on(PaginationEvent.PAGE_TURNING, function(e) {
                view.dispatch(e);
            }).on(SecurityEvent.RESOURCES_PROTECTED, function(e) {
                view.dispatch(e);
            });
        },
        getCurrentPanel: function() {
            return this.panels[0];
        },
        list: function(data) {
            var panel = this.getCurrentPanel();
            panel.list(data);
        },
        changeState: function(state) {
            var state = state || "login", panel = this.getCurrentPanel();
            panel.changeState(state);
        },
        dispatch: function(e) {
            this.trigger(e);
        },
        append: function() {
            this.trigger({
                type: PaginationEvent.PAGE_TURNING,
                eventData: {
                    page: 1
                }
            });
        },
        show: function() {
            if (!this.isShown) {
                this.el.show();
            }
        },
        restoreButton: function(id) {
            var panel = this.getCurrentPanel();
            panel.restoreButton(id);
        }
    };
    return ComboView;
});
/**
 * 
 * 购物车view层定义
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/view/shoppingcart-debug", [ "jQuery-debug", "../event/observable-debug", "../model/cart-data-wrap-debug", "../event/shoppingcart-debug", "../event/product-debug", "../util/ui-debug" ], function(require) {
    /**
	 * import 依赖
	 */
    var $ = require("jQuery-debug"), Observable = require("../event/observable-debug"), CartDataWrap = require("../model/cart-data-wrap-debug"), CartEvent = require("../event/shoppingcart-debug"), ProductEvent = require("../event/product-debug"), UI = require("../util/ui-debug");
    /**
	 * 确认小窗口，本次暂未将它单独成一个widget
	 * 暂时只有购物车中会使用它
	 * @param {Object} opt
	 */
    function ItemConfirm(opt) {
        var defaultOpt = {
            dock: null,
            title: "确认删除",
            message: "从购物车中删除此商品？",
            type: "dock",
            confirm: function() {},
            cancel: function() {}
        };
        this.opt = $.extend(defaultOpt, opt);
        this.init();
    }
    ItemConfirm.TEMPLATE = {
        INIT: '<div class="cart-confirm"><div class="wrap {type}"><div class="title"><p>{title}</p><a hre="javascript:;" bind="close">Close</a>' + '</div><div class="msg"><p>{message}</p></div><div class="button"><button name="confirm">确认</button><button name="cancel">取消' + "</button></div></div></div>"
    };
    ItemConfirm.prototype = {
        init: function() {
            var html = ItemConfirm.TEMPLATE.INIT;
            html = html.replace(/\{title\}/g, this.opt.title);
            html = html.replace(/\{message\}/g, this.opt.message);
            html = html.replace(/\{type\}/g, this.opt.type);
            this.el = $(html);
            this.el.appendTo(document.body);
            this.bind();
        },
        bind: function() {
            var confirm = this;
            this.el.find("[bind='close']").click(function() {
                confirm.close();
            });
            this.el.find("button[name='confirm']").click(function() {
                confirm.opt.confirm.apply(this);
                confirm.close();
            });
            this.el.find("button[name='cancel']").click(function() {
                confirm.opt.cancel.apply(this);
                confirm.close();
            });
        },
        show: function() {
            if (!this.opt.dock) {
                throw new Error("The ItemConfirm need have a dock element");
            }
            var dock = $(this.opt.dock), offset = dock.offset();
            if (this.opt.type == "center") {
                this.el.css({
                    top: offset.top + (dock.height() - this.el.height()) / 2,
                    left: offset.left + (dock.width() - this.el.width()) / 2
                });
            } else {
                this.el.css({
                    top: offset.top - this.el.height(),
                    left: offset.left - this.el.width() / 2 + dock.width() / 2
                });
            }
            this.el.show();
        },
        close: function() {
            this.el.hide();
            this.el.remove();
        }
    };
    /**
	 * 与ItemConfirm类似
	 * @param {Object} opt
	 */
    function ItemTip(opt) {
        var defaultOpt = {
            dock: null,
            status: "init",
            message: '<em class="loading"></em>'
        };
        this.opt = $.extend(defaultOpt, opt);
        this.init();
    }
    ItemTip.TEMPLATE = {
        INIT: '<div class="cart-tip"><div class="tip-wrap {status}"><div class="tip-msg">{msg}</div><p class="tip-footer">' + '<a href="javascript:;" bind="close">关闭</a></p></div></div>',
        SUCCESS: "<p>更新成功，共有<b>{count}</b>件商品</p><p>总计：<span>&yen;<em>{salePrice}</em></span></p>",
        UNAVAILABLE: "<p>库存数量不足！</p><p>可购买的数量为:<em>{avalibleQuantity}</em></p>",
        ERROR: "<p>未找到此商品或商品已停用！</p>",
        OFFSELF: "<p>此商品已下架！</p>",
        NUMBER: "<p>错误的商品数量！</p>",
        PARAM: "<p>参数错误！</p>",
        LIMIT: "<p>商品数量超出购买限制！</p>"
    };
    ItemTip.prototype = {
        init: function() {
            var html = ItemTip.TEMPLATE.INIT;
            html = html.replace(/\{msg\}/g, this.opt.message);
            html = html.replace(/\{status\}/g, this.opt.status);
            this.el = $(html);
            //this.el.data("status",this.opt.status);
            this.el.appendTo(document.body);
            this.el.hide();
            this.bind();
        },
        bind: function() {
            var tip = this;
            this.el.find("[bind='close']").click(function() {
                tip.close();
            });
        },
        show: function() {
            var dock = $(this.opt.dock || ".layout"), offset = dock.offset();
            this.el.css({
                top: offset.top - this.el.height(),
                left: offset.left - this.el.width() / 2 + dock.width() / 2
            });
            this.el.fadeIn("slow");
            if (!!this.opt.delayClose) {
                var tip = this;
                setTimeout(function() {
                    tip.close();
                }, this.opt.delayClose);
            }
        },
        change: function(opt) {
            $.extend(this.opt, opt);
            this.show();
        },
        close: function() {
            var tip = this;
            this.el.fadeOut("slow", function() {
                tip.el.remove();
            });
        }
    };
    /**
     * 购物车合并窗口
     * @param {Object} opt
     */
    function MergeWindow(opt) {
        var defaultOpt = {
            dock: null,
            type: "center",
            title: "历史购物车",
            content: "<p>数据加载中……</p>",
            close: function() {},
            merge: function() {},
            addToFavor: function() {}
        };
        this.mask = UI.build("Mask");
        this.opt = $.extend(defaultOpt, opt);
        this.init();
    }
    MergeWindow.ACTION = {
        CONFIRM: 1,
        CANCEL: 2,
        ADDTOFAVOR: 3
    };
    MergeWindow.TEMPLATE = {
        INIT: '<div class="widgets-window merge-window" id="mergeWin" style="display:none;">' + '<div class="widgets-title"><span>{title}</span><a href="javascript:;" bind="close">X</a></div>' + '<div class="widgets-content">{content}</div></div>',
        CONTENT: '<p class="tip"><b class="fb">温馨提示：</b>你曾经在购物车中加入过商品，是否与现在的商品合并？</p>' + '<p class="fb">共有<b class="red">{quantity}</b>件商品</p>' + '<div class="list"><ul class="booklist">{list}</ul></div>' + '<p class="operate"><button bind="merge">添加至购物车</button><button bind="addFavor">不，移入收藏</button></p>',
        ITEM: '<li><a href="{url}" target="_blank" title="{name}" class="img">' + '<img alt="{name}" src="{imageUrl}" class="fl"></a>' + '<p><a href="{url}" target="_blank" title="{sellName}">{sellName}</a><br/>' + '<b class="red fb">文轩价：￥{salePrice}</b><del>定价￥{listPrice}</del></p></li>'
    };
    MergeWindow.prototype = {
        init: function() {
            var html = MergeWindow.TEMPLATE.INIT;
            html = html.replace(/\{title\}/g, this.opt.title);
            html = html.replace(/\{content\}/g, this.opt.content);
            this.el = $(html);
            this.el.appendTo(document.body);
            this.bind();
        },
        bind: function() {
            var merge = this;
            this.el.find("[bind='close']").click(function() {
                merge.opt.close.apply(this);
                merge.close();
            });
            this.el.find("button[bind='merge']").click(function() {
                merge.opt.merge.apply(this);
                merge.close();
            });
            this.el.find("button[bind='addFavor']").click(function() {
                merge.opt.addToFavor.apply(this);
                merge.close();
            });
        },
        show: function() {
            if (!this.opt.dock) {
                throw new Error("The MergeWindow need have a dock element");
            }
            var dock = $(this.opt.dock), offset = dock.offset();
            if (this.opt.type == "center") {
                this.el.css({
                    top: offset.top + (dock.height() - this.el.height()) / 2,
                    left: offset.left + (dock.width() - this.el.width()) / 2
                });
            } else {
                this.el.css({
                    top: offset.top - this.el.height(),
                    left: offset.left - this.el.width() / 2 + dock.width() / 2
                });
            }
            this.mask.show();
            UI.center(this.el);
            this.el.show();
        },
        close: function() {
            this.el.hide();
            this.el.remove();
            this.mask.remove();
        }
    };
    /**
	 * 
	 * 删除历史对象
	 * 在这里只封装了相关DOM操作方法
	 * 如果需要对数据进行持久化操时，扩展此对象即可
	 * 
	 */
    function HistoryList() {
        this.el = $("div.history-list");
        this.isVisible = false;
        $.extend(this, Observable);
        this.init();
    }
    HistoryList.TEMPLATE = {
        ROW: '<div class="trow" data-id="{id}"></div>',
        QUANTITY: '<div class="quantity">{quantity}</div>',
        OPERATION: '<div class="operation"><a href="javascript:;" bind="restore" data-id="{id}" data-quantity="{quantity}">重新购买</a> | <a href="javascript:;" bind="favorite" data-id="{id}">加入收藏</a></div>',
        OUT_OF_STOCK: '<div class="operation">商品已下架或停用 | <a href="javascript:;" bind="favorite" data-id="{id}">加入收藏</a></div>'
    };
    HistoryList.prototype = {
        init: function() {
            this.bind();
        },
        bind: function() {
            var list = this;
            this.el.delegate("a[bind='restore']", "click", function() {
                var row = $(this).parents(".trow"), id = row.data("id"), quantity = $(this).data("quantity");
                list.restore(id, quantity, this);
            }).delegate("a[bind='favorite']", "click", function() {
                var row = $(this).parents(".trow");
                id = row.data("id");
                list.favorite(id, this);
            });
        },
        add: function(row) {
            var product = row.find(".product").clone(), price = row.find(".price").clone(), credits = row.find(".credits").clone(), privilege = row.find(".privilege").clone(), input = row.find("input[name='quantity']"), quantity = input.val(), id = row.find("input[name='item']").val(), newRow, quantityEl, operationLink;
            newRow = $(HistoryList.TEMPLATE.ROW.replace(/\{id\}/g, id));
            if (!row.hasClass("out-of-stock")) {
                quantityEl = $(HistoryList.TEMPLATE.QUANTITY.replace(/\{quantity\}/g, quantity));
                operationLink = $(HistoryList.TEMPLATE.OPERATION.replace(/\{id\}/g, id).replace(/\{quantity\}/g, quantity));
            } else {
                quantityEl = $(HistoryList.TEMPLATE.QUANTITY.replace(/\{quantity\}/g, "&nbsp;"));
                operationLink = $(HistoryList.TEMPLATE.OUT_OF_STOCK.replace(/\{id\}/g, id));
            }
            newRow.hide();
            newRow.append(product).append(price).append(credits).append(privilege).append(quantityEl).append(operationLink);
            newRow.appendTo(this.el);
            newRow.fadeIn("slow");
            if (!this.isVisible) {
                this.el.show("slow");
            }
        },
        remove: function(id) {
            var list = this;
            this.el.find(".trow[data-id='" + id + "']").fadeOut("slow", function() {
                $(this).remove();
                var rows = list.el.find(".trow");
                if (rows.length == 0) {
                    list.isVisible = false;
                    list.el.hide("slow");
                }
            });
        },
        favorite: function(id, el) {
            this.trigger({
                type: CartEvent.FAVORITE_ITEM,
                source: el,
                eventData: {
                    id: id
                }
            });
        },
        restore: function(id, quantity, el) {
            this.trigger({
                type: CartEvent.RESTORE,
                source: el,
                eventData: {
                    id: id,
                    quantity: quantity
                }
            });
        }
    };
    /**
	 * 
	 * 购物车列表视图，可继续细分为多个小的视图
	 * 
	 */
    function CartView() {
        this.isAllSelected = false;
        this.el = $("div#cart-list");
        this.context = $("body.cart");
        this.header = $("div.header");
        this.gobackButton = $("a.continue");
        this.balanceButton = $("a.balance");
        this.validTip = $("p.valid-tip");
        this.checkbox = this.context.find("input[type='checkbox']");
        this.history = new HistoryList();
        this.init();
    }
    CartView.TEMPLATE = {
        ROW: '<div class="trow"><div class="checkbox"><input name="item" type="checkbox" value="{id}" {checked}/></div>' + '<div class="product"><a href="{url}" target="_blank"><img src="{imgUrl}"/></a>' + '<a href="{url}" target="_blank" title="{sellName}">{sellName}</a></div><div class="price">&yen;<em>{salePrice}</em></div>' + '<div class="credits">{points}</div><div class="privilege">&nbsp;</div>' + '<div class="quantity"><a href="javascript:;" title="减少" class="reduce" bind="reduce" data-id="{id}">减少</a>' + '<input name="quantity" value="{quantity}" data-value="{quantity}" data-id="{id}" bind="change"/>' + '<a href="javascript:;" title="增加" class="increase" bind="increase" data-id="{id}">增加</a></div>' + '<div class="operation"><a href="javascript:;" bind="remove" data-id="{id}">删除</a></div>' + "</div>",
        GROUP: '<div class="group" data-shop-id="{id}" data-supply="{supply}"><div class="shop"><b>商家:{name}</b><label class="{className}">新品预售</label><p class="prom-tag"></p></div></div>',
        GIFT: "<p>[赠品] {name} x {num}</p>",
        PROMOTION: '<span data-type="{type}"></span>',
        SAVE_PROMOTION: '<div class="proms"><p data-type="{type}"></p></div>'
    };
    CartView.prototype = {
        init: function() {
            var view = this;
            if (view.isAllSelected) {
                view.checkbox.attr("checked", "checked");
            } else {
                view.checkbox.removeAttr("checked");
            }
            $.extend(view, Observable);
            this.timer = setTimeout(function() {
                view.watch();
            }, 2e3);
            this.bind();
            this.render();
        },
        /**
		 * 
		 * 渲染促销信息
		 * 
		 */
        render: function() {
            var proms = this.el.find(".proms p,.proms li");
            proms.each(function() {
                var el = $(this), data = el.data("prom-price");
                if (!!data) {
                    data = eval("(" + data + ")");
                    el.addClass(CartDataWrap.PromTemplate[data.promType].style);
                    el.html(CartDataWrap.getPromMessage(data));
                    if (data.promType == 20004) {
                        el.parents(".proms").addClass("save-label");
                    }
                }
            });
            proms.filter("li:first").addClass("first");
        },
        /**
		 * 
		 * 用户数量修改的监听器
		 * 当用户输入正确的数字时，监听器会触发更新事件
		 * 应用捕获此事件后，通知模型沙箱进行服务端处理
		 *  
		 */
        watch: function() {
            var view = this;
            this.el.find("input[name='quantity']").each(function() {
                var el = $(this), quantity = parseInt(el.val()), original = el.data("value");
                if (quantity != original) {
                    if (!isNaN(quantity)) {
                        el.data("value", quantity);
                        view.trigger({
                            type: CartEvent.UPDATE,
                            source: el,
                            original: original,
                            quantity: el.val(),
                            eventData: el.data()
                        });
                    } else {
                        el.val(original);
                    }
                }
            });
            this.timer = setTimeout(function() {
                view.watch();
            }, 1500);
        },
        bind: function() {
            var view = this;
            this.el.delegate("a[bind]", "click", function(e) {
                var el = this, event = $(this).attr("bind");
                event = event ? event.toUpperCase() : "";
                if (typeof CartEvent[event] != "undefined") {
                    view.trigger({
                        type: CartEvent[event],
                        source: el,
                        eventData: $(el).data()
                    });
                }
            });
            this.el.delegate("a[bind='arrival_notice']", "click", function() {
                view.trigger({
                    type: ProductEvent.ARRIVAL_NOTICE,
                    source: this,
                    eventData: $(this).data()
                });
            });
            this.checkbox.filter("[name='all']").click(function() {
                view.selectAll();
            });
            this.el.delegate("input[name='quantity']", "paste", function(e) {
                view.paste(e);
            }).delegate("input[name='quantity']", "keypress", function(e) {
                view.keypress(this, e);
            }).delegate("input[name='item'][type='checkbox']", "click", function(e) {
                view.selectItem(this);
            });
            this.gobackButton.click(function() {
                view.goback();
            });
            this.balanceButton.click(function() {
                view.balance(this);
            });
            this.history.on(CartEvent.RESTORE, function(e) {
                view.trigger(e);
            }).on(CartEvent.FAVORITE_ITEM, function(e) {
                view.trigger(e);
            });
        },
        goback: function() {
            var url = document.referrer;
            if (url.length > 0 && url.indexOf("/customer") == -1 && url.indexOf("/shoppingcart") == -1) {
                window.location = url;
            } else {
                window.location = "http://www.winxuan.com";
            }
        },
        balance: function(el) {
            this.trigger({
                type: CartEvent.BALANCE,
                source: el
            });
        },
        /**
		 * 
		 * 监听用户输入事件,如果用户输入的数量不是数字，是其它字符时忽略掉当前操作
		 * 
		 * @param {Object} el
		 * @param {Object} e
		 */
        keypress: function(el, e) {
            var view = this, el = $(el), code = e.charCode || e.keyCode, isDigit = code >= 48 && code <= 57;
            if (!isDigit) {
                if (e.keyCode == 13 && el.val() != el.data("value")) {
                    el.data("value", el.val());
                    setTimeout(function() {
                        view.trigger({
                            type: CartEvent.UPDATE,
                            source: el,
                            quantity: $(el).val(),
                            eventData: $(el).data()
                        });
                    }, 1);
                } else if (e.charCode == 0) {
                    return;
                } else {
                    e.preventDefault();
                    e.stopPropagation();
                    return;
                }
            } else {
                clearTimeout(this.timer);
                this.timer = setTimeout(function() {
                    view.watch();
                }, 1500);
            }
        },
        /**
		 * 针对IE下的粘贴事件，这里此方法可忽略
		 * 意义不大  
		 * @param {Object} e
		 */
        paste: function(e) {
            var isDigit = true;
            if (!!window.clipboardData) {
                isDigit = window.clipboardData.getData("text").match(/\D/);
            }
            if (!isDigit) {
                e.preventDefault();
                e.stopPropagation();
            }
        },
        /**
		 * DOM层全选操作
		 */
        selectAll: function() {
            this.isAllSelected = !this.isAllSelected;
            if (this.isAllSelected) {
                this.context.find("input[type='checkbox']:not(:disabled)").attr("checked", "checked");
            } else {
                this.context.find("input[type='checkbox']:not(:disabled)").removeAttr("checked");
            }
        },
        selectItem: function(el) {
            if (!el.checked) {
                this.isAllSelected = false;
                this.checkbox.filter("[name='all']").removeAttr("checked");
            }
        },
        getSelectedItem: function() {
            var items = [];
            this.el.find("input[type='checkbox']").filter("[name='item']:checked").each(function() {
                var id = $(this).val();
                if (!!id) {
                    items.push(id);
                }
            });
            return items;
        },
        getItemQuantity: function(id) {
            var input = this.el.find("input[name='quantity'][data-id='" + id + "']");
            return parseInt(input.val());
        },
        getItemDock: function(id) {
            var dock = this.el.find("input[name='quantity'][data-id='" + id + "']");
            if (dock.length == 0) {
                dock = this.el.find("div.quantity em[data-id='" + id + "']");
            }
            if (dock.length > 0) {
                return dock;
            }
            return null;
        },
        remove: function(data) {
            var view = this;
            if (data && data.itemList) {
                if (data.itemList.length > 1) {
                    view.closeTip();
                    $.each(data.itemList, function() {
                        view.removeItemRow(this);
                    });
                } else {
                    view.closeTip();
                    view.removeItemRow(data.itemList[0]);
                }
                view.refresh(new CartDataWrap(data));
            } else {
                throw new Error("shoppingcart view remove method-Todo:fix this bug!");
            }
        },
        removeItemRow: function(id) {
            var dock = this.getItemDock(id), row = dock.parents(".trow"), next = row.next(), view = this, group = row.parents(".group");
            if (next.hasClass("gift")) {
                next.fadeOut("slow");
            }
            row.fadeOut("slow", function() {
                view.history.add(row);
                row.remove();
                if (group.find(".trow").length == 0) {
                    group.fadeOut("slow", function() {
                        group.remove();
                        view.checkGroup();
                    });
                }
            });
            this.validTip.fadeOut();
        },
        update: function(data) {
            var data = new CartDataWrap(data, ItemTip.TEMPLATE), el = this.getItemDock(data.id), msg = data.getMessage(), status = data.getStatusString(), row = el.parents(".trow");
            if (data.getStatusInt() === 1) {
                if (data.refresh) {
                    el.val(data.getUpdateItem().quantity);
                    el.data("value", data.getUpdateItem().quantity);
                }
            } else {
                el.val(data.getAvailable());
                el.data("value", data.getAvailable());
            }
            this.showTip({
                dock: el,
                status: status,
                message: msg,
                delayClose: 3e3
            });
            this.refresh(data, row);
        },
        checkGroup: function() {
            var group = this.el.find(".group");
            if (group.length > 1) {
                this.context.find("div.progress").addClass("split");
            } else {
                this.context.find("div.progress").removeClass("split");
            }
        },
        refresh: function(data, row) {
            var brief = this.context.find(".brief"), count = this.context.find(".count");
            brief.find(".save em").html(data.getSaveMoney());
            brief.find("span b").html(data.getCount());
            brief.find(".price em").html(data.getBriefPrice());
            count.find(".price em").html(data.getCountPrice());
            if (!!row) {
                row.find(".credits").html(data.getPoints());
            }
            this.checkGroup();
            this.reloadPromotion(data);
        },
        reloadPromotion: function(data) {
            var proms = data.getProms(), view = this;
            if (proms && proms.length !== 0) {
                $.each(proms, function() {
                    var group = view.el.find(".group[data-shop-id='" + this.shopId + "'][data-supply='" + this.supplyTypeCode + "']"), el = group.find(".proms [data-type='" + this.promotionPrice.promType + "']"), parent = el.parents(".proms");
                    el.html(CartDataWrap.getPromMessage(this.promotionPrice));
                    if (this.promotionPrice.promType == 20004 && !parent.hasClass("save-label")) {
                        parent.addClass("save-label");
                    }
                });
            }
        },
        showTip: function(opt) {
            this.closeTip();
            this.itemTip = new ItemTip(opt);
            this.itemTip.show();
        },
        closeTip: function() {
            if (!!this.itemTip) {
                this.itemTip.close();
                this.itemTip = null;
            }
        },
        reset: function(id) {
            var el = this.el.find("input[name='quantity'][data-id='" + id + "']"), defaultValue = el.data("value");
            el.val(defaultValue);
        },
        confirm: function(opt) {
            if (!!this.itemConfirm) {
                this.itemConfirm.close();
                this.itemConfirm = null;
            }
            this.itemConfirm = new ItemConfirm(opt);
            this.itemConfirm.show();
        },
        renderRow: function(item) {
            var html = CartView.TEMPLATE.ROW, checked = "";
            if (this.isAllSelected) {
                checked = 'checked="checked"';
            }
            html = html.replace(/\{checked\}/g, checked);
            html = html.replace(/\{id\}/g, item.productSaleId);
            html = html.replace(/\{sellName\}/g, item.sellName);
            html = html.replace(/\{imgUrl\}/g, item.imageUrl);
            html = html.replace(/\{salePrice\}/g, item.salePrice.toFixed(2));
            html = html.replace(/\{points\}/g, item.points);
            html = html.replace(/\{quantity\}/g, item.quantity);
            html = html.replace(/\{url\}/g, item.url);
            if (!!item.gifts && item.gifts.length > 0) {
                $.each(item.gifts, function() {
                    html += CartView.TEMPLATE.GIFT.replace(/\{name\}/g, this.giftName).replace(/\{num\}/g, this.sendNum);
                });
            }
            return html;
        },
        buildGroup: function(data, item) {
            var className = item.supplyTypeCode == 13102 ? "new" : "", group = $(CartView.TEMPLATE.GROUP.replace(/\{id\}/g, item.shop.id).replace(/\{supply\}/g, item.supplyTypeCode).replace(/\{name\}/g, item.shop.shopName).replace(/\{className\}/g, className)), proms = data.getProms(item.shop.id, item.supplyTypeCode), saveProm = null;
            $.each(proms, function() {
                var html, promPrice = this.promotionPrice;
                if (promPrice.promType != 20004 && promPrice.promType != 20008) {
                    html = CartView.TEMPLATE.PROMOTION.replace(/\{type\}/g, promPrice.promType);
                    $(html).appendTo(group.find(".shop"));
                } else {
                    if (promPrice.promType == 20004) {
                        html = CartView.TEMPLATE.SAVE_PROMOTION.replace(/\{type\}/g, promPrice.promType);
                        $(html).appendTo(group);
                    }
                }
            });
            return group;
        },
        arrivalNotice: function(data) {
            var el, view = this, list = data.list;
            this.closeTip();
            $.each(list, function() {
                el = view.el.find("div.quantity em[data-id='" + this + "']");
                if (el.length > 0) {
                    view.trigger({
                        type: CartEvent.REMOVE,
                        source: el[0],
                        eventData: el.data()
                    });
                }
            });
        },
        append: function(data) {
            if (!!this.getItemDock(data.sourceId)) {
                data.refresh = true;
                this.update(data);
                return;
            }
            var data = new CartDataWrap(data, ItemTip.TEMPLATE), item = data.getUpdateItem(), view = this, html, row, el, wrap;
            if (data.getStatusInt() === 1) {
                if (!!item) {
                    html = this.renderRow(item);
                    row = $(html);
                    var group = this.el.find("div.group[data-shop-id='" + item.shop.id + "'][data-supply='" + item.supplyTypeCode + "']");
                    if (group.length == 0) {
                        group = this.buildGroup(data, item);
                        group.append(row);
                        group.insertBefore(this.el.find(".tfoot"));
                        wrap = group;
                    } else {
                        row.appendTo(group);
                        wrap = row;
                    }
                    el = this.getItemDock(data.id);
                    wrap.hide().fadeIn("slow", function() {
                        view.history.remove(data.id);
                    });
                    view.showTip({
                        dock: el,
                        status: data.getStatusString(),
                        message: data.getMessage(),
                        delayClose: 3e3
                    });
                    view.refresh(data);
                } else {
                    throw new Error("Can not found element by id:" + data.id);
                }
            } else {
                this.itemTip.change({
                    status: data.getStatusString(),
                    message: data.getMessage(),
                    delayClose: 3e3
                });
            }
        },
        /*
		 * 判断是否需要合并购物车
		 */
        query: function(data) {
            var data = new CartDataWrap(data, MergeWindow.TEMPLATE), itemList = data.getHistoryList(), view = this;
            if (!!itemList && itemList.length > 0) {
                this.showMergeWin({
                    dock: "body",
                    content: data.getContent(),
                    close: function() {
                        view.trigger({
                            type: CartEvent.MERGE,
                            action: MergeWindow.ACTION.CANCEL
                        });
                    },
                    merge: function() {
                        view.trigger({
                            type: CartEvent.MERGE,
                            action: MergeWindow.ACTION.CONFIRM
                        });
                    },
                    addToFavor: function() {
                        view.trigger({
                            type: CartEvent.MERGE,
                            action: MergeWindow.ACTION.ADDTOFAVOR
                        });
                    }
                });
            } else {
                view.trigger({
                    type: CartEvent.BALANCE
                });
            }
        },
        /*
		 * 显示合并购物车窗口
		 */
        showMergeWin: function(opt) {
            this.closeMergeWin();
            this.mergeWindow = new MergeWindow(opt);
            this.mergeWindow.show();
        },
        /*
         * 关闭合并购物车窗口
         */
        closeMergeWin: function() {
            if (!!this.mergeWindow) {
                this.mergeWindow.close();
                this.mergeWindow = null;
            }
        },
        /*
		 * 合并购物车
		 */
        merge: function(data) {
            this.closeMergeWin();
            this.refresh(new CartDataWrap(data));
            this.trigger({
                type: CartEvent.BALANCE
            });
        },
        /*
		 *验证购物车数据有效性
		 */
        valid: function() {
            var unavailable = this.el.find(".out-of-stock").html();
            validTip = this.validTip;
            if (!!unavailable) {
                validTip.fadeIn();
                return true;
            }
            return false;
        }
    };
    return CartView;
});
/**
 * 
 * 用户登录/注册框View
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define("v1/src/view/signin-signup-debug", [ "jQuery-debug", "../event/observable-debug", "../event/user-debug", "../util/ui-debug" ], function(require) {
    var $ = require("jQuery-debug"), Observable = require("../event/observable-debug"), UserEvent = require("../event/user-debug"), UI = require("../util/ui-debug");
    /**
	 * 
	 * Panel 对象，注册和登录都由此对象保持状态
	 * 
	 * @param {Object} template
	 */
    function Panel(template, ajaxFunc) {
        this.el = $(template);
        this.verifyImg = this.el.find('img[name="verify"]');
        this.form = this.el.find("form");
        this.button = this.el.find("button");
        this.msgEl = this.el.find("p.message");
        this.otherEl = this.el.find("div.other");
        this.ajaxFunc = ajaxFunc || function() {};
        $.extend(this, Observable);
        this.init();
    }
    Panel.prototype = {
        init: function() {
            var panel = this;
            this.el.delegate("[bind]", "click", function(e) {
                panel.trigger({
                    type: $(this).attr("bind"),
                    source: this
                });
                e.preventDefault();
                e.stopPropagation();
            });
            this.otherEl.delegate("a", "click", function(e) {
                if (this.href.indexOf("returnUrl") == -1 && this.href != "javascript:;") {
                    this.href += "?returnUrl=" + encodeURI(document.location.href);
                }
            });
            this.el.delegate("form", "keypress", function(e) {
                panel.keypress(this, e);
            });
            this.loadVerify();
            this.bind();
        },
        loadVerify: function() {
            if (this.verifyImg.length > 0 && this.verifyImg.is(":visible")) {
                this.verifyImg.attr("src", "http://passport.winxuan.com/verifyCode?" + new Date().getTime());
            }
        },
        showVerify: function() {
            this.verifyImg.parents(".verify").show();
            if (!this.verifyImg.data("loaded")) {
                this.loadVerify();
                this.verifyImg.data("loaded", true);
            }
        },
        hideVerify: function() {
            this.verifyImg.parent(".verify").hide();
        },
        keypress: function(form, e) {
            var name = $(form).attr("name"), eventType;
            if (e.keyCode == 13) {
                eventType = name == "signin" ? UserEvent.SIGNIN_SUBMIT : UserEvent.SIGNUP_SUBMIT;
                this.trigger({
                    type: eventType,
                    source: form
                });
            }
        },
        bind: function() {
            var panel = this;
            this.el.find("input").focus(function(e) {
                panel.focus(this);
            }).blur(function(e) {
                panel.blur(this);
            });
            panel.on(UserEvent.SIGNUP_SUBMIT, function(e) {
                panel.lock();
            }).on(UserEvent.SIGNIN_SUBMIT, function(e) {
                panel.lock();
            }).on(UserEvent.RELOAD_VERIFY, function(e) {
                panel.loadVerify();
            });
        },
        focus: function(el) {
            var el = $(el);
            el.removeClass("placeholder");
            this.showTip(el);
            this.msgEl.hide();
        },
        blur: function(el) {
            var el = $(el), value = el.val(), type = el.attr("type"), ajax = el.attr("ajax");
            if (type == "text" || type == "password") {
                if (value == el.attr("placeholder") || value.length == 0) {
                    if (!el.hasClass("placeholder")) {
                        el.addClass("placeholder");
                    }
                }
            }
            this.hideTip(el);
            if (ajax && value.length > 0 && this.check(el)) {
                this.ajaxValid(el);
            }
        },
        ajaxValid: function(el) {
            this.ajaxFunc(el);
        },
        lock: function() {
            if (this.validate()) {
                this.button.data("text", this.button.text());
                this.el.find(":input").attr("disabled", "disabled");
                this.el.find(".loading").css("visibility", "visible");
                this.button.text("处理中...");
            }
        },
        validate: function() {
            var fields = this.form.find("input"), panel = this;
            panel.isValidated = true;
            fields.each(function() {
                var valid = false;
                if ($(this).is(":visible")) {
                    if (this.validity) {
                        valid = this.validity.valid;
                    } else {
                        valid = panel.check(this);
                    }
                    if (!valid) {
                        panel.isValidated = false;
                        panel.showRule(this);
                        return false;
                    }
                }
            });
            return panel.isValidated;
        },
        showRule: function(el, msg) {
            var el = $(el), name = el.attr("name"), tip, message = msg || el.attr("message");
            if (name) {
                tip = this.el.find("p." + name);
                tip.data("init-text", tip.text());
                if (message) {
                    tip.html(message);
                    tip.addClass("error");
                }
                this.showTip(el);
            }
        },
        showTip: function(el) {
            var name = el.attr("name");
            if (name) {
                this.el.find("p." + name).css({
                    visibility: "visible"
                });
            }
        },
        hideTip: function(el) {
            var name = el.attr("name"), tip, initText;
            if (name) {
                this.el.find("p." + name).css("visibility", "hidden");
                tip = this.el.find("p." + name);
                initText = tip.data("init-text");
                if (initText) {
                    tip.html(initText);
                }
                tip.removeClass("error");
            }
        },
        check: function(el) {
            var el = $(this), reg, required = el.attr("required"), pattern = el.attr("pattern");
            if (required) {
                reg = new RegExp(pattern);
                if (reg.test(el.val())) {
                    return true;
                } else {
                    this.showTip(el);
                    return false;
                }
            }
            return true;
        },
        unlock: function() {
            this.el.find(":input").removeAttr("disabled");
            this.el.find(".loading").css("visibility", "hidden");
            this.button.text(this.button.data("text"));
        },
        appendTo: function(target) {
            this.el.appendTo(target);
        },
        getFormData: function() {
            var data = {};
            this.form.find("input").each(function() {
                var el = $(this), name = el.attr("name"), value = el.val();
                if (!!name && name.length > 0) {
                    data[name] = value;
                }
            });
            return data;
        },
        submit: function(ref) {
            var action = this.form.attr("action"), name = this.form.find("input[name='name']"), email = this.form.find("input[name='email']");
            name.val(email.val());
            if (action.indexOf("returnUrl") == -1) {
                action += "?returnUrl=" + encodeURIComponent(ref || document.URL);
                action += "&" + $.param(this.getFormData());
                this.form.attr("action", action);
                this.form.submit();
            }
        },
        error: function(msg) {
            this.msgEl.show();
            this.msgEl.html(msg);
            this.unlock();
            this.showVerify();
            this.loadVerify();
        },
        show: function() {
            this.el.show();
            this.loadVerify();
        },
        hide: function() {
            this.el.hide();
        }
    };
    function View(footer) {
        var view = this;
        this.el = $(View.TEMPLATE.BASE.replace(/\{footer\}/g, footer));
        this.panels = this.el.find(".panels");
        this.closeButton = this.el.find(".close");
        this.navTabs = this.el.find(".nav-tab li");
        this.signinPanel = new Panel(View.TEMPLATE.SIGNIN, function(el) {
            view.signinNameValidate(this, el);
        });
        this.signupPanel = new Panel(View.TEMPLATE.SIGNUP, function(el) {
            view.signupNameValidate(this, el);
        });
        this.mask = UI.build("Mask");
        this.currentState = "signin";
        this.signinPanel.appendTo(this.panels);
        this.signupPanel.appendTo(this.panels);
        $.extend(this, Observable);
        this.init();
    }
    View.TEMPLATE = {
        BASE: '<div id="mini-signin"><div class="wrap">' + '<ul class="nav-tab"><li class="selected" tabindex="1">登录</li><li tabindex="2">注册</li></ul>' + '<div class="panels"></div>' + '<div class="foot-panel">{footer}</div>' + '<div class="close"><a href="javascript:;">关闭</a></div>' + "</div></div>",
        SIGNIN: '<div class="signin-panel"><form name="signin" action="https://passport.winxuan.com/signin" method="GET">' + '<fieldset><div><label>帐户</label><input name="name" type="text" class="placeholder" placeholder="帐户名或Email地址" required="required" pattern=".{1,}" ajax="https://passport.winxuan.com/needVerifyCode"/></div>' + '<p class="name tip">请在上方输入您的帐户名/Email</p>' + '<div><label>密码</label><input name="password" type="password" class="placeholder" placeholder="您的文轩网密码" required="required" pattern=".{4,}"/><a href="https://passport.winxuan.com/findPassword" target="_blank">忘记密码了？</a></div>' + '<p class="password tip">请在上方输入您的密码</p>' + '<div class="verify"><div><label>验证码</label><input type="text" name="verifyCode" required="required" pattern="[A-Za-z0-9_-]{4}"/><img name="verify"/><a href="#" bind="reload_verify">换一张</a></div>' + '<p class="verifyCode tip">请输入右上方图片中的文字</p></div>' + '</fieldset><p class="message"></p>' + '<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signin_submit">登录</button>' + '<b>新用户？</b><a href="javascript:;" bind="user_signup">立即注册</a></div>' + '</form><div class="other">' + "<p>使用下面合作网站帐号登录文轩网</p>" + '<ul><li class="qq"><a href="https://passport.winxuan.com/auth/qq" target="signin">QQ</a></li>' + '<li class="alipay"><a href="https://passport.winxuan.com/auth/alipay" target="signin">支付宝</a></li>' + '<li class="sina"><a href="https://passport.winxuan.com/auth/weibo" target="signin">新浪微博</a></li>' + '<li class="douban"><a href="https://passport.winxuan.com/auth/douban" target="signin">豆瓣</a></li></ul>' + "</div></div>",
        SIGNUP: '<div class="signup-panel"><form name="signup" action="https://passport.winxuan.com/signup" method="POST" class="signup"><input type="hidden" name="name"/><fieldset>' + '<div><label>邮箱</label><input name="email" type="text" class="placeholder" placeholder="电子邮箱/Email地址" required="required" pattern="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$" message="请按正确的Email格式填写" ajax="https://passport.winxuan.com/emailExist"/></div>' + '<p class="email tip">请输入您的Email，做为您的帐户名</p>' + '<div><label>密码</label><input name="password" class="placeholder" type="password" placeholder="请输入您的密码" required="required" pattern=".{4,12}" message="密码由4-12位字符、符号组成"/></div>' + '<p class="password tip">请输入您的文轩网密码</p>' + '<div><label>确认密码</label><input name="passwordConfirm" class="placeholder" type="password" placeholder="请重复输入上方的密码" required="required" pattern=".{4,12}" message="密码由4-12位字符、符号组成"/></div>' + '<p class="passwordConfirm tip">请重复输入上方的登录密码</p>' + '<div class="verify"><label>验证码</label><input type="text" name="verifyCode" required="required" pattern="[A-Za-z0-9_-]{4}"/><img name="verify"/><a href="#" bind="reload_verify">换一张</a></div>' + '<p class="verifyCode tip">请输入右上方图片中的文字</p>' + '<div class="agreement"><input type="checkbox" name="agree" required="required" value="1"/><p>我已阅读并同意 <a href="http://www.winxuan.com/help/terms.html" target="terms">《文轩网交易条款》</a></p></div>' + '<p class="agree tip">请阅读并同意文轩网交易条款</p><p class="message"></p></fieldset>' + '<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signup_submit">立即注册</button></div>' + "</form></div>"
    };
    View.prototype = {
        init: function() {
            this.el.hide();
            this.el.appendTo(document.body);
            this.bind();
        },
        bind: function() {
            var view = this;
            view.closeButton.click(function() {
                view.close();
            });
            view.navTabs.click(function() {
                view.toggle(this);
            });
            view.signinPanel.on(UserEvent.SIGNUP, function() {
                view.toggle(view.navTabs.filter(":not(.selected)"));
            }).on(UserEvent.SIGNIN_SUBMIT, function(e) {
                if (view.signinPanel.isValidated) {
                    view.trigger({
                        type: e.type,
                        eventData: view.signinPanel.getFormData()
                    });
                }
            });
            view.signupPanel.on(UserEvent.SIGNUP_SUBMIT, function(e) {
                if (view.signupPanel.isValidated) {
                    view.trigger({
                        type: e.type,
                        eventData: view.signupPanel.getFormData()
                    });
                }
            });
        },
        toggle: function(el) {
            var el = $(el), idx = el.attr("tabindex");
            if (idx == "1") {
                this.currentState = "signin";
                this.signinPanel.show();
                this.signupPanel.hide();
            } else {
                this.currentState = "signup";
                this.signupPanel.show();
                this.signinPanel.hide();
            }
            this.navTabs.removeClass("selected");
            el.addClass("selected");
        },
        error: function(msg) {
            if (this.currentState == "signin") {
                this.signinPanel.error(msg);
            } else {
                this.signupPanel.error(msg);
            }
        },
        submit: function(ref) {
            this.signupPanel.submit(ref);
        },
        show: function() {
            this.mask.show();
            UI.center(this.el);
            this.el.show();
        },
        close: function() {
            this.el.remove();
            this.mask.remove();
        },
        signupNameValidate: function(panel, el) {
            var img = el.parent().find("img"), url = el.attr("ajax");
            if (img.length === 0) {
                img = $("<img src='http://static.winxuancdn.com/css/images/loading_16x16.gif' width='16' height='16'/>");
                img.insertAfter(el);
            }
            $.getJSON(url + "?" + el.attr("name") + "=" + el.val() + "&format=jsonp&callback=?", function(data) {
                if (!data.status) {
                    img.attr("src", "http://static.winxuancdn.com/css/images/success.gif");
                    el.data("status", "validated");
                } else {
                    img.attr("src", "http://static.winxuancdn.com/css/images/error.gif");
                    panel.isValidated = false;
                    panel.showRule(el[0], "此邮箱已被注册，请更换一个邮箱");
                    el.data("status", "existed");
                }
            });
        },
        signinNameValidate: function(panel, el) {
            var img = el.parent().find("img"), url = el.attr("ajax");
            if (img.length == 0) {
                img = $("<img src='http://static.winxuancdn.com/css/images/loading_16x16.gif' width='16' height='16'/>");
                img.insertAfter(el);
            }
            $.getJSON(url + "?username=" + el.val() + "&format=jsonp&callback=?", function(data) {
                if (!data.needVerifyCode) {
                    panel.hideVerify();
                } else {
                    panel.showVerify();
                }
                img.remove();
            });
        }
    };
    return View;
});
define("v1/src/wml/shoppingcart-combo-debug", [ "jQuery-debug", "../model/favorite-model-debug", "../event/observable-debug", "../event/sandbox-debug", "../event/security-debug", "../../etc/config-debug", "../event/shoppingcart-debug", "../event/pagination-debug", "../wml/signin-signup-debug", "../view/signin-signup-debug", "../event/user-debug", "../util/ui-debug", "../model/signin-signup-debug", "../view/shoppingcart-combo-debug" ], function(require) {
    var $ = require("jQuery-debug"), FavoriteModel = require("../model/favorite-model-debug"), CartEvent = require("../event/shoppingcart-debug"), SandboxEvent = require("../event/sandbox-debug"), SecurityEvent = require("../event/security-debug"), PaginationEvent = require("../event/pagination-debug"), SigninModule = require("../wml/signin-signup-debug"), ComboView = require("../view/shoppingcart-combo-debug");
    function ComboModule(cartModel, defPanel) {
        this.cartModel = cartModel;
        this.comboView = new ComboView();
        this.initPanel = defPanel || ComboView.PANEL.FAVORITE;
        this.favModel = new FavoriteModel();
        this.isLogin = false;
        this.pageOpt = {
            index: 0,
            page: 1,
            size: 6
        };
    }
    ComboModule.prototype = {
        bindView: function() {
            var view = this.comboView, app = this;
            view.on(CartEvent.ADD, function(e) {
                app.addToCart(e);
            }).on(PaginationEvent.PAGE_TURNING, function(e) {
                app.pageTurning(e);
            }).on(SecurityEvent.RESOURCES_PROTECTED, function(e) {
                app.showLogin();
            });
        },
        bindModel: function() {
            var favModel = this.favModel, cartModel = this.cartModel, app = this;
            favModel.on(SecurityEvent.RESOURCES_PROTECTED, function(e) {
                app.login(e);
            }).on(SandboxEvent.NOTIFY, function(e) {
                app.notify(e);
            });
            cartModel.on(SandboxEvent.NOTIFY, function(e) {
                app.restoreButton(e);
            });
        },
        pageTurning: function(e) {
            if (this.isLogin) {
                var page = e.eventData.page;
                this.comboView.changeState("loading");
                this.favModel.list($.extend(this.pageOpt, {
                    page: page
                }));
            } else {
                this.showLogin();
            }
        },
        showLogin: function(callback) {
            var combo = this, callback = callback || function() {
                combo.isLogin = true;
                combo.comboView.changeState("loading");
                combo.favModel.list();
            };
            /*
			SigninModule=combo.SigninModule;
			if(!SigninModule){
				seajs.use("wml/signin-signup",function(Module){
					SigninModule=combo.SigninModule=Module;
					(new SigninModule({signin:callback})).show();
				});
			}else{
				(new SigninModule({signin:callback})).show();
			}
			*/
            new SigninModule({
                signin: callback
            }).show();
        },
        addToCart: function(e) {
            var id = e.eventData.id;
            this.cartModel.append(id);
        },
        init: function() {
            this.bindView();
            this.bindModel();
            if (this.initPanel == ComboView.PANEL.FAVORITE) {
                this.favModel.list();
            }
        },
        addToFavorite: function(list) {
            var combo = this;
            if (this.isLogin) {
                this.comboView.changeState("loading");
                this.favModel.append(list);
            } else {
                this.showLogin(function() {
                    combo.isLogin = true;
                    combo.favModel.append(list);
                });
            }
        },
        start: function() {
            this.init();
        },
        notify: function(e) {
            var method = this.comboView[e.action];
            if (typeof method == "function") {
                method.call(this.comboView, e.eventData);
            }
            this.isLogin = true;
        },
        login: function(e) {
            this.isLogin = false;
            if (e.action == FavoriteModel.ACTION.APPEND) {
                alert("TODO favorite append login");
            } else {
                this.comboView.changeState("login");
            }
        },
        restoreButton: function(e) {
            var id = e.eventData.sourceId;
            this.comboView.restoreButton(id);
        },
        error: function() {
            throw new Error("TODO Handle this Error");
        },
        show: function() {
            this.comboView.show();
        }
    };
    return ComboModule;
});
define("v1/src/wml/signin-signup-debug", [ "jQuery-debug", "../view/signin-signup-debug", "../event/observable-debug", "../event/user-debug", "../util/ui-debug", "../event/sandbox-debug", "../model/signin-signup-debug", "../../etc/config-debug" ], function(require) {
    var $ = require("jQuery-debug"), View = require("../view/signin-signup-debug"), UserEvent = require("../event/user-debug"), SandboxEvent = require("../event/sandbox-debug"), SigninModel = require("../model/signin-signup-debug");
    function SigninModule(cfg) {
        this.opt = {
            signin: function() {},
            signup: function() {},
            footer: "&nbsp;",
            referrer: null
        };
        $.extend(this.opt, cfg);
        this.view = new View(this.opt.footer);
        this.model = new SigninModel();
        this.init();
    }
    SigninModule.MESSAGE = {
        "0": "请输您的用户名/Email及您的密码",
        "2": "用户名或密码错误",
        "3": "用户名或密码错误",
        "4": "您的帐户已被锁定，请与我们的客服联系",
        "5": "验证码错误",
        password: "您输入的两次密码不符，请确认后提交"
    };
    SigninModule.prototype = {
        init: function() {
            this.bindView();
            this.bindModel();
        },
        bindModel: function() {
            var app = this;
            this.model.on(SandboxEvent.NOTIFY, function(e) {
                app.notify(e);
            });
        },
        bindView: function() {
            var app = this;
            this.view.on(UserEvent.SIGNIN_SUBMIT, function(e) {
                app.signin(e.eventData);
            });
            this.view.on(UserEvent.SIGNUP_SUBMIT, function(e) {
                app.signup(e.eventData);
            });
        },
        show: function() {
            this.view.show();
        },
        signin: function(data) {
            this.model.signin(data);
        },
        signup: function(data) {
            if (data.password == data.passwordConfirm) {
                this.view.submit(this.opt.referrer);
            } else {
                this.view.error(SigninModule.MESSAGE["password"]);
            }
        },
        notify: function(e) {
            var data = e.eventData;
            if (data && data.status == "1") {
                this.view.close();
                if (typeof this.opt[e.action] == "function") {
                    this.opt[e.action]();
                }
            } else {
                this.view.error(SigninModule.MESSAGE[data.status]);
            }
        }
    };
    return SigninModule;
});
define("v1/etc/config-debug", [], function(require) {
    return {
        portalServer: "http://www.winxuan.com",
        searchServer: "http://search.winxuan.com",
        passportServer: "https://passport.winxuan.com",
        consoleServer: "http://console.winxuan.com",
        imgServer: "http://static.winxuancdn.com",
        /*
		apiServer: "http://api.winxuan.com/v1",
		*/
        apiServer: "http://www.winxuan.com"
    };
});