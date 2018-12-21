package com.ashwin.retrofitmultifileupload.retrofit;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;

public class UploadProgressRequestBody extends RequestBody {
    private final RequestBody requestBody;
    private final ProgressListener progressListener;

    public UploadProgressRequestBody(RequestBody requestBody, ProgressListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return requestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (progressListener == null) {
            requestBody.writeTo(sink);
            return;
        }
        ProgressOutputStream progressOutputStream = new ProgressOutputStream(sink.outputStream(), progressListener, contentLength());
        BufferedSink progressSink = Okio.buffer(Okio.sink(progressOutputStream));
        requestBody.writeTo(progressSink);
        progressSink.flush();
    }

    public interface ProgressListener {
        void update(long bytesWritten, long contentLength);
    }
}
