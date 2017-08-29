package com.Dictionary.Controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.Dictionary.dao.WordDefDBUtil;
import com.Dictionary.model.WordDef;


/* AdminControllerServlet controls the form action to 
 * - Get list from Database
 * - Update/Load the words from database
 * - Delete the words from database
 * These above functions are linked to DAO which is further linked to database
 * */

@WebServlet("/AdminControllerServlet")
public class AdminControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*mapping this to Context.xml which has all the JDBC connection and handling max & min users
	 *  context.xml contains a JNDI(Java Naming and Directory Interface) Resource, of the type javax.sql.DataSource.
	 */
	@Resource(name="jdbc/WebApplicationDictionary")

	private DataSource dataSource;
	private WordDefDBUtil wordDefDB;

	/*inIt method is started first when the controller is invoked
	 * The init method must complete successfully before the servlet can receive any request
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		try{

	/*Instantiate WordDefDBUtil to establish connection before taking in the appropriate method
	* */			
			wordDefDB = new WordDefDBUtil(dataSource);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}
	
	/*LOAD of update words and Delete  are HREF's and cannot be called through doPOST hence they 
	 * are called in doGET... Default action of doGET is to ListWords 
	 * */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//Get the required Action
			String actionToDo = request.getParameter("FormAction");
			System.out.println("About to get list");

			if(actionToDo == null){
				actionToDo="ListWords";

			}
			//route to appropriate method
			switch(actionToDo){

			case "LOAD":
				loadWord(request,response);
				break;

			case "Delete":
				deleteWord(request,response);
				break;

			default:
				listWords(request, response);


			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* doPOST is used for the below FormActions 
	 * ListWords, InsertWord, UpdateWord
	 * */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			//Get the required Action
			String actionToDo = request.getParameter("FormAction");
	
			if(actionToDo == null){
				actionToDo="ListWords";
			}

			//route to appropriate method
			switch(actionToDo){

			case "ListWords":

				listWords(request, response);
				break;

			case "InsertWord":
				insertWord(request,response);
				break;

			case "UpdateWord":
				updateWord(request,response);
				break;

			default:
				listWords(request, response);

			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void listWords(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Inside <listWords> invoking method to get List of words");
		
		List<WordDef> listsOfWords = wordDefDB.getWordList();
		request.setAttribute("word_list", listsOfWords);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list_words_for_Admin.jsp");
		dispatcher.forward(request, response);
		//send to jsp

	}
	
	private void insertWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		System.out.println("Inside <insertWord> invoking method to insert new word");
		
		PrintWriter out = response.getWriter();
		//read info from form
		System.out.println("Inside to insert Word");
		String word = request.getParameter("word");
		String wordType= request.getParameter("wordType");
		String wordDef = request.getParameter("Defintion");
		
		WordDef theWord = wordDefDB.getWordDetails(word);
		if(theWord != null){
			out.print("<p style=\"color:red\">Word already in database</p>");  
			RequestDispatcher rd=request.getRequestDispatcher("InsertWord.jsp");  
			rd.include(request,response);  
		}
		else{
		//read new wordDef object
		WordDef newWord =  new WordDef();
		newWord.setWord(word);
		newWord.setWordDef(wordDef);
		newWord.setWordType(wordType);
		//add to DB
		wordDefDB.insertWord(newWord);
		//Send back to List page
		listWords(request, response);
		}
	}

	private void loadWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("Inside <loadWord> invoking method to load update word data");
		//read word
		String word = request.getParameter("word");

		//get data from database

		WordDef theWord = wordDefDB.getWordDetails(word);
		request.setAttribute("theWord", theWord);
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Update_word_Details.jsp");
		dispatcher.forward(request, response);

	}


	private void updateWord(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		
		System.out.println("Inside <updateWord> invoking method to update word data");
		//Read info from form data
		String upWord = request.getParameter("word");
		String upWordType = request.getParameter("wordType");
		String upWordDef = request.getParameter("Defintion");
		//create an object

		WordDef updtWord = new WordDef();
		updtWord.setWord(upWord);
		updtWord.setWordDef(upWordDef);
		updtWord.setWordType(upWordType);

		//Perform update
		wordDefDB.updateWord(updtWord);

		//Send them back to list of words
		listWords(request, response);
	}
	

	private void deleteWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		System.out.println("Inside <deleteWord> invoking method to delete word data");
		
		String delWord = request.getParameter("word");
		wordDefDB.deleteWord(delWord);
		listWords(request, response);
	}

}
