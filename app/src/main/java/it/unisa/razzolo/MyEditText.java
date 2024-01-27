package it.unisa.razzolo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText{
    private Paint arrowPaint;

    public MyEditText(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        arrowPaint = new Paint();
        arrowPaint.setColor(Color.BLACK);
        arrowPaint.setStyle(Paint.Style.STROKE);
        arrowPaint.setStrokeWidth(dpToPx(5));

        super.setBackground(getResources().getDrawable(R.drawable.box_corners_white));
        super.setPadding(0,0,0,0);
        super.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        super.setTypeface(super.getTypeface(), android.graphics.Typeface.BOLD);
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        super.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals(""))
                            return source;
                        String s = Character.toString(source.charAt(0));
                        if (s.matches("[a-z]+"))
                            return s.toUpperCase();
                        if (s.matches("[A-Z]+"))
                            return s;
                        return "";
                    }
                }
        });
        super.setTextColor(Color.BLACK);
        final int size = dpToPx(60);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        final int margin = dpToPx(5);
        layoutParams.setMargins(margin, margin, margin, margin);
        super.setLayoutParams(layoutParams);
    }

    int dpToPx(int dp) {
        final float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // drawArrows(canvas);
    }

    private void drawArrows(Canvas canvas) {
        float cellWidth = getWidth();
        float cellHeight = getHeight();
        
        float centerX = cellWidth / 2;
        float centerY = cellHeight / 2;

        drawArrow(canvas, centerX, centerY, centerX, centerY - cellHeight / 2);
        drawArrow(canvas, centerX, centerY, centerX, centerY + cellHeight / 2);
        drawArrow(canvas, centerX, centerY, centerX + cellWidth / 2, centerY);
        drawArrow(canvas, centerX, centerY, centerX - cellWidth / 2, centerY);
        drawArrow(canvas, centerX, centerY, centerX - cellWidth / 2, centerY - cellHeight / 2);
        drawArrow(canvas, centerX, centerY, centerX + cellWidth / 2, centerY - cellHeight / 2);
        drawArrow(canvas, centerX, centerY, centerX - cellWidth / 2, centerY + cellHeight / 2);
        drawArrow(canvas, centerX, centerY, centerX + cellWidth / 2, centerY + cellHeight / 2);
    }

    private void drawArrow(Canvas canvas, float startX, float startY, float endX, float endY) {
        canvas.drawLine(startX, startY, endX, endY, arrowPaint);
    }
    
}
