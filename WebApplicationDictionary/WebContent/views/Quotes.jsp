<%@page import="com.Dictionary.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<title>Quotes</title>
</head>
<body>

	<h1 align="center">Welcome to Quote Engine</h1>
	
	<form action="QuoteControllerServlet" method="get">

			<input type="text" name="word"/>
			<input type="submit" value="getQuotes">

	</form>
	
	<table>
		<c:forEach var="temQuote" items="${QuoteList}">

	

			<tr>
				<td>${temQuote.quote}</td>
				<td>${temQuote.author}</td>
				
			</tr>

		</c:forEach>
		
		</table>
</body>
</html>