package com.example.administrator.words.helper

/**
 * Created by ldp.
 *
 * Date: 2021-02-08
 *
 * Summary:
 *
 */
class TranslateHelper {

    companion object {

        /**
         * 获取  百度 translate API 可以翻译的语言列表
         */
        fun getLanguageMap(): LinkedHashMap<String, String> {
            val linkedHashMap = LinkedHashMap<String, String>()
            linkedHashMap["zh"] = "中文"
            linkedHashMap["en"] = "英语"
            linkedHashMap["jp"] = "日语"
            linkedHashMap["kor"] = "韩语"
            linkedHashMap["yue"] = "粤语"
            linkedHashMap["wyw"] = "文言文"
            linkedHashMap["cht"] = "繁体中文"
            linkedHashMap["fra"] = "法语"
            linkedHashMap["spa"] = "西班牙语"
            linkedHashMap["th"] = "泰语"
            linkedHashMap["ara"] = "阿拉伯语"
            linkedHashMap["ru"] = "俄语"
            linkedHashMap["pt"] = "葡萄牙语"
            linkedHashMap["de"] = "德语"
            linkedHashMap["it"] = "意大利语"
            linkedHashMap["el"] = "希腊语"
            linkedHashMap["nl"] = "荷兰语"
            linkedHashMap["pl"] = "波兰语"
            linkedHashMap["bul"] = "保加利亚语"
            linkedHashMap["est"] = "爱沙尼亚语"
            linkedHashMap["dan"] = "丹麦语"
            linkedHashMap["fin"] = "芬兰语"
            linkedHashMap["cs"] = "捷克语"
            linkedHashMap["rom"] = "罗拉尼亚语"
            linkedHashMap["slo"] = "斯洛文尼亚语"
            linkedHashMap["swe"] = "瑞典语"
            linkedHashMap["hu"] = "匈牙利语"
            linkedHashMap["vie"] = "越南语"
            return linkedHashMap
        }
    }
}