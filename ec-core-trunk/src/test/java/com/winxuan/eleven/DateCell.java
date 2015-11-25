package com.winxuan.eleven;

/**
 * 
 * @author Other
 *
 */
public class DateCell {
	
	public Integer col;
	
	public Integer sheet;
	
	public Integer row;
	
	public String value;
	
	public String username;
	
	public String sql;
	
	public String name;
	
	public String table;

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "DateCell [col=" + col + ", sheet=" + sheet + ", row=" + row
				+ ", value=" + value + ", username=" + username + ", sql="
				+ sql + ", name=" + name + ", table=" + table + "]";
	}

	public Integer getSheet() {
		return sheet;
	}

	public void setSheet(Integer sheet) {
		this.sheet = sheet;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
}
