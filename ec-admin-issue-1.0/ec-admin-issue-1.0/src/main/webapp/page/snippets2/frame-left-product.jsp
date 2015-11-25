<%@page pageEncoding="UTF-8"%>
<div class="well sidebar-nav">
<ul class="nav nav-list">
	<li class="nav-header">商品管理</li>
	<li><a href="/category/view">商品分类管理</a>				</li>
				<li><a href="/productmeta/list?available=1">商品分类属性维护</a>				</li>
				<li><a href="/product/prepare">商品查询</a>				</li>
				<li><a href="/product/productPriceUpdate">批量修改商品价格</a>				</li>
 				<li><a href="/product/productstop">商品批量停用</a>				</li>
				<li><a href="/product/data">商品合并</a>				</li>
				
		<li class="nav-header">促销活动管理</li>
				<li><a href="/promotion/prepare">促销活动新建</a>
				</li>
				<li><a href="/promotion/list">促销活动查询</a>
				</li>
				<li><a href="/promotion/unVerifyPromotion">促销活动审核<c:if test="${needVerifyPromotionCount>0}">
							<span id="needVerifyPromotionCount">(${needVerifyPromotionCount})</span>
						</c:if>
				</a>
				</li>	
				
		<li class="nav-header">套装书管理</li>
				<li><a href="/product/complex">创建套装书</a>
				</li>
				<li><a href="/product/querycomplex">查询套装书</a>
				</li>

			<li class="nav-header">搭配推荐管理</li>
				<li><a href="/bundle/new">创建搭配推荐</a></li>
				<li><a href="/bundle/list">查询推荐管理</a></li>
				<li class="nav-header">商品监控</li>
				<li><a href="/monitor/create">新建监控任务</a></li>
				<li><a href="/monitor/list">监控任务列表</a></li>
				
</ul>
</div>


<%-- 
<div class="frame-side">
	<div class="frame-side-inner">
		<!-- <div class="menu-nav">
			<h3>栏目管理</h3>
		</div> -->
		<div class="menu-nav">
			<h3></h3>
			<ul class="sub">
				
			</ul>
		</div>
		<div class="menu-nav">
			<h3>促销活动管理</h3>
			<ul class="sub">
				<li><a href="/promotion/prepare">促销活动新建</a>
				</li>
				<li><a href="/promotion/list">促销活动查询</a>
				</li>
				<li><a href="/promotion/unVerifyPromotion">促销活动审核<c:if test="${needVerifyPromotionCount>0}">
							<span id="needVerifyPromotionCount">(${needVerifyPromotionCount})</span>
						</c:if>
				</a>
				</li>
			</ul>
		</div>
	    <div class="menu-nav">
			<h3>套装书管理</h3>
			<ul class="sub">
				<li><a href="/product/complex">创建套装书</a>
				</li>
				<li><a href="/product/querycomplex">查询套装书</a>
				</li>
			</ul>
		</div>
	    <div class="menu-nav">
			<h3>搭配推荐管理</h3>
			<ul class="sub">
				<li><a href="/bundle/new">创建搭配推荐</a></li>
				<li><a href="/bundle/list">查询推荐管理</a></li>
			</ul>
		</div>
		 <div class="menu-nav">
			<h3>商品监控</h3>
			<ul class="sub">
				<li><a href="/monitor/create">新建监控任务</a></li>
				<li><a href="/monitor/list">监控任务列表</a></li>
			</ul>
		</div>
	</div>
</div> --%>