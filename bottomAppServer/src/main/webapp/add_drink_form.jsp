<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
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
	<h1>Add Drink</h1>
	<br>
    <form method="POST" action="" id="drink">
		<table>
			<tr><td>Name:</td>
				<td><input type="text" name="name" id="name"/></td>
    		</tr>
    		
    		<tr>
    			<td>Description:</td>
  				<td><textarea name="description" id="description"></textarea></td>
    		</tr>
    		
    		<tr>
				<td><input type="hidden" name="ratingUp" id="ratingUp" value="0"/></td>
    		</tr>
    		
    		<tr>
				<td><input type="hidden" name="ratingDown" id="ratingDown" value="0"/></td>
    		</tr>
    		
    		<tr><td>Ingredients</td></tr>
    			<c:forEach var="curr" varStatus="status" begin="0" end="10" step="1">
    			<tr>
	    			<td>
	    			<form:select path="ingredients" multiple="false" name="ingredients[${status.count}].id" id="indredients[${status.count}].id">
		    			<form:option value="0" label="Select"></form:option>
		    			<c:forEach var="ingredient" items="${ingredients}">
		      				<form:option value="${ingredient.id}" label="${ingredient.name}"></form:option>
						</c:forEach>
					</form:select>
	    			</td>
	    			<td><input type="text" name="ingredients[${status.count}].measurement" id="ingredients[${status.count}].measurement" /></td>
	    		</tr>
    			</c:forEach>
    	</table>
    	<input type="submit" value="Submit">
    </form>	
    <br>
   	<div id="response" class="green"> </div>
    <br>
	<a href="<c:url value='/drinks'/>">Back</a> 

	<script type="text/javascript">
   
    $(document).ready(function() {
     
      // Save Drink AJAX Form Submit
      $('#drink').submit(function(e) {
    	  $("#response").text("");
        // will pass the form data using the jQuery serialize function
        $.post('${pageContext.request.contextPath}/add_drink', $(this).serialize(), function(response) {
		  
          // clear values
          $(':input','#drink')
 			.not(':button, :submit, :reset, :hidden')
 			.val('')
 			.removeAttr('selected');
        	
          $('#response').text(response);
        });
         
        e.preventDefault(); // prevent actual form submit and page reload
      });
       
    });
   
  </script>
</body>
</html>

