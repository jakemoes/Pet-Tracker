package com.example.pettracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class Chart extends View{
    private Paint paint;

    // Constructor for programmatically creating the view
    public Chart(Context context) {
        super(context);
        init();
    }

    // Constructor for XML inflation
    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Constructor with style attributes
    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // Initialize the paint or any resources
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE); // Set a default color
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw a simple circle as a placeholder
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(getWidth(), getHeight()) / 4f;
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}
