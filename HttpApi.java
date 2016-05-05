package com.renasoft.retrofitfulldemo;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class HttpApi {

    public static final String API_URL = "http://httpbin.org/";
    private static Context mContext;

    public static void testApiRequest(final int request_type, Context context) {
        mContext = context;
        final StringBuilder response_result = new StringBuilder("");
        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Service setup
        final HttpBinService service = retrofit.create(HttpBinService.class);

        // Prepare the HTTP request
        Call<HttpAPIResponse> call;
        switch (request_type) {
            case MainActivity.FLAG_GET:
                call = service.get();
                break;
            case MainActivity.FLAG_GET_ARGS:
                call = service.getWithArg("Test get with args");
                break;

            case MainActivity.FLAG_POST_PARAMS:
                call = service.postWithFormParams("Test post with form params");
                break;
            case MainActivity.FLAG_POST_JSON:
                call = service.postwithJson(new LoginData("Mohamed", "Shanan"));
                break;
            case MainActivity.FLAG_HEADERS:
                call = service.addCustomHeader("Accept: text/plain");
                break;
            case MainActivity.FLAG_BODY:
                call = service.asBody(new BodyData("dummy title",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit"));
                break;
            default:
                call = service.get();
                break;
        }

        // Asynchronously execute HTTP request
        call.enqueue(new Callback<HttpAPIResponse>() {
            @Override
            public void onResponse(Call<HttpAPIResponse> call, Response<HttpAPIResponse> response) {
                // http response status code + headers

                //"RAW: Response status code: " + response.code() + "\n"


                // if parsing the JSON body failed, `response.body()` returns null
                HttpAPIResponse decodedResponse = response.body();

                if (decodedResponse == null) return;

                //response.raw().toString() + "\n";


                // isSuccess is true if response code => 200 and <= 300

                if (!response.isSuccessful()) {
                    // print response body if unsuccessful
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {

                    }
                    return;
                }

                // at this point the JSON body has been successfully parsed

                String response_code = String.valueOf(response.code());
                String raw_response = response.raw().toString();

                String url = decodedResponse.url;
                String ip = decodedResponse.origin;
                String headers = decodedResponse.headers.toString();
                String get_args = decodedResponse.args.toString();
                String post_params = decodedResponse.form + "";

                Intent i = new Intent(mContext, ResponseActivity.class);

                i.putExtra("code", response_code);
                i.putExtra("raw", raw_response);
                i.putExtra("url", url);
                i.putExtra("ip", ip);
                i.putExtra("headers", headers);
                i.putExtra("args", get_args);
                i.putExtra("params", post_params);

                mContext.startActivity(i);
            }

            /**
             * onFailure gets called when the HTTP request didn't get through.
             * For instance if the URL is invalid / host not reachable
             */
            @Override
            public void onFailure(Call<HttpAPIResponse> call, Throwable t) {
                Log.i("Error: ", t.getLocalizedMessage());
            }
        });
    }

    /**
     * HttpBin.org service definition
     */
    public interface HttpBinService {
        @GET("/get")
        Call<HttpAPIResponse> get();

        // request /get?testArg=...
        @GET("/get")
        Call<HttpAPIResponse> getWithArg(
                @Query("testArg") String arg
        );

        // POST form encoded with form field params
        @FormUrlEncoded
        @POST("/post")
        Call<HttpAPIResponse> postWithFormParams(
                @Field("field1") String field1
        );

        // SEND Object in Request Body (POST)
        @POST("/post")
        Call<HttpAPIResponse> postwithJson(
                @Body LoginData loginData
        );

        // DEFINE DYNAMIC HEADER
        @GET("/get")
        Call<HttpAPIResponse> addCustomHeader(
                @Header("Accept-Charset") String accept_charset
        );

        // SEND data in Request Body (POST)
        @POST("/post")
        Call<HttpAPIResponse> asBody(
                @Body BodyData bodyData
        );
    }

    /**
     * Generic HttpBin.org Response Container
     */
    static class HttpAPIResponse {
        // the request url
        String url;

        // the requester ip
        String origin;

        // all headers that have been sent
        Map headers;

        // url arguments
        Map args;

        // post form parameters
        Map form;

        // post body json
        Map json;
    }

    /**
     * Exemplary login data sent as JSON
     */
    static class LoginData {
        String username;
        String password;

        public LoginData(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    /**
     * Exemplary data sent as JSON
     */
    static class BodyData {
        String title;
        String text;

        public BodyData(String title, String text) {
            this.title = title;
            this.text = text;
        }
    }

}
