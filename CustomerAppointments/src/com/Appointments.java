package com;

import java.sql.Statement;

import javax.servlet.http.HttpServlet;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Appointments extends HttpServlet {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/paf", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertAppointment(String aname, String anic, String aphone, String adoctorname, String ahosname,
			String adate, String atime) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into appointment(`A_AppointmentID`,`A_PatientName`,`A_PatientNIC`,`A_PatientPhoneNo`,`A_DoctorName`,`A_HospitalName`,`A_Date`,`A_Time`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, aname);
			preparedStmt.setString(3, anic);
			preparedStmt.setString(4, aphone);
			preparedStmt.setString(5, adoctorname);
			preparedStmt.setString(6, ahosname);
			preparedStmt.setString(7, adate);
			preparedStmt.setString(8, atime);

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newAppointments = readAppointments();
			output = "{\"status\":\"success\", \"data\": \"" + newAppointments + "\"}";

			// output = "Inserted successfully";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			// output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readAppointments()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr>Appointments<th>Patient Name</th><th>Patient NIC</th><th>Patient PhoneNumber</th><th>Doctor Name</th><th>Hospital Name</th><th>Date</th><th>Time</th><th>Update</th><th>Remove</th></tr>";
	 String query = "select * from appointment";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
		 String A_AppointmentID = Integer.toString(rs.getInt("A_AppointmentID"));
		 String A_PatientName = rs.getString("A_PatientName");
		 String A_PatientNIC = rs.getString("A_PatientNIC");
		 String A_PatientPhoneNo = rs.getString("A_PatientPhoneNo");		
		 String A_DoctorName = rs.getString("A_DoctorName");
		 String A_HospitalName = rs.getString("A_HospitalName");
		 String A_Date = rs.getString("A_Date");
		 String A_Time = rs.getString("A_Time");
		 
		 output += "<tr><td><input id='hidAppointmentIDUpdate' name='hidAppointmentIDUpdate' type='hidden' value='" + A_AppointmentID + "'>" + A_PatientName + "</td>";
		 
		 
	 // Add into the html table
		
		 output += "<td>" + A_PatientNIC + "</td>";
		 output += "<td>" + A_PatientPhoneNo + "</td>";
		 output += "<td>" + A_DoctorName + "</td>";
		 output += "<td>" + A_HospitalName + "</td>";
		 output += "<td>" + A_Date + "</td>";
		 output += "<td>" + A_Time + "</td>";
	 // buttons
	 /*output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"+ "<td><form method=\"post\" action=\"items.jsp\">" + "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
	 + "<input name=\"A_AppointmentID\" type=\"hidden\" value=\"" + A_AppointmentID
	 + "\">" + "</form></td></tr>";*/
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-appointmentid='"
				 + A_AppointmentID + "'>" + "</td></tr>";
		 
	 }
	 con.close();
	 
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the appointments";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 } 
	
	
	public String updateAppointment(String aID, String aname, String anic, String aphone, String adoctorname, String ahosname, String adate, String atime)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 String query = "UPDATE appointment SET A_PatientName=?,A_PatientNIC=?,A_PatientPhoneNo=?,A_DoctorName=?,A_HospitalName=?,A_Date=?,A_Time=?WHERE A_AppointmentID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, aname);
	 preparedStmt.setString(2, anic);
	 preparedStmt.setString(3, aphone);
	 preparedStmt.setString(4, adoctorname);
	 preparedStmt.setString(5, ahosname);
	 preparedStmt.setString(6, adate);
	 preparedStmt.setString(7, atime);
	 preparedStmt.setInt(8, Integer.parseInt(aID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	//output = "Updated successfully";
		String newAppointments = readAppointments();
		 output = "{\"status\":\"success\", \"data\": \"" +newAppointments + "\"}";
		
	} catch (Exception e) {
		output = "{\"status\":\"error\", \"data\":\"Error while updating the appointment.\"}";
		//output = "Error while updating the item.";
		System.err.println(e.getMessage());
	}
	return output;
	 }
	
	
	public String DeleteAppointment(String A_AppointmentID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from appointment where A_AppointmentID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(A_AppointmentID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	//output = "Deleted successfully";
		String newAppointments = readAppointments();
		 output = "{\"status\":\"success\", \"data\": \"" +newAppointments + "\"}";
		
	} catch (Exception e) {
		output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
		//output = "Error while deleting the item.";
		System.err.println(e.getMessage());
	}
	return output;
	 } 

	
	

}
