package com.example.smallgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	int prize_count=0;
	SharedPreferences preferences;
	SharedPreferences.Editor edit;
	//第一张卡片的变量
	String level;//游戏难度
	int data=72;//随机数的取值范围
	private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14,
			b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25,b_random,b_reset;
    private Button[] btn={b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14,
			b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25,b_random};
  	private int[] btnID={R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,
  			R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10,
  			R.id.button11,R.id.button12,R.id.button13,R.id.button14,R.id.button15,
  			R.id.button16,R.id.button17,R.id.button18,R.id.button19,R.id.button20,
  			R.id.button21,R.id.button22,R.id.button23,R.id.button24,R.id.button25};
    private EditText et;
    private TextView mTextViewPrize;
    int[] arr = new int[72];
    String[] ar=new String[72];
    String s2[];
    int[] value=new int[5];
    int count=0;
    int coun=0;
    boolean prizeFlag;//是否中奖
    boolean hasFlag;//是否已经领奖
    //第二张卡片的变量
    private int c=0;
    private TextView mTextViewRotate;    //旋转的textview
	private Animation rotate;            //旋转效果
	private TextView mTextViewContext;   //获取内容
	private Handler mHandler;
    private int i=0;
    private boolean Flag=false;
    private int time=1500;
    private String context="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//把中奖成绩记录到排行榜
		preferences=getSharedPreferences("game", MODE_PRIVATE);
		edit=preferences.edit();
		
		//实现选项卡的功能
		TabHost m = (TabHost)findViewById(R.id.tabhost);  
        m.setup();  
		LayoutInflater i=LayoutInflater.from(this);  
        i.inflate(R.layout.tab1, m.getTabContentView());  
        i.inflate(R.layout.tab2, m.getTabContentView());
        m.addTab(m.newTabSpec("tab1").setIndicator("抽 奖 页").setContent(R.id.linearlayout1));    
        m.addTab(m.newTabSpec("tab2").setIndicator("选 数 页").setContent(R.id.relativelayout));
        
		// 接受来自设置界面的游戏难度
		Intent intent = getIntent();
		level = intent.getStringExtra("level");

		if (level.equals("简单(25)")) {
			data = 25;
		} else if (level.equals("中等(49)")) {
			data = 49;
		} else if (level.equals("较难(64)")) {
			data = 64;
		} else if (level.equals("高难(72)")) {
			data = 72;
		}

		initView();//初始化第一张卡片的视图
		initData();//初始化第一张卡片的数据
		initWidget();//初始化第二张卡片的视图
	}
    /**
     * 初始化第二张卡片的视图
     */
	private void initWidget() {
		// TODO Auto-generated method stub
		rotate = AnimationUtils.loadAnimation(this, R.anim.effect_edittext);
		mTextViewRotate = (TextView) findViewById(R.id.tv_rotate);
		mTextViewContext=(TextView)findViewById(R.id.tv_context);
		
		mTextViewRotate.setOnClickListener(this);
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				createRandom();
				if(msg.what==0x101){
					mTextViewRotate.startAnimation(rotate);
					if (i > data-1) {
                        i=0;
					}
					mTextViewRotate.setText(String.valueOf(s2[i++]));
				}
			}
		};
	}
	/**
	 * 初始化第一张卡片的数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		/*for (int i = 1; i < 73; i++) {
			int index = new Random().nextInt(72);
			if (arr[index] != 0)
				i--;
			else
				arr[index] = i;
		}
		for (int i = 0; i < 72; i++) {
			ar[i] = String.valueOf(arr[i]);
		}
		for (int i = 0; i < 25; i++) {
			btn[i].setText(ar[i]);
		}这是其中一种方法*/
		createRandom();
		for (int i =24; i>=0; i--) {
			btn[i].setText(s2[i]);
		}
	}
	/**
	 * 初始化第一张卡片的视图
	 */
	private void initView() {
		// TODO Auto-generated method stub
		for(int i=0;i<25;i++){
			btn[i]=(Button) findViewById(btnID[i]);
			btn[i].setOnClickListener(this);
		}
		mTextViewPrize=(TextView)findViewById(R.id.getprize);
		
		b_reset=(Button)findViewById(R.id.btn_reset);
		mTextViewPrize.setOnClickListener(this);
		b_reset.setOnClickListener(this);
	}
	/**
	 * 创建随机不重复的参数
	 */
    public void createRandom(){
    	List list=new ArrayList();
    	int [] s1=new int[data];
    	int number=data;
    	for(int i=0;i<data;i++){
    		list.add(i+1);
    	}
    	for(int j=0;j<s1.length;j++){
    		int index=(int)(Math.random()*number);
    		list.get(index);
    		s1[j]=Integer.parseInt(String.valueOf(list.get(index)));
    		list.remove(index);
    		number--;
    		}
    	 s2=new String[s1.length];
    	for(int i=0;i<data;i++){
    		s2[i]=String.valueOf(s1[i]);
    	}
    	
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// 第二张卡片的监听事件
		if (v.getId() == R.id.tv_rotate) {

			Flag = !Flag;
			if (time > 310 && Flag == false) {
				Flag = !Flag;
				Toast.makeText(MainActivity.this, "亲，人家还没准备好呢！",
						Toast.LENGTH_SHORT).show();
			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						if (Flag == false) {
							time = 1500;
							break;
						}
						Message message = mHandler.obtainMessage();
						message.what = 0x101;
						mHandler.sendMessage(message);
						if (time > 310)
							time -= 100;

						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
			while(Flag==false){
				c=0;
				rotate.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						if(c==0){
						context+=mTextViewRotate.getText().toString()+",";
						mTextViewContext.setText(context);
						c++;
						}
					}
				});
				break;
			}
			}
		
		// 以下全部为第一张卡片的监听事件
		if (v.getId() == R.id.getprize) {
			if (prizeFlag) {
				if(hasFlag==false){
				prize_count++;
				edit.putInt("count", prize_count).commit();
				Intent intent = new Intent(MainActivity.this,
						PrizeActivity.class);
				startActivity(intent);
				hasFlag=true;//已经领过奖
				}else{
					Toast.makeText(MainActivity.this, "不能重复领奖！！",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(MainActivity.this, "您未中奖，暂不能领奖！！",
						Toast.LENGTH_SHORT).show();
			}
		}
		for (int i = 0; i < 25; i++) {
			if (v.getId() == btnID[i]) {
				btn[i].setVisibility(View.INVISIBLE);
				value[count] = i;
				count++;
			}
			if (count == 5) {
				for (int j = 0; j < 25; j++) {
					btn[j].setClickable(false);
				}
			}
		}
		if (count == 5) {
			count = 0;
			Arrays.sort(value);
			for (int i = 0; i < 4; i++) {
				if (((value[i + 1] - value[i]) == 1) && (value[0] % 5 == 0)) {
					coun++;
				} else if (((value[i + 1] - value[i]) == 5)
						&& (value[0] == 0 || value[0] == 1 || value[0] == 2
								|| value[0] == 3 || value[0] == 4)) {
					coun++;
				} else if ((value[0] == 0) && ((value[i + 1] - value[i]) == 6)) {
					coun++;
				} else if ((value[0] == 4) && ((value[i + 1] - value[i]) == 4)) {
					coun++;
				}
			}
			if (coun == 4) {
				coun = 0;
				prizeFlag = true;//中奖
				hasFlag=false;//重置为还未领奖
				Toast.makeText(MainActivity.this, "恭喜你中奖了！！",
						Toast.LENGTH_SHORT).show();
			} else {
				coun = 0;
				prizeFlag = false;
				Toast.makeText(MainActivity.this, "很遗憾，没有中奖！！",
						Toast.LENGTH_SHORT).show();
			}
		}

		if (v.getId() == R.id.btn_reset) {
			createRandom();
			for (int i = 0; i < 25; i++) {
				btn[i].setClickable(true);
			}
			for (int i = 0; i < 5; i++) {
				btn[value[i]].setVisibility(View.VISIBLE);
			}

			for (int i = 0; i < 25; i++) {
				btn[i].setText(s2[i]);
			}
			prizeFlag=false;
			context="";
			mTextViewRotate.setText("");
			mTextViewContext.setText("");
		}

	}

}
