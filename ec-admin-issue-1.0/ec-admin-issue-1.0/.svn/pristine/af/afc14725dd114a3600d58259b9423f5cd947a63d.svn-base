$(document).ready(function(){
	$('.taskBox').delegate('.taskBoxLinks','mousemove',function(e){
		var $mouse = e.pageX - $(this).offset().left;
		var span = Math.round($mouse/2.25)*1;
		$(this).find('h4').stop().animate({width:span+'%'},5);
		if(span<101&&span>=0){
			$(this).next('span').text(span+'%'); 
		}
	}).delegate('.taskBoxLinks','mouseleave',function(){  
		$(this).find('h4').stop().animate({width:'5%'},5);  
		var $mousex = $(this).find('h3').width();  
		var spanx = Math.round($mousex/2.25)*1;
		if(spanx <101&&spanx>=0){
			$(this).next('span').text(spanx+'%'); 
		}
	}).delegate('.taskBoxLinks','click',function(e){  
		var $mouse = e.pageX - $(this).offset().left;
		var span = Math.round($mouse/2.25)*1; 
		if(span<101&&span>=0){
			$(this).find('h3').stop().animate({width:span+'%'},100);  
		}
	});
});