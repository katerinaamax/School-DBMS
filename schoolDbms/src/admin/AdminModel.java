package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.AdminData;
import data.ClassData;
import data.ParentsData;
import data.StudentData;
import data.TeacherData;
import dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {
	
Connection connection;
ObservableList<AdminData> admin_data;
ObservableList<TeacherData> teacher_data;
ObservableList<ClassData> class_data;
ObservableList<StudentData> students_data;
ObservableList<ParentsData> parents_data;

	public AdminModel() {
		try {
			this.connection = DbConnection.getConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(this.connection == null) {
			System.exit(1);
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public boolean isConnected() { 
		return this.connection != null;
	}
	
	public  ObservableList<AdminData> getAdmins() {
		String admins = "SELECT * FROM User JOIN Admin ON User.userID = Admin.adminId";
		
		try {
			this.admin_data = FXCollections.observableArrayList();
			PreparedStatement pr_admins = this.connection.prepareStatement(admins);
			ResultSet rs = pr_admins.executeQuery();
			
			while(rs.next()) {
				admin_data.add(new AdminData(rs.getString(1), rs.getString(6), rs.getString(2), rs.getString(5), rs.getString(4)));
			}
			
			pr_admins.close();
			rs.close();
	
			return admin_data;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public  ObservableList<TeacherData> getTeachers() {
		String teachers = "SELECT * FROM User JOIN Teacher ON User.userID = Teacher.teacherId";
		
		try {
			this.teacher_data = FXCollections.observableArrayList();
			PreparedStatement pr_teachers = this.connection.prepareStatement(teachers);
			ResultSet rs = pr_teachers.executeQuery();
			
			while(rs.next()) {
				teacher_data.add(new TeacherData(rs.getString(1), rs.getString(6), rs.getString(2), rs.getString(5), rs.getString(4)));

			}
			
			pr_teachers.close();
			rs.close();
			return teacher_data;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ObservableList<ClassData> getClasses(){
		String classes = "SELECT * FROM Class";
		
		try {
			this.class_data = FXCollections.observableArrayList();
			PreparedStatement pr_class = this.connection.prepareStatement(classes);
			ResultSet rs = pr_class.executeQuery();
			
			while(rs.next()) {
				
				try {
					String id = rs.getString(1);
					String name =  rs.getString(2);
			
					String assign = "SELECT User.userID, User.name FROM User INNER JOIN Teacher_assigned_to_class ON User.userID = Teacher_assigned_to_class.teacherId WHERE Teacher_assigned_to_class.classId = ?";
					PreparedStatement assign_pr = this.connection.prepareStatement(assign);
					assign_pr.setString(1,  id);
					
					String teacher_id;
					String teacher_name;
					ResultSet result = assign_pr.executeQuery();
					
					if(result.next()) {
						teacher_id = result.getString(1);
						teacher_name = result.getString(2);
					} 
					else {
						teacher_id = "";
						teacher_name= "";
					}
					
					System.out.println(teacher_id);
					class_data.add(new ClassData(id, name, teacher_id, teacher_name));
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			pr_class.close();
			rs.close();
			return class_data;
				
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;		
	}
	
	public  ObservableList<StudentData> getStudents() {
		String students = "SELECT * FROM Students";
		
		try {
			this.students_data = FXCollections.observableArrayList();
			PreparedStatement pr_students = this.connection.prepareStatement(students);
			ResultSet rs = pr_students.executeQuery();
			
			while(rs.next()) {
				students_data.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4)));
			}
			
			pr_students.close();
			rs.close();
			return students_data;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ObservableList<ParentsData> getParents(){
		String parents = "SELECT * FROM Parent";
		try {
			this.parents_data = FXCollections.observableArrayList();
			PreparedStatement pr_parents = this.connection.prepareStatement(parents);
			ResultSet rs = pr_parents.executeQuery();
			
			while(rs.next()) {
				try {
					String id = rs.getString(1);
					String studentId =  rs.getString(2);
					String name = rs.getString(3);
					String email = rs.getString(4);
					String relationship;
					int rel = rs.getInt(5);
					
					if(rel ==0)
						relationship = "Mother";
					else
						relationship = "Father";
					String phone = rs.getString(6);
					
					String stud_name = "SELECT Students.name FROM Students WHERE Students.studentId = ?";
					PreparedStatement name_pr = this.connection.prepareStatement(stud_name);
					name_pr.setString(1, id);
					
					String student_name;
					ResultSet result = name_pr.executeQuery();
					
					if(result.next()) {
						student_name = result.getString(1);
					} 
					else {
						student_name= "";
					}
					
					parents_data.add(new ParentsData(id, name, studentId, student_name, relationship, email, phone));
					name_pr.close();
					result.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			pr_parents.close();
			rs.close();
			System.out.println("PArents: "+parents_data == null);
			return parents_data;
				
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;		
	}
	
	public boolean checkEmail(String email) {
		PreparedStatement  pr = null;
		ResultSet rs = null;
		
		String sqlQuery = "SELECT * FROM User WHERE email = ? ";
		try {
			pr = this.connection.prepareStatement(sqlQuery);			
			pr.setString(1, email);
		
			rs = pr.executeQuery();
		
			if(rs.next()) 
				return false;
		
			else
				return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		finally {
			try {
				pr.close();
				rs.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//Check if user with this username exists in database
		public boolean checkUsername(String username) {
			PreparedStatement  pr = null;
			ResultSet rs = null;
			
			String sqlQuery = "SELECT * FROM User WHERE username = ? ";
			try {
				pr = this.connection.prepareStatement(sqlQuery);			
				pr.setString(1, username);
			
				rs = pr.executeQuery();
			
				if(rs.next()) 
					return false;
			
				else
					return true;
			}
			catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			finally {
				try {
					pr.close();
					rs.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	public boolean registerUser(String username, String password, String name, String email, String phone, String role) {
		if (checkEmail(email) && checkUsername(username)) {
			String sql_user = "INSERT INTO User(username, password, name, email, phone, role) VALUES(?,?,?,?,?,?)";
			try {
				PreparedStatement pr = this.connection.prepareStatement(sql_user);
				pr.setString(1, username);
				pr.setString(2,  password);
				pr.setString(3,  name);
				pr.setString(4, email);
				pr.setObject(5, phone, java.sql.Types.NUMERIC);
				pr.setString(6,  role);
				pr.executeUpdate();
				pr.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
				return false;

			}
			
			if(role.equals("Admin")) {
				
				int id = 0;
				String sql_id = "SELECT userID FROM User WHERE username = ?";
				
				try {
					PreparedStatement pr = this.connection.prepareStatement(sql_id);			
					pr.setString(1, username);
					ResultSet rs = pr.executeQuery();
				
					if(rs.next()) {
						System.out.println("id: "+ id);
						id = rs.getInt("userId");
						pr.close();
						rs.close();
					
						String sql_admin = "INSERT INTO Admin(adminId) VALUES(?)";
						try {
							PreparedStatement pr_admin = this.connection.prepareStatement(sql_admin);
							pr_admin.setInt(1, id);
							pr_admin.executeUpdate();
							pr_admin.close();
							return true;
						}
						catch(SQLException e) {
							e.printStackTrace();
							return false;
						}
					}		
				}
				catch(SQLException ex) {
					ex.printStackTrace();
					return false;
				}
				return false;
				
			}
			
			else if (role.equals("Teacher")) {
				
				int id = 0;
				String sql_id = "SELECT userID FROM User WHERE username = ?";
				
				try {
					PreparedStatement pr = this.connection.prepareStatement(sql_id);			
					pr.setString(1, username);
					ResultSet rs = pr.executeQuery();
				
					if(rs.next()) {
						id = rs.getInt("userId");
						pr.close();
						rs.close();
					
						String sql_admin = "INSERT INTO Teacher(teacherId) VALUES(?)";
						try {
							PreparedStatement pr_teacher = this.connection.prepareStatement(sql_admin);
							pr_teacher.setInt(1, id);
							pr_teacher.executeUpdate();
							pr_teacher.close();
							return true;
						}
						catch(SQLException e) {
							e.printStackTrace();
							return false;
						}
					}		
				}
				catch(SQLException ex) {
					ex.printStackTrace();
					return false;
				}
			}
		}
		return false;
		
	}
	
	public void deleteTeacher(String username) {
		String sqlDelete = "DELETE FROM User WHERE User.username = ?";
		String delTeacher = "DELETE FROM Teacher_assigned_to_class JOIN User WHERE User.username = ?";
		try {	
			PreparedStatement pr = this.connection.prepareStatement(sqlDelete);
		
			pr.setString(1, username);
			pr.executeUpdate();
			pr.close();
		}
		
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {	
			PreparedStatement pr = this.connection.prepareStatement(delTeacher);
		
			pr.setString(1, username);
			pr.executeUpdate();
			pr.close();
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addClass(String name) {

		String sqlClass = "INSERT INTO Class(name) VALUES(?)";
		try {
			PreparedStatement pr_class = this.connection.prepareStatement(sqlClass);
			pr_class.setString(1, name);
			
			pr_class.executeUpdate();
			pr_class.close();
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteClass(String id) {
		String sqlDelete = "DELETE FROM Class WHERE Class.classId = ?";
		
		try {	
			PreparedStatement pr = this.connection.prepareStatement(sqlDelete);
		
			pr.setString(1, id);
			pr.executeUpdate();
			pr.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void assign(String classID, String teacherID) {
		String sqlClass = "INSERT INTO Teacher_assigned_to_class(teacherId, classId) VALUES(?, ?)";
		try {
			PreparedStatement pr_class = this.connection.prepareStatement(sqlClass);
			pr_class.setString(1, teacherID);
			pr_class.setString(2, classID);
			pr_class.executeUpdate();
			pr_class.close();
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String addStudent(String name, String address, String phone, String email) {
		String addStudent = "INSERT INTO Students(name, address, phone, email) VALUES(?,?,?,?)";
		
		try {
			PreparedStatement student_pr = this.connection.prepareStatement(addStudent);
			student_pr.setString(1,  name);
			student_pr.setString(2,  address);
			student_pr.setString(3, phone);
			student_pr.setString(4,  email);
			
			student_pr.executeUpdate();
			student_pr.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		String id = "";
		String stu_id = "SELECT Students.studentId FROM Students WHERE email = ? AND name = ?";
		try {
			PreparedStatement id_pr = this.connection.prepareStatement(stu_id);
			id_pr.setString(1, email);
			id_pr.setString(2,  name);
			ResultSet rs = id_pr.executeQuery();
			if(rs.next())
				id = rs.getString(1);
			
			id_pr.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void addParent(String studentId, String name, String email, int relationship, String phone) {
		String addParent ="INSERT INTO Parent(parentId, studentId, name, email, relationship, phone) VALUES(?,?,?,?,?,?)";
	
		try {
			PreparedStatement parent_pr = this.connection.prepareStatement(addParent);
			parent_pr.setString(1,  studentId);
			parent_pr.setString(2, studentId);
			parent_pr.setString(3,  name);
			parent_pr.setString(4,  email);
			parent_pr.setInt(5, relationship);
			parent_pr.setString(6, phone);
			parent_pr.executeUpdate();
			parent_pr.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	} 
	
	public void deleteStudent(String id) {
		String delStudent = "DELETE FROM Students WHERE Students.studentId = ?";
		String delParent = "DELETE FROM Parent WHERE Parent.studentId = ?";
		try {
			PreparedStatement del_student = this.connection.prepareStatement(delStudent);
			del_student.setString(1, id);
			del_student.executeUpdate();
			del_student.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement del_parent = this.connection.prepareStatement(delParent);
			del_parent.setString(1, id);
			del_parent.executeUpdate();
			del_parent.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
