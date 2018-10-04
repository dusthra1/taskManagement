package com.mindtree.task.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.exception.DAOException;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.util.CriteriaExpression;

@Repository
public class TaskDAOImpl implements TaskDAO {
	
	private static final Logger log = Logger.getLogger(TaskDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Persistable saveEntity(Persistable obj) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(obj);
			
		} catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		}
		return obj; 	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Persistable getEntity(Class classObj, String key) {
		Persistable retrievedObj=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			retrievedObj= (Persistable) session.load(classObj, key);
			
		} catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return retrievedObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Persistable getEntity(Class classObj, Integer key) {
		Persistable retrievedObj=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			retrievedObj= (Persistable) session.load(classObj, key);
			
		} catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return retrievedObj;
	}

	@Override
	public void deleteEntity(Persistable obj){

		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(obj);
			
		} catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
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
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
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
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		}		
		return persistable;
	}

	@Override
	public void updateRecords(String queryName, Map<String, Object> params) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session.createNamedQuery(queryName);
			if(params !=null){
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			query.executeUpdate();
		}catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		} 	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Persistable> findByCriteria(Class classObj, List<CriteriaExpression> criteriaExpressions) {
		List<Persistable> records = null;
		Session session = sessionFactory.getCurrentSession();
		try{
			CriteriaBuilder creteriaBuilder = session.getCriteriaBuilder();			
			
			CriteriaQuery<Persistable> crQuery = creteriaBuilder.createQuery(classObj);
			 Root<Persistable> objRoot = crQuery.from(classObj);
			 
			 Predicate[] predicates = null;
			
			 if(criteriaExpressions != null && !criteriaExpressions.isEmpty()){
				 predicates = new Predicate[criteriaExpressions.size()];
				 int i=0;
				 for(CriteriaExpression critExp: criteriaExpressions){
					 predicates[i] = critExp.toPredicate(objRoot, crQuery, creteriaBuilder);
					 i++;
				 }
			}
			if(null != predicates){
				crQuery.select(objRoot).where(predicates);
			}else{
				crQuery.select(objRoot);
			}
			
	        Query query=session.createQuery(crQuery);
	        records = query.getResultList();
			
		}catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return records;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findRecordsNSQL(String queryName, Map<String, Object> params) {

		Session session = sessionFactory.getCurrentSession();
		List<Object[]> records = null;
		try{
			Query query = session.createNativeQuery(queryName);
			if(params !=null){
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			records = query.getResultList();
		}catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		} 	
		return records;
	
	}

	@Override
	public void saveRecord(String insertQry, Map<String, Object> params) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session.createNativeQuery(insertQry);
			if(params !=null){
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			query.executeUpdate();
		}catch (Exception ex) {	
			log.error(ApplicationConstants.DAO_EXCEPTION_MSG+ex.getMessage());
			throw new DAOException(ex.getMessage(), ex);
		} 	
		
	}*/	
}
