package com.mindtree.task.model;

import java.util.ArrayList;
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
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name="ROLE")
public class Role extends AbstractTimestampEntity implements Persistable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ROLE_ID")
	private int roleId;
	
	@Column(name="ROLE_NAME")
	private String roleName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)	
	@JoinTable(name="ROLE_PERMISSIONS", 
	joinColumns=@JoinColumn(name="ROLE_ID"),
	inverseJoinColumns=@JoinColumn(name="PERMISSION_ID"))
	@GenericGenerator(name="rolePermissions-sequence-hilo",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",
	parameters = {
		@Parameter(name = "sequence_name", value = "role_permissions_seq"),
		@Parameter(name = "initial_value", value = "1"),
		@Parameter(name = "increment_size", value = "1")
		})
	@CollectionId(columns = { @Column(name="ID") }, generator = "rolePermissions-sequence-hilo", type= @Type(type="long"))
	private List<TypeValues> permissions = new ArrayList<>();

	public int getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getDescription() {
		return description;
	}

	public List<TypeValues> getPermissions() {
		return permissions;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPermissions(List<TypeValues> permissions) {
		this.permissions = permissions;
	}
}
