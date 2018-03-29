package com.dhc.businesscomponent.data.net;

import com.dhc.library.data.net.ApiResponse;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/8/22 19:52
 * 描述	      ${TODO}
 */

public class GankApiResponse<T> implements ApiResponse<T> {
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }


    public T results;

    @Override
    public T getData() {
        return results;
    }

    public void setData(T data) {
        this.results = data;
    }


    @Override
    public boolean isSuccess() {
        return !isError();
    }

    @Override
    public boolean checkReLogin() {
        return false;
    }
}