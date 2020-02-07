package com.example.myclosetapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

import androidx.annotation.NonNull;

public class CircleTransformation implements Transformation {
    private int x;
    private int y;

    @Override
    public Bitmap transform(final @NonNull Bitmap source) {
        final int size = Math.min(source.getWidth(), source.getHeight());

        this.x = (source.getWidth() - size) / 2;
        this.y = (source.getHeight() - size) / 2;

        final Bitmap squaredBitmap = Bitmap.createBitmap(source, this.x, this.y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        final Bitmap.Config config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        final Bitmap bitmap = Bitmap.createBitmap(size, size, config);

        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        final BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        final float r = size/2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle(x=" + this.x + ",y=" + this.y + ")";
    }
}