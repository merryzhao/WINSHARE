var areaConstant = {
    country : 'select[areaLevel="country"]',
    province : 'select[areaLevel="province"]',
    city : 'select[areaLevel="city"]',
    district : 'select[areaLevel="district"]',
    district1 : 'select[areaLevel="district1"]',
    town : 'select[areaLevel="town"]',
    chooseProvince : '<option value="-1">请选择省份</option>',
    chooseCity : '<option value="-1">请选择城市</option>',
    chooseDistrict : '<option value="-1">请选择区县</option>',
    //多选择器时用
    chooseDistrict1 : '<option value="-1">请选择区县</option>',
    chooseTown : '<option value="-1">请选择乡镇</option>',
    option : '<option></option>',
    china : 23
}
function initArea(co,p,c,d,t){
    bindAreaEvent();
    for(var v in areaMap){
    	var dom = $(areaConstant.option).attr("value",v).text(areaMap[v][0]);
    	if(v == co){
    		dom.attr("selected","selected");
    	}
    	$(areaConstant.country).append(dom);
    	
    }
    buildPronvice(co,p);
    buildCity(co,p,c);
    buildDistrict(co,p,c,d);
    buildDistrict1(co,p,c,d);
	buildTown(co,p,c,d,t);
//    if( $(areaConstant.country).val() != areaConstant.china){
//    	hideWithoutCountry();
//    }
}
function bindAreaEvent(){
    $(areaConstant.country).change(function(){
    	var co = $(areaConstant.country).val();
	   changed($(areaConstant.province), areaConstant.chooseProvince,function() {
		   buildPronvice(co);
	   });
	   var p = $(areaConstant.province).val();
	   changed($(areaConstant.city),areaConstant.chooseCity,function() {
		   buildCity(co,p);
	   });
	   changed($(areaConstant.district),areaConstant.chooseDistrict,function() {
		   buildDistrict(co,p,$(areaConstant.city).val());
	   });
	   changed($(areaConstant.district1),areaConstant.chooseDistrict1, function() {
			buildDistrict1(co, p, $(areaConstant.city).val());
		});
	   changed($(areaConstant.town),areaConstant.chooseTown, function() {
	    	buildTown(co, p, $(areaConstant.city).val(),$(areaConstant.district).val(),$(areaConstant.town).val());
		});
	   $(areaConstant.province).show();
	   $(areaConstant.city).show();
	   $(areaConstant.district).show();
	   $(areaConstant.town).show();
//       if($(this).val() != areaConstant.china){
//        	hideWithoutCountry();
//        }
    });
	$(areaConstant.province).change(function() {
    	changed($(areaConstant.city), areaConstant.chooseCity, function() {
    		buildCity($(areaConstant.country).val(), $(areaConstant.province).val());
    	});
    	changed($(areaConstant.district),areaConstant.chooseDistrict, function() {
 			buildDistrict($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val());
 		});
    	changed($(areaConstant.district1),areaConstant.chooseDistrict1, function() {
			buildDistrict1($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val());
		});
    	changed($(areaConstant.town),areaConstant.chooseTown, function() {
	    	buildTown($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val(),$(areaConstant.district).val(),$(areaConstant.town).val());
		});
    });
    $(areaConstant.city).change(function(){
	    changed($(areaConstant.district),areaConstant.chooseDistrict, function() {
			buildDistrict($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val());
		});
	    changed($(areaConstant.district1),areaConstant.chooseDistrict1, function() {
			buildDistrict1($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val());
		});
	    changed($(areaConstant.town),areaConstant.chooseTown, function() {
	    	buildTown($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val(),$(areaConstant.district).val(),$(areaConstant.town).val());
		});
    });
    $(areaConstant.district).change(function(){
	    changed($(areaConstant.town),areaConstant.chooseTown, function() {
	    	buildTown($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val(),$(areaConstant.district).val(),$(areaConstant.town).val());
		});
    });
}
function changed(obj,opt,fn){
    obj.empty();
    obj.append(opt);
    if(fn != null && typeof(fn) != "undefine"){
        fn();
    }
}

function hideWithoutCountry(){ 
    $(areaConstant.province).hide();
    $(areaConstant.city).hide();
    $(areaConstant.district).hide();
    $(areaConstant.district1).hide();
    $(areaConstant.town).hide();
}
function buildPronvice(co,p){
	 if(co != null && co != -1){
		 var ap = areaMap[co][1];
		    var index  = 0;
		    for(var v in ap){
		    	var dom = $(areaConstant.option).attr("value",v).text(ap[v][0]);
		    	if(v == p){
		    		dom.attr("selected","selected");
		    	}
		    	$(areaConstant.province).append(dom);
		        index++;
		    }  
		    if(index == 1){
		        $(areaConstant.province).children("[value='-1']").remove();
		    }
	 }
}
function buildCity(co,p,c){
    if(p != null &&  p != -1){
        var ac = areaMap[co][1][p][1];
        var index  = 0;
        for(var v in ac){
        	var dom = $(areaConstant.option).attr("value",v).text(ac[v][0]);
        	if(v == c){
        		dom.attr("selected","selected");
        	}
        	$(areaConstant.city).append(dom);
            index++;
        }
        if(index == 1){
            $(areaConstant.city).children("[value='-1']").remove();
        }
    }
}
function buildDistrict(co,p,c,d){
    if( p != null && c != null &&  p != -1 && c != -1){
        var ad = areaMap[co][1][p][1][c][1];
        var index  = 0;
        for(var v in ad){
        	var dom = $(areaConstant.option).attr("value",v).text(ad[v][0]);
        	if(v == d){
        		dom.attr("selected","selected");
        	}
        	$(areaConstant.district).append(dom);
            index++;
        }
        if(index == 1){
            $(areaConstant.district).children("[value='-1']").remove();
        }
    }
}
function buildDistrict1(co,p,c,d){
    if( p != null && c != null &&  p != -1 && c != -1){
        var ad = areaMap[co][1][p][1][c][1];
        var index  = 0;
        for(var v in ad){
        	var dom = $(areaConstant.option).attr("value",v).text(ad[v][0]);
        	if(v == d){
        		dom.attr("selected","selected");
        	}
        	$(areaConstant.district1).append(dom);
            index++;
        }
        if(index == 1){
            $(areaConstant.district1).children("[value='-1']").remove();
        }
    }
}

function buildTown(co,p,c,d,t){
	if(co!=null&&co!=-1&&p != null && c != null && d != null && p != -1 && c != -1 && d != -1){
		var ad = null;
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "/order/area/"+d,
			data : {'format':'json'},
			error : function() {
			},
			success : function(data) { 
				if(data.result==1){
					ad = data.children;
				}
			}
		});
		var index = 0;
		$.each(ad, function(key, value){
			var dom = $(areaConstant.option).attr("value", value.id).text(value.name);
			if(value.id == t){
				dom.attr("selected","selected");
			}
			$(areaConstant.town).append(dom);
			index++;
		});
		if(index == 1){
			$(areaConstant.town).children("[value='-1']").remove();
		}
	}
}
