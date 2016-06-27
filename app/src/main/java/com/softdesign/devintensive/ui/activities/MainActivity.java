package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";

    private DataManager mDataManager;
    private int mCurrentEditMode = 0;

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFab;
    private TextView mRatingTextView, mCodeStringsTextView, mProjectsTextView;
    private EditText mUserPhone, mUserMail, mUserVK, mUserGit, mUserBio;

    private List<EditText> mUserInfoViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        mRatingTextView = (TextView) findViewById(R.id.rating_tv);
        mCodeStringsTextView = (TextView) findViewById(R.id.code_string_tv);
        mProjectsTextView = (TextView) findViewById(R.id.projects_tv);

        mUserInfoViews = new ArrayList<>();
        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserInfoViews.add(mUserPhone);
        mUserMail = (EditText) findViewById(R.id.email_et);
        mUserInfoViews.add(mUserMail);
        mUserVK = (EditText) findViewById(R.id.vk_et);
        mUserInfoViews.add(mUserVK);
        mUserGit = (EditText) findViewById(R.id.github_et);
        mUserInfoViews.add(mUserGit);
        mUserBio = (EditText) findViewById(R.id.about_et);
        mUserInfoViews.add(mUserBio);

        mDataManager = DataManager.getInstance();
        loadUserInfoValue();

        //TODO: Заполнить данные формы для больей наглядности.

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolBar();

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        setupDrawer();

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        if (savedInstanceState == null) {
            //активность запускается впервые
        } else {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * За onCreate() всегда следует вызов onStart(), но перед onStart() не обязательно должен
     * идти onCreate(), onStart() может вызываться и для возобновления работы приостановленного
     * приложения.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    /**
     * Метод onResume() вызывается после onStart(). Также может вызываться после onPause().
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        loadUserInfoValue();
    }

    /**
     * Метод onPause() вызывается после сворачивания текущей активности или перехода к
     * новому. От onPause() можно перейти к вызову либо onResume(), либо onStop().
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        saveUserInfoValue();
    }

    /**
     * Метод onStop() вызывается, когда окно становится невидимым для пользователя. Это можtn
     * произойти при её уничтожении, или если была запущена другая активность (существующая или
     * новая), перекрывшая окно текущей активности.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    /**
     * Если окно возвращается в приоритетный режим после вызова onStop(), то в этом случае
     * вызывается метод onRestart(). Т.е. вызывается после того, как активность была остановлена и
     * снова была запущена пользователем. Всегда сопровождается вызовом метода onStart().
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    /** Метод вызывается по окончании работы активности, при вызове метода finish() или в случае,
     * * когда система уничтожает этот экземпляр активности для освобождения ресурсов. */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.fab:
                if (mCurrentEditMode == 0) {
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                } else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
        }
    }

    private void showSnackBar(String message) {
        Log.d(TAG, "showSnackBar");
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolBar() {
        Log.d(TAG, "setupToolBar");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        Log.d(TAG, "setupDrawer");
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    showSnackBar(item.getTitle().toString());
                    item.setChecked(true);
                    mNavigationDrawer.closeDrawer(GravityCompat.START);
                    return false;
                }
            });
        }
    }

    /**
     * Переключает режим редактрования.
     *
     * @param mode если 1 - режим редактирования, если 0 - режим просмотра.
     */
    private void changeEditMode(int mode) {
        Log.d(TAG, "changeEditMode");
        if (mode == 1) {
            mFab.setImageResource(R.drawable.ic_done);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
            }
        }
    }

    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileDate();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}


