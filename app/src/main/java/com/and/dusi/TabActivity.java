package com.and.dusi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TabActivity extends AppCompatActivity {
    MembershipOpenHelper openHelper;
    SQLiteDatabase db;


    EditText title, content;
    Button CButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        //어디에 분리시켜놓은 layou파일을 넣을지 결정!
        LinearLayout layout1 =findViewById(R.id.tabList);
        LinearLayout layout2 =findViewById(R.id.tabWrite);
        LinearLayout layout3 =findViewById(R.id.tabAlbum);

        //분리시켜놓은 xlm파일 ( layout)들을 객체화 시켜 가져오자 !
        //inflation
        View listView = View.inflate(com.and.dusi.TabActivity.this,R.layout.list,null);
        View writeView = View.inflate(com.and.dusi.TabActivity.this,R.layout.write,null);
        View albumView = View.inflate(com.and.dusi.TabActivity.this,R.layout.album,null);


        //layout 에 view를 끼워 넣자
        layout1.addView(listView);
        layout2.addView(writeView);
        layout3.addView(albumView);



//        //인플레이션을 했을때 그 페이지에서 findView id 를 해야한다.
//        Button btnButton2 =writeView.findViewById(R.id.btnChange2);
//        ImageView imageView2 = writeView.findViewById(R.id.imageView2);
//        TextView textView4 =writeView.findViewById(R.id.text4);

//        btnButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageView2.setImageResource(R.drawable.dusi);
//                String data ="바뀌었다.";
//                textView4.setText(data);
//            }
//        });

        //탭전체 tabHost
        TabHost tabHost =findViewById(R.id.tabhost);
        tabHost.setup();//기초적인 tab초기화!

        //각탭마다의 설정을 넣음 (TabSpac),
        TabHost.TabSpec tabSpecSong =tabHost.newTabSpec("LIST").setIndicator("전체보기");
        tabSpecSong.setContent(R.id.tabList);
        tabHost.addTab(tabSpecSong);

        TabHost.TabSpec tabSpecArtist =tabHost.newTabSpec("WRITE").setIndicator("작성");
        tabSpecArtist.setContent(R.id.tabWrite);
        tabHost.addTab(tabSpecArtist);

        TabHost.TabSpec tabSpecAlbum =tabHost.newTabSpec("ALBUM").setIndicator("미정");
        tabSpecAlbum.setContent(R.id.tabAlbum);
        tabHost.addTab(tabSpecAlbum);

        //탭의 순서는 0번1번2번이다 .

        tabHost.setCurrentTab(0);




        //------------------------------------------------------------------------------------------
        //인플레이션을 했을때 그 페이지에서 findView id 를 해야한다.
        //writeView 에 있는 버튼 클릭시 DB에 게시물을 저장해야한다.
        Button CButton =writeView.findViewById(R.id.CButton);

        CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

};
