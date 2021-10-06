package com.example.sqsreadandwritedybamodb.service.listener;

import com.example.sqsreadandwritedybamodb.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SQSListener {

    @Autowired
    private CarRepository carRepository;

//    @SqsListener(value = "test-queue")
//    public void loggingSQSMessage(String message) {
//        log.info("Message from SQS: {}", message);
//        messageRepository.save(new Message(message));
//    }
}
