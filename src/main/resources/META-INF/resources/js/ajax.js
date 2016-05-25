function compare() {
	//var startDate = document.getElementById('startingDateCompare').value;
	//var endDate = document.getElementById('endingDateCompare').value;
	//var initialInput = document.getElementById('initialInputCompare').value;
	//var percentage = document.getElementById('percentageCompare').value;
	
	var startDate = "2011-02-02";
	var endDate = "2011-03-02";
	var initialInput = "1000";
	var percentage = "2";
	document.getElementById("p1").innerHTML = "Enter function";

	var table = document.getElementById("myTableCompare");
	while(table.rows.length > 1) {
		  table.deleteRow(1);
		}
	
	var xhttp = new XMLHttpRequest();
	document.getElementById("p2").innerHTML = xhttp.status;
	xhttp.onreadystatechange = function() {
		document.getElementById('p3').innerHTML = xhttp.status;

		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var pre = xhttp.responseText;
			obj = JSON.parse(pre);
			// alert(obj[0].row[0]); !!!!!!!!! REMEMBER THIS PATTERN
			for ( var i in obj) {
				var id = obj[i].row[0];
				var name = obj[i].row[1];
				var deposit = obj[i].row[2];
				document.getElementById('button2').value = "Chang123123123123123";

				insertRowThree(id, name, deposit);
			}

		}
	};
	
	//document.getElementById('button2').value = "Chang123123123123123";

	
	document.getElementById('p4').innerHTML = xhttp.status;

	xhttp.open("GET", "dataCompare?startdate=" + startDate + "&enddate="+ enddate + "&initialInput=" + initialInput + "&percentage=" + percentage, true);
	//xhttp.open("GET", "dataExchange?startdate=" + startdate + "&enddate=" + enddate, true);

	xhttp.send();
	

}