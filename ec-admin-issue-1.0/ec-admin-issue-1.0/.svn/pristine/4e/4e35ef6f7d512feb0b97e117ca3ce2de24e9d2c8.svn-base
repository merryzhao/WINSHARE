
$(function(){
    window.sms = {
        check : function(elm){
            if ($("#remark").val().trim()==""){
                alert("请填写备注.");return false;
            }
            
            var len = 0;
            $("input[name=channels]").each(function(i,e){ if($(e).attr("checked"))len++; });
            if (len == 0){ alert("至少选中一个渠道");return false; }
            
            len = 0;
            $("input[name=paytype]").each(function(i,e){ if($(e).attr("checked"))len++;  });
            if (len == 0){ alert("至少选中一个支付方式");return false; }
            
            len = 0;
            $("input[name=deliverytype]").each(function(i,e){ if($(e).attr("checked"))len++;    });
            if (len == 0){  alert("至少选中一个发货方式"); return false;  }
            
            var great = $("#kindgreat").val();
            var less = $("#kindless").val();
            if (great=="" || less ==""){
                alert("缺货品种数不能为空"); return false;
            } else if (parseInt(great) > parseInt(less) ){
                alert("缺货品种数不正确!"); return false;
            }
            
            
            
            if ($("#message").val().trim() == ""){
                alert("短信消息不能为空!");return false;
            }
            
            $(elm).val("保存中...");
            
            return true;
        }
    }
    
});