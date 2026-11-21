package com.apex.wallet.service;

import com.apex.wallet.model.IdempotencyRecord;
import com.apex.wallet.model.Transaction;
import com.apex.wallet.repository.IdempotencyRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IdempotencyService {
    private final IdempotencyRecordRepository idempotencyRepository;
    private final ObjectMapper objectMapper;

    public Optional<IdempotencyRecord> findRecord(String idempotencyKey){
        Optional<IdempotencyRecord> record = idempotencyRepository.findById(idempotencyKey);
        if(record.isPresent()){
            if(record.get().getExpiredAt().isBefore(OffsetDateTime.now())){
                idempotencyRepository.deleteById(idempotencyKey);
                return Optional.empty();
            }
            return record;
        }
        return Optional.empty();
    }

    @Transactional
    public void saveRecord(String key, Transaction transaction, int responseStatus, Object responseBody){
        IdempotencyRecord newRecord = new IdempotencyRecord();
        newRecord.setIdempotencyKey(key);
        newRecord.setTransaction(transaction);
        newRecord.setResponseStatus(responseStatus);
        try {
            newRecord.setResponseBody(objectMapper.writeValueAsString(responseBody));
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to serialize idempotency payload", ex);

        }
        idempotencyRepository.save(newRecord);
    }
}
