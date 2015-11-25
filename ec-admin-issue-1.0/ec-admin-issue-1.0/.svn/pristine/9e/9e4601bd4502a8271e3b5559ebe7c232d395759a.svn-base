

$().ready(function(){
	$("input[bind='datepicker']").datepicker("option","maxDate",new Date());
	$("#actionFlag").val("");
	var productSaleOnShelfStatus = $("#productSaleOnShelfStatus").val();	 //上架
	var productSaleOffShelfStatus = $("#productSaleOffShelfStatus").val();	//下架
	var productSaleDeleteStatus = $("#productSaleDeleteStatus").val();		//删除

	var productComplexList = {
			init:function(){

				$("SELECT[name=statusSelect]").each(function(i,j){						// 初始化套装书的状态
					var selectObj = $(this);
					var psStatus=selectObj.attr("psStatus");
					var isDel = psStatus == productSaleDeleteStatus? true : false;		//是否是已删除状态的商品
					productComplexList.chooiseStatus(psStatus, selectObj);
				});
				$("#statusChange").live("change",this.statusChange);
			},
			statusChange:function(){
				var selectObj = $(this);
				var productSaleId=selectObj.attr("psid");
				var selectVal = selectObj.val();
				if(selectVal == productSaleOnShelfStatus){	
					productComplexList.onShelf(productSaleId,selectObj)
				}else if(selectVal == productSaleOffShelfStatus){
					productComplexList.offShelf(productSaleId,selectObj)
				}else if(selectVal == productSaleDeleteStatus){
					productComplexList.deleteShelf(productSaleId,selectObj)
				}
			},
			onShelf:function(productSaleId,selectObj){			//执行上架操作
				if(!confirm("确认上架该商品吗?")){
					return;
				}
				$.ajax({
					url : "/product/offproduct",
					async : false,
					cache : false,
					type : 'POST',
					dataType : 'json',
					data : {
						"proId" : productSaleId,
						"format" : 'json'
					},
					error : function() {// 请求失败处理函数
						alert('请求失败');
						productComplexList.chooiseStatus(selectObj.attr("psStatus"),selectObj);
					},
					success : function(data) { // 请求成功后回调函数。
						if(data.status){
							alert("上架成功");
							productComplexList.chooiseStatus(productSaleOnShelfStatus,selectObj);
						}else{
							alert("上架失败!请检查套装书内的商品是否有没有上架的!");
							productComplexList.chooiseStatus(selectObj.attr("psStatus"),selectObj);
						}
					}
				});
			},
			offShelf:function(productSaleId,selectObj){			//执行下架操作
				if(!confirm("确认下架该商品吗?")){
					return;
				}
				$.ajax({
					url : "/product/stopproduct",
					async : false,
					cache : false,
					type : 'POST',
					dataType : 'json',
					data : {
						"proId" : productSaleId,
						"format" : 'json'
					},
					error : function() {// 请求失败处理函数
						alert('请求失败');
						productComplexList.chooiseStatus(selectObj.attr("psStatus"),selectObj);
					},
					success : function(data) { // 请求成功后回调函数。
						alert("下架成功");
						productComplexList.chooiseStatus(productSaleOffShelfStatus,selectObj);
					}
				});
			},
			deleteShelf:function(productSaleId,selectObj){		//执行删除操作
				if(!confirm("确认删除该商品吗? 删除将再不能更改状态!")){
					return;
				}
				$.ajax({
					url : "/product/deleteproduct",
					async : false,
					cache : false,
					type : 'POST',
					dataType : 'json',
					data : {
						"proId" : productSaleId,
						"format" : 'json'
					},
					error : function() {// 请求失败处理函数
						alert('请求失败');
						productComplexList.chooiseStatus(selectObj.attr("psStatus"),selectObj);
					},
					success : function(data) { // 请求成功后回调函数。
						alert("删除成功");
						productComplexList.chooiseStatus(productSaleDeleteStatus,selectObj);
					}
				});
			},
			chooiseStatus:function(statusId,selectObj){
				var isDel = statusId == productSaleDeleteStatus? true : false;
				var options = selectObj.find("option");
				options.each(function(m,n){
					var value = $(n).val();
					if(isDel){
						if(value!=productSaleDeleteStatus){
							$(n).remove();
						}
					}else{
						if(statusId==value){
							$(n).attr("selected",true);
						}
					}
				});
			}
	};
	productComplexList.init();

});

