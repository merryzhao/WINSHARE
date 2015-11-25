$(document).ready(function(){
    window.manufacturer = {
       add : function(){
           var name = window.prompt("输入供应商名字");
           if (name != null) {
               $.ajax({
                  type : "POST",
                  url : "/manufacturer/save",
                  async : false,
                  data : {"format":"json", "name":name},
                  success : function(msg){
                      if(msg.result != 1) {
                          alert(msg.message);
                      }
                  } 
               });
               window.location.reload();
           }
       },
       edit : function(id,old) {
           var name = window.prompt("修改名称",old);
           if (name != null && name !=old && name != "") {
               $.ajax({
                   type: "POST",
                   async: false,
                   url: "/manufacturer/updateName",
                   data: {"format":"json", "id":id, "name":name},
                   success : function(msg){
                      if (msg.result != 1) {
                          alert(msg.message);
                      }
                   }
               });
               window.location.reload();
           }
           
           
       },
       append : function(id) {
           var name = window.prompt("输入子出版社");
           if (name != null && name != "") {
               $.ajax({
                   type: "POST",
                   async: false,
                   url: "/manufacturer/append",
                   data: {"format":"json", "id":id, "name":name},
                   success : function(msg){
                      if (msg.result != 1) {
                          alert(msg.message);
                      }
                   }
               });
               window.location.reload();
           }
           
       }
       
    }
    
});