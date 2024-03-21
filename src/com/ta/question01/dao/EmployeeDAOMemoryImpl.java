package com.ta.question01.dao;

import java.util.ArrayList;
import java.util.List;

import com.ta.question01.model.Employee;

public class EmployeeDAOMemoryImpl implements EmployeeDAO {
	
    private Employee[] employeeArray = new Employee[10];

	@Override
	public void add(Employee emp) throws DAOException{
		try {
			if(employeeArray[emp.getId()]!=null)
				throw new DAOException("員工編號已存在,新增失敗!");
	        employeeArray[emp.getId()] = emp;
	    } catch(ArrayIndexOutOfBoundsException ex) {
	    	throw new DAOException("員工編號小於10,新增失敗!", ex);
	    }
	}

	@Override
	public void update(Employee emp) {
        employeeArray[emp.getId()] = emp;
	}

	@Override
	public void delete(int id) {
        employeeArray[id] = null;
	}

	@Override
	public Employee findById(int id) throws DAOException {
		try {
			return employeeArray[id];
		} catch(ArrayIndexOutOfBoundsException ex) {
	    	throw new DAOException("員工編號小於10,查詢失敗!", ex);
	    }
	}

	@Override
	public Employee[] getAllEmployees() {
		List<Employee> emps = new ArrayList<>();
	    // Iterate through the memory array and find Employee objects
	    for (Employee e : employeeArray) {
	        if (e != null) {
	            emps.add(e);
	        }
	    }
	    return emps.toArray(new Employee[0]);
	}

	@Override
	public void close() {
		System.out.println("資源關閉......");
	}

}
