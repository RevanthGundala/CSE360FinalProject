// Imports
package com.example;
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
 
// This class represents the main method of the application. 
// It is responsible for displaying the login screen and handling user input.

//Created By Julien Purvis 4/20/2023

public class App extends Application{

	// Create login system
    UserSystem loginSystem = new UserSystem();
    Stage primaryStage = null;
		
		// Launch something
        public static void main(String[] args) {
            launch(args);
        }
        
        // What should be done when app starts.
        @Override
        public void start(Stage ps) {
        	// Make primart stage the passed through stage
            primaryStage = ps;
			// Window title
            primaryStage.setTitle("Effort Logger V.2");
			// Allow resizing
            primaryStage.setResizable(true);
			// Login screen
            primaryStage.setScene(createLoginScreen());

            //Dev statement to skip login
            primaryStage.setScene(new LogWindow());

			// display stage
            primaryStage.show();
		}

        // Create Account Button
        // boolean createAccountSuccess = loginSystem.createNewAccount(username, password);
        // if(createAccountSuccess){
        //     System.out.println("Account created successfully!");
        // }
        // else{
        //     System.out.println("Account creation failed!");
        // }        
    }

    //Create Login Layouts
    public Scene createLoginScreen(){
		// Creating a new scen
        Scene createLoginScene = new Scene(new VBox() , 1000, 1000);

		// Creating and importing fonts
		// WE NEED TO MAKE THIS A RELATIVE PATH
        String fontSheet = fileToStylesheetString( new File ("/Users/Work/Documents/Eclipse/CSE360FinalProject/src/darkmode-style.css") );
        createLoginScene.getStylesheets().add(fontSheet);

		// View Title
        Text titleText = new Text("Log In");
        
        // Text Fields
        Text passwordReqs = new Text("Password may only contain letters, numbers and !@#$%^&*?");
        final TextField usernamTextField = new TextField();
        final PasswordField passwordTextField = new PasswordField();
        
        // Buttons
        Button loginButton = new Button();
        Button createButton = new Button();

        // Set Text
        usernamTextField.setPromptText("Email");
        passwordTextField.setPromptText("Password");
        loginButton.setText("Log In");
        createButton.setText("Create New Account");

		// Stylization
        titleText.setId("title-text");
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.rgb(129, 129, 129));
        passwordReqs.setFill(Color.rgb(129, 129, 129));
        
        //handle Login Attempt
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                String username = usernamTextField.getText();
                String password = passwordTextField.getText();
                handleLoginAttempt(username, password);
            }
        });

        // Handle Create Account Attempt
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(createCreateAccountScreen());
            }
        });


        // Add all elements to the layout
        // create the grid pane and set the spacing and padding
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setAlignment(Pos.CENTER);

        // add the labels, textfields, and buttons to the grid pane
        gridPane.add(titleText, 1, 0);
        gridPane.add(usernamTextField, 1, 1);
        gridPane.add(passwordTextField, 1, 2);
        gridPane.add(loginButton, 2, 2);
        gridPane.add(createButton, 1, 3);
        gridPane.add(passwordReqs, 1, 4);

		// Specifying main view
        createLoginScene.setRoot(gridPane);

		// Returning completed scene
        return createLoginScene;
    }

    //Create Create Account Layouts
    public Scene createCreateAccountScreen(){
		// Scene object
        Scene createAccountScene = new Scene(new VBox() , 1000, 1000);

		// Creating and importing fonts
		// WE NEED TO MAKE THIS A RELATIVE PATH
        String fontSheet = fileToStylesheetString( new File ("/Users/Work/Documents/Eclipse/CSE360FinalProject/src/darkmode-style.css") );
        createAccountScene.getStylesheets().add(fontSheet);

		// Items
        Text titleText = new Text("Create Account");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        final TextField usernamTextField = new TextField();
        final TextField passwordTextField = new TextField();
        final TextField firstNameTextField = new TextField();
        final TextField lastNameTextField = new TextField();
        Button createButton = new Button();
        Button backButton = new Button();
		
		// Buttons
        createButton.setText("Create Account");
        backButton.setText("Back");

		// Style Stuff
        titleText.setId("title-text");
        titleText.setFill(Color.rgb(129, 129, 129));

        // Handle Create Account Attempt
        createButton.setOnAction(new EventHandler<ActionEvent>() {
        	// Event Handler
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
                primaryStage.setScene(createLoginScreen());

            }
        });

        //Add all elements to the layout
        // create the grid pane and set the spacing and padding
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // add the labels, textfields, and buttons to the grid pane
        gridPane.add(titleText, 1, 0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernamTextField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordTextField, 1, 2);
        gridPane.add(firstNameLabel, 0, 3);
        gridPane.add(firstNameTextField, 1, 3);
        gridPane.add(lastNameLabel, 0, 4);
        gridPane.add(lastNameTextField, 1, 4);
        gridPane.add(createButton, 2, 4);
        gridPane.add(backButton, 1, 5);
		
		// Pane alignment
        gridPane.setAlignment(Pos.CENTER);
        
        // Setting root object
        createAccountScene.setRoot(gridPane);

		// Returning completed scene
        return createAccountScene;
    }

    //Handle login from user input
    public void handleLoginAttempt(String username, String password){
        int loginSuccess = loginSystem.login(username, password);
        if(loginSuccess >= 1){
        	// User is based
            System.out.println("Login successful!");
        }
        else{
        	// User has a skill issue
            System.out.println("Login failed!");
        }
    }
    
    // Little funciton to turn css file to usable stylesheet string
    public String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }

}
