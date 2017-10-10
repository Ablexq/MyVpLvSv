package example.com.myvplvsv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lenovo on 2017/10/8.
 */

public class MyScrollview extends ScrollView {


    private boolean isIntercept;
    private int yDown;
    private int yMove;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if (yMove - yDown < 0) {                    //根据业务需求更改判断条件，判断是时候需要拦截
                    isIntercept = false;
                } else if (yMove - yDown > 0 && getChildAt(0).getScrollY() == 0) {
                    isIntercept = true;
                } else if (yMove - yDown > 0 && getChildAt(0).getScrollY() > 0) {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }
        return isIntercept;         //返回true表示拦截，返回false表示不拦截
    }
}
