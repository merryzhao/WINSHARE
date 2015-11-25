define(function(require){
	var $ = require("jQuery"), 
		config = require("config"), 
		areaMap = require("./areadata"),
		listArea = require("../module/list-area"),
		ipUrl = "http://www.winxuan.com/ipaddress/ip?format=jsonp&callback=?",
		areaUrl = config.portalServer,
		iplocation = {
		    temp:{
		        TABITEM:'<div class="tab-item" area-id="{id}">{name}<i></i></div>',
		        CONTITEM:'<li><a href="javascript:void(0);" data-id="{id}">{name}</a></li>'
		    },
		    selector:{
		        context:"dl.express",
		        el:"div.store",
		        input:".store-input>span",
		        tab:".tab-1",
		        cont:".col-text-tab .list-text-4",
		        tip:".express-tip"
		    },
			init:function(){
			    this.context=$(this.selector.context);
			    this.el=$(this.selector.el);
			    this.tabs=this.el.find(this.selector.tab);
			    this.cont=this.el.find(this.selector.cont);
			    this.input=this.el.find(this.selector.input);
			    this.tip=this.context.find(this.selector.tip);
                this.loaded=false;
			    this.load();
			    this.bind();
			    listArea.init();
			},
			bind:function(){
                this.el.mouseover(function(e){iplocation.show(e);});
                $(document).mouseover(function(e){iplocation.hide(e);});
                this.el.delegate(".close","click",function(e){iplocation.hide(e);});
                this.tabs.delegate(".tab-item","click",function(e){
                    $(this).addClass("current");
                    $(this).nextAll().remove();
                    iplocation.loadList($(this));
                    listArea.createLetters();
                    e.stopPropagation();
                });
                this.cont.delegate("a","click",function(){
                    var districtId=$(this).data("id"),name=$(this).text(),
                        tab=iplocation.tabs.find(".current");
                    tab.attr("area-id",districtId);
                    tab.html(name+"<i></i>");
                    tab.removeClass("current");
                    iplocation.tabs.append('<div class="tab-item current">请选择<i></i></div>');
                    iplocation.loadList(iplocation.tabs.find(".current"),districtId);
                    listArea.createLetters();
                });
            },
            show:function(e){
                if(!iplocation.loaded){
                    iplocation.addressTitle();
                    iplocation.addressList();
                }
                iplocation.el.addClass("over");
                iplocation.loaded=true;
                e.stopPropagation();
            },
            hide:function(e){
                iplocation.el.removeClass("over");
                listArea.createLetters();
                e.preventDefault();
                e.stopPropagation();
			},
			loadList:function(el,id){
                var doms=el.siblings(),ids=[],i;
                $.each(doms,function(){
                    i=$(this).attr("area-id");
                    ids.push(i);
                });
                if(ids[4]||(ids[0]&&ids[0]!=23)){
                    iplocation.setDistrict(id);
                }else if(ids[3]){
                    iplocation.getTown(ids[3]);
                }else{
                    iplocation.getDistrict(ids[0],ids[1],ids[2]);
                }
			},
			load:function(level){
			    $.getJSON(ipUrl,function(data){
			        if(data.customerIP){
			            iplocation.render(data.customerIP,level);
			            iplocation.delivery(data);
			        }
			    });
			},
			render:function(data,level){
			    var address=[],input=this.input;
			    this.id=data.id;
			    $.each(data,function(k,v){
			        iplocation[k]=v;
			    });
		        address.push(this.country?this.country.name:"");
		        if(this.country&&this.country.id!=23){
		            address.push(this.area?this.area.name:"");
		        }
		        address.push(this.province?this.province.name:"");
		        address.push(this.city?this.city.name:"");
		        address.push(this.county?this.county.name:"");
		        input.text(address.join(""));
			},
			addressTitle:function(){
			    var tab=this.tabs,
			        h,
			        html="",
			        temp=this.temp.TABITEM;
			    if(this.country){
			        h = temp.replace(/\{id\}/g, this.country.id);
			        h = h.replace(/\{name\}/g, this.country.name);
			        html+=h;
			    }
			    if(this.province){
                    h = temp.replace(/\{id\}/g, this.province.id);
                    h = h.replace(/\{name\}/g, this.province.name);
                    html+=h;
                }
                if(this.city){
                    h = temp.replace(/\{id\}/g, this.city.id);
                    h = h.replace(/\{name\}/g, this.city.name);
                    html+=h;
                }
                if(this.county){
                    h = temp.replace(/\{id\}/g, this.county.id);
                    h = h.replace(/\{name\}/g, this.county.name);
                    html+=h;
                }
                if(this.town){
                    h = temp.replace(/\{id\}/g, this.town.id);
                    h = h.replace(/\{name\}/g, "请选择");
                    html+=h;
                }
                tab.html(html);
                tab.find(".tab-item:last-child").addClass("current");
			},
			addressList:function(){
			    if(this.town&&this.county){
			        this.getTown(this.county.id);
			    }else if(this.county&&this.city){
                    this.getDistrict(this.country.id,this.province.id,this.city.id);
                }else if(this.city&&this.province){
			        this.getDistrict(this.country.id,this.province.id,null);
			    }else if(this.province&&this.country){
                    this.getDistrict(this.country.id,null,null);
                }else{
                    this.getDistrict(null,null,null);
                }
			},
			getTown:function(id){
			    var url=areaUrl+"/area?districtId=";
			    iplocation.cont.html("正在加载中……");
                $.getJSON(url+id+"&format=json",function(data){
                    if(data.result&&data.towns){
                        iplocation.areaList(data.towns);
                    }
                });
            },
            getDistrict:function(country,province,city){
                var area;
                if(!!city&&!!province&&!!country){
                    area=iplocation.assemble(areaMap[country][1][province][1][city][1]);
                }else if(!city&&!!province&&!!country){
                    area=iplocation.assemble(areaMap[country][1][province][1]);
                }else if(!city&&!province&&!!country){
                    area=iplocation.assemble(areaMap[country][1]);
                }else if(!city&&!province&&!country){
                    area=iplocation.assemble(areaMap);
                }
                iplocation.areaList(area);
            },
            assemble:function(object){
                var list=[];
                $.each(object,function(k,v){
                    var area={};
                    area.id=k;
                    area.name=v[0];
                    list.push(area);
                });
                return list;
            },
			setDistrict:function(id){
			    $.post(areaUrl+"/ipaddress/selectArea?areaid="+id+"&id="+this.id+"&format=json",function(data){
                    iplocation.el.removeClass("over");
                    iplocation.loaded=false;
                    iplocation.delivery(data);
                    iplocation.load();
                    
                });
            },
            areaList:function(data){
                var cont=this.cont,
                    h,
                    html="",
                    temp=this.temp.CONTITEM,
                    data=this.bubbleSort(data);
                $.each(data,function(k,v){
                    h = temp.replace(/\{id\}/g, this.id);
                    h = h.replace(/\{name\}/g, this.name);
                    html+=h;
                });
                cont.html(html);
                listArea.addClasses();
            },
            delivery:function(data){
                if(data.support&&data.delivery){
                    iplocation.tip.html("满38元<b>免运费</b>且可<b>货到付款</b>");
                }else if(!data.support&&data.delivery){
                    iplocation.tip.html("满38元<b>免运费</b>");
                }else if(data.support&&!data.delivery){
                    iplocation.tip.html("可<b>货到付款</b>");
                }else{
                    iplocation.tip.html("文轩网将及时为您发货");
                }
            },
            bubbleSort:function(array){  
                var i = 0, len = array.length,  
                    j, d;  
                for(; i<len; i++){  
                    for(j=0; j<len; j++){  
                        if(array[i].name.length < array[j].name.length){  
                            d = array[j];  
                            array[j] = array[i];  
                            array[i] = d;  
                        }  
                    }  
                }  
                return array;  
            }
        };
    return iplocation;
});
