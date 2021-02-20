package com.dpdp.base_moudle.http.volley;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dpdp.base_moudle.utils.JsonFormatUtil;
import com.dpdp.base_moudle.utils.ObjectNullUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary: 封装 Volley 网络请求 + Gson 数据解析    https://developer.android.google.cn/training/volley
 *
 * @see com.android.volley.toolbox.HurlStack
 * <p>
 * {@link com.android.volley.toolbox.BaseHttpStack} based on {@link java.net.HttpURLConnection}
 **/
public class VolleyHttpHelper {

    private final RequestQueue requestQueue;
    private final String tag = VolleyHttpHelper.class.getSimpleName();

    /**
     * 构造方法 初始化
     */
    public VolleyHttpHelper(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * 发起请求
     *
     * @param requestParams {@link VolleyRequestParams}
     * @param callback      请求结果回调
     */
    public void startRequest(@NonNull VolleyRequestParams requestParams, @NonNull VolleyHttpCallback callback) {
        if (ObjectNullUtils.objectsIsNull(requestParams, callback)) {
            throw new NullPointerException("requestParams / callback 不能为空");
        }

        Log.e(tag, requestParams.getRequestUrl());

        StringRequest stringRequest = new StringRequest(requestParams.getRequestMethod(), requestParams.getRequestUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.startsWith("\n")) {
                            response = response.replaceFirst("\n", "");
                        }
                        Object data = new Gson().fromJson(response, requestParams.getParseClass());
                        callback.onSuccess(data, requestParams);
                        Log.e(tag, "response1 :" + response);
                        Log.e(tag, "response2 :\n" + JsonFormatUtil.formatDataFromJson(unicodeToString(response)));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("请求失败", error, requestParams);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // post 请求时 提交的参数
                if (requestParams.getRequestMethod() == Method.POST
                        && requestParams.getParamsMap() != null) {
                    return requestParams.getParamsMap();
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // 可根据需求 设置项目的通用请求头 token 等等
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


    /**
     * Unicode转 汉字字符串
     *
     * @param str \u6728
     * @return '木' 26408
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }
}
