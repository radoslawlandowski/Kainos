function areDatesValid() {
	var startElement = document.getElementById('startingDate').value;
	var endElement = document.getElementById('endingDate').value;
	
	var startDate = new Date(startElement);
	var endDate = new Date(endElement);
	var startTime = startDate.getTime();
	var endTime = endDate.getTime();
	
	var upperLimit = '2016-05-12';
	var lowerLimit = '1998-01-05';
	var upperDate = new Date(upperLimit);
	var lowerDate = new Date(lowerLimit);
	var upperTime = upperDate.getTime();
	var lowerTime = lowerDate.getTime();
	
	var regEx = /^\d{4}-\d{2}-\d{2}$/;
	var startRegExResult = startElement.match(regEx);
	var endRegExResult = endElement.match(regEx);
	
	var result = false;
	if(startTime > endTime || startTime < lowerTime || endTime > upperTime || !startRegExResult || !endRegExResult) {
		result = false;
	} else {
		result = true;
	}
	return result;
	
}


function dateChecker() {
	if(!areDatesValid()) {
		alert("Dates are invalid!\n" +
				"End date must be after start date!\n" +
				"Format must be: yyyy-mm-dd! (if entered manually)\n" +
				"Dates must be between: 1998-01-05 and 2016-05-12!");
		return false;
	} else {
		return true;
	}
}

function insertData(obj) {
	for ( var i in obj) {
		var id = obj[i].row[0];
		var name = obj[i].row[1];
		insertRow(id, name);
	}
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

function insertDataArgumented(argum) {
	var obj = argum;
	var table = document.getElementById("myTable");
	for(var i = 0 ; i < obj.length; i++) {
		var row = table.insertRow(1);
		for(var j = 0 ; j < obj[i].row.length ; j++) {
			var cell = row.insertCell(j);
			cell.innerHtml = obj[i].row[j];
		}
	}
}

function clearTable(table) {
	while(table.rows.length > 1) {
		  table.deleteRow(1);
		}
}

