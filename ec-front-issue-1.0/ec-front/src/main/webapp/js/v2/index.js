seajs.use(["jQuery", "newSlider", "switchable", "pagescroll"], function($, Slider,
	Switchable, pageScroll) {		var scroll = new pageScroll({
		context: ".head",
		fixedEl: ".top-nav-wrap",
		popClass: "search-fixed",
		relativeEl: ".head-content",
		fixedClass: "top-nav-fixed"
	});
	var index = new Slider({
		selector: {
			context: "div.mod-mainslider",
			cont: "div.J-tab-cont",
			trigger: "div.J-tab-trigger"
		},
		autotag: true,
		delay: 200,
		interval: 4000,
		isAnimate: true,
		vertical: "top"
	});
	var authorsrecommend = new Switchable({
		context: "div.mod-authorsrecommend",
		currentline: ".J-tab-currentline",
		currentClass: "current",
		animatetime: 200,
		animate: true
	});
	var specialrecommend = new Switchable({
		context: "div.mod-specialrecommend",
		currentline: ".J-tab-currentline",
		currentClass: "current",
		animatetime: 200,
		animate: true
	});
	var classifyrecommend = new Switchable({
		context: "div.mod-classifyrecommend",
		currentline: ".J-tab-currentline",
		currentClass: "current",
		animatetime: 300,
		animate: true
	});
	var brandpublication = new Switchable({
		context: "div.mod-brandpublication",
		currentline: ".J-tab-currentline",
		currentClass: "current",
		animatetime: 200,
		animate: true
	});
});