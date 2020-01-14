package app.g4a.com.fisheye.Modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.g4a.com.fisheye.R;

public class Sample extends AppCompatActivity {

    private int mNumLabels = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        GraphView graph1 = findViewById(R.id.graphsample);
        initGraph(graph1);
    }

    public void initGraph(GraphView graph){
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.HOUR,1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.HOUR,1);
        Date d3 = calendar.getTime();


        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1,1),
                new DataPoint(d2,5),
                new DataPoint(d3,3)
        });

        series.setDrawDataPoints(true);
        series.setAnimated(true);

        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            public String formatLabel(double value, boolean isValueX){
                if(isValueX){
                    Format formatter = new SimpleDateFormat("HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });
        graph.getGridLabelRenderer().setNumHorizontalLabels(mNumLabels);
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0d);
        graph.getViewport().setMaxY(30d);

        graph.getViewport().setScrollable(true);
    }
}
