package com.mindtree.task.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.task.constants.MessageCode;
import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.exception.DAOException;
import com.mindtree.task.model.Persistable;

@Service
public class I18nServiceImpl implements I18nService {
	
@Autowired
private TaskDAO  taskDAO;	
	
private static final Logger log = Logger.getLogger(I18nServiceImpl.class);
	
@Override
@Transactional(propagation = Propagation.REQUIRED)
public List<Persistable> getProperties() {
	List<Persistable> i18nMessageLst = null;
	try {
		i18nMessageLst = taskDAO.findRecords(NamedQueryConstants.I18N_MESSAGES, null);		
		
	} catch (DAOException daoEx) {
		log.error("Exception occured while getting projects " + daoEx.getMessage());
		ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, daoEx);
		throw ae;

	} catch (Exception ex) {
		log.error("Exception occured while getting projects " + ex.getMessage());
		ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		throw ae;
	}
	return i18nMessageLst;

}

}
