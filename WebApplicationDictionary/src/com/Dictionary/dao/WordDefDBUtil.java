package com.Dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.Dictionary.model.WordDef;

public class WordDefDBUtil {

	private DataSource dataSource;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pStmt= null;
	PreparedStatement pStmt2= null;
	ResultSet rs = null;
	
	WordDef tempWordDet;
	static int searchWordNum;
	int MaxRowval ;

	public WordDefDBUtil(DataSource theDataSource) {
		this.dataSource = theDataSource;
	}

	public List<WordDef> getWordList() throws SQLException{

		List<WordDef> wordslist = new ArrayList<>();
		System.out.println("Getting list...");

		con = dataSource.getConnection();
		String sql = "select * from dictionary.searchengine";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			int wordNum = rs.getInt("WordNum");
			String word= rs.getString("word");
			String type = rs.getString("wordType");
			String wordDef = rs.getString("Definition");
						
			WordDef tempWord = new WordDef(wordNum,word, type, wordDef);
			wordslist.add(tempWord);
			
		}

		closeConnection();
		return wordslist;

	}

	public  void insertWord(WordDef newWord) throws Exception {

		con = dataSource.getConnection();
		String sqlMax = "select max(wordNum) from dictionary.searchengine";
		pStmt =con.prepareStatement(sqlMax);
		System.out.println("statement prepared");
		rs = pStmt.executeQuery();
		System.out.println("statement executed");
		rs.next();
		MaxRowval= rs.getInt(1);
		MaxRowval++;
		System.out.println(MaxRowval);
		
		String sql = "Insert into dictionary.searchengine"+"(wordNum, word, wordType, Definition) "+" values (?,?,?,?)";
		
		pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, MaxRowval);
		pStmt.setString(2, newWord.getWord());
		pStmt.setString(3, newWord.getWordType());
		pStmt.setString(4, newWord.getWordDef());

		pStmt.execute();
		System.out.println("Inserted in DB");
		closeConnection();
	}

	public WordDef getWordDetails(String word) throws Exception {
		con = dataSource.getConnection();
		String sql = "select * from dictionary.searchengine"+" where "+" word =? ";
		pStmt = con.prepareStatement(sql);
		pStmt.setString(1,word);

		rs = pStmt.executeQuery();


		if(rs.next()){
			int tempWordNum =rs.getInt("wordNum");
			String tempword = rs.getString("word");
			String temptype= rs.getString("wordtype");
			String tempDef = rs.getString("definition");
			
			this.tempWordDet = new WordDef();
			
			tempWordDet.setWordNum(tempWordNum);
			tempWordDet.setWord(tempword);
			tempWordDet.setWordType(temptype);
			tempWordDet.setWordDef(tempDef);
			
			closeConnection();
			return tempWordDet;
			
		}else{
			System.out.println("Could not find word ");	
			return null;
		}
		
		
		
		
	}

	public void updateWord(WordDef updtWord) throws Exception {
		
		System.out.println("Inside update");
		con = dataSource.getConnection();
		String sql = "update dictionary.searchengine"+" set wordType=?, Definition=? "+
				"where word = ?";
	
		System.out.println("Ready to update");
		pStmt = con.prepareStatement(sql);

		pStmt.setString(1, updtWord.getWordType());
		pStmt.setString(2, updtWord.getWordDef());
		pStmt.setString(3, updtWord.getWord());
		pStmt.execute();
		
		String sql2 = "update dictionary.flashcards "+" set FDefinition=? where fWordNum =?";
		
		searchWordNum= WordDef.getWordNum();
		pStmt2 = con.prepareStatement(sql2);
		
		pStmt2.setString(1, updtWord.getWordDef());
		pStmt2.setInt(2, searchWordNum);
		
		pStmt2.execute();
		
		System.out.println("Updated the new values "+ searchWordNum +" "+updtWord.getWordDef());
		closeConnection();
	}

	public void deleteWord(String delWord) throws Exception {
		
		System.out.println("delete entered");
		con = dataSource.getConnection();
		String sql = "delete from dictionary.searchengine"+
				" where word = ?";

		pStmt = con.prepareStatement(sql);

		pStmt.setString(1, delWord);
		
		pStmt.execute();
		System.out.println("Deleted the new values");
		closeConnection();

	}
	
	private  void closeConnection() throws SQLException {
		if(rs != null){
			rs.close();
		}
		if(stmt != null){
			stmt.close();
		}

		if(pStmt != null){
			pStmt.close();
		}
		if(con != null){
			con.close();
		}
		System.out.println("Connection Closed...");

	}

}
