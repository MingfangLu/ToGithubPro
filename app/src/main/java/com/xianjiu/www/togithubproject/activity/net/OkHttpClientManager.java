package com.xianjiu.www.togithubproject.activity.net;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.xianjiu.www.togithubproject.activity.base.BaseApplication;
import com.xianjiu.www.togithubproject.activity.net.cookies.CookiesManager;
import com.xianjiu.www.togithubproject.activity.utile.LogUtil;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp网络请求封装类
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private        OkHttpClient        mOkHttpClient;
    private        Handler             mDelivery;
    private        Gson                mGson;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit
                .SECONDS).readTimeout(30, TimeUnit.SECONDS).cookieJar(new CookiesManager(BaseApplication.context))
                .build();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, Map<String, String> params, final ResultCallback callback) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params)
            throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws
            IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedResultCallback(new BaseError(call.request(), e), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedResultCallback(new BaseError(response.request(), e), callback);
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
    }

    /**
     * 获取文件名
     *
     * @param path
     * @return
     */
    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    //*************供外部调用的方法************

    /**
     * 同步Get请求
     *
     * @param url 请求路径
     * @return
     * @throws IOException
     */
    public static Response getAsyn(String url) throws IOException {
        return getInstance()._getAsyn(url);
    }


    /**
     * 同步Get请求
     *
     * @param url 请求路径
     * @return
     * @throws IOException
     */
    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    /**
     * 异步Get请求
     *
     * @param url      请求路径
     * @param callback 响应回调接口
     */
    public static void getAsyn(String url, ResultCallback callback) {
        getInstance()._getAsyn(url, callback);
    }

    /**
     * 同步Post请求
     *
     * @param url    请求路径
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public static Response post(String url, Param... params) throws IOException {
        return getInstance()._post(url, params);
    }

    /**
     * 同步Post请求
     *
     * @param url    请求路径
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public static String postAsString(String url, Param... params) throws IOException {
        return getInstance()._postAsString(url, params);
    }

    /**
     * 异步Post请求
     *
     * @param url      请求路径
     * @param callback 响应回调接口
     * @param params   请求参数
     */
    public static void postAsyn(String url, final ResultCallback callback, Param... params) {
        getInstance()._postAsyn(url, callback, params);
    }


    /**
     * 异步Post请求
     *
     * @param url      请求路径
     * @param callback 响应回调接口
     * @param params   请求参数
     */
    public static void postAsyn(String url, Map<String, String> params, final ResultCallback callback) {
        getInstance()._postAsyn(url, params, callback);
    }


    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        return getInstance()._post(url, files, fileKeys, params);
    }

    public static Response post(String url, File file, String fileKey) throws IOException {
        return getInstance()._post(url, file, fileKey);
    }

    public static Response post(String url, File file, String fileKey, Param... params) throws IOException {
        return getInstance()._post(url, file, fileKey, params);
    }

    public static void postAsyn(String url, File[] files, String[] fileKeys, ResultCallback callback, Param...
            params) throws IOException {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public static void postAsyn(String url, File file, String fileKey, ResultCallback callback) throws IOException {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public static void postAsyn(String url, File file, String fileKey, ResultCallback callback, Param... params)
            throws IOException {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    public static void downloadAsyn(String url, String destDir, ResultCallback callback) {
        getInstance()._downloadAsyn(url, destDir, callback);
    }

    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        params = validateParam(params);
        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""), RequestBody
                    .create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + fileKeys[i] + "\"; " +
                        "filename=\"" + fileName + "\""), fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params) {
        if (params == null) return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";

    private Map<String, String> mSessions = new HashMap<String, String>();

    private void deliveryResult(final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedResultCallback(new BaseError(request, e, BaseError.STATUS_REQUEST_ERROR, "请求失败"), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    sendFailedResultCallback(new BaseError(response.request(), new Exception()), callback);
                    return;
                }
                String jsonString;
                JSONObject object = null;
                int status = 0;
                String message = "";
                try {
                    jsonString = response.body().string();
                    LogUtil.LogAllCat("1", "deliveryResult--->jsonString==" + jsonString);
                    object = new JSONObject(jsonString);
                    status = object.optInt("status");
                    message = object.optString("message");
                    if (status != 0) {
                        throw new Exception();
                    }
//                    if (callback.mType == String.class)
//                    {
//                        sendSuccessResultCallback(jsonString, callback);
//                    } else
//                    {
//                        Object o = mGson.fromJson(jsonString, callback.mType);
//                        sendSuccessResultCallback(o, callback);
//                    }
                    Object o = mGson.fromJson(jsonString, callback.tClass);
                    sendSuccessResultCallback(o, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    LogUtil.e(e.getMessage(), e);
                    sendFailedResultCallback(new BaseError(response.request(), e, BaseError.STATUS_PARSE_ERROR,
                            "数据解析错误"), callback);
                } catch (Exception e) {
                    LogUtil.e(e.getMessage(), e);
                    if (status == 2000) {
                        sendFailedResultCallback(new BaseError(response.request(), e, BaseError.STATUS_BIZ_ERROR,
                                message), callback);
                    } else if (status == 2100) {
                        // 版本过低的异常返回，需要强制升级
                        JSONObject data = object.optJSONObject("data");
                        if (data != null) {
                            // 处理版本过低的参数获取
                            String releaseNote = data.optString("releaseNote", "");
                            String url = data.optString("url", "");
                            String title = data.optString("title", "");
                            sendFailedResultCallback(new LowVersionError(response.request(), e,
                                    BaseError.STATUS_BIZ_ERROR, message, releaseNote, url, title), callback);
                        } else {
                            sendFailedResultCallback(new BaseError(response.request(), e, BaseError
                                    .STATUS_DATA_ERROR, message), callback);
                        }
                    } else if (status == 3000) {
                        sendFailedResultCallback(new BaseError(response.request(), e, BaseError.STATUS_APP_ERROR,
                                message), callback);

                    } else if (status == 4000) {
                        sendFailedResultCallback(new BaseError(response.request(), e, BaseError.STATUS_TOKEN_ERROR,
                                message), callback);
                    } else {
                        sendFailedResultCallback(new BaseError(response.request(), e, BaseError.STATUS_UNKNOW,
                                message), callback);
                    }
                }
            }
        });
    }

    private void sendFailedResultCallback(final BaseError baseError, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) callback.onError(baseError);
            }
        });
    }

    private void sendSuccessResultCallback(final Object result, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(result);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value==null?"":value;
        }

        String key;
        String value;
    }


}