package com.sodagift.biz.config;

import com.sodagift.biz.domain.email.EmailHostHolder;
import com.sodagift.common.notification.email.configuration.EmailNotificationProperties;
import com.sodagift.common.notification.slack.SlackNotifier;
import com.sodagift.common.notification.slack.configuration.SlackNotificationInitializationConfiguration;
import com.sodagift.common.notification.slack.configuration.SlackNotificationPropertyConfiguration;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import({
        EmailNotificationProperties.class,
        com.sodagift.common.notification.email.EmailSender.class,
        EmailHostHolder.class,
        SlackNotifier.class,
        SlackNotificationInitializationConfiguration.class,
        SlackNotificationPropertyConfiguration.class
})
public class ApplicationConfiguration {

    @Bean
    @Profile("!test")
    public Clock defaultClock() {
        return Clock.systemUTC();
    }
}
