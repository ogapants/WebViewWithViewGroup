package com.github.ogapants.webviewwithviewgroup

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * ref https://android.googlesource.com/platform/packages/apps/UnifiedEmail/+/master/src/com/android/mail/browse/ConversationContainer.java#
 */
class ArticleContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var webView: ArticleWebView
    private var widthMeasureSpec = 0
    private var webContentHeightDp = 0
    private var offsetY: Int = 0
    private var touchIsDown: Boolean = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        webView = findViewById(R.id.webView)
        header = findViewById(R.id.button1)
        footer = findViewById(R.id.button2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.widthMeasureSpec = widthMeasureSpec
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as ViewGroup.MarginLayoutParams
                val width = child.measuredWidth
                val height = child.measuredHeight
                val childLeft = paddingLeft + lp.leftMargin
                val childTop = paddingTop + lp.topMargin
                child.layout(childLeft, childTop, childLeft + width, childTop + height)
            }
        }
    }

    override fun generateDefaultLayoutParams(): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    fun dispatchScroll(oldScrollY: Int, scrollY: Int) {
        this.offsetY = scrollY
        val oldScrollYAbs = Math.abs(oldScrollY)
        val scrollYAbs = Math.abs(scrollY)
        val offset = oldScrollYAbs - scrollYAbs
        ViewCompat.offsetTopAndBottom(header, offset)
        ViewCompat.offsetTopAndBottom(footer, offset)
    }

    fun onReady(webContentHeightDp: Int) {
        this.webContentHeightDp = webContentHeightDp
        layoutHeaderFooter()
    }

    private fun layoutHeaderFooter() {
        val footerPositionTop = header.measuredHeight + webContentHeightDp
        if (webContentHeightDp == 0 || footer.measuredHeight == 0) {
            return
        }
        layoutOverlay(header, 0, header.measuredHeight)
        layoutOverlay(footer, footerPositionTop, footerPositionTop + footer.measuredHeight)
    }

    private fun layoutOverlay(child: View, childTop: Int, childBottom: Int) {
        val top = childTop - offsetY
        val bottom = childBottom - offsetY
        val lp = child.layoutParams as ViewGroup.MarginLayoutParams
        val childLeft = lp.leftMargin
        child.layout(childLeft, top, childLeft + child.measuredWidth, bottom)
    }

    private fun measureOverlayView(child: View) {
        var p: ViewGroup.MarginLayoutParams? = child.layoutParams as ViewGroup.MarginLayoutParams
        if (p == null) {
            p = generateDefaultLayoutParams()
        }

        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthMeasureSpec,
            paddingLeft + paddingRight + p.leftMargin + p.rightMargin, p.width
        )
        val lpHeight = p.height
        val childHeightSpec = if (lpHeight > 0) {
            View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        child.measure(childWidthSpec, childHeightSpec)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> touchIsDown = false
            MotionEvent.ACTION_MOVE -> if (!touchIsDown) {
                val newEvent = MotionEvent.obtain(ev)
                newEvent.action = MotionEvent.ACTION_DOWN
                webView.onTouchEvent(newEvent)
                touchIsDown = true
            }
        }
        return webView.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        //copy from ScrollView
        var isBeingDragged = false
        when (ev.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                isBeingDragged = true
            }
        }
        return isBeingDragged
    }

    fun fetchHeaderHeight(): Int {
        measureOverlayView(header)
        return header.measuredHeight
    }

    fun fetchFooterHeight(): Int {
        measureOverlayView(footer)
        return footer.measuredHeight
    }
}
