package com.andr.entity.base;

import com.andr.common.tool.util.ReflectUtils;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/4/15
 *     desc  : 所有实体类的超类
 * </pre>
 */
public class BaseData
{

    /**
     * 检查参数是否不为空且服务要求
     *
     * @param filedNm 可变长度,变量名
     * @return
     */
    public boolean isReady(String... filedNm)
    {
        if (filedNm != null)
        {
            for (String item : filedNm)
            {
                Object obj = ReflectUtils.getFieldValue(this, item);

                if (obj == null)
                {
                    return false;
                }
            }


        }
        return true;
    }


}
