package com.example;
// This class is responsible for all database operations related to efforts

// Each effort is associated with a taskId
// For example, if a user is working on a task called "Create a login screen", they may create an effort called "Create login screen UI"

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EffortSystem {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public EffortSystem(){
        dbUrl = "jdbc:mysql://localhost:3306/loginuser"; // 'loginuser' is the name of the database on my local computer
        dbUsername = "root";
        dbPassword = "password";
    }

    public boolean createEffortForTask(int taskId, String effortName, String effortDescription, int effortHours) {
        boolean createEffortForTaskSuccess = false;
        try {
            // Prepare INSERT statement
            String sql = "INSERT INTO efforts (task_id, effort_name, effort_description, effort_hours) VALUES (?, ?, ?, ?)";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            stmt.setString(2, effortName);
            stmt.setString(3, effortDescription);
            stmt.setInt(4, effortHours);

            // Execute the query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                createEffortForTaskSuccess = true;
                System.out.println("Effort inserted successfully!");

            } else {
                System.out.println("Failed to insert Effort.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return createEffortForTaskSuccess;
    }

    public List<Effort> getAllEfforts() {
        List<Effort> efforts = new ArrayList<>();
        try {
            // Execute SELECT statement
            String sql = "SELECT * FROM efforts";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            // Iterate through the result set and create Effort objects
            while (rs.next()) {
                int effortId = rs.getInt("effort_id");
                int taskId = rs.getInt("task_id");
                String effortName = rs.getString("effort_name");
                String effortDescription = rs.getString("effort_description");
                int effortHours = rs.getInt("effort_hours");
    
                Effort effort = new Effort(effortId, taskId, effortName, effortDescription, effortHours);
                efforts.add(effort);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return efforts;
    }
    

    // Get all efforts for a task
    public List<Effort> getEffortsForTask(int taskId) {
        List<Effort> efforts = new ArrayList<>();
        try {
            // Prepare SELECT statement with parameter
            String sql = "SELECT * FROM efforts WHERE task_id=?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Effort objects
            while (rs.next()) {
                int effortId = rs.getInt("effort_id");
                String effortName = rs.getString("effort_name");
                String effortDescription = rs.getString("effort_description");
                int effortHours = rs.getInt("effort_hours");

                Effort effort = new Effort(effortId, taskId, effortName, effortDescription, effortHours);
                efforts.add(effort);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return efforts;
    }

    public Effort getSpecificEffort(int effortId){
        Effort effort = null;
        try {
            // Prepare SELECT statement with parameter
            String sql = "SELECT * FROM efforts WHERE effort_id=?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, effortId);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Effort objects
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String effortName = rs.getString("effort_name");
                String effortDescription = rs.getString("effort_description");
                int effortHours = rs.getInt("effort_hours");

                effort = new Effort(effortId, taskId, effortName, effortDescription, effortHours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return effort;
    }

    public boolean updateEffort(int effortId, int taskId, String newEffortName, String newEffortDescription, int newEffortHours){
        boolean updateEffortSuccess = false;
        try{
            String sql = "UPDATE efforts SET effort_name = ?, effort_description = ?, effort_hours = ? WHERE task_id = ? and effort_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, newEffortName);
            stmt.setString(2, newEffortDescription);
            stmt.setInt(3, newEffortHours);
            stmt.setInt(4, taskId);
            stmt.setInt(5, effortId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updateEffortSuccess = true;
                System.out.println("Task updated successfully!");
            } else {
                System.out.println("Failed to update task.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return updateEffortSuccess;
    }

    public boolean deleteEffort(int effortId){
        boolean deleteEffortSuccess = false;
        try{
            String sql = "DELETE FROM efforts WHERE effort_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, effortId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteEffortSuccess = true;
                System.out.println("Effort deleted successfully!");
            } else {
                System.out.println("Failed to delete effort.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return deleteEffortSuccess;
    }
}
