package gameClient;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import gameCommon.GameException;
import gameCommon.GameProperties;

public class BaseGameClientApplication {

    private static int REQUEST_TIMEOUT = 1000;
    private static int CONNECTION_TIMEOUT = 5000;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
	PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
	result.setMaxTotal(20);
	return result;
    }

    @Bean
    public RequestConfig requestConfig() {
	RequestConfig result = RequestConfig.custom().setConnectionRequestTimeout(REQUEST_TIMEOUT)
		.setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(CONNECTION_TIMEOUT).build();
	return result;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
	    RequestConfig requestConfig) {
	CloseableHttpClient result = HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
		.setDefaultRequestConfig(requestConfig).build();
	return result;
    }

    @Bean
    public RestTemplate restTemplate(HttpClient httpClient) {
	HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	requestFactory.setHttpClient(httpClient);
	RestTemplate rt = new RestTemplate(requestFactory);
	rt.setErrorHandler(new ClientErrorHandler());
	return rt;
    }

    private class ClientErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
	    if (HttpStatus.OK.equals(response.getStatusCode())) {
		return false;
	    }
	    return true;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
	    if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
		String errorMessage = response.getHeaders().get(GameProperties.BAD_REQUEST_ERROR).get(0);
		throw new GameException(errorMessage);
	    }
	}
    }
}
