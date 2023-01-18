package com.htb.domain;

import java.util.ArrayList;

public class BranchDetails {
	private int id;
	private String name;
	private String address;
	ArrayList<TableDetails> tablesList;

	public BranchDetails(int id, String name, String address, ArrayList<TableDetails> tablesList) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.tablesList = tablesList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<TableDetails> getTablesList() {
		return tablesList;
	}

	public void setTablesList(ArrayList<TableDetails> tablesList) {
		this.tablesList = tablesList;
	}
}
