seajs.use(["jQuery","config"],function($,conf){
    var elPop=$(".lipop"),context=$(".payment_choose_box_content");
    elPop.mouseover(function(){
        $(this).find("div.bankpop").show();
    });
    elPop.mouseout(function(){
        $(this).find("div.bankpop").hide();
    });
    context.delegate("li>img","click",function(e){
        $(this).parent().find("input[type='radio']").trigger("click");
        e.preventDefault();
        e.stopPropagation();
    });
    $("a[bind='confirm-post']").click(function(){
        var url=$("input[type='radio']").filter("[name='banks']:checked").val();
        if(!!url){
            window.open(conf.portalServer+url,"_blank");
        }else{
            alert("请选择一种付款方式！");
        }
    });
    $("a[bind='confirm-post-list']").click(function(){
        var obj=$("input[type='radio']").filter("[name='banks']:checked");
        if(obj.length>0){
            selectBank(obj);
            e.preventDefault();
            e.stopPropagation();
        }else{
            alert("请选择一种付款方式！");
        }
    });
    function selectBank(el){
        var bank = $(el).data("bank");
        var code = $(el).data("code");
        $("input[@name='order'][type='hidden']").each(function(index, order){
            if ($(order).attr("name") == 'order') {
                $("form[name='batchPayOrder']").append("<input type='hidden' name='id' value='" + $(order).val() + "'>");
            }
        });
        $("form[name='batchPayOrder']").append("<input type='hidden' name='bank' value='" + bank + "'>");
        if(!!code){
            $("form[name='batchPayOrder']").append("<input type='hidden' name='code' value='" + code + "'>");               
        }
        var form = $("form[name='batchPayOrder']");
        form.submit();
    }
});