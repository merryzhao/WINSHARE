define(function(require){
    var $=require("jQuery"),
        cart=require("cart"),
        ui=require("ui"),
        conf=require("config"),
        msgMap={
            cart:{
                "1":'<div class="success">商品已成功添加到购物车！</div><p>购物车共有<b class="red fb">{count}</b>种商品，合计：<b class="red fb">￥{price}</b></p>',
                "2":"您所购买的商品不存在",
                "3":"您所购买的商品库存数量不足，系统已将商品的最大库存数更新至您的购物车",
                "4":"您所购买的商品已下架",
                "5":"错误的数量",
                "presell":'<div class="success">商品已成功添加到购物车！</div><p>该商品预计{date},从{region}发货</p>',
                "lowStocks":'<div class="success">商品已成功添加到购物车！</div><p>部份商品库存数量不能满足您的要求，请进入您的购物车中调整。</p>'
            },
            favorite:{
                "batch":'<div class="favorite-result"><p>{result}</p><p><a href="http://www.winxuan.com/customer/favorite" target="myfavorite" bind="viewFavorite">查看我的收藏</a></p></div>'
            }
        };
    function CombBuy(cfg){
        this.apiUrl=conf.searchServer+"/hotsalelist?";
        this.format="format=jsonp";
        this.callback="callback=?";
        this.opt={
            productId:"",
            context:""
        };
        $.extend(this.opt,cfg);
    };
    CombBuy.TEMPLATE={
        ITEM:'<div class="cell cell-style-3 cell-style-3-plus"><div class="img"><a href="{url}" class="book_pic" target="_blank"><img src="{image}" /></a></div>'+
             '<div class="name"><a href="{url}" class="book_name link4" target="_blank">{name}</a></div>'+
             '<div class="price-n"><input type="checkbox" name="price" class="checkbox" data-list-price="{listprice}" data-sale-price="{saleprice}"  value="{id}"><b>￥{saleprice}</b></div>'+
             '<s class="operation"></s></div>'
    };
    CombBuy.prototype={
        init:function(){
            var comb=this;
            this.context=$(this.opt.context);
            this.el=this.context.find(".assemble-slave-overflow");
            this.listPrice=this.context.find(".master-book").data("list-price");
            this.salePrice=this.context.find(".master-book").data("sale-price");
            this.productId=this.context.find(".master-book").data("product-id");
            this.load();
            this.bind();
            $(cart).bind(cart.BATCH_ADD_EVENT,function(e,data,el){comb.cartUpdate(data,el,true);});
        },
        load:function(){
            var url=this.apiUrl,param=[],comb=this;
            param.push("id="+this.opt.productId);
            param.push(this.format);
            param.push(this.callback);
            $.getJSON(url+param.join("&"),function(data){
                if(!!data.hotsalelist){
                    comb.render(data.hotsalelist);
                }else{
                    comb.context.remove();
                }
            });
        },
        render:function(data){
            var comb=this,html="",w=data.length*168;
            $.each(data,function(){
                html+=comb.renderItem(this);
            });
            this.el.append(html);
            this.el.width(w);
            this.context.show();
        },
        renderItem:function(item){
            var html=CombBuy.TEMPLATE.ITEM;
            html = html.replace(/\{url\}/g, item.url);
            html = html.replace(/\{name\}/g, item.name);
            html = html.replace(/\{image\}/g, item.coverPath);
            html = html.replace(/\{listprice\}/g, item.listprice.toFixed(2));
            html = html.replace(/\{saleprice\}/g, item.saleprice.toFixed(2));
            html = html.replace(/\{id\}/g, item.id);
            return html;
        },
        bind:function(){
            var comb=this;
            this.context.delegate("input[type='checkbox']","click",function(e){comb.selectItem(this);});
            this.context.delegate("a.comb_buy","click",function(e){comb.batchAddToCart(this);});
        },
        selectItem:function(el){
            if(el.checked){
                this.listPrice+=$(el).data("list-price");
                this.salePrice+=$(el).data("sale-price");
            }else{
                this.listPrice-=$(el).data("list-price");
                this.salePrice-=$(el).data("sale-price");
            }
            this.context.find("#list-price").html('￥'+this.listPrice.toFixed(2));
            this.context.find("#sale-price").html('￥'+this.salePrice.toFixed(2));
        },
        batchAddToCart:function(el){
            var ids=["p="+this.productId];
            this.el.find("input[type='checkbox']").filter("[name='price']:checked").each(function(){
                var id=$(this).val();
                if(!!id){
                    ids.push("p="+id);                  
                }
            });
            this.showCartTipWin(el);
            cart.batchAdd(ids,el);
        },
        showCartTipWin:function(el){
            if(!this.cartWin||this.cartWin.hasClosed){
                this.cartWin=ui.tipWindow({
                    width:"300",
                    dock:el,
                    callback:function(){
                        try{
                            window.open(conf.portalServer+"/shoppingcart","shoppingcart");
                            this.cartWin.close();
                        }catch(e){}
                    }
                });
            }else{
                this.cartWin.opt.dock=el;
                this.cartWin.change("loading");
            }
        },
        cartUpdate:function(data,el,isBatch){
            var message;
            if(this.cartWin&&el){
                var date=$(el).data("date");
                if(!date){
                    this.normalUpdate(data,el,isBatch);
                }else{
                    message=msgMap.cart["presell"].replace(/{date}/g,date).replace(/{region}/g,"成都");
                    this.cartWin.change("success",message);
                }
            }else{
                throw new Error("can't found the tip window or element!");
            }
        },
        normalUpdate:function(data,el,isBatch){
            var message;
            if(data.status=="1"){
                message=msgMap.cart["1"].replace("{count}",data.shoppingcart.count).replace("{price}",data.shoppingcart.salePrice.toFixed(2));
                this.cartWin.change("success",message);
            }else if(data.status=="3"){
                var ref=$(el).data("ref");
                if(!!ref){
                    $(ref).val(data.avalibleQuantity);
                }
                if(isBatch){
                    message=msgMap.cart["lowStocks"];                       
                }else{
                    message=msgMap.cart[data.status];
                }
                this.cartWin.change("success",message);
            }else{
                message=msgMap.cart[data.status];
                this.cartWin.change("error",message);
            }
        }
    };
    return function(cfg){
            return new CombBuy(cfg);
    };
});