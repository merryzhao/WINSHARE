<?xml version="1.0" encoding="UTF-8"?>
<root>
     <version>${indexModel.version}</version>
     <modified>${indexModel.nowTime}</modified>
     <seller_id>${indexModel.sellId}</seller_id>
     <cat_url>${indexModel.catUrl}</cat_url>
     <dir>${indexModel.dir}/</dir>
     <item_ids>
     <#list indexModel.outerList as outer >
		<outer_id action="${outer.action}">${outer.index}</outer_id>
	 </#list>
	</item_ids>
</root>