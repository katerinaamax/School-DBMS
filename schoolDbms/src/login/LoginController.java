package login;

import teachers.TeachersController;
import admin.AdminController;
import register.RegisterController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class LoginController implements Initializable {
	
	LoginModel loginModel = new LoginModel();
	
	@FXML
	private Label welcome_label, or_label, wrong_label;
	@FXML
	private TextField username_textfield;
	@FXML
	private PasswordField password_field;
	@FXML
	private ComboBox<option> role_combobox;
	@FXML
	private Button login_button, signup_button;
	
	private String username;
	
	public void initialize(URL url, ResourceBundle rb) {
		if (this.loginModel.isConnected()) 
			System.out.println("connected");
		else
			System.out.println("not connected");
		
		this.role_combobox.setItems(FXCollections.observableArrayList(option.values()));
	}
	
	
	@FXML
	public void login(ActionEvent event) {
		try {
			
			if(this.username_textfield.getText() == null ||  this.password_field.getText() == null || this.role_combobox.getValue() == null) {
				wrong_label.setText("There are empty fields.");
			}
			
			if(this.loginModel.isLogin(this.username_textfield.getText(), this.password_field.getText(), 
					((option) this.role_combobox.getSelectionModel().getSelectedItem()).toString())) {
				
				this.wrong_label.setText("");
				
				this.username = this.username_textfield.getText();
				
				switch(((option) this.role_combobox.getValue()).toString()) {
					case "Admin":
						adminLogin();
						break;
					case "Teacher":
						teacherLogin();
						break;
				}
			}
			else {
				
				this.wrong_label.setText("Wrong credentials or not registered.");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	@FXML 
	public void register(ActionEvent event) {
		try {
			Stage stage = (Stage) this.login_button.getScene().getWindow();
			stage.close();
			
			//Stage stage = (Stage) this.signup_button.getScene().getWindow();
			//stage.close();
			
			Stage registerStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = (Pane) loader.load(getClass().getResource("/register/Register.fxml").openStream());
			
			RegisterController registerController = (RegisterController) loader.getController();
			
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.setTitle("Register Form");
			registerStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void teacherLogin( ) {
		try {
			Stage stage = (Stage) this.login_button.getScene().getWindow();
			stage.close();
			
			Stage teacherStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = (Pane) loader.load(getClass().getResource("/teachers/Teachers.fxml").openStream());
			
			TeachersController teachersController = (TeachersController) loader.getController();
			teachersController.initData(username);
			
			Scene scene = new Scene(root);
			teacherStage.setScene(scene);
			teacherStage.setTitle("Teachers' Dashboard");
			teacherStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void adminLogin() {
		try {
			Stage stage = (Stage) this.login_button.getScene().getWindow();
			stage.close();
			
			Stage adminStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = (Pane) loader.load(getClass().getResource("/admin/Admin.fxml").openStream());
			
			AdminController adminController = (AdminController) loader.getController();
			
			Scene scene = new Scene(root);
			adminStage.setScene(scene);
			adminStage.setTitle("Admins' Dashboard");
			adminStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
