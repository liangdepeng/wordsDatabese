package com.example.administrator.words;

import android.app.Activity;
import android.database.Cursor;

import com.example.administrator.words.database.DBOpenHelper;
import com.dpdp.base_moudle.store.SpUtils;

import java.util.ArrayList;

/**
 * 写入数据库 帮助类
 */
@Deprecated
public class DataHelper {

    private Activity activity;
    public final static String isWrited = "isWrited";

    public DataHelper(Activity activity) {
        this.activity = activity;
    }

    public void writeWords() {

        // 获取数据库 是否创建并且 录入数据 如果录入过数据 则 不用再添加
        if (SpUtils.getBoolean(activity, isWrited, false)) {
            return;
        }

        ArrayList<Word> wordList = new ArrayList<>();

        Word word0 = new Word(0, "abandon", "vt. 丢弃；放弃，抛弃");
        wordList.add(word0);

        Word word1 = new Word(1, "ability", "n. 能力，本领；才能，才智");
        wordList.add(word1);

        Word word2 = new Word(2, "abnormal", "a. 反常的，不正常的，不规则的");
        wordList.add(word2);

        Word word3 = new Word(3, "aboard", "ad.&prep. 在船(飞机、车)上#nad. 上船(飞机)");
        wordList.add(word3);

        Word word4 = new Word(4, "abroad", "ad. 到国外，在国外；在传播，在流传");
        wordList.add(word4);

        Word word5 = new Word(5, "absent", "a. 缺席的；缺乏的，不存在的；心不在焉的");
        wordList.add(word5);

        Word word6 = new Word(6, "absolute", "a. 绝对的，完全的；确实的，肯定的");
        wordList.add(word6);

        Word word7 = new Word(7, "absolutely", "ad. 完全地；绝对地");
        wordList.add(word7);

        Word word8 = new Word(8, "absorb", "vt. 吸收(水、光、蒸汽等）；使全神贯注");
        wordList.add(word8);

        Word word9 = new Word(9, "abstract", "a. 抽象的#nn. 摘要，梗概#nvt. 提取；摘录要点");
        wordList.add(word9);

        Word word10 = new Word(10, "abundant", "a. 大量(充足)的；丰富(富裕)的");
        wordList.add(word10);

        Word word11 = new Word(11, "abuse", "vt. 滥用；虐待；辱骂#nn. 滥用；恶习；弊端");
        wordList.add(word11);

        Word word12 = new Word(12, "academic", "a. 学术性的；学院的，大学的；理论的#nn. 大学教师，大学生；学者");
        wordList.add(word12);

        Word word13 = new Word(13, "academy", "n. 学院；研究院；学会；专科院校");
        wordList.add(word13);

        Word word14 = new Word(14, "accelerate", "v. 使加速，使增速，促进#nvi. 加快，增加");
        wordList.add(word14);

        Word word15 = new Word(15, "acceleration", "n. 加速；加速度");
        wordList.add(word15);

        Word word16 = new Word(16, "accent", "n. 口音，腔调；重音(符号)#nvt. 重读");
        wordList.add(word16);

        Word word17 = new Word(17, "acceptable", "a. 可接受的，合意的");
        wordList.add(word17);

        Word word18 = new Word(18, "acceptance", "n. 接受，接收；验收；接纳；承认，认可");
        wordList.add(word18);

        Word word19 = new Word(19, "access", "n. 进入；接入；到达；享用权；入口，通路#nvi. 存取");
        wordList.add(word19);

        Word word20 = new Word(20, "accessory", "n. 附件，附属品；(为全套衣服增加美感的)服饰");
        wordList.add(word20);
        for (int i = 0; i < wordList.size(); i++) {

            Word words = wordList.get(i);
            String word = words.word;
            String translate = words.translate;

            if (word.endsWith("ion")) {
                translate = "n. " + translate;
            } else if (word.endsWith("ness")) {
                translate = "n. " + translate;
            } else if (word.endsWith("ment")) {
                translate = "n. " + translate;
            } else if (word.endsWith("ty")) {
                translate = "n. " + translate;
            } else if (word.endsWith("acy")) {
                translate = "n. " + translate;
            } else if (word.endsWith("ance")) {
                translate = "n. " + translate;
            } else if (word.endsWith("ence")) {
                translate = "n. " + translate;
            } else if (word.endsWith("ice")) {
                translate = "n. " + translate;
            } else if (translate.endsWith("的")) {
                translate = "adj. " + translate;
            } else if (translate.endsWith("地")) {
                translate = "adv. " + translate;
            } else {
                translate = translate;
            }

            DBOpenHelper.getInstance().writeData(word, translate, false, false);

        }

        // 添加完成之后 记录已经添加过 用 SharedPreferences 保存到 xml文件
        SpUtils.putBoolean(activity, isWrited, true);

    }


    /**
     * 实现特定的接口  简单的查找数据库数据  实现接口 来筛选数据 不需要筛选 则传null 默认全部数据
     *
     * @param checkWordsCallback
     * @return
     */
    public static ArrayList<Word> getAllWords(ICheckWords checkWordsCallback) {
        try {
            ArrayList<Word> words = new ArrayList<>();
            Cursor cursor = DBOpenHelper.getInstance().getReadableDatabase().query("tb_dict", null, null, null, null, null, null);
            int i = 1;
            while (cursor.moveToNext()) {
                Word word = new Word();
                //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
                word.id = i;
                word.word = cursor.getString(cursor.getColumnIndex("word"));
                word.translate = cursor.getString(cursor.getColumnIndex("translate"));
                String complete = cursor.getString(cursor.getColumnIndex("isComplete"));
                String collect = cursor.getString(cursor.getColumnIndex("isCollect"));
                word.isComplete = "1".equals(complete);
                word.isCollect = "1".equals(collect);
                if (checkWordsCallback != null) {
                    if (checkWordsCallback.checkWordIsNeed(word)) {
                        words.add(word);
                        i++;
                    }
                } else {
                    words.add(word);
                    i++;
                }
            }
            cursor.close();
            return words;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Word>();
    }

    public interface ICheckWords {
        /**
         * 检查单词 是否满足特定的要求
         *
         * @param word
         * @return
         */
        boolean checkWordIsNeed(Word word);
    }
}
