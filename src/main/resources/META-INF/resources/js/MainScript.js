function getData() {
	var table = document.getElementById("myTable");
	var startDate = document.getElementById('startingDate').value;
	var endDate = document.getElementById('endingDate').value
	
	if(!dateChecker()) {
		return;
	}
	
	clearTable(table);
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var obj = JSON.parse(xhttp.responseText);
			insertData(obj);
			createChartNew(obj);
		}
	};
	xhttp.open("GET", "dataExchange?startdate=" + startDate + "&enddate=" + endDate, true);
	xhttp.send();
}