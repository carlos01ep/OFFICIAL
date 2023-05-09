package com.example.hbweb.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolUsuarioFormList {

	private Map<String, RolUsuarioForm> rolUsuarioFormList;
	
	
	

	public RolUsuarioFormList() {
		super();
		this.rolUsuarioFormList =  new HashMap<>();
	}

	public RolUsuarioFormList(Map<String, RolUsuarioForm> rolUsuarioFormList) {
		super();
		this.rolUsuarioFormList = rolUsuarioFormList;
	}

	public Map<String, RolUsuarioForm> getRolUsuarioFormList() {
		return rolUsuarioFormList;
	}

	public void setRolUsuarioFormList(Map<String, RolUsuarioForm> rolUsuarioFormList) {
		this.rolUsuarioFormList = rolUsuarioFormList;
	}

	
	
	
	
	
}
