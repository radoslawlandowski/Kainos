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

function insertRowThree(date, value, deposit) {
	var table = document.getElementById("myTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);

	cell1.innerHTML = date;
	cell2.innerHTML = value;
	cell3.innerHTML = deposit;

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
function compare() {
	var startDate = document.getElementById('startingDateCompare').value;
	var endDate = document.getElementById('endingDateCompare').value;
	document.getElementById('button').value = "Changed";
	var initialInput = document.getElementById('initialInputCompare').value;
	var percentage = document.getElementById('percentageCompare').value;
	
	var table = document.getElementById("myTableCompare");
	while(table.rows.length > 1) {
		  table.deleteRow(1);
		}
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var pre = xhttp.responseText;
			obj = JSON.parse(pre);
			// alert(obj[0].row[0]); !!!!!!!!! REMEMBER THIS PATTERN
			for ( var i in obj) {
				var id = obj[i].row[0];
				var name = obj[i].row[1];
				var deposit = obj[i].row[2];

				insertRowThree(id, name, deposit);
			}

		}
	};
	xhttp.open("GET", "dataCompare?startdate=" + startDate + "&enddate="+ enddate + "&initialInput=" + initialInput + "&percentage=" + percentage, true);
	xhttp.send();
}
	


