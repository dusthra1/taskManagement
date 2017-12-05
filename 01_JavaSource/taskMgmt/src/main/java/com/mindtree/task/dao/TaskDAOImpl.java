package com.mindtree.task.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mindtree.task.exception.DAOException;
import com.mindtree.task.model.Persistable;

@Repository
public class TaskDAOImpl implements TaskDAO {
	
	private static final Logger log = Logger.getLogger(TaskDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Persistable saveEntity(Persistable obj) throws DAOException {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.persist(obj);
			
		} catch (Exception ex) {	
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		}
		return obj; 	
	}
	
	@Override
	public void updateEntity(Persistable obj) throws DAOException {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(obj);
			
		} catch (Exception ex) {	
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		} 	
	}

	@Override
	public Persistable getEntity(Class classObj, String key) throws DAOException {
		Persistable retrievedObj=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			retrievedObj= (Persistable) session.get(classObj, key);
			
		} catch (Exception ex) {	
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return retrievedObj;
	}

	@Override
	public Persistable getEntity(Class classObj, Integer key) throws DAOException {
		Persistable retrievedObj=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			retrievedObj= (Persistable) session.get(classObj, key);
			
		} catch (Exception ex) {	
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return retrievedObj;
	}

	@Override
	public void deleteEntity(Persistable obj) throws DAOException {

		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(obj);
			
		} catch (Exception ex) {	
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		} 	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Persistable> findRecords(String queryName, Map<String, Object> params) {
		Session session = sessionFactory.getCurrentSession();
		List<Persistable> records = null;
		try{
			Query query = session.createNamedQuery(queryName);
			if(params !=null){
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			records = query.getResultList();
		}catch (Exception ex) {	
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return records;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Persistable findRecord(String queryName, Map<String, Object> params) {
		Session session = sessionFactory.getCurrentSession();
		Persistable persistable = null;
		try {
			
			Query query = session.createNamedQuery(queryName);
			if(params !=null){
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			List<Persistable> resultList = query.getResultList();
			if (null != resultList && !resultList.isEmpty()) {
				persistable = resultList.get(0);
			}
		} catch (Exception ex) {
			log.error("DAO exception occured: "+ex);
			throw new DAOException(ex.getMessage(), ex);
		}		
		return persistable;
	}	
}