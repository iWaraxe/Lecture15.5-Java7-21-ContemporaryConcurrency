package com.coherentsolutions.advanced.java.section03;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.List;

/**
 * Ex01FlowApiExample demonstrates a simple publisher-subscriber system using Java 9's Flow API.
 */
class MySubscriber<T> implements Flow.Subscriber<T> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);  // Request one item initially to handle backpressure
    }

    @Override
    public void onNext(T item) {
        System.out.println("Received: " + item);
        subscription.request(1);  // Request the next item
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("All items received.");
    }
}

public class Ex01FlowApiExample {
    public static void main(String[] args) throws InterruptedException {
        // Create a publisher (SubmissionPublisher allows us to submit items asynchronously)
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Create a subscriber
        MySubscriber<String> subscriber = new MySubscriber<>();

        // Register the subscriber with the publisher
        publisher.subscribe(subscriber);

        // Publish some items
        System.out.println("Publishing items...");
        List.of("Java", "Reactive Streams", "Flow API", "Backpressure").forEach(publisher::submit);

        // Close the publisher after all items are submitted
        publisher.close();

        // Allow the subscriber to finish processing
        Thread.sleep(1000);
    }
}
