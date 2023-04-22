package com.example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
// This class represents the main method of the application. 
// It is responsible for displaying the login screen and handling user input.


//Created By Julien Purvis 4/20/2023

public class App extends Application{

    UserSystem loginSystem = new UserSystem();
    Stage primaryStage = null;

        public static void main(String[] args) {
            launch(args);
        }
        
        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Effort Logger V.2");


            primaryStage.setScene(new Scene(createLoginScreen(), 1000, 1000));

            primaryStage.setResizable(false);


            primaryStage.show();





        // Create Account Button
        // boolean createAccountSuccess = loginSystem.createNewAccount(username, password);
        // if(createAccountSuccess){
        //     System.out.println("Account created successfully!");
        // }
        // else{
        //     System.out.println("Account creation failed!");
        // }

        try{
           EffortSystem effortSystem = new EffortSystem();
           boolean success = effortSystem.deleteEffort(1);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        
        
    }



    //Create Login Layouts
    public VBox createLoginScreen(){
        VBox vbox = new VBox();
        HBox hBox = new HBox();
        Text titleText = new Text("Log In");
        final TextField usernamTextField = new TextField();
        final TextField passwordTextField = new TextField();
        Button loginButton = new Button();
        Button createButton = new Button();

        //Set Text
        usernamTextField.setPromptText("Email");
        passwordTextField.setPromptText("Password");
        loginButton.setText("Log In");
        createButton.setText("Create Account");

        titleText.fontProperty().set(titleText.getFont().font(20));

        //Set Layouts
        vbox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        vbox.setPadding(new Insets(10, 400, 50, 400));
        vbox.setSpacing(10);;

            //handle Login Attempt
            loginButton.setOnAction(new EventHandler<ActionEvent>() {
     
                @Override
                public void handle(ActionEvent event) {
                    String username = usernamTextField.getText();
                    String password = passwordTextField.getText();
                    handleLoginAttempt(username, password);
                }
            });

            //handle Create Account Attempt
            createButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    primaryStage.setScene(new Scene(createCreateAccountScreen(), 1000, 1000));
                }
            });


        //Add all elements to the layout
        vbox.getChildren().add(titleText);
        vbox.getChildren().add(usernamTextField);
        vbox.getChildren().add(passwordTextField);
        hBox.getChildren().add(loginButton);
        hBox.getChildren().add(createButton);
        vbox.getChildren().add(hBox);
        return vbox;
    }



    //Create Create Account Layouts
    public VBox createCreateAccountScreen(){
        VBox vbox = new VBox();
        HBox hBox = new HBox();
        Text titleText = new Text("Create Account");
        final TextField usernamTextField = new TextField();
        final TextField passwordTextField = new TextField();
        final TextField firstNameTextField = new TextField();
        final TextField lastNameTextField = new TextField();
        Button createButton = new Button();
        Button backButton = new Button();
        

        vbox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        vbox.setPadding(new Insets(10, 50, 50, 50));
        vbox.setSpacing(10);

        usernamTextField.setPromptText("Email");
        passwordTextField.setPromptText("Password");
        firstNameTextField.setPromptText("First Name");
        lastNameTextField.setPromptText("Last Name");

        createButton.setText("Create Account");
        backButton.setText("Back");

        titleText.fontProperty().set(titleText.getFont().font(20));

        //handle Create Account Attempt
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernamTextField.getText();
                String password = passwordTextField.getText();
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                boolean createAccountSuccess = loginSystem.createNewUser(username, password, firstName, lastName);
                if(createAccountSuccess){
                    System.out.println("Account created successfully!");
                }
                else{
                    System.out.println("Account creation failed!");
                }
            }
        });

        //handle Back Button
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(createLoginScreen(), 300, 250));
            }
        });

        //Add all elements to the layout
        vbox.getChildren().add(titleText);
        vbox.getChildren().add(usernamTextField);
        vbox.getChildren().add(passwordTextField);
        vbox.getChildren().add(firstNameTextField);
        vbox.getChildren().add(lastNameTextField);
        hBox.getChildren().add(createButton);
        hBox.getChildren().add(backButton);
        vbox.getChildren().add(hBox);

        return vbox;
    }


    //Handle login from user input
    public void handleLoginAttempt(String username, String password){
        int loginSuccess = loginSystem.login(username, password);
        if(loginSuccess >= 1){
            System.out.println("Login successful!");
        }
        else{
            System.out.println("Login failed!");
        }
    }
    
}
