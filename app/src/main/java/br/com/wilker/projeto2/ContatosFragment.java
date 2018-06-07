package br.com.wilker.projeto2;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

/**
 * Created by Wilker on 06/06/2018.
 */

public class ContatosFragment extends Fragment {
    WebView mWebView;
    Button botaoIr;
    TextInputEditText urlInput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.contatos, container, false);
        return fragmentView;
    }


}
