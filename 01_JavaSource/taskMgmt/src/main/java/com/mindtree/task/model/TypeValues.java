package com.mindtree.task.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TYPE_VALUES")
public class TypeValues extends AbstractTimestampEntity implements Persistable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="TYPE_VALUE_ID")
	private int typeValueId;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name="TYPE_CODE_ID")
	private TypeCode typeCode;
	@Column(name="TYPE_VALUE")
	private String typeValue;
	@Column(name="DESCRIPTION")
	private String description;
	
	public int getTypeValueId() {
		return typeValueId;
	}
	public TypeCode getTypeCode() {
		return typeCode;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public String getDescription() {
		return description;
	}
	public void setTypeValueId(int typeValueId) {
		this.typeValueId = typeValueId;
	}
	public void setTypeCode(TypeCode typeCode) {
		this.typeCode = typeCode;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
		result = prime * result + ((typeValue == null) ? 0 : typeValue.hashCode());
		result = prime * result + typeValueId;
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
		TypeValues other = (TypeValues) obj;
		if (typeCode == null) {
			if (other.typeCode != null)
				return false;
		} else if (!typeCode.equals(other.typeCode))
			return false;
		if (typeValue == null) {
			if (other.typeValue != null)
				return false;
		} else if (!typeValue.equals(other.typeValue))
			return false;
		if (typeValueId != other.typeValueId)
			return false;
		return true;
	}
	
	

}
