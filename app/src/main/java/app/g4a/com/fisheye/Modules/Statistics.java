package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.g4a.com.fisheye.R;

public class Statistics extends AppCompatActivity {

    ImageView image_back_statistics;
    GraphView graphDO, graphWT, graphPH;
    String currentDate;
    Date dMax, dMin;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = mdformat.format(calendar.getTime());

        reference = FirebaseDatabase.getInstance().getReference().child("data")
                .child("readings").child(currentDate);

        image_back_statistics = findViewById(R.id.imageview_back_statistics);
        graphDO = findViewById(R.id.graphDO);
        graphWT = findViewById(R.id.graphWT);
        graphPH = findViewById(R.id.graphPH);

        initGraphDO(graphDO);
        initGraphWT(graphWT);
        initGraphPH(graphPH);

        image_back_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Statistics.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initGraphDO(final GraphView graph) {
        // first series is a line
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot dSnap : dataSnapshot.getChildren()) {

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        try {
                            Date d = sdf.parse(dSnap.getKey());
                            double doxy = Double.parseDouble(dSnap.child("dislv_O2").getValue().toString());
                            dp[i] = new DataPoint(d, doxy);
                            i++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                    series.setDrawDataPoints(true);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    try {
                        dMin = sdf.parse("00:00:00");
                        dMax = sdf.parse("04:00:00");

                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(dMin.getTime());
                        graph.getViewport().setMaxX(dMax.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(0d);
                    graph.getViewport().setMaxY(16d);

                    graph.getViewport().setScrollable(true);

                    graph.addSeries(series);

                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                        public String formatLabel(double value, boolean isValueX){
                            if(isValueX){
                                Format formatter = new SimpleDateFormat("HH:mm");
                                return formatter.format(value);
                            }
                            return super.formatLabel(value, isValueX);
                        }
                    });
                }
                else
                    graphDO.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initGraphWT(final GraphView graph) {
        // first series is a line
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        try {
                            Date d = sdf.parse(dSnap.getKey());
                            double water = Double.parseDouble(dSnap.child("water_temp").getValue().toString());
                            dp[i] = new DataPoint(d,water);
                            i++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                    series.setDrawDataPoints(true);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    try {
                        dMin = sdf.parse("00:00:00");
                        dMax = sdf.parse("04:00:00");

                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(dMin.getTime());
                        graph.getViewport().setMaxX(dMax.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(0d);
                    graph.getViewport().setMaxY(100d);

                    graph.getViewport().setScrollable(true);

                    graph.addSeries(series);

                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                        public String formatLabel(double value, boolean isValueX){
                            if(isValueX){
                                Format formatter = new SimpleDateFormat("HH:mm");
                                return formatter.format(value);
                            }
                            return super.formatLabel(value, isValueX);
                        }
                    });
                }
                else
                    graphWT.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initGraphPH(final GraphView graph) {
        // first series is a line
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        try {
                            Date d = sdf.parse(dSnap.getKey());
                            double ph = Double.parseDouble(dSnap.child("pH").getValue().toString());
                            dp[i] = new DataPoint(d,ph);
                            i++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                    series.setDrawDataPoints(true);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    try {
                        dMin = sdf.parse("00:00:00");
                        dMax = sdf.parse("04:00:00");

                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(dMin.getTime());
                        graph.getViewport().setMaxX(dMax.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(1d);
                    graph.getViewport().setMaxY(14d);

                    graph.getViewport().setScrollable(true);

                    graph.addSeries(series);

                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                        public String formatLabel(double value, boolean isValueX){
                            if(isValueX){
                                Format formatter = new SimpleDateFormat("HH:mm");
                                return formatter.format(value);
                            }
                            return super.formatLabel(value, isValueX);
                        }
                    });
                }
                else
                    graphPH.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
