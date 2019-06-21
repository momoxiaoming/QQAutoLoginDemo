package com.andr.common.tool.net.okhttpUtil.builder;

import com.andr.common.tool.net.okhttpUtil.request.HttpRequestCall;
import com.andr.common.tool.net.okhttpUtil.request.PostRequest;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class PostBuilder extends HttpRequestBuilder
{
    @Override
    public HttpRequestCall build()
    {
        return new PostRequest(this.id,this.json,this.tag,this.url,this.headers,defaultTimeOut,readTimeOut, connTimeOut,writeTimeOut,isOnMainThread).build();
    }


}
