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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;

@Entity
@Table(name="USERS")
	@NamedQueries(value = { @NamedQuery(name = NamedQueryConstants.FIND_USER_BY_USERNAME_KEY, query = QueryConstants.FIND_USER_BY_USERNAME_KEY),
			 				@NamedQuery(name = NamedQueryConstants.FIND_USER_BY_USERNAME, query = QueryConstants.FIND_USER_BY_USERNAME),
			 				@NamedQuery(name = NamedQueryConstants.UPDATE_USERS_LOGIN_STATUS, query = QueryConstants.UPDATE_USERS_LOGIN_STATUS)
			
})
public class User implements Persistable  {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	private int id;
	
	@Column(name="FNAME")
	private String firstName;
	
	@Column(name="LNAME")
	private String lastName;
	
	@Column(name="USERNAME")
	private String userName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="EMAIL_ID")
	private String emailId;
		
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)	
	@JoinTable(name="USERS_ROLE", 
	joinColumns=@JoinColumn(name="USER_ID"),
	inverseJoinColumns=@JoinColumn(name="ROLE_ID"))
	@GenericGenerator(name="userRole-sequence-hilo",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",
	parameters = {
		@Parameter(name = "sequence_name", value = "users_role_seq"),
		@Parameter(name = "initial_value", value = "1"),
		@Parameter(name = "increment_size", value = "1")
		})
	@CollectionId(columns = { @Column(name="ID") }, generator = "userRole-sequence-hilo", type= @Type(type="long"))
	private List<TypeValues> roles = new ArrayList<>();
	
	@Column(name="STATUS")
	private String status;	
	
	@Column(name="LOGIN_STATUS")
	private String loginStatus;
	

	@Column(name="LAST_LOGIN")
	private Date lastLogin;
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmailId() {
		return emailId;
	}

	public List<TypeValues> getRoles() {
		return roles;
	}

	public String getStatus() {
		return status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setRoles(List<TypeValues> roles) {
		this.roles = roles;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
