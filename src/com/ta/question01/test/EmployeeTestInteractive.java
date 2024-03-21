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

public class EmployeeTestInteractive {

	public static void main(String[] args) {

		// 利用 Factory Pattern (工廠模式) 只會建立一次
		EmployeeDAOFactory factory = new EmployeeDAOFactory();

		// 每次建立物件都會增加記憶體位址
		// EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();

		EmployeeDAO dao = factory.createEmployeeDAO();

		boolean timeToQuit = false;

		//
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		do {
			try {
				timeToQuit = executeMenu(in, dao);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IOException 處理中...");
				// ...
				System.out.println("IOException 處理完...");
			} catch (DAOException e) {
				e.printStackTrace();
				System.out.println("IOException 處理中...");
				// ...
				System.out.println("IOException 處理完...");
			}

		} while (!timeToQuit);
	}

	public static boolean executeMenu(BufferedReader in, EmployeeDAO dao) throws IOException, DAOException {
		Employee emp;
		String action;
		int id;

		System.out.println("\n\n[C]reate | [R]ead | [U]pdate | [D]elete | [L]ist | [Q]uit: ");
		action = in.readLine();
		if ((action.length() == 0) || action.toUpperCase().charAt(0) == 'Q') {
			return true;
		}

		switch (action.toUpperCase().charAt(0)) {
		// Create a new employee record
		case 'C':
			emp = inputEmployee(in);
			// emp.save();
			dao.add(emp);
			System.out.println("Successfully added Employee Record: " + emp.getId());
			System.out.println("\n\nCreated " + emp);
			break;

		// Display an employee record
		case 'R':
			System.out.println("Enter int value for employee id: ");
			id = Integer.valueOf(in.readLine().trim());

			// Find this Employee record
			emp = dao.findById(id);
			if (emp != null) {
				System.out.println(emp + "\n");
			} else {
				System.out.println("\n\nEmployee " + id + " not found");
				break;
			}

			break;

		// Update an existing employee record
		case 'U':
			System.out.println("Enter int value for employee id: ");
			id = Integer.valueOf(in.readLine().trim());
			// Find this Employee record
			emp = null;
			emp = dao.findById(id);
			if (emp == null) {
				System.out.println("\n\nEmployee " + id + " not found");
				break;
			}
			// Go through the record to allow changes

			emp = inputEmployee(in, emp);
			dao.update(emp);
			System.out.println("Successfully updated Employee Record: " + emp.getId());
			break;

		// Delete an employee record
		case 'D':
			System.out.println("Enter int value for employee id: ");
			id = Integer.valueOf(in.readLine().trim());

			// Find this Employee record
			emp = null;
			emp = dao.findById(id);
			if (emp == null) {
				System.out.println("\n\nEmployee " + id + " not found");
				break;
			}
			dao.delete(id);
			System.out.println("Deleted Employee " + id);
			break;

		// Display a list (Read the records) of Employees
		case 'L':
			Employee[] allEmps = dao.getAllEmployees();
			for (Employee employee : allEmps) {
				System.out.println(employee + "\n");
			}
			break;
		}

		return false;
	}

	public static Employee inputEmployee(BufferedReader in) throws IOException {
		return inputEmployee(in, null, true);
	}

	public static Employee inputEmployee(BufferedReader in, Employee empDefaults) throws IOException {
		return inputEmployee(in, empDefaults, false);
	}

	public static Employee inputEmployee(BufferedReader in, Employee empDefaults, boolean newEmployee)
			throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.US);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		int id = -1;
		String firstName;
		String lastName;
		Date birthDate = null;
		Employee emp;
		float salary;

		if (newEmployee) {
			do {
				System.out.println("Enter int value for employee id: ");
				try {
					id = Integer.valueOf(in.readLine().trim());
					if (id < 0) {
						System.out.println("Please retry with a valid positive integer id");
					}
				} catch (NumberFormatException e) {
					System.out.println("Please retry with a valid positive integer id");
				}
			} while (id < 0);
		} else {
			id = empDefaults.getId();
			System.out
					.println("Modify the fields of Employee record: " + id + ". Press return to accept current value.");
		}

		String prompt = "Enter value for employee first name"
				+ ((empDefaults == null) ? "" : " [" + empDefaults.getFirstName() + "]");

		do {
			System.out.println(prompt + " : ");
			firstName = in.readLine().trim();
			if (firstName.equals("") && empDefaults != null) {
				firstName = empDefaults.getFirstName();
			}
			if (firstName.length() < 1) {
				System.out.println("Please retry with a valid first name");
			}
		} while (firstName.length() < 1);

		prompt = "Enter value for employee last name"
				+ ((empDefaults == null) ? "" : " [" + empDefaults.getLastName() + "]");
		do {
			System.out.println(prompt + " : ");
			lastName = in.readLine().trim();
			if (lastName.equals("") && empDefaults != null) {
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
			if (dateStr.equals("") && empDefaults != null) {
				birthDate = empDefaults.getBirthDate();
			} else {
				birthDate = null;
				try {
					birthDate = new Date(df.parse(dateStr).getTime());
				} catch (ParseException e) {
					System.out.println("Please retry with a valid birth date: " + e.getMessage());
				}
			}
		} while (birthDate == null);

		prompt = "Enter float value for employee salary"
				+ ((empDefaults == null) ? "" : " [" + nf.format((double) empDefaults.getSalary()) + "]");
		do {
			System.out.println(prompt + " : ");
			salary = 0;
			try {
				String amt = in.readLine().trim();
				if (!amt.equals("")) {
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
			}
		} while (salary == 0);

		// Create an Employee object
		emp = new Employee(id, firstName, lastName, birthDate, salary);
		return emp;
	}
}
