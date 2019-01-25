package com.huoyun.huoyun.page;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.huoyun.huoyun.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(MainActivity.this);

    }

    @OnClick({R.id.rl_menu})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }

}
