package com.example.projectquoteapp.Home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.projectquoteapp.R;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ViewPager viewPager;
    HomeAdapter homeAdapter;
    ArrayList<HomeModel> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models = new ArrayList<HomeModel>();
        models.add(new HomeModel(R.drawable.c2, "#GETCAPTIONHERE", "Caption for your social media"));
        models.add(new HomeModel(R.drawable.c3, "#CAPTIONTIPS", "Tips choosing caption"));
        models.add(new HomeModel(R.drawable.c7, "#GETCAPTION", "Express feeling on your IG"));
        models.add(new HomeModel(R.drawable.b111, "#GETBESTCAPT", "Express love with caption"));
        models.add(new HomeModel(R.drawable.b11, "#STAYHEALTY", "Preventive Defense"));
        models.add(new HomeModel(R.drawable.c6, "#STAYATHOME", "We can do it"));

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        homeAdapter = new HomeAdapter(models, getContext());
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(homeAdapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.colorAccent)
        };
        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (homeAdapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return v;
    }

}
