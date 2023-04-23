package com.example;
// This class represents all of the methods that are used to interact with the users database. 
// It is responsible for creating new users, logging in users, and updating user information.

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSystem {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    // Constructor
    public UserSystem(){
        dbUrl = "jdbc:mysql://localhost:3306/loginuser"; // 'loginuser' is the name of the database on my local computer
        dbUsername = "root";
        dbPassword = "password";
    }
    
    public boolean createNewUser(String email, String password, String firstName, String lastName) {
        boolean createAccountSuccess = false;
        try {
            // check username and password for malicious characters
            if(!validateInputForSQLQuery(firstName) || !validateInputForSQLQuery(lastName)){
                System.out.println("Invalid first or last name!");
                return false;
            }
            // hash password
            String encrypted_password = hash(password);
            // Connect to the database
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            // Prepare a SQL query to check if the username and password match a user in the database
            String sql = "INSERT INTO users (email, password, first_name, last_name, role) "
                         + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // NOTE: Current roles are only admin and user, can add manager or other roles later
            
            // Set the parameters for the query
            stmt.setString(1, email);
            stmt.setString(2, encrypted_password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            if(email.equals("admin")){
                stmt.setString(5, "admin");
            }
            else{
                stmt.setString(5, "user");
            }
            //stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            
            // Execute the query and check if there is a matching user in the database
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                createAccountSuccess = true;
                System.out.println("User inserted successfully!");
            } else {
                System.out.println("Failed to insert user.");
            }
            
            // Close the database connection and resources
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return createAccountSuccess;
    }

    public User getUser(int userId){
        User user = null;
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            // Prepare a SQL query to check if the username and password match a user in the database
            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Set the parameters for the query
            stmt.setInt(1, userId);
            
            // Execute the query and check if there is a matching user in the database
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");
                //Timestamp created_at = rs.getTimestamp("created_at");
                user = new User(id, email, password, firstName, lastName, role);
            }
            
            // Close the database connection and resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateUser(int userId, String newEmail, String newPassword, String newFirstName, String newLastName){
        boolean updateAccountSuccess = false;
        try {
            // check username and password for malicious characters
            if(!validateInputForSQLQuery(newFirstName) || !validateInputForSQLQuery(newLastName)){
                System.out.println("Invalid first or last name!");
                return false;
            }
            // hash password
            String encrypted_password = hash(newPassword);
            // Connect to the database
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            // Prepare a SQL query to check if the username and password match a user in the database
            String sql = "UPDATE users SET email = ?, password = ?, first_name = ?, last_name = ?, role = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // NOTE: Current roles are only admin and user, can add manager or other roles later
            
            // Set the parameters for the query
            stmt.setString(1, newEmail);
            stmt.setString(2, encrypted_password);
            stmt.setString(3, newFirstName);
            stmt.setString(4, newLastName);
            if(newEmail.equals("admin")){
                stmt.setString(5, "admin");
            }
            else{
                stmt.setString(5, "user");
            }
            stmt.setInt(6, userId);

            //stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            
            // Execute the query and check if there is a matching user in the database
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updateAccountSuccess = true;
                System.out.println("User updated successfully!");
            } else {
                System.out.println("Failed to update user.");
            }
            
            // Close the database connection and resources
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateAccountSuccess;
    }

    public boolean deleteUser(int userId){
        boolean deleteUserSuccess = false;
        try{
            String sql = "DELETE FROM users WHERE user_id = ?";
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteUserSuccess = true;
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Failed to delete user.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return deleteUserSuccess;
    }

    // returns the userId if user exists, -1 if user does not exist
    public int login(String email, String password) {
        int userId = -1;
        try {
            // hash password
            String encrypted_password = hash(password);
            // Connect to the database
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            // Prepare a SQL query to check if the username and password match a user in the database
            String sql = "SELECT user_id FROM users WHERE email = ? AND password = ?"; // Modified query to only select user_id
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the parameters for the query
            stmt.setString(1, email);
            stmt.setString(2, encrypted_password);

            // Execute the query and check if there is a matching user in the database
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id"); // Retrieve user_id from the ResultSet
            }

            // Close the database connection and resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }


    public static boolean validateInputForSQLQuery(String userInput){
		///The pattern is the regex method of specifying allowed characters
		//in this case I have allowed upper and lower case letters and the top 8 special characters
		//which are not malicious
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9!@#$%^&*?]*$");
		Matcher matcher = pattern.matcher(userInput);
		return matcher.matches();
	}

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
