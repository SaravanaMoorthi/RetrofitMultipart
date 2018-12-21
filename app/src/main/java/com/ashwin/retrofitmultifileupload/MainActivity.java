package com.ashwin.retrofitmultifileupload;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashwin.retrofitmultifileupload.retrofit.GetRetrofit;
import com.ashwin.retrofitmultifileupload.retrofit.RetrofitApi;
import com.ashwin.retrofitmultifileupload.retrofit.UploadProgressRequestBody;
import com.ashwin.retrofitmultifileupload.retrofit.UploadResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Author by Ashwin Prabhu*/

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private String FILE_KEY = "fileKey";

    private AppCompatActivity mActivity;
    private Dialog progressUploadDialog;
    private ProgressBar progressBar;
    private TextView txtViewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        uploadFile();
    }

    private void uploadFile() {
        try {

            /**
             * Display ProgressBar Using Dialog View
             */
            showUploadProgress();

            /**
             * Create Parameters
             */
            String name = "";
            String customerName = "";
            String userId = "";
            String imei = "";
            String latitude = "";
            String longitude = "";
            String sid = "";
            String timestamp = "";
            String portfolio = "";
            String area = "";
            String dealerCode = "";
            String programCode = "";
            String referenceNumber = "";
            String source = "";
            String mapekyc = "";


            /**
             * Create List For Multiple Files or Single File.
             */
            List<MultipartBody.Part> parts = new ArrayList<>();

            /**
             * This Line of code for adding multiple files from file list.
             */
//            for (int i = 0; i < fileList.size(); i++) {
//                File file = fileList.get(i);
//                if (file.getAbsolutePath().length() > 0) {
//                    parts.add(prepareFilePart(FILE_KEY, file.getAbsolutePath()));
//                }
//            }


            /**
             * This code for Single File if you no need of FileList
             */
            File file = null;
            if (file.getAbsolutePath().length() > 0) {
                parts.add(prepareFilePart(FILE_KEY, file.getAbsolutePath()));
            }

            /**
             * This code for Creating Request Body for All Text Parameters.
             */
            RequestBody requestBodyName = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), name);
            RequestBody requestBodyCustomerName = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), customerName);
            RequestBody requestBodyUserId = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), userId);
            RequestBody requestBodyImei = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), imei);
            RequestBody requestBodyLatitude = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), latitude);
            RequestBody requestBodyLongitude = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), longitude);
            RequestBody requestBodySid = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), sid);
            RequestBody requestBodyTimeStamp = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), timestamp);
            RequestBody requestBodyPortfolio = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), portfolio);
            RequestBody requestBodyArea = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), area);
            RequestBody requestBodyDealerCode = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), dealerCode);
            RequestBody requestBodyProgramCode = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), programCode);
            RequestBody requestBodyMapkyc = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), mapekyc);
            RequestBody requestBodyReferenceNumber = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), referenceNumber);
            RequestBody requestBodySource = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_TEXT), source);

            /**
             * this code for Creating retrofit Api Call.
             * The ProgressListener is used to Update the Progressbar while Uploading Data
             */
            RetrofitApi retrofitApi = GetRetrofit.getRetrofit(progressListener).create(RetrofitApi.class);

            /**
             * Make a call request for file uploading using Retrofit.
             */
            Call<UploadResult> call = retrofitApi.uploadFile(parts, requestBodyName, requestBodyCustomerName,
                    requestBodyUserId, requestBodyPortfolio, requestBodyImei,
                    requestBodyLatitude, requestBodyLongitude, requestBodySid,
                    requestBodyTimeStamp, requestBodyArea, requestBodyDealerCode,
                    requestBodyProgramCode, requestBodyMapkyc, requestBodyReferenceNumber, requestBodySource);

            call.enqueue(new Callback<UploadResult>() {
                @Override
                public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                    progressUploadDialog.dismiss();

                    UploadResult serverResponse = response.body();
                    if (serverResponse != null) {
                        Toast.makeText(getApplicationContext(), serverResponse.getStatus()
                                + "==="
                                + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Response ===> " + serverResponse.toString());
                    }
                }

                @Override
                public void onFailure(Call<UploadResult> call, Throwable t) {
                    progressUploadDialog.dismiss();
                    Log.e(TAG, "onFailure ===>" + t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUploadProgress() {
        progressUploadDialog = new Dialog(mActivity);
        progressUploadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressUploadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressUploadDialog.setCancelable(false);
        progressUploadDialog.setContentView(R.layout.dialog_upload_progress);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);

        txtViewProgress = (TextView) progressUploadDialog.findViewById(R.id.txtViewProgress);

        progressBar = (ProgressBar) progressUploadDialog.findViewById(R.id.circularProgressbar);
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(100);
        progressBar.setMax(100);
        progressBar.setProgressDrawable(drawable);
        progressUploadDialog.show();
    }

    /**
     * This code is used to create Request body for Files
     *
     * @param partName
     * @param fileUri
     * @return
     */
    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(RetrofitApi.MEDIA_TYPE_FILE), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    /**
     * This ProgressListener is used to update the Progressbar.
     */
    UploadProgressRequestBody.ProgressListener progressListener = new UploadProgressRequestBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength) {
            Log.e(UploadProgressRequestBody.class.getSimpleName(), "bytesRead: " + bytesRead);
            Log.e(UploadProgressRequestBody.class.getSimpleName(), "contentLength: " + contentLength);
            Log.e(UploadProgressRequestBody.class.getSimpleName(), "Done: " + (100 * bytesRead) / contentLength);

            final int uploadValue = (int) ((100 * bytesRead) / contentLength);

            progressBar.setProgress(uploadValue);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtViewProgress.setText(String.valueOf(uploadValue) + " uploading...");

                    if (uploadValue == 100 && progressUploadDialog != null) {
//                        progressUploadDialog.dismiss();
                    }
                }
            });
        }
    };
}
