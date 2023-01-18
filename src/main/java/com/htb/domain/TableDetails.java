package com.htb.domain;

public class TableDetails {


	private int tableId;
	private int tableSize;

	public TableDetails(int tableId, int tableSize) {
		this.tableId = tableId;
		this.tableSize = tableSize;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public int getTableSize() {
		return tableSize;
	}

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}
	@Override
	public String toString() {
		return "TableDetails [tableId=" + tableId + ", tableSize=" + tableSize + "]";
	}
}
