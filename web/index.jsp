<%-- 
    Document   : index
    Created on : 01.11.2012, 20:01:57
    Author     : Павел
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' type='text/css' href='res/style.css'>
		<script type='text/javascript' src='res/store.js'></script>
		<script type='text/javascript' src='res/jquery.js'></script>
		<script type='text/javascript' src='res/jquery.hotkeys.js'></script>
		<script type='text/javascript' src='res/all.js'></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Java WEB Shell</title>
	</head>
	<body>
		<form onsubmit="execute(); return false;">
			<div>
				<div id="rows">
					<div id="results"></div>
					<div id="priv_indicator">></div>
					<input id="query" autocomplete="off" autofocus size="100">
				</div>
			</div>
		</form>
	</body>
</html>
