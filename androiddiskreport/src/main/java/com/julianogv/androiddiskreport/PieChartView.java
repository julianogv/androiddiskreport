package com.julianogv.androiddiskreport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.julianogv.androiddiskreport.database.FileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juliano.vieira on 14/01/14.
 */
public class PieChartView extends View {
    private Paint p;
    private int startX;
    private int startY;
    private int radius;
    private ArrayList<Integer> colors;
    private ArrayList<Float> values;
    Bitmap bitmap;
    Context mContext;
    List<ColorDictionary> colorDictionaries;

    public PieChartView(Context context, List<FileEntity> directories) {
        super(context);
        colorDictionaries = new ArrayList<ColorDictionary>();

        mContext = context;

        p = new Paint();
        p.setAntiAlias(true);

        colors = new ArrayList<Integer>();
        values = new ArrayList<Float>();

        startX = 0;
        startY = 0;
        radius = 1000;

        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);

        values.add(5f);
        values.add(1f);
        values.add(3f);
        values.add(5f);
        values.add(2f);
        values.add(2f);

    }

    private float[] calculateData(float[] data) {
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }
        return data;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);

        Log.e("JULIANOJ", "onDraw() is called...");

        float offset = 0;
        float sum = 0;
        for (int a = 0; a < values.size(); a++) {
            sum += values.get(a);
        }

        float angle = (float) (360 / sum);

        Log.e("angle", "" + angle);

        RectF rectF = new RectF();
        rectF.set(getStartX(), getStartY(), getStartX() + getRadius(),
                getStartY() + getRadius());

        for (int i = 0; i < values.size(); i++) {

            p.setColor(colors.get(i));

            if (i == 0) {
                canvas.drawArc(rectF, 0, values.get(i) * angle, true, p);
                c.drawArc(rectF, 0, values.get(i) * angle, true, p);
            } else {
                canvas.drawArc(rectF, offset, values.get(i) * angle, true, p);
                c.drawArc(rectF, offset, values.get(i) * angle, true, p);
            }

            offset += (values.get(i) * angle);
        }

        canvas.save();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int color = bitmap.getPixel((int) event.getX(), (int) event.getY());

        Log.e("", "" + color);

        if (colors.contains(color)) {
            Log.e("", "is matching");
            if (color == Color.RED) {
                Toast.makeText(mContext, "Is Red", Toast.LENGTH_SHORT).show();
            }

            if (color == Color.CYAN) {
                Toast.makeText(mContext, "Is Cyan", Toast.LENGTH_SHORT).show();
            }

            if (color == Color.MAGENTA) {
                Toast.makeText(mContext, "Is MAGENTA", Toast.LENGTH_SHORT)
                        .show();
            }
            if (color == Color.BLUE) {
                Toast.makeText(mContext, "Is BLUE", Toast.LENGTH_SHORT).show();
            }
            if (color == Color.GREEN) {
                Toast.makeText(mContext, "Is GREEN", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onTouchEvent(event);
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public ArrayList<Float> getValues() {
        return values;
    }

    public void setValues(ArrayList<Float> values) {
        this.values = values;
    }

}
