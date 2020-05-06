$(document).ready(function () {
	$("#alertSuccess").hide(); 
	$("#alertError").hide();
	
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateAppointmentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "AppointmentsAPI",
		type : type,
		data : $("#formAppointment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentSaveComplete(response.responseText, status);
		}
	});
});

//UPDATE==========================================
function onAppointmentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidAppointmentIDSave").val("");
	$("#formAppointment")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "AppointmentsAPI",
		type : "DELETE",
		data : "A_AppointmentID=" + $(this).data("appointmentid"),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentDeleteComplete(response.responseText, status);
		}
	});
});

//DELETE==========================================
function onAppointmentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidAppointmentIDSave").val(
					$(this).closest("tr").find('#hidAppointmentIDUpdate').val());
			$("#A_PatientName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#A_PatientNIC").val($(this).closest("tr").find('td:eq(1)').text());
			$("#A_PatientPhoneNo").val($(this).closest("tr").find('td:eq(2)').text());
			$("#A_DoctorName").val($(this).closest("tr").find('td:eq(3)').text());
			$("#A_HospitalName").val($(this).closest("tr").find('td:eq(4)').text());
			$("#A_Date").val($(this).closest("tr").find('td:eq(5)').text());
			$("#A_Time").val($(this).closest("tr").find('td:eq(6)').text());
		});

// CLIENTMODEL=========================================================================
function validateAppointmentForm() {
	// patient name
	if ($("#A_PatientName").val().trim() == "") {
		return "Insert patient name.";
	}
	// patient NIC
	if ($("#A_PatientNIC").val().trim() == "") {
		return "Insert patient NIC.";
	}
	// Patient Phone Number
	if ($("#A_PatientPhoneNo").val().trim() == "") {
		return "Insert patient phone number.";
	}
	
	// is numerical value
	var tmpPhoneNo = $("#A_PatientPhoneNo").val().trim();
	if (!$.isNumeric(tmpPhoneNo)) {
		return "Insert a numerical value for phone number.";
	}
	
	// Doctor Name
	if ($("#A_DoctorName").val().trim() == "") {
		return "Insert doctor name.";
	}
	
	//Hospital Name
	if ($("#A_HospitalName").val().trim() == "") {
		return "Insert hospital name.";
	}
	
	//Date
	if ($("#A_Date").val().trim() == "") {
		return "Insert date.";
	}
	
	//Time
	if ($("#A_Time").val().trim() == "") {
		return "Insert time.";
	}
	
	
	
	return true;
}
