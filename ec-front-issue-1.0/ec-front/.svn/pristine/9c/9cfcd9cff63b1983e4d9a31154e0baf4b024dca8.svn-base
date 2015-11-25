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
		        LAYOUT:'<div class="title"> <div class="tab"> <div class="tab-item">电子书</div><div class="tab-item current">图书/音像/百货</div>  </div> </div>'+
		               '<div class="cont"  style="display:none">{jiuyue}</div><div class="cont">{winxuan}</div>',
		        BOOKLIST:'<ul class="list list-base-1">{item}</ul><div class="master-user-carttotal"> <div class="total-num">共<s>{count}</s>件商品  共计<b>￥{price}</b></div>'+
		                 '<div class="total-active"><a href="{server}/shoppingcart" target="_blank" class="total-active-btn" title="去购物车结算"></a></div> </div>',
		        BOOK:'<li> <div class="cell cell-style-1 cell-style-cart">'+
		             '<div class="img"><a href="{url}" title="{sellName}" target="_blank"><img src="{imageUrl}"  alt="{sellName}"></a></div>'+
		             '<div class="name"><a  href="{url}" title="{sellName}" target="_blank">{sellName}</a></div> <div class="price-n"><b>￥{price}</b>'+
		             '<span class="sale-num">X{quantity}</span></div> </div></li>',
		        EMPTYTIP:' <div class="no-goods"> <div class="no-goods-main"><b></b>您的购物车中没有商品哟~<br>赶快挑选心爱的商品吧！</div> </div>',
		        LOADING:'正在努力为你加载数据……'
		    },
			baseUrl:conf.portalServer+'/customer/shoppingcart?callback=?',
			ebookUrl:conf.ebookServer+'/shoppingcart?callback=?',
			selector:{
				header:".head",
				box:".master-user-box",
				miniCart:".master-user-sc",
				balance:"a[bind='gotoBalance']",
				count:".master-cartnum-pop"			
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
				this.book=this.temp.BOOK;
				this.emptyTip=this.temp.EMPTYTIP;
				this.layout=this.temp.LAYOUT;
                this.ebooks=this.papers=this.temp.LOADING;
				this.miniCart=$(this.selector.miniCart);
				this.content=this.miniCart.find("div.col-user-sc");
				this.el=$(this.selector.header);
				this.box=this.el.find(this.selector.box);
				this.balance=this.el.find(this.selector.balance);
				this.count=this.el.find(this.selector.count);
                this.count.html(this.getCount());
				this.userHot=this.content.html();
				this.bind();
				this.loadOtherWidgets();

				if(!$(document.body).data("lazyimg")){
					lazy.run();
					return;
				}else{
					seajs.use("lazyimg",function(lazy){
						lazy.run();
					});
				}
			},
			bind:function(){
				$(cart).bind(cart.UPDATE_EVENT,function(e,data){header.render(data);});
				this.miniCart.click(header.showCart).mouseover(header.showCart);
				$(document).mouseover(function(){header.hideCart();header.isEbook=false;});
				this.miniCart.delegate("a[bind='removeCartItem']",'click',function(){
					cart.remove($(this).attr("pid"));
				});
				this.miniCart.delegate("a[bind='removeEbookItem']",'click',function(){
                    header.removeEbook($(this).attr("pid"));
                });
                this.box.mouseover(function(){$(this).addClass("over");}).
                mouseout(function(){$(this).removeClass("over");});
			},
			showCart:function(e){
				if(!header.loading){
					header.loading=true;
					header.reload();
				}
				e.stopPropagation();
			},
			hideCart:function(){
				if(header.loading){
					header.loading=false;
				}
			},
			render:function(data,isEbook){
				var cart=data.shoppingcart,html=this.layout;
				if(isEbook){
				    this.ebooks=this.renderBooks(cart,this.bookList,isEbook);
				}else{
                    this.papers=this.renderBooks(cart,this.bookList);
                    this.count.html(cart.count);
				}
				html=html.replace(/\{jiuyue\}/g, this.ebooks).replace(/\{winxuan\}/g,this.papers);
				this.content.html(html);
				tab({context:$("div.master-user-sc-pop"),label:"div.tab .tab-item",className:"current",content:"div.cont"});
			},
			renderBooks:function(data,html,isEbook){
			    var itemList="",index;
			    if(data&&data.kind){
                    index=data.kind>4?4:data.kind;
                    html=html.replace(/\{server\}/g, isEbook?conf.ebookServer:conf.portalServer);
                    html=html.replace(/\{count\}/g, data.count);
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
                }else{
                    html=this.emptyTip+this.userHot;
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
                    header.render(paper);
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
