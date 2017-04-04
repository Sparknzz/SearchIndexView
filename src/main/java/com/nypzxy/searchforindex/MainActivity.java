package com.nypzxy.searchforindex;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lv_contact)
    ListView lvContact;
    @BindView(R.id.searchIndex)
    SearchIndexView searchIndex;
    @BindView(R.id.tv_letter)
    TextView tvLetter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initActionBar();

        final ArrayList<Contact> contacts = ContactModel.getContacts();

        //将contacts集合排序 需要让model实现擦comparable接口 按照字母排序
        Collections.sort(contacts);

        //排序完成之后  将拍好序的结果传给adapter可以进行判断并展示
        lvContact.setAdapter(new ContactAdapter(contacts));

        searchIndex.setOnLetterChangeListener(new SearchIndexView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                showLetter(letter);
                //让listView根据letter的改变而改变
                for (int i = 0; i < contacts.size(); i++) {
                    if ((contacts.get(i).alphabet.charAt(0) + "").equals(letter)) {
                        lvContact.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onRelease() {

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.animate(tvLetter).scaleY(0f).scaleX(0f).setDuration(300).start();
//                        tvLetter.setVisibility(View.GONE);
                    }
                },1000);

            }
        });
    }

    /**
     * 展示手指点击的字母
     *
     * @param letter
     */
    private void showLetter(String letter) {
        tvLetter.setVisibility(View.VISIBLE);
        tvLetter.setText(letter);
        ViewCompat.animate(tvLetter).scaleY(1f).scaleX(1f).setDuration(300).start();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("WeChat");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
