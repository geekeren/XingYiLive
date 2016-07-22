package com.tencent.snglive.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 圆角图片(只针对 Bitmap 生效).
 *
 * @author arnozhang
 */
public class RoundImageView extends ImageView {

    private static float roundPx =0;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);

        updateRound();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);

        updateRound();
    }

    public static Bitmap generateRoundBitmap(Resources res, Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap bkg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bkg);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 绘制 mask.
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        // 绘制底图.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);

        return bkg;
    }

    private void updateRound() {
        Drawable drawable = getDrawable();
        if (drawable == null || !(drawable instanceof BitmapDrawable)) {
            return ;
        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap srcBmp = bitmapDrawable.getBitmap();
        if (srcBmp == null) {
            return ;
        }

        Bitmap roundBmp = generateRoundBitmap(getResources(), srcBmp);
        RoundImageView.super.setImageDrawable(
                new BitmapDrawable(getResources(), roundBmp));
    }

    public void setRoundPx(float r){
        roundPx = r;
    }
}
