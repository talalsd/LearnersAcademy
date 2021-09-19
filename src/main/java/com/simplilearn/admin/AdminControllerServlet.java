package com.simplilearn.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

import com.simplilearn.models.Student;
import com.simplilearn.models.Subject;
import com.simplilearn.models.Teacher;
import com.simplilearn.models.Class;

/**
 * Servlet implementation class AdminControllerServlet
 */
@WebServlet("/AdminControllerServlet")
public class AdminControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DbRetrieve dbRetrieve;

	@Resource(name = "jdbc_database")
	private DataSource datasource;

	@Override
	public void init() throws ServletException {

		super.init();

		// create instance of db util, to pass in conn pool object
		try {
			dbRetrieve = new DbRetrieve(datasource);

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doGet(req, resp);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			// read the "command" parameter
			String command = request.getParameter("command");

			if (command == null) {
				command = "CLASSES";
			}
			
			// if no cookeies
			if (!getCookies(request, response) && (!command.equals("LOGIN"))) {

				response.sendRedirect("/Administrative-Portal/login.jsp");
			}

			else {

				// if there is no command, how to handle

				// route the data to the appropriate method
				switch (command) {

				case "classReport":
					
					classReport(request, response);
					break;
				
				case "STUDENTS":
					
					studentsList(request, response);
					break;

				case "TEACHERS":
					teachersList(request, response);
					break;

				case "SUBJECTS":
					subjectList(request, response);
					break;

				case "CLASSES":
					classesList(request, response);
					break;

				case "ST_LIST":
					classStudentsList(request, response);
					break;

				case "LOGIN":
					login(request, response);
					break;
					
				case "studentsAction":
					studentsAction(request, response);
					break;
					
				case "subjectsAction":
					subjectsAction(request, response);
					break;
					
				case "teachersAction":
					teachersAction(request, response);
					break;
					
				case "classAction":
					classAction(request, response);
					break;	
					
					

				default:
					classesList(request, response);

				}
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	private void classReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get subjects from db util
		List<Class> classes = dbRetrieve.getClasses();

		// add subjects to the request
		request.setAttribute("CLASSES_LIST", classes);

		// send it to the jSP view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/class-report.jsp");
		dispatcher.forward(request, response);

	}

	public void studentsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		// get students from db util
		List<Student> students = dbRetrieve.getStudents();

		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
		

//		 send it to the jsp view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		}catch(Exception e) {
			e.getMessage();
		}

	}

	private void teachersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students from db util
		List<Teacher> teachers = dbRetrieve.getTeachers();

		// add students to the request
		request.setAttribute("TEACHERS_LIST", teachers);

		// send it to the jSP view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/teachers-list.jsp");
		dispatcher.forward(request, response);

	}

	private void subjectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get subjects from db util
		List<Subject> subjects = dbRetrieve.getSubjects();

		// add subjects to the request
		request.setAttribute("SUBJECTS_LIST", subjects);

		// send it to the jSP view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/subjects-list.jsp");
		dispatcher.forward(request, response);

	}

	private void classesList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get subjects from db util
		List<Class> classes = dbRetrieve.getClasses();

		// add subjects to the request
		request.setAttribute("CLASSES_LIST", classes);

		// send it to the jSP view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/classes-list.jsp");
		dispatcher.forward(request, response);

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username.toLowerCase().equals("admin") && password.toLowerCase().equals("admin")) {

			Cookie cookie = new Cookie(username, password);
			

			// Setting the maximum age to 1 day
			cookie.setMaxAge(86400); // 86400 seconds in a day

			// Send the cookie to the client
			response.addCookie(cookie);
			classesList(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void classStudentsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
		int classId = Integer.parseInt(request.getParameter("classId"));
		String section = request.getParameter("section");
		String subject = request.getParameter("subject");

		// get subjects from db util
		List<Student> students = dbRetrieve.loadClassStudents(classId);

		// add subjects to the request
		request.setAttribute("STUDENTS_LIST", students);
		request.setAttribute("SECTION", section);
		request.setAttribute("SUBJECT", subject);
		

		// send it to the jSP view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/class-students.jsp");
		dispatcher.forward(request, response);
		

        }catch (Exception e) {
        	e.getMessage();
        }

	}

	private boolean getCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean check = false;
		Cookie[] cookies = request.getCookies();
		// Find the cookie of interest in arrays of cookies
		for (Cookie cookie : cookies) {
			 
			if (cookie.getName().equals("admin") && cookie.getValue().equals("admin")) {
				check = true;
				break;
			} 

		}
		
		
		return check;

	}
	private void studentsAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String id = request.getParameter("id");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String age = request.getParameter("age");
		String classn = request.getParameter("class");
		
		PreparedStatement posted = null;
		Connection conn = getConnection();
		conn.setAutoCommit(true);
		
		
		String action = request.getParameter("add");
		
		if (action != null) {
			
		    posted = conn.prepareStatement("INSERT INTO students (id, fname, lname, age, class) VALUES ('"+id+"', '"+fname+"', '"+lname+"', '"+age+"', '"+classn+"')");
		    posted.executeUpdate();
		}
		else {
			action = request.getParameter("delete");
			if (action != null) {
				
				posted = conn.prepareStatement("DELETE FROM students WHERE (id = '"+id+"')");
				posted.executeUpdate();
			}
			else {
				action = request.getParameter("update");
				String query = null;
				
				if(fname != null && fname != "") {
					posted = conn.prepareStatement("UPDATE students SET fname = '"+fname+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					
					//query = " fname = '"+fname+"'";
				
				if(lname != null && lname != "") {
					
					posted = conn.prepareStatement("UPDATE students SET lname = '"+lname+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					//query += " lname = '"+lname+"'";
				if(age != null && age != "") {
					posted = conn.prepareStatement("UPDATE students SET age = '"+age+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					//query += " age = '"+age+"'";
				if(classn != null && classn != "") {
					posted = conn.prepareStatement("UPDATE students SET class = '"+classn+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					//query += " class = '"+classn+"'";
					//posted = conn.prepareStatement("UPDATE students SET fname = '"+fname+"' WHERE (id = '"+id+"')");
				
//				posted = conn.prepareStatement("UPDATE students SET fname = '"+fname+"', lname = '"+lname+"', age = '"+age+"' WHERE (id = '"+id+"')");
				//posted = conn.prepareStatement("UPDATE students SET "+query+" WHERE (id = '"+id+"')");
			}
		}
		//String actionDelete = "";
        
		
//		System.out.println(actionDelete);
		
		
//		PreparedStatement posted = null;
//		Connection conn = getConnection();
//		System.out.println(conn);
		
//	    if (actionDelete.equals("Delete")) {
//			System.out.println("TalalDelete");
//			posted = conn.prepareStatement("DELETE FROM students WHERE (id = '"+id+"')");
//		}
//	    
//	    else if (actionAdd.equals("Add")) {
//			System.out.println("TalalAdd");
//		    posted = conn.prepareStatement("INSERT INTO students (id, fname, lname, age, class) VALUES ('"+id+"', '"+fname+"', '"+lname+"', '"+age+"', '"+classn+"')");
//		}
	    
        
        //posted.executeUpdate();
        studentsList(request ,response);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
//		dispatcher.forward(request, response);
//		Statement stmt = conn.createStatement();
//		stmt.executeUpdate("INSERT INTO students (id, fname, lname, age) values (8, 'Talal', 'Saud', 24)");
		}catch(Exception e) {
			studentsList(request ,response);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		  
		
		
	}
	
	private void subjectsAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String shortcut = request.getParameter("shortcut");
		
		PreparedStatement posted = null;
		Connection conn = getConnection();
		conn.setAutoCommit(true);
		String action = request.getParameter("add");
		if (action != null) {
			
		    posted = conn.prepareStatement("INSERT INTO subjects (id, name, shortcut) VALUES ('"+id+"', '"+name+"', '"+shortcut+"')");
		    posted.executeUpdate();
		}
		else {
			action = request.getParameter("delete");
			if (action != null) {
				
				posted = conn.prepareStatement("DELETE FROM subjects WHERE (id = '"+id+"')");
				posted.executeUpdate();
			}
			else {
				action = request.getParameter("update");
				String query = null;
				
				if(name != null && name != "") {
					posted = conn.prepareStatement("UPDATE subjects SET name = '"+name+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					
				if(shortcut != null && shortcut != "") {
					
					posted = conn.prepareStatement("UPDATE subjects SET shortcut = '"+shortcut+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
			}
		}
		
	    
        subjectList(request ,response);
		}catch(Exception e) {
			subjectList(request ,response);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		  
		
		
	}
	
	private void teachersAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String id = request.getParameter("id");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String age = request.getParameter("age");
		
		PreparedStatement posted = null;
		Connection conn = getConnection();
		conn.setAutoCommit(true);
		
		String action = request.getParameter("add");
		if (action != null) {
			
		    posted = conn.prepareStatement("INSERT INTO teachers (id, fname, lname, age) VALUES ('"+id+"', '"+fname+"', '"+lname+"', '"+age+"')");
		    posted.executeUpdate();
		}
		else {
			action = request.getParameter("delete");
			if (action != null) {
				
				posted = conn.prepareStatement("DELETE FROM teachers WHERE (id = '"+id+"')");
				posted.executeUpdate();
			}
			else {
				action = request.getParameter("update");
				String query = null;
				
				if(fname != null && fname != "") {
					posted = conn.prepareStatement("UPDATE teachers SET fname = '"+fname+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					
					//query = " fname = '"+fname+"'";
				if(lname != null && lname != "") {
					
					posted = conn.prepareStatement("UPDATE teachers SET lname = '"+lname+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
				if(age != null && age != "") {
					posted = conn.prepareStatement("UPDATE teachers SET age = '"+age+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
			}
		}
		teachersList(request ,response);
		}catch(Exception e) {
			teachersList(request ,response);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		  
		
		
	}
	
	private void classAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String id = request.getParameter("id");
		String section = request.getParameter("section");
		String teacher = request.getParameter("teacher");
		String subject = request.getParameter("subject");
		String time = request.getParameter("time");
		
		PreparedStatement posted = null;
		Connection conn = getConnection();
		conn.setAutoCommit(true);
		
		String action = request.getParameter("add");
		if (action != null) {
			
		    posted = conn.prepareStatement("INSERT INTO classes (id, section, teacher, subject, time) VALUES ('"+id+"', '"+section+"', '"+teacher+"', '"+subject+"', '"+time+"')");
		    posted.executeUpdate();
		}
		else {
			action = request.getParameter("delete");
			if (action != null) {
				
				posted = conn.prepareStatement("DELETE FROM classes WHERE (id = '"+id+"')");
				posted.executeUpdate();
			}
			else {
				action = request.getParameter("update");
				String query = null;
				
				if(section != null && section != "") {
					posted = conn.prepareStatement("UPDATE classes SET section = '"+section+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					
				if(teacher != null && teacher != "") {
					
					posted = conn.prepareStatement("UPDATE classes SET teacher = '"+teacher+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
					//query += " lname = '"+lname+"'";
				if(subject != null && subject != "") {
					posted = conn.prepareStatement("UPDATE classes SET subject = '"+subject+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
				
				if(time != null && time != "") {
					posted = conn.prepareStatement("UPDATE classes SET time = '"+time+"' WHERE (id = '"+id+"')");
					posted.executeUpdate();
				}
			}
		}
		classesList(request ,response);
		}catch(Exception e) {
			classesList(request ,response);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		  
		
		
	}
	
	
	
	public static Connection getConnection() throws Exception{
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/new_database";
            String username = "root";
            String password = "123Talal";
            //Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(url,username,password);
            
            return conn;
        } catch(Exception e){System.out.println(e);}
        
        
        return null;
    }


}
