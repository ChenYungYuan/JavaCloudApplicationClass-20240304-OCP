package com.ta.question01.dao;

public class EmployeeDAOFactory {
	
	public EmployeeDAO createEmployeeDAO() {
		return new EmployeeDAOMemoryImpl();
	}
	
}
