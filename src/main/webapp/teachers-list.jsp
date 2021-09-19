<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Teachers</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
	<div id="page">
		<jsp:include page="left-list.jsp" />


		<div id="wrapper">

			<div id="header">
				<h3>Teachers</h3>
			</div>
		</div>


		<div id="container">

			<div id="content">

				<table>

					<tr>

						<th>ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Age</th>

					</tr>

					<c:forEach var="tempStudent" items="${TEACHERS_LIST }">
						<tr>

                            <td>${tempStudent.id}</td>
							<td>${tempStudent.fname}</td>
							<td>${tempStudent.lname}</td>
							<td>${tempStudent.age}</td>



						</tr>


					</c:forEach>

				</table>
			</div>
		</div>
	</div>
	
	<form action="AdminControllerServlet" method="POST">  
        <div class="container">   
        	<input type="hidden" name="command" value="teachersAction" />
            <label>ID : </label>   
            <br/>
            <input type="text" placeholder="Enter ID" name="id" style="border:1px solid black; border-radius:2.5px" required>  
            <br/>
            <label>First Name : </label>   
            <br/>
            <input type="text" placeholder="Enter First Name" name="fname" style="border:1px solid black; border-radius:2.5px">  
            <br/>
            <label>Last Name : </label>   
            <br/>
            <input type="text" placeholder="Enter Last Name" name="lname" style="border:1px solid black; border-radius:2.5px">  
            <br/>
            <label>Age : </label>   
            <br/>
            <input type="text" placeholder="Enter Age" name="age" style="border:1px solid black; border-radius:2.5px">
             <br/>  
            <button type="submit" name="add" value="Add">Add</button>
            <button type="submit" name="delete" value="Delete">Delete</button>
            <button type="submit" name="update" value="Update">Update</button>    
             
        </div>   
    </form>

</body>
</html>