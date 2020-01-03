package com.common.borderable.utils;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

/**
 * 颜色作图助手
 * 
 * @author W,x
 * 
 */
public class ColorDrawableHelper {
	private static final int ANDROID403 = 15;

	private ColorDrawableHelper() {
	}

	/**
	 * 获得当前状态下的颜色作图对象
	 * 
	 * @param colorDrawable
	 * @param colorStateList
	 * @param stateSet
	 * @return 更新后的对象
	 */
	static ColorDrawable updateColorDrawable(ColorDrawable colorDrawable,
                                             ColorStateList colorStateList, int[] stateSet) {
		if (colorStateList.isStateful()) {
			if (Build.VERSION.SDK_INT >= ANDROID403) {
				((ColorDrawable) colorDrawable).setColor(colorStateList
						.getColorForState(stateSet,
								colorStateList.getDefaultColor()));
				return colorDrawable;
			} else {
				return new ColorDrawable(colorStateList.getColorForState(
						stateSet, colorStateList.getDefaultColor()));
			}
		} else {
			return colorDrawable;
		}
	}

	/**
	 * 以某个颜色在画布上填充一个矩阵
	 * 
	 * @param canvas
	 *            待作图的画布
	 * @param l
	 *            左边左边
	 * @param t
	 *            顶部坐标
	 * @param r
	 *            右边坐标
	 * @param b
	 *            底部坐标
	 * @param paint
	 *            画笔
	 * @param color
	 *            填充色
	 */
	public static void drawRect(Canvas canvas, int l, int t, int r, int b,
                                Paint paint, int color) {
		paint.setColor(color);
		canvas.drawRect(l, t, r, b, paint);
	}
}
