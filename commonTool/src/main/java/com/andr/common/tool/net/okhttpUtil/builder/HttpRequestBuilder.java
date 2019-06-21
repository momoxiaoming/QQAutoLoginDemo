package com.andr.common.tool.net.okhttpUtil.builder;


import com.andr.common.tool.net.okhttpUtil.request.HttpRequestCall;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : http请求构造器
 * </pre>
 */
public abstract class HttpRequestBuilder
{

    protected HashMap<String,String> headers; //请求的头信息
    protected int id; //请求 id值
    protected String json; //请求的json数据
    protected String tag; //请求标识
    protected String url; //请求地址

    protected Map<String,String> params; //地址外接参数


    protected long defaultTimeOut; //默认超时
    protected long readTimeOut;  //读超时
    protected long connTimeOut;  //链接超时
    protected long writeTimeOut;  //写超时
    protected boolean isOnMainThread;  //默认是回调到主线程





    public HttpRequestBuilder()
    {
        headers=new HashMap<>();
        params=new HashMap<>();
        defaultTimeOut=5000; //默认五秒
    }

    public HttpRequestBuilder addParam(String arg2, String arg3) {
        params.put(arg2, arg3);
        return this;
    }

    public HttpRequestBuilder addParams(HashMap<String,String> map) {
        params=map;
        return this;
    }

    public HttpRequestBuilder setReadTimeOut(long readTimeOut)
    {
        this.readTimeOut = readTimeOut;
        return this;
    }


    public HttpRequestBuilder setConnTimeOut(long connTimeOut)
    {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public HttpRequestBuilder setWriteTimeOut(long writeTimeOut)
    {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    /**
     * 将回调切回主线程
     * @return
     */
    public HttpRequestBuilder isOnMainThread(boolean isOnMainThread)
    {
        this.isOnMainThread = isOnMainThread;
        return this;
    }

    public HttpRequestBuilder addHeader(String arg2, String arg3) {
        headers.put(arg2, arg3);
        return this;
    }

    public HttpRequestBuilder addHeaders(HashMap<String,String> map) {
        headers=map;
        return this;
    }

    public HttpRequestBuilder json(String json) {
        this.json = json;
        return this;
    }

    public HttpRequestBuilder id(int id) {
        this.id = id;
        return this;
    }

    public HttpRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequestBuilder tag(String tag) {
        this.tag = tag;
        return this;
    }



    public abstract HttpRequestCall build();
}
