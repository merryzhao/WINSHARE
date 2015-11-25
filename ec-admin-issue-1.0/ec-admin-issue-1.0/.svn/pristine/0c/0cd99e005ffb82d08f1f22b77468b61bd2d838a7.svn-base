<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>订单跟踪查询</title>
<link rel="stylesheet"
	href="${contextPath}/css/slidingtabs-horizontal.css">
<style type="text/css">
div.querypart {
	margin-top: 10px;
	margin-bottom: 5px;
}

div.typepart {
	margin-top: 10px;
	margin-bottom: 5px;
}

div.timepart {
	margin-top: 10px;
	margin-bottom: 5px;
}

div.timenone {
	display: none;
}

div.tablepart {
	margin-top: 10px;
	margin-bottom: 5px;
}

table.track-table {
	width: 100%;
}

table.track-table tr {
	height: 30px;
	line-height: 20px;
	border: 1px solid #DFDFDF;
}

table.track-table tr:HOVER {
	background: #ffffe1
}

table.track-table th {
	background: #6293BB;
	color: #fff;
	font-weight: bolder;
	text-align: center;
}

table.track-table .date-time {
	width: 20%;
	margin-left: 20px;
}

table.track-table .type {
	width: 10%;
	min-width: 80px;
	margin-left: 20px;
}

table.track-table .number {
	width: 15%;
	margin-left: 20px;
}

table.track-table .name {
	width: 10%;
	margin-left: 50px;
}

table.track-table .reason {
	width: 45%;
	min-width: 120px;
	margin-left: 20px;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
			<div id="requestInfo" align="center" style="font-size: 15px; font-weight: bold;"></div>
				<h4>订单跟踪查询</h4>
				<div>
					<!-- 查询表单 -->
					<div>
						<form action="/order/track" method="post" id="form">
							<!-- input部分 -->
							<div class="querypart">
								订单号：<input type="text" name="orderId" value="">
								操作人：<input type="text" name="employee" value="">
								注册名：<input type="text" name="name" value="">
								<button type="button" onclick="ajaxpost('/order/track','form','contenttable')">查询</button>
							</div>
							<!-- 类型部分 -->
							<div class="typepart">
								类型：
								<c:forEach var="typechild" items="${types.children }">
									<input type="checkbox" name="type" value="${typechild.id}">
									<lable> ${typechild.name }</lable>
								</c:forEach>
							</div>
							<!-- 时间部分 -->
							<div class="timepart">
								<!-- 1 -->
								<div>
									<select id="time1" name="time1">
										<option value="1">订单跟踪日期</option>
										<option value="2">订单下单日期</option>
										<option value="3">订单发货日期</option>
									</select> <input type="radio" id="radio1_1" name="radio1"
										onclick="getTime(1,1)" />
									<lable>最近一天 </lable>
									<input type="radio" id="radio1_2" name="radio1"
										onclick="getTime(1,2)" />
									<lable>最近一周 </lable>
									<input type="radio" id="radio1_3" name="radio1"
										onclick="getTime(1,3)" />
									<lable>最近一月</lable>
									<input type="text" id="startTime1" name="startTime1"
										bind="datepicker" value="" onfocus="cleanRadio(1)">
									<lable>至</lable>
									<input type="text" id="endTime1" name="endTime1"
										bind="datepicker" value="" onfocus="cleanRadio(1)">
									<button id="but1" type="button"
										onclick="addDateCondition('time1','time2','time3','timenone2','but1');">添加</button>
								</div>
								<div class="timenone" id="timenone2">
									<select id="time2" name="time2">
										<option value="1">订单跟踪日期</option>
										<option value="2">订单下单日期</option>
										<option value="3">订单发货日期</option>
									</select> <input type="radio" id="radio2_1" name="radio2"
										onclick="getTime(2,1)" />
									<lable>最近一天</lable>
									<input type="radio" id="radio2_2" name="radio2"
										onclick="getTime(2,2)" />
									<lable>最近一周</lable>
									<input type="radio" id="radio2_3" name="radio2"
										onclick="getTime(2,3)" />
									<lable>最近一月</lable>
									<input type="text" id="startTime2" name="startTime2"
										bind="datepicker" value="" onfocus="cleanRadio(2)">
									<lable>至</lable>
									<input type="text" id="endTime2" name="endTime2"
										bind="datepicker" value="" onfocus="cleanRadio(2)">
									<button id="but2" type="button"
										onclick="addDateCondition('time2','time3','','timenone3','but2')">添加</button>
								</div>
								<div class="timenone" id="timenone3">
									<select id="time3" class="timenone" name="time3">
										<option value="1">订单跟踪日期</option>
										<option value="2">订单下单日期</option>
										<option value="3">订单发货日期</option>
									</select> <input type="radio" id="radio3_1" name="radio3"
										onclick="getTime(3,1)" />
									<lable>最近一天</lable>
									<input type="radio" id="radio3_2" name="radio3"
										onclick="getTime(3,2)" />
									<lable>最近一周 </lable>
									<input type="radio" id="radio3_3" name="radio3"
										onclick="getTime(3,3)" />
									<lable>最近一月</lable>
									<input type="text" id="startTime3" name="startTime3"
										bind="datepicker" value="" onfocus="cleanRadio(3)">
									<lable>至</lable>
									<input type="text" id="endTime3" name="endTime3"
										bind="datepicker" value="" onfocus="cleanRadio(3)">
								</div>
							</div>
						</form>
					</div>
					<!-- 结果列表 -->
					<div id="contenttable">
					</div>
				</div>
			</div>
		</div>
	</div>
		<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript">
		function addDateCondition(oneTime, twoTime, threeTime, showdiv, thisid) {
			$("#" + thisid).hide();
			var result = "";
			if (oneTime != "") {
				result = $("#" + oneTime + " option:selected").val();
				var resulttext = $("#" + oneTime + " option:selected").text();
				$("#" + oneTime).empty();// 清空下拉框
				$("<option value='"+result+"'>" + resulttext + "</option>")
						.appendTo("#" + oneTime)//添加下拉框的option		
			}
			if (twoTime != "" && result != "") {
				$("#" + twoTime + " option[value='" + result + "']").remove();
			}
			if (threeTime != "" && result != "") {
				$("#" + threeTime + " option[value='" + result + "']").remove();
			}
			$("#" + showdiv).show();
		}
		function getTime(id, type) {
			var today = new Date();
			var start = null;
			var end = null;
			if (type == 1) {
				start = today.getFullYear() + "-" + (today.getMonth() + 1)
						+ "-" + (today.getDate() - 1);
				end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
						+ today.getDate();
			}
			if (type == 2) {
				start = today.getFullYear() + "-" + (today.getMonth() + 1)
						+ "-" + (today.getDate() - 7);
				end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
						+ today.getDate();
			}
			if (type == 3) {
				start = today.getFullYear() + "-" + today.getMonth() + "-"
						+ today.getDate();
				end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
						+ today.getDate();
			}
			if (id == 1) {
				$("#startTime1").val(start);
				$("#endTime1").val(end);
			}
			if (id == 2) {
				$("#startTime2").val(start);
				$("#endTime2").val(end);
			}
			if (id == 3) {
				$("#startTime3").val(start);
				$("#endTime3").val(end);
			}
		}
		function cleanRadio(num) {
			switch (num) {
			case 1:
				$("#radio1_1").attr('checked', false);
				$("#radio1_2").attr('checked', false);
				$("#radio1_3").attr('checked', false);
				break;
			case 2:
				$("#radio2_1").attr('checked', false);
				$("#radio2_2").attr('checked', false);
				$("#radio2_3").attr('checked', false);
				break;
			case 3:
				$("#radio3_1").attr('checked', false);
				$("#radio3_2").attr('checked', false);
				$("#radio3_3").attr('checked', false);
				break;
			default:
				break;
			}
		}
		function ajaxpost(url, form, id) {
	    	$("#requestInfo").html('正在请求,请稍后......');
	    	$("#requestInfo").dialog("open");
			$.ajax({
				type : 'post', //可选get
				url : url, //这里是接收数据的程序
				data : getFormVal(form), //传给PHP的数据，多个参数用&连接
				dataType : 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等

				success : function(msg) {
					//这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
					$('#' + id).html(msg);
					$("#requestInfo").dialog("close");
				},
				error : function() {
					$("#requestInfo").html('请求失败');
				}
			})
		}
	    function getFormVal(form){
	    	var datevaule = "";
	    	var thisform=document.getElementById(form);
	    	//处理input 内容
	    	var inputs=thisform.getElementsByTagName("input");
	    	for(i=0;i<inputs.length;i++){//inputs.length
	    		  //name为 type 的  checkbox
	    		  if(inputs[i].name=="type"){
		    		  if(inputs[i].checked){
		    			  datevaule +=inputs[i].name+"="+inputs[i].value+"&"
		    		  } 
	    		  }else{
	    			  datevaule +=inputs[i].name+"="+inputs[i].value+"&"
	    		  } 
	    	}
	    	//处理select 内容
	    	var selects=thisform.getElementsByTagName("select");
	    	for(i=0;i<selects.length;i++){//inputs.length
	     	   if(i+1==selects.length){
	     		  datevaule += selects[i].name+"="+selects[i].value
	     	   }else{
	     		  datevaule +=selects[i].name+"="+selects[i].value+"&"
	        	   }
	    	}
	    	return datevaule;
	    }
	    $("#requestInfo").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 250,
			height : 80
		});
	</script>
</body>
</html>
