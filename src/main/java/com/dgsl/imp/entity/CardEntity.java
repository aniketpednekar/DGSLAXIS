package com.dgsl.imp.entity;

public class CardEntity {

	private String ID, HEADER, BODY;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getHEADER() {
		return HEADER;
	}

	public void setHEADER(String hEADER) {
		HEADER = hEADER;
	}

	public String getBODY() {
		return BODY;
	}

	public void setBODY(String bODY) {
		BODY = bODY;
	}

	@Override
	public String toString() {
		return "CardEntity [ID=" + ID + ", HEADER=" + HEADER + ", BODY=" + BODY + "]";
	}

}
