package com.example.mvvmexample.utils

import okhttp3.FormBody

object Const
{
    val BASEURL="https://api.simplifiedcoding.in/course-apis/mvvm/"
    val DMSBASEURL="https://tms.anblicks.com/dms-qa-api/"

    val defaultRequest: FormBody.Builder
        get() = FormBody.Builder()
}