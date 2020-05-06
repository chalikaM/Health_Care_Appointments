
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.Appointments"%>

<!DOCTYPE htmlt>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.0.min.js"></script>
<script src="Components/appointments.js"></script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">


				<h1>Appointments</h1>

				<form id="formAppointment" name="formAppointment" method="post" action="appointments.jsp">
					Patient Name : <input id="A_PatientName" name="A_PatientName" type="text"
						class="form-control form-control-sm"> <br> 
					Patient NIC : <input id="A_PatientNIC" name="A_PatientNIC" type="text"
						class="form-control form-control-sm"> <br> 
					Patient Phone Number : <input id="A_PatientPhoneNo" name="A_PatientPhoneNo" type="text"
						class="form-control form-control-sm"> <br> 
					Doctor Name : <input id="A_DoctorName" name="A_DoctorName" type="text"
						class="form-control form-control-sm"> <br>
						Hospital Name : <input id="A_HospitalName" name="A_HospitalName" type="text"
						class="form-control form-control-sm"> <br>
						Date : <input id="A_Date" name="A_Date" type="text"
						class="form-control form-control-sm"> <br>
						Time : <input id="A_Time" name="A_Time" type="text"
						class="form-control form-control-sm"> <br>
						
					
					 <input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidAppointmentIDSave" name="hidAppointmentIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
						Appointments appointmentObj = new Appointments();
					out.print(appointmentObj.readAppointments());
					%>
				</div>
</body>
</html>