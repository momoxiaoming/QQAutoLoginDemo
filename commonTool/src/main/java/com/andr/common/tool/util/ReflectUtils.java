package com.andr.common.tool.util;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射调用工具类
 */
public class ReflectUtils
{
    /**
     * 创建类对象
     *
     * @param className   类名
     * @param params      参数名称
     * @param paramsValue 参数
     * @return 返回值, 可为空
     */
    public static Object newInstance(String className, Class<?>[] params, Object[] paramsValue)
    {
        Object rlt = null;
        try
        {
            Class<?> cls = getCls(className);
            if (cls != null)
            {
                Constructor<?> cons = cls.getDeclaredConstructor(params);
                rlt = cons.newInstance(paramsValue);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return rlt;
    }

    /**
     * 根据类名获取cls
     *
     * @param className
     * @return
     */
    public static Class<?> getCls(String className)
    {
        try
        {
            return Class.forName(className);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 反射获取静态方法值,会从父类中查找
     *
     * @param methodName     反射方法名
     * @param parameterTypes 方法参数
     * @param args           注入的参数
     * @return 返回结果, 可以null
     */
    public static Object invokeStaticMethod(Class<?> cls, String methodName, Class<?>[] parameterTypes, Object... args)
    {
        Object rlt = null;
        try
        {
            Method method = getMethods(cls, methodName, parameterTypes);


            if (method != null)
            {
                rlt = method.invoke(null, args);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return rlt;

    }





    /**
     * 反射获取方法值,会从父类中查找
     *
     * @param obj            反射对象
     * @param methodName     反射方法名
     * @param parameterTypes 方法参数
     * @param args           注入的参数
     * @return 返回结果, 可以null
     */
    public static Object invokeMethod(Object obj,String methodName, Class<?>[] parameterTypes, Object... args)
    {

        Object rlt = null;
        try
        {
            Method method = getMethods(obj.getClass(), methodName, parameterTypes);


            if (method != null)
            {
                rlt = method.invoke(obj, args);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return rlt;
    }

    /**
     * 为反射类变量赋值,包含父类
     *
     * @param obj       反射对象
     * @param filedName 变量名
     * @param value     变量值
     */
    public static void setFieldValue(Object obj, String filedName, Object value)
    {
        try
        {
            List<Field> fieldList = getFields(obj.getClass());

            if (fieldList != null)
            {
                for (Field item : fieldList)
                {
                    if (item.getName().equals(filedName))
                    {
                        item.set(obj, value);
                    }
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 为反射类变量赋值,包含父类
     *
     * @param
     * @param filedName 变量名
     * @param value     变量值
     */
    public static void setStaticFieldValue(Class<?> cls, String filedName, Object value)
    {
        try
        {
            List<Field> fieldList = getFields(cls);

            if (fieldList != null)
            {
                for (Field item : fieldList)
                {
                    if (item.getName().equals(filedName))
                    {
                        item.set(null, value);
                    }
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 反射静态变量的值,包含父类
     *
     * @param clsName   类名
     * @param filedName 变量名
     * @return 变量值, 不存在时为空
     */
    public static Object getStaticFieldValue(String clsName,ClassLoader classLoader, String filedName)
    {
        try
        {
            Class<?> cls = classLoader.loadClass(clsName);
            List<Field> mlist = getFields(cls);
            if (mlist != null)
            {
                for (Field item : mlist)
                {
                    if (item.getName().equals(filedName))
                    {
                        return item.get(null);
                    }

                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取反射对象的变量值,包含父类的变量
     * @param obj
     * @param filedName
     * @return
     */
    public static Object getFieldValue(Object obj, String filedName)
    {

        try
        {
            List<Field> fieldList = getFields(obj.getClass());

            if (fieldList != null)
            {
                for (Field item : fieldList)
                {
                    if (item.getName().equals(filedName))
                    {
                        return item.get(obj);
                    }
                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * 获取反射对象的变量值,处理了变量名相同的情况
     *
     * @param obj
     * @param filedName
     * @return
     */
    public static Object getFieldValueForFiledClsNm(Object obj, String filedName, String filedClsNm)
    {


        List<Object> mList = new ArrayList<>();
        try
        {
            List<Field> fieldList = ReflectUtils.getFields(obj.getClass());

            if (fieldList != null)
            {
                for (Field item : fieldList)
                {
                    if (item.getName().equals(filedName))
                    {
                        if(item.get(obj)!=null){
                            mList.add(item.get(obj));
                        }

                    }
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        }
        for (Object itemObj : mList)
        {

            if (itemObj.getClass().getName().equals(filedClsNm))
            {
                return itemObj;
            }
        }

        return null;

    }

    /**
     * 获取所有的变量名和变量值
     * @param obj
     * @return
     */
    public static List<String> getAllFieldValue(Object obj)
    {


        try
        {
            List<String> rltList = new ArrayList<>();
            List<Field> fieldList = getFields(obj.getClass());
            if (fieldList != null)
            {
                for (Field item : fieldList)
                {

                    Object rlt = item.get(obj);
                    rltList.add(item.getName() + ":" + rlt);
                }


            }

            return rltList;
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取所有的field 包括父类的
     */
    public static List<Field> getFields(Class<?> cls)
    {
        try
        {

            List<Field> fieldList = new ArrayList<>();
            while (cls != null && !cls.getName().toLowerCase().equals("java.lang.object"))
            {
                Field[] fields = cls.getDeclaredFields();
                if (fields != null)
                {
                    for (Field field : fields)
                    {
                        if (field != null)
                        {
                            field.setAccessible(true);
                            fieldList.add(field);
                        }
                    }
                }

                cls = cls.getSuperclass();
            }

            return fieldList;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取符合条件的method,子类不存在会从父类中查找
     */
    public static Method getMethods(Class<?> cls, String methodName, Class<?>[] parameterTypes)
    {


        while (cls != null && !cls.getName().toLowerCase().equals("java.lang.object"))
        {
            try
            {
                Method method = cls.getDeclaredMethod(methodName, parameterTypes);
                if (method != null)
                {
                    method.setAccessible(true);
                    return method;
                }


            } catch (NoSuchMethodException e)
            {
                //                e.printStackTrace();
                cls = cls.getSuperclass();
            }
        }

        return null;

    }
}