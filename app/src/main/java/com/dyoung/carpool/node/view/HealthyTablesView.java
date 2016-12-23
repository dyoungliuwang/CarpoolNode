package com.dyoung.carpool.node.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.dyoung.carpool.node.R;

import java.util.Arrays;

/**
 * Created by admin on 2016/12/9.
 */
public class HealthyTablesView extends View {

    private int mWidth;
    private int mHeight;
    /**
     * 坐标轴宽度
     */
    private int mCoordinatesLineWidth;

    /**
     * 坐标旁边文字颜色
     */
    private int mCoordinatesTextColor;

    /**
     * 坐标旁边文字大小
     */
    private int mCoordinatesTextSize;

    /**
     * 折线颜色
     */
    private int mLineColor;

    /**
     * 圆点半径
     */
    private int mCircleradius;

    /**
     * 背景色
     */
    private int mBgColor;

    /**
     * 折线宽度
     */
    private int mLineWidth;

    /**
     * 小圆填充色
     */
    private int mMincircleColor;

    /**
     * 绘制类型,比如,画步数,画心率,画睡眠等
     */
    private String mDrawType;

    /**
     * 大圆点填充色
     */
    private int mMaxcircleColor;

    private Paint xyPaint;

    private Rect textBound;

    private String[] weeks;

    private Paint textPaint;

    private int[] values;

    private Paint linePaint;

    private Paint maxCirclePaint;

    private Paint minCirclePaint;

    private int XScale;

    public HealthyTablesView(Context context) {
        this(context, null);
    }

    public HealthyTablesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HealthyTablesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.HealthyTableView, defStyleAttr, 0);
        int index = array.getIndexCount();
        for (int i = 0; i < index; i++)
        {
            int attr = array.getIndex(i);

            switch (attr)
            {
                case R.styleable.HealthyTableView_coordinatesLineWidth:
                    // 这里将以px为单位,默认值为2px;
                    mCoordinatesLineWidth = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_PX, 2,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HealthyTableView_coordinatesTextColor:
                    mCoordinatesTextColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.HealthyTableView_coordinatesTextSize:
                    mCoordinatesTextSize = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_SP, 11,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HealthyTableView_lineColor:
                    mLineColor = array.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.HealthyTableView_averageCircleradius:
                    mCircleradius = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 10,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HealthyTableView_bgColor:
                    mBgColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.HealthyTableView_lineWidth:
                    mLineWidth = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 11,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HealthyTableView_maxcircleColor:
                    mMaxcircleColor = array.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.HealthyTableView_mincircleColor:
                    mMincircleColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.HealthyTableView_tableType:
                    mDrawType = array.getString(attr);
                    break;
            }
        }
        // 记得释放资源
        array.recycle();
        init();
    }

    private void init(){
        weeks = new String[]{ "02", "03", "04", "05", "06", "07", "08" };
        values = new int[7];

        xyPaint = new Paint();
        xyPaint.setAntiAlias(true);
        xyPaint.setColor(mCoordinatesTextColor);
        xyPaint.setStrokeWidth(mCoordinatesLineWidth);
        xyPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(mCoordinatesTextColor);
        textPaint.setTextSize(mCoordinatesTextSize);
        textPaint.setStyle(Paint.Style.STROKE);
        textBound = new Rect();

        minCirclePaint = new Paint();
        minCirclePaint.setStyle(Paint.Style.FILL);
        minCirclePaint.setColor(Color.WHITE);
        minCirclePaint.setAntiAlias(true);

        maxCirclePaint = new Paint();
        maxCirclePaint.setStyle(Paint.Style.FILL);
        maxCirclePaint.setColor(mMaxcircleColor);
        maxCirclePaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(mLineColor);
        linePaint.setStrokeWidth(mLineWidth);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        /**
         * 自定义控件的宽高必须由调用者自己指定具体的数值
         */
        if (widthSpecMode == MeasureSpec.EXACTLY)
        {
            mWidth = widthSpecSize;
        }
        else
        {
            mWidth = 300;

        }

        if (heightSpecMode == MeasureSpec.EXACTLY)
        {
            //高是宽的3/5,这样好吗?
            mHeight = (mWidth / 5) * 3;
        }
        else
        {
            mHeight = 230;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画坐标系
        drawCoordinates(canvas);
        //画x刻度
        drawCoordinatesXvalues(canvas);
        //画y轴刻度

        values = new int[]{ 67, 66, 68, 74, 77, 128, 98 };
        int[] valuesTemp= Arrays.copyOf(values,values.length);
        Arrays.sort(valuesTemp);
        int max=valuesTemp[valuesTemp.length-1];
        int min=valuesTemp[0];
        int []yValues=cacluterYValues(max,min);
        drawYValues(canvas,yValues[4]-yValues[0],yValues);
        //画折线图
        drawLine(canvas,yValues[4]-yValues[0],yValues[0]);

    }

    private  void drawLine(Canvas canvas,float arrayMax,float yMin){
        float YScale = ((mHeight - getPaddingBottom() - getPaddingTop() - 40))/ arrayMax;
        for(int x=0;x<values.length;x++){
            int y;
            int scale = (int) (values[x] - yMin);
            if(x<6){
                int textScale = (int) (values[x + 1] - yMin);
                y=x+1;
                canvas.drawLine(getPaddingLeft() + XScale * x, mHeight - getPaddingBottom() - YScale * scale, getPaddingLeft() + XScale * y, mHeight - getPaddingBottom() - YScale * textScale, linePaint);
            }
            String text = String.valueOf(values[x]);
            textPaint.getTextBounds(text, 0, text.length(), textBound);
            canvas.drawText(text, getPaddingLeft() + (XScale * x) - textBound.width() / 2, mHeight - getPaddingBottom() - (YScale * scale) - 15, textPaint);
            canvas.drawCircle(getPaddingLeft() + (XScale * x), mHeight - getPaddingBottom() - (YScale * scale), 10, maxCirclePaint);
            canvas.drawCircle(getPaddingLeft() + (XScale * x),  mHeight - getPaddingBottom() - (YScale * scale), 10 - 2,  minCirclePaint);

        }
    }
    //画坐标系s
    private void drawCoordinates(Canvas canvas){
        //画x轴
        canvas.drawLine(getPaddingLeft(), mHeight - getPaddingBottom(), mWidth - getPaddingRight(), mHeight - getPaddingBottom(), xyPaint);
        //画x轴箭头
        canvas.drawLine(mWidth-getPaddingRight()-20,mHeight-getPaddingBottom()-10,mWidth-getPaddingRight(),mHeight-getPaddingBottom(),xyPaint);
        canvas.drawLine(mWidth - getPaddingRight() - 20, mHeight - getPaddingBottom() + 10, mWidth - getPaddingRight(), mHeight - getPaddingBottom(), xyPaint);
        // 绘制Y轴
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), mHeight - getPaddingBottom(), xyPaint);
        //画y轴箭头
        canvas.drawLine(getPaddingLeft()-10,getPaddingTop()+20,getPaddingLeft(),getPaddingTop(),xyPaint);
        canvas.drawLine(getPaddingLeft() + 10, getPaddingTop() + 20, getPaddingLeft(), getPaddingTop(), xyPaint);
    }

    //画x轴刻度
    private void drawCoordinatesXvalues(Canvas canvas){
        XScale=(mWidth-getPaddingRight()-getPaddingLeft()-40)/6;
        for(int x=0;x<weeks.length;x++){
            textPaint.getTextBounds(weeks[x],0,weeks[x].length(),textBound);
            canvas.drawLine(getPaddingLeft() + (x * XScale), mHeight - getPaddingBottom(), getPaddingLeft() + x * XScale, mHeight - getPaddingBottom()-10,xyPaint);
            canvas.drawText(weeks[x],getPaddingLeft() + (x * XScale) -textBound.width()/2,mHeight-getPaddingBottom()+30,textPaint);
        }
    }

    private void drawYValues(Canvas canvas,float max,int[] values){
        float YScale=(mHeight-getPaddingBottom()-getPaddingTop()-40)/max;
        for(int x=0;x<values.length;x++){
            int scale = values[x] - values[0];
            textPaint.getTextBounds(values[x]+"",0,(values[x]+"").length(),textBound);
            canvas.drawText(values[x] + "", getPaddingLeft() - 80, mHeight - getPaddingBottom() - (YScale * scale) + textBound.height() / 2, textPaint);
            canvas.drawLine(getPaddingLeft(),mHeight - getPaddingBottom() - (YScale * scale),mWidth-getPaddingRight(),mHeight - getPaddingBottom() - (YScale * scale),xyPaint);

        }
    }

    private int[] cacluterYValues(float max, float min)
    {
        int[] values;
        int min1;
        int max1;
        int resultNum = getResultNum(min); // 计算出的最小值
        max1 = getResultNum(max); // 计算出最大值
        if (resultNum <= 20) // 如果小于等于20 就不要减20,否则Y最小值是0了
        {
            min1 = resultNum - 10;
        }
        else
        {

            min1 = resultNum - 20;
        }

        //步行特殊处理
        if (resultNum >= 1000)
        {
            min1 = resultNum - 1000;
        }

        if (resultNum <= 10 || resultNum == 0) // 如果小于10 就不用再减了,否则就是负数了
        {
            min1 = 0;
        }

        // 将计算出的数值均分为5等分
        double ceil = Math.ceil((max1 - min1) / 4);
        values = new int[]
                { min1, (int) (min1 + ceil), (int) (min1 + ceil * 2),
                        (int) (min1 + ceil * 3), (int) (min1 + ceil * 4) };
        return values;

    }

    private int getResultNum(float num)
    {
        int resultNum;
        int gw = 0; // 个位
        int sw = 0; // 十位
        int bw = 0; // 百位
        int qw = 0; // 千位
        int ww = 0; // 万位

        if (num > 0)
        {
            gw = (int) (num % 10 / 1);
        }
        if (num > 10)
        {
            sw = (int) (num % 100 / 10);
        }

        if (num > 100)
        {
            bw = (int) (num % 1000 / 100);
        }

        if (num > 1000)
        {
            qw = (int) (num % 10000 / 1000);
        }

        if (num > 10000)
        {
            ww = (int) (num % 100000 / 10000);
        }
        /*********************************/
        if (ww >= 1)
        {
            resultNum = qw > 5 ? ww * 10000 + 10000 : ww * 10000 + 5000;
        }
        else if (qw >= 1)
        {
            resultNum = bw > 5 ? qw * 1000 + 1000 : qw * 1000 + 500;
        }
        else if (bw >= 1)
        {
            resultNum = bw * 100 + sw * 10 + 10;

        }
        else if (sw >= 1)
        {

            resultNum = gw > 5 ? sw * 10 + 20 : sw * 10 + 10;
        }
        else
        {
            resultNum = 0;
        }

        return resultNum;
    }



}
