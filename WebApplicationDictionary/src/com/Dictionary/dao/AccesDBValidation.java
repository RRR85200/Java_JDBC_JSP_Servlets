package com.Dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AccesDBValidation {

	private DataSource dbSource;
	PreparedStatement pStmt;
	Connection con;
	ResultSet rs;
	static boolean access;
	private static boolean accessType;
	private static int id;


	public AccesDBValidation(DataSource dbSource) {
		super();
		this.dbSource = dbSource;
	}



	public boolean validateUser(String name, String pass) throws SQLException, ClassNotFoundException{
		System.out.println("Establishing connection......");
		con = dbSource.getConnection();
		System.out.println("Connection successful");
		pStmt = con.prepareStatement("SELECT * FROM dictionary.usertable where UserName=? and password =?");
		pStmt.setString(1, name);
		pStmt.setString(2, pass);
		rs = pStmt.executeQuery();

		if(rs.next()){
			accessType= rs.getBoolean("AdminAccess");
			id =rs.getInt("UserID");
			access = true;
			closeConnection();
			return access;

		}
		else {
			closeConnection();
			access = false;
			return access;
		}
	}


	public  boolean adminAccess(){

		return  accessType;
	}

	public static int getUserId(){

		return id;
	}

	private  void closeConnection() throws SQLException {
		if(rs != null){
			rs.close();
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
