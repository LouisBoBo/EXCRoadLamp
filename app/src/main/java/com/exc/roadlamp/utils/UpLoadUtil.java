package com.exc.roadlamp.utils;

import android.content.Context;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.exc.roadlamp.http.HttpApi;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpLoadUtil {


    /**
     * 上传图片
     *
     * @return 新图片的路径
     * @throws IOException
     * @throws JSONException
     */
    @NotNull
    public static void uploadImage(Context context, File imgFile, UpLoadImgListener upLoadImgListener) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String imgName = imgFile.getName();
        RequestBody image = RequestBody.create(MediaType.parse("image/jpg"), imgFile);
        Request.Builder builder = new Request.Builder();
        builder.addHeader("token", TokenUtils.getToken());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imgName, image)
//                .addFormDataPart("file", imagePath, image)
                .addFormDataPart("fileType", "1")
                .build();
        Request request = builder
                .url(HttpApi.UPLOAD_IMG)
                .post(requestBody)
                .build();


        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                upLoadImgListener.upLoadFail();
                android.util.Log.e("上传图片", "失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    System.out.println(response.body().toString());
//                } else {
//                    System.out.println(response.code());
//                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    UpLoadImageResult result = JSON.parseObject(jsonObject.toString(), UpLoadImageResult.class);


                    if (result.getCode() != 200) {
                        upLoadImgListener.upLoadFail();
                        android.util.Log.e("上传图片", "失败" + result.getMessage());

                    } else {
                        upLoadImgListener.upLoadSuccess(result.getData());
                        android.util.Log.e("上传图片", "成功" + jsonObject.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface UpLoadImgListener {
        void upLoadSuccess(int imgId);

        void upLoadFail();
    }


}
