package com.andr.common.tool.net.okhttpUtil.callback;


import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;

public abstract class FileCallBack extends Callback
{
    private String dir;
    private String name;

    public FileCallBack(String dir, String name)
    {
        super();
        this.dir = dir;
        this.name = name;
    }

    public abstract void onProgress(int id, long total, long index);

    public abstract void onResponse(int id, File file);

    @Override
    public void onResponse(int id, Object object)
    {
        onResponse(id, (File) object);
    }

    public File onParse(Response response, int id) throws Exception
    {
        return this.saveFile(response, id);
    }


    public File saveFile(Response arg17, int arg18) throws Exception
    {
        File v7 = new File(this.dir);
        File v8 = new File(this.dir, this.name);
        if (!v7.exists())
        {
            v7.mkdirs();
        }

        if (arg17.body() != null)
        {
            int v0 = 0;
            long v9 = arg17.body().contentLength();
            byte[] v11 = new byte[1024];
            InputStream v12 = arg17.body().byteStream();
            FileOutputStream v13 = new FileOutputStream(v8);
            while (true)
            {
                int v1 = v12.read(v11);
                int v14 = v1;
                if (v1 != -1)
                {
                    v13.write(v11, 0, v14);
                    v1 = v0 + v14;
                    this.sendProgress(arg18, v9, ((long) v1));
                    v0 = v1;
                    continue;
                }

                return v8;
            }
        }

        return v8;
    }

    private void sendProgress(final int id, final long total, final long index)
    {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
            @Override
            public void run()
            {
                FileCallBack.this.onProgress(id, total, index);
            }
        });

    }
}

