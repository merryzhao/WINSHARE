var areaConstant = {
    country : 'select[areaLevel="country"]',
    province : 'select[areaLevel="province"]',
    city : 'select[areaLevel="city"]',
    district : 'select[areaLevel="district"]',
    chooseProvince : '<option value="-1">请选择省份</option>',
    chooseCity : '<option value="-1">请选择城市</option>',
    chooseDistrict : '<option value="-1">请选择区县</option>',
    option : '<option></option>',
    china : 23
} 
function initArea(co,p,c,d){
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
    if( $(areaConstant.country).val() != areaConstant.china){
    	hideWithoutCountry();
    }
}
function bindAreaEvent(){
    $(areaConstant.country).change(function(){
	   changed($(areaConstant.province), areaConstant.chooseProvince,function() {
		   buildPronvice($(areaConstant.country).val());
	   });
	   changed($(areaConstant.city),areaConstant.chooseCity);
	   changed($(areaConstant.district),areaConstant.chooseDistrict);
	   $(areaConstant.province).show();
	   $(areaConstant.city).show();
	   $(areaConstant.district).show();
        if($(this).val() != areaConstant.china){
        	hideWithoutCountry();
        }
    });
	$(areaConstant.province).change(function() {
    	changed($(areaConstant.city), areaConstant.chooseCity, function() {
    		buildCity($(areaConstant.country).val(), $(areaConstant.province).val());
    	});
        changed($(areaConstant.district),areaConstant.chooseDistrict, function() {
			buildDistrict($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val());
		});
        
    });
    $(areaConstant.city).change(function(){
	    changed($(areaConstant.district),areaConstant.chooseDistrict, function() {
			buildDistrict($(areaConstant.country).val(), $(areaConstant.province).val(), $(areaConstant.city).val());
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
}
function buildPronvice(co,p){
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