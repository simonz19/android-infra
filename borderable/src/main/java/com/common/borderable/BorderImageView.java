package com.common.borderable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.common.borderable.utils.ColorDrawableHelper;


public class BorderImageView extends AppCompatImageView implements IBadgeView,
		IBorderView, IHintView {

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

	private boolean badgeVisible;
	private boolean badgeTL;
	private boolean badgeTR;
	private boolean badgeBL;
	private boolean badgeBR;
	private int badgePostion;
	private int badgePadding;
	private int badgeMargin;
	private int badgeResourceId;
	private int badgeRadius;
	private int badgeColor;
	private Paint badgePaint;
	private int badgeCx;
	private int badgeCy;
	private Rect textbounds;
	private int badgePaddingTopAdd;
	private int badgePaddingTopSub;

	private boolean hintVisible;
	private boolean hintTL;
	private boolean hintTR;
	private boolean hintBL;
	private boolean hintBR;
	private boolean hintCenter;

	private int hintPostion;
	private int hintPadding;
	private int hintPadding2;
	private int hintMargin2;
	private int hintMargin;
	private int hintResourceId;
	private int hintRadius;
	private int hintColor;
	private Paint hintPaint;
	private int hintCx;
	private int hintCx2;
	private int hintCy;
	private int hintCy2;
	private Rect hintbounds;
	private String hintText;
	private String hintText2;
	private float hintTextSize;
	private int hintWidth;

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

	public BorderImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public BorderImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public BorderImageView(Context context) {
		super(context, null);
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
		attributes = context.obtainStyledAttributes(attrs, R.styleable.Badge,
				defStyle, 0);
		n = attributes.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = attributes.getIndex(i);
			if (attr == R.styleable.Badge_badgePosition) {
				setBadgePostion(attributes.getInt(attr, 0));
			} else if (attr == R.styleable.Badge_badgeColor) {
				setBadgeColor(attributes.getColor(attr, getResources().getColor(android.R.color.black)));
			} else if (attr == R.styleable.Badge_badgePadding) {
				setBadgePadding(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Badge_badgeMargin) {
				setBadgeMargin(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Badge_badgeRadius) {
				setBadgeRadius(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Badge_badgeResource) {
				setBadgeResource(attributes.getResourceId(attr, 0));
			} else if (attr == R.styleable.Badge_badgePaddingTopAdd) {
				setBadgePaddingTopAdd(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Badge_badgePaddingTopSub) {
				setBadgePaddingTopSub(attributes.getDimensionPixelSize(attr, 0));
			}

		}
		attributes = context.obtainStyledAttributes(attrs, R.styleable.Hint,
				defStyle, 0);
		for (int i = 0; i < n; i++) {
			int attr = attributes.getIndex(i);
			if (attr == R.styleable.Hint_hintPosition) {
				setHintPostion(attributes.getInt(attr, 0));
			} else if (attr == R.styleable.Hint_hintColor) {
				setHintColor(attributes.getColor(attr, getResources().getColor(android.R.color.black)));
			} else if (attr == R.styleable.Hint_hintPadding) {
				setHintPadding(attributes.getDimensionPixelSize(attr, 0));
			}else if(attr== R.styleable.Hint_hintPadding2){
				setHintPadding2(attributes.getDimensionPixelSize(attr, 0));
			}
			else if (attr == R.styleable.Hint_hintMargin2) {
				setHintMargin2(attributes.getDimensionPixelSize(attr, 0));
			}
			else if (attr == R.styleable.Hint_hintMargin) {
				setHintMargin(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Hint_hintRadius) {
				setHintRadius(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Hint_hintResource) {
				setHintResource(attributes.getResourceId(attr, 0));
			} else if (attr == R.styleable.Hint_hintText) {
				setHintText(attributes.getString(R.styleable.Hint_hintText));
			} 
			else if (attr == R.styleable.Hint_hintText2) {
				setHintText2(attributes.getString(R.styleable.Hint_hintText2));
			} 
			else if (attr == R.styleable.Hint_hintTextSize) {
				setHintTextSize(attributes.getDimensionPixelSize(attr, 0));
			} else if (attr == R.styleable.Hint_hintWidth) {
				setborderWidth_(attributes.getDimensionPixelSize(attr, 0));
			}
		}
		attributes.recycle();
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
	public void showBadge() {
		if (!badgeVisible) {
			badgeVisible = true;
			invalidate();
		}
	}

	@Override
	public void hideBadge() {
		if (badgeVisible) {
			badgeVisible = false;
			invalidate();
		}
	}

	@Override
	public void setBadgePostion(int position) {
		if (this.badgePostion == position)
			return;
		badgeTL = IBadgeView.POSITION_TOPLEFT == (IBadgeView.POSITION_TOPLEFT & position);
		badgeTR = IBadgeView.POSITION_TOPRIGHT == (IBadgeView.POSITION_TOPRIGHT & position);
		badgeBL = IBadgeView.POSITION_BOTTOMLEFT == (IBadgeView.POSITION_BOTTOMLEFT & position);
		badgeBR = IBadgeView.POSITION_BOTTOMRIGHT == (IBadgeView.POSITION_BOTTOMRIGHT & position);
		invalidate();
	}

	@Override
	public void setBadgePadding(int padding) {
		if (this.badgePadding == padding)
			return;
		this.badgePadding = padding;
		invalidate();
	}

	@Override
	public void setBadgeMargin(int margin) {
		if (this.badgeMargin == margin)
			return;
		if (textbounds == null) {
			textbounds = new Rect();
		}
		this.badgeMargin = margin;
		invalidate();
	}

	@Override
	public void setBadgeResource(int resid) {
		if (this.badgeResourceId == resid)
			return;
		this.badgeResourceId = resid;
		invalidate();
	}

	@Override
	public void setBadgeRadius(int radius) {
		if (this.badgeRadius == radius)
			return;
		this.badgeRadius = radius;
		invalidate();
	}

	@Override
	public void setBadgeColor(int color) {
		if (this.badgeColor == color)
			return;
		this.badgeColor = color;
		getBadgePaint().setColor(badgeColor);
		invalidate();
	}

	private Paint getBadgePaint() {
		if (badgePaint == null) {
			badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		return badgePaint;
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
		if (badgeVisible) {
			if (badgeRadius > 0) {
				canvas.drawCircle(badgeCx, badgeCy, badgeRadius, badgePaint);
			}
		}
		if (hintVisible) {
			if (hintRadius > 0) {
				canvas.drawText(hintText, hintCx, hintCy, hintPaint);
				if(!hintText.equals("") && hintCx2>0 &&hintCy>0)
				canvas.drawText(hintText2, hintCx2, hintCy2, hintPaint);
			}
		}
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
		if (badgeRadius > 0) {
			int width = getMeasuredWidth();
			int height = getMeasuredHeight();
			badgeCx = (badgeBL || badgeTL) ? badgePadding
					: (badgeBR | badgeTR) ? width - badgePadding : 0;
			badgeCy = (badgeTL || badgeTR) ? badgePadding
					: (badgeBL | badgeBR) ? height - badgePadding : 0;
		}
		if (hintRadius > 0) {
			int width = getMeasuredWidth();
			int height = getMeasuredHeight();
			hintCx = (hintBL || hintTL) ? hintPadding
					: (hintBR | hintTR) ? width - hintPadding : 0;
			hintCy = (hintTL || hintTR) ? hintPadding
					: (hintBL | hintBR) ? height - hintPadding : 0;
		}
		if(hintCenter){
//			
//			hintCx=(hintBL || hintTL) ? width/2+hintPadding
//					: (hintBR | hintTR) ? width/2 + hintPadding : 0;
//			hintCy = (hintTL || hintTR) ?height/2+ hintPadding
//					: (hintBL | hintBR) ? height/2+ hintPadding : 0;
			//padding =   image.getWitdh();
			//marign=image.height
			getHintPaint().setAlpha(hintRadius);
			hintCx=hintPadding;
			hintCy=hintMargin;
			hintCx2=hintPadding2;
			hintCy2=hintMargin2;
			
			
		}
	}

	@Override
	public void showHint() {
		if (!hintVisible) {
			hintVisible = true;
			invalidate();
		}
	}

	@Override
	public void hideHint() {
		if (hintVisible) {
			hintVisible = false;
			invalidate();
		}
	}

	@Override
	public void setHintPostion(int position) {
		if (this.hintPostion == position)
			return;
		hintCenter = IHintView.POSITION_CENTER == (IHintView.POSITION_CENTER & position);
		hintTL = IHintView.POSITION_TOPLEFT == (IHintView.POSITION_TOPLEFT & position);
		hintTR = IHintView.POSITION_TOPRIGHT == (IHintView.POSITION_TOPRIGHT & position);
		hintBL = IHintView.POSITION_BOTTOMLEFT == (IHintView.POSITION_BOTTOMLEFT & position);
		hintBR = IHintView.POSITION_BOTTOMRIGHT == (IHintView.POSITION_BOTTOMRIGHT & position);
	
		invalidate();
	}

	@Override
	public void setHintPadding(int padding) {
		if (this.hintPadding == padding)
			return;
		this.hintPadding = padding;
		invalidate();
	}

	@Override
	public void setHintMargin(int margin) {
		if (this.hintMargin == margin)
			return;
		if (hintbounds == null) {
			hintbounds = new Rect();
		}
		this.hintMargin = margin;
		invalidate();
	}
	
	public void setHintPadding2(int padding) {
		if (this.hintPadding2 == padding)
			return;
		this.hintPadding2 = padding;
		invalidate();
	}

	public void setHintMargin2(int margin) {
		if (this.hintMargin2 == margin)
			return;
		if (hintbounds == null) {
			hintbounds = new Rect();
		}
		this.hintMargin2 = margin;
		invalidate();
	}


	@Override
	public void setHintResource(int resid) {
		if (this.hintResourceId == resid)
			return;
		this.hintResourceId = resid;
		invalidate();
	}

	@Override
	public void setHintRadius(int radius) {
		if (this.hintRadius == radius)
			return;
		this.hintRadius = radius;
		invalidate();
	}

	@Override
	public void setHintColor(int color) {
		if (this.hintColor == color)
			return;
		this.hintColor = color;
		getHintPaint().setColor(hintColor);
		invalidate();
	}

	private Paint getHintPaint() {
		if (hintPaint == null) {
			hintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		return hintPaint;
	}

	@Override
	public void setHintText(String text) {
		if (this.hintText == text)
			return;
		this.hintText = text;
		invalidate();
	}

	public void setHintText2(String text) {
		if (this.hintText2 == text)
			return;
		this.hintText2 = text;
		invalidate();
	}
	@Override
	public void setHintWidth(int pix) {
		if (this.hintWidth == pix)
			return;
		hintWidth = pix;
		getHintPaint().setStrokeWidth(hintWidth);
		invalidate();
	}

	@Override
	public void setHintTextSize(float size) {
		if (this.hintTextSize == size)
			return;
		this.hintTextSize = size;
		getHintPaint().setTextSize(hintTextSize);
		invalidate();
	}

	/**
	 * 加重距离top上面的距离
	 * 
	 * @param size
	 *            2015年2月4日-上午11:41:54
	 *
	 */
	public void setBadgePaddingTopAdd(int size) {
		if (this.badgePaddingTopAdd == size)
			return;
		this.badgePaddingTopAdd = size;
		if (badgeTL || badgeTR) {
			badgeCy = badgeCy - badgePaddingTopAdd;
			invalidate();
		}
	}

	/**
	 * 减少距离top上面的距离
	 * 
	 * @param size
	 *            2015年2月4日-上午11:43:31
	 *
	 */
	public void setBadgePaddingTopSub(int size) {
		if (this.badgePaddingTopSub == size)
			return;
		this.badgePaddingTopSub = size;
		if (badgeTL || badgeTR) {
			badgeCy = badgeCy + size;
			invalidate();
		}
	}

	// 预留
	/*
	 * private void isPaddingTopAdd(){ if(badgePaddingTopAdd==0){ return ; } if
	 * (badgeTL || badgeTR) { badgeCy = badgeCy - badgePaddingTopAdd;
	 * invalidate(); } }
	 */

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
