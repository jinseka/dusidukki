
package com.and.dusi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.and.dusi.Bbs;
import com.and.dusi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseReference database;
    EditText et_user_name, et_user_email, et_user_id2;
    Button btn_save, btn_read;
    TextView read_data;
    int i = 1; //userId값 count하기 위한 변수
    ListView listView;
    TextView text1, text2;

    ArrayList<Bbs> arrayList;
    Button btnPOST;
    //수정된거 올려보자 ! 올라갔나 다시해보자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPOST=findViewById(R.id.btnPOST);
        et_user_name = findViewById(R.id.et_user_name);
        et_user_email = findViewById(R.id.et_user_email);
        et_user_id2 = findViewById(R.id.et_user_id2);
        btn_save = findViewById(R.id.btn_save);
        btn_read = findViewById(R.id.btn_read);
        listView = findViewById(R.id.listView);


        database = FirebaseDatabase.getInstance().getReference("Bbs");
        Log.d("파이어베이스>> ", database + " ");


        //db에서 가지고 오는 유저들의 목록을 넣을 공간 !
        arrayList = new ArrayList<>();



        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("파이어베이스 >> " , "게시판  아래의 자식들의 개수 : " + snapshot.getChildrenCount());
                Log.d("파이어베이스 >> ","전체 json 목록 가지고 온 것 "+snapshot.getChildren());
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Log.d("파이어베이스 >> ","하나의 snapshot : " + snapshot1);
                    Log.d("파이어베이스 >> ","하나의 snapshot  value: " + snapshot1.getValue());
                    Bbs bbs = snapshot1.getValue(Bbs.class);
                    Log.d("파이어베이스 >>", "게시판  1명 : " + bbs);
                    arrayList.add(bbs);

                }
                Log.d("파이어베이스 >> ", "게시판  목록 전체 : " +arrayList);
                Log.d("파이어베이스 >> ", "게시판  목록 전체 : " +arrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, " -----------.",
                        Toast.LENGTH_SHORT).show();
                String title = et_user_name.getText().toString();
                String content = et_user_email.getText().toString();
                String i = et_user_id2.getText().toString();
                //i++;

                Bbs bbs = new Bbs(title,content);
                Toast.makeText(MainActivity.this, bbs+" ",
                        Toast.LENGTH_SHORT).show();
                database.child(i).setValue(bbs)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("파이어베이스", bbs+"=======");
                                Toast.makeText(MainActivity.this, "저장을 완료했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "저장을 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = et_user_id2.getText().toString();

                database.child(title).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bbs bbs = snapshot.getValue(Bbs.class);
                        Log.d("파이어베이스>> ", title + ": userId 상세정보: " + bbs);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("파이어베이스>> ", title + ": userId 없음");
                    }
                });
            }
        });
//        btnPOST.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, TabActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });



    } //onCreate

}//class






