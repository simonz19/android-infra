package com.common.borderable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.common.borderable.utils.ColorDrawableHelper;


/**
 * 可分别画四边的布局
 * 
 * @author W,x
 * 
 */
public class BorderRadioGroup extends RadioGroup implements IBorderView {

	private boolean borderBottom;
	private boolean borderLeft;
	private boolean borderRight;
	private boolean borderTop;
	private int borderWidth_;
	private int borderColor;
	private Paint borderPaint;
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int borderBottomSize;
	private int borderTopSize;
	private int borderLeftSize;
	private int borderRightSize;
	private Drawable borderLeftDrawable;
	private Drawable borderRightDrawable;
	private Drawable borderTopDrawable;
	private Drawable borderBottomDrawable;
	private boolean borderInsidePadding;
	private ColorStateList borderBottomColorList;
	private ColorStateList borderTopColorList;
	private ColorStateList borderLeftColorList;
	private ColorStateList borderRightColorList;
	private Paint colorPaint;
	private int borderTopPaddingLeft;
	private int borderTopPaddingRight;
	private int borderBottomPaddingLeft;
	private int borderBottompaddingRight;
	private int borderLeftPaddingTop;
	private int borderLeftPaddtiongBottom;
	private int borderRightPaddingBottom;
	private int borderRightPaddingTop;

	public BorderRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public BorderRadioGroup(Context context) {
		this(context, null);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		TypedArray attributes = context.obtainStyledAttributes(attrs,
				R.styleable.BorderableViews, defStyle, 0);
		int n = attributes.getIndexCount();
		Drawable bottomDrawable = null;
		Drawable topDrawable = null;
		Drawable leftDrawable = null;
		Drawable rightDrawable = null;
		boolean borderInsidePadding = true;
		for (int i = 0; i < n; i++) {
			int attr = attributes.getIndex(i);
			if (attr == R.styleable.BorderableViews_borders) {
				int borders = attributes.getInt(attr, 0);
				setBorders(borders);
			} else if (attr == R.styleable.BorderableViews_borderWidth_) {
				setborderWidth_(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderColor) {
				setBorderColor(attributes.getColor(attr, Color.TRANSPARENT));
			} else if (attr == R.styleable.BorderableViews_borderBottom) {
				bottomDrawable = attributes.getDrawable(attr);
			} else if (attr == R.styleable.BorderableViews_borderBottomColor) {
				borderBottomColorList = attributes.getColorStateList(attr);
			} else if (attr == R.styleable.BorderableViews_borderBottomSize) {
				setBorderBottomSize(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderTop) {
				topDrawable = attributes.getDrawable(attr);
			} else if (attr == R.styleable.BorderableViews_borderTopColor) {
				borderTopColorList = attributes.getColorStateList(attr);
			} else if (attr == R.styleable.BorderableViews_borderTopSize) {
				setBorderTopSize(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderLeft) {
				leftDrawable = attributes.getDrawable(attr);
			} else if (attr == R.styleable.BorderableViews_borderLeftColor) {
				borderLeftColorList = attributes.getColorStateList(attr);
			} else if (attr == R.styleable.BorderableViews_borderLeftSize) {
				setBorderLeftSize(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderRight) {
				rightDrawable = attributes.getDrawable(attr);
			} else if (attr == R.styleable.BorderableViews_borderRightColor) {
				borderRightColorList = attributes.getColorStateList(attr);
			} else if (attr == R.styleable.BorderableViews_borderRightSize) {
				setBorderRightSize(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderInsidePadding) {
				borderInsidePadding = attributes.getBoolean(attr,
						borderInsidePadding);
			} else if (attr == R.styleable.BorderableViews_borderTopPaddingLeft) {
				setBorderTopPaddingLeft(attributes.getDimensionPixelSize(attr,
						0));
			} else if (attr == R.styleable.BorderableViews_borderTopPaddingRight) {
				setBorderTopPaddingRight(attributes.getDimensionPixelSize(attr,
						0));
			} else if (attr == R.styleable.BorderableViews_borderBottomPaddingLeft) {
				setBorderBottomPaddingLeft(attributes.getDimensionPixelSize(
						attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderBottomPaddingRight) {
				setBorderBottomPaddingRight(attributes.getDimensionPixelSize(
						attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderLeftPaddingTop) {
				setBorderLeftPaddingTop(attributes.getDimensionPixelSize(attr,
						0));
			} else if (attr == R.styleable.BorderableViews_borderLeftPaddingBottom) {
				setBorderLeftPaddingBottom(attributes.getDimensionPixelSize(
						attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderRightPaddingBottom) {
				setBorderRightPaddingBottom(attributes.getDimensionPixelSize(
						attr, 0));
			} else if (attr == R.styleable.BorderableViews_borderRightPaddingTop) {
				setBorderRightPaddingTop(attributes.getDimensionPixelSize(attr,
						0));
			}
		}
		attributes.recycle();
		if (topDrawable != null) {
			setBorderTopDrawable(topDrawable);
		}
		if (bottomDrawable != null) {
			setBorderBottomDrawable(bottomDrawable);
		}
		if (leftDrawable != null) {
			setBorderLeftDrawable(leftDrawable);
		}
		if (rightDrawable != null) {
			setBorderRightDrawable(rightDrawable);
		}
		setBorderInsidePadding(borderInsidePadding);
	}

	@Override
	public void setBorderLeftSize(int borderLeftSize) {
		this.borderLeftSize = borderLeftSize;
		invalidate();
	}

	@Override
	public void setBorderRightSize(int borderRightSize) {
		this.borderRightSize = borderRightSize;
		invalidate();
	}

	@Override
	public void setBorderTopSize(int borderTopSize) {
		this.borderTopSize = borderTopSize;
		invalidate();
	}

	@Override
	public void setBorderBottomSize(int size) {
		this.borderBottomSize = size;
		invalidate();
	}

	@Override
	public void setBorderLeftDrawable(Drawable borderLeftDrawable) {
		this.borderLeftDrawable = borderLeftDrawable;
		invalidate();
	}

	@Override
	public void setBorderRightDrawable(Drawable borderRightDrawable) {
		this.borderRightDrawable = borderRightDrawable;
		invalidate();
	}

	@Override
	public void setBorderTopDrawable(Drawable borderTopDrawable) {
		this.borderTopDrawable = borderTopDrawable;
		invalidate();
	}

	@Override
	public void setBorderBottomDrawable(Drawable borderBottomDrawable) {
		this.borderBottomDrawable = borderBottomDrawable;
		invalidate();
	}

	@Override
	public void setBorderInsidePadding(boolean borderInsidePadding) {
		this.borderInsidePadding = borderInsidePadding;
		initBorderPaint();
	}

	@Override
	public void setBorderColor(int color) {
		borderColor = color;
		initBorderPaint();
	}

	@Override
	public void setborderWidth_(int pix) {
		borderWidth_ = pix;
		initBorderPaint();
	}

	@Override
	public void setBorders(int borders) {
		borderLeft = (0x1 & borders) != 0;
		borderRight = (0x2 & borders) != 0;
		borderTop = (0x4 & borders) != 0;
		borderBottom = (0x8 & borders) != 0;
		initBorderPaint();
	}

	private void initBorderPaint() {
		if (borderBottom || borderRight || borderTop || borderLeft) {
			if (borderColor != Color.TRANSPARENT && borderWidth_ > 0) {
				if (borderPaint == null) {
					borderPaint = new Paint();
				}
				borderPaint.setColor(borderColor);
				borderPaint.setStrokeWidth(borderWidth_);
				setWillNotDraw(false);
				invalidate();
			} else {
				if (borderPaint != null) {
					borderPaint = null;
					invalidate();
					setWillNotDraw(true);
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		if (borderPaint != null) {
			if (borderBottom) {
				canvas.drawLine(left + borderBottomPaddingLeft,
						getMeasuredHeight() - bottom, getMeasuredWidth()
								- right - borderBottompaddingRight,
						getMeasuredHeight() - bottom, borderPaint);
			}
			if (borderTop) {
				canvas.drawLine(left + borderTopPaddingLeft, top,
						getMeasuredWidth() - right - borderTopPaddingRight,
						top, borderPaint);
			}
			if (borderLeft) {
				canvas.drawLine(left, top + borderLeftPaddingTop, left,
						getMeasuredHeight() - bottom
								- borderLeftPaddtiongBottom, borderPaint);

			}
			if (borderRight) {
				canvas.drawLine(
						getMeasuredWidth() - right,
						top + borderRightPaddingTop,
						getMeasuredWidth() - right,
						getMeasuredHeight() - bottom - borderRightPaddingBottom,
						borderPaint);
			}
		}
		if (borderLeftColorList != null || borderLeftDrawable != null) {
			int l = left;
			int t = top;
			int r = left + borderLeftSize;
			int b = getMeasuredHeight() - bottom;
			if (borderLeftColorList != null) {
				ColorDrawableHelper.drawRect(canvas, l, t, r, b,
						getColorPaint(), borderLeftColorList.getColorForState(
								getDrawableState(),
								borderLeftColorList.getDefaultColor()));
			}
			if (borderLeftDrawable != null) {
				if (borderLeftDrawable.isStateful())
					borderLeftDrawable.setState(getDrawableState());
				borderLeftDrawable.setBounds(l, t, r, b);
				borderLeftDrawable.draw(canvas);
			}
		}
		if (borderRightColorList != null || borderRightDrawable != null) {
			int l = getMeasuredWidth() - right - borderRightSize;
			int t = top;
			int r = getMeasuredWidth() - right;
			int b = getMeasuredHeight() - bottom;
			if (borderRightColorList != null) {
				ColorDrawableHelper.drawRect(canvas, l, t, r, b,
						getColorPaint(), borderRightColorList.getColorForState(
								getDrawableState(),
								borderRightColorList.getDefaultColor()));
			}
			if (borderRightDrawable != null) {
				if (borderRightDrawable.isStateful())
					borderRightDrawable.setState(getDrawableState());
				borderRightDrawable.setBounds(l, t, r, b);
				borderRightDrawable.draw(canvas);
			}
		}
		if (borderTopColorList != null || borderTopDrawable != null) {
			int l = left;
			int t = top;
			int r = getMeasuredWidth() - right;
			int b = top + borderTopSize;
			if (borderTopColorList != null) {
				ColorDrawableHelper.drawRect(canvas, l, t, r, b,
						getColorPaint(), borderTopColorList.getColorForState(
								getDrawableState(),
								borderTopColorList.getDefaultColor()));
			}
			if (borderTopDrawable != null) {
				if (borderTopDrawable.isStateful())
					borderTopDrawable.setState(getDrawableState());
				borderTopDrawable.setBounds(l, t, r, b);
				borderTopDrawable.draw(canvas);
			}
		}

		if (borderBottomColorList != null || borderBottomDrawable != null) {
			int l = left;
			int t = getMeasuredHeight() - borderBottomSize - bottom;
			int r = getMeasuredWidth() - right;
			int b = getMeasuredHeight() - bottom;
			if (borderBottomColorList != null) {
				ColorDrawableHelper.drawRect(canvas, l, t, r, b,
						getColorPaint(),
						borderBottomColorList.getColorForState(
								getDrawableState(),
								borderBottomColorList.getDefaultColor()));
			}
			if (borderBottomDrawable != null) {
				int bheight = borderBottomSize;
				if (bheight <= 0
						&& borderBottomDrawable.getIntrinsicHeight() > 0) {
					bheight = borderBottomDrawable.getIntrinsicHeight();
					t = getMeasuredHeight() - bheight - bottom;
				}
				canvas.save();
				if (borderBottomDrawable.isStateful())
					borderBottomDrawable.setState(getDrawableState());
				canvas.translate(0, t);
				borderBottomDrawable.setBounds(l, 0, r, bheight);
				borderBottomDrawable.draw(canvas);
				canvas.restore();
			}
		}
		canvas.restore();
	}

	private Paint getColorPaint() {
		if (colorPaint == null) {
			colorPaint = new Paint();
		}
		return colorPaint;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (borderInsidePadding) {
			left = getPaddingLeft();
			right = getPaddingRight();
			top = getPaddingTop();
			bottom = getPaddingBottom();
		} else {
			left = 0;
			right = 0;
			top = 0;
			bottom = 0;
		}
	}

	@Override
	public void setBorderTopPaddingLeft(int size) {
		this.borderTopPaddingLeft = size;
		invalidate();
	}

	@Override
	public void setBorderTopPaddingRight(int size) {
		this.borderTopPaddingRight = size;
		invalidate();
	}

	@Override
	public void setBorderBottomPaddingLeft(int size) {
		this.borderBottomPaddingLeft = size;
		invalidate();
	}

	@Override
	public void setBorderBottomPaddingRight(int size) {
		this.borderBottompaddingRight = size;
		invalidate();
	}

	@Override
	public void setBorderLeftPaddingTop(int borderLeftPaddtingTopSize) {
		this.borderLeftPaddingTop = borderLeftPaddtingTopSize;
		invalidate();
	}

	@Override
	public void setBorderLeftPaddingBottom(int borderLeftPaddtingBottomSize) {
		this.borderLeftPaddtiongBottom = borderLeftPaddtingBottomSize;
		invalidate();
	}

	@Override
	public void setBorderRightPaddingBottom(int borderRightPaddingBottom) {
		this.borderRightPaddingBottom = borderRightPaddingBottom;
		invalidate();
	}

	@Override
	public void setBorderRightPaddingTop(int borderRightPaddingTop) {
		this.borderRightPaddingTop = borderRightPaddingTop;
		invalidate();
	}
}
