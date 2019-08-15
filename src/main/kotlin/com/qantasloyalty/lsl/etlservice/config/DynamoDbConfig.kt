package com.qantasloyalty.lsl.etlservice.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.slf4j.LoggerFactory

@Configuration
class DynamoDbConfig {

    @Value("\${aws.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String? = null

    @Autowired
    private val accountCredentialsProvider: AWSCredentialsProvider? = null

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(EndpointConfiguration(amazonDynamoDBEndpoint, null))
                .withCredentials(accountCredentialsProvider)
                .build()
    }

    @Bean
    fun dynamoDb(): DynamoDB {
        return DynamoDB(amazonDynamoDB())
    }

    //FIXME should we use aws-dynamodb-encryption-java or do our own encryption since it's only for one field?
    @Bean
    fun dynamoDBMapper(): DynamoDBMapper {
        val dynamoDBMapperConfig = DynamoDBMapperConfig.builder()
                //.withTableNameResolver(dynamoDbTableNameResolver())
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                //                                                   .withSaveBehavior(SaveBehavior.PUT)      // Changing SaveBehaviour for encryption if using aws-dynamodb-encryption-java
                .build()


        //        final AWSKMS kms = AWSKMSClientBuilder.standard().withCredentials(accountCredentialsProvider()).build();
        //        final DirectKmsMaterialProvider cmp = new DirectKmsMaterialProvider(kms, cmkArn);
        //        final DynamoDBEncryptor encryptor = DynamoDBEncryptor.getInstance(cmp);
        //
        //        return new DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig(), new AttributeEncryptor(encryptor));

        return DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig)
    }

    /**
     * DynamoDBMapperConfig to use SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES so we can update
     * a small set of attributes only without impacting other updates that may be running.
     */
    @Bean
    fun dynamoDBMapperConfigPartialUpdate(): DynamoDBMapperConfig {
        return DynamoDBMapperConfig.builder()
               // .withTableNameResolver(dynamoDbTableNameResolver())
                .withSaveBehavior(SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                .build()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(javaClass);
    }

}
