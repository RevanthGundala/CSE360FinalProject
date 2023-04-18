// This class is responsible for all database operations related to efforts

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

    public boolean createEffortForTask(int taskId, String effortDescription, int effortHours) {
        boolean createEffortForTaskSuccess = false;
        try {
            // Prepare INSERT statement
            String sql = "INSERT INTO efforts (task_id, effort_description, effort_hours) VALUES (?, ?, ?)";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            stmt.setString(2, effortDescription);
            stmt.setInt(3, effortHours);

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
                int effortId = rs.getInt("id");
                int taskId = rs.getInt("task_id");
                String effortDescription = rs.getString("effort_description");
                int effortHours = rs.getInt("effort_hours");
    
                Effort effort = new Effort(effortId, taskId, effortDescription, effortHours);
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
                int effortId = rs.getInt("effortId");
                String effortDescription = rs.getString("effort_description");
                int effortHours = rs.getInt("effort_hours");

                Effort effort = new Effort(effortId, taskId, effortDescription, effortHours);
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
            String sql = "SELECT * FROM efforts WHERE id=?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, effortId);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Effort objects
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String effortDescription = rs.getString("effort_description");
                int effortHours = rs.getInt("effort_hours");

                effort = new Effort(effortId, taskId, effortDescription, effortHours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return effort;
    }

    // todo: fix sql statment
    public boolean updateEffort(int effortId, int taskId, String newEffortDescription, int newHoursWorked){
        boolean updateEffortSuccess = false;
        try{
            String sql = "UPDATE efforts SET task_name = ?, effort_description = ?, hours_worked = ? WHERE effort_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, effortId);
            stmt.setInt(2, taskId);
            stmt.setString(3, newEffortDescription);
            stmt.setInt(4, newHoursWorked);
            
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
            String sql = "DELETE FROM efforts WHERE id = ?";
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
