$(function () {
    $("input:submit,input:button,input:file,button,.jButton").button();

    $(".active").click(function () {
        var appkey = $(this).attr("appkey");
        var hostname = $(this).attr("hostname")
        
		if(!window.confirm("主机"+hostname+"上名为"+appkey+"的应用将被启用！")){
			return false;
		} 

        $.ajax({
            type: "POST",
            async: false,
            url: "/heartbeat/app/modify",
            data: {
                _method: "PUT",
                appkey: appkey,
                hostname: hostname
            },
            success: function (mav) {
                if (mav.result == 1) {
                    result = true;
                    
                } 
            }
        });
        window.location.reload();
    });

    $(".block").click(function () {
        var appkey = $(this).attr("appkey");
        var hostname = $(this).attr("hostname")
        
        if(!window.confirm("主机"+hostname+"上名为"+appkey+"的应用将被禁用！")){
        	return false;
        }
        
        $.ajax({
            type: "POST",
            async: false,
            url: "/heartbeat/app/modify",
            data: {
                _method: "PUT",
                appkey: appkey,
                hostname: hostname
            },
            success: function (mav) {
                if (mav.result == 1) {
                    result = true;
                } 
            }
        });
        window.location.reload();
    });
});