seajs.use(["jQuery"],function($){
	var data = {
			"33001":{
				label:"在校学生",
				children:[
					{id:"38010",name:"初中生"},
					{id:"38011",name:"高中生"},
					{id:"38012",name:"大专生"},
					{id:"38013",name:"本科生"},
					{id:"38014",name:"硕士生"},
					{id:"38015",name:"博士生"}
				]
			},
			"33002":{
				label:"教师",
				children:[
					{id:"38016",name:"幼儿教育"},
					{id:"38017",name:"小学教师"},
					{id:"38018",name:"中学教师"},
					{id:"38019",name:"大学教师/教授"},
					{id:"38020",name:"教务管理人员"},
					{id:"38021",name:"培训教练/顾问"}
				]
			},
			"33003":{
				label:"上班族",
				children:[
					{id:"38022",name:"IT从业者"},
					{id:"38023",name:"广告设计"},
					{id:"38024",name:"美术设计"},
					{id:"38025",name:"影视制作"},
					{id:"38026",name:"公务员"},
					{id:"38027",name:"咨询/顾问"},
					{id:"38028",name:"市场/公关"},
					{id:"38029",name:"编辑/记者"},
					{id:"38030",name:"翻译"},
					{id:"38031",name:"科研人员"},
					{id:"38032",name:"金融从业者"},
					{id:"38033",name:"保险"},
					{id:"38034",name:"财务"},
					{id:"38035",name:"销售"},
					{id:"38036",name:"医生"},
					{id:"38037",name:"律师"},
					{id:"38038",name:"人力资源"},
					{id:"38039",name:"建筑师"}
				]
			},
			"33004":{
				label:"自由职业者",
				children:[
					{id:"38040",name:"作家"},
					{id:"38041",name:"摄影师"},
					{id:"38042",name:"离退休"},
					{id:"38043",name:"个体工商业"},
					{id:"38044",name:"农林渔牧"},
					{id:"38045",name:"求职中"}
				]
			}
		},
		identity={
			init:function(){
				this.labels=$("input[name='careerType']");
				this.select=$("select[name='career']");
				this.bind();
				this.reload(data[this.labels.filter(":checked").val()]);
			},
			bind:function(){
				this.labels.click(function(){
					identity.labelClick(this);
				});
			},
			labelClick:function(el){
				if(el.checked){
					this.reload(data[el.value]);
				}
			},
			reload:function(obj){
				if(obj){
					this.select.html("");
					var options=this.select[0].options;
					$.each(obj.children,function(){
						if(identity.select.attr("currentValue")==this.id){
							options.add(new Option(this.name,this.id,true,true));	
						}else{
							options.add(new Option(this.name,this.id));
						}
					});
				}
			}
		};
	identity.init();
});