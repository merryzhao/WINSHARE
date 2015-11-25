package com.winxuan.ec.task.dao.quartz;

import java.util.List;

import com.winxuan.ec.task.model.quartz.TaskTrigger;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

public interface TriggerDao {
	
	@Save
	public void save(TaskTrigger quartzTrigger);
	
	@Delete
	public void delete(TaskTrigger quartzTrigger);
	
	@Update
	public void update(TaskTrigger quartzTrigger);

	@Query(" from TaskTrigger t WHERE t.triggerName=?")
	public TaskTrigger get(String triggerName);
	
	@Query("from TaskTrigger t ORDER BY t.nextFireTime asc")
	public List<TaskTrigger> find(@Page Pagination pagination);

}
