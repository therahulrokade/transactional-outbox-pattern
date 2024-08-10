package com.freecodebucket.service;

import com.freecodebucket.entity.Outbox;
import com.freecodebucket.publisher.MessagePublisher;
import com.freecodebucket.repository.OutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class OrderPollerService {

    private final OutboxRepository outboxRepository;
    private final MessagePublisher messagePublisher;

    public OrderPollerService(OutboxRepository outboxRepository, MessagePublisher messagePublisher) {
        this.outboxRepository = outboxRepository;
        this.messagePublisher = messagePublisher;
    }

    @Scheduled(fixedRate = 60000)
    public void pollOutboxMessagesAndPublish() {
        List<Outbox> unProcessedRecords = outboxRepository.findByProcessedFalse();
        log.info("Unprocessed records: {}", unProcessedRecords.size());
        unProcessedRecords.forEach(outboxMessage -> {
            try {
                messagePublisher.publish(outboxMessage.getPayload());
                outboxMessage.setProcessed(true);
                outboxRepository.save(outboxMessage);
                log.info("outbox processed: {}", outboxMessage.getId());
            }catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });

    }
}
