seajs.use(["jQuery","areaSelector","config"],function($,area,config){
    var form=$("#invoiceForm"),       
	    invoice = {
	        container : '<div class="invoice_container" id="invoice"><div class="widgets-title">提示 <a href="javascript:;" bind="close">X</a></div><div class="widgets-content"></div></div>',
	        view:{
	            success:'<p class="success"><b>发票信息提交成功！</b></p><p>我们会在提交发票后三个工作日内给你通过平邮方式补寄，请注意查收。</p>',
	            fail:'<p class="error"><b>发票信息提交失败！</b></p><p>请重新填写你的发票信息</p>',
	            repeat:'<p class="error"><b>请勿重复创建发票！</b></p><p>请查看你的订单信息或咨询客服</p>'	            
	        },
	        info:{
	            mobileReg:/^1[1-9]{1}[0-9]{9}$/,
	            phoneReg:/^((\d{3,4})|(\d{3,4}\-))?((\d){7,8})+((\d{2,4})|(\-\d{2,4}))?$/,
	            phoneMsg:"格式:区号-固话号码-分机号或者输入11位手机号码",
	            zipCodeReg:/^[1-9]{1}[0-9]{5}$/,
	            zipCodeMsg:"邮编由6位数字组成",
	            addressReg:/^[\s\S]{4,200}$/,
	            addressMsg:"街道地址由4-200字符组成",
	            consigneeReg:/^[\s\S]{2,30}$/,
	            consigneeMsg:"收货人姓名,由2-30个字符组成,如：张三"	            
	        },
			init:function(){
			    var container=invoice.container;                    
                    $(container).appendTo(document.body);
                    this.initArea();
                    this.bind();          										   
    			   },
    	    bind:function(){
    	        var invoice=this,el=$("#invoice");
    	        form.find("a.view").click(function(){
                     form.find(".view_pop").toggle();
                });
                form.find("span.close").click(function () {
                     form.find('.view_pop').hide();
                });
                form.find("input#personal").attr("checked","checked");
                form.find("input#personal").click(function() {
                    form.find("input[name='title']").hide();                    
                    $(this).closest("p").find("span[name='info']").html("");
                });
                form.find("input#company").click(function() {
                    form.find("input[name='title']").show();
                });
                form.find("input[name='title']").click(function() {
                    $(this).val("");
                });
                form.find(".btn1").click(function() {
                    invoice.load();
                }),                  
    	        el.find("a[bind='close']").click(function(){
                    el.hide();
                    el.find("div.widgets-content").html("");
                       
               });
    	    },
    	    initArea:function(){
    	        var ids = form.find('#area').val().split(",");
                area.init(ids[0],ids[1],ids[2],ids[3],ids[4]);    	        
    	    },
    	    load:function(){
    	        var para = form.serialize(),view=this.view;
                   if(invoice.validate()){
                       $.ajax({
                        url:config.portalServer+"/customer/order/save?format=json",
                        type:"POST",
                        dataType:"json",
                        data:para,
                        success:function(data){                         
                          if(data.result==1){
                           invoice.showMessage(view.success);
                           $(this).attr("disabled","true");
                          }
                          if(data.result==0){
                            invoice.showMessage(view.fail);
                          }
                          if(data.result==3){                             
                             invoice.showMessage(view.repeat);
                          }                                                                                                                          
                        }
                    })
                    }
    	    },    
    		showMessage: function(msg){
    			var c =form.find("button[name='submit']");
                var offset = c.offset(),el=$("#invoice");
                el.find("div.widgets-content").html(msg);
                el.css({
                    "top": offset.top - c.height() - el.height(),
                    "left": offset.left - el.width()/2 + c.width(),
                    "display": "block"
                });
            },			
			validate:function(){
                var ret = true;                        
                var country=form.find("select[arealevel='country']"),
                    province=form.find("select[arealevel='province']"),
                    city=form.find("select[arealevel='city']"),
                    district=form.find("select[arealevel='district']"),
                    town=form.find("select[arealevel='town']"),
                    address=form.find("input[name='address']"), 
                    phone=form.find("input[name='mobile']"),
                    zipCode=form.find("input[name='zipCode']"),
                    consignee=form.find("input[name='consignee']"),
                    title=form.find("input[name='title']"),
                    info=invoice.info;
                    if(form.find("#company").attr("checked")&&title.val().replace(/\s/g,"").length==0) {
                        invoice.errorTip(title,"请填写单位名称");
                        ret = false;
                    }else{
                        invoice.errorTip(title,"");
                    }    
                    if(!invoice.errorMsg(phone, info.mobileReg, info.phoneMsg)&&!invoice.errorMsg(phone, info.phoneReg, info.phoneMsg)){
                                ret = false;                            
                    }             
                    if(!invoice.errorMsg(zipCode,info.zipCodeReg,info.zipCodeMsg)){
                            ret = false;
                        }                  
                    if((country.val() == 23 && (province.val() == -1  || city.val() == -1 || district.val() == -1 || town.val() == -1 ))){
                            invoice.errorTip(country,"请选择所在地区");
                            ret = false;
                    }
                    else {
                        var ele = country.closest("p").find("span[name='info']");
                        ele.html("");
                        ele.removeAttr("style");
                    }
                    if(!invoice.errorMsg(address,info.addressReg,info.addressMsg)){
                        ret = false;                            
                    }
                    if(!invoice.errorMsg(consignee,info.consigneeReg,info.consigneeMsg)){                        
                        ret = false;                              
                    }    
                    return ret;
                },
            errorTip:function(el,msg){
                var ele = el.closest("p").find("span[name='info']");
                    ele.html(msg);
                    ele.css("color","red");
                
            },
            errorMsg:function(el,reg,msg){
                var ele = el.closest("p").find("span[name='info']");
                if (!reg.test(el.val())) {
                    ele.html(msg);
                    ele.css("color","red");
                    return false;
                }
                else{
                    ele.html("");
                    ele.removeAttr("style");
                    return true;
                }
            }                             
	};
	invoice.init();
});