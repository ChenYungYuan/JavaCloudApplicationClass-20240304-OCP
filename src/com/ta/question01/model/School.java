package com.ta.question01.model;

/**
 * 學校
 */
public class School {

	private Integer id; // 編號
	private String name; // 名稱
	private Integer payment = 5000; // 付款金額

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

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", payment=" + payment + "]";
	}

}
