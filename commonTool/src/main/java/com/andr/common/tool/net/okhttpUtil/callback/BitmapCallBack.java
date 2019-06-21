package com.andr.common.tool.net.okhttpUtil.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

public abstract class BitmapCallBack extends Callback
{
    public BitmapCallBack()
    {
        super();
    }

    public Bitmap onParse(Response response, int id)
    {
        if (response.body() == null)
        {
            return null;
        }

        return BitmapFactory.decodeStream(response.body().byteStream());
    }

    public abstract void onResponse(int id, Bitmap bitmap);

    @Override
    public void onResponse(int id, Object object)
    {
        onResponse(id, (Bitmap) object);
    }
}

