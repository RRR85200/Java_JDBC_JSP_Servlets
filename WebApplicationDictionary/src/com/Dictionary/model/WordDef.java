package com.Dictionary.model;

public class WordDef {
	
	private static int wordNum;
	private String word;
	private String wordType;
	private String wordDef;
	
	public WordDef(){
		
	}
	public WordDef(int wordNum, String word, String wordType, String wordDef) {
		this.word = word;
		this.wordType = wordType;
		this.wordDef = wordDef;
	}
	
	public static int getWordNum() {
		return wordNum;
	}

	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWordType() {
		return wordType;
	}

	public void setWordType(String wordType) {
		this.wordType = wordType;
	}

	public String getWordDef() {
		return wordDef;
	}

	public void setWordDef(String wordDef) {
		this.wordDef = wordDef;
	}

	@Override
	public String toString() {
		return "WordDef [wordNum=" + wordNum + ", word=" + word + ", wordType=" + wordType + ", wordDef=" + wordDef
				+ "]";
	}
	
	
	
	

}
