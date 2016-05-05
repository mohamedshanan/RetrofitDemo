package com.renasoft.retrofitfulldemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity implements View.OnClickListener {
    public final static int FLAG_GET = 0;
    public final static int FLAG_GET_ARGS = 1;
    public final static int FLAG_POST_PARAMS = 2;
    public final static int FLAG_POST_JSON = 3;
    public final static int FLAG_HEADERS = 4;
    public final static int FLAG_BODY = 5;
    public final static int FLAG_UPLOAD = 6;

    private final static int REQUEST_CODE = 1;

    public static Uri file_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureViews();
    }

    private void configureViews() {
        Button btn_get = (Button) findViewById(R.id.btn_get);
        Button btn_GetWithArgs = (Button) findViewById(R.id.btn_get_with_args);
        Button btn_post_params = (Button) findViewById(R.id.btn_post_params);
        Button btn_post_json = (Button) findViewById(R.id.btn_post_json);
        Button btn_headers = (Button) findViewById(R.id.btn_headers);
        Button btn_body = (Button) findViewById(R.id.btn_body);
        Button btn_upload = (Button) findViewById(R.id.btn_upload);

        btn_get.setOnClickListener(this);
        btn_GetWithArgs.setOnClickListener(this);
        btn_post_params.setOnClickListener(this);
        btn_post_json.setOnClickListener(this);
        btn_headers.setOnClickListener(this);
        btn_body.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                HttpApi.testApiRequest(FLAG_GET, this);
                break;
            case R.id.btn_get_with_args:
                HttpApi.testApiRequest(FLAG_GET_ARGS, this);
                break;
            case R.id.btn_post_params:
                HttpApi.testApiRequest(FLAG_POST_PARAMS, this);
                break;
            case R.id.btn_post_json:
                HttpApi.testApiRequest(FLAG_POST_JSON, this);
                break;
            case R.id.btn_headers:
                HttpApi.testApiRequest(FLAG_HEADERS, this);
                break;
            case R.id.btn_body:
                HttpApi.testApiRequest(FLAG_BODY, this);
                break;
            case R.id.btn_upload:
                Toast.makeText(MainActivity.this, "Still working on it    :)", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                String file_chooser_result = data.getStringExtra("result");
                file_uri = Uri.parse(new File(file_chooser_result).toString());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Couldn't select a file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
