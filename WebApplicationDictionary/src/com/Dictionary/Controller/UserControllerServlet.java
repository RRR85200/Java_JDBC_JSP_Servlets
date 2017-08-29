package com.Dictionary.Controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.Dictionary.dao.FlashCardDBUtil;
import com.Dictionary.dao.QuotesFromBriany;
import com.Dictionary.dao.WordDefDBUtil;
import com.Dictionary.model.FlashCard;
import com.Dictionary.model.Quote;
import com.Dictionary.model.WordDef;

/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*mapping this to Context.xml which has all the JDBC connection and handling max & min users
	 *  context.xml contains a JNDI(Java Naming and Directory Interface) Resource, of the type javax.sql.DataSource.
	 */
	@Resource(name = "jdbc/WebApplicationDictionary")
	
	DataSource dataSource;
	WordDefDBUtil wordDefDBUtil;
	FlashCardDBUtil flashDB;
	WordDef word_Details;
	String word;
	QuotesFromBriany goGet = new QuotesFromBriany();
	List<Quote> listOfQuotes = new ArrayList<>();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			/*Instantiate WordDefDBUtil to establish connection before taking in the appropriate method
			 * */	
			flashDB = new FlashCardDBUtil(dataSource);
			wordDefDBUtil = new WordDefDBUtil(dataSource);


		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String actionNeeded = request.getParameter("FormAction");
		word = request.getParameter("word");
		System.out.println(actionNeeded);
		
		//deleteling a flash card  word
		if(actionNeeded.equals("DeleteFlashCard")){

			try {
				System.out.println("Routing from Delete switch case");
				deleteFlashCard(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			try {
				//to get the definition of the  word
				getWordDetails(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String actionNeeded = request.getParameter("ButtonAction");
		word = request.getParameter("word");

		switch (actionNeeded) {
		
		
		//searching the word details 

		case "SearchDefinition":
			try {
				getWordDetails(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
				//adding the word to flashcard
		case "AddToFlashcard":
			try {
				addtoflashcard(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
				//diplaying flashcards
		case "ShowFlashCards":
			try {
				generateFlashCards(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

				//getting the quotes using the searched word
		case "ShowQuotes":
			System.out.println("Switching to ShowQuotes");
			try {
				getQuotesFromWeb(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		default: 
			try {
				//default action to display the word details 
				getWordDetails(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	private void getQuotesFromWeb(HttpServletRequest request, HttpServletResponse response) throws Exception {


		System.out.println("I am in getQuoteFromWeb");	
		String word = request.getParameter("word");

		if(word != null){

			String words =word;
			//calling the method from Quotes form Brainy DAO

			word_Details = wordDefDBUtil.getWordDetails(words);
			List<Quote> listOfQuotes=goGet.getQuotefor(word);
			request.setAttribute("QuoteList", listOfQuotes);
			request.setAttribute("word_Searched", word_Details);

		}



		System.out.println(listOfQuotes);
		//RequestDispatcher dispatcher = request.getRequestDispatcher("Quotes.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("word_search_engine.jsp");
		dispatcher.forward(request, response);

	}

	private void getWordDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String words = request.getParameter("word");


		System.out.println("I am in getWordDetails"+words);
		word_Details = wordDefDBUtil.getWordDetails(words);
		if(word_Details != null){

			request.setAttribute("word_Details", word_Details);

		}
		else{
			String message = "Word definition not found in Dictionary"; 
			request.setAttribute("System_message", message);
		}

		RequestDispatcher dispatch = request
				.getRequestDispatcher("word_search_engine.jsp");
		dispatch.forward(request, response);




	}


	private void addtoflashcard(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

		if(request.getParameter("word") != null){

			String message = flashDB.addtoflashCard(word_Details);
			//calling to flashcard add to flashcard method

			request.setAttribute("System_message", message);
			request.setAttribute("word_Searched", word_Details);
			RequestDispatcher dispatch = request.getRequestDispatcher("word_search_engine.jsp");
			dispatch.forward(request, response);

		}
		else{

			PrintWriter out = response.getWriter();

			out.print("<p style=\"color:red\">Word already in flashcards</p>");  
			RequestDispatcher rd=request.getRequestDispatcher("word_search_engine.jsp");  
			rd.include(request,response);  

		}


	}

	private List<FlashCard> generateFlashCards(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		System.out.println("Setting generation");
		List<FlashCard> flashlist = flashDB.getFlashList();

		request.setAttribute("flashword_list", flashlist);
		// add students to request
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("word_search_engine.jsp");
		dispatcher.forward(request, response);

		return flashlist;
	}


	private void deleteFlashCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String wordD = request.getParameter("word");
		String message= flashDB.deleteFlashcard(wordD);
		request.setAttribute("System_message", message);
		request.setAttribute("word_Searched", word_Details);
		generateFlashCards(request, response);
		/*RequestDispatcher dispatch = request.getRequestDispatcher("word_search_engine.jsp");
		dispatch.forward(request, response); */

	}

}
