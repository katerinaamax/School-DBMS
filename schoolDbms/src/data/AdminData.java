package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AdminData {
	
	private final StringProperty ID, Name, Username, Email, Phone;
	
	public AdminData(String id, String name, String username, String email, String phone) {
		this.ID = new SimpleStringProperty(id);
		this.Name = new SimpleStringProperty(name);
		this.Username = new SimpleStringProperty(username);
		this.Email = new SimpleStringProperty(email);
		this.Phone = new SimpleStringProperty(phone);
	}
	
	public String getID() {
		return ID.get();
	}
	
	public StringProperty IDProperty() {
		return ID;
	}
	
	public void setID (String ID) {
		this.ID.set(ID);
	}
	
	public String getName() {
		return Name.get();
	}
	
	public StringProperty NameProperty() {
		return Name;
	}
	
	public void setName (String name) {
		this.Name.set(name);
	}
	
	public String getusername() {
		return Username.get();
	}
	
	public StringProperty UsernameProperty() {
		return Username;
	}
	
	public void setUsername (String username) {
		this.Username.set(username);
	}
	
	public String getEmail() {
		return Email.get();
	}
	
	public StringProperty EmailProperty() {
		return Email;
	}
	
	public void setEmail (String email) {
		this.Email.set(email);
	}
	
	public String getPhone() {
		return Phone.get();
	}
	
	public StringProperty PhoneProperty() {
		return Phone;
	}
	
	public void setPhone (String phone) {
		this.Phone.set(phone); 
	}

}
