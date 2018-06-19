package br.com.wilker.projeto2.fragments;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import br.com.wilker.projeto2.R;

/**
 * Created by Wilker on 06/06/2018.
 */

public class WebViewFragment extends Fragment{

    private Button botaoIr;
    private WebView mWebView;
    private TextInputEditText urlInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.web_view, container, false);
        mWebView = fragmentView.findViewById(R.id.web_view);
        botaoIr = fragmentView.findViewById(R.id.web_view_btn_ir);
        urlInput = fragmentView.findViewById(R.id.web_view_ipt_url);

        // configura a webView
        configurarWebView();

        // adiciona o onclick para navegar de acordo com o escrito no input
        botaoIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(urlInput.getText().toString());
            }
        });
        return fragmentView;
    }

    // configura WebView
    public void configurarWebView(){
        // abre apágina do google como padrão
        mWebView.loadUrl("https://www.google.com.br/");
        // configura a webview
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
    }

}
