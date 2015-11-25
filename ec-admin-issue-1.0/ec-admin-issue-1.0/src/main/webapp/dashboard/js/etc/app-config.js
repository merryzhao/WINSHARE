define({
	
	/*数据接口地址*/
	"poll-data-url":"http://console.winxuan.com/dashboard/order?format=json",
	
	"poo-data-devurl":"http://console.winxuan.com/dashboard/summary?format=json&start=2012-11-13 00:00:00",

	/*
		提供的数据类型
		有两种格式可以选择，json和jsonp xml格式暂不支持
	*/
	"poll-data-type":"json",

	/*统计信息获取接口*/
	"summary-data-url":"http://console.winxuan.com/dashboard/summary?format=json",

	/*
		统计信息接口格式
		有两种格式可以选择，json和jsonp xml格式暂不支持
	*/
	"summary-data-type":"json",

	/*服务器配置信息*/
	"global-param":{

		/*渠道编码*/
		"channel":[],

		/*数据时间范围*/
		"date-scope":{
			"start":"2012-11-12 00:00:00",
			"end":"2012-11-12 12:00:00",
		}
	}

});