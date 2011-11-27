package com.critize.model;

public class Stock {

	private String Id ;
	

	private String sCompany;
	private String sPoducts;
	private String sModelNo;
	private String sDescription;
	private String sQTY;
	private String sDate;
	
	public String getsCompany() {
		return sCompany;
	}
	public void setsCompany(String sCompany) {
		this.sCompany = sCompany;
	}
	public String getsPoducts() {
		return sPoducts;
	}
	public void setsPoducts(String sPoducts) {
		this.sPoducts = sPoducts;
	}
	public String getsModelNo() {
		return sModelNo;
	}
	public void setsModelNo(String sModelNo) {
		this.sModelNo = sModelNo;
	}
	public String getsDescription() {
		return sDescription;
	}
	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}
	public String getsQTY() {
		return sQTY;
	}
	public void setsQTY(String sQTY) {
		this.sQTY = sQTY;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	
	public Stock(String Id,String sCompany, String sPoducts, String sModelNo,
			String sDescription, String sQTY, String sDate) {
		super();
		this.Id = Id ;
		this.sCompany = sCompany;
		this.sPoducts = sPoducts;
		this.sModelNo = sModelNo;
		this.sDescription = sDescription;
		this.sQTY = sQTY;
		this.sDate = sDate;
	}
	
	
}
