<%@page import="com.Dictionary.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<title>Welcome <%=session.getAttribute("name")%></title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

<div id= "page">
	<h3>Welcome to	<%=session.getAttribute("name")%> page</h3>

	<br />
	<br />
	<br />
	<form action="UserControllerServlet" method="post">
		<label for="word">Enter your word</label>
		 <input type="text"	name="word" title="EnterYourWordHere" />
		 <input type="checkbox" name="ButtonAction" value="ShowQuotes"/> Get Quotes
		 <input type="submit" name="ButtonAction" value="SearchDefinition" /> <br />
		<textarea rows="1" cols="25" disabled> ${word_Details.word}${temQuote.word}${word_Searched.word}</textarea>
		<br />
		<textarea rows="4" cols="80" disabled> ${word_Details.wordDef}${temQuote.wordDef}${word_Searched.wordDef} 
		
		${System_message}
 </textarea>
		<br /> 
			<input type="submit" name="ButtonAction" value="AddToFlashcard">
			<input type="submit" name="ButtonAction" value="ShowFlashCards" />		
			
	</form>
	<table>
		<c:forEach var="tempWord" items="${flashword_list}">

			<c:url var="DefinitionLink" value="UserControllerServlet">
				<c:param name="FormAction" value="ShowFlashDef" />
				<c:param name="word" value="${tempWord.flashword}" />
			</c:url>
			<c:url var="Deletelink" value="UserControllerServlet">
				<c:param name="FormAction" value="DeleteFlashCard" />
				<c:param name="word" value="${tempWord.flashword}" />
			</c:url>

			<tr>
				<td>${tempWord.flashword}</td>
				<td><a href="${DefinitionLink}">Definition</a></td>
				<td><a href="${Deletelink}"> Delete</a></td>
			</tr>

		</c:forEach>
	</table>
</div>
	<table border=1>
		<c:forEach var="temQuote" items="${QuoteList}">

			<tr>
				<td>${temQuote.quote}</td>
				<td>${temQuote.author}</td>
				
			</tr>

		</c:forEach>
		
		</table>
		


</body>
</html>