package teachers;

import data.*;
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
import javafx.scene.input.MouseEvent;
import login.LoginController;
import login.LoginModel;

import java.net.URL;
import java.util.ResourceBundle;

import admin.AdminModel;

public class TeachersController implements Initializable {
	@FXML
	private TableColumn<AdminData, String> admin_id, admin_name, admin_username, admin_email, admin_phone;
	@FXML 
	private TableView<AdminData> admin_tableview;
	@FXML
	private TableColumn<TeacherData, String> teacher_id, teacher_name, teacher_username, teacher_email, teacher_phone;
	@FXML
	private TableView<TeacherData> teachers_table;
	@FXML
	private TableColumn<StudentData, String> student_id, student_name, student_email, student_address, student_phone, student_id_class, student_name_class;
	@FXML
	private TableView<StudentData> students_table, class_students;
	@FXML
	private TableView<ParentsData> parents_table;
	@FXML
	private TableColumn<ParentsData, String> parents_id, parents_name, student_par_id, par_student_name, parent_relationship, parent_email, parent_phone;
	@FXML 
	private TableView<ClassData> classes_table;
	@FXML
	private TableColumn<ClassData, String> class_id, class_name, teacherId_class, teacher_class;
	@FXML
	private TextField new_student_name, new_student_email, new_student_address, new_student_phone, new_parent_name, new_parent_email, new_parent_phone, student_delete, class_id_stu,
				student_class_id, remove_class_id, remove_student_class;
	@FXML
	private ComboBox<relationship> new_relationship;
	@FXML
	private Label students_label;
	
	private ObservableList<AdminData> data;
	private ObservableList<TeacherData> teacher_data;
	private ObservableList<ClassData> class_data;
	private ObservableList<StudentData> students_data, class_students_data;
	private ObservableList<ParentsData> parents_data;
	
	TeacherModel teacherModel = new TeacherModel();
	//LoginModel loginModel = new LoginModel();
	
	String username = "";
	
	public void initialize(URL url, ResourceBundle rb) {
		if (this.teacherModel.isConnected()) {
			displayAdmins();
			displayTeachers();
			displayStudents();
			displayParents();
			this.new_relationship.setItems(FXCollections.observableArrayList(relationship.values()));
		}
		else
			System.out.println("not connected");
	}
	
	public void displayAdmins() {
		this.data = teacherModel.getAdmins();
		 
		this.admin_id.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("ID"));
		this.admin_name.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Name"));
		this.admin_username.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Username"));
		this.admin_email.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Email"));
		this.admin_phone.setCellValueFactory(new PropertyValueFactory<AdminData, String> ("Phone"));
		
		this.admin_tableview.setItems(null);
		this.admin_tableview.setItems(this.data);
	}
	
	public void displayTeachers() {
		this.teacher_data = teacherModel.getTeachers();
	
		this.teacher_id.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("ID"));
		this.teacher_name.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Name"));
		this.teacher_username.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Username"));
		this.teacher_email.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Email"));
		this.teacher_phone.setCellValueFactory(new PropertyValueFactory<TeacherData, String> ("Phone"));
		
		this.teachers_table.setItems(null);
		this.teachers_table.setItems(this.teacher_data);
	}
	
	public void displayClasses() {
		this.class_data = teacherModel.getClasses(this.username);
		
		this.class_id.setCellValueFactory(new PropertyValueFactory<ClassData, String> ("ID"));
		this.class_name.setCellValueFactory(new PropertyValueFactory<ClassData, String> ("ClassName"));
		
		this.classes_table.setItems(null);
		this.classes_table.setItems(this.class_data);
	}
	
	public void displayStudents() {
		this.students_data = teacherModel.getStudents();
		
		this.student_id.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("ID"));
		this.student_name.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Name"));
		this.student_email.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Email"));
		this.student_address.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Address"));
		this.student_phone.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Phone"));

		this.students_table.setItems(null);
		this.students_table.setItems(this.students_data);
	}
	
	public void displayParents() {
		this.parents_data = teacherModel.getParents();
		
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
	
	public void displayClassStudents(String classId) {
		this.class_students_data = teacherModel.getClassStudents(classId);
		
		this.student_id_class.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("ID"));
		this.student_name_class.setCellValueFactory(new PropertyValueFactory<StudentData, String> ("Name"));
		
		this.class_students.setItems(null);
		this.class_students.setItems(this.class_students_data);
	}
	
	public void getSelectedId(MouseEvent event) {
		ClassData selClass = classes_table.getSelectionModel().getSelectedItem();
		displayClassStudents(selClass.getID());
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
			
			String studentId = teacherModel.addStudent(new_student_name.getText(), new_student_address.getText(), new_student_phone.getText(), new_student_email.getText());
			
			teacherModel.addParent(studentId, new_parent_name.getText(), new_parent_email.getText(), rel, new_parent_phone.getText());
			
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
			teacherModel.deleteStudent(student_delete.getText());
		}
		student_delete.clear();
		displayStudents();
		displayParents();
	}
	
	public void addStudentToClass(ActionEvent event) {
		teacherModel.addStudentToClass(class_id_stu.getText(), student_class_id.getText());
		displayClassStudents(class_id_stu.getText());
		class_id_stu.clear();
		student_class_id.clear();
	}
	
	public void removeStudentFromClass(ActionEvent event) {
		teacherModel.removeStudentFromClass(remove_class_id.getText(), remove_student_class.getText());
		displayClassStudents(remove_class_id.getText());
		remove_class_id.clear();
		remove_student_class.clear();
	}

	public void initData(String username) {
		this.username = username;
		displayClasses();
	}
	
	public String getUsername() {
		return username;
	}
}
