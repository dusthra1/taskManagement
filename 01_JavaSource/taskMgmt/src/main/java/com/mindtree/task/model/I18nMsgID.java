package com.mindtree.task.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class I18nMsgID implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="code")
	private String code;
	
	@OneToOne
	@JoinColumn(name="LOCALE_ID")
	private Language locale;
	
	public I18nMsgID(){		
	}
	
	public I18nMsgID(String code, Language locale){
		this.code = code;
		this.locale = locale;
	}

	public String getCode() {
		return code;
	}

	public Language getLocale() {
		return locale;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLocale(Language locale) {
		this.locale = locale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		I18nMsgID other = (I18nMsgID) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}
}
