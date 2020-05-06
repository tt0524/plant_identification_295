package org.snowcorp.imageupload;


import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ImageUploadTask extends AsyncTask<String, Integer, String> {

    private static final String BASE_URL = "http://10.0.2.2:5000/upload";
    private static final String IMGUR_CLIENT_ID = "123";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final OkHttpClient client = new OkHttpClient.Builder()
                                            .connectTimeout(30, TimeUnit.SECONDS)
                                            .writeTimeout(30, TimeUnit.SECONDS)
                                            .readTimeout(30, TimeUnit.SECONDS).build();

    private File f;

    // Constructor
    public ImageUploadTask(File f) {
        this.f = f;
    }


    //执行上传图片线程，并返回线程执行的结果
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String...params) {
        String predictResult = null;
        try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", "Square Logo")
                    .addFormDataPart("file", UUID.randomUUID().toString() + ".png",
                            RequestBody.create(MEDIA_TYPE_PNG, f))
                    .build();
            //设置为自己的ip地址 BASE_URL
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                    .url(BASE_URL)
                    .post(requestBody)
                    .build();
            try (okhttp3.Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println("This is the response");
                predictResult = response.body().string();
                System.out.println(predictResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return predictResult;
    }


    // 在这里需要把ShowResult方法写进来
    @Override
    protected void onPostExecute(String s) {
        // 执行上传线程后，会得到预测结果，以String的形式返回，在onPostExecute里面要写showResult UI
        System.out.println("Post Execution:" + s);
    }

}
