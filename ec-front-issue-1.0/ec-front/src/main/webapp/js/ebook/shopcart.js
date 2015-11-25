seajs.use([ "jQuery" ],
				function($) {

					var template = {
						goon : '<div style="text-align: center;">你还没有挑选商品，去 <a href="http://ebook.winxuan.com" class="link2">电子书首页>></a></div>',
						load : '<div class="loading">购物车正在更新中...</div>',
						success : '<div class="success">购物车更新成功.</div>',
						cancel:'<div class="loading">处理中...</div>',
						cancelSuccess:'<div class="success">处理完成.</div>'
					};

					var view = {
						delbut : $('a[bind=delete]'),
						spanProductCount : $('span[bind="productCount"]'),
						divcartGoodsList : $('div[bind="goodsList"]'),
						bCountPrice : $('b[bind="countPrice"]'),
						divCartMessage : $('div#cart-message'),
						aOrderCancel : $('a[bind="order-cancel"]'),
						init : function() {
							this.bind();
						},
						bind : function() {
							var obj = this;
							this.delbut.click(function() {
								obj.remove($(this));
							});
							this.aOrderCancel.click(function() {
								obj.cancel($(this));
							});

						},
						remove : function(el) {
							var actionUrl = "http://ebook.winxuan.com/shoppingcart";
							var obj = this;
							var data = {
								opt : 'remove',
								p : el.attr('date-id'),
								format : 'json'
							};
							$
									.ajax({
										url : actionUrl,
										data : data,
										type : 'GET',
										dataType : 'json',
										beforeSend : function() {
											obj.divCartMessage.show();
											obj.divCartMessage
													.html(template.load)
													.css(
															{
																"top" : ($(
																		window)
																		.height() / 2 + $(
																		document)
																		.scrollTop()),
																"z-index" : 99
															});
										},
										success : function(d) {
											if (d.result == 1) {
												obj.spanProductCount
														.html(d.shoppingcart.productCount);
												obj.bCountPrice
														.html(d.shoppingcart.countPrice
																.toFixed(2));
												el.parents("li").remove();
												if (d.shoppingcart.productCount == 0) {
													obj.divcartGoodsList
															.html(template.goon);
													$("#statistics").hide();
												}
											}
										},
										complete : function() {
											obj.divCartMessage
													.html(template.success);
											obj.divCartMessage.fadeOut('slow');
										}

									});
						},
						cancel : function(el) {
							var obj = this;
							var actionUrl = el.attr("actionUrl");
							$.ajax({
							   url:	actionUrl,
							   data : "format=json",
							   type : 'GET',
							   dataType : 'json',
							   beforeSend : function() {
									obj.divCartMessage.show();
									obj.divCartMessage
											.html(template.cancel)
											.css({
														"top" : ($(
																window)
																.height() / 2 + $(
																document)
																.scrollTop()),
														"z-index" : 99
													});
							     	},
							  complete : function() {
										obj.divCartMessage.html(template.cancelSuccess);
										obj.divCartMessage.fadeOut('slow');
									},
							  success:function(d){
								  if(d.order.state == 0){
										el.parents("tr").remove();
								  }
							  }
							});
						}

					};
					view.init();
				});