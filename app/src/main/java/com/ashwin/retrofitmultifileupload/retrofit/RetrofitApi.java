package com.ashwin.retrofitmultifileupload.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApi {
    String MEDIA_TYPE_TEXT = "text/plain";
    String MEDIA_TYPE_FILE = "*/*";

    String AREA = "area";

    String CUSTOMER_NAME = "customername";

    String DEALER_CODE = "dealercode";

    String IMEI = "imei";

    String LATITUDE = "lat";
    String LONGITUDE = "lang";

    String METHOD_NAME = "name";
    String MAP_KYC = "mapekyc";

    String PORTFOLIO = "portfolio";
    String PROGRAM_CODE = "programcode";

    String REFERENCE_NUMBER = "referencenumber";

    String SID = "sid";
    String SOURCE = "source";

    String TIME_STAMP = "timestamp";

    String USER_ID = "userid";

    @Multipart
    @POST("transaction/Mobiletransaction")
    Call<UploadResult> uploadFile(@Part List<MultipartBody.Part> file, @Part(METHOD_NAME) RequestBody name,
                                  @Part(CUSTOMER_NAME) RequestBody customerName, @Part(USER_ID) RequestBody userid,
                                  @Part(PORTFOLIO) RequestBody portfolio, @Part(IMEI) RequestBody imei,
                                  @Part(LATITUDE) RequestBody latitude, @Part(LONGITUDE) RequestBody longtitude,
                                  @Part(SID) RequestBody sid, @Part(TIME_STAMP) RequestBody timestamp,
                                  @Part(AREA) RequestBody area, @Part(DEALER_CODE) RequestBody dealercode,
                                  @Part(PROGRAM_CODE) RequestBody programcode, @Part(MAP_KYC) RequestBody mapekyc,
                                  @Part(REFERENCE_NUMBER) RequestBody refNo, @Part(SOURCE) RequestBody source);
}
