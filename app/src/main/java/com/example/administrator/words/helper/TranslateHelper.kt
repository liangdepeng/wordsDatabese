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
         * 默认的 翻译类型 和 文字 可以直接在此处更改 已经做了配置
         */
        private const val defaultLanguageType = "en"
        private const val defaultLanguage = "英语"

        /**
         * 有序 map 集合 保存列表数据
         */
        private val dataLinkedHashMap by lazy { LinkedHashMap<String, String>() }

        /**
         * 获取  百度 translate API 可以翻译的语言列表
         */
        fun getLanguageMap(): LinkedHashMap<String, String> {
            return initDataMap()
        }

        /**
         * 默认语言类型 type
         */
        fun getDefaultLanguageType(): String {
            return defaultLanguageType
        }

        /**
         * 默认语言 文字说明
         */
        fun getDefaultLanguage(languageType: String): String {
            return dataLinkedHashMap[languageType] ?: defaultLanguage
        }

        private fun initDataMap(): LinkedHashMap<String, String> {
            if (dataLinkedHashMap.isNullOrEmpty()) {
                dataLinkedHashMap["zh"] = "中文"
                dataLinkedHashMap["en"] = "英语"
                dataLinkedHashMap["jp"] = "日语"
                dataLinkedHashMap["kor"] = "韩语"
                dataLinkedHashMap["yue"] = "粤语"
                dataLinkedHashMap["wyw"] = "文言文"
                dataLinkedHashMap["cht"] = "繁体中文"
                dataLinkedHashMap["fra"] = "法语"
                dataLinkedHashMap["spa"] = "西班牙语"
                dataLinkedHashMap["th"] = "泰语"
                dataLinkedHashMap["ara"] = "阿拉伯语"
                dataLinkedHashMap["ru"] = "俄语"
                dataLinkedHashMap["pt"] = "葡萄牙语"
                dataLinkedHashMap["de"] = "德语"
                dataLinkedHashMap["it"] = "意大利语"
                dataLinkedHashMap["el"] = "希腊语"
                dataLinkedHashMap["nl"] = "荷兰语"
                dataLinkedHashMap["pl"] = "波兰语"
                dataLinkedHashMap["bul"] = "保加利亚语"
                dataLinkedHashMap["est"] = "爱沙尼亚语"
                dataLinkedHashMap["dan"] = "丹麦语"
                dataLinkedHashMap["fin"] = "芬兰语"
                dataLinkedHashMap["cs"] = "捷克语"
                dataLinkedHashMap["rom"] = "罗拉尼亚语"
                dataLinkedHashMap["slo"] = "斯洛文尼亚语"
                dataLinkedHashMap["swe"] = "瑞典语"
                dataLinkedHashMap["hu"] = "匈牙利语"
                dataLinkedHashMap["vie"] = "越南语"
            }
            return dataLinkedHashMap
        }
    }
}