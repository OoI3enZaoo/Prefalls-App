package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import Activity.R;

import static Activity.HomeActivity.PID;


public class FeedGaugeFragment extends Fragment {

    private WebView mWebView;
    public  FeedGaugeFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feed_gauge, container, false);
        mWebView = (WebView) v.findViewById(R.id.webview_gauge);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("http://sysnet.utcc.ac.th/prefalls/activity/index_phone_fallrisk.jsp?SSSN="+PID);

        return v;

    }


}
