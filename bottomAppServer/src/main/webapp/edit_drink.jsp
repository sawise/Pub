<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<title>BottomApp</title>
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
	<h1>Edit this Drink</h1>
    <br>
    <h2>Edit: ${drink.getName()}</h2>
    <c:url var="editUrl" value="/edit_drink"/>
   
    <form:form method="PUT" action="${editUrl}" commandName="drink">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr><td>Name:</td>
    			<td><form:input path="name" value="" /></td>
    			<td><form:errors path="name" cssClass="error" /></td>
    		</tr>
    		
    		<tr>
    			<td>Description:</td>
    			<td><form:textarea path="description" value="" /></td>
    			<td><form:errors path="description" cssClass="error" /></td>
    		</tr>
    		
    		<tr>
    			<td>Rating Up:</td>
    			<td><form:input path="ratingUp" value="" /></td>
    			<td><form:errors path="ratingUp" cssClass="error" /></td>
    		</tr>
    		
    		<tr>
    			<td>Rating Down:</td>
    			<td><form:input path="ratingDown" value="" /></td>
    			<td><form:errors path="ratingDown" cssClass="error" /></td>
    		</tr>
    	    <tr>
    	    	<td>Ingredients:</td>
    	    </tr>
    	    
    	    <c:forEach var="ingredient" items="${drink.ingredients}">
          	<tr>
          		<td>${ingredient.name}</td>
          	    <td>${ingredient.measurement}</td>
           </tr>
  		  </c:forEach>
  		    
    	</table>
    	<form:hidden path="id" value="" />
    	<br>
    	<input type="submit" value="Edit">
    </form:form>	
    
	<br>
	<a href="<c:url value='/drinks'/>">Back</a> 
</body>
</html>