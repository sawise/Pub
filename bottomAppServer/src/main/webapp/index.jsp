<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
	<title>BottomApp</title>
	<style>body {margin-left:20px;}</style>
</head>
<body>
<h1>BottomApp Backend</h1>
<br>
<br>
 <c:url value="/categories" var="categoriesUrl"/>
 <a href="<c:out value='${categoriesUrl}'/>">Categories</a>
<br>
 <c:url value="/ingredients" var="ingredientsUrl"/>
 <a href="<c:out value='${ingredientsUrl}'/>">Ingredients</a>
 <br>
 <c:url value="/drinks" var="drinksUrl"/>
 <a href="<c:out value='${drinksUrl}'/>">Drinks</a>
 <br>
 <br>
 <br>
 <h2>API Calling:</h2>
 <br>
 <p>Get all Ingredients:</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/all</p>
 <br>
 <p>Get single ingredient (with id):</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/1</p>
 <br>
 <p>Get all Drinks: (to be used later to list all drinks without ingredients. right now ingredients are included)</p>
 <p>http://dev2-vyh.softwerk.se:8080/json/drinks/all</p>
 <br>
 <p>Get single Drink with id:(Ingredients included)</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/6</p>
  <br>
 <p>Get list of categories:</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/categories/all</p>
 <br>
 <p>Get ingredients that belong to this category (with category id):</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/category/8</p>
 <br>
 <p>Exceptions not handled just yet, will return proper error code later</p>
 <p>Headers for requests are not set either</p>
 <p>More will come :)</p>
 <br>
 <br>
 <a href="<c:url value="/logout" />" > Logout</a>
</body>
</html>
