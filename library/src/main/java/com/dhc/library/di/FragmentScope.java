package com.dhc.library.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:52
 * 描述 ：TODO 请描述该类职责
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
