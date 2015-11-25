package com.winxuan.ec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.service.activity.ActivityShowService;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.bill.BillService;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.category.McCategoryService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.channel.RuleOnshelfService;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.customer.CustomerQuestionService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.documents.DocumentsService;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.order.OrderCancelAppService;
import com.winxuan.ec.service.order.OrderDcService;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.service.order.OrderLogisticsInfoFetcher;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.order.UnionOrderService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.presentcard.PresentCardOrderService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.product.ProductRecommendationService;
import com.winxuan.ec.service.product.ProductSaleDisableRecordService;
import com.winxuan.ec.service.product.ProductSaleRankService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.service.replenishment.MrCycleService;
import com.winxuan.ec.service.replenishment.MrDeliveryRecordService;
import com.winxuan.ec.service.replenishment.MrMcTypeService;
import com.winxuan.ec.service.replenishment.MrProductFreezeService;
import com.winxuan.ec.service.replenishment.MrProductItemService;
import com.winxuan.ec.service.replenishment.MrSupplyService;
import com.winxuan.ec.service.report.ReportService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.service.roadmap.RoadmapService;
import com.winxuan.ec.service.seller.SellerService;
import com.winxuan.ec.service.shop.ShopCategoryService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.service.shop.ShopServiceNoService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.service.society.KeenessService;
import com.winxuan.ec.service.union.UnionCommissionLogService;
import com.winxuan.ec.service.union.UnionCommissionService;
import com.winxuan.ec.service.union.UnionService;
import com.winxuan.ec.service.util.ImageService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;


/**
 * 服务类
 * @author Heyadong
 *
 */
/**
 * @author Administrator
 *
 */
@Component
public class Services {
	@Autowired public ActivityShowService activityShowService;
	
	@Autowired public AreaService areaService;
	
	@Autowired public CategoryService categoryService;
	
	@Autowired public McCategoryService mcCategoryService;
	
	@Autowired public ChannelService channelService;
	
	@Autowired public RuleOnshelfService ruleOnshelfService;
	
	@Autowired public CmService cmService;
	
	@Autowired public CodeService codeService;
	
	@Autowired public CustomerCommentService customerCommentService;
	
	@Autowired public CustomerQuestionService customerQuestionService;
	
	@Autowired public CustomerService customerService;
	
	@Autowired public DeliveryService deliveryService;
	
	@Autowired public AttachService attachService;
	
	@Autowired public EmployeeService employeeService;
	
	@Autowired public MailService mailService;
	
	@Autowired public BatchPayService batchPayService;
	
	@Autowired public OrderCancelAppService orderCancelAppService;
	
	@Autowired public OrderInvoiceService orderInvoiceService;
	
	@Autowired public OrderLogisticsInfoFetcher orderLogisticsInfoFetcher;
	
	@Autowired public OrderService orderService;
	
	@Autowired public UnionOrderService unionOrderService;
	
	@Autowired public PaymentService paymentService;
	
	@Autowired public PresentService presentService;
	
	@Autowired public PresentCardOrderService presentCardOrderService;
	
	@Autowired public PresentCardService presentCardService;
	
//	@Autowired public ChannelProductService channelProductService;
	
	@Autowired public ProductRecommendationService productRecommendationService;
	
	@Autowired public ProductSaleRankService productSaleRankService;
	
	@Autowired public ProductService productService;
	
	@Autowired public ImageService imageService;
	
	@Autowired public PromotionService promotionService;
	
	@Autowired public ReportService reportService;
	
	@Autowired public ReturnOrderService returnOrderService;
	
	@Autowired public RoadmapService roadmapService;
	
	@Autowired public SellerService sellerService;
	
	@Autowired public ShopCategoryService shopCategoryService;
	
	@Autowired public ShopService shopService;
	
	@Autowired public ShopServiceNoService shopServiceNoService;
	
	@Autowired public ShoppingcartService shoppingcartService;
	
	@Autowired public KeenessService keenessService;
	
	@Autowired public UnionCommissionLogService unionCommissionLogService;
	
	@Autowired public UnionCommissionService unionCommissionService;
	
	@Autowired public UnionService unionService;
	
	@Autowired public VerifyCodeService verifyCodeService;
	
	@Autowired public MrCycleService mrCycleService;
	
	@Autowired public MrDeliveryRecordService mrDeliveryRecordService;
	
	@Autowired public MrMcTypeService mrMcTypeService;
	
	@Autowired public MrProductItemService mrProductItemService;
	
	@Autowired public MrProductFreezeService mrProductFreezeService;
	
	@Autowired public MrSupplyService mrSupplyService;
	
	@Autowired public ProductSaleStockService productSaleStockService;
	
	@Autowired public OrderDcService orderDcService;
	
	@Autowired public BillService billService;
	
	@Autowired public ProductSaleDisableRecordService productSaleDisableRecordService;
	
	@Autowired public DocumentsService documentsService;
}
