package com.renasoft.retrofitfulldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    String code;
    String url;
    String ip;
    String headers;
    String args;
    String params;
    String raw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        extractData();

        TextView tv_code = (TextView) findViewById(R.id.tv_code);
        TextView tv_url = (TextView) findViewById(R.id.tv_url);
        TextView tv_ip = (TextView) findViewById(R.id.tv_ip);
        TextView tv_headers = (TextView) findViewById(R.id.tv_headers);
        TextView tv_args = (TextView) findViewById(R.id.tv_args);
        TextView tv_params = (TextView) findViewById(R.id.tv_params);
        TextView tv_raw = (TextView) findViewById(R.id.tv_raw);

        tv_code.setText(code);
        tv_url.setText(url);
        tv_ip.setText(ip);
        tv_headers.setText(headers);
        tv_args.setText(args);
        tv_params.setText(params);
        tv_raw.setText(raw);
    }

    private void extractData() {
        Intent intent = this.getIntent();
        if (intent != null) {
            this.code = intent.getStringExtra("code");
            this.url = intent.getStringExtra("url");
            this.ip = intent.getStringExtra("ip");
            this.headers = intent.getStringExtra("headers");
            this.args = intent.getStringExtra("args");
            this.params = intent.getStringExtra("params");
            this.raw = intent.getStringExtra("raw");
        }
    }
}
