package com.taasha.microservice.api;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.taasha.microservice.model.CommentCollectionResource;
import com.taasha.microservice.model.CommentResource;

@Service
public class CommentsService {

	private static final Logger LOGGER = Logger.getLogger(CommentsService.class.getName());
	
	/*@Autowired
	private OAuth2RestTemplate restTemplate;*/
	
	@HystrixCommand(fallbackMethod = "getFallbackCommentsForTask", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000") })
	public CommentCollectionResource getCommentsForTask(String taskId) {
		/**
		 * We can't use the context root for comments webservice because of
		 * problems getting Spring cloud contract to work with it.
		 * 
		 * If we add context-root, then we cannot mock the server on the
		 * comments-webservice side and we will have to make calls with
		 * <code>testMode = 'EXPLICIT'</code> for the producer tests on the
		 * comments-webservice side. This doesn't work on the
		 * comments-webservice side since there is no mock OAuth2 token that
		 * gets injected there.
		 * 
		 * We can't introduce an annotation on the generated test class since
		 * there is NO original test and hence the @WithMockOAuth2Token
		 * mechanism that we use in the task-webservice project can't be used in
		 * the comments-webservice project.
		 */
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info(String.format("Checking comments for taskId [%s]", taskId));
		}
		return null;
		
		/*restTemplate.getForObject(String.format("http://comments-webservice/comments/%s", taskId),
				CommentCollectionResource.class);*/
	}
	
	@SuppressWarnings("unused")
	private CommentCollectionResource getFallbackCommentsForTask(String taskId) {
		// Get the default comments
		CommentCollectionResource comments = new CommentCollectionResource();
		comments.addComment(new CommentResource(taskId, "default comment", Calendar.getInstance().getTime()));

		return comments;
	}
}
