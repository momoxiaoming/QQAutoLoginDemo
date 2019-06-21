package com.andr.common.tool.net.okhttpUtil.callback;


import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallBack extends Callback
{
    public StringCallBack()
    {
        super();
    }

    @Override
    public String onParse(Response response, int id) throws IOException
    {
        if (response.body() == null)
        {
            return null;
        }

        return new String(response.body().bytes());
    }

    @Override
    public void onResponse(int id, Object object)
    {
        onResponse(id,(String)object);
    }

    public abstract void onResponse(int id, String res);
}

