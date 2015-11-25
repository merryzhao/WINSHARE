define(function(require){
    var $ = require("jQuery"), conf = require("config"), cacheManager = require("cms-cachemanager"),
    template = {
        frame: '<div class="widget:window"><div class="widget:wrap"><div class="widget:title"><label></label><div><a href="javascript:;" bind="close">关闭</a></div></div><div class="widget:content"><p class="pre-loading">loading...</p></div></div></div>',
        configui: '<div class="ed:link"><table><caption>当前配置列表</caption><thead><tr><th class="description">描述</th><th class="available">启用</th><th class="action">操作</th></tr></thead><tbody><tr></tbody></table></div></div>',
        linkItem: '<tr class="datalist" param="{id}"><td><div class="description">{description}</div></td><td><div class="value">{value}</div></td><td class="action"><a href="javascript:;" param="{id}" class="edit">保存</a></td></tr>'
    };
    require("jQuery-drag")($);
    var view = {
        init: function(){
			if(frame.isopen){
				return;
			}
            frame.init();
            this.el = $(template.configui);
            this.body = this.el.find("table tbody");
            this.getData();
            this.bind();
        },
        bind: function(){
            var obj = this;
            this.el.delegate("a.edit", "click", function(){
                obj.updateconfig($(this))
            });
            
        },
        updateconfig: function(obj){
            var tr = obj.parents("tr");
            var instance = this;
            var checkbox = tr.find("input:checkbox");
            var valueB = (checkbox.attr("checked") == "checked");
            var url = conf.consoleServer + "/fragment/updateconfig/" + obj.attr("param") + "?format=json";
            var data = {
                value: valueB
            };
            $.post(url, data, function(d){
                var fragmentid = d.cmsconfig.fragment.id;
                instance.clearCacheByKey(fragmentid, data);
            }, "json");
            
            
        },
        clearCacheByKey: function(fragmentid, data){
            var fragment = $("[fragment=" + fragmentid + "]");
            var key = fragment.attr("cachekey");
            cacheManager.flushFragmentCache(key);
        },
        getData: function(){
            var url = conf.portalServer + "/fragment/config?format=jsonp&callback=?";
            var obj = this;
            $.getJSON(url, function(d){
                if (d.configlist.length > 0 && d.configlist) {
                    var data = d.configlist
                    obj.render(data);
                }
                else {
                    frame.setContent("没有配置信息");
                }
            });
        },
        render: function(data){
            var html = [];
            $.each(data, function(){
                var item = template.linkItem;
                item = item.replace(/{id}/g, this.id);
                item = item.replace(/{description}/g, this.description);
                if (this.valueType == "B") {
                    if (this.value == 'true') {
                        item = item.replace(/{value}/g, '<input type="checkbox" name="value" checked="checked"/>');
                    }
                    else {
                        item = item.replace(/{value}/g, '<input type="checkbox" name="value" />');
                    }
                }
                html.push(item);
            })
            this.body.html(html.join(""));
            frame.setContent(this.el);
        }
    };
    
    
    
    
    var frame = {
        init: function(){
            this.el = $(template.frame);
            this.wrap = this.el.find("div.widget\\:wrap");
            this.title = this.el.find("div.widget\\:title label");
            this.close = this.el.find("div.widget\\:title a[bind='close']");
            this.content = this.el.find("div.widget\\:content");
            this.bind();
            this.show();
        },
        bind: function(){
			 this.el.easydrag();
			 this.el.setHandler(this.title.parent());
            this.close.click(function(){
                frame.remove();
            });
            
        },
        remove: function(){
            this.el.remove();
			this.isopen = false;
        },
        setContent: function(content){
            this.content.html(content);
            this.locate();
			this.isopen = true;
        },
        show: function(){
            this.el.appendTo(document.body);
            this.locate();
            this.el.show();
        },
        locate: function(){
            var top = $(window).height() / 2 - this.el.height() / 2 + $(document).scrollTop(), left = $(window).width() / 2 - this.el.width() / 2;
            top = top < 0 ? 0 : top;
            left = left < 0 ? 0 : left;
            this.el.css({
                top: top,
                left: left
            });
        },
		isopen:false
        
    }
    
    return view;
})
