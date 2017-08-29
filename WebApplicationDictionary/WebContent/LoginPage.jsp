<%@page import="com.Dictionary.*"%>
<html>
<head>

<title>Login Dictionary</title>
</head>
<body>

	<h1 align="center">Welcome to Word Search Engine</h1>
	<h3 align="center">Please Login to continue</h3>
	<form action="LoginControllerServlet" method="post" >


		<table>

			<tr><td>User ID</td></tr>
			<tr><td><input type="text" name="userId"/></td></tr>
			<tr> <td>Password</td> </tr>
			<tr><td><input type="password" name="passWord"/></td></tr>
			<tr><td><input type="submit" value="Login" /></td></tr>

		</table>

	</form>
</body>
</html>