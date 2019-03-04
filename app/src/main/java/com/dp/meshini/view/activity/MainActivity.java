package com.dp.meshini.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.dp.meshini.R;
import com.dp.meshini.view.activity.BaseActivity;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends BaseActivity implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.loadUrl("https://xd.adobe.com/view/4c7a3744-a13a-4862-597e-596994f82124-3a4b/?fullscreen");
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}
