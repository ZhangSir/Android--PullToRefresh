package com.zs.pulltorefreshtest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final String FORMAT = "MM-dd HH:mm";
	
	private final ImageView headerImage;
	private final ProgressBar headerProgress;
	private final TextView headerText;
	private final TextView headerTime;

	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;
	
	/**
	 * 用来标示是否显示上次更新时间
	 */
	private boolean showUpdateTime = true;
	/**
	 * 记录上次更新时间
	 */
	private long time = -1;

	private final Animation rotateAnimation, resetRotateAnimation;

	public LoadingLayout(Context context, final int mode, boolean showUpdateTime, String releaseLabel, String pullLabel, String refreshingLabel) {
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		headerTime = (TextView) header.findViewById(R.id.pull_to_refresh_time);
		headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		headerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);

		final Interpolator interpolator = new LinearInterpolator();
		rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		        0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

		this.showUpdateTime = showUpdateTime;
		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;

		switch (mode) {
			case PullToRefreshView.MODE_PULL_UP_TO_REFRESH:
				headerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
				break;
			case PullToRefreshView.MODE_PULL_DOWN_TO_REFRESH:
			default:
				headerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
				break;
		}
		
		if(!this.showUpdateTime){
			headerTime.setVisibility(View.GONE);
		}else{
			headerTime.setVisibility(View.VISIBLE);
		}
	}

	public void reset() {
		headerText.setText(pullLabel);
		headerImage.setVisibility(View.VISIBLE);
		headerProgress.setVisibility(View.GONE);
	}

	public void releaseToRefresh() {
		headerText.setText(releaseLabel);
		headerImage.clearAnimation();
		headerImage.startAnimation(rotateAnimation);
		if(time > 0){
			headerTime.setText(getResources().getString(R.string.pull_to_refresh_last_time)
					+ new SimpleDateFormat(FORMAT, Locale.CHINA).format(time));
		}
	}

	public void refreshing() {
		headerText.setText(refreshingLabel);
		headerImage.clearAnimation();
		headerImage.setVisibility(View.GONE);
		headerProgress.setVisibility(View.VISIBLE);
	}

	public void pullToRefresh() {
		headerText.setText(pullLabel);
		headerImage.clearAnimation();
		headerImage.startAnimation(resetRotateAnimation);
		if(time > 0){
			headerTime.setText(getResources().getString(R.string.pull_to_refresh_last_time)
					+ new SimpleDateFormat(FORMAT, Locale.CHINA).format(time));
		}
	}
	
	/**
	 * 设置刷新时间
	 * @param time
	 */
	public void setRefreshTime(long time) {
		this.time = time;
		headerTime.setText(getResources().getString(R.string.pull_to_refresh_last_time)
				+ new SimpleDateFormat(FORMAT, Locale.CHINA).format(this.time));
	}

	/**
	 * 设置刷新时间为当前时间
	 */
	public void setRefreshTimeToCurrent(){
		this.time = new Date().getTime();
		headerTime.setText(getResources().getString(R.string.pull_to_refresh_last_time)
				+ new SimpleDateFormat(FORMAT, Locale.CHINA).format(this.time));
	}

	public void setTextColor(int color) {
		headerText.setTextColor(color);
	}
	
	public void setPullLabel(String pullLabel) {
		this.pullLabel = pullLabel;
	}
	
	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

}
