package emu.edu.fiscal.controller;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
/*
Class that will handle interacting with the database
 */
public class UserInfo {


    private static int id;


    PreparedStatement psGetInfo = null;
    static ResultSet resultSet = null;
    static Connection connection = null;

    public UserInfo(){
        id = 0;
    }

    //Method creates a connection to the database
    public static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FISCALDB", "root", "password");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //checks if username and password is in database and returns a boolean value
    public  static boolean authentication(String username, String password){
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
                        id  = resultSet.getInt("id");
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
    public static double getIncome(){
        PreparedStatement psGetIncome = null;
        double income = 0;
        try {
            psGetIncome = connection.prepareStatement("SELECT SUM(income) from userInfo WHERE userID = "+ id + " ;");

            resultSet = psGetIncome.executeQuery();
            while(resultSet.next()) {
                income = resultSet.getDouble("SUM(income)");

            }
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return income;
    }


    //returns the expense names  from the database in the form of array list
    public static ArrayList<String>  getExpenseNames(){
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
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenseNames;
    }
    public static ArrayList<Double>  getExpenseValues(){
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
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenseVals;
    }
    /*
        Takes the new user information and inserts this information into the database
        */
    public static void insertUser(String userName, String email, String password, String name){
        try {
            PreparedStatement psInsertUser = connection.prepareStatement("INSERT INTO UserAuth (username, email, password, name)" +
                    " VALUES( ?,?,?,?)");

            psInsertUser.setString(1, userName);
            psInsertUser.setString(2, email);
            psInsertUser.setString(3, password);
            psInsertUser.setString(4, name);
            int row = psInsertUser.executeUpdate();


        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    Takes the name and value of an expense and inserts this into the database
    */
    public static void insertExpense(String name, double value){
        try {
            PreparedStatement psInsertExp = connection.prepareStatement("INSERT INTO userInfo(userID, expenseName, expenseValue, transactionDate) VALUES (?,?,?, ?)");
            psInsertExp.setInt(1, id);
            psInsertExp.setString(2, name);
            psInsertExp.setDouble(3, value);
            psInsertExp.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            int row = psInsertExp.executeUpdate();

        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
   Takes the name and value of an expense and inserts this into the database
   */
    public static void insertIncome(double value){
        try {
            PreparedStatement psInsertIn = connection.prepareStatement("INSERT INTO userInfo(income, incomeDate, userID) VALUES (?,?,?)");
            psInsertIn.setInt(3, id);
            psInsertIn.setDouble(1, value);
            psInsertIn.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            int row = psInsertIn.executeUpdate();

        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
