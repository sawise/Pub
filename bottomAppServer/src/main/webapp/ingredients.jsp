<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
	<title>BottomApp</title>
	<style>body {margin-left:20px;}</style>
</head>
<body>
	<h1>All Ingredients</h1>
    <br>
    <a href="<c:url value='/add_ingredient'/>">New Ingredient</a> 
    <br>
    <br>
    <table>
    	<th>Name</th>
	    <c:forEach items="${ingredients}" var="ingredient" >
	   	    <tr>
	    		<td><c:out value="${ingredient.getName()}"/></td> 
	    	    <td><form:form method="GET" action="/bottomAppServer/edit_ingredient/${ingredient.getId()}"><input type="submit" value="Edit"></form:form></td>
	    		<td><form:form method="DELETE" action="/bottomAppServer/ingredient/${ingredient.getId()}"><input type="submit" value="Delete" onClick="return confirm('Are you sure?')"></form:form></td>
	    	</tr>
		</c:forEach>	
	</table>
	<br>
	<a href="<c:url value='/'/>">Back</a> 
</body>
</html>