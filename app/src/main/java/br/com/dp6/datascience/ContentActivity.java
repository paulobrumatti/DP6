package br.com.dp6.datascience;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class ContentActivity extends AppCompatActivity {

    private static final String SCREEN_NAME = "Cronograma";

    FloatingActionButton fab;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public void OnFragmentInteractionListener () {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        GTMHelper.pushScreenview(SCREEN_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(android.R.color.transparent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Uri uri = Uri.parse("https://www.google.com.br/maps/place/A+Figueira+Rubaiyat/@-23.5653559,-46.6718463,17z/data=!3m1!4b1!4m2!3m1!1s0x94ce583397637bdb:0xcc15b6cb26a775b5?hl=pt-BR"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                String title = String.valueOf(mSectionsPagerAdapter.getPageTitle(position));
                float size = title.equals("Local") ? 1 : 0;
                GTMHelper.pushScreenview(title);
                fab.animate().scaleX(size).scaleY(size);
            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private FragmentDict[] fragmentList = new FragmentDict[]{
                new FragmentDict("Cronograma", CronogramaFragment.newInstance()),
                new FragmentDict("Local", LocalFragment.newInstance()),
                new FragmentDict("Palestrantes", PalestrantesFragment.newInstance()),
                new FragmentDict("Sobre", SobreFragment.newInstance())
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList[position].fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList[position].title;
        }

        private class FragmentDict {
            public String title;
            public Fragment fragment;

            public FragmentDict(String title, Fragment fragment) {
                this.title = title;
                this.fragment = fragment;
            }
        }
    }

}
