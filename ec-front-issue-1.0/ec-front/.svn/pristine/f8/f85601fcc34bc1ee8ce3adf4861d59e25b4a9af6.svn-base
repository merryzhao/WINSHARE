seajs.use(["jQuery"],function($){
 var view  = {
 	flag:false,
 	init:function(){
		this.btnsubmit = $("input.submit");
		this.txtname = $("input[name='name']");
		this.txtphone = $("input[name='phone']");
		this.txtcompanyname = $("input[name='companyName']");
		this.txtcontent = $("textarea[name='content']");
		this.txtsRand = $("input[name='sRand']");
		this.imgsafeCode = $("img#safeCode");
		this.bind();
	},
	bind:function(){
		var obj = this;
		this.btnsubmit.click(function(){obj.request()});
		this.txtcontent.click(function(){obj.setcontent()});
	},
	setcontent:function(){
		this.txtcontent.val("");
		this.txtcontent.css("color","#000000");
		this.flag = true;
	},
	request:function(){
		var obj = this;
		var para = {
			name:obj.txtname.val(),
			phone:obj.txtphone.val(),
			companyName:obj.txtcompanyname.val(),
			content:obj.txtcontent.val(),
			sRand:obj.txtsRand.val()
		};
	
		if(!this.validate(para)){
			return false;
		}
		$.ajax({
			type: "POST",
			dataType:"json",
			beforeSend:function(){obj.changeRandCode();},
			url:"/groupinfo/save?format=json",
			data:para,
			success:function(d){
				obj.reload(d);
			}
		});
	},
	reload:function(d){
		alert(d.message);
		this.flag = false;
		if(d.result>0){
			this.clear();
		}
	},
	changeRandCode:function(){
			var newsrc = this.imgsafeCode.attr("src");
			this.imgsafeCode.attr("src",newsrc+"?d="+Math.random());
		
	},
	clear:function(){
		   this.txtname.val("");
			this.txtphone.val("");
			this.txtcompanyname.val("");
			this.txtcontent.val("");
			this.txtsRand.val("");
		
	},
	validate:function(data){
		if(data.name==""){
			alert("请输入姓名");
			return false;
		};
		 if(isNaN(+data.phone)){
		 	alert("请正确输入联系电话,区号不加'-'线");
                return false;
            };
		if(data.name.length>14){
			alert("请将名字长度限制在14位");
			return false;
		}
		if(data.phone==""){
			alert("请输入电话号码");
			return false;
		};
		if(data.phone.length>14){
			alert("请输入正确的电话号码");
			return false;
		};
		if(data.companyName==""){
			alert("请输入公司名字");
			return false;
		};
		if(data.companyName.length>20){
			alert("请将公司名称长度限制在20位");
			return false;
		};
		if(data.content==""||!this.flag){
			alert("填写咨询内容");
			return false;
		};
		if(data.content.length>800){
			alert("对不起，您输入的内容过多(PS:小于800)");
			return false;
		};
		return true;
	}
 }
 view.init();
	
});
