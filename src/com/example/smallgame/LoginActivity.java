package com.example.smallgame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class LoginActivity extends Activity implements OnClickListener{
	private ImageView mImageButtonIn,mImageButtonExit,mImageButtonBoard,mImageButtonHelp,mImageButtonSet;
	String level="����(72)";//��Ϸ���Ѷȣ��������洫ֵ��Ĭ��Ϊ�����ѡ�
	//���ҳ�������
	private View view1, view2, view3;  
	private ViewPager viewPager;  //��Ӧ��viewPager  
	private List<View> viewList;//view���� 
	String[] titles; // ͼƬ����
	List<View> dots; // ͼƬ�������ĵ���Щ��
	TextView tv_title;//��ǰ����
	int currentItem = 0; // ��ǰͼƬ��������
	Handler handler;
	ScheduledExecutorService scheduledExecutorService;//��������ִ��ָ��������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		initWidget();
		//��ʼ�����ҳ���淽��
		initView();
		
	}
	/*
	 * ��ʼ�����ҳ���淽��
	 */
	private void initView() {
		// TODO Auto-generated method stub
		titles = new String[3];
		titles[0] = "��������";
		titles[1] = "����Ϧʰ";
		titles[2] = "����·��";
		viewList = new ArrayList<View>();
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater inflater = getLayoutInflater();
		view1 = inflater.inflate(R.layout.layout1, null);
		view2 = inflater.inflate(R.layout.layout2, null);
		view3 = inflater.inflate(R.layout.layout3, null);
		viewList = new ArrayList<View>();// ��Ҫ��ҳ��ʾ��Viewװ��������
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		// �л���ǰ��ʾ��ͼƬ
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				viewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
			};
		};
		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.removeView(viewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				container.addView(viewList.get(position));
				return viewList.get(position);
			}
		};
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// ��Activity���ɼ���ʱ��ֹͣ�л�
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * �����л�����
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % viewList.size();
				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
			}
		}

	}

	/**
	 * ��ViewPager��ҳ���״̬�����ı�ʱ����
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}
	/*
	 * ��ʼ�����淽��
	 */
	private void initWidget() {
		// TODO Auto-generated method stub
		mImageButtonBoard=(ImageView)findViewById(R.id.ib_board);
		mImageButtonExit=(ImageView)findViewById(R.id.ib_exit);
		mImageButtonHelp=(ImageView)findViewById(R.id.ib_help);
		mImageButtonIn=(ImageView)findViewById(R.id.ib_in);
		mImageButtonSet=(ImageView)findViewById(R.id.ib_set);
		mImageButtonBoard.setOnClickListener(this);
		mImageButtonExit.setOnClickListener(this);
		mImageButtonHelp.setOnClickListener(this);
		mImageButtonIn.setOnClickListener(this);
		mImageButtonSet.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_in:
			Intent intent=new Intent("START_ACTION");
			intent.putExtra("level", level);
			intent.addCategory("MYCATEGORY");
			startActivity(intent);
			break;
		case R.id.ib_exit:
			new AlertDialog.Builder(this)
			.setTitle("�˳�����")
			.setMessage("ȷ���˳���")
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
				
			})
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
				
			}).show();
			break;
		case R.id.ib_set:
			Intent intent1=new Intent(LoginActivity.this,SetActivity.class);
			startActivityForResult(intent1, 10);
			break;
		case R.id.ib_help:
			Intent intent2=new Intent();
			intent2.setClass(LoginActivity.this, HelpActivity.class);
			startActivity(intent2);
			break;
		case R.id.ib_board:
			/*new AlertDialog.Builder(this)
			.setTitle("��ܰ��ʾ")
			.setMessage("ϵͳά���У������ڴ�...")
			.setPositiveButton("�ر�", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
				
			}).show();*/
			Intent intent3=new Intent();
			intent3.setClass(LoginActivity.this, RankActivity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//����������ý�������׶�
		if(resultCode==11){
			level=data.getStringExtra("level");
		}
	}
}
