package com.winxuan.ec.service.survey;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SurveyDao;
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
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 * 
 */
@Service("surveyService")
@Transactional(rollbackFor = Exception.class)
public class SurveyServiceImpl implements SurveyService {

	@InjectDao
	private SurveyDao surveyDao;

	@Override
	public List<Survey> querySurveys(Pagination pagination) {

		return surveyDao.querySurveys(pagination, (short) 0);
	}

	@Override
	public void addSurvey(Survey survey) {

		surveyDao.save(survey);
	}

	@Override
	public Survey get(long surveyId) {
		return surveyDao.get(surveyId);
	}

	@Override
	public Separator getSeparator(Long separatorId) {

		return surveyDao.getSeparator(separatorId);
	}

	@Override
	public void saveOrUpdateSeparator(Separator separator) {

		surveyDao.saveOrUpdateSeparator(separator);
	}

	@Override
	public void updateSurvey(Survey survey) {
		surveyDao.updateSurvey(survey);
	}

	@Override
	public SurveyText getText(Long textId) {
		return surveyDao.getText(textId);
	}

	@Override
	public void saveOrUpdateText(SurveyText text) {

		surveyDao.saveOrUpdateText(text);
	}

	@Override
	public Select getSelect(Long selectId) {
		return surveyDao.getSelect(selectId);
	}

	@Override
	public void saveOrUpdateSelect(Select select) {

		surveyDao.saveOrUpdateSelect(select);
	}

	@Override
	public Item getItem(Long id) {
		return surveyDao.getItem(id);
	}

	@Override
	public void deleteItem(Item item) {

		surveyDao.delete(item);
	}

	@Override
	public void removeSelect(Content content) {

		Select select = (Select) content;
		for (SelectValue sv : select.getValues()) {
			surveyDao.removeSelectValue(sv);
		}
		surveyDao.removeSelect(select);
	}

	@Override
	public void removeSeparator(Content content) {

		surveyDao.removeSeparator((Separator) content);
	}

	@Override
	public void removeText(Content content) {
		surveyDao.removeText((SurveyText) content);
	}

	@Override
	public void delete(Survey survey) {
		surveyDao.delete(survey);
	}

	@Override
	public void saveClient(Client client) {
		surveyDao.saveClient(client);
	}

	@Override
	public SelectValue getSelectValue(Long valueId) {

		return surveyDao.getSelectValue(valueId);
	}

	@Override
	public void saveAnswer(Answer answer) {
		surveyDao.saveAnswer(answer);
	}

	@Override
	public List<Client> queryClients(Pagination pagination) {
		return surveyDao.queryClients(pagination, (short) 0);
	}

	@Override
	public Client queryClient(Long clientId) {
		return surveyDao.queryClient(clientId);
	}

	@Override
	public void moveup(Item item) {

		List<Item> items = surveyDao.findUpItem(item.getSurvey().getId(),
				item.getIndex());
		if (items != null && items.size() > 0) {
			Item upItem = items.get(0);
			int tmp = upItem.getIndex();
			upItem.setIndex(item.getIndex());
			item.setIndex(tmp);
			surveyDao.saveOrUpdate(item);
			surveyDao.saveOrUpdate(upItem);
		}
	}

	@Override
	public void movedown(Item item) {

		List<Item> items = surveyDao.findDownItem(item.getSurvey().getId(),
				item.getIndex());
		if (items != null && items.size() > 0) {
			Item downItem = items.get(0);
			int tmp = downItem.getIndex();
			downItem.setIndex(item.getIndex());
			item.setIndex(tmp);
			surveyDao.saveOrUpdate(item);
			surveyDao.saveOrUpdate(downItem);
		}
	}

	@Override
	public void removeSelectValue(SelectValue sv) {
		surveyDao.removeSelectValue(sv);
	}

	@Override
	public void merge(Select select) {
		surveyDao.merge(select);
	}

	@Override
	public void addRelease(String url, Survey survey) {
		Release release = new Release();
		release.setUrl(url);
		release.setSurvey(survey);
		surveyDao.addRealse(release);
	}

	@Override
	public void deleteRelease(Survey survey) {

		surveyDao.deleteRelease(survey.getId());
	}

	@Override
	public List<Release> findReleasesByUrl(String releaseUrl) {
		String[] urls = releaseUrl
				.substring("http://www.winxuan.com/".length()).split("/");
		String url = urls[0].split("\\?")[0];
		if ("".equals(url)) {
			url = "/";
		}
		return surveyDao.findReleasesByUrl(url);
	}

}
