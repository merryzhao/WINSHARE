jQuery(function($){
	initArea(23,180,5708,5717);
	
	var forms=$("form"),
		currentIndex=0,
		steps=["consignee","deliver","pay"],
		formBinds=["addressId","deliveryId"];
		checkout={};
	
	function showFormInfo(obj,stepEl){
		var arr=[];
		for(var name in obj){
			if(!!obj[name])
			arr.push(obj[name]);
		}
		stepEl.find(".form-info p").html(arr.join(","));
	};
	
	function showErrorMessage(){
		// TODO
	};
	
	function getStepName(index){
		return steps[index];
	};
	
	function getConsigneeContent(){
		return {
			conignee:$("input[name='consignee']").val(),
			country:$("select[areaLevel='country'] option:selected").html(),
			province:$("select[areaLevel='province'] option:selected").html(),
			city:$("select[areaLevel='city'] option:selected").html(),
			district:$("select[areaLevel='district'] option:selected").html(),
			address:$("input[name='address']").val(),
			zipCode:$("input[name='zipCode']").val(),
			mobile:$("input[name='mobile']").val(),
			phone:$("input[name='phone']").val()
		}
	};
	
	function getDeliverContent(){
		return {};
	};
	
	function getPayContent(){
		return {};
	};
	
	function getFormContent(index){
		var obj={};
		switch(index) {
			case 0:
				obj=getConsigneeContent();
				break;
			case 1:
				obj=getDeliverContent();
				break;
			case 2:
				obj=getPayContent();
			default: 
				break;
		}
		
		return obj;
	};
	
	function nextStep(){
		var currentStep=$("div[step='"+getStepName(currentIndex)+"']");
			showFormInfo(getFormContent(currentIndex),currentStep);
			currentStep.removeClass("edit").addClass("view");
			$("div[step='"+getStepName(currentIndex+1)+"']").removeClass("step view").addClass("edit");
	};
	
	function validate(a){
		// TODO
		return true;
	};
	
	function saveForm(jForm){
		var data=jForm.serialize();
		if(validate(jForm)){
			jForm.find(":input").attr("disabled","disabled");
			jForm.find("button").parent().toggleClass("processing");
			$.ajax({
				"type":jForm.attr("method"),
				"data":data,
				"url":jForm.attr("action")+"?format=json",
				"success":function(data){
					
					jForm.find(":input").removeAttr("disabled","disabled");
					jForm.find("button").parent().toggleClass("processing");
					
					if(data.result=="1"){
						if(!!formBinds[currentIndex]){
							checkout[formBinds[currentIndex]]=data.JSON.id;
						}
						
					}else{
						showErrorMessage(data.message);
					}
				},
				"error":function(x,s,e){
					throw e;
				},
				dataType:"JSON"
			});
		}
	};
	
	function init(){
			forms.each(function(){
				var form=$(this);
				form.validate({
					submitHandler:function(form){
						$(form).submit(function(){return false;});
						saveForm($(form));
						return false;
					}
				});
			});
	};
	init();
	
});