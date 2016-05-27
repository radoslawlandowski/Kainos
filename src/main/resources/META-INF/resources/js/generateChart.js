 function createChart(obj)
    {
	 var canvas = document.getElementById('cvs');
	 canvas.width = canvas.width;
	 RGraph.ObjectRegistry.clear();
	 RGraph.clear(canvas);
	 
	 var dates = [];
	 for ( var i=0,j=obj.length;i<j;i++ ) {
	     dates.push(obj[i].row[0]);
	 }

	 var values = [];
	 for ( var i=0,j=obj.length;i<j;i++ ) {
	     values.push(obj[i].row[1]);
	 }
        var line = new RGraph.Line({
            id: 'cvs',
            data: values,
            options: {
                spline: false,
                splineTickmarksFill: null,
                textAccessible: true
            }
        }).trace2();
    };
 
    
function createChartNew(obj) {
	 var canvas = document.getElementById('cvs');
	 canvas.width = canvas.width;
	 RGraph.ObjectRegistry.clear();
	 RGraph.clear(canvas);

	 var separatedData = [];
	 var l = obj.length;
	 var k = obj[0].row.length // check how many lines to draw
	 for(j = 0 ; j < k; j++) {
		 var column = [];
		 for ( var i=0; i < l ; i++ ) {
		     column.push(obj[i].row[j]);
		 }
		 separatedData.push(column);
	 }
	 var dataC = [];
	 for(i = 1 ; i < separatedData.length ; i++) {
		 dataC.push(separatedData[i]); // Zeroth element of separatedData are labels. 
		 							  // First, second, third element and so on are data for plotting the curve
	 }
        var line = new RGraph.Line({
            id: 'cvs',
            data: dataC,
            options: {
            	xaxispos: 'center',
            	backgroundGrid: false,
            	outofbounds: true,
                spline: true,
                splineTickmarksFill: null,
                textAccessible: true
            }
        }).trace2();
    };
 