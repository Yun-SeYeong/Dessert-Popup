package com.popup.dessert.dessertpopup.Config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Tag;
import software.amazon.awssdk.services.sns.model.TagResourceRequest;

@SpringBootTest
class AwsConfigTest {

  @Autowired
  private SnsClient snsClient;

  @Test
  @DisplayName(value = "create tag")
  public void createTag() {
    // given
    String key = "Test";
    String value = "TestValue";
    String snsArn = "arn:aws:sns:ap-northeast-1:190068804192:DessertPopup.fifo";

    // when
    TagResourceRequest tagResourceRequest = TagResourceRequest.builder()
        .resourceArn(snsArn)
        .tags(Tag.builder()
            .key(key)
            .value(value)
            .build())
        .build();

    snsClient.tagResource(tagResourceRequest);
  }

  @Test
  @DisplayName(value = "publish sms")
  public void publishSMS() {
    // given
    String message = "test";
    String phoneNumber = "+8201096283350";

    // when
    PublishRequest publishRequest = PublishRequest.builder()
        .message(message)
        .phoneNumber(phoneNumber)
        .build();

    PublishResponse response = snsClient.publish(publishRequest);
    System.out.println("response.messageId() = " + response.messageId());
    System.out.println(
        "response.sdkHttpResponse().statusCode() = " + response.sdkHttpResponse().statusCode());

  }
}