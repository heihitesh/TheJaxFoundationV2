package com.itshiteshverma.thejaxfoundationv2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int PERMISSION_REQUEST_CODE = 1;

    final static String myurl = "http://www.jaxhcf.org";
   String ua = "Mozilla/5.0 (Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 [FB_IAB/FB4A;FBAV/28.0.0.20.16;]";
    private String webSettings;
    private WebView mweb;
    SwipeRefreshLayout mySwipeRefreshLayout;
    Context context;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        activity = this;
        mySwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mweb.reload();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                if (mySwipeRefreshLayout.isRefreshing()) {
                                    mySwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, 5500);

                    }
                }
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       Intent i = new Intent(Intent.ACTION_SEND);
                                       i.setType("message/rfc822");
                                       i.putExtra(Intent.EXTRA_EMAIL, new String[]{"yrjitendrakumar@gmail.com","theJaxFoundation@gmail.com"});
                                       i.putExtra(Intent.EXTRA_SUBJECT, "Hei");
                                       i.putExtra(Intent.EXTRA_TEXT, "");
                                       try {
                                           startActivity(Intent.createChooser(i, "Send mail..."));
                                       } catch (android.content.ActivityNotFoundException ex) {
                                           Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();

                                       }

//                                       new FinestWebView.Builder(MainActivity.this).theme(R.style.RedTheme)
//                                               .titleDefault("Bless This Stuff")
//                                               .webViewBuiltInZoomControls(true)
//                                               .showIconClose(false)
//                                               .showIconMenu(true)
//                                               .webViewDisplayZoomControls(true)
//                                               .dividerHeight(0)
//                                               .gradientDivider(false)
//                                               .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
//                                                       R.anim.activity_close_enter, R.anim.activity_close_exit)
//                                               .injectJavaScript("javascript: document.getElementById('msg').innerHTML='Hello "
//                                                       + "TheFinestArtist"
//                                                       + "!';")
//                                               .show("http://www.jaxhcf.org");

                                   }
                               }
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mweb = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = mweb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //  webSettings.setBuiltInZoomControls(true);
       mweb.getSettings().setUserAgentString(ua);

        mweb.setWebViewClient(new MyBrowser());
        mweb.loadUrl(myurl);
        findViewById(R.id.webView).setVisibility(View.VISIBLE);

        // TODO: Move this to where you establish a user session


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mweb.canGoBack()) {
                        mweb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            //hide loading image
            //findViewById(R.id.imageLoading1).setVisibility(View.GONE);
            //show webview
            findViewById(R.id.pbLoading).setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contact) {
            Intent i = new Intent(MainActivity.this, Contact.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
           Intent i = new Intent(MainActivity.this, MadeBy.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}