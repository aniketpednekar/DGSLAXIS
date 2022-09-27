<html>
<head>
<title>Axis Project</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}
</style>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>


<script>
$(document).ready(function(){

	$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});


  $("#openModal").click(function(){
		$.ajax({
			url : "http://localhost:8080/getModalData",
			type : "POST",
			data : {

			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("textStatus : " + textStatus);
			},
			beforeSend : function() {

			},
			complete : function(jqXHR, textStatus) {
				console.log("textStatus : " + textStatus);
			},
			success : function(responseText) {
				var responseTextdata =  JSON.parse(responseText);
				console.log(responseTextdata);
				setUpModulBody(responseTextdata.data);
			}
		});
  });
  
  
  $("#UplodeFile").click(function(){
	alert("dsd");
		var formData = new FormData(document.getElementById("fileinfo"));
		formData.append("label", "WEBUPLOAD");
		
		$.ajax({
			url : "http://localhost:8080/uplodeFile",
			type : "POST",
			data : formData,
			async: false,
	          cache: false,
	          contentType: false,
	          enctype: 'multipart/form-data',
	          processData: false,
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("textStatus : " + textStatus);
			},
			beforeSend : function() {

			},
			complete : function(jqXHR, textStatus) {
				console.log("textStatus : " + textStatus);
			},
			success : function(responseText) {
				alert(responseText);
			}
		});
  });
  
  
  function setUpModulBody(dataArr)	{
	var modelBodyHeadStr = "";
	var modelBodyStr = "";
	for (let element of dataArr) {
		if(element.ID == 'data1')	{
			modelBodyHeadStr= "<div id=\""+element.ID+"\"><div style=\"position:absolute; width:100%; display: flex;justify-content: center;\"><h5 id=\"Header_"+element.ID+"\">"+element.HEADER+"</h5></div><BR><BR><div id=\"Body_"+element.ID+"\">"+element.BODY+"</div></div><BR>"
		} else	{
			var card = "<div id=\""+element.ID+"\"><div class=\"card\"><div class=\"card-header\" id=\"heading"+element.ID+"\"><h5 class=\"mb-0\"><button class=\"btn btn-link\" data-toggle=\"collapse\" data-target=\"#collapse"+element.ID+"\" aria-expanded=\"true\" aria-controls=\"collapse"+element.ID+"\" id=\"Header_"+element.ID+"\">"+element.HEADER+"</button><button type=\"button\" class=\"btn btn-outline-secondary rounded-circle pull-right\" onclick=\"editData(\'"+element.ID+"\')\" style=\"padding-bottom: 12px;padding-top: 12px;\" ><i class=\"fa fa-edit\"></i></button></h5></div><div id=\"collapse"+element.ID+"\" class=\"collapse\" aria-labelledby=\"heading"+element.ID+"\" data-parent=\"#accordion\"><div class=\"card-body tabEdit\" id=\"Body_"+element.ID+"\">"+element.BODY+"</div></div></div></div><Br>"
			modelBodyStr += card;
		}
	}
	var modelBody = modelBodyHeadStr + modelBodyStr;
	modelBody = modelBody.replace(new RegExp(",@", 'g'), "<br />");
	console.log(modelBody);
	document.getElementById("modalBody").innerHTML = modelBody;
	$('#dataModal').modal('show');
  }
})

	function editData(str)	{
		var flag = document.getElementById("Body_"+str).contentEditable;
		if(flag == 'inherit')	{
			document.getElementById("Body_"+str).contentEditable = "true";
		} else	{
			document.getElementById("Body_"+str).contentEditable = "inherit";
		}
	}
	
	
	function saveData()	{
		var myStringArray = $('[id^="Header_"]');
		var dataArr = [];
		for (let element of myStringArray) {	
			var id = element.id.split("_")[1];
			var jsonObj = {};
			jsonObj["ID"] = id;
			jsonObj["HEADER"] = document.getElementById("Header_" + id).innerHTML;
			jsonObj["BODY"] = (document.getElementById("Body_" + id).innerHTML).replace(new RegExp("<br />", 'g'), ",@");
			dataArr.push(jsonObj);
		}
		var finalData = {'data' : dataArr};
		
		$.ajax({
			url : "http://localhost:8080/saveModalData",
			type : "POST",
			contentType: "application/text",
			data : JSON.stringify(finalData),
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("textStatus : " + textStatus);
			},
			beforeSend : function() {

			},
			complete : function(jqXHR, textStatus) {
				console.log("textStatus : " + textStatus);
			},
			success : function(responseText) {
				alert("Data Update successfully");
				$('#dataModal').modal('hide');
			}
		});
	}
	
	
	function download() {
		var tableStr = document.getElementById("tableDiv").innerHTML;
		var tableStr = encodeURIComponent($("#tableDiv").html());
		location.href="http://localhost:8080/downloadPdf?table1=" + tableStr;
	}
	


</script>

</head>

<body>
	<br>
	<br>

	<br>
	<br>

	<div class="tableDiv"
		style="width: 100%; display: flex; justify-content: center;"
		id="tableDiv">
		<table id="example" class="table table-hover" style="width: 70%;">
			<thead>
				<tr>
					<th>Name</th>
					<th>Position</th>
					<th>Office</th>
					<th>Age</th>
					<th>Start date</th>
					<th>Salary</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Tiger Nixon</td>
					<td>System Architect</td>
					<td>Edinburgh</td>
					<td>61</td>
					<td>2011-04-25</td>
					<td>$320,800</td>
				</tr>
				<tr>
					<td>Garrett Winters</td>
					<td>Accountant</td>
					<td>Tokyo</td>
					<td>63</td>
					<td>2011-07-25</td>
					<td>$170,750</td>
				</tr>
				<tr>
					<td>Garrett Winters</td>
					<td>Accountant</td>
					<td>Tokyo</td>
					<td>63</td>
					<td>2011-07-25</td>
					<td>$170,750</td>
				</tr>
				<tr>
					<td>Garrett Winters</td>
					<td>Accountant</td>
					<td>Tokyo</td>
					<td>63</td>
					<td>2011-07-25</td>
					<td>$170,750</td>
				</tr>
			</tbody>
		</table>
	</div>
	<BR>
	<BR>
	<div style="width: 100%; display: flex; justify-content: center;">
		<button id="openModal" type="button" class="btn btn-primary"
			style="margin-right: 30px;">Open Modal</button>

		<button type="button" class="btn btn-success" onclick="download()">Download</button>
	</div>

	<!-- The Modal -->

	<div id="dataModal" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header" style="height: 100px;">
					<div id="logo"
						style="position: absolute; width: 100%; display: flex; justify-content: center;">
						<img
							src='data:image/jpeg;base64,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'
							class="img-responsive" alt="Cinque Terre" width="200" height="50" />
					</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div id="modalBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="saveData()">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	<form id="fileinfo" name="fileinfo">
		<div class="custom-file mb-3">
			<input type="file" class="custom-file-input" id="customFile"
				name="filename"> <label class="custom-file-label"
				for="customFile">Choose file</label>
		</div>
		<br> <br> <br>
		<div style="width: 100%; display: flex; justify-content: center;">
			<button id="UplodeFile" type="button" class="btn btn-primary">Uplode
				File</button>
		</div>

	</form>
</body>
</html>
