package com.andr.common.tool.net.okhttpUtil.request;

import com.andr.common.tool.net.okhttpUtil.callback.Callback;
import com.andr.common.tool.util.StringUtil;

import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public abstract class HttpRequest
{
    protected Request.Builder builder;
    protected int id;
    protected String json;
    protected String tag;
    protected String url;
    private HashMap<String,String> headers;

    private long defaultTimeOut;
    private long readTimeOut;
    private long conTimeOut;
    private long writeTimeOut;
    private boolean isOnMainThread;  //默认是回调到主线程


    public HttpRequest(int id, String json, String tag, String url, HashMap<String, String> headers, long defaultTimeOut, long readTimeOut, long conTimeOut, long writeTimeOut, boolean isOnMainThread)
    {
        this.id = id;
        this.json = json;
        this.tag = tag;
        this.url = url;
        this.headers = headers;
        this.defaultTimeOut = defaultTimeOut;
        this.readTimeOut = readTimeOut;
        this.conTimeOut = conTimeOut;
        this.writeTimeOut = writeTimeOut;
        this.isOnMainThread = isOnMainThread;
        this.builder = new Request.Builder();


        if (StringUtil.isStringEmpty(url))
        {
            throw new IllegalArgumentException("请求地址为空!");
        }

        this.builder.url(url).tag(tag);

        for (String key : headers.keySet())
        {
            String value = headers.get(key);
            if (value == null)
                break;
            this.builder.header(key, value);
        }

    }






    public HttpRequestCall build() {
        return new HttpRequestCall(this,defaultTimeOut,readTimeOut,conTimeOut,writeTimeOut,isOnMainThread);
    }

    public int getId()
    {
        return id;
    }

    public abstract Request onRequest(RequestBody arg1);

    public abstract RequestBody onRequestBody();

    public abstract RequestBody onRequestBody(RequestBody arg1, Callback arg2);
}
