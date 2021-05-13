package register;

import login.option;
import teachers.TeachersController;
import admin.AdminController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterController implements Initializable{
	RegisterModel registerModel = new RegisterModel();
	
	@FXML 
	private TextField rusername_textfield, rname_textfield, remail_textfield, rphopne_textfield, rphone_textfield;
	@FXML
	private ComboBox<option> rrole_combobox;
	@FXML
	private PasswordField rpassword_field;
	@FXML
	private Button rregister_button;
	@FXML 
	private Label rwrong_label;
	private String username;

	public void initialize(URL url, ResourceBundle rb) {
		if (this.registerModel.isConnected()) 
			System.out.println("connected");
		else
			System.out.println("not connected");
		
		this.rrole_combobox.setItems(FXCollections.observableArrayList(option.values()));
	}
	
	public void register(ActionEvent event) {
		if(this.rusername_textfield.getText() == null ||  this.rpassword_field.getText() == null || this.rrole_combobox.getValue() == null) {
			rwrong_label.setText("There are empty fields.");
		}
		else
			rwrong_label.setText("");
		
		if(registerModel.registerUser(rusername_textfield.getText(), rpassword_field.getText(), rname_textfield.getText(), remail_textfield.getText(),
				rphone_textfield.getText(), ((option) rrole_combobox.getSelectionModel().getSelectedItem()).toString()) == false)
			rwrong_label.setText("Email or Username already exist.");
			
		else {
			this.username = this.rusername_textfield.getText();
			Stage stage = (Stage) this.rregister_button.getScene().getWindow();
			stage.close();
		
			switch(((option) this.rrole_combobox.getValue()).toString()) {
				case "Admin":
					adminLogin();
					break;
				case "Teacher":
					teacherLogin();
					break;
			}
		}
	}
	
	public void teacherLogin( ) {
		try {
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
