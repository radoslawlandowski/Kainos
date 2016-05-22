function createTable() {
    var table = document.getElementById("myTable");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    cell1.innerHTML = "Date";
    cell2.innerHTML = "Value";
}

function insertRow(date, value) {
	var table = document.getElementById("myTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	cell1.innerHTML = date;
	cell2.innerHTML = value;
}

function insertMultipleRows() {
	for (i = 0; i < 5; i++) { 
	    insertRow(123,32);
	}
}

function getDate(startdate, enddate) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
     document.getElementById("myTable").innerHTML = xhttp.responseText;
     insertRow();
    }
  };
  xhttp.open("GET", "dataExchange?startdate="+startdate+"&enddate="+enddate, true);
  xhttp.send();
}