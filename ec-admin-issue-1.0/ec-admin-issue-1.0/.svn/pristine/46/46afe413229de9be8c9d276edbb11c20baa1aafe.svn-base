$().ready(function() {
	// 表单验证
	$("#orderInvoiceCreateForm").validate({
		rules : {
			title : {
				required : true
			},
			money : {
				required : true,
				number : true,
				range : [ 0.0, 999999999.999 ]
			},
			quantity : {
				required : true,
				number : true,
				range : [ 1, 999999999 ]
			},
			consignee : {
				required : true
			},
			mobile : {
				multiphone : true
			},
			email : {
				required : true,
				email : true
			},
			zipCode : {
				required : true,
				isZipCode : true
			},
			address : {
				required : true
			}
		},

		messages : {
			title : {
				required : "发票抬头不能为空"
			},
			money : {
				required : "金额不能为空",
				number : "金额格式错误",
				range : "金额错误"
			},
			quantity : {
				required : "商品数量不能为空",
				number : "数量输入错误",
				range : "数量输入错误"
			},
			consignee : {
				required : "收货人不能为空"
			},
			mobile : {
				multiphone : "电话格式错误"
			},
			email : {
				required : "邮箱不能为空",
				email : "邮箱格式错误"
			},
			zipCode : {
				required : "邮编不能为空",
				isZipCode : "邮编格式错误"
			},
			address : {
				required : "地址不能为空"
			}
		}
	});

});
