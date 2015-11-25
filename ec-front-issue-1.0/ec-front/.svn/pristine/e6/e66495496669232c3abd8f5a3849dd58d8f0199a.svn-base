<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的帐户---文轩网网上书店,新华书店网上书城</title>
<jsp:include page="../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="my_acc_order" />
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp"></jsp:include>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  我的文轩</span></div>
    <jsp:include page="/page/left_menu.jsp">
		<jsp:param name="id" value="1_0" />
	</jsp:include>
    <div class="right_box">
    	<h3 class="myfav_tit margin10">我的文轩</h3>
    	<div class="account_info">
            <p class="fl acc_photo txt_center">
            <c:choose>
            	<c:when test="${not empty customer.avatar }"><img src="${customer.avatar }" width="96" height="96"></c:when>
            	<c:otherwise><img src="../goods/account_photo.jpg" width="96" height="96"></c:otherwise>
            </c:choose>
            <div class="fl information">
                <dl class="base_info">
                    <dt>欢迎您，<b class="fb"><c:out value="${customer.displayName}"></c:out></b> <a href="/customer/detail" class="l_gray haveline">完善我的资料</a></dt>
                    <dd>会员级别：<a href="javascript:;" class="link1"><c:if test="${customer.grade==0}">普通会员</c:if><c:if test="${customer.grade==1}">银卡会员</c:if><c:if test="${customer.grade==2}">金卡会员</c:if></a><a class="rmb_icon" href="javascript:;" title=""></a><%-- <a class="mobile_icon" href="#" title=""></a> --%></dd>
                    <dd>账户积分：<b class="red">${customer.account.points}</b> <a href="/customer/points/pointsExchange" class="l_gray haveline">兑换礼品</a></dd>
                    <dd>礼品卡：<b class="red">${total}</b> <a href="/customer/presentcard" class="l_gray haveline">绑定新的礼品卡</a></dd>
                    <dd>暂存款：<b class="red">￥${customer.account.balance }</b> <a href="/customer/advanceaccount" class="l_gray haveline">暂存款详细</a></dd>
                </dl>
            </div>
            <div class="hei1"></div>
        </div>
        
        <!-- 
        @ TODO 规则不明确, 相应的匹配功能无实现.
        <dl class="more_info">
          <dd><b class="fb">交易提醒：</b> <a href="javascript:;">待支付订单(0)</a><a href="javascript:;">待支付订单(0)</a><a href="javascript:;">待评价商品(0)</a></dd>
          <dd><b class="fb">收藏提醒：</b> <a href="javascript:;">降价商品<b class="red">(2)</b></a><a href="javascript:;">促销商品(0)</a></dd>
          <dd><b class="fb">账户提醒：</b> <a href="javascript:;">未读消息(0)</a><a href="javascript:;">新购物礼券(0)</a></dd>
         </dl>
          -->
         <h3 class="table_tit"><span>最近订单<a href="/customer/order">更多订单 &gt;&gt;</a></span></h3>
        <table width="100%" class="data_table" cellspacing="0" cellpadding="0">           
            <thead>
                <tr>
                	<th></th>
                    <th>订单编号</th>
                    <th>收货人</th>
                    <th>付款方式</th>
                    <th>订单总金额</th>
                    <th>订单状态</th>
                    <th>下单时间</th>
                    <th>商家</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${(fn:length(orderList)) <= 0}">
            		<tr><td colspan="8">
            		<div class="null_context_tip">暂无订单</div>
            		</td></tr>
            	</c:if>
               <c:forEach var="order" items="${orderList}">
                <tr attr="${order.id}">
                	<td></td>
                    <td><a href ="/customer/order/${order.id}" class="link1" target="_blank">${order.id}</a></td>
                    <td>${order.consignee.consignee}</td>
                    <td>
                    	<ul>
                    	<c:forEach items="${order.paymentList}" var="pl" varStatus="status">
							<li>${pl.payment.name}</li>
                    	</c:forEach>
                    	</ul>
                    </td>
                    <td>${order.totalPayMoney }</td>
                    <td>${order.processStatus.name }</td>
                    <td><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${order.shop.shopName }</td>
                    <td attr="control">
                    	<c:if test="${order.processStatus.id == 8001}">
	                    	<c:if test="${!order.requidCod}">
		                    	<a class="link1" href="/customer/checkout/${order.id}">支付</a>&nbsp; 
	                    	</c:if>
	                    	<a class="link1" href="javascript:;" bind="cancelOrder" data-id="${order.id}">取消</a>&nbsp;
	                    </c:if>
	                    <c:if test="${order.processStatus.id == 8004 || order.processStatus.id == 8011}">
	                    	<c:if test="${empty order.receive}">
		                    	<a class="link1" href="javascript:;" bind="receiveOrder" data-id="${order.id}">收货确认</a>&nbsp;                   	                 	
	                    	</c:if>
	                    </c:if>
	                    <c:if test="${order.processStatus.id == 8005}">
	                    	<a class="link1" href="/customer/bought/${order.id}" >写评论</a>&nbsp;           	                 	
	                    </c:if>
					</td>
                </tr>
               </c:forEach>
            </tbody>
        </table>
        
        <h3 class="table_tit"><span>我的咨询<a href="/customer/question">更多提问&gt;&gt;</a></span></h3>
        <table width="100%" class="data_table" cellspacing="0" cellpadding="0">           
            <thead>
                <tr>
                    <th width="27%">被提问的商品</th>
                    <th width="17%">提问时间</th>
                    <th width="36%">问题</th>
                    <th width="10%">状态</th>
                    <th width="10%">操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${(fn:length(questionList)) <= 0}">
            		<tr><td colspan="5">
            		<div class="null_context_tip">暂无咨询</div>
            		</td></tr>
            	</c:if>
            	<c:forEach items="${questionList}" var="question">
	                <tr>
	                    <td><p class="txt_left"><a class="link1" href="/product/${question.productSale.product.id}" target="_black">${question.productSale.effectiveName}</a></p></td>
	                    <td><fmt:formatDate value="${question.askTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                    <td><p class="txt_left">${question.title}</p></td>
	                    <td><c:if test="${question.reply}"><b class="green">已解答</b></c:if><c:if test="${!question.reply}">未解答</c:if></td>
	                    <td><a class="link2" href="/product/question/${question.productSale.product.id}" target="_black">查看</a></td>
	                </tr>
            	</c:forEach>
            	<!-- 
                <tr>
                    <td><p class="txt_left"><a class="link1" href="#">人类行为的经济学分析 (下)</a></p></td>
                    <td>2011-07-31</td>
                    <td><p class="txt_left">请问这本书有光盘吗，送货估计几天可以到达呢？</p></td>
                    <td>未解答</td>
                    <td><a class="link2" href="#">查看</a></td>
                </tr>
                <tr>
                    <td><p class="txt_left"><a class="link1" href="#">36.5℃行为经济学</a></p></td>
                    <td>2011-07-31</td>
                    <td><p class="txt_left">请问这本书有光盘吗，送货估计几天可以到达呢？</p></td>
                    <td><b class="green">已解答</b></td>
                    <td><a class="link2" href="#">查看</a></td>
                </tr>
                 -->
            </tbody>
        </table>
      <h3 class="table_tit"><span>我的收藏<a href="/customer/favorite">更多收藏 >></a></span></h3>
        <table width="100%" class="data_table" cellspacing="0" cellpadding="0">           
            <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>定价</th>
                    <th>折扣</th>
                    <th>文轩价</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${(fn:length(favoriteList)) <= 0}">
            		<tr><td colspan="6">
            		<div class="null_context_tip">暂无收藏</div>
            		</td></tr>
            	</c:if>
            	<c:forEach items="${favoriteList}" var="favorite">
	                <tr>
	                    <td><p class="txt_left"><a class="link1" href="${favorite.productSale.product.url}" target="_black">${favorite.productSale.effectiveName}</a></p></td>
	                    <td>
	                    
	                    
	                    
	                    	<c:choose>
                    <c:when test="${favorite.productSale.supplyType.id != 13102 && favorite.productSale.supplyType.id !=13103}">
                    		<c:choose>
                    			<c:when test="${favorite.productSale.canSale}">
                    			 	<c:choose>
                    			 		<c:when test="${favorite.productSale.avalibleQuantity<=5}">仅剩${favorite.productSale.avalibleQuantity}本
                    			 		</c:when>
                    			 		<c:otherwise>现在有货</c:otherwise>
                    			 	</c:choose>	            				
                    			</c:when>
                    			<c:otherwise>现在没货</c:otherwise>
                    		</c:choose>
                	</c:when>
                	<c:otherwise>
                		<c:if test="${favorite.productSale.supplyType.id == 13102}">
                				<c:choose>
                					<c:when test="${favorite.productSale.preSaleCanBuy }">正在预售</c:when>
                					<c:otherwise>现在没货</c:otherwise>
                				</c:choose>
                		</c:if>
                		<c:if test="${favorite.productSale.supplyType.id == 13103}">
               				正在订购
                		</c:if>
                	</c:otherwise>
                	</c:choose>
                	
                	
	                    </td>
	                    <td>￥${favorite.addPrice }</td>
	                    <td>${favorite.productSale.integerDiscount}折 </td>
	                    <td><b class="red">￥${favorite.productSale.effectivePrice}</b></td>
	                    <td>
	                    	<c:choose>
                    			<c:when test="${!(favorite.productSale.avalibleQuantity < 5 && favorite.productSale.avalibleQuantity > 0) }">
                    				---
                    			</c:when>
                    			<c:when test="${favorite.productSale.avalibleQuantity < 5 && favorite.productSale.avalibleQuantity > 0}">
				                    <a href="javascript:;" class="red fb" bind="fav_buy" data-id="${favorite.productSale.product.id}">购买</a>
                    			</c:when>
                    		</c:choose>
	                   	</td>
	                </tr>
            	</c:forEach>
       			<!-- 
                <tr>
                    <td><p class="txt_left"><a class="link1" href="#">人类行为的经济学分析 (下)</a></p></td>
                    <td><b class="green">有货</b></td>
                    <td>￥35.60</td>
                    <td>68折 </td>
                    <td><b class="red">￥28.90</b></td>
                    <td><a href="#" class="red fb">购买</a></td>
                </tr>
                <tr>
                    <td><p class="txt_left"><a class="link1" href="#">36.5℃行为经济学</a></p></td>
                    <td><b class="green">有货</b></td>
                    <td>￥35.60</td>
                    <td>68折 </td>
                    <td><b class="red">￥28.90</b></td>
                    <td><a href="#" class="red fb">购买</a></td>
                </tr>
                 -->
            </tbody>
        </table>  
    	
    	
    	<%-- 
        <div class="account_info">
            <p class="fl acc_photo txt_center"><img src="goods/account_photo.jpg" width="96" height="96"><br>
                我的个人首页</p>
            <div class="fl information">
                <p>欢迎您, <b class="red fb">${customer.name}</b> <a class="link2" href="/customer/detail">完善我的资料</a></p>
                <dl class="base_info">
                    <dt><b class="fb black">会员级别： <c:if test="${customer.grade==0}">普通会员</c:if><c:if test="${customer.grade==1}">银卡会员</c:if><c:if test="${customer.grade==2}">金卡会员</c:if></b>（消费再满200可升级为XX会员）</dt>
                    <dd>帐户积分：<span class="red">${customer.account.points}</span> <a class="link2" href="javascript:;">礼品兑换</a></dd>
                    <dd>礼品卡： <span class="red">￥0.00</span></dd>
                    <dd>帐户余额： <span class="red">￥${customer.account.balance }</span></dd>
                </dl>
                <dl class="more_info">
                    <dd><b class="fb">交易提醒：</b> <a href="javascript:;">待支付订单(0)</a><a href="javascript:;">待支付订单(0)</a><a href="javascript:;">待评价商品(0)</a></dd>
                    <dd><b class="fb">收藏提醒：</b> <a href="javascript:;">降价商品<b class="red">(2)</b></a><a href="javascript:;">促销商品(0)</a></dd>
                    <dd><b class="fb">收藏提醒：</b> <a href="javascript:;">未读消息(0)</a><a href="javascript:;">新购物礼券(0)</a></dd>
                </dl>
            </div>
            <div class="hei1"></div>
        </div>
        <h3 class="data_title">我最近的订单</h3>
        <table width="100%" class="order_coll txt_center" cellspacing="0" cellpadding="0">           
            <thead>
                <tr>
                	<th></th>
                    <th>订单编号</th>
                    <th>收货人</th>
                    <th>付款方式</th>
                    <th>订单总金额</th>
                    <th>订单状态</th>
                    <th>下单时间</th>
                    <th>商家</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
               <c:forEach var="order" items="${orderList}">
                <tr attr="${order.id}">
                	<td></td>
                    <td><a href ="/customer/order/${order.id}" target="_blank">${order.id}</a></td>
                    <td>${order.consignee.consignee}</td>
                    <td>${order.payType.name}</td>
                    <td>${order.totalPayMoney }</td>
                    <td>${order.processStatus.name }</td>
                    <td><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${order.shop.shopName }</td>
                    <td attr="control">
                    	<c:if test="${order.processStatus.id == 8001}">
	                    	<a class="link2" href="/customer/checkout/${order.id}">支付</a>/ 
	                    	<a class="link2" href="javascript:;" onclick="cancelOrder(${order.id})">取消</a>/
	                    	<a class="link2" href="javascript:;">修改</a>                   	
	                    </c:if>
	                       <c:if test="${order.processStatus.id == 8004}">
	                    	<a class="link2" href="javascript:;" onclick="receiveOrder(${order.id})">确认收货</a>                    	                 	
	                    </c:if> 
					</td>
                </tr>
               </c:forEach>
            </tbody>
        </table>
        <h3 class="data_title">我最近的收藏</h3>
        <table width="100%" class="order_coll" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th width="12%">商品信息</th>
                    <th width="44%">&nbsp;</th>
                    <th width="11%">原价</th>
                    <th width="11%">折扣</th>
                    <th width="11%">现价</th>
                    <th width="11%">操作</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><img src="goods/sml_10002845.jpg" alt="20几岁男人要懂点心理学大全集" width="80" class="fl"></td>
                    <td><p class="ac_goods_intro"><a href="javascript:;" title="20几岁男人要懂点心理学大全集">20几岁男人要懂点心理学大全集</a><br/>(随着心理学的逐步发展，人们逐渐认识到心理学的应用范围越来越广...)</p></td>
                    <td>￥23.00</td>
                    <td>6.8折</td>
                    <td><span class="red fb">￥15.00</span></td>
                    <td><a class="link2" href="javascript:;">购买</a> <a class="link2" href="javascript:;">删除</a></td>
                </tr>
                <tr>
                    <td><img src="goods/sml_10002845.jpg" alt="20几岁男人要懂点心理学大全集" width="80" class="fl"></td>
                    <td><p class="ac_goods_intro"><a href="javascript:;" title="20几岁男人要懂点心理学大全集">20几岁男人要懂点心理学大全集</a><br/>(随着心理学的逐步发展，人们逐渐认识到心理学的应用范围越来越广...)</p></td>
                    <td>￥23.00</td>
                    <td>6.8折</td>
                    <td><span class="red fb">￥15.00</span></td>
                    <td><a class="link2" href="javascript:;">购买</a> <a class="link2" href="javascript:;">删除</a></td>
                </tr>
            </tbody>
        </table>
        <div class="recommend">
            <h3>精品推荐</h3>
            <a id="arrow_pre" href="javascript:;"></a> <a id="arrow_next" href="javascript:;"></a>
            <div class="recom_goods_box">
                <ul id="recom_goods_scroll_ul">
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_1226030.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002725.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002850.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002724.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002724.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_1226030.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                    <li>
                        <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                        <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                        <div class="comment"><span class="fr">20条评论</span>
                            <div class="comment_star"><span style="width:80%"></span></div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        
          --%>
         
         
         
    </div>
    <div class="hei10"></div>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
<script type="text/javascript" src="${serverPrefix}js/my/myIndex.js"></script>
</body>
</html>
