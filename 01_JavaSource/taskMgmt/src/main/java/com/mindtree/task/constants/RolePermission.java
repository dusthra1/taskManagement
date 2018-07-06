package com.mindtree.task.constants;

public enum RolePermission {
	
	PERM_ACCESS_ADMIN_AREA(10),
	PERM_ACCESS_VIEW_TASKS(11);
	
	public final int value;
	
	private RolePermission(int permissionId){
		this.value = permissionId;
	}
	
	public int getValue() {
		return value;
	}

}
