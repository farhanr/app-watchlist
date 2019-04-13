package fr.pmobile.watchlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.LayoutInflaterCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {

    @BindView(R.id.tb_window)
    Toolbar toolbar;

    @BindView(R.id.tl_main)
    TabLayout tabLayout;

    @BindView(R.id.vp_main)
    CustomViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    Gson gson = new Gson();
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);

        sharedPref = getSharedPreferences("favData",Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.remove("favData").commit();

        setSupportActionBar(toolbar);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ReleasedFragment(), "RELEASED");
        viewPagerAdapter.addFragment(new UpcomingFragment(), "UPCOMING");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.disableScroll(true);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar,menu);
        if (menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_fav:
                Toast.makeText(this, "Congrats, you discovered an upcoming item!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                Toast.makeText(this, "Congrats, you discovered an upcoming item!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_credit:
                Toast.makeText(this, "aan",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
