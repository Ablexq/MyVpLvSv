package example.com.myvplvsv;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.myvplvsv.adapter.MyAdapter;
import example.com.myvplvsv.fragment.Fragment1;
import example.com.myvplvsv.fragment.Fragment2;
import example.com.myvplvsv.fragment.Fragment3;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TAB_COUNT = 3;
    private TextView tab1;
    private TextView tab2;
    private TextView tab3;
    private ViewPager mVp;

    private int offset = 0;//初始位置
    private View cursor;
    private int scrollWidth;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        setListener();

        initVp();

        initTabs();
    }

    private void setListener() {
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
    }

    private void findViews() {
        tab1 = (TextView) findViewById(R.id.tab1);
        tab2 = (TextView) findViewById(R.id.tab2);
        tab3 = (TextView) findViewById(R.id.tab3);
        cursor = findViewById(R.id.cursor);
        mVp = ((ViewPager) this.findViewById(R.id.vp));
    }

    private void initTabs() {
        //将顶部文字恢复默认值
        setTabsColor(tab1);
        tab1.animate().scaleX(1.5f).scaleY(1.5f).setDuration(0);

        //屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;

        //指示器滚动间隔
        scrollWidth = screenW / 3;
        //指示器宽度设定
        int cursorWidth = (screenW / 6);
        //指示器初始位置
        offset = (screenW / TAB_COUNT - cursorWidth) / 2;

        //布局管理器使用方法一：
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cursor.getLayoutParams());
//        layoutParams.width = cursorWidth;
//        layoutParams.setMargins(offset, 0, 0, 0);
//        cursor.setLayoutParams(layoutParams);

        //布局管理器使用方法二：
        LinearLayout.LayoutParams cursorLayoutParams = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        cursorLayoutParams.width = cursorWidth;
        cursorLayoutParams.leftMargin = offset;
        cursor.setLayoutParams(cursorLayoutParams);
    }

    private void initVp() {
        ArrayList<Fragment> fragmentLists = new ArrayList<>();
        fragmentLists.add(new Fragment1());
        fragmentLists.add(new Fragment2());
        fragmentLists.add(new Fragment3());
        FragmentManager fragmentManager = getSupportFragmentManager();

        mVp.setFocusable(false);
        mVp.setAdapter(new MyAdapter(fragmentManager, fragmentLists));
        mVp.setOffscreenPageLimit(2);
        mVp.setCurrentItem(0);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    //成功左滑一页（0到1）：
                    // onPageScrolled执行，positionOffset从0开始变大，此时position=0，currentIndex=0
                    // onPageSelected执行：currentIndex变为1，此时position=0，currentIndex=1
                    // onPageScrolled继续执行，positionOffset从0开始变大直到无限接近1，此时position=0，currentIndex=1
                    // 最后onPageScrolled继续执行，positionOffset从无限接近1突然恢复到0，此时position=1，currentIndex=1
                    case 0:
                        if (currentIndex == 0) {
                            setIndicatorPosition(offset + (int) (scrollWidth * positionOffset));
                            Log.e("000", "0-->currentIndex == 0");
                        } else if (currentIndex == 1) {
                            setIndicatorPosition(offset + (int) (scrollWidth * (positionOffset)));
                            Log.e("000", "0-->currentIndex == 1");
                        }
                        break;

                    case 1:
                        if (currentIndex == 1) {
                            setIndicatorPosition(offset + scrollWidth + (int) (scrollWidth * positionOffset));
                            Log.e("000", "1-->currentIndex == 1");
                        } else if (currentIndex == 2) {
                            setIndicatorPosition(offset + scrollWidth + (int) (scrollWidth * (positionOffset)));
                            Log.e("000", "1-->currentIndex == 2");
                        }
                        break;

                    case 2:
                        if (currentIndex == 2) {
                            setIndicatorPosition(offset + scrollWidth * 2);
                            Log.e("000", "2-->currentIndex == 2");
                        }
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;//已选中位置:012
                Log.e("000", "onPageSelected==currentIndex==" + currentIndex);
                switch (position) {
                    case 0:
                        tab1.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200);
                        tab2.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                        tab3.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);

                        setTabsColor(tab1);
//                        setIndicatorPosition(offset);
                        break;

                    case 1:
                        tab2.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200);
                        tab1.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                        tab3.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);

                        setTabsColor(tab2);
//                        setIndicatorPosition(offset + scrollWidth);
                        break;

                    case 2:
                        tab3.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200);
                        tab2.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                        tab1.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);

                        setTabsColor(tab3);
//                        setIndicatorPosition(offset + scrollWidth * 2);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicatorPosition(int offset) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cursor.getLayoutParams());
        layoutParams.setMargins(offset, 0, 0, 0);
        cursor.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                mVp.setCurrentItem(0);
                break;
            case R.id.tab2:
                mVp.setCurrentItem(1);
                break;
            case R.id.tab3:
                mVp.setCurrentItem(2);
                break;

        }
    }

    private void setTabsColor(TextView textView) {
        tab1.setTextColor(ContextCompat.getColor(this, R.color.defalut_color));
        tab2.setTextColor(ContextCompat.getColor(this, R.color.defalut_color));
        tab3.setTextColor(ContextCompat.getColor(this, R.color.defalut_color));

        textView.setTextColor(ContextCompat.getColor(this, R.color.select_color));
    }


}
