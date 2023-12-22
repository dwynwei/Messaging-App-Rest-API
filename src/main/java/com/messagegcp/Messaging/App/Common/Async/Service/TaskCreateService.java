package com.messagegcp.Messaging.App.Common.Async.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.HttpMethod;
import com.google.cloud.tasks.v2.HttpRequest;
import com.google.cloud.tasks.v2.QueueName;
import com.google.cloud.tasks.v2.Task;
import com.google.protobuf.ByteString;
import com.nimbusds.jose.util.StandardCharset;

@Service
public class TaskCreateService {

	@Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.location-id}")
    private String locationId;

    @Value("${gcp.queue-name}")
    private String queueName;

    @Value("${api.key}")
    private String apiKey;

    private final Logger logger = LoggerFactory.getLogger(TaskCreateService.class);

    public void createTask(String to, String subject, String message) throws Exception {
        try (CloudTasksClient client = CloudTasksClient.create()) {
            String queuePath = QueueName.of(projectId, locationId, queueName).toString();
            String url = "https://many-donkeys-begin.loca.lt/send-email" 
            	    + "?to=" + URLEncoder.encode(to, StandardCharsets.UTF_8.name()) 
            	    + "&subject=" + URLEncoder.encode(subject, StandardCharsets.UTF_8.name()) 
            	    + "&content=" + URLEncoder.encode(message, StandardCharsets.UTF_8.name());

            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
                    .setUrl(url)
                    .setHttpMethod(HttpMethod.POST)
                    .putHeaders("API-Key", apiKey);

            Task.Builder taskBuilder = Task.newBuilder()
                    .setHttpRequest(httpRequestBuilder.build());

            Task task = client.createTask(queuePath, taskBuilder.build());
            System.out.println("Task Created : " + task.getName());
        } catch (Exception e) {
            logger.error("Task Queue Error : ", e);
            e.getCause().printStackTrace();
            throw e;
        }
    }
	
}
