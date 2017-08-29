<%@page import="com.Dictionary.*"%>
<html>
<head>

<title>Dictionary</title>
</head>
<body>

	<h1 align="center">Welcome to Word Search Engine</h1>
	<h3 align="center">Please Login to continue</h3>
	<form action="LoginControllerServlet" method="post">


		<table>

			<tr>User ID
			</tr>
			<tr>:
			</tr>

			<input type="text" name="userId">
			<tr>Password
			</tr>
			<tr>:
			</tr>
			<input type="password" name="passWord">
			<br />
			<br />
			<input type="submit" value="Login">

		</table>

	</form>
</body>
</html>