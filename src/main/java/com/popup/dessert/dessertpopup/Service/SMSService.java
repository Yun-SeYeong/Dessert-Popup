package com.popup.dessert.dessertpopup.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
@RequiredArgsConstructor
public class SMSService {

  private final SnsClient snsClient;

  public void publishSMS(String phone, String code) {
    // given
    String message = "code:[" + code + "]";
    String phoneNumber = "+82" + phone;

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
