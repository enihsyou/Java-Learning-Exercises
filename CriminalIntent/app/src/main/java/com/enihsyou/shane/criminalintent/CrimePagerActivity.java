package com.enihsyou.shane.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{
    private static final String EXTRA_CRIME_ID = "com.enihsyou.shane.criminalintent.crime_id";
    private ViewPager mViewPager;
    private List<Crime> mCrime;

    public static Intent newIntent(Context packageContext, UUID crimeID) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

    @Override
    public Intent getParentActivityIntent() {
        return getIntent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrime = CrimeLab.get(this).getCrimes();

        FragmentManager manager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public int getCount() {
                return mCrime.size();
            }

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrime.get(position);
                return CrimeFragment.newInstance(crime.getID());
            }
        });

        UUID crimeID = ((UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID));
        for (int i = 0; i < mCrime.size(); i++) {
            if (mCrime.get(i).getID().equals(crimeID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onCrimeUpdate(Crime crime) {

    }
}
