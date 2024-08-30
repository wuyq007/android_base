package com.pers.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.pres.common.view.R;


public class UnderlineTextView extends androidx.appcompat.widget.AppCompatTextView {

    private final Paint mUnderlinePaint = new Paint();

    /**
     * 下划线颜色
     */
    private @ColorInt int underLineColor;
    /**
     * 下划线高度
     */
    private float underlineHeight;
    /**
     * 下划线和文字距离
     */
    private float underlinePaddingTop;

    public UnderlineTextView(Context context) {
        this(context, null);
    }

    public UnderlineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnderlineTextView);
        underLineColor = typedArray.getColor(R.styleable.UnderlineTextView_underline_color, Color.parseColor("#740AB8"));
        underlineHeight = typedArray.getDimension(R.styleable.UnderlineTextView_underline_height, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        underlinePaddingTop = typedArray.getDimension(R.styleable.UnderlineTextView_underline_padding, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));

        //底部下划线可能因为距离不够而显示不下，设置一个 paddingBottom
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), (int) (underlineHeight + underlinePaddingTop + 1));
    }


    //绘制下划线
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这个组件可能给它设置了边距
        float paddingTop = getCompoundPaddingTop();
        float paddingLeft = getCompoundPaddingLeft();

        //文字高度
        final int fontHeight = getPaint().getFontMetricsInt(null);

        mUnderlinePaint.setColor(underLineColor);
        mUnderlinePaint.setStrokeWidth(underlineHeight);

        Layout layout = getLayout();

        int lineCount = getLineCount();
        //画线
        for (int i = 0; i < lineCount; i++) {
            //下划线左边位置
            float lineLeft = layout.getLineLeft(i) + paddingLeft;
            //下划线位置 = 左边位置 + 文字的宽度
            float lineRight = lineLeft + layout.getLineWidth(i);
            float underlineY = paddingTop + layout.getLineTop(i) + fontHeight + underlinePaddingTop + underlineHeight / 2;
            canvas.drawLine(lineLeft, underlineY, lineRight, underlineY, mUnderlinePaint);
            canvas.save();
        }
    }

}
