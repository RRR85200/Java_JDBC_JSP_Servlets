package com.Dictionary.model;

public class FlashCard {

	private int flashCardId;
	private String flashword;
	private String FDefinition;
	private int FwordNUm;
	private int UserId;

	public FlashCard(int flashCardId, String flashword, String fDefinition, int fwordNUm) {
		super();
		this.flashCardId = flashCardId;
		this.flashword = flashword;
		FDefinition = fDefinition;
		FwordNUm = fwordNUm;
	}

	public int getFlashCardId() {
		return flashCardId;
	}
	public void setFlashCardId(int flashCardId) {
		this.flashCardId = flashCardId;
	}
	public String getFlashword() {
		return flashword;
	}

	public void setFlashword(String flashword) {
		this.flashword = flashword;
	}
	public String getFDefinition() {
		return FDefinition;
	}
	public void setFDefinition(String fDefinition) {
		FDefinition = fDefinition;
	}
	public int getFwordNUm() {
		return FwordNUm;
	}
	public void setFwordNUm(int fwordNUm) {
		FwordNUm = fwordNUm;
	}

	@Override
	public String toString() {
		return "FlashCard [flashCardId=" + flashCardId + ", flashword=" + flashword + ", FDefinition=" + FDefinition
				+ ", FwordNUm=" + FwordNUm + "]";
	}






}
