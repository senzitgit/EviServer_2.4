<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Evidencer | Login</title>
<script> 
var ws = new WebSocket("ws://159.8.42.34:8080/ServePlusServer/socket/demouser");
</script>
</head>
<body>
Login From localhost
<form action="http://127.0.0.1:8080/Evidencer/evidencer/login" method="POST">
 User Name: <input type="text" name="userName">
 <br />
 Password: <input type="text" name="password">
 <br /><input type="submit" value="Submit" /></form>
<br>
Login From other
<form action="http://192.168.0.108:8080/Evidencer/evidencer/login" method="POST">
 User Name: <input type="text" name="userName">
 <br />
 Password: <input type="text" name="password">
 <br /><input type="submit" value="Submit" /></form>
<br>
</body>
</html>