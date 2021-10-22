package com.and.dusi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    MembershipOpenHelper openHelper;
    SQLiteDatabase db;
    EditText emailEt, pwdEt,emailEt2;
    Button joinBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        openHelper = new MembershipOpenHelper(this);
        db = openHelper.getWritableDatabase();
        emailEt = (EditText) findViewById(R.id.emailEt2);
        pwdEt = (EditText) findViewById(R.id.pwdEt2);
        joinBtn = (Button) findViewById(R.id.joinBtn2);
        joinBtn.setOnClickListener(listener);
        emailEt2=findViewById(R.id.emailEt2);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.joinBtn2:
                    String email = emailEt.getText().toString();
                    String pwd = pwdEt.getText().toString();
                    String sql = "select * from membership where email = '"+email+"'";
                    Cursor cursor = db.rawQuery(sql, null);
                    if(cursor.getCount() == 1) {
                        // 해당 이메일과 아이디가 있으면 1개의 row를 가져온다.
                        Toast.makeText(JoinActivity.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JoinActivity.this, JoinActivity.class));
                        finish();
                    } else {
                        // 없다면 아무 값도 가져오지 않으므로 count 가 0 가져온다.
                        String sql2 ="insert into membership(email, pwd) values('"+email+"','"+pwd+"')";
                        db.execSQL(sql2);
                        Toast.makeText(JoinActivity.this, "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JoinActivity.this, LoginActivity.class));
                    }
                    cursor.close();
                    break;

            }
        //이메일 입력 정규식
       emailEt2.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    Pattern p = Pattern.compile("^[a-zA-X0-9]@[a-zA-Z0-9].[a-zA-Z0-9]");
                    Matcher m = p.matcher((emailEt2).getText().toString());

                    if ( !m.matches()){
                        Toast.makeText(JoinActivity.this, "Email형식으로 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        }

    };


}