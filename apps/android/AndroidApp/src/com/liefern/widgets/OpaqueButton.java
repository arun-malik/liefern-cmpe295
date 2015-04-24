package com.liefern.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class OpaqueButton extends Button {

	public OpaqueButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		makeLayoutOpaque();
	}

	public OpaqueButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		makeLayoutOpaque();
	}
	
	private void makeLayoutOpaque() {
		Drawable background = this.getBackground();
		background.setAlpha(85);
	}

}
