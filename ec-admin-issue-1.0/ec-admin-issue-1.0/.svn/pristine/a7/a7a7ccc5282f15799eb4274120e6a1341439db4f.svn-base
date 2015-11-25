$(document).ready(function() {
	ProductSaleLabel.initEvent();
	ProductSaleLabel.initZtree();
});
(function() {
	window.ProductSaleLabel = {};

	ProductSaleLabel.saveProductSaleLabel = {
		add : false,
		modity : false
	};
	//发货仓库dc信息
	ProductSaleLabel.dc = {

	};
	//初始化事件
	ProductSaleLabel.initEvent = function() {
		ProductSaleLabel.initErrMsg();
		//初始化添加按钮
		$("#add_label_button").click(function() {
			ProductSaleLabel.saveProductSaleLabel.add = true;
			ProductSaleLabel.saveProductSaleLabel.modity = false;
			/*$(".dc").val("");
			$("#grade").val("");
			$("#weights").val("");*/

			$("#add_label").dialog({
				height : 400,
				width : 500,
				title : "商品标签新增界面",
				modal : true
			});

		});
		$("#labelName").click(function() {
			$("#labelName").val("");
			$("#labelId").val("");
		});

	};
	ProductSaleLabel.initZtree = function() {
		var setting = {
			view : {
				addHoverDom : addHoverDom,
				removeHoverDom : removeHoverDom,
				selectedMulti : false
			},
			edit : {
				enable : true,
				editNameSelectAll : true,
				showRemoveBtn : showRemoveBtn
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				beforeDrag : beforeDrag,
				beforeEditName : beforeEditName,
				beforeRemove : beforeRemove,
				beforeRename : beforeRename,
				onRemove : onRemove,
				onRename : onRename,
				onClick : onClick
			}

		};
		var labels = $("#labels").html();
		var zNodes = null;
		if (labels != "" && null != labels) {
			zNodes = jQuery.parseJSON(labels);
		} else {
			zNodes = [ {
				id : 1,
				pId : 0,
				name : "商品标签",
				open : false
			} ];
		}

		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		function onClick(event, treeId, treeNode) {
			var id = treeNode.id;
			var tId = treeNode.tId;
			$("#addBtn_" + id).hide();
			$("#" + tId + "_edit").hide();
			$("#" + tId + "_remove").hide();
			$("#labelId").val(id);
			$("#labelName").val(treeNode.name);
		}
		function beforeEditName(treeId, treeNode) {
			return confirm("确认要这个修改标签吗？");
		}
		// 删除节点
		function beforeRemove(treeId, treeNode) {
			confirm("确认删除这个标签吗？");
			var id = treeNode.id;
			var auditUrl = '/product/label/deleteLabel?format=json&id=' + id;
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : auditUrl,
				error : function() {// 请求失败处理函数
					alert('请求失败');
				},
				success : function(data) { // 请求成功后回调函数。
					$("#message").html(data.message);
					$("#auditResult").dialog('open');
				}
			});
		}
		// 删除节点后
		function onRemove(e, treeId, treeNode) {
			//					showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		// 修改节点
		function beforeRename(treeId, treeNode, newName, isCancel) {
			var labelName = treeNode.name;
			var id = treeNode.id;
			var tId = treeNode.tId;
			var pId = treeNode.pId;
			if (newName.length == 0) {
				alert("标签名称不能为空！");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function() {
					zTree.editName(treeNode)
				}, 10);
				return false;
			}
//			var id = tId.slice("treeDemo_".length);
			if (labelName.indexOf("new node") != -1 || labelName == "商品标签") {
				var auditUrl = '/product/label/create?format=json&labelName='
						+ newName + '&parentId=' + pId;
				$.ajax({
					async : false,
					cache : false,
					type : 'GET',
					url : auditUrl,
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) { // 请求成功后回调函数。
						$("#message").html(data.message);
						$("#auditResult").dialog('open');
					}
				});
			} else {
				var auditUrl = '/product/label/' + id
						+ '/edit?format=json&labelName=' + newName;
				$.ajax({
					async : false,
					cache : false,
					type : 'GET',
					url : auditUrl,
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) { // 请求成功后回调函数。
						$("#message").html(data.message);
						$("#auditResult").dialog('open');
					}
				});
			}

		}
		function onRename(e, treeId, treeNode, isCancel) {
		}
		function showRemoveBtn(treeId, treeNode) {
			return treeNode.pId != null;
		}

		function showLog(str) {
			if (!log)
				log = $("#log");
			log.append("<li class='" + className + "'>" + str + "</li>");
			if (log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
					.getSeconds(), ms = now.getMilliseconds();
			return (h + ":" + m + ":" + s + " " + ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag
					|| $("#addBtn_" + treeNode.tId).length > 0)
				return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
					+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_" + treeNode.tId);
			if (btn)
				btn.bind("click", function() {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.addNodes(treeNode, {
						id : (100 + newCount),
						pId : treeNode.id,
						name : "new node" + (newCount++)
					});
					return false;
				});
		}
		;
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_" + treeNode.tId).unbind().remove();
		}
		;
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	};

	//表单提交验证
	ProductSaleLabel.validateForm = function() {
		if ($("#grade").val().replace(/^(\s+)|(\s+)$/g, "") == "") {
			alert("销售级别不能为空！");
			$("#grade").focus();
			return false;
		} else {
			if (!(/^[a-zA-Z]{1,1}[+-]{0,1}$/).test($("#grade").val())) {
				alert("销售级别只能是字母或者'+','-'号！");
				$("#grade").focus();
				return false;
			}
		}
		if ($("#weights").val().replace(/^(\s+)|(\s+)$/g, "") == "") {
			alert("权重数值不能为空！");
			$("#weights").focus();
			return false;
		} else {
			if (!(/^0\.[0-9]{0,2}$/).test($("#weights").val())) {
				alert("权重数值只能填写0到1之间的有效小数！");
				$("#weights").focus();
				return false;
			}
		}
		return true;
	};
	ProductSaleLabel.closeDialog = function() {
		$("#add_ProductSaleLabel").dialog("close");
	};
	//初始化错误信息
	ProductSaleLabel.initErrMsg = function() {
		var errMsg = $("#errMsg").html();
		if (null != errMsg && "" != errMsg) {
			alert(errMsg);
		}
	};

})();