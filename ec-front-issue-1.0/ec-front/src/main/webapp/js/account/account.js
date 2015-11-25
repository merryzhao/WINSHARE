seajs.use(["jQuery"], function($){
    $("a[bind='cancel']").bind("click", function(){
        if (!confirm("确认要撤销?")) {
            return;
        }
        var id = $(this).attr("parma"), mark = $("#remark" + id), statusName = $(".statusName" + id), balance = $("b[bind='balance']"), frozen = $("b[bind='frozen']"), overagebalance = $("b[bind='overagebalance']");
        mark.html("<img src='../../images/loading.gif'/>");
	    $.ajax({
            url: "/customer/advanceaccount/refund/" + id + "/cancel?format=json",
            type: "GET",
            dataType: "json",
            success: function(data){
                if (data.status) {
                    if (data.cashApplyStatus.id == 44001) {
                        mark.html("您可以<a class='link2' href='#'>修改</a>，<a class='link2' href='javasrcipt:;' bind='cancel' parma='" + id + "'>撤销</a>改申请");
                    }
                    if (data.cashApplyStatus.id == 44003) {
                        mark.html("退款成功");
                    }
                    if (data.cashApplyStatus.id == 44004) {
                        balance.html("￥" + data.account.total.toFixed(2));
                        frozen.html("￥" + data.account.frozen.toFixed(2));
                        overagebalance.html("￥" + data.account.balance.toFixed(2));
                        if (data.account.canRefundMoeny <1) {
                            $("#refundhref").hide();
                        }else{
							$("#refundhref").show();
						}
                        mark.html("已撤销");
                    }
                    statusName.html(data.cashApplyStatus.name);
                    
                }
                else {
                    alert(data.message);
                }
            }
        });
    });
});
