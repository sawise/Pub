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
 <p>Different ResponseStatus: 200 = OK, 400 = Bad Request, 401 = Not Authorized, 404 = Not Found, 500 = Internal Server Error</p>
 <br>
 <p>Get all Ingredients:</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/all</p>
 <br>
 <p>Get single ingredient (with id):</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/1</p>
 <br>
 <p>Get all Drinks: (to be used later to list all drinks without ingredients. right now ingredients are included)</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/all</p>
 <br>
 <p>Get single Drink with id:(Ingredients included)</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/6</p>
 <br>
 <p>Get list of categories:</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/categories/all</p>
 <br>
 <p>Get ingredients that belong to this category (with category id):</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/category/8</p>
 <br>
 <br>
 <p>Get a list of drinks that a certain user have ingredients to make (with user id):</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/1</p>
 <br>
 <br>
 <p>This is used to set rating up +1 for Drink with id 4, response will be 200 ("Drink rating updated!") if ok.</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/4/ratingup</p>
 <p>Header should be set to:</p>
 <p>Method = PUT</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Content-type: application/json</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X PUT http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/4/ratingup</p>
 <br>
 <br>
 <p>This is used to set rating down +1 for Drink with id 4, response will be 200 ("Drink rating updated!") if ok.</p>
 <p>http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/4/ratingdown</p>
 <p>Header should be set to:</p>
 <p>Method = PUT</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X PUT http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/4/ratingdown</p>
 <br>
 <br>
 <p>Login system: When sign up is done, login is done with email and password. When logging in with email and password a new identifier is created and returned (to be stored on device).</p> 
 <p>The identifier is then used for login/add and remove ingredients for that user.</p>
 <p>When a login with the identifier is done a new identifier is created and returned (to be stored on device).</p>
 <p>This is done so that a temporary identifier only will be used as login credentials once (when stating app).</p>
 <p>If user is logged out or login from another device the identifier will be invalid and force the user to login using email and password.</p>
 <br>
 <br>
 <p>Sending a json object as such: {"username":"svempa","email":"svenne87@gmail.com","password":"svempa"} to http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/new</p>
 <p>Will create a new user. If user is created, 200 is returned (with message). If email is in use 400 will be returned, If server error 500 will be returned. Password is sent in clear text should be over SSL!.</p>
  <p>Header should be set to:</p>
 <p>Method = POST</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"username":"svempa","email":"svenne87@gmail.com","password":"svempa"}' http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/new</p>
 <br>
 <br>
 <p>Sending a json object as such: {"email":"svenne87@gmail.com","password":"svempa"} to http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/login</p>
 <p>Will login a user. If user is logged in, 200 is returned (with message, id, identifier and username). If wrong credentials 401 will be returned. Else it will return 400 = bad request, password is sent in clear text should be over SSL!.</p>
 <p>Header should be set to:</p>
 <p>Method = POST</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"email":"svenne87@gmail.com","password":"svempa"}' http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/login</p>
 <br>
 <br>
 <p>Sending a json object as such: {"email":"svenne87@gmail.com","identifier":"e4fa63c2662a4bd380e42a21699826a8"} to http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/identifierlogin</p>
 <p>Will login a user using a identifier. If user is logged in, 200 is returned (with message, id, identifier and username). If wrong credentials 401 will be returned. Else it will return 400 = bad request, a new identifier is generate and returned with this request.</p>
 <p>So the identifier is only valid for one login.</p>
 <p>Header should be set to:</p>
 <p>Method = POST</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"email":"svenne87@gmail.com","identifier":"e4fa63c2662a4bd380e42a21699826a8"}' http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/identifierlogin</p>
 <br>
 <br>
 <p>Sending a json object as such: {"email":"svenne87@gmail.com","identifier":"e4fa63c2662a4bd380e42a21699826a8"} to http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/logout</p>
 <p>Will logout a new user. If user is logged out, 200 is returned (with message and username). If wrong credentials 401 will be returned. Else it will return 400 = bad request, identifier is sent in clear text should be over SSL!.</p>
 <p>When logout is successful the identifier is destroyed, and user will have to login using email and password credentials.</p>
 <p>Header should be set to:</p>
 <p>Method = POST</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"email":"svenne87@gmail.com","identifier":"e4fa63c2662a4bd380e42a21699826a8"}' http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/logout</p>
 <br>
 <br>
 <p>Sending a json object as such: {"id":"2","identifier":"e4fa63c2662a4bd380e42a21699826a8"} to http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/add/ingredient/2</p>
 <p>With ingredient id as url parameter, if the ingredient is added and login with identifier is successful. 200 is returned (with message). If wrong credentials 401 is returned and if server error then 500 is returned. Else it will return 400 = bad request.</p>
 <p>Header should be set to:</p>
 <p>Method = POST</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"id":"2","identifier":"e4fa63c2662a4bd380e42a21699826a8"}' http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/add/ingredient/2</p>
 <br>
 <br>
 <p>Sending a json object as such: {"id":"2","identifier":"e4fa63c2662a4bd380e42a21699826a8"} to http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/remove/ingredient/2</p>
 <p>With ingredient id as url parameter, if the ingredient is removed and login with identifier is successful. 200 is returned (with message). If wrong credentials 401 is returned and if server error then 500 is returned. Else it will return 400 = bad request.</p>
 <p>Header should be set to:</p>
 <p>Method = POST</p>
 <p>Accept: application/json</p>
 <p>Authorization: apikey='1c9fk3u35ldcefgw'</p>
 <p>Try ut out with curl (Linux/Mac):</p>
 <p>curl -i -H "Accept: application/json" -H "Authorization: apikey='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"id":"2","identifier":"e4fa63c2662a4bd380e42a21699826a8"}' http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/remove/ingredient/2</p>
 <br>
 <br>
 <p>More will come :)</p>
 <br>
 <br>
 <a href="<c:url value="/logout" />" > Logout</a>
</body>
</html>
