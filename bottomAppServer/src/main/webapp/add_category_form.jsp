<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>BottomApp</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<style>body {margin-left:20px;}</style>
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<h1>Add Category</h1>
	<br>
    <c:url var="addUrl" value="/add_category"/>	
    <form:form method="POST" action="${editUrl}" commandName="category">
		<form:errors path="*" cssClass="errorblock" element="div" />    	
		<table>
			<tr>
				<td>Category Name :</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" cssClass="error" /></td>
			</tr>
		</table>
    	<form:hidden path="id" value="" />
    	<input type="submit" value="Submit">
    </form:form>	
	<br>
	<a href="<c:url value='/categories'/>">Back</a> 
</body>
</html>