package com.fastwon.board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Component
public class LambdaService {
    private LambdaClient lambdaClient;

    public LambdaService(@Value("${aws.accessKeyId}") String accessKeyId,
                         @Value("${aws.secretKey}") String secretKey) {
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, secretKey));

        this.lambdaClient = LambdaClient.builder()
                .region(Region.AP_NORTHEAST_2) // 사용하는 리전에 맞게 설정
                .credentialsProvider(credentialsProvider)
                .build();
    }

    public String invokeLambdaFunction(String functionName, String payload) {
        SdkBytes payloadBytes = SdkBytes.fromUtf8String(payload);

        InvokeRequest request = InvokeRequest.builder()
                .functionName(functionName)
                .payload(payloadBytes)
                .build();

        InvokeResponse response = lambdaClient.invoke(request);

        return response.payload().asUtf8String();
    }
}
