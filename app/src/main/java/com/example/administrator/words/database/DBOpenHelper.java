package com.example.administrator.words.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.words.WordApplication;

/**
 * 数据库 帮助类 继承自 {@link SQLiteOpenHelper}
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private final static class Instance {
        private final static DBOpenHelper INSTANCE = new DBOpenHelper(WordApplication.getAppContext(), "tb_dict", null, 2);
    }

    public static DBOpenHelper getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * 数据库 建表语句
     */
    final String Create_Table_SQL = "create table tb_dict (" +
            "_id integer primary key autoincrement" +
            ",word" +
            ",translate" +
            ",isComplete" +
            ",isCollect)";

    /**
     * 数据库初始化
     *
     * @param context 上下文
     * @param name    数据库名称
     * @param factory 游标工厂
     * @param version 数据库版本
     */
    private DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    /**
     * 创建 数据库
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Table_SQL);
    }

    /**
     * 数据库更新
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("delete from tb_dict");
        sqLiteDatabase.execSQL("update sqlite_sequence SET seq = 0 where name ='tb_dict'");
    }

    /**
     * 数据库 添加 单词
     */
    public void writeData(String word, String translate, boolean isComplete, boolean isCollect) {
        SQLiteDatabase database = getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);// 单词
        values.put("translate", translate);// 翻译
        values.put("isComplete", isComplete);//是否标记背会
        values.put("isCollect", isCollect);// 是否标记收藏
        database.insert("tb_dict", null, values);//保存功能
        database.close();
    }

    /**
     * 数据库 更新单词 记忆状态
     *
     * @param words    单词
     * @param complete 是否背会
     */
    public void updateData(String words, boolean complete) {
        SQLiteDatabase database = getInstance().getWritableDatabase();
        database.execSQL("update tb_dict set isComplete = ? where word = ?", new Object[]{complete, words});
        database.close();
    }

    /**
     * 更新 单词收藏状态
     *
     * @param words   单词
     * @param collect 收藏 状态
     */
    public void updateDataIsCollect(String words, boolean collect) {
        SQLiteDatabase database = getInstance().getWritableDatabase();
        database.execSQL("update tb_dict set isCollect = ? where word = ?", new Object[]{collect, words});
        database.close();
    }

    /**
     * 数据库 删除单词
     *
     * @param word 单词
     */
    public void deleteData(String word) {
        SQLiteDatabase database = getInstance().getWritableDatabase();
        database.execSQL("delete from tb_dict where word = ?", new Object[]{word});
        database.close();
    }

    /**
     * ！！！！！！！！！！！！！！
     * <p>
     * 删除所有数据  简称 删库跑路
     */
    public void deleteAll() {
        SQLiteDatabase database = getInstance().getWritableDatabase();
        database.execSQL("delete from tb_dict");
        database.close();
    }

    //单词的删除方法
    //判断是否是合法用户

}
