package com.winxuan.ec.service.survey;

import java.util.List;

import com.winxuan.ec.model.survey.Answer;
import com.winxuan.ec.model.survey.Client;
import com.winxuan.ec.model.survey.Content;
import com.winxuan.ec.model.survey.Item;
import com.winxuan.ec.model.survey.Release;
import com.winxuan.ec.model.survey.Select;
import com.winxuan.ec.model.survey.SelectValue;
import com.winxuan.ec.model.survey.Separator;
import com.winxuan.ec.model.survey.Survey;
import com.winxuan.ec.model.survey.SurveyText;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 * 
 */
public interface SurveyService {

	List<Survey> querySurveys(Pagination pagination);

	void addSurvey(Survey survey);

	Survey get(long surveyId);

	Separator getSeparator(Long separatorId);

	void saveOrUpdateSeparator(Separator separator);

	void updateSurvey(Survey survey);

	SurveyText getText(Long textId);

	void saveOrUpdateText(SurveyText text);

	Select getSelect(Long selectId);

	void saveOrUpdateSelect(Select select);

	Item getItem(Long id);

	void deleteItem(Item item);

	void removeSelect(Content content);

	void removeSeparator(Content content);

	void removeText(Content content);

	void delete(Survey survey);

	void saveClient(Client client);

	SelectValue getSelectValue(Long valueId);

	void saveAnswer(Answer answer);

	List<Client> queryClients(Pagination pagination);

	Client queryClient(Long clientId);

	void moveup(Item item);

	void movedown(Item item);

	void removeSelectValue(SelectValue sv);

	void merge(Select select);

	void addRelease(String url, Survey survey);

	void deleteRelease(Survey survey);

	List<Release> findReleasesByUrl(String releaseUrl);

}
