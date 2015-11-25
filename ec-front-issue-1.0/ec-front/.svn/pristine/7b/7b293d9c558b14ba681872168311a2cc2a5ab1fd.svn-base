define(function (require){
	var $=require("jQuery"),
		top=require("top"),
		conf=require("config"),
		cart=require("cart"),
		Assister=require("searchAssister"),
		Menu=require("category-menu"),
		tab=require("table"),
		header={
		    temp:{
		        LAYOUT:'<ul class="tabs"><li>电子书</li><li class="curr_tab">图书/音像/百货</li></ul><div class="tabs_cont" style="display:none">{jiuyue}</div><div class="tabs_cont">{winxuan}</div>',
		        BOOKLIST:'<p class="txt_center"><a class="gray" href="{server}/shoppingcart" target="shoppingcart">共<b class="c2">{kind}</b>种商品，查看全部商品</a></p>'+
		               '<table width="300" border="0" cellspacing="0" cellpadding="0">{item}</table>'+
		               '<p class="txt_right">共<b class="c2">{kind}</b>种商品 金额总计：<b class="c2">￥{price}</b></p>'+
		               '<p class="p_but"><a class="settlement fr" href="{server}/shoppingcart" target="_blank">去购物车并结算</a>{fav}</p>',
		        FAVORITE:'<a class="shop_button" href="{favorite}/customer/favorite/addAllFromCart" target="_blank">全部收藏</a>',
		        BOOK:'<tr><td width="54" align="center"><a href="{url}" title="{sellName}" target="_blank" class="cartHead"><img src="{imageUrl}" alt="{sellName}"/></a></td>'+
		             '<td width="147"><a href="{url}" title="{sellName}" target="_blank"/>{sellName}</a></td>'+
		             '<td width="99" align="right"><b class="c2">￥{price} x {quantity}</b>'+
		             '<a href="javascript:;" bind="{remove}" pid="{productSaleId}">删除</a></td></tr>',
		        EMPTYTIP:'<p class="empty_tip">您的购物车是空的，赶快挑选心爱的商品吧！</p>',
		        LOADING:'正在努力为你加载数据……'
		    },
			baseUrl:conf.portalServer+'/customer/status?callback=?',
			ebookUrl:conf.ebookServer+'/shoppingcart?callback=?',
			selector:{
				header:"div.header",
				cartButton:"div.shop_cart",
				balance:"a[bind='gotoBalance']",
				count:"b.red",
				miniCart:"#nav_mini_cart"
			},
			getCount:function(){
				var cookies=document.cookie.split(";"),
					temp;
				for(var i=0;i<cookies.length;i++){
					temp=cookies[i].split("=");
					if($.trim(temp[0])=="sc"){
						return decodeURIComponent(temp[1]);	
					}
				}
				return 0;
			},
			init:function(){
				top.init();
				new Assister({isReload:false});
				new Menu();
				this.isEbook=false;
				this.bookList=this.temp.BOOKLIST;
				this.favorite=this.temp.FAVORITE;
				this.book=this.temp.BOOK;
				this.emptyTip=this.temp.EMPTYTIP;
				this.layout=this.temp.LAYOUT;
                this.ebooks=this.papers=this.temp.LOADING;
				this.miniCart=$(this.selector.miniCart);
				this.content=this.miniCart.find("div.mini_cart");
				this.el=$(this.selector.header);
				this.cartButton=this.el.find(this.selector.cartButton);
				this.hotZone=this.cartButton.find("li:eq(1)");
				this.balance=this.el.find(this.selector.balance);
				this.count=this.el.find(this.selector.count);
				this.bind();
				this.cartButton.find("li b.red").html(this.getCount());
				this.loadOtherWidgets();
				if(!$(document.body).data("lazyimg")){
					return;
				}else{
					seajs.use("lazyimg",function(lazy){
						lazy.run();
					});
				}
			},
			bind:function(){
				$(cart).bind(cart.UPDATE_EVENT,function(e,data){header.render(data);});
				this.hotZone.click(header.showCart).mouseover(header.showCart);
				this.miniCart.mouseover(header.showCart);
				$(document).mouseover(function(){header.hideCart();header.isEbook=false;});
				this.miniCart.delegate("a[bind='removeCartItem']",'click',function(){
					cart.remove($(this).attr("pid"));
				});
				this.miniCart.delegate("a[bind='removeEbookItem']",'click',function(){
                    header.removeEbook($(this).attr("pid"));
                });
			},
			showCart:function(e){
				header.dock();
				if(!header.cartShowing){
					header.miniCart.fadeIn(200,function(){
						header.cartShowing=true;
					});
				}
				if(!header.loading){
					header.loading=true;
					header.reload();
				}
				e.stopPropagation();
			},
			dock:function(){
				var el=this.cartButton,position=el.position();
				this.miniCart.css({
					top:position.top+74,
					left:580
				});
			},
			hideCart:function(){
				if(header.cartShowing){
					header.miniCart.fadeOut("slow",function(){
						header.cartShowing=false;
						if(header.loading){
							header.loading=false;
						}
					});
				}
			},
			render:function(data,isEbook){
				var cart=data.shoppingcart,html=this.layout;
				if(isEbook){
				    this.ebooks=this.renderBooks(cart,this.bookList,isEbook);
				}else{
                    this.papers=this.renderBooks(cart,this.bookList);
                    this.cartButton.find("li b.red").html(cart.count);
				}
				html=html.replace(/\{jiuyue\}/g, this.ebooks).replace(/\{winxuan\}/g,this.papers);
				this.content.html(html);
				tab({context:$("div.mini_cart"),label:"ul.tabs li",className:"curr_tab",content:"div.tabs_cont"});
			},
			renderBooks:function(data,html,isEbook){
			    var itemList="",index;
			    if(data&&data.kind){
                    index=data.kind>4?4:data.kind;
                    html=html.replace(/\{server\}/g, isEbook?conf.ebookServer:conf.portalServer);
                    html=html.replace(/\{kind\}/g, data.kind);
                    html=html.replace(/\{price\}/g, data.salePrice.toFixed(2));
                    $.each(data.itemList,function(i){
                        var book=header.book;
                        book=book.replace(/\{url\}/g, this.url);
                        book=book.replace(/\{sellName\}/g, this.sellName);
                        book=book.replace(/\{imageUrl\}/g, this.imageUrl);
                        book=book.replace(/\{price\}/g, this.salePrice.toFixed(2));
                        book=book.replace(/\{imageUrl\}/g, this.imageUrl);
                        book=book.replace(/\{quantity\}/g, this.quantity);
                        book=book.replace(/\{productSaleId\}/g, this.productSaleId);
                        book=book.replace(/\{remove\}/g, isEbook?"removeEbookItem":"removeCartItem");
                        itemList+=book;
                        if(i>=index){
                            return false;
                        }
                    });
                    html=html.replace(/\{item\}/g, itemList);
                    html=html.replace(/\{fav\}/g, isEbook?"":this.favorite);
                    html=html.replace(/\{favorite\}/g, conf.portalServer);             
                }else{
                    html=this.emptyTip;
                }
                return html;
			},
			reload:function(){
				var option={format:"jsonp"};
				$.getJSON(this.baseUrl,option,function(data){
				    header.loadEbook(data);
				});
			},
			loadEbook:function(paper){
                var option={format:"jsonp"};
                $.getJSON(this.ebookUrl,option,function(data){
                    header.render(paper)
                    header.render(data,true);
                });
            },
			removeEbook:function(id){
                var option={p:id,opt:"remove",format:"jsonp"};
                $.getJSON(this.ebookUrl,option,function(data){
                    header.render(data,true);
                });
            },
			loadOtherWidgets:function(){
				$(function(){
					if($(document).height()>1000){
						seajs.use("returnTop",function(returnTop){
							returnTop.init();
						});
					}
				});
			}
		};
	return header;
});
