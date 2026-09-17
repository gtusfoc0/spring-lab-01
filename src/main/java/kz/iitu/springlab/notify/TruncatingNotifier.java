package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("truncating")
@Order(3)
public class TruncatingNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(TruncatingNotifier.class);

    @PostConstruct
    public void init() {
        log.info("LIFECYCLE >> TruncatingNotifier initialized successfully");
    }

    @Override
    public String send(String message) {
        if (message == null) {
            return "...";
        }
        if (message.length() > 20) {
            return message.substring(0, 20) + "...";
        }
        return message;
    }

    @Override
    public String channel() {
        return "truncating";
    }
}