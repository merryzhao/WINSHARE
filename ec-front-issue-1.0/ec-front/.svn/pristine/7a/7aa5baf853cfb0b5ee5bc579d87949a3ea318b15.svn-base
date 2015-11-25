seajs.use(["jQuery"],function($){
	
	var el = $(".layout");
	var tab = el.find(".category li");
	var book = el.find("span[name='bookinput']");
	var music = el.find("span[name='musicinput']");
	var mall = el.find("span[name='mallinput']");
	var submit = el.find("input[name='search_submit']");
	var reset = el.find("input[name='search_reset']");
	var searchCodeEl = el.find("input[name='searchCode']");
	
	var sellNameEl = el.find("input[name='sellName']");
	var authorEl = el.find("input[name='author']");
	var publisherNameEl = el.find("input[name='publisherName']");
	var isbnEl = el.find("input[name='isbn']");
	var discountEl = el.find("select[name='discount']");
//	var listPriceMinEl = el.find("input[name='listPriceMin']");
//	var listPriceMaxEl = el.find("input[name='listPriceMax']");
	var sellPriceMinEl = el.find("input[name='sellPriceMin']");
	var sellPriceMaxEl = el.find("input[name='sellPriceMax']");
	var publishDateStartEl = el.find("input[name='publishDateStartDate']");
	var publishDateEndEl = el.find("input[name='publishDateEndDate']");
	var keywordMusicEl = el.find("input[name='keywordMusic']");
	var keywordMallEl = el.find("input[name='keywordMall']");
	
	
	clear();
	
	//tab
	tab.click(function(){
		var now = $(this);
		tab.attr("class","");
		now.addClass("cur_ca");
		
		if(now.html() == "图书"){
			searchCodeEl.val(1);
			music.hide();
			mall.hide();
			book.show();
		}
		else if(now.html() == "音像"){
			searchCodeEl.val(7786);
			book.hide();
			mall.hide();
			music.show();
		}
		else if(now.html() == "百货"){
			searchCodeEl.val(7787);
			book.hide();
			music.hide();
			mall.show();
		}
	});
	
	//reset
	reset.click(function(){
		clear();
	});
	
	//search
	submit.click(function(){
		var code = searchCodeEl.val();
		var url = "/search?";
		var param = "";
		
		if(code == 1){
			var sellName = $.trim(sellNameEl.val());
			var author = $.trim(authorEl.val());
			var publisherName = $.trim(publisherNameEl.val());
			var isbn = $.trim(isbnEl.val());
			var discount = parseInt(discountEl.val());
			var sellPriceMin = $.trim(sellPriceMinEl.val());
			var sellPriceMax = $.trim(sellPriceMaxEl.val());
			var publishDateStartYear = new Date(publishDateStartEl.val()).getFullYear();
			var publishDateStartMonth = new Date(publishDateStartEl.val()).getMonth()+1;
			var publishDateEndYear = new Date(publishDateEndEl.val()).getFullYear();
			var publishDateEndMonth = new Date(publishDateEndEl.val()).getMonth()+1;
			
			if (sellName) {
				if (sellName.length > 100) {
					alert("请输入100字以内的书名");
					return;
				}
				param += (param == '' ? '' : '&') + 'name=' + encodeURIComponent(sellName);
			};
	
			if(author){
				if (author.length > 50) {
					alert("请输入50字以内的作者名");
					return;
				}
				param += (param == '' ? '' : '&') + 'author=' + encodeURIComponent(author);
			}
			
			if(publisherName){
				if (publisherName.length > 100) {
					alert("请输入100字以内的出版社名");
					return;
				}
				param += (param == '' ? '' : '&') + 'manufacturer=' + encodeURIComponent(publisherName);
			}
			
			if(isbn){
				if (isbn.length > 100) {
					alert("请输入50字以内的isbn");
					return;
				}
				param += (param == '' ? '' : '&') + 'isbn=' + isbn;
			}
			
			if(discount){
				if (discount < 1 || discount > 5) {
					alert("请选择正确的折扣范围");
					return;
				}
				var mindiscount;
				var maxdiscount;
				switch(discount){
					case 1 : break;
					case 2 : mindiscount = 0.7;break;
					case 3 : mindiscount = 0.5 ; maxdiscount = 0.7 ;break;
					case 4 : mindiscount = 0.3 ; maxdiscount = 0.5 ;break;
					case 5 : maxdiscount = 0.3;break;
				}
				if(mindiscount){
					param += (param == '' ? '' : '&') + 'mindiscount=' + mindiscount;
				}
				if(maxdiscount){
					param += (param == '' ? '' : '&') + 'maxdiscount=' + maxdiscount;
				}
			}
			
			if(sellPriceMin){
				sellPriceMin = parseFloat(sellPriceMin);
				if(!sellPriceMin){
					alert('请输入正确的数字');
					sellPriceMinEl.focus();
					return;
				}
				param += (param == '' ? '' : '&') + "minprice=" + sellPriceMin;
			}
			
			if(sellPriceMax){
				sellPriceMax = parseFloat(sellPriceMax);
				if(!sellPriceMax){
					alert('请输入正确的数字');
					sellPriceMaxEl.focus();
					return;
				}
				param += (param == '' ? '' : '&') + "maxprice=" + sellPriceMax;
			}
			
			if(publishDateStartYear){
				param += (param == '' ? '' : '&') + "ybegin=" + publishDateStartYear;
				if(publishDateStartMonth){
					param += (param == '' ? '' : '&') + "mbegin=" + publishDateStartMonth;
				}
			}
			
			if(publishDateEndYear){
				param += (param == '' ? '' : '&') + "yend=" + publishDateEndYear;
				if(publishDateEndMonth){
					param += (param == '' ? '' : '&') + "mend=" + publishDateEndMonth;
				}
			}
		}
		else if(code == 7786){
			var keyword = $.trim(keywordMusicEl.val());
			if(!keyword){
				alert("请输入搜索关键字");
				return;
			}
			param += "keyword=" + encodeURIComponent(keyword);
		}
		else if(code == 7787){
			var keyword = $.trim(keywordMallEl.val());
			if(!keyword){
				alert("请输入搜索关键字");
				return;
			}
			param += "keyword=" + encodeURIComponent(keyword);
		}
		
		if(param != ''){
				url += param + "&code=" + code;
				window.location = url;
		}
		else{
			alert('请填写要搜索的内容');
		}
	});
	
	function clear(){
		sellNameEl.val('');
		authorEl.val('');
		publisherNameEl.val('');
		isbnEl.val('')
		discountEl.val(1);
		sellPriceMinEl.val('');
		sellPriceMaxEl.val('');
		publishDateStartEl.val('');
		publishDateEndEl.val('');
		keywordMusicEl.val('');
		keywordMallEl.val('');
		sellNameEl.focus();
	}
});