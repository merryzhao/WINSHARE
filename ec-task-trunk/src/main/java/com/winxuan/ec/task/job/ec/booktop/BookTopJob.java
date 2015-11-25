package com.winxuan.ec.task.job.ec.booktop;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.booktop.BookTopCategory;
import com.winxuan.ec.task.service.booktop.BookTopService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 
 * @author sunflower
 * 
 */
@Component("bookTopJob")
public class BookTopJob implements TaskAware, Serializable {

	private static final Log LOG = LogFactory.getLog(BookTopJob.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int FIRST_CATEGORY_NUM = 20;// 最少一级分类数
	private static final int FIRST_CATEGORY_NEW_NUM = 10;// 新书最少一级分类数
	private static final int FIRST_CATEGORY_SALE_NUM = 100;// 一级分类最小动销商品数

	@Autowired
	private BookTopService booktopService;

	@Override
	public String getName() {

		return "bookTopJob";
	}

	@Override
	public String getDescription() {
		return "图书榜单";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	}

	@Override
	public void start() {

		// 删除原有榜单数据
		booktopService.delete();
		// 新增图书顶级分类
		booktopService.addBookCategory(BookTopCategory.TOP_TYPE_SALE);
		// 新增一级分类数据：畅销
		booktopService.addFirstCategory(FIRST_CATEGORY_NUM,
				FIRST_CATEGORY_SALE_NUM);
		// 新增二级分类数据：畅销
		booktopService.addSecondCategory();
		// 新增商品（一周）：畅销
		booktopService.addAWeekProductSale();
		// 新增商品（一个月）：畅销
		booktopService.addAMonthProductSale();
		// 新增商品（三个月）：畅销
		booktopService.addThreeMonthProductSale();
		// 新增商品（头一年）：畅销
		booktopService.addLastYearProductSale();

		// 新增图书顶级分类
		booktopService.addBookCategory(BookTopCategory.TOP_TYPE_NEW);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -365);
		Date date = calendar.getTime();
		// 新增一级分类数据：新书
		booktopService.addNewFirstCategory(FIRST_CATEGORY_NEW_NUM,
				FIRST_CATEGORY_SALE_NUM, date);
		// 新增二級分類數據:新書
		booktopService.addNewSecondCategory(date);
		// 新增商品(一周):新書
		booktopService.addAWeekProductNew(date);
		// 新增商品(一月):新书
		booktopService.addAMonthProductNew(date);
		// 新增商品(三月):新书
		booktopService.addThreeMonthProductNew(date);
		// 新增商品(头一年):新书
		booktopService.addLastYearProductNew(date);

		// 预先请求,缓存数据
		List<BookTopCategory> btcs = booktopService.getAllCategorys();
		if (btcs != null && btcs.size() > 0) {
			for (BookTopCategory bookTopCategory : btcs) {
				for(int time=1;time<=4;time++){
					String url = bookTopCategory.getUrl();
					url = url.substring(0,url.length()-3);
					url = url + String.valueOf(time);
/*					for(int page=1;page<=1;page++){
						String http = url + "/"+String.valueOf(page);
						getResponse(http);
					}*/
					getResponse(url+"/1");
				}
			}
		}
	}

	public String getResponse(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				5000);
		String responseBody = null;
		try {
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			LOG.info("executing request " + httpget.getURI());
			responseBody = httpClient.execute(httpget, responseHandler);
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}
}
