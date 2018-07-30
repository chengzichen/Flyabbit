package com.dhc.library.framework;

import android.os.Bundle;

/**
 * @creator：denghc(desoce)
 * @updateTime：2018/7/30$ 12:52$
 * @description： BaseActivity support
 */
public interface ISupportBaseActivity extends  ISupportBaseView {

    /**
     * Reload this Activity  (NoAnim)
     */
    void reload();

    /**
     * Reload this Activity
     *
     * @param isNeedAnim IsNeed animation for reload
     */
    void reload(boolean isNeedAnim);


}
