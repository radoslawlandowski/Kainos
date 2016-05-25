 function createChart (obj)
    {
        var line = new RGraph.Line({
            id: 'cvs',
            data: obj,
            options: {
                spline: true,
                splineTickmarksFill: null,
                splineTickmarksSize: 5,
                splineTickmarksStroke: '#aaa',
                splineTickmarksLinewidth: 2,
                linewidth: 3,
                hmargin: 5,
                labels: ['Mon','Tue','Wed','Thu','Fri','Sat','Sun'],
                tooltips: ['Mon','Tue','Wed','Thu','Fri','Sat','Sun'],
                tickmarks: 'circle',
                textAccessible: true
            }
        }).trace2();
    };