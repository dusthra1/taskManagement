package com.mindtree.task.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;

@Entity
@Table(name="i18n_messages")
@NamedQueries(value = { @NamedQuery(name = NamedQueryConstants.I18N_MESSAGES, query = QueryConstants.I18N_MESSAGES)
			
})
public class I18nMessage implements Persistable{
	
	private static final long serialVersionUID = 1L;	
	
	@EmbeddedId
	private I18nMsgID i18nMsgID;	

	@Column(name="message")
	private String message;	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public I18nMsgID getI18nMsgID() {
		return i18nMsgID;
	}

	public void setI18nMsgID(I18nMsgID i18nMsgID) {
		this.i18nMsgID = i18nMsgID;
	}

}
