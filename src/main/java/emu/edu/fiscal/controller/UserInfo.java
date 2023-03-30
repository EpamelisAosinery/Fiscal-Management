package emu.edu.fiscal.controller;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
/*
Class that will handle interacting with the database
 */
public class UserInfo {


    private static int id;


    PreparedStatement psGetInfo = null;
    ResultSet resultSet = null;
    Connection connection = null;

    public UserInfo(){
        id = 0;
    }

    //Method creates a connection to the database
    public void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FISCALDB", "root", "password");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //checks if username and password is in database and returns a boolean value
    public  boolean authentication(String username, String password){
        try {
            PreparedStatement psCheckUserExists = null;
            createConnection();
            psCheckUserExists = connection.prepareStatement("SELECT * FROM userAuth WHERE username = ?");
            psCheckUserExists.setString(1, username);

            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String retrievePassword = resultSet.getString("password");

                    if (retrievePassword.equals(password)) {
                        int userId = resultSet.getInt("id");
                        id  =  userId;
                        return true;
                    } else
                        return false;

                }
            } else {
                return false;
            }

        }
        catch(Exception e){
            System.out.println(e);

        }
        return false;
    }

    //returns the total income of the user
    public double getIncome(){
        PreparedStatement psGetIncome = null;
        double income = 0;
        try {
            psGetIncome = connection.prepareStatement("SELECT SUM(income) from userInfo WHERE userID = "+ id + " ;");

            resultSet = psGetIncome.executeQuery();
            while(resultSet.next()) {
                income = resultSet.getDouble("SUM(income)");

            }
        }
        catch(Exception e){}
        return income;
    }


    //returns the expense names  from the database in the form of array list
    public ArrayList<String>  getExpenseNames(){
        PreparedStatement psGetExpenseName = null;
        ArrayList<String> expenseNames = new ArrayList<String>();
        ResultSet rs1 = null;
        try {
            psGetExpenseName = connection.prepareStatement("SELECT expenseName from userInfo WHERE userID = "+ id + " ;");

            rs1 = psGetExpenseName.executeQuery();

            while(rs1.next()) {
                expenseNames.add(rs1.getString("expenseName"));

            }
            //remove the first null element
            expenseNames.remove(0);
        }
        catch(Exception e){}
        return expenseNames;
    }
    public ArrayList<Double>  getExpenseValues(){
        PreparedStatement psGetExpenseValue= null;
        ArrayList<Double> expenseVals = new ArrayList<Double>();
        ResultSet rs1 = null;
        try {
            psGetExpenseValue = connection.prepareStatement("SELECT expenseValue from userInfo WHERE userID = "+ id + " ;");

            rs1 = psGetExpenseValue.executeQuery();
            while(rs1.next()) {
                expenseVals.add(rs1.getDouble("expenseValue"));
            }
            //remove the first null element
            expenseVals.remove(0);
        }
        catch(Exception e){}
        return expenseVals;
    }

}
