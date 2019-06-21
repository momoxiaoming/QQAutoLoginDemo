package com.mediatek.qqautologindemo.hk;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/6/21
 *     desc  : new class
 * </pre>
 */
public class HkCenter implements IXposedHookLoadPackage
{
    private static final String QQ_PAKE = "com.tencent.mobileqq";
    private static final String qqApplicationClsNm = "com.tencent.mobileqq.qfix.QFixApplication";

    private static boolean isQQFlg = false;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable
    {
        if (lpparam.packageName.equals(QQ_PAKE) && lpparam.processName.equals(QQ_PAKE))
        {
            //已进入qq主进程
            hookQQ(lpparam);
        }
    }


    public void hookQQ(XC_LoadPackage.LoadPackageParam lpparam)
    {
        //hook 入门类,拿到全局Context
        XposedHelpers.findAndHookMethod(qqApplicationClsNm, lpparam.classLoader, "onCreate", new XC_MethodHook()
        {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);
                if (!isQQFlg) //防止多次进入
                {
                    isQQFlg = true;
                    HkQQClass.hookMain((Context) param.thisObject);
                }

            }
        });
    }



}
