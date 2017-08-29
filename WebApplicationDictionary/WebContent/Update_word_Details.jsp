<!DOCTYPE html>
<html>
<head>
<title>Update word</title>
</head>
<body>
	<div id="wrapper">
		<div id="header">

			<h2>Admin Dictionary</h2>
		</div>
	</div>


	<div id="container">
		<h3>Update Word</h3>
		
		<form action="AdminControllerServlet" method="post">

			<input type="hidden" name="FormAction" value="UpdateWord" />
			<table>
				<tbody>
					<tr>
						<td><label>Enter Word:</label></td>
						<td><input type="text" name="word" value="${theWord.word}" /></td>
					</tr>
					<tr>
						<td><label>Enter Word Type:</label></td>
						<td><input type="text" name="wordType"
							value="${theWord.wordType}" /></td>
					</tr>
					<tr>
						<td><label>Enter Word Definition:</label></td>
						<td><input type="text" name="Defintion"
							value="${theWord.wordDef}" /></td>
					</tr>

					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Update" /></td>
					</tr>

				</tbody>

			</table>

		</form>


		<p>
			<a href="AdminControllerServlet">Go back to word List</a>
		</p>


	</div>
</body>
</html>