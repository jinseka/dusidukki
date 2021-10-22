package com.and.dusi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    MembershipOpenHelper openHelper;
    EditText emailEt, pwdEt;
    SQLiteDatabase db;
    Button loginBtn, joinBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openHelper = new MembershipOpenHelper(this);
        db = openHelper.getWritableDatabase();
        emailEt =  findViewById(R.id.emailEt);
        pwdEt = findViewById(R.id.pwdEt);
        loginBtn =  findViewById(R.id.loginBtn);
        joinBtn =  findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(listener);
        joinBtn.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.joinBtn:
                    startActivity(new Intent(LoginActivity.this, JoinActivity.class));
                    finish();
                    break;
                case R.id.loginBtn:
                    String email = emailEt.getText().toString();
                    String pwd = pwdEt.getText().toString();

                    String sql = "select * from membership where email = '"+email+"' and pwd = '"+pwd+"'";
                    Cursor cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        String no = cursor.getString(0);
                        String rest_id = cursor.getString(1);
                        Log.d("select ", "no : " + no + "\nrest_id : " + rest_id);
                    }
                    if(cursor.getCount() == 1) {
                        // 해당 이메일과 아이디가 있으면 1개의 row를 가져온다
                        Toast.makeText(LoginActivity.this, email+ "님 환영합니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("pwd", pwd);
                        startActivity(intent);
                        finish();
                    } else {
                        // 없다면 아무 값도 가져오지 않으므로 count 가 0 이므로 text로 알려준다.
                        Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    break;
            }
                //이메일 정규식 적용
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(hasFocus) {
                Pattern p = Pattern.compile("^[a-zA-X0-9]@[a-zA-Z0-9].[a-zA-Z0-9]");
                Matcher m = p.matcher((emailEt).getText().toString());

                if ( !m.matches()){
                    Toast.makeText(LoginActivity.this, "Email형식으로 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


        }
    };
}
