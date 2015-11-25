(function(){
	var template={
		selectOption:"<div class='select_option' ><input type='text' name='label'/><button type='button' class='insert' title='添加选项'>&nbsp;</button><button type='button' class='remove' title='删除选项'>&nbsp;</button><input type='checkbox' name='allowInput'/><label>可填空</label><span class='text_limit' style='display:none;' >至少<input type='text' name='leastInput' size='2' maxlength='2'/>字，最多<input type='text' name='mostInput' size='2' maxlength='2'/>字</span></div>"
	},
	view={
		init:function(){
			this.separatorBox = $(".separator_box");
			if (!!this.separatorBox) {
				this.separatorBox.dialog({
					title:'分隔符管理',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:250,
					maxHeight:400,
					height: 250
				});
				this.separatorBox.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.textBox = $(".text_box");
			if (!!this.textBox) {
				this.textBox.dialog({
					title:'文本项管理',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:350,
					maxHeight:400,
					height: 250
				});
				this.textBox.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}

			this.selectBox = $(".select_box");
			if (!!this.selectBox) {
				this.selectBox.dialog({
					title:'选择项管理',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:600,
					maxHeight:800,
					height: 400
				});
				this.selectBox.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.surveyEditBox = $(".survey_edit_box");
			if (!!this.surveyEditBox) {
				this.surveyEditBox.dialog({
					title:'调查表标题编辑',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:600,
					maxHeight:800,
					height: 400
				});
				this.surveyEditBox.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.releaseBox = $(".release_box");
			if (!!this.releaseBox) {
				this.releaseBox.dialog({
					title:'调查表发布',
					autoOpen:false,
					bgiframe:false,
					modal:true,
					width:600,
					maxHeight:800,
					height: 500
				});
				this.releaseBox.bind("dialogclose",function(event, ui){
					view.closeAction(event, ui);
				});
			}
			this.addSelectRadio = $("button[id='add_select_radio']");
			this.addSelectCheckbox = $("button[id='add_select_checkbox']");
			this.addTextInput = $("button[id='add_text_input']");
			this.addTextTextarea = $("button[id='add_text_textarea']");
			this.addSeparator = $("button[id='add_separator']");
			this.separatorManage = $("button[name='separator_button']");
			this.separatorTitle = $("input[name='separator_title']");
			this.textManage = $("button[name='text_button']");
			this.textTitle = $("input[name='text_title']");
			this.minInput = $("input[name='min_input']");
			this.maxInput = $("input[name='max_input']");
			this.selectManage = $("button[name='select_button']");
			this.editBtn = $("button[name='edit']");
			this.removeBtn = $("button[name='remove']");
			this.moveupBtn = $("button[name='moveup']");
			this.movedownBtn = $("button[name='movedown']");
			this.surveyEdit = $("button[name='survey_edit']");
			this.releaseBtn = $("button[name='release']");
			this.releaseManage = $("button[name='release_button']");
			this.bind();
		},
		bind:function(){
			this.addSeparator.click(view.separatorShow);
			this.separatorManage.click(view.separatorManageAction);
			this.addTextInput.click(view.textInputShow);
			this.addTextTextarea.click(view.textTextareaShow);
			this.textManage.click(view.textManageAction);
			this.addSelectRadio.click(view.selectRadioShow);
			this.addSelectCheckbox.click(view.selectCheckboxShow);
			this.selectBox.find(".insert").click(view.addSelectOption);
			this.selectBox.find(".remove").click(view.removeSelectOption);
			this.selectManage.click(view.selectManageAction);
			this.removeBtn.click(view.removeItemAction);
			this.editBtn.click(view.editItemAction);
			this.moveupBtn.click(view.moveupAction);
			this.movedownBtn.click(view.movedownAction);
			this.surveyEdit.click(view.surveyEditShow);
			this.releaseBtn.click(view.releaseShow);
			this.releaseManage.click(view.releaseManageAction);
		},
		releaseManageAction:function(){
			var release =view.releaseBox.find("textarea[name='release']").val();
			var surveyId = view.releaseBox.attr("survey");
			var releaseUrl = '/survey/release?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : releaseUrl,
				data: "release="+release+"&surveyId="+surveyId,
				error : function() {//请求失败处理函数
					alert('出现错误，请联系技术人员');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});	
		},
		releaseShow:function(){
			view.releaseBox.dialog("open");
		},
		surveyEditShow:function(){
			view.surveyEditBox.dialog("open");
		},
		moveupAction:function(){
			var moveupId =$(this).attr("moveup");
			var moveupUrl = '/survey/moveup?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : moveupUrl,
				data: "moveupId="+moveupId,
				error : function() {//请求失败处理函数
					alert('出现错误，请联系技术人员');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});	
		},
		movedownAction:function(){
			var movedownId =$(this).attr("movedown");
			var movedownUrl = '/survey/movedown?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : movedownUrl,
				data: "movedownId="+movedownId,
				error : function() {//请求失败处理函数
					alert('出现错误，请联系技术人员');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});	
		},
		editItemAction:function(){
			var id = $(this).attr("edit");
			var liElement = $(this).parent().parent();
			if(liElement.hasClass("separator")){
				view.separatorTitle.attr("separator",id);
				var item = liElement.find("h2[class='title']");
				view.separatorBox.find("select[name='type']").val("1");
				if(!item.html()){
					item = liElement.find("h3[class='title']");
					view.separatorBox.find("select[name='type']").val("2");
				}
				view.separatorTitle.val(item.html());
				view.separatorShow(id);
			}else if(liElement.hasClass("text")){
				var item = liElement.find("input[class='input_text']");
				view.textTitle.val(liElement.find("span[class='subject']").html());
				var type = liElement.find("input[name='content_type']").val();
				if(type=="T"){
					item = liElement.find("textarea");
					view.minInput.val(item.attr("minlength"));
					view.maxInput.val(item.attr("maxlength"));
					view.textTextareaShow(id);
					return true;
				}else if(type=="I"){
					view.minInput.val(item.attr("minlength"));
					view.maxInput.val(item.attr("maxlength"));
					view.textInputShow(id);
					return true;
				}
			}else if(liElement.hasClass("select")){
				view.selectBox.find("input[name='select_title']").attr("selectId",id);
				view.selectBox.find("input[name='select_title']").val(liElement.find("span[class='subject']").html());
				var type = liElement.find("input[name='content_type']").val();
				view.selectBox.find("input[name='min_select']").val(liElement.find("span[class='subject']").attr("minselect"));
				view.selectBox.find("input[name='max_select']").val(liElement.find("span[class='subject']").attr("maxselect"));
				view.selectBox.find("select[name='direction']").val(liElement.find("input[name='content_direction']").val());
				var labels = liElement.find("label[name='label']");
				var labs = view.selectBox.find("input[name='label']");
				for(var i=0;i<2 && labels.length>=2;i++){
					$(labs[i]).val($(labels[i]).html());
					if($(labels[i]).attr("type")==1){
						$(labs[i]).parent().find("input[name='allowInput']").attr("checked","checked");
					}
				}
				var aheadSelectOption;
				for(var i=2;i<labels.length;i++){
					var selectOption = $(template.selectOption);
					selectOption.find(".insert").click(view.addSelectOption);
					selectOption.find(".remove").click(view.removeSelectOption);
					if(i==2){
						$(labs[1]).parent().after(selectOption);
					}else{
						aheadSelectOption.after(selectOption);
					}
					selectOption.find("input[name='label']").val($(labels[i]).html());
					if($(labels[i]).attr("type")==1){
						selectOption.find("input[name='allowInput']").attr("checked","checked");
					}
					aheadSelectOption = selectOption;
				}
				if(type=="R"){
					view.selectRadioShow(id);
				}else if(type=="C"){
					view.selectCheckboxShow(id);
				}
			}
		},
		removeItemAction:function(){
			if(!window.confirm("确认要删除该项吗？")){
				return false;
			}
			var removeId =$(this).attr("remove");
			var removeUrl = '/survey/remove?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : removeUrl,
				data: "removeId="+removeId,
				error : function() {//请求失败处理函数
					alert('出现错误，请联系技术人员');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});		
		},
		selectManageAction:function(){
			var title = view.selectBox.find("input[name='select_title']").val();
			var type = view.selectBox.find("input[name='select_type']").val();
			var direction = view.selectBox.find("select[name='direction']").val();
			var minSelect = view.selectBox.find("input[name='min_select']").val();
			var maxSelect = view.selectBox.find("input[name='max_select']").val();
			var selectId = view.selectBox.find("input[name='select_title']").attr("selectId");
			if(selectId == null || ""==selectId || selectId == "null"){
				selectId = 0;
			}
			var surveyId = view.selectBox.attr("survey");
			if(!view.checkEL(title)){
				alert("标题不能为空");
				return false;
			}
			$("input[name='label']").each(function(){
				if(!view.checkEL(this.value)){
					alert("标签项不能为空");
					return false;
				}
			});
			var params=[];
			params.push("title="+title);
			params.push("type="+type);
			params.push("direction="+direction);
			if($(".limit").css("display")!="none"){
				params.push("minSelect="+minSelect);
				params.push("maxSelect="+maxSelect);
			}
			params.push("selectId="+selectId);
			params.push("surveyId="+surveyId);
			$("input[name='label']").each(function(){
				params.push(this.name+"="+this.value);
			});
			if ($(".text_limit").css("display") != "none") {
				$("input[name='leastInput']").each(function(){
					params.push(this.name+"="+this.value);
				});
				$("input[name='mostInput']").each(function(){
					params.push(this.name+"="+this.value);
				});
			}
			$("input[name='allowInput']").each(function(){
					if(this.checked){
						params.push(this.name+"="+1);
					}else{
						params.push(this.name+"="+0);
					}
			});
			var data = params.join("&");
			var selectManageUrl = '/survey/selectManage?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : selectManageUrl,
				data: data,
				error : function() {//请求失败处理函数
					alert('出现错误，请联系技术人员');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});		
		},
		addSelectOption:function(){
			var selectOption = $(template.selectOption);
			selectOption.find(".insert").click(view.addSelectOption);
			selectOption.find(".remove").click(view.removeSelectOption);
			$(this).parent().after(selectOption);
		},
		removeSelectOption:function(){
			if($(this).parent().parent().children().size() == 2){
				return false;
			}
			$(this).parent().remove();
		},
		textManageAction:function(){
			var title = view.textTitle.val();
			var minInput = view.minInput.val();
			var maxInput = view.maxInput.val();
			var textId = view.textTitle.attr("textId");
			if(textId == null || ""==textId || textId == "null"){
				textId = 0;
			}
			if(minInput == null || ""==minInput || minInput == "null"){
				minInput = 0;
			}
			if(maxInput == null || ""==maxInput || maxInput == "null"){
				maxInput = 100;
			}
			var surveyId = view.textBox.attr("survey");
			var textType = $("input[name='text_type']").val();
			if(!view.checkEL(title)){
				alert("标题不能为空");
				return false;
			}
			var textManageUrl = '/survey/textManage?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : textManageUrl,
				data: "title="+title+"&minInput="+minInput+"&surveyId="+surveyId+"&maxInput="+maxInput+"&textId="+textId+"&type="+textType,
				error : function() {//请求失败处理函数
					alert('添加失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});			
		},
		separatorManageAction:function(){
			var title = view.separatorTitle.val();
			var type = view.separatorBox.find("select[name='type']").val();
			var separatorId = view.separatorTitle.attr("separator");
			if(separatorId == null || ""==separatorId || separatorId == "null"){
				separatorId = 0;
			}
			var surveyId = view.separatorBox.attr("survey");
			if(!view.checkEL(title)){
				alert("分隔符不能为空");
				return false;
			}
			var separatorManageUrl = '/survey/separatorManage?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : separatorManageUrl,
				data: "title="+title+"&separatorId="+separatorId+"&surveyId="+surveyId+"&type="+type,
				error : function() {//请求失败处理函数
					alert('添加分隔符失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});
		},
		checkEL:function(val){
			if ($.trim(val).length == 0) {
				return false;
			};
			return true;
		},
		closeAction:function(event, ui){
			window.location.reload();
			//view.separatorBox.dialog("close");
		},
		separatorShow:function(id){
			if(id>0){
				view.separatorTitle.attr("separator",id);
			}else{
				view.separatorTitle.attr("separator","");
			}
			view.separatorBox.dialog("open");
		},
		textInputShow:function(id){
			$("input[name='text_type']").val("I");
			if(id>0){
				view.textTitle.attr("textId",id);
			}else{
				view.textTitle.attr("textId","");
			}
			view.textBox.dialog("open");
		},
		textTextareaShow:function(id){
			$("input[name='text_type']").val("T");
			if(id>0){
				view.textTitle.attr("textId",id);
			}else{
				view.textTitle.attr("textId","");
			}
			view.textBox.dialog("open");
		},
		selectRadioShow:function(id){
			$("input[name='select_type']").val("R");
			$(".limit").css("display","none");
			if(id>0){
				view.selectBox.find("input[name='select_title']").attr("selectId",id);
			}else{
				view.selectBox.find("input[name='select_title']").attr("selectId","");
			}
			view.selectBox.dialog("open");
		},
		selectCheckboxShow:function(id){
			$("input[name='select_type']").val("C");
			$(".limit").css("display","");
			if(id>0){
				view.selectBox.find("input[name='select_title']").attr("selectId",id);
			}else{
				view.selectBox.find("input[name='select_title']").attr("selectId","");
			}
			view.selectBox.dialog("open");
		}
	};
	view.init();
})();
seajs.use(["jQuery","wysiwyg"],function($,wysiwyg){

	var view = {
		init:function(){
			var sub=$.sub();
			wysiwyg($);
			this.textarea = $('#wysiwyg');
			this.textarea.wysiwyg({
			    controls: {
			        strikeThrough: { visible: true },
			        underline: { visible: true },
			        subscript: { visible: true },
			        superscript: { visible: true }
			    }
			});
			var description = $("input[name='description']").val();
			this.textarea.wysiwyg("setContent", description);
			this.surveyBtn = $("button[name='survey_button']");
			this.bind();
		},
		bind:function(){
			this.surveyBtn.click(view.surveyEditAction);
		},
		checkEL:function(val){
			if ($.trim(val).length == 0) {
				return false;
			};
			return true;
		},
		surveyEditAction:function(){
			var title = $("input[name='survey_title']").val();
			var description = view.textarea.val();
			var surveyId = $("input[name='surveyId']").val();
			if(!view.checkEL(title)){
				alert("标题不能为空");
				return false;
			}
			var surveyEditUrl = '/survey/surveyEdit?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : surveyEditUrl,
				data: "title="+title+"&description="+description+"&surveyId="+surveyId,
				error : function() {//请求失败处理函数
					alert('编辑失败');
				},
				success : function(data) { //请求成功后回调函数。  
					if(data.result==1){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}
			});
		}
	};
	view.init();
});
