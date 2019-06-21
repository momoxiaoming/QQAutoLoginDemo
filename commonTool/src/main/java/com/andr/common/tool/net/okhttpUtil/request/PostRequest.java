package com.andr.common.tool.net.okhttpUtil.request;

import com.andr.common.tool.net.okhttpUtil.callback.Callback;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class PostRequest extends HttpRequest
{



    public PostRequest(int id, String json, String tag, String url, HashMap<String, String> headers, long defaultTimeOut, long readTimeOut, long conTimeOut, long writeTimeOut, boolean isOnMainThread)
    {
        super(id, json, tag, url, headers, defaultTimeOut, readTimeOut, conTimeOut, writeTimeOut, isOnMainThread);
    }

    @Override
    public Request onRequest(RequestBody arg1)
    {
        return this.builder.post(arg1).build();
    }

    @Override
    public RequestBody onRequestBody()
    {
        if(this.json != null) {
            return FormBody.create(MediaType.parse("application/json; charset=utf-8"), this.json);
        }
        throw new IllegalArgumentException("请求参数为空");

    }

    @Override
    public RequestBody onRequestBody(RequestBody arg1, Callback arg2)
    {
        return arg1;
    }
}
