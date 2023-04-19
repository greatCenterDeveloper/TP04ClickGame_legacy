package com.swj.tp04clickgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout backLayout;
    TextView title;
    ImageView startImg;
    ImageView[] imgs = new ImageView[12];
    ArrayList<Map<Integer, Integer>> imgRes = new ArrayList<>();
    int num=1;
    int stageCount = 1; // 현재 스테이지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backLayout = findViewById(R.id.backLayout);
        title = findViewById(R.id.title);
        startImg = findViewById(R.id.start_img);

        for(int i=0; i<imgs.length; i++) {
            imgs[i] = findViewById(R.id.img01 + i);
            imgs[i].setOnClickListener(listener);
        }

        startImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView i = (ImageView) view;
                i.setEnabled(false);
                i.setImageResource(R.drawable.ing);
                nextStage(stageCount);
            }// onClick()
        });// setOnClickListener()
    }// onCreate()

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageView img = (ImageView) view; // 현재 클릭된 이미지뷰

            if(img.getTag() == null) return;

            int n = (Integer)img.getTag();

            // 이미지뷰의 태그값과 현재 클릭해야되는 번호가 같으면..
            if(n == num) {
                img.setVisibility(View.INVISIBLE);
                num++;
            }

            if(num > imgs.length) {
                num = 1;
                stageCount++;
                if(stageCount > 3) endStage();
                else nextStage(stageCount);
            }
        }
    };

    private void nextStage(int stageCount) {
        if(stageCount > 1) imgRes.clear();

        // key값은 숫자 1 ~ 12
        // value값은 R.drawable.num01 ~ R.drawable.num12 값
        for(int n=0; n<imgs.length; n++) {
            Map<Integer, Integer> map = new HashMap<>();

            switch (stageCount) {
                case 1 :
                    title.setText(R.string.number_title);
                    map.put(n+1, R.drawable.num01 + n);
                    break;
                case 2 :
                    title.setText(R.string.alpa_title);
                    backLayout.setBackgroundResource(R.drawable.bg2);
                    map.put(n+1, R.drawable.alpa01 + n);
                    break;
                case 3:
                    title.setText(R.string.twelve_god_title);
                    backLayout.setBackgroundResource(R.drawable.bg3);
                    map.put(n+1, R.drawable.cha01 + n);
            }
            imgRes.add(map);
        }
        Collections.shuffle(imgRes);

        for(int i=0; i<imgs.length; i++) {
            Map<Integer, Integer> map = imgRes.get(i);

            Iterator<Integer> keys = map.keySet().iterator();
            Integer key = keys.next();

            imgs[i].setImageResource(map.get(key)); // map value값
            imgs[i].setTag(key); // map key값
            imgs[i].setVisibility(View.VISIBLE);
        }// for()
    }// presentStage()

    private void endStage() {
        title.setVisibility(View.GONE);
        startImg.setVisibility(View.GONE);
        backLayout.setBackgroundResource(R.drawable.bg4);
    }

}// MainActivity class