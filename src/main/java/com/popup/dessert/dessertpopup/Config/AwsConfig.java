package com.popup.dessert.dessertpopup.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class AwsConfig {

  @Value("${aws.access-key}")
  private String accessKey;
  @Value("${aws.secret-key}")
  private String secretKey;

  @Bean
  public SnsClient snsClient(AwsBasicCredentials awsBasicCredentials) {
    return SnsClient.builder()
        .region(Region.AP_NORTHEAST_1)
        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
        .build();
  }

  @Bean
  public AwsBasicCredentials awsBasicCredentials() {
    return AwsBasicCredentials.create(
        "AKIASYQH3ZJQF2T2UJ26",
        "s8YuBUGsVU8WkEzv9NydBPg7fXMxwTK1aaKN+zKZ"
    );
  }
}
