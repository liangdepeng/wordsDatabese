package com.example.administrator.words.activity

import android.os.Bundle
import android.text.BidiFormatter
import android.text.TextDirectionHeuristics
import android.text.TextUtils
import android.view.View
import com.dpdp.base_moudle.base.ui.BaseActivity
import com.dpdp.base_moudle.http.HttpCallback
import com.dpdp.base_moudle.http.HttpHelper
import com.dpdp.base_moudle.http.HttpRequestMethod
import com.dpdp.base_moudle.http.HttpRequestParams
import com.dpdp.base_moudle.http.bean.TranslateBean
import com.dpdp.base_moudle.http.volley.VolleyHttpCallback
import com.dpdp.base_moudle.http.volley.VolleyHttpHelper
import com.dpdp.base_moudle.http.volley.VolleyRequestParams
import com.dpdp.base_moudle.store.AppConstants
import com.dpdp.base_moudle.utils.DeviceUtil
import com.dpdp.base_moudle.utils.MD5
import com.dpdp.base_moudle.utils.ToastUtil
import com.example.administrator.words.R
import com.example.administrator.words.helper.TranslateHelper
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import kotlinx.android.synthetic.main.activity_trans_lation.*

class TranslateActivity : BaseActivity(), View.OnClickListener, OnSelectListener {

    private var languageMap: LinkedHashMap<String, String>? = null
    private var translationType: String = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_lation)
        languageMap = TranslateHelper.getLanguageMap()
        initView()
    }

    private fun initView() {
        trans_late_btn?.setOnClickListener(this)
        translate_btn?.setOnClickListener(this)
        clear_input_tv?.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.trans_late_btn -> {
                showSelectLanguageDialog()
            }
            R.id.translate_btn -> {
              //  requestApiTranslateContent()
                requestApiTranslateContentByVolley()
            }
            R.id.clear_input_tv -> {
                content_tv?.setText("")
                ToastUtil.showMsg("输入框已清空")
            }
        }
    }

    /**
     * volley 网络框架封装请求
     */
    private fun requestApiTranslateContentByVolley() {
        if (TextUtils.isEmpty(content_tv?.text?.toString())) {
            ToastUtil.showMsg("翻译内容不能为空~")
            return
        }

        showCustomLoadingDialog()

        val num = (Math.random() * 10000).toInt()
        val str = content_tv?.text?.toString()

        val volleyHttpHelper = VolleyHttpHelper(this)
        val volleyRequestParams = VolleyRequestParams(AppConstants.BAI_DUI_TRANSLATE_API_URL, VolleyRequestParams.Method.POST, TranslateBean::class.java)

        volleyRequestParams.addParams("q", str)
        volleyRequestParams.addParams("from", "auto")//翻译的内容 语言属于 哪一种 auto 自动监测
        volleyRequestParams.addParams("to", translationType)
        volleyRequestParams.addParams("appid", "20210119000675743")
        volleyRequestParams.addParams("salt", num)
        volleyRequestParams.addParams("sign", MD5.md5("20210119000675743$str$num" + "U9TcTM4AtXAqISFKvezO"))

        volleyHttpHelper.startRequest(volleyRequestParams,object:VolleyHttpCallback{
            override fun onSuccess(response: Any?, requestParams: VolleyRequestParams?) {
                dismissCustomLoadingDialog()
                if (response is TranslateBean) {
                    val transResult = response.trans_result
                    val result = StringBuilder()
                    if (transResult.isNullOrEmpty()) {
                        ToastUtil.showMsg("翻译出错，结果为空")
                        return
                    }
                    for (itemText in transResult) {
                        result.append(itemText.dst.trim()).append("\n")
                    }
                    //因为有些语言是 RTL 显示的 从右到左 这边转换一下 变为 我们中国人的 LTR 从左到右显示
                    result_txt_tv?.text = BidiFormatter.getInstance().unicodeWrap(result.toString(), TextDirectionHeuristics.LTR)
                }
            }

            override fun onError(tips: String?, error: com.android.volley.VolleyError?, requestParams: VolleyRequestParams?) {
                dismissCustomLoadingDialog()
                ToastUtil.showMsg(tips)
            }
        })
    }

    /**
     *  HttpURLConnection  + AsyncTask + Gson 解析实现
     */
    private fun requestApiTranslateContent() {
        if (TextUtils.isEmpty(content_tv?.text?.toString())) {
            ToastUtil.showMsg("翻译内容不能为空~")
            return
        }

        val num = (Math.random() * 10000).toInt()
        val str = content_tv?.text?.toString()

        var httpRequestParams: HttpRequestParams = HttpRequestParams()


        //  httpRequestParams = HttpRequestParams(Constants.BAI_DUI_TRANSLATE_API_URL, HttpRequestMethod.GET, TranslateBean::class.java)

        httpRequestParams = HttpRequestParams(AppConstants.BAI_DUI_TRANSLATE_API_URL, HttpRequestMethod.POST, TranslateBean::class.java)
        httpRequestParams.contentType = "application/x-www-form-urlencoded"

        httpRequestParams.put("q", str)
        httpRequestParams.put("from", "auto")//翻译的内容 语言属于 哪一种 auto 自动监测
        httpRequestParams.put("to", translationType)
        httpRequestParams.put("appid", "20210119000675743")
        httpRequestParams.put("salt", num)
        httpRequestParams.put("sign", MD5.md5("20210119000675743$str$num" + "U9TcTM4AtXAqISFKvezO"))

        showCustomLoadingDialog()

        HttpHelper.getInstance().startRequest(httpRequestParams, object : HttpCallback {
            override fun onSuccess(gsonData: Any, requestTag: Any?) {
                dismissCustomLoadingDialog()
                if (gsonData is TranslateBean) {
                    val transResult = gsonData.trans_result
                    val result = StringBuilder()
                    if (transResult.isNullOrEmpty()) {
                        ToastUtil.showMsg("翻译出错，结果为空")
                        return
                    }
                    for (itemText in transResult) {
                        result.append(itemText.dst.trim()).append("\n")
                    }
                    //因为有些语言是 RTL 显示的 从右到左 这边转换一下 变为 我们中国人的 LTR 从左到右显示
                    result_txt_tv?.text = BidiFormatter.getInstance().unicodeWrap(result.toString(), TextDirectionHeuristics.LTR)
                }
            }

            override fun onError(errorMsg: String, requestTag: Any) {
                dismissCustomLoadingDialog()
                ToastUtil.showMsg(errorMsg)
            }

        })
    }

    private fun showSelectLanguageDialog() {
        var array: Array<String?> = arrayOf("")
        languageMap?.apply {
            array = arrayOfNulls<String>(size)
            var i = 0
            for ((key, value) in this) {
                array[i++] = value
            }
//            forEach{
//                it.key
//                it.value
//            }
        }

        XPopup.Builder(this)
                .maxHeight(DeviceUtil.dp2px(600f))
                .asCenterList("请选择要翻译的目标语言", array, this)
                .setCheckedPosition(1)
                .show()
    }

    override fun onSelect(position: Int, text: String?) {
        // ToastUtil.showMsg("$position  $text")
        translate_tv?.text = text

        languageMap?.forEach {
            if (it.value == text) {
                translationType = it.key
                ToastUtil.showMsg("已切换为 $text ")
                return
            }
        }
    }

    override fun getRetTag(): String {
        return TranslateActivity::class.java.simpleName
    }
}