package com.example.ecommercce;

import java.sql.ResultSet;

public class Login {


    public  Customer customerLogin(String userName,String password){
        String loginQuery = " SELECT * FROM customer WHERE email= '"+userName+"' AND password = '"+password+"'";
        DbConnection conn = new DbConnection();
        ResultSet rs=conn.getQueryTable(loginQuery);
        try{
            if(rs.next()){
                return  new Customer(rs.getInt("id"),rs.getNString("name"),
                        rs.getNString("email"),rs.getString("mobile"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
      return  null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer= login.customerLogin("harshsinghdh@gmail.com","abc123");
        System.out.println("Welcome : "+customer.getName());
    }
}
