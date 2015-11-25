

function addTrigger(name,description){
	$("#dialog-modal").dialog("destory");
	$("#dialog-modal" ).dialog({
		height: 300,
		title:description,
		width:600,
		modal: true,
		buttons: {
			'添 加': function() {
				submitAddTrigger();
				
			}
		}
	});
	$('input[name="taskName"]').val(name);
}

function submitAddTrigger(){
	var selected = $("#tabs-trigger").tabs().tabs('option', 'selected');
	$('input[name="type"]').val(selected+1);

	var param = {
			taskName: $('input[name="taskName"]').val(),
			type: $('input[name="type"]').val(),
			dateTimeType:$('select[name="dateTimeType"]').val(),
			datetime:$('input[name="datetime"]').val(),
			appointType:$('select[name="appointType"]').val(),
			appointStr:$('input[name="appointStr"]').val(),
			cron:$('input[name="cron"]').val(),
	};

	$.ajax({
		cache : false,
		type : 'POST',
		data:param,
		url : "/task/addTrigger?format=json",
		error : function() {			//请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { //请求成功后回调函数。 
			if(data.result==1){
				alert('任务添加成功!');
//				refreshTab('/task');
				selectTab(2,'/trigger');
				$( "#dialog-modal" ).dialog("close");
			}else{
				alert(data.message);
			}
		}
	});
}

function pauseTrigger(triggerName){
	sendTriggerAjax(triggerName,"/trigger/pause","任务暂停成功!");
}

function resumeTrigger(triggerName){
	sendTriggerAjax(triggerName,"/trigger/resume","任务恢复成功!");
}

function clearTrigger(triggerName){
	sendTriggerAjax(triggerName,"/trigger/del","任务清除成功!");
}

function refreshTrigger(){
	var $tabs = $("#tabs-index").tabs();
	var selected = $tabs.tabs('option', 'selected');
	$tabs.tabs( 'url', selected,'/trigger');
	$tabs.tabs('load', selected);
}

function refreshLog(){
	var $tabs = $("#tabs-index").tabs();
	var selected = $tabs.tabs('option', 'selected');
	$tabs.tabs( 'url', selected,'/log/error');
	$tabs.tabs('load', selected);
}

function refreshRun(){
	var $tabs = $("#tabs-index").tabs();
	var selected = $tabs.tabs('option', 'selected');
	$tabs.tabs( 'url', selected,'/trigger/run');
	$tabs.tabs('load', selected);
}

function sendTriggerAjax(triggerName,url,successStr){
	var param = {
			triggerName:triggerName,
			format:"json"
	};
	$.ajax({
		cache : false,
		type : 'GET',
		data:param,
		url :url,
		error : function() {			//请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { //请求成功后回调函数。 
			if(data.result==1){
				refreshTab('/trigger');
				alert(successStr);
			}else{
				alert(data.message);
			}
		}
	});
}

function refreshTab(url){
	var $tabs = $("#tabs-index").tabs();
	var selected = $tabs.tabs('option', 'selected');
	$tabs.tabs( 'url', selected,url);
	$tabs.tabs('load', selected);
}

function selectTab(index,url){
	var $tabs = $("#tabs-index").tabs();
	$tabs.tabs( 'url', index,url);
	$tabs.tabs('select', index);
}










