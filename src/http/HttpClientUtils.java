package http;


import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientUtils {

    public static final String CHARSET = "UTF-8";

    public static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(35000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static CloseableHttpClient getHttpClient(int timeOut) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    
    
    
    /**
     * HTTP Get 
     * 
     * @param url 请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET);
    }

    
    public static String doGet(String url, Map<String, String> params, String charset) {
        return doGet(url, params, charset, null);
    }

    public static String doGet(String url, Map<String, String> params, String charset, Collection<Header> headers) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            if (CollectionUtils.isNotEmpty(headers)) {
                for (Header header : headers) {
                    httpGet.addHeader(header);
                }
            }
            return execute(null, httpGet, charset, null);
        } catch (Exception e) {
            log.error("",e);
        }
        return null;
    }



    public static String execute(HttpClient httpClient, @NonNull HttpUriRequest request, String charset,
        Integer timeOut) {
        boolean closeHttpClient = false;
        if (httpClient == null) {
            closeHttpClient = true;
            if (timeOut != null) {
                httpClient = getHttpClient(timeOut);
            } else {
                httpClient = getHttpClient();
            }
        }
        if (StringUtils.isBlank(charset)) {
            charset = CHARSET;
        }

        try {
            long current = System.currentTimeMillis();
            // log.debug("send request:{}", request);
            String result = httpClient.execute(request, new StringResponseHandler(request, charset));
            log.info("cost:{} ms to execute http method:{},url:{}", System.currentTimeMillis() - current,
                request.getMethod(), request.getURI());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("do request exception - ", e);
        } finally {
            if (closeHttpClient) {
                if (httpClient != null) {
                    if (httpClient instanceof Closeable) {
                        try {
                            ((Closeable) httpClient).close();
                        } catch (final IOException ignore) {
                        }
                    }
                }
            }
        }
    }
    
    static class StringResponseHandler implements ResponseHandler<String> {

        private HttpUriRequest request;

        private String charset;

        public StringResponseHandler(HttpUriRequest request, String charset) {
            this.request = request;
            this.charset = charset;
        }

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            try {
                StatusLine status = response.getStatusLine();
                log.debug("doPost - execute end, url:{}, status:{}", request.getURI(), status);
                if (status.getStatusCode() != HttpStatus.SC_OK) {
                    request.abort();
                    throw new RuntimeException("HttpClient,error status code :" + status);
                }
                HttpEntity responseEntity = response.getEntity();
                String result = null;
                if (responseEntity != null) {
                    result = EntityUtils.toString(responseEntity, charset);
                }
                log.debug("http response - url:{}, status:{}, response:{}", request.getURI(), status, result);
                return result;
            } finally {
                if (response != null) {
                    final HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        try {
                            EntityUtils.consume(entity);
                        } catch (final IOException ex) {
                        }
                    }
                }
            }

        }
    }

  
}
