package com.xianjiu.www.togithubproject.activity.net;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ResultCallback<T> {
    Type     mType;
    Class<T> tClass;

    public ResultCallback( Class<T> tClass) {
        mType = getSuperclassTypeParameter(getClass());
        this.tClass=tClass;
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onError(BaseError baseError);

    public abstract void onResponse(T response);
}