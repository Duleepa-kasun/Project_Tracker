<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href='http://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
<style type="text/css">
table, td, th
{
border:1px solid green;
font-family: 'Oxygen', sans-serif;
}
th
{
background-color:green;
color:white;
}
body
{
 text-align: center;
}
.container
{
 margin-left: auto;
 margin-right: auto;
 width: 40em;
}
</style>
</head>
<body>

<input type="button" value="Show Table" id="showTable"/>
<div id="tablediv">
<table cellspacing="0" id="countrytable"> 
    <tr> 
        <th scope="col">Code</th> 
        <th scope="col">Name</th> 
        <th scope="col">Continent</th> 
        <th scope="col">Region</th> 
        <th scope="col">Population</th> 
        <th scope="col">Capital</th>          
    </tr> 
</table>
</div>


</body>
</html>