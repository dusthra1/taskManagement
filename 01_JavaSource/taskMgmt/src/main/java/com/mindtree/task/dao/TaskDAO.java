package com.mindtree.task.dao;

import java.util.List;
import java.util.Map;

import com.mindtree.task.model.Persistable;

public interface TaskDAO {
	
	public Persistable saveEntity(Persistable obj);
	
	public void deleteEntity(Persistable obj);
	
	public void updateEntity(Persistable obj);
	
	public Persistable getEntity(Class obj, String key);
	
	public Persistable getEntity(Class obj, Integer key);
	
	Persistable findRecord(String queryName, Map<String, Object> params);
	
	public List<Persistable> findRecords(String queryName, Map<String, Object> params);
	
	public void updateRecords(String queryName, Map<String, Object> params);
	
}
