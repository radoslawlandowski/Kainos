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
	var table = document.getElementById("myTableCompared");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);

	cell1.innerHTML = date;
	cell2.innerHTML = value;
	cell3.innerHTML = deposit;

}

function createTableArgumented() {
	var table = document.getElementById("myTable");
	var row = table.insertRow(0);
	for(var i = 0 ; i < arguments.length; i++) {
		var cell = row.insertCell(i);
		cell.innerHtml = arguments[i];
	}
}

function insertData(obj) {
	for ( var i in obj) {
		var id = obj[i].row[0];
		var name = obj[i].row[1];
		insertRow(id, name);
	}
}

function clearTable(table) {
	while(table.rows.length > 1) {
		  table.deleteRow(1);
		}
}

function getData() {
	var table = document.getElementById("myTable");
	var startdate = document.getElementById('startingDate').value;
	var enddate = document.getElementById('endingDate').value
	
	clearTable(table);
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var obj = JSON.parse(xhttp.responseText);
			insertData(obj);
			drawChart(obj);
		}
	};
	
	xhttp.open("GET", "dataExchange?startdate=" + startdate + "&enddate=" + enddate, true);
	xhttp.send();
}

function exampleFunc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			alert("goodbye");
		}
	};
	xhttp.open("GET", "example2", true);
	xhttp.send();
}

function compare2() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			alert("goodbye");
		}
	};
	xhttp.open("GET", "example2", true);
	xhttp.send();
}

function compare() {
	//var startDate = document.getElementById('startingDateCompare').value;
	//var endDate = document.getElementById('endingDateCompare').value;
	//var initialInput = document.getElementById('initialInputCompare').value;
	//var percentage = document.getElementById('percentageCompare').value;
	
	var startDate = "2011-02-02";
	var endDate = "2011-05-02";
	var initialInput = "33";
	var percentage = "2";
	document.getElementById("p1").innerHTML = "Enter function";

	var table = document.getElementById("myTableCompared");
	while(table.rows.length > 1) {
		  table.deleteRow(1);
		}
	
	var xhttp = new XMLHttpRequest();
	document.getElementById("p2").innerHTML = xhttp.status;
	xhttp.onreadystatechange = function() {
		document.getElementById('p3').innerHTML = xhttp.status;

		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var pre = xhttp.responseText;
			var obj = JSON.parse(pre);
			// alert(obj[0].row[0]); !!!!!!!!! REMEMBER THIS PATTERN
			for ( var i in obj) {
				var id = obj[i].row[0];
				var name = obj[i].row[1];
				var deposit = obj[i].row[2];
				document.getElementById('button2').value = "Loop: " + i + "times";

				insertRowThree(id, name, deposit);
			}

		}
	};
	
	//document.getElementById('button2').value = "Chang123123123123123";

	
	document.getElementById('p4').innerHTML = xhttp.status;

	//xhttp.open("GET", "dataCompare?startdate=" + startDate + "&enddate="+ enddate + "&initialInput=" + initialInput + "&percentage=" + percentage, true);
	xhttp.open("GET", "dataCompare?startdate=" + startDate + "&enddate=" + endDate + "&initialInput=" + initialInput + "&percentage=" + percentage, true);
	//xhttp.open("GET", "dataExchange?startdate=" + startdate + "&enddate=" + enddate, true);

	xhttp.send();
	

}

	
// alert(obj[0].row[0]); !!!!!!!!! REMEMBER THIS PATTERN


