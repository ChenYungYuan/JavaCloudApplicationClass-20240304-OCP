package com.ta.question01.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.ta.question01.dao.DAOException;
import com.ta.question01.dao.EmployeeDAO;
import com.ta.question01.dao.EmployeeDAOFactory;
import com.ta.question01.model.Employee;

public class StudentDaoTestForJava17 {
	
	private static Employee emp;
	private static String action;
	private static int id;
	private static SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.US);

	public static void main(String[] args) {

		EmployeeDAOFactory factory = new EmployeeDAOFactory();
		EmployeeDAO dao = factory.createEmployeeDAO();
		boolean timeToQuit = false;

		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
			do {
				timeToQuit = executeMenu(in, dao);
			} while (!timeToQuit);
		} catch (IOException | DAOException e) {
			e.printStackTrace();
		}
	}

	public static boolean executeMenu(BufferedReader in, EmployeeDAO dao) throws IOException, DAOException {
		
		System.out.println("\n\n[C]reate | [R]ead | [U]pdate | [D]elete | [L]ist | [Q]uit: ");
		action = in.readLine();
		if ((action.length() == 0) || action.isBlank() || action.toUpperCase().charAt(0) == 'Q') {
			return true;
		}

		/*
		 * 這邊 使用 Java 14 的 switch/case 比之前的 switch/case 要簡單很多 也不需要使用 break
		 */
		switch (action.toUpperCase().charAt(0)) {
		
			case 'C' -> {
				emp = inputEmployee(in);
				dao.add(emp);
				System.out.println("Successfully added Employee Record: " + emp.getId());
				System.out.println("\n\nCreated " + emp);
			}
	
			case 'R' -> {
				System.out.println("Enter int value for employee id: ");
				id = Integer.parseInt(in.readLine().trim());
				emp = dao.findById(id);
				if (emp != null) {
					System.out.println(emp + "\n");
				} else {
					System.out.println("\n\nEmployee " + id + " not found");
				}
			}
	
			case 'U' -> {
				System.out.println("Enter int value for employee id: ");
				id = Integer.parseInt(in.readLine().trim());
				emp = dao.findById(id);
				if (emp == null) {
					System.out.println("\n\nEmployee " + id + " not found");
					break;
				}
				emp = inputEmployee(in, emp);
				dao.update(emp);
				System.out.println("Successfully updated Employee Record: " + emp.getId());
			}
	
			case 'D' -> {
				System.out.println("Enter int value for employee id: ");
				id = Integer.parseInt(in.readLine().trim());
				emp = dao.findById(id);
				if (emp == null) {
					System.out.println("\n\nEmployee " + id + " not found");
					break;
				}
				dao.delete(id);
				System.out.println("Deleted Employee " + id);
			}
	
			case 'L' -> {
				Employee[] allEmps = dao.getAllEmployees();
				for (Employee employee : allEmps) {
					System.out.println(employee + "\n");
				}
			}
		}
		
		return false;
	}

	public static Employee inputEmployee(BufferedReader in) throws IOException {
		return inputEmployee(in, null, true);
	}

	public static Employee inputEmployee(BufferedReader in, Employee empDefaults) throws IOException {
		return inputEmployee(in, empDefaults, false);
	}

	public static Employee inputEmployee(BufferedReader in, Employee empDefaults, boolean newEmployee) throws IOException {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		int id;
		String firstName;
		String lastName;
		Date birthDate;
		Employee emp;
		float salary;

		if (newEmployee) {
			do {
				System.out.println("Enter int value for employee id: ");
				try {
					id = Integer.parseInt(in.readLine().trim());
					if (id < 0) {
						System.out.println("Please retry with a valid positive integer id");
					}
				} catch (NumberFormatException e) {
					System.out.println("Please retry with a valid positive integer id");
					id = -1;
				}
			} while (id < 0);
		} else {
			id = empDefaults.getId();
			System.out.println("Modify the fields of Employee record: " + id + ". Press return to accept current value.");
		}
		
		String prompt = "Enter value for employee first name" + ((empDefaults == null) ? "" : " [" + empDefaults.getFirstName() + "]");

		do {
			System.out.println(prompt + " : ");
			firstName = in.readLine().trim();
			if (firstName.isEmpty() && empDefaults != null) {
				firstName = empDefaults.getFirstName();
			}
			if (firstName.length() < 1) {
				System.out.println("Please retry with a valid first name");
			}
		} while (firstName.length() < 1);

		prompt = "Enter value for employee last name" + ((empDefaults == null) ? "" : " [" + empDefaults.getLastName() + "]");
		
		do {
			System.out.println(prompt + " : ");
			lastName = in.readLine().trim();
			if (lastName.isEmpty() && empDefaults != null) {
				lastName = empDefaults.getLastName();
			}
			if (lastName.length() < 1) {
				System.out.println("Please retry with a valid last name");
			}
		} while (lastName.length() < 1);

		prompt = "Enter value for employee birth date (" + df.toLocalizedPattern() + ")"
				+ ((empDefaults == null) ? "" : " [" + df.format(empDefaults.getBirthDate()) + "]");
		do {
			System.out.println(prompt + " : ");
			String dateStr = in.readLine().trim();
			if (dateStr.isEmpty() && empDefaults != null) {
				birthDate = empDefaults.getBirthDate();
			} else {
				try {
					birthDate = df.parse(dateStr);
				} catch (ParseException e) {
					System.out.println("Please retry with a valid birth date: " + e.getMessage());
					birthDate = null;
				}
			}
		} while (birthDate == null);

		prompt = "Enter float value for employee salary" + ((empDefaults == null) ? "" : " [" + nf.format(empDefaults.getSalary()) + "]");
		
		do {
			System.out.println(prompt + " : ");
			salary = 0;
			try {
				String amt = in.readLine().trim();
				if (!amt.isEmpty()) {
					salary = Float.parseFloat(amt);
				}
				if (salary == 0 && empDefaults != null) {
					salary = empDefaults.getSalary();
				}
				if (salary < 0) {
					System.out.println("Please retry with a positive salary");
					salary = 0;
				}
			} catch (NumberFormatException e) {
				System.out.println("Please retry with a valid float salary: " + e.getMessage());
				salary = 0;
			}
		} while (salary == 0);

		// Create an Employee object
		emp = new Employee(id, firstName, lastName, birthDate, salary);
		return emp;
	}
}
