package com.andr.common.tool.net.okhttpUtil.callback;

import com.google.gson.Gson;

import okhttp3.Response;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public abstract class BeanCallback extends Callback
{
    private Class clazz;


    public Object onParse(Response response, int id) throws Exception
    {
        if (response.body() == null)
        {
            return null;
        }

        return new Gson().fromJson(new String(response.body().bytes()),clazz);
    }



    public BeanCallback setClazz(Class arg1)
    {
        this.clazz = arg1;
        return this;
    }

}
