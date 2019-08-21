package com.qantasloyalty.lsl.etlservice.etl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.google.common.collect.Lists;
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationMapper;
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataKey;
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataNew;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Component
public class BatchRetriever {

    @Autowired
    private AmazonDynamoDB awsdynamoDB;

    @Autowired
    private ApplicationMapper applicationMapper;

    private static final String APPLICATION_DATA_TABLE_NAME = "avro-dev-integration-motorapplication-application-data";

    public List<ApplicationDataNew> batchGetItems(List<ApplicationDataKey> applicationKeys) {
        List<ApplicationDataNew> applicationDataList = null;
        List<List<ApplicationDataKey>> choppedLists = Lists.partition(applicationKeys, 100);
        final int parallelism = 2;

        ForkJoinPool forkJoinPool = null;
        long startTime = System.currentTimeMillis();
        List<List<Map<String, AttributeValue>>> lists = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            lists = forkJoinPool.submit(() ->
                    //parallel stream invoked here
                    choppedLists.parallelStream()
                            .map(this::batchGet)
                            .collect(Collectors.toList())

            ).get();//this makes it an overall blocking call
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown(); //always remember to shutdown the pool
            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Parallel Fetch finished in " + elapsedTime + "ms");

        if (lists != null) {
            applicationDataList = lists
                    .stream()
                    .flatMap(Collection::stream)
                    .map(applicationMapper::fromAttributeValuesToApplicationData)
                    .collect(Collectors.toList());

        }
        return applicationDataList;
    }

    public List<Map<String, AttributeValue>> batchGet(List<ApplicationDataKey> applicationDataList) {
        long startTime = System.currentTimeMillis();
        BatchGetItemRequest request = new BatchGetItemRequest().addRequestItemsEntry(APPLICATION_DATA_TABLE_NAME, new KeysAndAttributes());
        applicationDataList.forEach((it) -> {
            KeysAndAttributes keysAndAttributes = request.getRequestItems().get(APPLICATION_DATA_TABLE_NAME);
            Map keyMap = new HashMap<String, AttributeValue>();
            keyMap.put("applicationId", new AttributeValue(it.getApplicationId()));
            keyMap.put("createdTimestamp", new AttributeValue(it.getLastDataCreatedTimestamp()));
            keysAndAttributes.withKeys(keyMap);
        });
        BatchGetItemResult batchGetItemResult = awsdynamoDB.batchGetItem(request);
        val mutableList = batchGetItemResult.getResponses().get(APPLICATION_DATA_TABLE_NAME);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Single BatchGet finished in " + elapsedTime + "ms");
        return mutableList;
    }
}

