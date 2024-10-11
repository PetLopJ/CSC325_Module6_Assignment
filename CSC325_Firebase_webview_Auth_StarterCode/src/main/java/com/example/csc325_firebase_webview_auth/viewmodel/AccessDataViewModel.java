package com.example.csc325_firebase_webview_auth.viewmodel;



import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccessDataViewModel {

	private final StringProperty userFname = new SimpleStringProperty();
	private final StringProperty userLname = new SimpleStringProperty();
	private final StringProperty userDep = new SimpleStringProperty();
	private final StringProperty userMajor = new SimpleStringProperty();
	private final StringProperty userEmail = new SimpleStringProperty();
	private final StringProperty userImage = new SimpleStringProperty();
        private final int age=0;
	private final ReadOnlyBooleanWrapper writePossible = new ReadOnlyBooleanWrapper();

	public AccessDataViewModel() {
		writePossible.bind(userFname.isNotEmpty().and(userLname.isNotEmpty().and(userDep.isNotEmpty().and(userMajor.isNotEmpty().and(userEmail.isNotEmpty())))));
	}

	public StringProperty userFnameProperty() {
		return userFname;
	}

	public StringProperty userLnameProperty() {
		return userLname;
	}

	public StringProperty userDepProperty()  {return userDep; }

	public StringProperty userMajorProperty() {
		return userMajor;
	}

	public StringProperty userEmailProperty() { return userEmail; }

	//public StringProperty userImageProperty() {return userImage;}

	public ReadOnlyBooleanProperty isWritePossibleProperty() {
		return writePossible.getReadOnlyProperty();
	}
}
