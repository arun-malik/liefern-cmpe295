package com.liefern.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class OpaqueLinearLayout extends LinearLayout {

	public OpaqueLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
		makeLayoutOpaque();
	}

	public OpaqueLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		makeLayoutOpaque();
	}

	public OpaqueLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		makeLayoutOpaque();
	}

	public OpaqueLinearLayout(Context context) {
		super(context);
		makeLayoutOpaque();
	}
	
	private void makeLayoutOpaque() {
		Drawable background = this.getBackground();
		background.setAlpha(85);
	}

}
