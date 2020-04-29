package AK42.Qu1z;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import AK42.Qu1z.QuizContract.*;
import java.util.ArrayList;

/*
import android.text.Spannable;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import java.lang.reflect.Array;
import java.util.List;
*/

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Qu1z.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTABLE();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTABLE(){

        Question q1 = new Question("Wie hoch war die weltgrößte Rakete (Saturn V) ?", "99 m", "111 m","150 m", 2);
        addQuestion(q1);
        Question q2 = new Question("Wie schnell flog die Lookheed SR-71 (Blackbird) ?", "2.507 km/h", "3.529 km/h","5.082", 2);
        addQuestion(q2);
        Question q3 = new Question("Wieviel Liter Wasser gibt es auf der Erde ?", "138 Billionen Liter", "248 Trillionen Liter","1386 Trillionen Liter", 3);
        addQuestion(q3);
        Question q4 = new Question("Wie hoch war die Frachtkapazität des sog. Kaspischen  Seemonsters (Ekranoplan)", "52 Tonnen", "304 Tonnen","512 Tonnen", 2);
        addQuestion(q4);
        Question q5 = new Question("Wie hieß Mussolini mit Vornamen?", "Benito", "Rudolpho","Vincenzo", 1);
        addQuestion(q5);
    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }

    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }


}
