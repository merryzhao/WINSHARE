<?xml version="1.0" encoding="UTF-8"?>

<item>
   <seller_id>${indexModel.sellId}</seller_id>
   <outer_id>${outerid}</outer_id>
   <title>${ps.sellName?html}</title>
   <product_id>${ps.psid?c}</product_id>
   <brand>文轩网，新华文轩，新华书�?/brand>
   <tags>${ps.sellName?html}</tags>
   <type>fixed</type>
   <price>${ps.salePrice?string("0.00")}</price>
   <available>1</available>
   <desc>${ps.productIntro?xml}</desc>
  <image>${ps.imageUrl}</image>
  <scids>
	  ${ps.categoryId}
  </scids>
  <post_fee>0.00</post_fee>
  <href>${ps.productUrl}</href>
</item>