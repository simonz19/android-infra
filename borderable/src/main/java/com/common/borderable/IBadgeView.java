package com.common.borderable;

/**
 * 视图对象提示标志规范
 * 
 * @author Wx
 * 
 */
public interface IBadgeView {
	public static int POSITION_TOPLEFT = 0x1;
	public static int POSITION_TOPRIGHT = 0x2;
	public static int POSITION_BOTTOMLEFT = 0x4;
	public static int POSITION_BOTTOMRIGHT = 0x8;

	/**
	 * 显示提示标志
	 */
	void showBadge();

	/**
	 * 隐藏提示标志
	 */
	void hideBadge();

	/**
	 * 提示标志在目标视图
	 * 
	 * @param position
	 */
	void setBadgePostion(int position);

	/**
	 * 提示标志中心点距离边缘的大小
	 * 
	 * @param padding
	 */
	void setBadgePadding(int padding);

	/**
	 * 提示标志中心点距离有效内容边缘的大小
	 * 
	 * @param margin
	 */
	void setBadgeMargin(int margin);

	/**
	 * 提示标志的作图资源
	 * 
	 * @param resid
	 */
	void setBadgeResource(int resid);

	/**
	 * 图片标志是个圆形，并设置它的半径。 {@link #setBadgeResource(int)}无效。
	 * 
	 * @param radius
	 */
	void setBadgeRadius(int radius);

	/**
	 * 原型提示标志的颜色
	 * 
	 * @param color
	 */
	void setBadgeColor(int color);
}
