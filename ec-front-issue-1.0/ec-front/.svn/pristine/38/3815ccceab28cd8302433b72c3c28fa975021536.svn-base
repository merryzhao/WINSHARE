seajs.use(["jQuery","jQueryUI","areaSelector","portrait","ui"],function($,uiPlugin,area,portrait,ui){
	var $=$.sub();
	uiPlugin($);
	function DateSelector(ctx){
		if(!!ctx){
			this.year=ctx.find("select[bind='date\\:year']");
			this.month=ctx.find("select[bind='date\\:month']");
			this.day=ctx.find("select[bind='date\\:day']");
		}else{
			throw new Error("Can't found the context");
		}
		this.init();
	};
	DateSelector.prototype={
		init:function(){
			var date=new Date(),
				obj=this,
				year=date.getFullYear();
				
			this.loadYear(year);
			this.loadMonth();
			this.year.change(function(){obj.getDays();});
			this.month.change(function(){obj.getDays();});
			this.getDays();
			this.loadDefault();
		},
		loadDefault:function(){
			if((!!birthday)&&birthday.year){
				this.year.val(birthday.year);
				this.month.val(birthday.month);
				this.day.val(birthday.day);
			}else{
				this.year.val("1980");
			}
		},
		loadYear:function(year){
			for(var i=year;i>=year-50;i--){
				this.year[0].options.add(new Option(i,i));	
			}
		},
		loadMonth:function(){
			for(var i=1;i<=12;i++){
				this.month[0].options.add(new Option(i,i));
			}
		},
		getDays:function(){
			var y=this.year.val(),
				m=this.month.val();
			this.day.html("");
			if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12){
				this.render(31);
			}else if(m==4 || m==6 || m==9 || m==11){
				this.render(30);
			}else if(m==2){
				var flag=true;
				flag=y%4==0&&y%100!=0||y%400==0;
				if(flag){
					this.render(29);
				}else{
					this.render(28);
				}
			}
		},
		render:function(days){
			for(var i=1;i<=days;i++){
				this.day[0].options.add(new Option(i,i));
			}
		}
	};
	var customer={
		defOpt:{
			action:"http://www.winxuan.com/customer/detail",
			moreAction:"http://www.winxuan.com/customer/more"
		},
		init:function(){
			this.form=$("#detailForm");
			this.birthday=$("#birthday");
			this.moreInfo=$("#moreInfo");
			this.basicInfo=$("#basicInfo");
			this.basic_but=$("#basic_but");
			this.email=$("#email");
			this.mobile=$("#mobile");
			this.year=this.form.find("select[bind='date\\:year']");
			this.month=this.form.find("select[bind='date\\:month']");
			this.day=this.form.find("select[bind='date\\:day']");
			this.loadDefaultArea();
			this.bind();
		},
		bind:function(){
			var obj=this;
			this.form.submit(function(e){
				if(!obj.validate()){
					// cancel form
					e.preventDefault();
					e.stopPropagation();
					return false;
				}
			});
			this.birthday.datepicker({regional:"zh-CN"});
			this.moreInfo.click(function (){customer.moreClick();});
			this.basicInfo.click(function (){customer.basicClick();});
			this.form.find("div.edit_photo a").click(function(){customer.showPortrait();});
			this.form.find("a[bind='emailValidate']").click(function(){customer.emailValidate();});
			this.form.submit(function (){try {
				customer.saveClick();
			}catch(e){alert(e.message);}});
			this.initDate();
		},
		loadDefaultArea:function(){
			var defData=null;
			try{
				defData={
					country:parseInt(currentArea.country),
					province:parseInt(currentArea.province),
					city:parseInt(currentArea.city)
				};
			}catch(e){}
			if(!!defData&&(!isNaN(defData.country))&&(!isNaN(defData.province))&&(!isNaN(defData.city))){
					area.init(defData.country,defData.province,defData.city);
			}
			else{
				area.init(23,175,1507);
			}
		},
		initDate:function(){
			new DateSelector(this.form);
		},
		showMessage:function(msg){
			alert(msg);
		},
		validate:function(){
			if ($.trim(this.email.val()) == "" || ($.trim(this.email.val()) != "" && !/.+@.+\.[a-zA-Z]{2,4}$/.test(this.email.val()))) {
				alert("请输入正确的E-Mail地址!");
				return false;
			}
			if($.trim(this.mobile.val()) != ""){
				if (!/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(this.mobile.val())) {
					alert("请输入正确的手机号码!");
					return false;
				}
			}
			if(email.email != this.email.val()){
				if(customer.emailIsExist()){
					return false;
				}
			}
			return true;
		},
		moreClick:function(value){
			try{
				window.open(this.defOpt.moreAction,"_self");
			}catch(e){
				throw new Error(e.message);
			}
		},
		saveClick:function(){
			try{
				this.birthday.val(this.year.val()+"-"+this.month.val()+"-"+this.day.val());
			}catch(e){
				throw new Error(e.message);
			}
		},
		showPortrait:function(){
			var obj=this;
			this.portrait=portrait({
				action:"http://www.winxuan.com/customer/saveAvatar?format=jsonp&callback=?",
				dock:this.form.find("div.edit_photo"),
				callback:function(data){
					if(data.result=="1"){
						obj.form.find("div.edit_photo img").attr("src",obj.portrait.value);
						obj.portrait.close();
					}else{
						alert("修改头像失败!");
					}
					
				}
			});
		},
		emailValidate:function(){
			var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if(!emailReg.test(this.email.val())){
				alert("请正确输入邮箱地址");
				return false;
			}
			if(email.email != this.email.val()){
				if(customer.emailIsExist()){
					return false;
				}
			}
			$.ajax({
				url:"http://www.winxuan.com/customer/email?format=jsonp&callback=?",
				success:function(data){
					if(data.result=="1"){
						ui.confirm({
							title:"<span style='font-weight:bolder'>验证成功</span>",
							width:"330",
							message:"已经向"+$("#email").val()+"发送了一封验证邮件！<br/>请及时查看邮件，点击里面的“激活链接”。",
							buttons:{
								ok:"确认"
							},
							isOverlay:true
						});
					}else{
						ui.confirm({
							message:"验证失败",
							buttons:{
								ok:"确认"
							}
						});
					}
				},
				error:function(xhr,status){
					throw new Error(status);
				},
				dataType:"jsonp"
			});
		},
		emailIsExist:function(){
			var flag = false;
			var obj=this;
			$.ajax({
				url:"http://www.winxuan.com/customer/emailIsExist?format=jsonp&email="+this.email.val()+"&callback=?",
				async:false,
				success:function(data){
					if(data.result=="1"){
						flag = true;
						ui.confirm({
							message:"邮箱已注册",
							buttons:{
								ok:"确认"
							}
						});
					}
				},
				error:function(xhr,status){
					throw new Error(status);
				},
				dataType:"jsonp"
			});
			return flag;
		},
		basicClick:function(value){
			try{
				window.open(this.defOpt.action,"_self");
			}catch(e){
				throw new Error(e.message);
			}
		}
	};
	customer.init();
});
