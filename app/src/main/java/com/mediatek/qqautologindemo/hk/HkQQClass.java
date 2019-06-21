package com.mediatek.qqautologindemo.hk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.andr.common.tool.util.ReflectUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/6/21
 *     desc  : qq相关hook点主逻辑类
 * </pre>
 */
public class HkQQClass
{

    private static Context context;
    private static String accountManageActivityClsNm = "com.tencent.mobileqq.activity.AccountManageActivity";
    private static final String mainFramentClsNm = "com.tencent.mobileqq.activity.MainFragment";


    public static void hookMain(Context mContext)
    {
        context = mContext;


        hookRegisterGuideLogin();

        hookAutoLogin();

        loginOut();

        hookMain();
    }

    /**
     * hook新用户页,拿到登陆按钮,模拟点击登陆
     */
    public static void hookRegisterGuideLogin()
    {
        XposedHelpers.findAndHookMethod("com.tencent.mobileqq.activity.registerGuideLogin.RegisterGuideView", context.getClassLoader(), "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class, new XC_MethodHook()
        {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);

                final Object obj = param.thisObject;


                doMainLooper(context, 1000, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //得到按钮对象
                        Object viewObj = ReflectUtils.getFieldValueForFiledClsNm(obj, "a", "android.widget.Button");

                        if (viewObj instanceof Button)
                        {
                            //点击按钮
                            ((Button) viewObj).performClick();
                        }
                    }
                });

            }
        });
    }


    private static void hookMain(){
        XposedHelpers.findAndHookMethod(mainFramentClsNm, context.getClassLoader(), "onResume", new XC_MethodHook()
        {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);
                Object mainFramentObj = param.thisObject;

                Log.d("allen","---");
                final Activity  mainActivityObj = (Activity) ReflectUtils.invokeMethod(mainFramentObj, "getActivity", null);

                //启动账号管理也,进行退出登录
                doMainLooper(context, 3000, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        final Class<?> accountManageActivityCls = XposedHelpers.findClass(accountManageActivityClsNm, context.getClassLoader());


                        mainActivityObj.startActivity(new Intent(mainActivityObj, accountManageActivityCls));

                    }
                });


            }
        });

    }

    private static void hookAutoLogin()
    {
        String loginviewClsNm = "com.tencent.mobileqq.activity.registerGuideLogin.LoginView";
        final String loginBtnClsNm = "com.tencent.mobileqq.activity.registerGuideLogin.LoginAnimBtnView"; //登陆按钮类名
        final String loginBtnFiledNm = "a"; //登陆按钮变量名
        final String pwdEdTextClsNm = "com.tencent.mobileqq.widget.CustomSafeEditText"; //密码输入框类名
        final String pwdEdTextFiledNm = "a"; //密码输入框变量名
        final String newStyleDropdownViewClsNm = "com.tencent.mobileqq.widget.NewStyleDropdownView"; //输入框外view类名
        final String newStyleDropdownViewFiledNm = "a"; //输入框外view类名
        final String actInputViewClsNm = "aqsw"; //输入框类名
        final String actInputViewFiledNm = "a"; //输入框的变量名

        //hook登录页节点
        XposedHelpers.findAndHookMethod(loginviewClsNm, context.getClassLoader(), "a", View.class, new XC_MethodHook()
        {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);

                //得到登陆所需对象
                Object loginViewObj = param.thisObject;
                Object newStyleDropdownViewOBj = ReflectUtils.getFieldValueForFiledClsNm(loginViewObj, newStyleDropdownViewFiledNm, newStyleDropdownViewClsNm);
                final Object loginBtnObj = ReflectUtils.getFieldValueForFiledClsNm(loginViewObj, loginBtnFiledNm, loginBtnClsNm);
                final Object pwdEdTextObj = ReflectUtils.getFieldValueForFiledClsNm(loginViewObj, pwdEdTextFiledNm, pwdEdTextClsNm);
                final Object actInputViewobj = ReflectUtils.getFieldValueForFiledClsNm(newStyleDropdownViewOBj, actInputViewFiledNm, actInputViewClsNm);


                // 开始输入账号密码登陆

                final String account = "123456";
                final String pwd = "123445";

                doMainLooper(context, 3000, new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if (actInputViewobj instanceof AutoCompleteTextView)
                        {

                            ((AutoCompleteTextView) actInputViewobj).setText(account);
                        }

                        if (pwdEdTextObj instanceof EditText)
                        {

                            ((EditText) pwdEdTextObj).setText(pwd);
                        }
                        if (loginBtnObj instanceof View)
                        {

                            ((View) loginBtnObj).performClick();
                        }


                    }
                });


            }
        });
    }


    private static void loginOut()
    {
        //hook 账号管理页
        XposedHelpers.findAndHookMethod(accountManageActivityClsNm, context.getClassLoader(), "doOnCreate", Bundle.class, new XC_MethodHook()
        {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);
                final Object accountManageActivityObj = param.thisObject;

                doMainLooper(context, 3000, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ReflectUtils.invokeMethod(accountManageActivityObj, "a", new Class[]{int.class, boolean.class}, 0, true);


                    }
                });

            }
        });
    }






    private static void doMainLooper(Context context, long mills, Runnable runnable)
    {
        if (context != null)
        {
            Handler handler = new Handler(context.getMainLooper());
            handler.postDelayed(runnable, mills);
        }
    }
}
