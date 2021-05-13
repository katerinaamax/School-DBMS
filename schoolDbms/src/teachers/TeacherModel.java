package teachers;

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

public class TeacherModel {
	Connection connection;
	ObservableList<AdminData> admin_data;
	ObservableList<TeacherData> teacher_data;
	ObservableList<ClassData> class_data;
	ObservableList<StudentData> students_data, class_student_data;
	ObservableList<ParentsData> parents_data;
	
	public TeacherModel() {
		try {
			this.connection = DbConnection.getConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() { 
		return this.connection != null;
	}
	
	public String getId(String username) {
		
		String id = "";
		String getId = "SELECT User.userID FROM User WHERE User.username = ?";
		try {
			PreparedStatement pr_id = this.connection.prepareStatement(getId);
			pr_id.setString(1,  username);
			
			ResultSet rs = pr_id.executeQuery();
			if(rs.next())
				id = rs.getString(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
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
	
	public ObservableList<ClassData> getClasses(String username) {
		System.out.println("getClasses: "+ username);
		String id = getId(username);
		String classes = "SELECT * FROM Class JOIN Teacher_assigned_to_class ON Class.classId = Teacher_assigned_to_class.classId WHERE Teacher_assigned_to_class.teacherId = ?";
		
		try {
			this.class_data = FXCollections.observableArrayList();
			PreparedStatement pr_class = this.connection.prepareStatement(classes);
			pr_class.setString(1, id);
			ResultSet rs = pr_class.executeQuery();
			while(rs.next()) {
				class_data.add(new ClassData(rs.getString(1), rs.getString(2), "", ""));
			}
			pr_class.close();
			rs.close();
			return class_data;
		}
		catch(SQLException e) {
			e.printStackTrace();
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
	
	public ObservableList<ParentsData> getParents() {
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
			return parents_data;
				
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;		
	}
	
	public ObservableList<StudentData> getClassStudents(String classId) {
		String students = "SELECT Students.name, Students.studentId FROM Students JOIN Student_takes_class ON Students.studentId = Student_takes_class.studentId WHERE Student_takes_class.classId = ?";
		try {
			this.class_student_data = FXCollections.observableArrayList();
			PreparedStatement class_stud = this.connection.prepareStatement(students);
			class_stud.setString(1, classId);
			ResultSet rs = class_stud.executeQuery();

			while(rs.next()) {
				class_student_data.add(new StudentData(rs.getString(2), rs.getString(1), "", "", ""));
			}
			
			class_stud.close();
			rs.close();
			return class_student_data;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
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
	
	public void addStudentToClass(String classId, String studentId) {
		String addStud = " INSERT INTO Student_takes_class VALUES(? ,?)";
		
		try {
			PreparedStatement add_stud = this.connection.prepareStatement(addStud);
			add_stud.setString(1,  studentId);
			add_stud.setString(2,  classId);
			add_stud.executeUpdate();
			add_stud.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeStudentFromClass(String classId, String studentId) {
		String removeStu = "DELETE FROM Student_takes_class WHERE Student_takes_class.classId = ? AND Student_takes_class.studentId = ?";
		
		try {
			PreparedStatement remove_stu = this.connection.prepareStatement(removeStu);
			remove_stu.setString(1, classId);
			remove_stu.setString(2,  studentId);
			remove_stu.executeUpdate();
			remove_stu.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
