seajs.use(["jQuery", "validator"], function($, validator){
    validator($);
    var view = {
		flag : true,
        tab_class: "current_info",
        init: function(){
            this.newquestion = $("#newquestion");
            this.txtemail = $("input[name='email']");
            this.txtorder = $("input[name='orderId']");
            this.txtphone = $("input[name='phone']");
            this.txttitle = $("input[name='title']");
            this.txtcontent = $("textarea[name='content']");
            this.txtverifycode = $("input[name='verifyCode']");
            this.tabcontent02 = $("div.tabcontent02");
            this.tab = $("ul.infor_tab");
            this.tabArray = ["tabcontent01", "tabcontent02"];
            view.bind();
        },
        bind: function(){
        
            $("a[bind='changeVerifyCode']").click(function(){
                view.changeCode()
            });
            this.tab.find("li").click(function(){
                view.changeState(this);
            });
            this.newquestion.click(function(){
                view.request()
            });
            
        },
        request: function(){
            var obj = this;
            var param = this.seri(this.tabcontent02) + "&content=" + this.txtcontent.val();
            var url = "/customer/complaint/save?format=json";
            if (!this.check(param)) {
                return false
            };
			if(!obj.flag){
				alert("短时间内请勿重复提交");
				return false;
			};
            $.ajax({
                type: "POST",
				beforeSend :function(){//
				 obj.flag = false;
				 obj.changeCode();
				},
                dataType: "json",
                url: url,
                data: param,
                success: function(d){
                    obj.reload(d);
					obj.flag = true;
                }
            });
        },
        reload: function(d){
            alert(d.message);
           
             if (d.result==1) {
			    this.clear();
				window.location.href = "http://www.winxuan.com/customer/complaint";
		     }
        },
        clear: function(){
            this.txtorder.val("");
            this.txttitle.val("");
            this.txtcontent.val("");
            this.txtverifycode.val("");
            
        },
        changeCode: function(){
            var srcUrl = $(".verifyCodeImg").attr("src");
            $(".verifyCodeImg").attr("src", srcUrl + "?d=" + Math.random);
        },
        changeState: function(tag){
            var el = $(tag);
            this.tab.find("li").removeClass(view.tab_class);
            el.addClass(view.tab_class);
            $.each(this.tabArray, function(){
                $("." + this).hide();
            });
            $('div.' + el.attr("target")).show();
        },
        check: function(tag){
        
            var flag = reg.emila.test(this.txtemail.val());
            if (!flag) {
                alert("请输入正确的邮箱地址");
                return false;
            }
            flag = reg.phone.test(this.txtphone.val()) || reg.mobile.test(this.txtphone.val());
            
            if (!flag) {
                alert("请正确输入联系电话");
                return false;
            }
            flag = reg.order.test(this.txtorder.val());
            if ($.trim(this.txtorder.val()) != "") {
                if (!flag) {
                    alert("请输入正确的订单号");
                    return false;
                }
            }
            if ($.trim(this.txttitle.val()) == "") {
                alert("请输入投诉类型");
                return false;
            }
            if (this.txtcontent.val() == "") {
                alert("请输入投诉内容");
                return false;
            }
            if (this.txtcontent.length > 800) {
                alert("您输入的内容太多了");
                return false;
            }
            return true;
        },
        seri: function(context){
            var input = context.find("input[type='text']");
            return input.serialize();
        }
    };
    view.init();
    var reg = {
        name: /^[\s\S]{2,30}$/,
        zipCode: /^[0-9]{1}[0-9]{5}$/,
        mobile: /^1[1-9]{1}[0-9]{9}$/,
        phone: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/,
        emila: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
        order: /^[0-9]{14}$/
    };
    
})
