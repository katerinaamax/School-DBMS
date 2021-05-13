package admin;

import data.relationship;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import data.AdminData;
import data.TeacherData;
import data.ClassData;
import data.ParentsData;
import data.StudentData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import login.option;
import register.RegisterModel;

public class AdminController implements Initializable {
	
	@FXML 
	private TableView<AdminData> admin_tableview;
	@FXML
	private TableColumn<AdminData, String> admin_id, admin_username, admin_name, admin_email, admin_phone;
	@FXML 
	private TableView<TeacherData> teachers_table;
	@FXML 
	private TableView<ClassData> classes_table;
	@FXML
	private TableColumn<TeacherData, String> teacher_id, teacher_username, teacher_name, teacher_email, teacher_phone;
	@FXML
	private TableColumn<ClassData, String> class_id, class_name, teacherId_class, teacher_class;
	@FXML
	private TableView<StudentData> students_table;
	@FXML
	private TableColumn<StudentData, String> student_id, student_name, student_email, student_address, student_phone;
	@FXML
	private TableView<ParentsData> parents_table;
	@FXML
	private TableColumn<ParentsData, String> parents_id, parents_name, student_par_id, par_student_name, parent_relationship, parent_email, parent_phone;
	@FXML
	private TextField new_teacher_name, new_teacher_email, new_teacher_username, new_teacher_phone, new_class, class_delete, teacher_id_assign, class_id_assign,
		new_student_name, new_student_email, new_student_address, new_student_phone, new_parent_name, new_parent_email, new_parent_phone, student_delete; 
	@FXML
	private ComboBox<relationship> new_relationship;
	@FXML
	private Label new_teacher_label, classname_label, assign_label, students_label, delete_label;
	
	private ObservableList<AdminData> data;
	private ObservableList<TeacherData> teacher_data;
	private ObservableList<ClassData> class_data;
	private ObservableList<StudentData> students_data;
	private ObservableList<ParentsData> parents_data;
	
	//RegisterModel registerModel= new RegisterModel();
	AdminModel adminModel = new AdminModel();
	Connection connection;
	
	public void initialize(URL url, ResourceBundle rb) {
		if (this.adminModel.isConnected()) {
			
			System.out.println("AdminController: connected");
			
			displayAdmins();
			displayTeachers();
			displayClasses();
			displayStudents();
			displayParents();
			this.new_relationship.setItems(FXCollections.observableArrayList(relationship.values()));
			
		}
		else
			System.out.println("not connected");
	}
	
	public void displayAdmins() {
		this.data = adminModel.getAdmins();
		 
		this.admin_id.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("ID"));
		this.admin_name.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Name"));
		this.admin_username.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Username"));
		this.admin_email.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Email"));
		this.admin_phone.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Phone"));
		
		this.admin_tableview.setItems(null);
		this.admin_tableview.setItems(this.data);
	}
	
	public void displayTeachers() {
		this.teacher_data = adminModel.getTeachers();
	
		this.teacher_id.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("ID"));
		this.teacher_name.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Name"));
		this.teacher_username.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Username"));
		this.teacher_email.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Email"));
		this.teacher_phone.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Phone"));
		
		this.teachers_table.setItems(null);
		this.teachers_table.setItems(this.teacher_data);
	}
	
	public void displayClasses() {
		this.class_data = adminModel.getClasses();
		
		this.class_id.setCellValueFactory(new PropertyValueFactory<ClassData, String> ("ID"));
		this.class_name.setCellValueFactory(new PropertyValueFactory<ClassData, String> ("ClassName"));
		this.teacherId_class.setCellValueFactory(new PropertyValueFactory<ClassData, String> ("TeacherId"));
		this.teacher_class.setCellValueFactory(new PropertyValueFactory<ClassData, String> ("TeacherName"));
		
		this.classes_table.setItems(null);
		this.classes_table.setItems(this.class_data);
	}
	
	public void displayStudents() {
		this.students_data = adminModel.getStudents();
		
		this.student_id.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("ID"));
		this.student_name.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Name"));
		this.student_email.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Email"));
		this.student_address.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Address"));
		this.student_phone.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Phone"));

		this.students_table.setItems(null);
		this.students_table.setItems(this.students_data);
	}
	
	public void displayParents() {
		this.parents_data = adminModel.getParents();
		
		this.parents_id.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("ID"));
		this.parents_name.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("Name"));
		this.student_par_id.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("StudentID"));
		this.par_student_name.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("StudentName"));
		this.parent_relationship.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("Relationship"));
		this.parent_email.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("Email"));
		this.parent_phone.setCellValueFactory(new PropertyValueFactory<ParentsData, String> ("Phone"));
		
		this.parents_table.setItems(null);
		this.parents_table.setItems(this.parents_data);
	}
	
	public void add_teacher(ActionEvent event) {
		if(new_teacher_name.getText() == null || new_teacher_email.getText() == null ||  new_teacher_username.getText() == null ||
				new_teacher_phone.getText() == null)
			new_teacher_label.setText("There are empty fields.");
		else {
			new_teacher_label.setText("");
		
			if(adminModel.registerUser(new_teacher_username.getText(),"1234", new_teacher_name.getText(), new_teacher_email.getText(),
					new_teacher_phone.getText(), "Teacher") == false) {
				new_teacher_label.setText("Username or Email already exist.");
			}
			else {
				new_teacher_label.setText("");
				displayTeachers(); //to refresh tableview
				new_teacher_name.clear();
				new_teacher_email.clear();
				new_teacher_username.clear();
				new_teacher_phone.clear();
			}
		}
	}
	
	public void deleteTeacher(ActionEvent event) {
		if(new_teacher_username.getText() == null)
			new_teacher_label.setText("Please fill in username field.");
		
		adminModel.deleteTeacher(new_teacher_username.getText());
		new_teacher_label.setText("");
		displayTeachers(); //to refresh tableview
		displayClasses();
		new_teacher_name.clear();
		new_teacher_email.clear();
		new_teacher_username.clear();
		new_teacher_phone.clear();
	}
	
	public void addClass(ActionEvent event) {
		if(new_class.getText() == null) {
			classname_label.setText("Please fill in name field.");
		}
		else {
			classname_label.setText("");
			adminModel.addClass(new_class.getText());
			displayClasses();
			new_class.clear();
		}
	}
	
	public void deleteClass(ActionEvent event) {
		if(class_delete.getText() != null) {
			adminModel.deleteClass(class_delete.getText());
			displayClasses();
			class_delete.clear();
		}
	}
	
	public void assign(ActionEvent event) {
		if(class_id_assign.getText() == null || teacher_id_assign == null) {
			assign_label.setText("Please fill in all fields.");
		}
		else {
			assign_label.setText("");
			adminModel.assign(class_id_assign.getText(), teacher_id_assign.getText());
			displayClasses();
			class_id_assign.clear();
			teacher_id_assign.clear();
		}
	}
	
	public void addStudent(ActionEvent event) {
		if(new_student_name.getText() == null || new_student_email.getText() == null || new_student_address.getText() == null ||
				new_student_phone.getText() == null || new_parent_name.getText() == null ||  new_parent_email.getText() == null || new_parent_phone.getText() == null) {
			students_label.setText("There are empty fields.");
		}
		else {
			students_label.setText("");
			int rel;
			if( ( (relationship) new_relationship.getSelectionModel().getSelectedItem()).toString().equals("Mother") )
				rel = 0;
			else
				rel = 1;
			
			String studentId = adminModel.addStudent(new_student_name.getText(), new_student_address.getText(), new_student_phone.getText(), new_student_email.getText());
			
			adminModel.addParent(studentId, new_parent_name.getText(), new_parent_email.getText(), rel, new_parent_phone.getText());
			
			displayStudents();
			displayParents();
			new_student_name.clear();
			new_student_email.clear();
			new_student_address.clear();
			new_student_phone.clear();
			new_parent_name.clear();
			new_parent_email.clear();
			new_parent_phone.clear();
		}
	}
	
	public void deleteStudent(ActionEvent event) {
		if(student_delete.getText() != null) {
			adminModel.deleteStudent(student_delete.getText());
		}
		student_delete.clear();
		displayStudents();
		displayParents();
	}
}
 