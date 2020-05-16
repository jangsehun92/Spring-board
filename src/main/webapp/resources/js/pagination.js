$(function(){
	var category = $("#category").val();
	var totalPage = $("#totalPage").val();
	var startPage = $("#startPage").val(); 
	var endPage = $("#endPage").val(); 
	var page = $("#page").val();
	var sort = $("#sort").val();
	var query = $("#query").val();
	
	if(query==""){
		if(startPage > 1){
			$("#pagination").append("<li class=''><a href='/articles/"+category+ "' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
		}
		
		if(page > 1){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+(page-1)+"&sort="+sort+"' aria-label='Previous'><span aria-hidden='true'>&lang;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&lang;</span></a></li>");
		}
		
		for(var iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == page) {
		       $("#pagination").append("<li class='active'><a href='#'>"+iCount+"<span class='sr-only'></span></a></li>");
		    } else {
		    	$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+iCount+"&sort="+sort+"'>" + iCount + "<span class='sr-only'></span></a></li>");
		    }
		}
		
		if(page < totalPage){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+(Number(page)+1)+"&sort="+sort+"' aria-label='Next'><span aria-hidden='true'>&rang;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&rang;</span></a></li>");
		}
		
		if(endPage < totalPage){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+totalPage+"&sort="+sort+"' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
		}
	} else{
		if(startPage > 1){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?query="+query+"&sort="+sort+"' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
		}
		
		if(page > 1){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+(page-1)+"&query="+query+"&sort="+sort+"' aria-label='Previous'><span aria-hidden='true'>&lang;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&lang;</span></a></li>");
		}
		
		for(var iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == page) {
		       $("#pagination").append("<li class='active'><a href='#'>"+iCount+"<span class='sr-only'></span></a></li>");
		    } else {
		    	$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+iCount+"&query="+query+"&sort="+sort+"'>" + iCount + "<span class='sr-only'></span></a></li>");
		    }
		}
		
		if(page < totalPage){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+(Number(page)+1)+"&query="+query+"&sort="+sort+"' aria-label='Next'><span aria-hidden='true'>&rang;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&rang;</span></a></li>");
		}
		
		if(endPage < totalPage){
			$("#pagination").append("<li class=''><a href='/articles/"+category+"?page="+totalPage+"&query="+query+"&sort="+sort+"' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
		}else{
			$("#pagination").append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
		}
	}
})