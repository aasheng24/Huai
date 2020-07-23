package com.android.huai.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.huai.R;
import com.android.huai.widget.dialog.BaseDialog;
import com.android.huai.widget.dialog.DialogUtil;
import com.android.huai.widget.dialog.CustomDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTest();
            }
        });
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void dialogTest() {
        CustomDialog dialog = DialogUtil.getLongMultiDialog(getSupportFragmentManager(),"dialogTitle","这个弹窗的内容可以分开点击.","YES","NO", Color.parseColor("#780473"),1,3,7,10);
        dialog.setOnContentListener(new CustomDialog.OnClickContentListener() {
            @Override
            public void onClickContent(int index) {
                //toast(StringUtils.format("点击第%d个内容区域",index + 1));
            }
        });
        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {

            }
        });
        dialog.setOnCancelListener(dialog::dismiss);
        dialog.show(getSupportFragmentManager(),dialog.getTag());
    }

}
