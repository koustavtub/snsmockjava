package io.github.koustavtub;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SNSMockServerTest {
    Logger LOGGER = LoggerFactory.getLogger(SNSMockServerTest.class);

    @Test
    public void pingServer() throws IOException {

        int serverPort = MockSNSServer.start();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("http://localhost:" + serverPort + "/health");

            CloseableHttpResponse response = httpClient.execute(request);

            try {
                LOGGER.info(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    LOGGER.info("Entity as String: {}", EntityUtils.toString(entity));
                }

            } finally {
                response.close();
            }
        }


    }


}
