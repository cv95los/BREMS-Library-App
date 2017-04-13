var Highcharts;

Highcharts.chart('container', {
    chart: {
        zoomType: 'xy'
    },
    title: {
        text: 'Cantidad de préstamos mensuales en BREMS'
    },
    subtitle: {
        text: 'Datos de la base de datos de BREMS'
    },
    xAxis: [{
        categories: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
            'Jul', 'Ago', 'Set', 'Oct', 'Nov', 'Dic'],
        crosshair: true
    }],
    yAxis: [{ // Primary yAxis
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        },
        title: {
            text: 'Libros',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        }
    }, { // Secondary yAxis
        title: {
            text: 'Revistas',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        },
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        },
        opposite: true
    }],
    tooltip: {
        shared: true
    },
    legend: {
        layout: 'vertical',
        align: 'left',
        x: 50,
        verticalAlign: 'top',
        y: 10,
        floating: true,
        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
    },
    series: [{
        name: 'Revistas',
        type: 'column',
        yAxis: 1,
        data: [10, 15, 13, 12, 6, 16, 30, 13, 15, 16, 4, 17],
        tooltip: {
            valueSuffix: ' unidades'
        }

    }, {
        name: 'Libros',
        type: 'spline',
        data: [20, 15, 30, 27, 17, 16, 28, 33, 14, 18, 20, 17],
        tooltip: {
            valueSuffix: ' unidades'
        }
    }]
});
