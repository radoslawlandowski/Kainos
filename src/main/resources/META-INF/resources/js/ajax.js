function insertRow(date, value) {
	var names = ['experiment'];
	var tableRef = document.getElementById('experiment').getElementsByTagName('tbody')[0];
	var newRow   = tableRef.insertRow(tableRef.rows.length);

	var newCell1  = newRow.insertCell(0);
	var newCell2  = newRow.insertCell(1);
	var newText1  = document.createTextNode('Date');
	var newText2  = document.createTextNode('Value');

	newCell1.appendChild(newText1);
	newCell2.appendChild(newText2);

}


