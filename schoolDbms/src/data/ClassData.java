package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassData {
	public final StringProperty ID, ClassName , TeacherId, TeacherName;
	public ClassData(String id, String name, String teacher_id, String teacher_name) {
		this.ID = new SimpleStringProperty(id);
		this.ClassName = new SimpleStringProperty(name);
		this.TeacherId = new SimpleStringProperty(teacher_id);
		this.TeacherName = new SimpleStringProperty(teacher_name);
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
	
	public String getClassName() {
		return ClassName.get();
	}
	
	public StringProperty ClassNameProperty() {
		return ClassName;
	}
	
	public void setClassName (String name) {
		this.ClassName.set(name);
	}
	
	public String getTecherId() {
		return TeacherId.get();
	}
	
	public StringProperty TeacherIdProperty() {
		return TeacherId;
	}
	
	public void setTeacherId (String teacherId) {
		this.TeacherId.set(teacherId);
	}
	
	public String getTeacherName() {
		return TeacherName.get();
	}
	
	public StringProperty TeacherNameProperty() {
		return TeacherName;
	}
	
	public void setTeacherName (String name) {
		this.TeacherName.set(name);
	}
}
