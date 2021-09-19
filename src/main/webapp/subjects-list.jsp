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
				<h3>Subjects</h3>
			</div>
		</div>


		<div id="container">

			<div id="content">

				<table>

					<tr>
         
                        <th>ID</th>
						<th>Name</th>
						<th>Shortcut</th>
						

					</tr>

					<c:forEach var="tempSubject" items="${SUBJECTS_LIST }">
						<tr>

							<td>${tempSubject.id}</td>
							<td>${tempSubject.name}</td>
							<td>${tempSubject.shortcut}</td>
							

						</tr>


					</c:forEach>

				</table>
			</div>
		</div>
	</div>
	<form action="AdminControllerServlet" method="POST">  
        <div class="container">   
        	<input type="hidden" name="command" value="subjectsAction" />
            <label>ID : </label>   
            <br/>
            <input type="text" placeholder="Enter ID" name="id" style="border:1px solid black; border-radius:2.5px" required>  
            <br/>
            <label>Name : </label>   
            <br/>
            <input type="text" placeholder="Enter Subject Name" name="name" style="border:1px solid black; border-radius:2.5px">  
            <br/>
            <label>Shortcut : </label>   
            <br/>
            <input type="text" placeholder="Enter Subject Shortcut" name="shortcut" style="border:1px solid black; border-radius:2.5px">
            <br/>    
            <button type="submit" name="add" value="Add">Add</button>
            <button type="submit" name="delete" value="Delete">Delete</button>
            <button type="submit" name="update" value="Update">Update</button>    
             
        </div>   
    </form>

</body>
</html>