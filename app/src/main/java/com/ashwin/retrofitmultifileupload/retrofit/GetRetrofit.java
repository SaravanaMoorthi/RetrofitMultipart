package com.ashwin.retrofitmultifileupload.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static String BASE_URL = "https://imaexuat.tvscs.co.in/tvs/imaex/";
    private static final int REQ_TIME_OUT = 10;

    public static Retrofit getRetrofit(final UploadProgressRequestBody.ProgressListener progressListener) {
        Retrofit retrofit = null;
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
                    .connectTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();

                            if (originalRequest.body() == null) {
                                return chain.proceed(originalRequest);
                            }

                            Request progressRequest = originalRequest.newBuilder()
                                    .method(originalRequest.method(),
                                            new UploadProgressRequestBody(originalRequest.body(), progressListener))
                                    .build();

                            return chain.proceed(progressRequest);
                        }
                    }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = null;
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
                    .connectTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
