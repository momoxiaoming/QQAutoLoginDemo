package com.andr.common.tool.net.okhttpUtil.builder;

import com.andr.common.tool.net.okhttpUtil.request.DownFileRequest;
import com.andr.common.tool.net.okhttpUtil.request.HttpRequestCall;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/28
 *     desc  : new class
 * </pre>
 */
public class DownFileBuilder extends HttpRequestBuilder
{
    @Override
    public HttpRequestCall build()
    {
        return new DownFileRequest(this.id,this.json,this.tag,this.url,this.headers,defaultTimeOut,readTimeOut, connTimeOut,writeTimeOut,isOnMainThread).build();
    }
}
