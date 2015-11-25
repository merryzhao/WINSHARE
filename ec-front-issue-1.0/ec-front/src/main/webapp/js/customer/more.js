seajs.use(["jQuery","jQueryUI"],function($,uiPlugin){
	var $=$.sub();
	var defaultInterest = "不同项目之间请用空格隔开，例如“旅行 阅读 瑜伽”";
	var defaultFavorite = "不同人名之间请用空格隔开，例如“金庸 周杰伦 姚明”";
	uiPlugin($);
	var customerDetail={
		defOpt:{
			action:"http://www.winxuan.com/customer/detail"
		},
		init:function(){
			this.basicInfo=$("#basicInfo");
			this.living=$(".living");
			this.livingStatus=$("#livingStatus");
			this.moreForm=$("#moreForm");
			this.careerType=$("#careerType");
			this.career=$("#career");
			this.salary=$("#salary");
			this.interest=$("#interest");
			this.favorite=$("#favorite");
			this.bind();
		},
		bind:function(){
			var obj=this;
			this.basicInfo.click(function (){
				customerDetail.basicClick();
			});
			this.living.click(function(){
				customerDetail.livingClick();
			});
			this.moreForm.submit(function(e){
				if(!obj.validate()){
					// cancel form
					e.preventDefault();
					e.stopPropagation();
					return false;
				}
			});
			$(document).ready(function(){
				customerDetail.changeInterest();
				customerDetail.changeFavorite();
			});
			this.interest.click(function(){
				customerDetail.changeInterestClick();
			});
			this.favorite.click(function(){
				customerDetail.changeFavoriteClick();
			});
			this.interest.blur(function(){
				customerDetail.changeInterest();
			});
			this.favorite.blur(function(){
				customerDetail.changeFavorite();
			});
		},
		showMessage:function(msg){
			alert(msg);
		},
		validate:function(){
			if(this.livingStatus.val() == null || this.livingStatus.val() == ""){
				this.showMessage("请选择居住状态!");
				return false;
			}
			if(this.careerType.val() == null || this.careerType.val() == ""){
				this.showMessage("请选择身份!");
				return false;
			}
			if(this.career.val() == null || this.career.val() == ""){
				this.showMessage("请选择身份!");
				return false;
			}
			if(this.salary.val() == null || this.salary.val() == "" || this.salary.val() == "-1"){
				this.showMessage("请选择月收入!");
				return false;
			}
			if(this.interest.val() == defaultInterest){
				this.interest.val("");
			}
			if(this.favorite.val() == defaultFavorite){
				this.favorite.val("");
			}
			return true;
		},
		basicClick:function(){
			try{
				window.open(this.defOpt.action,"_self");
			}catch(e){
				throw new Error(e.message);
			}
		},
		livingClick:function(){
			try{
				var t = "";
				this.living.each(function(){
					if($(this).attr("checked")){
						t += $(this).val()+"@";
					}
				});
				this.livingStatus.val(t);
			}catch(e){
				throw new Error(e.message);
			}
		},
		changeInterest:function(){
			if(this.interest.val() == ""){
				this.interest.attr("class", "textarea_gray");
				this.interest.val(defaultInterest);
			}
		},
		changeFavorite:function(){
			if(this.favorite.val() == ""){
				this.favorite.attr("class", "textarea_gray");
				this.favorite.val(defaultFavorite);
			}
		},
		changeInterestClick:function(){
			if(this.interest.val() == defaultInterest){
				this.interest.val("");
				this.interest.removeAttr("class");
			}
		},
		changeFavoriteClick:function(){
			if(this.favorite.val() == defaultFavorite){
				this.favorite.val("");
				this.favorite.removeAttr("class");
			}
		}
	};
	customerDetail.init();
});
