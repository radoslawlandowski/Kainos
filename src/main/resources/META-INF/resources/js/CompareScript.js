function compare() {
	var startDate = document.getElementById('startingDate').value;
	var endDate = document.getElementById('endingDate').value;
	var initialInput = document.getElementById('inputValue').value;
	var percentage = document.getElementById('percentageValue').value;
	
	if(!dateChecker()) {
		return;
	}

	var table = document.getElementById("myTableCompared");
	clearTable(table);
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var pre = xhttp.responseText;
			var obj = JSON.parse(pre);
			for ( var i in obj) {
				var date = obj[i].row[0];
				var fund = obj[i].row[1];
				var depo = obj[i].row[2];
				insertRowThree(date, fund, depo);
			}
			createChartNew(obj);
		}
	};
	xhttp.open("GET", "dataCompare?startdate=" + startDate + "&enddate=" + endDate + "&initialInput=" + initialInput + "&percentage=" + percentage, true);
	xhttp.send();
}