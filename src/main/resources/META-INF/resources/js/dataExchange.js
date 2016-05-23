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
		insertRow(123, 32);
	}
}

function func(arr) {
	var v = arr[1].row[1];
	insertRow(arr[1].row[1], arr[1].row[2]);
	insertRow(10, 10);

}

function testing() {
	insertRow(15, 15);
}

function getData(startdate, enddate) {
	var table = document.getElementById("myTable");
	while(table.rows.length > 1) {
		  table.deleteRow(1);
		}
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var pre = xhttp.responseText;
			obj = JSON.parse(pre);
			// alert(obj[0].row[0]); !!!!!!!!! REMEMBER THIS PATTERN
			document.getElementById("demo").innerHTML = document.getElementById('startingDate').value;
			for ( var i in obj) {
				var id = obj[i].row[0];
				var name = obj[i].row[1];

				insertRow(id, name);
			}

		}
	};
	xhttp.open("GET", "dataExchange?startdate=" + startdate + "&enddate="
			+ enddate, true);
	xhttp.send();
}
