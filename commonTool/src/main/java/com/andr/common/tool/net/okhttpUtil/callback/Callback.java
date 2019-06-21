package com.andr.common.tool.net.okhttpUtil.callback;

import okhttp3.Response;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public abstract class Callback
{
    public abstract void onFailure(int id,  Exception exception, String errmsg);

    public abstract Object onParse(Response response, int id) throws Exception;

    public abstract void onResponse(int id, Object object);





}
