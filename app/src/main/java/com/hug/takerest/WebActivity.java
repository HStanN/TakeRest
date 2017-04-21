package com.hug.takerest;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hug.takerest.base.BaseActivity;
import com.orhanobut.logger.Logger;

public class WebActivity extends BaseActivity {
    private ProgressBar progressbar;
    private WebView webview;
    private Toolbar toolbar;

    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void init() {
        super.init();
        initToolbar();
        setBackEnable(true);
        toolbar = getToolbar();
        toolbar.setTitle("");
        webview = (WebView) findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progress);
        initSetting();
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
    }

    @Override
    protected void bind() {
        super.bind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                webview.reload();
                break;
            case R.id.action_copy:
                String url = webview.getUrl();
                android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(url);
                Toast.makeText(WebActivity.this, "已经复制地址到剪切板", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSetting() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setSaveEnabled(false);
        webview.requestFocus();

        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar.setTitle(title);
            }

            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressbar.getVisibility() == ProgressBar.GONE) {
                    progressbar.setVisibility(View.VISIBLE);
                }

                progressbar.setProgress(progress);
                if (progress == 100) {
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (webview.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            webview.goBack();
            return true;
        } else if (!webview.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.loadUrl("about:blank");
    }
}
