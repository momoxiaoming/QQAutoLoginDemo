package com.andr.common.tool.net.okhttpUtil;

import com.andr.common.tool.net.okhttpUtil.builder.DownFileBuilder;
import com.andr.common.tool.net.okhttpUtil.builder.FileBuilder;
import com.andr.common.tool.net.okhttpUtil.builder.GetBuilder;
import com.andr.common.tool.net.okhttpUtil.builder.PostBuilder;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class OkHttpUtils
{

    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;
    private int defultTimeOut = 5000;


    public OkHttpUtils(OkHttpClient client)
    {
        if (client == null)
        {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    //                    .addNetworkInterceptor(logInterceptor)   //打印请求的信息,缺点是会拖慢下载速度
                    .connectTimeout(defultTimeOut, TimeUnit.MILLISECONDS).readTimeout(defultTimeOut, TimeUnit.MILLISECONDS).writeTimeout(defultTimeOut, TimeUnit.MILLISECONDS).build();
        } else
        {
            okHttpClient = client;
        }

    }

    public static OkHttpUtils getInstance()
    {
        return OkHttpUtils.setOkHttpClient(null);
    }

    private static OkHttpUtils setOkHttpClient(OkHttpClient client)
    {
        if (OkHttpUtils.okHttpUtils == null)
        {
            OkHttpUtils.okHttpUtils = new OkHttpUtils(client);
        }
        return OkHttpUtils.okHttpUtils;
    }

    /**
     * 取消tag标识请求
     *
     * @param tag
     */
    public void cancelTag(String tag)
    {

        Iterator v0 = this.okHttpClient.dispatcher().queuedCalls().iterator();
        while (v0.hasNext())
        {
            Object item = v0.next();
            if (!tag.equals(((Call) item).request().tag()))
            {
                continue;
            }

            ((Call) item).cancel();
        }

    }


    public static PostBuilder post()
    {
        return new PostBuilder();
    }

    public static GetBuilder get()
    {
        return new GetBuilder();
    }

    public static FileBuilder uploadFile()
    {
        return new FileBuilder();
    }

    public static DownFileBuilder downFile()
    {
        return new DownFileBuilder();
    }

    public OkHttpClient getOkHttpClient()
    {
        return okHttpClient;
    }


}
