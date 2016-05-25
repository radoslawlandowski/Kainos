 function createChart(obj)
    {
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
                spline: true,
                splineTickmarksFill: null,
                splineTickmarksSize: 5,
                splineTickmarksStroke: '#aaa',
                splineTickmarksLinewidth: 2,
                linewidth: 3,
                hmargin: 5,
                labels: dates,
                tooltips: values,
                tickmarks: 'circle',
                textAccessible: true
            }
        }).trace2();
    };
 