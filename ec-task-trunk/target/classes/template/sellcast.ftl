<?xml version="1.0" encoding="UTF-8"?>
<root>
	 <version>${indexModel.version}</version>
     <modified>${indexModel.nowTime}</modified>
     <seller_id>${indexModel.sellId}</seller_id>
     <seller_cats>
      <cat>
         <scid>${book.id?c}</scid>
		  <name>${book.name}</name>
         <cats>
          <#list book.children as children>
            <#if children.available>
            <cat>
            <scid>${children.id?c}</scid>
		     <name>${children.name}</name>
		     </cat>
		     </#if>  
          </#list>
          </cats>          
          <scid>${media.id?c}</scid>
		  <name>${media.name}</name>
         <cats>
          <#list media.children as mediachildren>
	           <#if mediachildren.available>
	           <cat>
	            <scid>${mediachildren.id?c}</scid>
			     <name>${mediachildren.name}</name>
			     </cat>
			    </#if>   
          </#list>
          </cats>
      </cat>
     </seller_cats>
</root>