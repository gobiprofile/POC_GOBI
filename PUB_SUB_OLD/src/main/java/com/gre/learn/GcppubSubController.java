package com.gre.learn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcppubSubController {

	@Value("${gcp.topicName}")
	private String topicName;
	
	@Value("${gcp.subscriptionName}")
	private String subscriptionName;

	private final PubSubTemplate pubSubTemplate;

	private final PubSubAdmin pubSubAdmin;

	public GcppubSubController(PubSubTemplate pubSubTemplate, PubSubAdmin pubSubAdmin) {
		this.pubSubTemplate = pubSubTemplate;
		this.pubSubAdmin = pubSubAdmin;
	}

	@GetMapping("/postMessage")
	public String publish(@RequestParam("topicName") String topicName, @RequestParam("message") String message) {
		for (int i = 0; i< 10; i++) {
			this.pubSubTemplate.publish(topicName, (message +""+ i));
		}

		return "Messages published asynchronously";
	}

	@GetMapping("/subscribe")
	public String subscribe(@RequestParam("subscription") String subscriptionName) {
		List<String> list = new ArrayList<>();
		this.pubSubTemplate.subscribe(subscriptionName, message -> {
			String message1 = message.getPubsubMessage().getData().toStringUtf8();
			System.out.println("Message received from " + subscriptionName + " subscription: "
					+ message1);
			list.add(message1);
			message.ack();
		});
		
		for (String msg : list) {
			System.out.println("Message :"+ msg);
		}

		return "Subscribed.";
	}

	@PostConstruct
	public String subscribe() {
		this.pubSubTemplate.subscribe(subscriptionName, message -> {
			String message1 = message.getPubsubMessage().getData().toStringUtf8();
			System.out.println("Message received from " + subscriptionName + " subscription: "
					+ message1);
			message.ack();
		});

		return "Subscribed.";
	}

	@GetMapping("/listTopic")
	public String deleteTopic() {
		List<com.google.pubsub.v1.Topic> topics = this.pubSubAdmin.listTopics();
		
		for (com.google.pubsub.v1.Topic topic : topics) {
			System.out.println(topic.getName());
		}

		return "Topic list successfully.";
	}

	@GetMapping("/listSubscription")
	public String deleteSubscription() {
		List<com.google.pubsub.v1.Subscription> subscriptions = this.pubSubAdmin.listSubscriptions();
		
		for (com.google.pubsub.v1.Subscription subscription : subscriptions) {
			System.out.println(subscription.getName());
		}

		return "Subscription list successfully.";
	}
	
	@GetMapping("/createSubscription")
	public String createSubscription(@RequestParam("subscription") String subscriptionName, @RequestParam("topicName") String topicName) {
		this.pubSubAdmin.createSubscription(subscriptionName,topicName);
		
		
		return "Create Subscription successfully.";
	}

//https://github.com/GoogleCloudPlatform/spring-cloud-gcp/blob/main/spring-cloud-gcp-samples/spring-cloud-gcp-pubsub-sample/src/main/java/com/example/WebController.java
	
}
