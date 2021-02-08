package com.example.administrator.words;


/**
 * 单词数据库表结构
 */
public class Word {
    public int id;
    public String word;
    public String translate;
    public String enPronunciation;
    public String usPronunciation;
    public boolean isComplete = false;
    public boolean isCollect = false;

    public Word() {
    }

    public Word(int id, String word, String translate) {
        this(id, word, translate, "", "", false, false);
    }

    public Word(int id, String word, String translate, String enPronunciation, String usPronunciation, boolean isComplete, boolean isCollect) {
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.enPronunciation = enPronunciation;
        this.usPronunciation = usPronunciation;
        this.isComplete = isComplete;
        this.isCollect = isCollect;
    }

}
