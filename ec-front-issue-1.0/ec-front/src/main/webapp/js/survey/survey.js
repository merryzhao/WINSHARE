seajs.use(["jQuery"], function($){
    var view = {
        init: function(){
            this.radioItems = $("input[name='radio']");
            this.checkboxItems = $("input[name='checkbox']");
            this.submit = $("button[name='submit']");
            this.bind();
        },
        bind: function(){
            this.radioItems.each(function(){
                $(this).click(view.radioAction);
            });
            this.checkboxItems.each(function(){
                $(this).click(view.checkboxAction);
            });
            this.submit.click(view.submitAction);
        },
        submitAction: function(){
            var item = $("ol[class='content']").find("li");
            var params = [];
            item.each(function(){
                if ($(this).hasClass("text")) {
                    var text = $(this).find("input[name='text']").val();
                    var textarea = $(this).find("textarea[name='textarea']").val();
                    if (!!text) {
                        params.push($(this).attr('itemId') + "=0;" + encodeURIComponent(text));
                    }
                    else 
                        if (!!textarea) {
                            params.push($(this).attr('itemId') + "=0;" + encodeURIComponent(textarea));
                        }
                }
                else 
                    if ($(this).hasClass("select")) {
                        var itemId = $(this).attr('itemId');
                        var radios = $(this).find("input[name='radio']");
                        radios.each(function(){
                            if (this.checked) {
                                var radioText = $(this).parent().find("textarea[name='radio_text']").val();
                                if (!!radioText) {
                                    params.push(itemId + "=" + $(this).val() + ";" + encodeURIComponent(radioText));
                                }
                                else {
                                    params.push(itemId + "=" + $(this).val());
                                }
                            }
                        });
                        var checkboxes = $(this).find("input[name='checkbox']");
                        checkboxes.each(function(){
                            if (this.checked) {
                                var checkboxText = $(this).parent().find("textarea[name='checkbox_text']").val();
                                if (!!checkboxText) {
                                    params.push(itemId + "=" + $(this).val() + ";" + encodeURIComponent(checkboxText));
                                }
                                else {
                                    params.push(itemId + "=" + $(this).val());
                                }
                            }
                        });
                    }
            });
            var startTime = $("input[name='start_time']").val();
            var surveyId = $(".survey").attr("surveyId");
            var data = params.join("|");
            if (!data) {
                alert("提交前，请确保参与了至少一项调查，谢谢");
				return false;
            }
            var submitUrl = '/survey/submit?format=json';
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: submitUrl,
                data: "submit=" + data + "&startTime=" + startTime + "&surveyId=" + surveyId,
                error: function(){//请求失败处理函数
                    alert('出现错误，请联系技术人员');
                },
                success: function(data){ //请求成功后回调函数。  
                    if (data.result == 1) {
                        var url = $("input[name='referer']").val();
                        if (!url) {
                            url = "http://www.winxuan.com";
                        }
                        window.location = "/survey/" + surveyId + "/finish?referer=" + encodeURIComponent(url);
                    }
                    else {
                        alert(data.message);
                    }
                }
            });
        },
        radioAction: function(){
            $(this).parent().parent().parent().find("input[name='radio']").removeAttr("checked");
            $(this).parent().parent().parent().find("div[name='more']").css("display", "none");
            $(this).attr("checked", "checked");
            var more = $(this).parent().find("div[name='more']");
            if (!!more) {
                more.css("display", "");
            }
        },
        checkboxAction: function(){
            var table = $(this).parent().parent().parent().parent();
            var max = table.attr("maxSelect");
            var more = $(this).parent().find("div[name='more']");
            if (!this.checked) {
                $(this).removeAttr("checked");
                if (!!more) {
                    more.css("display", "none");
                }
                return true;
            }
            var checkbox = table.find("input[name='checkbox']");
            var num = 0;
            for (var i = 0; i < checkbox.length; i++) {
                if (checkbox[i].checked) {
                    num++;
                }
            }
            if (num <= max || max == 0) {
                $(this).attr("checked", "checked");
                if (!!more) {
                    more.css("display", "");
                }
            }
            else {
                $(this).removeAttr("checked");
            }
        }
    };
    view.init();
});
