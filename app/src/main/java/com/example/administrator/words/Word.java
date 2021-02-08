package com.example.administrator.words;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 单词数据库表结构
 */
public class Word implements Parcelable {

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

    protected Word(Parcel in) {
        id = in.readInt();
        word = in.readString();
        translate = in.readString();
        enPronunciation = in.readString();
        usPronunciation = in.readString();
        isComplete = in.readByte() != 0;
        isCollect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(word);
        dest.writeString(translate);
        dest.writeString(enPronunciation);
        dest.writeString(usPronunciation);
        dest.writeByte((byte) (isComplete ? 1 : 0));
        dest.writeByte((byte) (isCollect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
