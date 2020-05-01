$(document).ready(function(){
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	
	$(document).ajaxSend(function(e,xhr, options){
		xhr.setRequestHeader(header,token);
	})
})
