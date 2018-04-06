package com.mindtree.task.constants;

public enum Role {
	
	SUPER_ADMIN(4),
	ADMIN(5),
	USER(6),
	MANAGER(7);

	public final int value;
	
	private Role(int roleId){
		this.value = roleId;
	}

	public int getValue() {
		return value;
	}
}
