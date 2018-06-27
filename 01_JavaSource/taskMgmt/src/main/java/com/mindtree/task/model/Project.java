package com.mindtree.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;


@Entity
@Table(name="PROJECT")
@NamedQueries(value = { @NamedQuery(name = NamedQueryConstants.ALL_PROJECTS, query = QueryConstants.ALL_PROJECTS),
		 				@NamedQuery(name = NamedQueryConstants.PROJECTS_BY_LIKE_NAME, query = QueryConstants.PROJECTS_BY_LIKE_NAME)
	  })
public class Project extends AbstractTimestampEntity implements Persistable, Comparable<Project>{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PROJ_ID")
	private int id;
	@Column(name="PROJ_NAME")
	private String name;
	@Column(name="PROJ_DESC")
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Project o) {
		return Integer.compare(this.id, o.id);
	}

}
