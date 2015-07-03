package com.zs.pulltorefreshtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.zs.pulltorefreshtest.PullToRefreshView.OnLoadMoreListener;
import com.zs.pulltorefreshtest.PullToRefreshView.OnRefreshListener;

public class MainActivity extends Activity {

	private PullToRefreshView mPullToRefreshView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPullToRefreshView = (PullToRefreshView) this.findViewById(R.id.pull_view_main);
		mPullToRefreshView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "刷新", Toast.LENGTH_SHORT).show();
			}
		});
		mPullToRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "更多", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
