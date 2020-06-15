/*
 * Field naming:
 *
 * Non-public, non-static field names start with m.
 * Static field names start with s.
 * Other fields start with a lower case letter.
 * Public static final fields (constants) are ALL_CAPS_WITH_UNDERSCORES.
 *
 * Sample field naming:
 *
 * private static final int CONSTANT_VALUE = 42;
 * private static int sStaticIntValue = 42;
 * static int sStaticIntValue = 42;
 * public int publicIntValue = 42;
 * int mIntValue = 42;
 * private int mPrivateIntValue = 42;
 * protected int mIntValue = mProtectedIntValue = 42;
 *
 */

package br.svcdev.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);

        Fragment mFragment = new MainFragment();
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.fl_content_frame, mFragment);
        mFragmentTransaction.commit();
    }
}
