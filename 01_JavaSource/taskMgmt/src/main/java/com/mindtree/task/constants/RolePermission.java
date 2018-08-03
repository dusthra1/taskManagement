package com.mindtree.task.constants;

public enum RolePermission {
	
	PERM_ACCESS_ADMIN_AREA(10),
	PERM_ACCESS_VIEW_TASKS(11);
	
	private final int value;
	
	RolePermission(int permissionId){
		this.value = permissionId;
	}
	
	public int getValue() {
		return value;
	}
	
	public static RolePermission fromCode(int value){
		for(RolePermission rolePermission:RolePermission.values()){
			if(rolePermission.getValue() == value){
				return rolePermission;
			}
		}
		throw new UnsupportedOperationException(
	                "The code " + value + " is not supported for any RolePermission!");
	}

}
