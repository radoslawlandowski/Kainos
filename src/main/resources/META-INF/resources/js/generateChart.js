function createChartNew(obj) {

	var canvas = document.getElementById('cvs');
	canvas.width = canvas.width;
	RGraph.ObjectRegistry.clear();
	RGraph.clear(canvas);

	if (obj.length == 0) {
		alert("No data to be displayed, check input dates");
		return;
	}

	var separatedData = [];
	var arraySize = obj.length;
	var arr = obj[0];
	var numOfElements = arr
	for ( var key in arr) {
		var column = [];
		var attrName = key;
		for (var i = 0; i < arraySize; i++) {
			var value = obj[i][key]
			column.push(value);
		}
		separatedData.push(column);
	}
	var dataC = [];
	for (i = 1; i < separatedData.length; i++) {
		dataC.push(separatedData[i]); // Zeroth element of separatedData are
										// labels.
		// First, second, third element and so on are data for plotting the
		// curve
	}

	var labels = [];

	var numberOfElements = separatedData[0].length;
	var factor = Math.floor(numberOfElements / 10);
	for (i = 0; i < numberOfElements; i++) { // get 10 labels regardless of
												// the amount of data.
		if (i % factor == 0) {
			labels.push(separatedData[0][i]);
		} else {
			labels.push("");
		}
	}

	var tooltips = [];

	var line = new RGraph.Line({
		id : 'cvs',
		data : dataC,
		options : {
			ylabelsCount : 10,
			textAngle : 90,
			textSize : 8,
			shadow : true,
			tooltipsHotspotXonly : true,
			tooltipsHotspotSize : 8,
			crosshairs : true,
			labels : labels,
			tooltips : labels,
			xaxispos : 'center',
			backgroundGrid : false,
			outofbounds : true,
			spline : false,
			splineTickmarksFill : null,
			textAccessible : true
		}
	}).trace2();
};
