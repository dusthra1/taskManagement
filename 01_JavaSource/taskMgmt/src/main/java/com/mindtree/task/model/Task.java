package com.mindtree.task.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;

@Entity
@Table(name="TASK")
@NamedQueries(value = { @NamedQuery(name = NamedQueryConstants.ALL_TASKS_FOR_PROJ, query = QueryConstants.ALL_TASKS_FOR_PROJ)
})
public class Task implements Persistable,Comparable<Task>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TASK_ID")
	private int taskId;
	@Column(name="TASK_NAME")
	private String taskName;
	@Column(name="TASK_DESC")
	private String description;
	@Temporal (TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;
	@Temporal (TemporalType.DATE)
	@Column(name="DUE_DATE")
	private Date dueDate;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)	
	@JoinColumn(name="PROJ_ID")
	private Project project;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)	
	@JoinTable(name="TASK_EMPLOYEE", 
	joinColumns=@JoinColumn(name="TASK_ID"),
	inverseJoinColumns=@JoinColumn(name="MID"))
	@GenericGenerator(name="taskEmp-sequence-hilo",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",
	parameters = {
		@Parameter(name = "sequence_name", value = "task_employee_seq"),
		@Parameter(name = "initial_value", value = "1"),
		@Parameter(name = "increment_size", value = "1")
		})
	@CollectionId(columns = { @Column(name="Id") }, generator = "taskEmp-sequence-hilo", type= @Type(type="long"))
	private List<Employee> employeesList = new ArrayList<>();
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}	

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Employee> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employee> employeesList) {
		this.employeesList = employeesList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + taskId;
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
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
		Task other = (Task) obj;
		if (taskId != other.taskId)
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Task o) {
		return Integer.compare(this.taskId, o.taskId);
	}
}
