define(function(require) {
	var $ = require("jQuery"), config = require("../etc/app-config"), addresspoint = require("./point");

	var template = {
		orderinfo : "<div class='orderinfo'><div class='backimg ${backimg}'  ></div><span class='status'>${status}</span><br/><span class='orderid'>${orderid}</span><br/><span class='saleprice'>¥${saleprice}</span><br/>${address}</div>"
	};

	function Map() {
		this.init();
		this.bind();
       // this.audio = new Audio();
	};


	Map.prototype = {
		init : function() {
			this.map = new BMap.Map("map-canvas");
			this.map.centerAndZoom("中国石家庄", 5);
			this.map.disableScrollWheelZoom();
			//禁用滚轮放大缩小
			this.map.disableDoubleClickZoom();
			//禁用双击放大
			this.map.enableContinuousZoom();
			this.map.disableDragging();

		},
		bind : function() {
			var obj = this;
			this.map.addEventListener("tilesloaded", function() {
				obj.dellog()
			});
		},
		tip : function(data) {
			var obj = this;
			var address = data.province + data.city;
			var imageddress = "";
			var datalength = data.lastProcessTime.length;
			var newdate = data.lastProcessTime.substring(datalength - 8, datalength);
			var orderinfo = template.orderinfo.replace("${orderid}", data.orderId).replace("${backimg}", "info"+data.statusId).replace("${status}", data.status).replace("${address}", address).replace("${lastProcessTime}", newdate).replace("${saleprice}", data.saleprice.toFixed(2));
			var nowsalePrice =data.saleprice.toFixed(2); 
            // if(nowsalePrice>800&&data.statusId==8002){
              		// $(this.audio).attr("src","audio/style8002.mp3");
			         // this.audio.play();
              // };
			obj.geocoder(address, orderinfo);
		},
		/**
		 * 删除log
		 */
		dellog : function() {
			var anchorBL = $("div.anchorBL");
			anchorBL.remove();
		},
		animation : function(point, info) {
			var obj = this;
			var marker = new BMap.Marker(point);
			//marker.setIcon(icon);
			this.map.addOverlay(marker);
			window.setTimeout(function() {
			obj.map.removeOverlay(marker);
			}, 10000)
			marker.setAnimation(BMAP_ANIMATION_BOUNCE)//跳动
			var label = new BMap.Label(info, {
				offset : new BMap.Size(20, -4)
			});
			label.setStyle({
				border : "none",
				background : "none"
			});
			marker.setLabel(label);
		},
		geocoder : function(address, orderinfo) {
			var obj = this;
			var cachepoint = addresspoint[address];
			if(cachepoint) {
				var point = new BMap.Point(cachepoint.lng, cachepoint.lat);
				this.animation(point, orderinfo);
			} else {
				var mapGeo = new BMap.Geocoder();
				console.info(address + " Cache not found,Uses the api!");
				mapGeo.getPoint(address, function(point) {
					if(!point) {
						console.info(address + " point is not fount!")
					} else {
						obj.animation(point, orderinfo);
					}
				}, "中国")
			}

		},
		calculate : function(addr) {
			return ["longitude and latitude"];
		}
	};
	return Map;
});
