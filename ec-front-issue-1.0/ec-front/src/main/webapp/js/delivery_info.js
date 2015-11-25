seajs.use(["jQuery", "areaSelector"], function($, area){


    var view = {
        init: function(){
            this.country = $("select[areaLevel ='country']");
            this.province = $("select[areaLevel ='province']");
            this.district = $(".district");
            this.city = $(".city");
            this.datashow = $("tbody.#datashow");
            area.init(23, -1, -1, -1);
            this.bind();
            
        },
        bind: function(){
        
            var obj = this;
            this.province.change(function(){
                obj.change(this)
            });
            this.country.change(function(){
                obj.change(this)
            });
            this.city.change(function(){
                obj.change(this)
            });
            this.district.change(function(){
                obj.send()
            });
        },
        change: function(){
            var id = this.district.val();
            if (id > 0) {
                this.send();
            }
            else {
                this.waitinfo("请选择地区");
            }
            
        },
        send: function(){
            var id = this.district.val();
            var url = "http://www.winxuan.com/deliveryinfo/area/" + id + "?format=json";
            var obj = this;
            $.ajax({
                url: url,
                beforeSend: function(){
                    obj.waitinfo("加载中");
                },
                error: function(){
                    obj.waitinfo("加载失败.请致电客服");
                },
                success: function(d){
                    obj.reload(d);
                }
            });
        },
        waitinfo: function(msg){
            var html = "<tr>";
            html += "<td colspan='5'>" + msg + "</td>";
            html += "</tr>";
            this.datashow.html(html);
        },
        reload: function(d){
            var html = "";
            for (var i in d.deliveryInfo) {
                var item = d.deliveryInfo[i];
				
                if (item.id < 0) {
                    continue
                };
                html += "<tr>" ;
                html +=	"<td>";
                html +=	item.area.name;
                html +=	"</td>";
                html +=	"<td>";
                html += item.deliveryType.name;
                html += "</td>";
                html += "<td>";
                html += item.description;
                html += "</td>";
                
                html += "<td>";
               
				if(item.deliveryType.id == 1){//平邮
					if(this.country.val()!=23){
						html +="30-50天,以邮局送货时间为准";
					}
					else if (this.province.val() == 175) {
						html += "3-7天,以邮局送货时间为准";
					}
					else if(this.province.val() == 178 || this.province.val() == 180||this.province.val()== 179||this.province.val()== 151||this.province.val()== 176){
						html += "15-30天,以邮局送货时间为准";
					}
					
					else {
						html += "7-15天,以邮局送货时间为准";
					}
				}
				else if(item.deliveryType.id == 2){//EMS
					html += "2-4天";
				}
				else if(item.deliveryType.id == 3){//快递
					if (this.province.val() == 175) {
						html += "1-3天";
					}else {
						html += "3-5天";
					}
				}
				
                html += "</td>";
                html += "<td>";
              
                if(item.area.supportCod && item.deliveryType.id == 3) 
                     html += "支持";
                else{
                     html+="暂不支持";
                }
                
                html += "</td>";
                html += "</tr>";
            }
            this.datashow.html(html);   
            
        }        
    };
    view.init();
    
});
