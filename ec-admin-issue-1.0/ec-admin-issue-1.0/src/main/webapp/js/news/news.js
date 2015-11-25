seajs.use(["jQuery", "wysiwyg", "config"], function($, wysiwyg, config){
    wysiwyg($);
    var text = $("div.text");
    var loaddiv = text.find("div.loaddiv");
    var textarea = text.find("textarea");
    var title = text.find("input[name='title']");
    var id = text.find("input[name='id']");
    var button = text.find("div.button button");
    var close = text.find("a[class='close']");
    var listtable = $(".list-table");
    var tbody = listtable.find("tbody");
    var isinit = false;
    var speed = 300;
    
    var initevent = function(){
		$("a[class='del']").unbind("click");
        $("a[class='del']").click(function(){
            if (confirm("确定删除?")) {
                delNews(this);
            }
            return false;
        });
        
		$("a[class='edit']").unbind("click");
        $("a[class='edit']").click(function(){
            id.val(getId(this));
            title.val(getTitle(this));
            $(".text").fadeIn(speed, function(){
            });
            init();
            textarea.wysiwyg("setContent", getContent(this));
        });
    }
    
    
    $("a[class='add']").click(function(){
        closeDiv();
        $(".text").fadeIn(speed, function(){
            init()
        });
    });
    
    close.click(function(){
        $(".text").fadeOut(speed, function(){
            closeDiv();
        });
    });
    
    
    button.click(function(){
        editNews(this);
    });
    
    function delNews(tag){
        var postUrl = config.consoleServer + "/news/del/" + getId(tag) + "?format=json";
        var tr = $(tag).parents("tr");
        $.get(postUrl, function(d){
            if (d.result > 0) {
                tr.remove();
            }
        });
    };
    
    function editNews(tag){
        var postUrl = config.consoleServer + "/fragment/news?format=json";
        param = {
            id: id.val(),
            title: title.val(),
            content: textarea.val()
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            beforeSend: beforeSend(),
            url: postUrl,
            data: param,
            success: function(d){
                loaddiv.hide();
                if (d.result > 0) {
                    $(".text").fadeOut(speed, reload(d.news));
                }
            }
        });
        
    };
    
    function beforeSend(){
        loaddiv.show();
        
    }
    function closeDiv(){
        id.val(0);
        title.val("");
        textarea.wysiwyg("setContent", "");
    }
    
    function reload(news){
        var tr = tbody.find("tr");
	    var q=0;
		$.each(tr,function(){
			var td = $(this).find("td");
			if(news.id == td.eq(0).html()){
				q++;
				tr = $(this);
				return;
			}
		})
		if(q>0){
			editDom(news,tr);
		}else{
			createDom(news);
		}
	}
	
	function createDom(news){
		 var tr = $("<tr>").appendTo(tbody);
        $("<td>").html(news.id).appendTo(tr);
        $("<td>").html(news.title).appendTo(tr);
        $("<td>").html("<a href=\"javascript:;\" class=\"edit\" >编辑内容</a>||<a href=\"javascript:;\" class=\"del\">删除</a>").appendTo(tr);
        $("<td>").html("<input type=\"hidden\" class=\"newsinfo\" value='" + news.content + "'/>").appendTo(tr);
        initevent();
	}
	
	function editDom(news,tr){
		var td = tr.find("td");
		td.eq(0).html(news.id);
		td.eq(1).html(news.title);
		td.eq(3).html("<input type=\"hidden\" class=\"newsinfo\" value='" + news.content + "'/>");
	}
    
    
    //id
    function getId(tag){
        var tr = $(tag).parents("tr");
        var td = tr.find("td");
        var id = td.eq(0).html();
        return id;
    }
    
    //标题
    function getTitle(tag){
        var tr = $(tag).parents("tr");
        var td = tr.find("td");
        var id = td.eq(1).html();
        return id;
    }
    
    //内容
    function getContent(tag){
        var tr = $(tag).parents("tr");
        var td = tr.find("td");
        var newsinfo = tr.find("input").val();
        return newsinfo;
    }
    
    function init(){
        if (!isinit) {
            textarea.wysiwyg({
                controls: {
                    html: {
                        visible: true
                    },
                    insertImage: {
                        visible: false
                    },
                    insertTable: {
                        visible: false
                    },
                    code: {
                        visible: false
                    },
                    h1: {
                        visible: false
                    },
                    h2: {
                        visible: false
                    },
                    h3: {
                        visible: false
                    }
                }
            });
            isinit = true;
            
            textarea.wysiwyg("setContent", "");
        }
    }
    initevent();
});

