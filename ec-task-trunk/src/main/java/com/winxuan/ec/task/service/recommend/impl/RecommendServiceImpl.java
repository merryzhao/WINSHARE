package com.winxuan.ec.task.service.recommend.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.ec.task.dao.recommend.RecommendDao;
import com.winxuan.ec.task.service.recommend.RecommendService;
import com.winxuan.ec.task.support.utils.ThreadLocalMode;
/**
 * 
 * @author Administrator
 *
 */
@Service("recommendService")
public class RecommendServiceImpl implements RecommendService , Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9091820306074861784L;
	private static Log log = LogFactory.getLog(RecommendServiceImpl.class);
	private static int bufferSize;
	private static String baseDatePath;
	private static String resultPath;
	private static int recommendCount;
	
	private static final String TAB = "\t";

	private ItemBasedRecommender recommender;

	@Autowired
	private RecommendDao recommendDao;


	public void setRecommendDao(RecommendDao recommendDao) {
		this.recommendDao = recommendDao;
	}

	static {
		Properties properties;
		try {
			properties = PropertiesLoaderUtils
					.loadAllProperties("recommend.properties");
			baseDatePath = properties.getProperty("BASE_DATA_PATH");
			resultPath = properties.getProperty("RESULT_PATH");
			recommendCount = Integer.valueOf((String) properties
					.getProperty("RECOMMEND_COUNT"));
			bufferSize = Integer.valueOf((String) properties
					.getProperty("BUFFER_SIZE"));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}
	
	private void initRecommender() throws IOException{
		DataModel dataModel = new FileDataModel(new File(baseDatePath));
		ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(
				dataModel);
		recommender = new GenericItemBasedRecommender(dataModel,
				itemSimilarity);
	}

	@Override
	public List<RecommendedItem> findRecommendListByCommodityId(long commodityId,short mode) {
		return recommendDao.findRecommendListByCommodityId(commodityId,mode);
	}

	@Override
	public List<RecommendedItem> getMostSimilarItems(long itemId) throws IOException, TasteException {
		List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
		recommendedItems = recommender.mostSimilarItems(itemId,recommendCount);
		return recommendedItems;
	}
	
	@Override
	public List<RecommendedItem> getItemsByUserId(long userId) throws IOException, TasteException{
		List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
		recommender.recommend(userId,recommendCount);
		return recommendedItems;
	}

	@Override
	public void initBaseData() {
		recommendDao.saveBaseDataByDate();
	}

	private BufferedWriter getBufferedWriter(String path) throws IOException {
		File file = new File(path);
		file.createNewFile();
		return new BufferedWriter(new FileWriter(file));
	}

	@Override
	public void saveBaseDataToFile() throws IOException {
		BufferedWriter bufferedWriter = getBufferedWriter(baseDatePath);
		SqlRowSet rowSet;
		int lastid=0;
		boolean finish;
		do {
			rowSet = recommendDao.findBaseData(lastid, bufferSize);
			finish = true;
			while (rowSet.next()) {
				finish = false;
				bufferedWriter.write(rowSet.getString(2) + TAB
						+ rowSet.getString(3) + "\r\n");
				lastid=rowSet.getInt(1);
			}
		} while (rowSet != null && !finish);
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	@Override
	public void saveResult() {
		if(ThreadLocalMode.get() == RecommendMode.SEARCH){
			recommendDao.saveResultByFile(resultPath);
			recommendDao.updateRecommendid();
			recommendDao.updateRecommendation();
			recommendDao.emptyResult();
			recommendDao.updateMode();
			
		}
		else{
			recommendDao.emptyResult();
			recommendDao.saveResultByFile(resultPath);
		}
	}

	@Override
	public void doCalculate() throws IOException, TasteException {
		log.info("doCalculate init start ---");
		initRecommender();
		log.info("doCalculate init end ---");
		int noItemIdCount = 0;
		
		short mode = ThreadLocalMode.getMode();
		if(ThreadLocalMode.get() == RecommendMode.SEARCH){
			mode = RecommendMode.TEMP_MODE;//搜索需要写入临时mode
		}
		BufferedWriter bufferedWriter = getBufferedWriter(resultPath);
		int start = 0;
		List<Long> commodityIds = new ArrayList<Long>();
		do {
			commodityIds = recommendDao.findNeedCalculateCommodityIdList(
					start, bufferSize);
			
			for (Long itemId : commodityIds) {
				
				List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
				try{
					
					recommendedItems = getMostSimilarItems(itemId);
					log.info("doCalculate itemId : " + itemId);
				}
				catch(org.apache.mahout.cf.taste.common.NoSuchItemException e){
					log.error("doCalculate 没有这个item ："+itemId + " 总数："+(++noItemIdCount), e);
				}
				for (RecommendedItem recommendedItem : recommendedItems) {
					
					bufferedWriter.write(itemId + TAB
							+ recommendedItem.getItemID() + TAB
							+ recommendedItem.getValue() + TAB
							+ mode + "\r\n");
				}
			}
			start += bufferSize;
		} while (commodityIds != null && commodityIds.size() > 0);
		bufferedWriter.flush();
		bufferedWriter.close();
		log.info("doCalculate end ---");
	}

	@Override
	public void deleteOnlyOneUserId() {
		recommendDao.deleteOnlyOneUserId();
		
	}
}
