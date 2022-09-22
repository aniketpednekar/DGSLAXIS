package com.dgsl.imp.entity;

public class TestEntity {

	private String requestNo;
	private String depositRate;
	private String empID;
	private String department;
	private String link;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getDepositRate() {
		return depositRate;
	}

	public void setDepositRate(String depositRate) {
		this.depositRate = depositRate;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "TestEntity [requestNo=" + requestNo + ", depositRate=" + depositRate + ", empID=" + empID
				+ ", department=" + department + ", link=" + link + "]";
	}

}
