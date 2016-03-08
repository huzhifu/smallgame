package com.example.smallgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RankActivity extends Activity {
	private TextView mTextViewCiShu;
	private TextView mTextViewBiZhong;
	private Button mButtonOk;
	SharedPreferences preference;
	String cishu="";
	String baifenbi="";
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.rank);
	initWidget();
	initResult();
}

private void initResult() {
	// TODO Auto-generated method stub
	int shu=preference.getInt("count", 0);
	cishu=String.valueOf(shu);
	if(shu==0){
		baifenbi="0%";
	}else if(shu>0&&shu<=5){
		baifenbi="30%";
	}else if(shu>5&&shu<=10){
		baifenbi="60%";
	}else if(shu>10&&shu<=15){
		baifenbi="90%";
	}else{
		baifenbi="99%";
	}
	mTextViewCiShu.setText(cishu);
	mTextViewBiZhong.setText(baifenbi);
	mButtonOk.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
}

private void initWidget() {
	// TODO Auto-generated method stub
	mTextViewCiShu=(TextView)findViewById(R.id.tv_cishu);
	mTextViewBiZhong=(TextView)findViewById(R.id.tv_baifenbi);
	mButtonOk=(Button)findViewById(R.id.btn_ok);
	
	preference=getSharedPreferences("game", MODE_PRIVATE);
	
}
}
