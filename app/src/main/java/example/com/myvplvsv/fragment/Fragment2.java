package example.com.myvplvsv.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import example.com.myvplvsv.R;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment2_layout, container, false);
        ListView mLv = ((ListView) view.findViewById(R.id.lv));
        mLv.setDivider(new ColorDrawable(Color.parseColor("#00ff00")));
        mLv.setDividerHeight(5);
        mLv.setFocusable(false);

        return view;
    }
}