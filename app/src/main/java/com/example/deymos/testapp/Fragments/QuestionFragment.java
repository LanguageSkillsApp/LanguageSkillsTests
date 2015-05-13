package com.example.deymos.testapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deymos.testapp.Database.ContentTestingProvider;
import com.example.deymos.testapp.Database.TestingDatabase;
import com.example.deymos.testapp.QuestionActivity;
import com.example.deymos.testapp.R;
import com.example.deymos.testapp.TestsListActivity;

import java.util.ArrayList;

public class QuestionFragment extends Fragment implements View.OnClickListener {
    int questionNum = 0, testNumber, rightAnswers=0;
    ArrayList<String> questions;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        testNumber = intent.getIntExtra(QuestionActivity.TEST_NUMBER, 0);
        questions = getQuestions(testNumber);

        setQuestionTitle(questionNum);
        setAnswersList(questionNum+1);

        Button nextQuestionButt = (Button) getActivity().findViewById(R.id.nextQuestion);
        nextQuestionButt.setOnClickListener(this);
    }

    // here I get the list of questions
    public ArrayList getQuestions(int testNum) {
        Uri uri = Uri.parse(ContentTestingProvider.CONTENT_URI + TestingDatabase.QuestionsTable.TABLE_NAME);
        Cursor cursor = getActivity().getContentResolver().query(uri,
                new String[]{TestingDatabase.QuestionsTable.QUESTION},
                TestingDatabase.QuestionsTable.TEST_ID + "=?",
                new String[]{String.valueOf(testNum)}, null);

        ArrayList<String> questions = new ArrayList<>();

        while (cursor.moveToNext()) {
            questions.add(cursor.getString(cursor.getColumnIndex(TestingDatabase.QuestionsTable.QUESTION)));
        }
        cursor.close();
        return questions;
    }

    // here I set title of a question
    public void setQuestionTitle(int questionNum) {
        TextView question = (TextView) getActivity().findViewById(R.id.question);
        question.setText(questions.get(questionNum));
    }

    // here I'm setting variaty of answers
    public void setAnswersList(int questionNum) {
        Uri uri = Uri.parse(ContentTestingProvider.CONTENT_URI + TestingDatabase.AnswersTable.TABLE_NAME);

        Cursor cursor = getActivity().getContentResolver().query(uri,
                new String[]{TestingDatabase.AnswersTable.FIRST_ANSWER, TestingDatabase.AnswersTable.SECOND_ANSWER, TestingDatabase.AnswersTable.THIRD_ANSWER, TestingDatabase.AnswersTable.FOURTH_ANSWER},
                TestingDatabase.AnswersTable.TEST_ID + "=? AND " + TestingDatabase.AnswersTable.QUESTION_ID + "=?",
                new String[]{String.valueOf(testNumber), String.valueOf(questionNum)},
                null);
        cursor.moveToFirst();

        String firstAnswer = cursor.getString(cursor.getColumnIndex(TestingDatabase.AnswersTable.FIRST_ANSWER));
        String secondAnswer = cursor.getString(cursor.getColumnIndex(TestingDatabase.AnswersTable.SECOND_ANSWER));
        String thirdAnswer = cursor.getString(cursor.getColumnIndex(TestingDatabase.AnswersTable.THIRD_ANSWER));
        String fourthAnswer = cursor.getString(cursor.getColumnIndex(TestingDatabase.AnswersTable.FOURTH_ANSWER));
        RadioButton rb1 = (RadioButton) getActivity().findViewById(R.id.answer1);
        RadioButton rb2 = (RadioButton) getActivity().findViewById(R.id.answer2);
        RadioButton rb3 = (RadioButton) getActivity().findViewById(R.id.answer3);
        RadioButton rb4 = (RadioButton) getActivity().findViewById(R.id.answer4);
        rb1.setText(firstAnswer);
        rb2.setText(secondAnswer);
        rb3.setText(thirdAnswer);
        rb4.setText(fourthAnswer);

        cursor.close();
    }


    //here I am returning true answer
    public String getRightAnswer(int questionNum) {
        Uri uri = Uri.parse(ContentTestingProvider.CONTENT_URI + TestingDatabase.QuestionsTable.TABLE_NAME);
        Cursor cursor = getActivity().getContentResolver().query(uri,
                new String[]{TestingDatabase.QuestionsTable.ANSWER},
                TestingDatabase.QuestionsTable.TEST_ID+"=? AND "+TestingDatabase.QuestionsTable.QUESTION_ID+"=?",
                new String[]{String.valueOf(testNumber), String.valueOf(questionNum)},
                null);
        cursor.moveToFirst();
        String answer = cursor.getString(cursor.getColumnIndex(TestingDatabase.QuestionsTable.ANSWER));
        cursor.close();

        return answer;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextQuestion:
                if(questionNum+1>=questions.size()) {


                    RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
                    RadioButton radioButt = (RadioButton) getActivity().findViewById(radioGroup.getCheckedRadioButtonId());

                    String rightAnswer = getRightAnswer(questionNum+1);
                    if(rightAnswer.equals(radioButt.getText())) {
                        rightAnswers++;
                        Toast.makeText(getActivity(), String.valueOf(rightAnswers), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "LOL", Toast.LENGTH_LONG).show();
                    }


                    new AlertDialog.Builder(getActivity())
                            .setTitle("Your result:")
                            .setMessage("You made "+rightAnswers+ " right answers!")
                            .setPositiveButton("Go to the test list", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(), TestsListActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Go to my profile", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    nextQuestion();
                }
                break;
        }
    }

    public void nextQuestion() {
        questionNum++;
        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        RadioButton radioButt = (RadioButton) getActivity().findViewById(radioGroup.getCheckedRadioButtonId());

        String rightAnswer = getRightAnswer(questionNum);
        if(rightAnswer.equals(radioButt.getText())) {
            rightAnswers++;
            Toast.makeText(getActivity(), String.valueOf(rightAnswers), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "LOL "+rightAnswer, Toast.LENGTH_LONG).show();
        }

        setQuestionTitle(questionNum);
        setAnswersList(questionNum + 1);
    }
}
