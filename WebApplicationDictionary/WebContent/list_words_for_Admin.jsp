<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>

<title>Admin Word List</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">

			<h2>Admin Dictionary Word List</h2>
		</div>
	</div>


	<div id="container">
		<div id="content">

			<!--  Add Button -->
			<div class="navigation">
				<input type="button" value="Add new Word"
					onclick="window.location.href= 'InsertWord.jsp'; return false;" />
				<a href="word_search_engine.jsp">View as User</a>

			</div>
			<table border=1>
				<tr>
					<th>Word</th>
					<th>WordType</th>
					<th>Definition</th>
					<th>Action</th>
					<th></th>
				</tr>

				<c:forEach var="tempWord" items="${word_list}">

					<!--  Set up a load/update link for each word -->
					<c:url var="updatelink" value="AdminControllerServlet">
						<c:param name="FormAction" value="LOAD" />
						<c:param name="word" value="${tempWord.word}" />
					</c:url>

					<!--  Set up a delete link for each word -->
					<c:url var="Deletelink" value="AdminControllerServlet">
						<c:param name="FormAction" value="Delete" />
						<c:param name="word" value="${tempWord.word}" />
					</c:url>



					<tr>
						<td>${tempWord.word}</td>
						<td>${tempWord.wordType}</td>
						<td>${tempWord.wordDef}</td>
						<td><a href="${updatelink}">update</a></td>
						<td><a href="${Deletelink}"> Delete</a></td>
					</tr>

				</c:forEach>

			</table>

		</div>
	</div>

</body>
</html>