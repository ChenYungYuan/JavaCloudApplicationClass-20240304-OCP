package com.ta.question01.test;

import com.ta.question01.model.School;
import com.ta.question01.model.Student;

public class StudentDaoTest {

	private static final Integer money = 1000;

	public static void main(String[] args) {

		// 建立學生 物件
		Student student = new Student();

		// 給值
		student.setId(1);
		student.setName("John");
		student.setBalance(money);
		System.out.println(student);

		
		try {
			
			pay(student); // 學校 付學費
			throw new IllegalArgumentException("餘額不足...");
		} catch (Exception e) {
			e.printStackTrace();
			// 父母
			System.out.println("跟父母借款");
			// 銀行
			System.out.println("跟銀行借款");
			// 借
			System.out.println("跟其他人借");
		}
		
	}

	/**
	 * 學校 付學費
	 * 
	 * 
	 * @return
	 */
	public static Integer pay(Student student) throws Exception {
		
		// 建立學校 物件
		School school = new School();
		school.setId(1);
		school.setName("某某大學");
		
		// 繳學費
		if (student.getBalance() < school.getPayment()) {
			System.out.println("餘額不足...");
			// 因為學校不處理這個問題，而拋給學生自行處理
			// throw new IllegalArgumentException("餘額不足...");
		}
		
		return null;
	};

}
