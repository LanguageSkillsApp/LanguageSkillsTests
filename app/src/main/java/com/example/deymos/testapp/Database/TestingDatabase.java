package com.example.deymos.testapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class TestingDatabase extends SQLiteOpenHelper {

    public static final int VERSION = 5;
    public static final String DB_NAME = "testing.db";

    //  CREATING TABLES
    public static final String CREATE_TABLE_TESTS = "CREATE TABLE " + TestsTable.TABLE_NAME + "("
            + TestsTable.ID + " INTEGER PRIMARY KEY, "
            + TestsTable.TITLE + " VARCHAR(100) UNIQUE, "
            + TestsTable.TIME_RESTRICTION + " INTEGER);";

    public static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + QuestionsTable.TABLE_NAME + " ("
            + QuestionsTable.TEST_ID + " INTEGER NOT NULL, "
            + QuestionsTable.QUESTION_ID + " INTEGER PRIMARY KEY, "
            + QuestionsTable.QUESTION + " VARCHAR(255) UNIQUE, "
            + QuestionsTable.ANSWER + " VARCHAR(50) NOT NULL);";

    public static final String CREATE_TABLE_ANSWERS = "CREATE TABLE " + AnswersTable.TABLE_NAME + " ("
            + AnswersTable.TEST_ID + " INTEGER NOT NULL, "
            + AnswersTable.QUESTION_ID + " INTEGER PRIMARY KEY, "
            + AnswersTable.FIRST_ANSWER + " VARCHAR(50) NOT NULL, "
            + AnswersTable.SECOND_ANSWER + " VARCHAR(50) NOT NULL, "
            + AnswersTable.THIRD_ANSWER + " VARCHAR(50) NOT NULL, "
            + AnswersTable.FOURTH_ANSWER + " VARCHAR(50) NOT NULL);";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + UsersTable.TABLE_NAME + " ("
            + UsersTable.ID + " INTEGER PRIMARY KEY, "
            + UsersTable.MAIL + " VARCHAR(30) UNIQUE, "
            + UsersTable.PASSWORD + " VARCHAR(50) NOT NULL);";

    public static final String CREATE_TABLE_RESULTS = "CREATE TABLE " + ResultsTable.TABLE_NAME + " ("
            + ResultsTable.USER_ID + " INTEGER NOT NULL, "
            + ResultsTable.TEST_ID + " INTEGER NOT NULL, "
            + ResultsTable.RESULT + " INTEGER NOT NULL);";

    // -------------------------


    public TestingDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TESTS);
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_ANSWERS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_RESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TestsTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ResultsTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + AnswersTable.TABLE_NAME);
            onCreate(db);
        } catch (SQLiteException e) {
            Log.e("Sorry!", "It can't drop the table");
        }
    }

    public class TestsTable {
        public static final String TABLE_NAME = "Tests";
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "Title";
        public static final String TIME_RESTRICTION = "Restriction";
    }

    public class QuestionsTable {
        public static final String TABLE_NAME = "Questions";
        public static final String TEST_ID = "Test_id";
        public static final String QUESTION_ID = BaseColumns._ID;
        public static final String QUESTION = "Question";
        public static final String ANSWER = "Answer";
    }

    public class AnswersTable {
        public static final String TABLE_NAME = "Answers";
        public static final String TEST_ID = "Test_id";
        public static final String QUESTION_ID = BaseColumns._ID;
        public static final String FIRST_ANSWER = "First_answer";
        public static final String SECOND_ANSWER = "Second_answer";
        public static final String THIRD_ANSWER = "Third_answer";
        public static final String FOURTH_ANSWER = "Fourth_answer";
    }

    public class UsersTable {
        public static final String TABLE_NAME = "Users";
        public static final String ID = BaseColumns._ID;
        public static final String MAIL = "Mail";
        public static final String PASSWORD = "Password";
    }

    public class ResultsTable {
        public static final String TABLE_NAME = "Results";
        public static final String USER_ID = "User_id";
        public static final String TEST_ID = "Test_id";
        public static final String RESULT = "Result";
    }
}