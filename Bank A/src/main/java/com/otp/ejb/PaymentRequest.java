package com.otp.ejb;

import java.sql.*;
import javax.ws.rs.*;

@Path("/")
public class PaymentRequest {

@POST
@Path("/checkBalance")
@Produces("text/plain")
public boolean approvePaymentRequest(@QueryParam("acc")String acc, 
		                    @QueryParam("pin")String pin,
							@QueryParam("payment")String payment) {
	String balance = "0";
	Double dbalance= 0.0;
	Double dpayment = Double.valueOf(payment);
	boolean enough_balance = false;
	try {
	DBConnection db = new DBConnection();
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection conn = db.createConnection();
	PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts where id=?");
	stmt.setString(1, acc);
	//stmt.setString(2, pin);
	ResultSet rs = stmt.executeQuery();
	if (rs.next()) {
        balance =  rs.getString("balance");
        dbalance = Double.valueOf(balance);
        if(dbalance > dpayment) {
            enough_balance = true;
        }
    }
	}catch(Exception e) {
		e.printStackTrace();
		System.out.print("Something went wrong...");
	}
	return enough_balance;
}
@POST
@Path("/updateBalance")
public void updateBalance(@QueryParam("id")String id,@QueryParam("payment")String payment) {
	try {
		DBConnection db = new DBConnection();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = db.createConnection();
		Double dpayment = Double.valueOf(payment);
		String balance = "0";
		Double dbalance= 0.0;
		PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts where id=?");
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
	       balance = rs.getString("balance");
	     }
	    dbalance = Double.valueOf(balance);
	    double newBalance = dbalance - dpayment;
	    String dnBalance = Double.toString(newBalance);
		String insertSQL = "UPDATE accounts SET balance = ? WHERE id = ?";
        PreparedStatement insertStatement = conn.prepareStatement(insertSQL);

            // Set the values for the statement's parameters
            insertStatement.setString(1, dnBalance);
            insertStatement.setString(2, id);
            // Execute the statement
            insertStatement.executeUpdate();

            // Close the connection
            conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
