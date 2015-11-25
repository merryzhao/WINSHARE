package com.winxuan.ec.task.dao.search;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * 
 * @author huangyixiang
 *
 */
public interface SearchDao {

	long searchIndexCount();
	
	void updateAuthorString();

	boolean isSearchIndexFinished(Date dateStart);
	
	void updateQuery() throws ParseException, SQLException;
	
	void clearQuery();
	
	void incrementQuery();
}
