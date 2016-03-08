package com.example.smallgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PrizeActivity extends Activity {
	private ImageView mImageViewPrize,mImageViewClose;
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.prize);
	mImageViewPrize=(ImageView)findViewById(R.id.iv_prize);
	mImageViewClose=(ImageView)findViewById(R.id.iv_close);
	mImageViewClose.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	/*View inflate = LayoutInflater.from(this).inflate(R.layout.prize, null);
	Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.prize_display);
	inflate.startAnimation(loadAnimation);*/
   }
}
