package com.example.administrator.words.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.administrator.words.Word;
import com.example.administrator.words.WordApplication;

import java.util.ArrayList;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-19
 * <p>
 * Summary: 数据库帮助类
 */
public class WordDataBaseDao {

    private final WordDBOpenHelper wordDBOpenHelper;

    private WordDataBaseDao() {
        wordDBOpenHelper = new WordDBOpenHelper(WordApplication.getAppContext());
    }

    public static WordDataBaseDao getInstance() {
        return DaoInstance.INSTANCE;
    }

    private static final class DaoInstance {
        private static final WordDataBaseDao INSTANCE = new WordDataBaseDao();
    }

    /**
     * 这个 比较特殊 是通过读取 外部的sql脚本文件  交由 sqlite 数据库 执行插入
     *
     * @param sql
     */
    @Deprecated
    public void insertBySql(String sql) {
        try {
            SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
            database.execSQL(sql);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertWord(String word, String translate) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
//        database.execSQL("INSERT INTO words (word,translate) VALUES(?,?)", new Object[]{word, translate});
//        database.close();

        ContentValues values = new ContentValues();
        values.put("word", word);// 单词
        values.put("translate", translate);// 翻译
        values.put("isComplete", false);//是否标记背会
        values.put("isCollect", false);// 是否标记收藏
        values.put("en_pronunciation", "");
        values.put("us_pronunciation", "");
        database.insert("words", null, values);
        database.close();
    }

    public void deleteWord(String word) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("DELETE FROM words WHERE word = ?", new Object[]{word});
        database.close();
    }

    public void updateWordIsComplete(String word, boolean isComplete) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("UPDATE words SET isComplete = ? WHERE word = ?", new Object[]{isComplete, word});
        database.close();
    }

    public void updateWordIsCollect(String word, boolean isCollect) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("UPDATE words SET isCollect = ? WHERE word = ?", new Object[]{isCollect, word});
        database.close();
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

    public ArrayList<Word> queryAllWords(@Nullable ICheckWords iCheckWords) {
        ArrayList<Word> arrayList = new ArrayList<>();
        try {
            SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
            Cursor cursor = database.query("words", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String enPronunciation = cursor.getString(cursor.getColumnIndex("en_pronunciation"));
                String usPronunciation = cursor.getString(cursor.getColumnIndex("us_pronunciation"));
                String translate = cursor.getString(cursor.getColumnIndex("translate"));
                String isComplete = cursor.getString(cursor.getColumnIndex("isComplete"));
                String isCollect = cursor.getString(cursor.getColumnIndex("isCollect"));
                Word word1 = new Word();
                word1.word = word;
                word1.translate = translate;
                word1.usPronunciation = usPronunciation;
                word1.enPronunciation = enPronunciation;
                word1.isCollect = "1".equals(isCollect);
                word1.isComplete = "1".equals(isComplete);

                if (iCheckWords != null) {
                    if (iCheckWords.checkWordIsNeed(word1)) {
                        arrayList.add(word1);
                    }
                } else {
                    arrayList.add(word1);
                }

            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    // public void queryCustom... Please yourself write ...

    @Deprecated
    public void deleteAll() {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("DELETE FROM words");
        database.close();
    }

    public void clearAllWords() {
        deleteAll();
    }


    public boolean insertUserAccount(String userId, String password) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("INSERT INTO users (name,password) VALUES(?,?)", new Object[]{userId, password});
        database.close();
        return queryUserAndPassword(userId, password);
    }

    public void deleteUser(String userId) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("DELETE FROM users WHERE userid=?", new Object[]{userId});
        database.close();
    }

    public boolean updateUser(String userId, String newPassword) {
        SQLiteDatabase database = wordDBOpenHelper.getWritableDatabase();
        database.execSQL("UPDATE users SET password = ? WHERE userid = ?", new Object[]{newPassword, userId});
        return queryUserAndPassword(userId, newPassword);
    }

    public boolean queryUserAndPassword(String userId, String password) {
        String password1 = "";
        try {
            SQLiteDatabase database = wordDBOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT password FROM users WHERE userid=?", new String[]{userId});
            while (cursor.moveToNext()) {
                password1 = cursor.getString(cursor.getColumnIndex("password"));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextUtils.equals(password1, password);
    }

//    public static void main(String[] args) {
//        int[] ints = {1, 2, 3, 4, 5, 6, 7};
//        int[] sum = twoSum(ints, 8);
//        StringBuilder builder = new StringBuilder();
//        for (int i=0;i<sum.length;i++){
//            builder.append(sum[i]).append("   ");
//        }
//        System.out.println(builder.toString());
//    }
//
//    public static int[] twoSum(int[] nums, int target) {
//        int length = nums.length;
//        int[] a = new int[length];
//        int index = 0;
//        for (int i = 0; i < length; i++) {
//            int aa = nums[i];
//            for (int j = i + 1; j < length; j++) {
//                if (nums[j] + aa == target) {
//                    a[index++] = i;
//                    a[index++] = j;
//                    nums[i]= -10000;
//                    nums[j]= -10000;
//                }
//            }
//        }
//        return a;
//    }
}
