define(function(require){
    var $ = require("jQuery"),
		conf=require("config");
    var widget = "<div id='resume' bind='resume'><a href='http://static.winxuancdn.com/upload/jianli.doc' target='_blank'><img src='"+conf.imgServer+"/images/returntop/resume.png' alt='简历模板'/></a></div>"+
                 "<div id='survey' bind='survey'><a href='#' target='_blank'><img src='"+conf.imgServer+"/images/returntop/survey.jpg' alt='△回顶部'/></a></div>"+
    			 "<div id='backTop' bind='backTop'><img src='"+conf.imgServer+"/images/returntop/topout.jpg' alt='△回顶部'/></div><style>#backTop,#survey,#resume{width:auto;position:absolute;padding:4px;cursor:pointer;}</style>", 
    			el = $("#backTop"),survey=$("#survey"),resume=$("#resume");
    if (el.length == 0||survey.length == 0) {
        $(widget).appendTo(document.body);
        el = $("#backTop");
        survey=$("#survey");
        resume=$("#resume");
    }
    el.hide();
    survey.hide();
    resume.hide();
    var backTop = {
        lock: function(){
            var scrollTop = $(window).scrollTop();
            if (scrollTop < 400) {
                if(el.is(":visible")){
					el.hide();
				}
				return;
            }
            else {
                el.show();
            }
            el.css({
                top: backTop.top(),
                left: backTop.left()
            })
        },
        left: function(){
            var ww = $(window).width();
            if (ww < 990) {
                return 990 + 10;
            }
            return 990 + (ww - 990) / 2 + 10;
        },
        top: function(){
            var wh = $(window).height(), scrollTop = $(window).scrollTop();
            if (scrollTop <= 1) {
                return (wh - el.height() - 20);
            }
            return wh - el.height() + scrollTop - 20;
        },
        getUrl:function(){
        	var locate=window.location.href;
        	var url=conf.portalServer+"/survey/release?releaseUrl="+locate+"&format=jsonp&callback=?";
        	$.getJSON(url,function(data){
				if(data.survey){
					survey.show();
					survey.find("a").attr("href",data.survey);
				}
			});
        },
        init: function(){
        	backTop.getUrl();
        	var resume_model=$("#resume_model");
        	if(resume_model.length>0){
        	    resume.show();
        	}        	
            $("#backTop").mouseout(function(){
                $(this).find("img").attr("src", ""+conf.imgServer+"/images/returntop/topout.jpg");
            });
            $("#backTop").mouseover(function(){
                $(this).find("img").attr("src", ""+conf.imgServer+"/images/returntop/topin.jpg");
            });
            $("#survey").mouseout(function(){
                $(this).find("img").attr("src", ""+conf.imgServer+"/images/returntop/survey.jpg");
            });
            $("#survey").mouseover(function(){
                $(this).find("img").attr("src", ""+conf.imgServer+"/images/returntop/survey_hover.jpg");
            });
            var boxModel = $.support.boxModel;
            if ($.browser.msie && $.browser.version == "6.0") {
                boxModel = false;
            }
            else {
                boxModel = true;
            }
            if (boxModel) {
                el.css({
                    bottom: "20px",
                    left: backTop.left(),
                    position: "fixed"
                });
                survey.css({
                    bottom: "80px",
                    left: backTop.left(),
                    position: "fixed"
                });
                resume.css({
                    bottom: "75px",
                    left: backTop.left(),
                    position: "fixed"
                });
                setInterval(function(){
                    var scrollTop = $(window).scrollTop();
                    if (scrollTop < 400) {
                        el.hide();
                    }
                    else {
                        el.show();
                    }
                    el.css({
                        left: backTop.left()
                    })
                }, 100);
            }
            else {
                this.lock();
                setInterval(function(){
                    backTop.lock()
                }, 100);
            }
            el.click(function(){
                window.scrollTo(0, 0)
            });
        }
    }
	return backTop;
})

