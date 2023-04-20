// This class is responsible for all database operations related to efforts

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskSystem {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public TaskSystem(){
        dbUrl = "jdbc:mysql://localhost:3306/loginuser"; // 'loginuser' is the name of the database on my local computer
        dbUsername = "root";
        dbPassword = "password";
    }

    public boolean createTask(String taskName, String taskDescription, int estimatedHours){
        // right now tasks is a global databse, unsure if we want to make it a user specific database
        // also unsure if we want to return a boolean or a Task object
        boolean createTaskSuccess = false;
        try{
            String sql = "INSERT INTO tasks (task_name, task_description, estimated_hours) VALUES (?, ?, ?)";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, taskName);
            stmt.setString(2, taskDescription);
            stmt.setInt(3, estimatedHours);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                createTaskSuccess = true;
                System.out.println("Task inserted successfully!");
            } else {
                System.out.println("Failed to insert task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return createTaskSuccess;
    }

    public Task getSpecificTask(int taskId){
        Task task = null;
        try{
            String sql = "SELECT * FROM tasks WHERE task_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int task_id = rs.getInt("task_id");
                String task_name = rs.getString("task_name");
                String task_description = rs.getString("task_description");
                int estimatedHours = rs.getInt("estimated_hours");

                task = new Task(task_id, task_name, task_description, estimatedHours);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            // Execute SELECT statement
            String sql = "SELECT * FROM tasks";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Task objects
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String taskName = rs.getString("task_name");
                String taskDescription = rs.getString("task_description");
                int estimatedHours = rs.getInt("estimated_hours");

                Task task = new Task(taskId, taskName, taskDescription, estimatedHours);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean updateTask(int taskId, String newTaskName, String newTaskDescription, int newEstimatedHours){
        boolean updateTaskSuccess = false;
        try{
            String sql = "UPDATE tasks SET task_name = ?, task_description = ?, estimated_hours = ? WHERE task_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            // todo: need to reorder these
            stmt.setString(1, newTaskName);
            stmt.setString(2, newTaskDescription);
            stmt.setInt(3, newEstimatedHours);
            stmt.setInt(4, taskId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updateTaskSuccess = true;
                System.out.println("Task updated successfully!");
            } else {
                System.out.println("Failed to update task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return updateTaskSuccess;
    }

    public boolean deleteTask(int taskId){
        boolean deleteTaskSuccess = false;
        try{
            String sql = "DELETE FROM tasks WHERE task_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteTaskSuccess = true;
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Failed to delete task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return deleteTaskSuccess;
    }


}
