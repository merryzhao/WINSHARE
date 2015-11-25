/**
 * 
 */ 
$(function(){
    window.monitor = {
            kill : function(elm){
                $(elm).parent().remove();
            },
            appendMobile : function(mobile){
                var mobile = $("#mobile").val();
                if(!(/^\d{11}$/.test(mobile))) {
                    alert("电话号码为11位");
                    return;
                }
              
                var html = "<span>"  + mobile;
                var hideHtml = "<input name='mobiles' type='hidden' value='"+mobile+"' />";
                html += hideHtml;
                html += "<input type='button' value='x' onclick='monitor.kill(this);' /></span>";
                $("#div_mobile").append(html);
                
            },
            appendEmail : function(email) {
                var email = $("#email").val();
                if(!(/^[a-z_0-9]+@[a-z0-9]+\.+[0-9a-z]+$/i.test(email))){
                    alert("email格式不正确, (e.g: 123@qq.com)");
                    return;
                }
                
                var html = "<span>"  + email;
                var hideHtml = "<input name='emails' type='hidden' value='"+email+"' />";
                html += hideHtml;
                html += "<input type='button' value='x' onclick='monitor.kill(this);' /></span>";
                $("#div_email").append(html);
            },
            submit : function(){
                
                if ($("#name").val() == "") {
                    alert("输入监控名称");return false;
                }
                
                if (!/\d+/.test($("#stockLess").val())){
                    alert("输入库存,并且保证为正整数");return false;
                }
                if (!(/.+\.xls$/.test($("#file").val()))) {
                        alert("请添加上传文件,并保存版本为Excel.2003,   (.xls后缀)");
                        return false;
                }
                return true;
            },
            updateStatus : function(id, status){
            
                $.ajax({
                    asycn : true,
                    type : "POST",
                    url : "/monitor/status.json",
                    data : {"taskId":id, "status":status},
                    success : function(){
                        window.location.reload(true);
                    }
                });
                
                
            }
    };
    
});