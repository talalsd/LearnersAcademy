<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Classes</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
	<div id="page">
		<jsp:include page="left-list.jsp" />


		<div id="wrapper">

			<div id="header">
				<h3>Classes</h3>
			</div>
		</div>


		<div id="container">

			<div id="content">

				<table>

					<tr>

						<th>ID</th>
						<th>Section</th>
						<th>Subject</th>
						<th>Teacher</th>
						<th>Time</th>
						<!-- <th>List of Students</th> -->

					</tr>

					<c:forEach var="tempClass" items="${CLASSES_LIST }">
						<tr>

							<c:url var="tempLink" value="AdminControllerServlet">
								<c:param name="command" value="ST_LIST" />
								<c:param name="classId" value="${tempClass.id }" />
								<c:param name="section" value="${tempClass.section }" />
								<c:param name="subject" value="${tempClass.subject }" />
							</c:url>

							<td>${tempClass.id}</td>
							<td>${tempClass.section}</td>
							<td>${tempClass.subject}</td>
							<td>${tempClass.teacher}</td>
							<td>${tempClass.time}</td>
							<%-- <td><a href="${tempLink }">List</a></td> --%>
 




						</tr>


					</c:forEach>

				</table>
			</div>
		</div>
	</div>
	
	<form action="AdminControllerServlet" method="POST">  
        <div class="container">   
        	<input type="hidden" name="command" value="classAction" />
            <label>ID : </label>   
            <br/>
            <input type="text" placeholder="Enter ID" name="id" style="border:1px solid black; border-radius:2.5px" required>  
            <br/>
            <label>Section : </label>   
            <br/>
            <input type="text" placeholder="Enter Section #" name="section" style="border:1px solid black; border-radius:2.5px">  
            <br/>
            <label>Teacher : </label>   
            <br/>
            <input type="text" placeholder="Enter Teacher ID" name="teacher" style="border:1px solid black; border-radius:2.5px">  
            <br/>
            <label>Subject : </label>   
            <br/>
            <input type="text" placeholder="Enter Subject ID" name="subject" style="border:1px solid black; border-radius:2.5px">
            <br/>
            <label>Time : </label>   
            <br/>
            <input type="text" placeholder="Enter Class Time" name="time" style="border:1px solid black; border-radius:2.5px">
            <br/>  
            <button type="submit" name="add" value="Add">Add</button>
            <button type="submit" name="delete" value="Delete">Delete</button>
            <button type="submit" name="update" value="Update">Update</button>    
             
        </div>   
    </form>

</body>
</html>