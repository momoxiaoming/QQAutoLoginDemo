package com.andr.common.tool.net.okhttpUtil.request;

import com.andr.common.tool.net.okhttpUtil.builder.FileBuilder;
import com.andr.common.tool.net.okhttpUtil.callback.Callback;

import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class FileRequest extends HttpRequest
{

    private List<FileBuilder.OkFile> fileList;
    private Map<String, String> params;

    public FileRequest(int id, String json, String tag, String url, HashMap<String, String> headers, long defaultTimeOut, long readTimeOut, long conTimeOut, long writeTimeOut, boolean isOnMainThread,List<FileBuilder.OkFile> fileList,Map<String, String> params)
    {
        super(id, json, tag, url, headers, defaultTimeOut, readTimeOut, conTimeOut, writeTimeOut, isOnMainThread);

        this.params = params;
        this.fileList=fileList;
    }


    public String getMediaType(String arg3)
    {
        try
        {
            return URLConnection.getFileNameMap().getContentTypeFor(URLEncoder.encode(arg3, "UTF-8"));
        } catch (Exception v0)
        {
            v0.printStackTrace();
            return "application/octet-stream";
        }
    }

    @Override
    public Request onRequest(RequestBody requestBody)
    {
        return this.builder.post(requestBody).build();
    }

    @Override
    public RequestBody onRequestBody()
    {

        int v3 = 2;
        if (this.fileList.size() != 0)
        {
            okhttp3.MultipartBody.Builder builder = new okhttp3.MultipartBody.Builder().setType(MultipartBody.FORM);

            for (Object v5 : this.params.keySet())
            {
                Object value = this.params.get(v5);
                if (value == null)
                {
                    break;
                }
                String[] v7 = new String[v3];
                v7[0] = "Content-Disposition";
                v7[1] = "form-data; name=\"" + (((String) v5)) + "\"";
                builder.addPart(Headers.of(v7), RequestBody.create(null, ((String) value)));

            }


            Iterator v1 = this.fileList.iterator();
            while (v1.hasNext())
            {
                Object v2 = v1.next();
                builder.addFormDataPart(((FileBuilder.OkFile) v2).name, ((FileBuilder.OkFile) v2).fileName, RequestBody.create(MediaType.parse(this.getMediaType(((FileBuilder.OkFile) v2).fileName)), ((FileBuilder.OkFile) v2).file));
            }

            return builder.build();
        }

        okhttp3.FormBody.Builder builder = new FormBody.Builder();
        for (Object key : this.params.keySet())
        {

            Object value = this.params.get(key);
            if (value == null)
            {
                break;
            }

            builder.add(((String) key), ((String) value));
        }


        return builder.build();
    }

    @Override
    public RequestBody onRequestBody(RequestBody arg1, Callback arg2)
    {
        return arg1;
    }
}
