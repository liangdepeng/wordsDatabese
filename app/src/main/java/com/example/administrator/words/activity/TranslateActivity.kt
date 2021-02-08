package com.example.administrator.words.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.dpdp.base_moudle.base.BaseActivity
import com.dpdp.base_moudle.http.HttpCallback
import com.dpdp.base_moudle.http.HttpHelper
import com.dpdp.base_moudle.http.HttpRequestMethod
import com.dpdp.base_moudle.http.HttpRequestParams
import com.dpdp.base_moudle.http.bean.TranslateBean
import com.dpdp.base_moudle.utils.AppConstants
import com.dpdp.base_moudle.utils.MD5
import com.dpdp.base_moudle.utils.StatusBarUtil
import com.dpdp.base_moudle.utils.ToastUtil
import com.example.administrator.words.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import kotlinx.android.synthetic.main.activity_trans_lation.*

class TranslateActivity : BaseActivity(), View.OnClickListener, OnSelectListener {

    private var languageMap: LinkedHashMap<String, String>? = null
    private var translationType: String = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_lation)
        languageMap = initData()
        initView()
    }

    private fun initData(): LinkedHashMap<String, String> {
        val hashMap = LinkedHashMap<String, String>()
        hashMap["zh"] = "中文"
        hashMap["en"] = "英语"
        hashMap["jp"] = "日语"
        hashMap["kor"] = "韩语"
        hashMap["yue"] = "粤语"
        hashMap["wyw"] = "文言文"
        hashMap["cht"] = "繁体中文"
        hashMap["fra"] = "法语"
        hashMap["spa"] = "西班牙语"
        hashMap["th"] = "泰语"
        hashMap["ara"] = "阿拉伯语"
        hashMap["ru"] = "俄语"
        hashMap["pt"] = "葡萄牙语"
        hashMap["de"] = "德语"
        hashMap["it"] = "意大利语"
        hashMap["el"] = "希腊语"
        hashMap["nl"] = "荷兰语"
        hashMap["pl"] = "波兰语"
        hashMap["bul"] = "保加利亚语"
        hashMap["est"] = "爱沙尼亚语"
        hashMap["dan"] = "丹麦语"
        hashMap["fin"] = "芬兰语"
        hashMap["cs"] = "捷克语"
        hashMap["rom"] = "罗拉尼亚语"
        hashMap["slo"] = "斯洛文尼亚语"
        hashMap["swe"] = "瑞典语"
        hashMap["hu"] = "匈牙利语"
        hashMap["vie"] = "越南语"
        return hashMap
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
                requestApiTranslateContent()
            }
            R.id.clear_input_tv -> {
                content_tv?.setText("")
                ToastUtil.showMsg("输入框已清空")
            }
        }
    }

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
                        result.append(itemText.dst).append("\n")
                    }

                    result_txt_tv?.text = result.toString()
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
                .maxHeight(StatusBarUtil.dip2px(600f).toInt())
                .asCenterList("请选择要翻译的目标语言", array, this)
                .setCheckedPosition(1)
                .show()
    }

    override fun onSelect(position: Int, text: String?) {
        ToastUtil.showMsg("$position  $text")
        translate_tv?.text = text

        languageMap?.forEach {
            if (it.value == text) {
                translationType = it.key
                ToastUtil.showMsg(translationType)
                return
            }
        }
    }

    override fun getRetTag(): String {
        return TranslateActivity::class.java.simpleName
    }
}