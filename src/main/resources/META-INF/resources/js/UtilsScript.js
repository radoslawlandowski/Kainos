function areDatesValid() {
	var startElement = document.getElementById('startingDate').value;
	var endElement = document.getElementById('endingDate').value;

	var regEx = /^\d{4}-\d{2}-\d{2}$/;
	var startRegExResult = startElement.match(regEx);
	var endRegExResult = endElement.match(regEx);

	var result = false;
	if (!startRegExResult || !endRegExResult) {
		result = false;
	} else {
		result = true;
	}
	return result;
}

function dateChecker() {
	if (!areDatesValid()) {
		alert("Dates are invalid!\n" + "End date must be after start date!\n"
				+ "Format must be: yyyy-mm-dd! (if entered manually)\n");
		return false;
	} else {
		return true;
	}
}

function insertDataArgumented(argum) {
	var obj = argum;
	var table = document.getElementById("myTable");
	var row = [];
	var cell = [];
	
	for (var i = 0; i < obj.length; i++) {
		row[i] = table.insertRow(table.rows.length);
		var arr = obj[i];
		var j = 0;
		for ( var key in arr) {
			var attrName = key;
			var value = arr[key];
			cell[j] = row[i].insertCell(j);
			cell[j].textContent = value;
			j++;
		}
	}
}

function clearTable(table) {
	while (table.rows.length > 1) {
		table.deleteRow(1);
	}
}
