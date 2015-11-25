package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.survey.Answer;
import com.winxuan.ec.model.survey.Client;
import com.winxuan.ec.model.survey.Item;
import com.winxuan.ec.model.survey.Release;
import com.winxuan.ec.model.survey.Select;
import com.winxuan.ec.model.survey.SelectValue;
import com.winxuan.ec.model.survey.Separator;
import com.winxuan.ec.model.survey.Survey;
import com.winxuan.ec.model.survey.SurveyText;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 * 
 */
public interface SurveyDao {

	@Query("from Survey s ")
	@OrderBys({ @OrderBy("s.id desc") })
	List<Survey> querySurveys(@Page Pagination pagination, @Order Short sort);
	
	@Query("from Client c ")
	@OrderBys({ @OrderBy("c.id desc") })
	List<Client> queryClients(@Page Pagination pagination, @Order Short sort);

	@Save
	void save(Survey survey);

	@Get
	Survey get(long surveyId);

	@Get
	Separator getSeparator(Long separatorId);

	@SaveOrUpdate
	void saveOrUpdateSeparator(Separator separator);

	@Update
	void updateSurvey(Survey survey);

	@Get
	SurveyText getText(Long textId);

	@SaveOrUpdate
	void saveOrUpdateText(SurveyText text);

	@Get
	Select getSelect(Long selectId);

	@SaveOrUpdate
	void saveOrUpdateSelect(Select select);

	@Get
	Item getItem(Long id);

	@Delete
	void delete(Item item);

	@Delete
	void removeSeparator(Separator content);

	@Delete
	void removeSelectValue(SelectValue sv);

	@Delete
	void removeSelect(Select select);

	@Delete
	void removeText(SurveyText content);

	@Delete
	void delete(Survey survey);

	@Save
	void saveClient(Client client);

	@Get
	SelectValue getSelectValue(Long valueId);

	@Save
	void saveAnswer(Answer answer);

	@Get
	Client queryClient(Long clientId);

	@Query("from Item item  where item.survey.id =? and item.index< ? order by item.index desc")
	List<Item> findUpItem(Long id, int index);

	@SaveOrUpdate
	void saveOrUpdate(Item item);

	@Query("from Item item  where item.survey.id =? and item.index> ? order by item.index asc")
	List<Item> findDownItem(Long id, int index);

	@Merge
	void merge(Select select);

	@Save
	void addRealse(Release release);

	@Query(value="DELETE FROM survey_release WHERE survey = ?",sqlQuery=true,executeUpdate=true)
	void deleteRelease(Long id);

	@Query("from Release r  where r.url=? order by r.id asc")
	List<Release> findReleasesByUrl(String url);

}
