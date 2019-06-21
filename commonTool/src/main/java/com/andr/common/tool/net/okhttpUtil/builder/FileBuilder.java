package com.andr.common.tool.net.okhttpUtil.builder;

import com.andr.common.tool.net.okhttpUtil.request.FileRequest;
import com.andr.common.tool.net.okhttpUtil.request.HttpRequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/27
 *     desc  : new class
 * </pre>
 */
public class FileBuilder extends HttpRequestBuilder
{
    public class OkFile {
        public File file;
        public String fileName;
        public String name;

        public OkFile(String arg2, File arg3) {
            super();
            this.name = arg2;
            this.file = arg3;
            this.fileName = arg3.getName();
        }

        public OkFile(String arg1, String arg2, File arg3) {
            super();
            this.file = arg3;
            this.name = arg1;
            this.fileName = arg2;
        }

        public String toString() {
            return String.format("{\'name\':\'%s\',\'fileName\':\'%s\',\'file\':\'%s\'}", this.name, this.fileName, this.file.getAbsolutePath());
        }
    }



    protected Map<String,String> params;

    private List<OkFile> files;


    public FileBuilder()
    {
        super();
        files = new ArrayList<>();
    }



    /**
     * 添加单个文件路径,
     *
     * @param filePath 文件路径
     * @return
     */
    public FileBuilder addFile(String filePath,String name)
    {
        this.files.add(new OkFile(name,new File(filePath)));
        return this;
    }



    @Override
    public HttpRequestCall build()
    {
        return new FileRequest(this.id,this.json,this.tag,this.url,this.headers,defaultTimeOut,readTimeOut, connTimeOut,writeTimeOut,isOnMainThread,files,params).build();
    }


}
