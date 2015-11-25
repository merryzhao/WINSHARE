<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right_box2 fl" >
  <div class="newdisk" fragment="433">
	<div class="new_tab">
		<a class="current_no">1</a><a>2</a><a>3</a>
	</div>
	<div class="new_album">
		<a href="javascript:;" class="pre_page"></a> <a href="javascript:;" class="next_page"></a>
		
		<div class="album_box">
		 <div class="goods_list">
			<c:forEach items="${contentList}" var="content" varStatus="status">
				<dl class="goods_rush" >
					<dt>
						<a  href="${content.url }"
							title="${content.product.name}"><img
							class="book_img" src="${content.imageUrl }"
							alt="${content.product.name }">
						</a>
					</dt>
					<dd class="goods_tit">
						<a   href="${content.url}"
							title="${content.product.name}">${content.product.name}</a>
					</dd>
					<dd>
						<del class="l_gray">定价：￥${content.product.listPrice }</del>
						<br/> 文轩价：<b class="red fb">￥${content.salePrice }</b>
					</dd>
				</dl>
				</c:forEach>
		</div>
	 </div>
	 </div>
	</div>
	<c:import url="/fragment/434"></c:import>
</div>
<script type="text/javascript" charset="utf-8">
    seajs.use(['jQuery','roller'],function($,Roller){
    	var context=$("div.right_box");
    	Roller({
    		context:context,
    		paging:true,
    		itemSize:3,
    		page:3,
    		selector:{
    			container:"div.newdisk",
    			page:"div.new_tab a",
    			next:"a.next_page",
    			prev:"a.pre_page",
    			box:"div.goods_list",
    			items:"dl"
    		},
    		className:{pageSelected:"current_no"}
    	});
    });
</script>
<style>.album_box{height:220px;} div.rush_details{height:auto !important;}div.rush_details dl{margin-top:0}div.rush_details dd{margin:0}</style>