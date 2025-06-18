package com.reservationapi.infra.common.client.slack;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackClient {

    Slack slack = Slack.getInstance();

    public void sendMessage(String webhookUrl, Payload payload) throws IOException {
        slack.send(webhookUrl, payload);
    }
}
