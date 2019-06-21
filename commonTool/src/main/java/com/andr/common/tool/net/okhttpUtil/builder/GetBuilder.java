package com.andr.common.tool.net.okhttpUtil.builder;

import android.net.Uri;

import com.andr.common.tool.net.okhttpUtil.request.GetRequest;
import com.andr.common.tool.net.okhttpUtil.request.HttpRequestCall;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class GetBuilder extends HttpRequestBuilder
{


    public GetBuilder()
    {
        super();
    }


    @Override
    public HttpRequestCall build()
    {
        if(this.params.size()>0){
            Uri.Builder builder=Uri.parse(this.url).buildUpon();

            for (Object v2 : this.params.keySet())
            {
                builder.appendQueryParameter(((String) v2), this.params.get(v2));
            }
            this.url=builder.build().toString();
        }

        return new GetRequest(this.id,this.json,this.tag,this.url,this.headers,defaultTimeOut,readTimeOut, connTimeOut,writeTimeOut,isOnMainThread).build();
    }
}
