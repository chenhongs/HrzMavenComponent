package com.ch.android.servicecomponent;

/**
 * Created by mac on 2018/11/13.
 */

public class ServiceLoader {

    private static ServiceLoader instance;

    private ServiceLoader(){

    }

    public static ServiceLoader getInstance(){
        if(instance==null){
            return new ServiceLoader();
        }
        return instance;
    }


    //拼接成一个类
    //找到这个类的注解接口@Service
    //找到实现类@ServiceImpl 找到名字相似的
    public BaseInvokeService getService(Class clz){
        //通过注解找到那个类 赋值返回
        return null;
    }



}
