
package com.dhc.library.data.net;

/**
 * @creator:denghc(desoce)
 * @updateTime:2018/7/30 13:42
 * @description: A wrapper class that responds to the results.
 */
public interface ApiResponse<T> {

    /**
     *  isSuccess and   Data of type
     *
     * @return
     */
    boolean isSuccess();

    /**
     *  is authentication failure
     *
     * @return
     */
    boolean checkReLogin();

    /**
     *  Data
     *
     * @return
     */
    T getData();
}
