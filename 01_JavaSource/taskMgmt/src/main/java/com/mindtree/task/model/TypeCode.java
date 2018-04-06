package com.mindtree.task.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TYPE_CODE")
public class TypeCode implements Persistable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="TYPE_CODE_ID")
	private int type_code_id;
	@Column(name="TYPE_CODE")
	private String type_code;
	@Column(name="DESCRIPTION")
	private String description;
	
	@OneToMany(mappedBy="typeCode", fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	private List<TypeValues> values = new ArrayList<>();

	public int getType_code_id() {
		return type_code_id;
	}

	public String getType_code() {
		return type_code;
	}

	public String getDescription() {
		return description;
	}

	public List<TypeValues> getValues() {
		return values;
	}

	public void setType_code_id(int type_code_id) {
		this.type_code_id = type_code_id;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValues(List<TypeValues> values) {
		this.values = values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type_code == null) ? 0 : type_code.hashCode());
		result = prime * result + type_code_id;
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
		TypeCode other = (TypeCode) obj;
		if (type_code == null) {
			if (other.type_code != null)
				return false;
		} else if (!type_code.equals(other.type_code))
			return false;
		if (type_code_id != other.type_code_id)
			return false;
		return true;
	}
	
	

}
