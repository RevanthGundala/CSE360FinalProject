package com.example;
import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


// This class represents the scene that is displayed when the user is logging effort
public class LogWindow extends Scene{
    public LogWindow(){
        super(new HBox(), 1000, 1000);

        String fontSheet =  new App().fileToStylesheetString( new File ("/Users/Work/Documents/Eclipse/CSE360FinalProject/src/darkmode-style.css") );
        this.getStylesheets().add(fontSheet);

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Effort", new Label("Log Effort"));

        //set the content of tab1 to LOG EFFORT TAB
        tab1.setContent(LogEffortTab());

        Tab tab2 = new Tab("Tasks"  , new Label("Manage Tasks"));


        Tab tab3 = new Tab("Visualize" , new Label("View Effort"));

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);


        this.setRoot(tabPane);


    }


    public HBox LogEffortTab(){
        EffortSystem effortSystem = new EffortSystem();
        TaskSystem taskSystem  = new TaskSystem();

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Text titleText  = new Text("Log Effort");
        final ComboBox<Task> taskComboBox = new ComboBox<>();
        final TextField effortNameTextField = new TextField();
        final TextField effortDescriptionTextField = new TextField();
        final TextField effortHoursTextField = new TextField();
        Button logEffortButton = new Button("Log Effort");
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefSize(500, 500);

        vBox.setAlignment(Pos.CENTER);

        vBox.setPadding(new Insets(10, 200, 50, 200));
        vBox.setSpacing(10);;


        hBox.setPadding(new Insets(10, 10, 50, 10));

        hBox.setAlignment(Pos.TOP_LEFT);


        titleText.getFont();
        //Set Field prompts
        titleText.fontProperty().set(Font.font(20));
        effortNameTextField.setPromptText("Effort Name");
        effortDescriptionTextField.setPromptText("Effort Description");
        effortHoursTextField.setPromptText("Effort Hours");

        //Set Field sizes
        effortDescriptionTextField.setPrefWidth(500);
        effortHoursTextField.setPrefWidth(500);
        effortNameTextField.setPrefWidth(500);




        //Add Elements to ComboBox
        List<Task> tasks = taskSystem.getAllTasks();
        taskComboBox.setPromptText("Select Task");
        taskComboBox.getItems().addAll(tasks);

        //add all tasks to scroll pane
        for(Task task : tasks){
            VBox taskVBox = new VBox();
            Text taskName = new Text(task.getTaskName());
            Text taskDescription = new Text(task.getTaskDescription());
            Text taskHours = new Text(Integer.toString(task.getTaskId()));
            taskVBox.getChildren().add(taskName);
            taskVBox.getChildren().add(taskDescription);
            taskVBox.getChildren().add(taskHours);
            scrollPane.setContent(taskVBox);
        }



        //handle button click
        logEffortButton.setOnAction(e -> {
            String effortName = effortNameTextField.getText();
            System.out.println(effortName);
            String effortDescription = effortDescriptionTextField.getText();
            int effortHours = Integer.parseInt(effortHoursTextField.getText());
            effortSystem.createEffortForTask(tasks.indexOf(taskComboBox.getValue()), effortName, effortDescription, effortHours);
        });


        //Add Elements to VBox
        vBox.getChildren().add(titleText);
        vBox.getChildren().add(effortNameTextField);
        vBox.getChildren().add(effortDescriptionTextField);
        vBox.getChildren().add(effortHoursTextField);
        vBox.getChildren().add(logEffortButton);

        //Add elements to Hbox
        hBox.getChildren().add(scrollPane);
        hBox.getChildren().add(vBox);

        return hBox;
    }


    public HBox ManageTasksTab(){
        return null;
    }


    public HBox ViewEffortTab(){
        return null;
    }


}