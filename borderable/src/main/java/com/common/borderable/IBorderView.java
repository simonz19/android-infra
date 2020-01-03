package com.common.borderable;


import android.graphics.drawable.Drawable;

/**
 * 可画边框的视图规范
 * 
 * <ol>
 * <li>支持单色分别画四边框</li>
 * {@link #setBorderColor(int)} 设置单色边框 {@link #setBorders(int)} 设置需要的四边框
 * 
 * <pre>
 *         &lt;attr name=&quot;borders&quot;&gt;
 *             &lt;flag name=&quot;left&quot; value=&quot;0x01&quot; /&gt;
 *             &lt;flag name=&quot;right&quot; value=&quot;0x02&quot; /&gt;
 *             &lt;flag name=&quot;top&quot; value=&quot;0x04&quot; /&gt;
 *             &lt;flag name=&quot;bottom&quot; value=&quot;0x08&quot; /&gt;
 *         &lt;/attr&gt;
 * </pre>
 * 
 * <li>支持单边画框</li>
 * 可使用图片或者 ColorStateList
 * </ol>
 * 
 * @author Wx
 * 
 */
public interface IBorderView {

	/**
	 * 左边框
	 */
	public static int BORDER_LEFT = 0x01;

	/**
	 * 右边框
	 */
	public static int BORDER_RIGHT = 0x02;

	/**
	 * 顶边框
	 */
	public static int BORDER_TOP = 0x04;

	/**
	 * 底边框
	 */
	public static int BORDER_BOTTOM = 0x08;

	/**
	 * 左边框
	 */
	public static int BORDER_TOP_BOTTOM = BORDER_TOP | BORDER_BOTTOM;

	/**
	 * 左边框宽度
	 * 
	 * @param borderLeftSize
	 */
	void setBorderLeftSize(int borderLeftSize);

	/**
	 * 右边框宽度
	 * 
	 * @param borderRightSize
	 */
	void setBorderRightSize(int borderRightSize);

	/**
	 * 上边框宽度
	 * 
	 * @param borderTopSize
	 */
	void setBorderTopSize(int borderTopSize);

	/**
	 * 下边框宽度
	 * 
	 * @param size
	 */
	void setBorderBottomSize(int size);

	/**
	 * 左边框作图资源
	 * 
	 * @param borderLeftDrawable
	 */
	void setBorderLeftDrawable(Drawable borderLeftDrawable);

	/**
	 * 右边框做图资源
	 * 
	 * @param borderRightDrawable
	 */
	void setBorderRightDrawable(Drawable borderRightDrawable);

	/**
	 * 上边框作图资源
	 * 
	 * @param borderTopDrawable
	 */
	void setBorderTopDrawable(Drawable borderTopDrawable);

	/**
	 * 下边框作图资源
	 * 
	 * @param borderBottomDrawable
	 */
	void setBorderBottomDrawable(Drawable borderBottomDrawable);

	/**
	 * 设置top下划线距离左边的距离
	 * 
	 * @param size
	 */
	void setBorderTopPaddingLeft(int size);

	/**
	 * 设置top下划线距离左边的距离
	 * 
	 * @param size
	 */
	void setBorderTopPaddingRight(int size);

	/**
	 * 设置bottom下划线距离左边的距离
	 * 
	 * @param size
	 *            2014年9月17日-下午3:10:55
	 * @author wx
	 */
	void setBorderBottomPaddingLeft(int size);

	/**
	 * 设置bottom下划线距离左边的距离
	 * 
	 * @param size
	 *            2014年9月17日-下午3:10:55
	 * @author wx
	 */
	void setBorderBottomPaddingRight(int size);

	/**
	 * 边框是否放置在内边距内，默认 <code>true</code>
	 * 
	 * @param borderInsidePadding
	 */
	void setBorderInsidePadding(boolean borderInsidePadding);

	/**
	 * 四边框单色，优先于单边框作图，即可以被指定的单边覆盖
	 * 
	 * @param color
	 */
	void setBorderColor(int color);

	/**
	 * 四边框宽度
	 * 
	 * @param pix
	 */
	void setborderWidth_(int pix);

	/**
	 * 指定要画的单色四边框
	 * 
	 * <pre>
	 *         &lt;attr name=&quot;borders&quot;&gt;
	 *             &lt;flag name=&quot;left&quot; value=&quot;0x01&quot; /&gt;
	 *             &lt;flag name=&quot;right&quot; value=&quot;0x02&quot; /&gt;
	 *             &lt;flag name=&quot;top&quot; value=&quot;0x04&quot; /&gt;
	 *             &lt;flag name=&quot;bottom&quot; value=&quot;0x08&quot; /&gt;
	 *         &lt;/attr&gt;
	 * </pre>
	 * 
	 * @param borders
	 */
	void setBorders(int borders);

	/**
	 * 左边线顶部内距
	 * 
	 * @param borderLeftPaddtingTopSize
	 */
	void setBorderLeftPaddingTop(int borderLeftPaddtingTopSize);

	/**
	 * 
	 * 左边线底部内距
	 * 
	 * @param borderLeftPaddtingBottomSize
	 */
	void setBorderLeftPaddingBottom(int borderLeftPaddtingBottomSize);

	/**
	 * 右边线底部内边距
	 * 
	 * @param borderRightPaddingBottom
	 */
	void setBorderRightPaddingBottom(int borderRightPaddingBottom);

	/**
	 * 右边线顶部内边距
	 * 
	 * @param borderRightPaddingTopSize
	 */
	void setBorderRightPaddingTop(int borderRightPaddingTopSize);

}