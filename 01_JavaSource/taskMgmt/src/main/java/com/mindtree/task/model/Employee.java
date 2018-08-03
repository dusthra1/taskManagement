package com.mindtree.task.model;



import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;


@Entity
@Table (name="EMPLOYEE")
@NamedQueries(value = { @NamedQuery(name = NamedQueryConstants.PROJECT_EMPLOYEES, query = QueryConstants.PROJECT_EMPLOYEES),
						@NamedQuery(name = NamedQueryConstants.ALL_EMPLOYEES, query = QueryConstants.ALL_EMPLOYEES),
						@NamedQuery(name = NamedQueryConstants.EMPLOYEES_FOR_IDS, query = QueryConstants.EMPLOYEES_FOR_IDS)
})
public class Employee extends AbstractTimestampEntity implements Persistable,Comparable<Employee> {
		
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name="MID")
	private String mid;
	@Column (name="NAME")
	private String name;
	@Column (name="JOIN_DATE")
	@Temporal (TemporalType.DATE)
	private Date joinDate;
	@Column (name="EMAIL_ID")
	private String emailId;
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="PROJ_ID")
	private Project project;	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
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
		Employee other = (Employee) obj;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public int compareTo(Employee o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
}
