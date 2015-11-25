<%@page pageEncoding="UTF-8"%>
<div class="well sidebar-nav">

	<ul class="nav nav-list">
		<li class="nav-header">用户管理</li>
		<li><a href="/comment/goList">用户评论管理</a></li>
		<li><a href="/question/goList">用户提问管理</a></li>
		<li><a href="/complaint/goList">用户投诉管理</a></li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">团购申请管理</li>
		<li><a href="/groupshop/goList">团购申请管理</a></li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">礼券管理</li>
		<li><a href="/presentbatch/new">礼券批次新建</a>
		</li>
		<li><a href="/presentbatch/select">礼券批次查询</a>
		</li>
		<li><a href="/present">礼券查询</a>
		</li>
		<li><a href="${contextPath}/presentbatch/unVerifyPresentBatch">审核礼券批次<c:if
					test="${(!empty needVerifyBatchCount)&&needVerifyBatchCount!=0}">(<span
						id="needVerifyBatchCount">${needVerifyBatchCount}</span>)</c:if> </a>
		</li>
		<li><a href="/customer/attach">礼券附件管理</a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">礼品卡管理</li>
		<li><a href="/presentcard/initnew">礼品卡新建</a>
		</li>
		<li><a href="/presentcard/print">礼品卡印刷</a>
		</li>
		<li><a href="/presentcard/query">礼品卡查询</a>
		</li>
		<li><a href="/presentcard/storePresentCard">礼品卡入库</a>
		</li>
		<li><a href="/presentcard/cancelpage">礼品卡注销</a>
		</li>
		<li><a href="/presentcard/delaypage">礼品卡延期</a>
		</li>
		<li><a href="/presentcard/password">礼品卡密码修改</a>
		</li>
		<li><a href="/presentcard/goProductNew">礼品卡商品新建</a>
		</li>
		<li><a href="/presentcard/productList">礼品卡商品查询</a>
		</li>
		<li><a href="/presentcard/orderList">礼品卡订单查询</a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">礼品卡订单管理</li>
		<li><a href="/presentcardorder/tosend">发放礼品卡订单</a>
		</li>
		<li><a href="/presentcardorder/toresend">补发礼品卡订单</a>
		</li>
		<li><a href="/presentcardorder/toactive">激活礼品卡订单</a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">促销活动管理</li>
		<li><a href="/promotion/prepare">促销活动新建</a>
		</li>
		<li><a href="/promotion/list">促销活动查询</a>
		</li>
		<li><a href="/promotion/unVerifyPromotion">促销活动审核<c:if
					test="${needVerifyPromotionCount>0}">
					<span id="needVerifyPromotionCount">(${needVerifyPromotionCount})</span>
				</c:if> </a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">商品管理</li>
		<li><a href="/category/view">商品分类管理</a>
		</li>
		<li><a href="/productmeta/list?available=1">商品分类属性维护</a>
		</li>
		<li><a href="/product/prepare">商品查询</a>
		</li>
		<li><a href="/product/productPriceUpdate">批量修改商品价格</a>
		</li>
		<li><a href="/product/productstop">商品批量停用</a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">会员管理</li>
		<li><a href="/customer/manage">会员管理</a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">套装书管理</li>
		<li><a href="/product/complex">创建套装书</a>
		</li>
		<li><a href="/product/querycomplex">查询套装书</a>
		</li>
	</ul>
	<ul class="nav nav-list">
		<li class="nav-header">搭配推荐管理</li>
		<li><a href="/bundle/new">创建搭配推荐</a></li>
		<li><a href="/bundle/list">查询推荐管理</a></li>
	</ul>

</div>
