package com.common.borderable;
/**
 *
 * @author W，x
 * @version 创建时间：2015年2月3日 下午12:00:52
 * 
 */
public interface IHintView {
	public static int POSITION_TOPLEFT = 0x1;
	public static int POSITION_TOPRIGHT = 0x2;
	public static int POSITION_BOTTOMLEFT = 0x4;
	public static int POSITION_BOTTOMRIGHT = 0x8;
	public static int POSITION_CENTER=0x16;

	/**
	 * 显示提示标志
	 */
	void showHint();

	/**
	 * 隐藏提示标志
	 */
	void hideHint();

	/**
	 * 提示标志在目标视图
	 * 
	 * @param position
	 */
	void setHintPostion(int position);

	/**
	 * 提示标志中心点距离边缘的大小
	 * 
	 * @param padding
	 */
	void setHintPadding(int padding);

	/**
	 * 提示标志中心点距离有效内容边缘的大小
	 * 
	 * @param margin
	 */
	void setHintMargin(int margin);

	/**
	 * 提示标志的作图资源
	 * 
	 * @param resid
	 */
	void setHintResource(int resid);

	/**
	 * 图片标志是个圆形，并设置它的半径。 {@link #setHintResource(int)}无效。
	 * 
	 * @param radius
	 */
	void setHintRadius(int radius);

	/**
	 * 原型提示标志的颜色
	 * 
	 * @param color
	 */
	void setHintColor(int color);
	/**
	 *	设置hint显示的值
	 * @param text
	 * 2015年2月3日-下午1:06:42
	 *
	 */
	void setHintText(String text);
	
	/**
	 * 设置hintText 字体大小
	 * @param size
	 * 2015年2月3日-下午3:39:50
	 *
	 */
	void setHintTextSize(float size);
	
	/**
	 * 设置画笔宽度
	 * @param pix
	 * 2015年2月3日-下午3:39:50
	 *
	 */
	void setHintWidth(int pix) ;
}
