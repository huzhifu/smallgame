package com.example.smallgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class SetActivity extends Activity {
	private boolean Flag = false;
	private SharedPreferences preference;
	private SharedPreferences.Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		//初始化SharedPreferences
		preference=getSharedPreferences("flag", MODE_PRIVATE);
		edit=preference.edit();
		
		final Intent music = new Intent("com.angel.Android.MUSIC");
		// 音乐开关
		Switch switcher = (Switch) findViewById(R.id.switch_music);
		//如果开启过，就保持开关的状态为开启
		Flag=preference.getBoolean("flag", false);
		if (Flag == true) {
			switcher.setChecked(true);
		}
		switcher.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					startService(music);
				    edit.putBoolean("flag", true).commit();
				} else {
					stopService(music);
					edit.putBoolean("flag", false).commit();
				}
			}
		});
		// 难易度下拉菜单
		final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// 回到主界面按钮
		Button btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				intent.putExtra("level", spinner.getSelectedItem().toString());
				setResult(11, intent);
				finish();
			}
		});
	}
}
