package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Person;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.google.firebase.cloud.StorageClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class AccessFBView {


     @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField dep;
    @FXML
    private TextField major;
    @FXML
    private TextField email;
    @FXML
    private TextField image;
    @FXML
    private Button clear;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button edit;
    @FXML
    private TextArea outputField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private VBox vbox;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn fnameColumn;
    @FXML
    private TableColumn lnameColumn;
    @FXML
    private TableColumn depColumn;
    @FXML
    private TableColumn majorColumn;
    @FXML private TableColumn emailColumn;
    @FXML private ImageView imageView;
    @FXML
     private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;
    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @FXML
    void initialize() {
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        depColumn.setCellValueFactory(new PropertyValueFactory<>("dep"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setItems(listOfUsers);
    }

    @FXML
    private void addRecord(ActionEvent event) {
        addData(image.getText());
    }

        //@FXML
   // private void readRecord(ActionEvent event) {
    //    readFirebase();
   // }

    @FXML
    private void regRecord(ActionEvent event) {
    registerUser();
    }

     @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/files/WebContainer.fxml");
    }

    @FXML
    private void clearHandle(ActionEvent event) throws IOException {
        String Fname = fname.getText();
        String Lname = lname.getText();
        String Dep = dep.getText();
        String Major = major.getText();
        String Email = email.getText();
        String Image = image.getText();

        fname.clear();
        lname.clear();
        dep.clear();
        major.clear();
        email.clear();
        image.clear();
    }

    @FXML
    private void addHandle(ActionEvent event) throws IOException {
        String Fname = fname.getText();
        String Lname = lname.getText();
        String Dep = dep.getText();
        String Major = major.getText();
        String Email = email.getText();
        String Image = image.getText();

        Person person = new Person(Fname, Lname, Dep, Major, Email, Image);

        if (Fname.isEmpty() || Lname.isEmpty() || Dep.isEmpty() || Major.isEmpty() || Email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else {
            listOfUsers.add(person);

            Image profileImage = new Image(Image);
            imageView.setImage(profileImage);
        }
    }


    @FXML
    private void deleteHandle(ActionEvent event) throws IOException {
        Person selectedPerson = (Person) tableView.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            listOfUsers.remove(selectedPerson);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You have to select a person first!");
            alert.showAndWait();
        }
    }

    @FXML
    private void editHandle(ActionEvent event) throws IOException {
        Person selectedPerson = (Person) tableView.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            selectedPerson.setFname(fname.getText());
            selectedPerson.setLname(lname.getText());
            selectedPerson.setDep(dep.getText());
            selectedPerson.setMajor(major.getText());
            selectedPerson.setEmail(email.getText());
            tableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You have to select a person first!");
            alert.showAndWait();
        }
    }

    @FXML
    private void imageHandle(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imageUrl = uploadImageToFirebase(selectedFile);
            image.setText(imageUrl);
            Image profileImage = new Image(imageUrl);
            imageView.setImage(profileImage);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Image uploaded successfully!");
            alert.showAndWait();
        }
    }

    private String uploadImageToFirebase(File file) throws IOException {
        String fileName = "profile_pictures/" + UUID.randomUUID().toString() + ".jpg";
        FileInputStream serviceAccount = new FileInputStream(file);
        Blob blob = StorageClient.getInstance().bucket().create(fileName, serviceAccount, "image/jpeg");
        String imageUrl = "https://storage.googleapis.com/" + blob.getBucket() + "/" + blob.getName();
        return imageUrl;
    }

    public void addData(String imageUrl) {

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("First Name", fname.getText());
        data.put("Last Name", lname.getText());
        data.put("Department", dep.getText());
        data.put("Major", major.getText());
        data.put("Email", email.getText());
        data.put("Image", imageUrl);
        ApiFuture<WriteResult> result = docRef.set(data);

        result.addListener(() -> {
            try {
                WriteResult writeResult = result.get();
                System.out.println("Document written with ID: " + docRef.getId());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error adding document: " + e.getMessage());
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void sendVerificationEmail() {
    try {
        UserRecord user = App.fauth.getUser("name");
        //String url = user.getPassword();

    } catch (Exception e) {
    }
}

    public void registerUser() {
    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmail("user@example.com")
            .setEmailVerified(false)
            .setPassword("secretPassword")
            .setPhoneNumber("+11234567890")
            .setDisplayName("John Doe")
            .setDisabled(false);

    UserRecord userRecord;
    try {
        userRecord = App.fauth.createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());

    } catch (FirebaseAuthException ex) {
       // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
    }

}
}

       /* public boolean readFirebase()
         {
             key = false;

        ApiFuture<QuerySnapshot> future =  App.fstore.collection("References").get();
        List<QueryDocumentSnapshot> documents;
        try
        {
            documents = future.get().getDocuments();
            if(documents.size()>0)
            {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents)
                {
                    outputField.setText(outputField.getText()
                            + "First Name: " + document.getData().get("First Name") + ", "
                                    + "Last Name: " + document.getData().get("Last Name") + ", "
                                    + "Department: " + document.getData().get("Department") + ", "
                                    + "Major: " + document.getData().get("Major") + ", "
                                    + "Email: " + document.getData().get("Email") + ", "
                                    + "Age: " + document.getData().get("Age") + "\n"
                    );
                    System.out.println(document.getId() + " => " + document.getData().get("First Name"));
                    person  = new Person(
                            String.valueOf(document.getData().get("First Name")),
                            String.valueOf(document.getData().get("Last Name")),
                            String.valueOf(document.getData().get("Department")),
                            String.valueOf(document.getData().get("Major")),
                            String.valueOf(document.getData().get("Email")),
                            String.valueOf(document.getData().get("Image"))
                            );
                    listOfUsers.add(person);
                }
            }
            else
            {
               System.out.println("No data");
            }
            key=true;

        }
        catch (InterruptedException | ExecutionException ex)
        {
             ex.printStackTrace();
        }
        return key;
    }*/

