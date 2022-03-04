package com.exc.roadlamp.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadUtils {
    private static DownloadUtils instance;
    private OkHttpClient okHttpClient;
    private Handler mHandler; //所有监听器在 Handler 中被执行,所以可以保证所有监听器在主线程中被执行

    public static DownloadUtils getInstance(Activity context) {
        if (instance == null) instance = new DownloadUtils(context);
        return instance;
    }

    private DownloadUtils(Activity context) {
        this.mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();

        PRDownloader.initialize(context.getApplicationContext());
        PRDownloaderConfig config1 = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(context.getApplicationContext(), config1);

        PRDownloaderConfig config2 = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30_000)
                .setConnectTimeout(30_000)
                .build();
        PRDownloader.initialize(context.getApplicationContext(), config2);
    }

    public interface OnDownloadListener{
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(Progress progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final String saveName, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // Okhttp/Retofit 下载监听
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    File file = new File(saveDir, saveName);
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 下载完成
//                            listener.onDownloadSuccess();
                        }
                    });

                } catch (Exception e) {
                    Log.e("下载异常", e.getMessage());
//                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });

//        ProgressManager.getInstance().addResponseListener(url, new ProgressListener() {
//            @Override
//            public void onProgress(ProgressInfo progressInfo) {
//                listener.onDownloading(progressInfo);
//            }
//
//            @Override
//            public void onError(long l, Exception e) {
//                listener.onDownloadFailed();
//            }
//        });

        int downloadId = PRDownloader.download(url, saveDir, saveName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        listener.onDownloading(progress);
                    }
                })
                .start(new com.downloader.OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        listener.onDownloadSuccess();
                    }

                    @Override
                    public void onError(Error error) {
                        listener.onDownloadFailed();
                    }
                });

    }

}