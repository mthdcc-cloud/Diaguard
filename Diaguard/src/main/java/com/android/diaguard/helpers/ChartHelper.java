package com.android.diaguard.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;

import com.android.diaguard.R;
import com.android.diaguard.database.Event;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by Filip on 01.12.13.
 */
public class ChartHelper {

    public final int CHART_OFFSET_LEFT = 3;
    public final int CHART_OFFSET_RIGHT = 1;

    Activity activity;

    public XYMultipleSeriesDataset seriesDataset;
    public XYMultipleSeriesRenderer renderer;
    public GraphicalView chartView;

    public ChartHelper(Activity activity) {
        seriesDataset = new XYMultipleSeriesDataset();
        renderer = new XYMultipleSeriesRenderer();
        this.activity = activity;
    }

    public void initialize() {

        // Needed for empty chart to render labels correctly (WTF?)
        XYSeriesRenderer rendererFake = new XYSeriesRenderer();
        renderer.addSeriesRenderer(rendererFake);
        XYSeries seriesFake = new XYSeries("");
        seriesDataset.addSeries(seriesFake);
        seriesFake.add(-999, -999);

        chartView = ChartFactory.getLineChartView(activity, seriesDataset, renderer);
    }

    public void render() {
        renderBasics();
        renderTime();
    }

    private void renderBasics() {
        renderer.removeAllRenderers();

        renderer.setClickEnabled(true);
        renderer.setSelectableBuffer(10);
        renderer.setAntialiasing(true);

        renderer.setPanEnabled(false , false);
        renderer.setZoomEnabled(false, false);
        renderer.setShowLegend(false);
        renderer.setShowGrid(false);
        renderer.setGridColor(Color.GRAY);
        renderer.setAxesColor(Color.DKGRAY);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        renderer.setPointSize(Helper.getDPI(activity, 4));

        renderer.setMargins(new int[]{0, 0, 0, 0});
        renderer.setMarginsColor(activity.getResources().getColor(R.color.ltgray));

        renderer.setLabelsTextSize(Helper.getDPI(activity, 12));
        renderer.setLabelsColor(Color.DKGRAY);

        renderer.setXLabelsColor(Color.DKGRAY);
        renderer.setXRoundedLabels(false);

        renderer.setYLabelsColor(0, Color.DKGRAY);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setYLabelsPadding(Helper.getDPI(activity, 100));
    }

    private void renderTime() {
        renderer.setXAxisMin(0 - CHART_OFFSET_LEFT);
        renderer.setXAxisMax(24 + CHART_OFFSET_RIGHT);
        renderer.setXLabels(0);
        for(int hour = 0; hour <= 24; hour = hour + 2)
            renderer.addXTextLabel(hour, Integer.toString(hour));

        renderer.setYLabels(6);
        float minimum = new PreferenceHelper(activity).
                formatDefaultToCustomUnit(Event.Category.BloodSugar,
                        activity.getResources().getIntArray(R.array.bloodsugar_extrema)[0]);
        renderer.setYAxisMin(minimum);
    }
}
