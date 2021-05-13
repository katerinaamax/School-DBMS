package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParentsData {
	public final StringProperty ID, Name , StudentID, StudentName, Relationship, Email, Phone;
	
	public ParentsData(String id, String name, String studentId, String studentName, String relationship, String email, String phone) {
		this.ID = new SimpleStringProperty(id);
		this.Name = new SimpleStringProperty(name);
		this.StudentID = new SimpleStringProperty(studentId);
		this.StudentName = new SimpleStringProperty(studentName);
		this.Relationship = new SimpleStringProperty(relationship);
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
	
	public String getStudentId() {
		return StudentID.get();
	}
	
	public StringProperty StudentIDProperty() {
		return StudentID;
	}
	
	public void setStudentId (String id) {
		this.StudentID.set(id);
	}
	
	public String getStudentName() {
		return StudentName.get();
	}
	
	public StringProperty StudentNameProperty() {
		return StudentName;
	}
	
	public void setStudentName (String name) {
		this.StudentName.set(name);
	}
	
	public String getRelationship() {
		return Relationship.get();
	}
	
	public StringProperty RelationshipProperty() {
		return Relationship;
	}
	
	public void setRelationship (String relationship) {
		this.Relationship.set(relationship);
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
