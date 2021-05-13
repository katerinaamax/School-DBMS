package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentData {
	public final StringProperty ID, Name , Address, Email, Phone;
	
	public StudentData(String id, String name, String address, String email, String phone) {
		this.ID = new SimpleStringProperty(id);
		this.Name = new SimpleStringProperty(name);
		this.Address = new SimpleStringProperty(address);
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
	
	public StringProperty ClassNameProperty() {
		return Name;
	}
	
	public void setClassName (String name) {
		this.Name.set(name);
	}
	
	public String getAddress() {
		return Address.get();
	}
	
	public StringProperty AddressProperty() {
		return Address;
	}
	
	public void setAddress (String address) {
		this.Address.set(address);
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


