package com.Dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.Dictionary.model.FlashCard;
import com.Dictionary.model.WordDef;

public class FlashCardDBUtil {


	private DataSource dataSource;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pStmt= null;
	ResultSet rs = null;
	WordDef tempWord;

	public FlashCardDBUtil(DataSource theDataSource) {
		this.dataSource = theDataSource;
	}

	public  List<FlashCard> getFlashList() throws SQLException{

		List<FlashCard> flashlist = new ArrayList<>();
		con = dataSource.getConnection();
		String sql = "select * from dictionary.flashcards where userId = ?";

		pStmt = con.prepareStatement(sql);	
		pStmt.setInt(1, AccesDBValidation.getUserId());
		rs = pStmt.executeQuery();

		while(rs.next()){

			int temp_flashCardId=rs.getInt("flashCardId");
			String temp_flashword=rs.getString("flashword");
			String temp_fDefinition=rs.getString("fDefinition");
			int temp_fwordNUm = rs.getInt("fwordNUm");

			FlashCard tempflashcard=new FlashCard(temp_flashCardId, temp_flashword, temp_fDefinition, temp_fwordNUm);

			flashlist.add(tempflashcard);
		}
		con.close();
		return flashlist;


	}

	//this will work if we enable the delete button after generating the flashcards
	public String deleteFlashcard(String word) throws SQLException{
		String dWord = word;
		System.out.println(word);
		con =dataSource.getConnection();
		String sql = "DELETE from  dictionary.flashcards where flashword= ? and userId =?";
		pStmt = con.prepareStatement(sql);

		pStmt.setString(1, dWord);
		pStmt.setInt(2, AccesDBValidation.getUserId());
		int status = pStmt.executeUpdate();
		System.out.println(status);
		if(status != 0){
			closeConnection();
			return dWord +"- FlashCard deleted";
		}
		else{
			closeConnection();
			return "FlashCard not found";

		}
	} 



	public String addtoflashCard(WordDef word_Details) throws SQLException {

		if(word_Details != null){
			WordDef addWord = word_Details;
			String fword= addWord.getWord();
			String fwordDef= addWord.getWordDef();
			con = dataSource.getConnection();
			ResultSet rs1= null;
			PreparedStatement pStmt1 = con.prepareStatement("SELECT * FROM dictionary.flashcards where flashword=? and userId =?");
			pStmt1.setString(1, fword);
			pStmt1.setInt(2, AccesDBValidation.getUserId());
			rs1 = pStmt1.executeQuery();

			System.out.println(WordDef.getWordNum());
			System.out.println(AccesDBValidation.getUserId());

			if(!rs1.next()){
				System.out.println("Inseting into table");
				String sql = "insert into dictionary.flashcards (flashword, FDefinition, FwordNum, UserId) values (?,?,?,?)";
				pStmt = con.prepareStatement(sql);

				pStmt.setString(1, fword);
				pStmt.setString(2, fwordDef);
				pStmt.setInt(3, WordDef.getWordNum());
				pStmt.setInt(4, AccesDBValidation.getUserId());
				pStmt.executeUpdate();
				System.out.println("Word added to flashcards");
				rs1.close();
				pStmt1.close();
				closeConnection();
				return "Added to flashcards";
			}else{
				System.out.println("Word already exists in the FlashCards");
				closeConnection();
				return "Word already exists in the FlashCards";
			}
		}
		else{
			System.out.println("Please enter the word");

			return "Please enter the word";
		}

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
