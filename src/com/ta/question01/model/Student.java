package com.ta.question01.model;

/**
 * 學生
 */
public class Student {

	private Integer id; // 編號
	private String name; // 名稱
	private Integer balance; // 學費

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Student [編號=" + id + ", 名稱=" + name + ", 餘額=" + balance + "]";
	}

}
