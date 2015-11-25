$(document).ready(function(){
	var template={
		input:function(){
			return "<input style='margin-bottom:0px;padding:0px;' type='text' class='content' />";
		},
		option:function(){
			return "<a href='javascript:;' class='del' >删除</a>||<a href='javascript:;' class='update' >修改</a>"
		}
		
	}
	var view={
		init:function(){
			this.td = $("td");
			this.aupdate=$("a.update");
			this.newdata =$("tr.newdata");
			this.asubmit = $("a.submit");
			this.aadd=$("a.add");
			this.adel=$("a.del");
			this.findproshop = $("a.findproshop");
			this.proshopinfo = $("div.proshopinfo");
			this.bind();
		},
		
		bind:function(){
			var obj = this;
			obj.td.dblclick(function(){obj.change(this)});
			obj.aupdate.click(function(){obj.saveorupdate(this)});
			obj.aadd.click(function(){obj.add(this)});
			obj.adel.click(function(){obj.del(this)});
			obj.proshopinfo.dblclick(function(){obj.closeinfo(this)});
			obj.findproshop.click(function(){obj.proshop(this)});
		},
		closeinfo:function(tag){
		
			var obj = $(tag);
			obj.css("display","none");
		},
		proshop:function(tag){
		  var id = this.id(tag);
		  var obj =this;
		 $.get("/proshop/get/"+id+"?format=json",function(d){
		 	var categories = d.proshop.categories;
		 	if(categories.length>0){
				var html = "";
				$(categories).each(function(){
					var category = this;
					for(var n in category){
						html+=n+":"+category[n]+",";
					}
					html+="<br/><br/>";
				});
				obj.proshopinfo.html(html);				
			}else{
				obj.proshopinfo.html("暂无专业店");
			}
		 	obj.proshopinfo.show();
		 	  
			
		    })
		},
		
		change:function(tag){
			var obj = $(tag);
			 var flag = obj.attr("edit");
			if(flag!="true"){
				return false;
			}
		    var html = $(tag).html().replace(/\s/g,"");
		     input = template.input();
		      obj.html(template.input());	  
		    var content = $("input.content");
		    content.val(html);
		    content.focus();
		    obj.attr("edit",false);
		
		    content.blur(function(){
		  	obj.html(content.val().replace(/\s/g,""));
			obj.attr("edit",true);
		  })
		   
		},
		
		del:function(tag){			
			if(window.confirm("确定删除?")){
			var tr = $(tag).parents("tr");	
			var id = tr.find("td").eq(0).html();
			  if(id==0){
			  	 tr.remove();
			  }else{
			  	$.post("/proshop/delete/"+id+"/?format=json",function(d){
					if(d.result>0){
						tr.remove();
					}
				})
			  }
			}
			
			
		},
		add:function(tag){
		 
		   var obj =this;
		   this.newdata.show();
		   obj.asubmit.unbind("click");
		   obj.asubmit.click(function(){obj.saveorupdate(this)})

			
		},		
		saveorupdate:function(tag){
			var obj =$(tag);
			var el =this;
			var tr = obj.parents("tr");
			var para = this.tableser(tr);
			$.post("/proshop/saveorupdate?format=json",para,function(d){
                        	var td = tr.find("td")
						 if(d.result>0){
							el.proshopinfo.html(d.message);
							td.eq(0).html(d.proshop.id);
						 }else{
						 	el.proshopinfo.html(d.message);
							td.eq(3).html(0);
						 }
						 el.proshopinfo.show();
			});
			
		},
		tableser:function(tag){
			var obj = $(tag).find("[name]");
			var para = "";
			for(var i=0;i<obj.length;i++){
				var dom =$(obj[i]);				
				var name =dom.attr("name");
				if(name!=undefined){
					if(dom.find("input").length>0){
					  var	input = dom.find("input");
					  var b = input.attr("checked")=="checked";
					     para+=name+"="+b;
					}else{
						para+=name+"="+$.trim(dom.html());
					}
					if(i!=obj.length-1){
						 para+="&";	
					}
				 }
				
			}
			return para.replace(/\s/g,"");
			
		},
		id:function(tag){
			var tr = $(tag).parents("tr");	
			var id = tr.find("td").eq(0).html();
			return id;
		},
		
		
	}
	
	view.init();
	
})
