package com.android.huai.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloaderResponse extends ResponseBody {
    private final static String TAG = "DownloaderResponse";

    /**
     * 实际请求体
     * @return
     */
    private ResponseBody mResponsBody;

    /**
     * 下载进度回调接口
     * @return
     */
    private DownLoadManager.DownloaderCallback mCallback;

    private BufferedSource bufferedSource;

    public DownloaderResponse(ResponseBody responseBody, DownLoadManager.DownloaderCallback callback) {
        this.mResponsBody = responseBody;
        this.mCallback = callback;
    }

    @Override
    public MediaType contentType() {
        return mResponsBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponsBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(mResponsBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalread = 0l;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                Log.i(TAG, "read");
                long read = super.read(sink, byteCount);
                totalread +=  read != -1? read:0;
                long total = mResponsBody.contentLength();
                Log.i(TAG, "total: "+total);
                final int progress = (int) (totalread * 100 / total);
                mCallback.onProgress(progress);
                return read;
            }
        };
    }
}
