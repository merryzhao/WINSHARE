KindEditor.ready(function(K) {
	var needInitEditorDom = $("textarea[id^='editor_textArea_']");
	for(var i = 0 ; i < needInitEditorDom.length;i++){
		K.create("#"+$(needInitEditorDom[i]).attr("id"),{
			uploadJson : '/editor/file',
			themeType : 'simple',
			newlineTag:'br',
			items:[
			        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
			        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
			        'table', 'hr','map','pagebreak',
			        'link', 'unlink',
			]
		});
	}
});


function syncEditor(domPrefix){
	var editorArray = KindEditor.instances
	for(var i = 0 ; i < editorArray.length;i++){
		editorArray[i].sync();
	}
}
