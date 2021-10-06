package com.example.sqsreadandwritedybamodb.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.sqsreadandwritedybamodb.entity.Car;
import com.example.sqsreadandwritedybamodb.repository.CarCrudRepository;
import com.example.sqsreadandwritedybamodb.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private AmazonSQSAsync amazonSQSAsync;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private ReceiveMessageRequest receiveMessageRequest;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarCrudRepository carCrudRepository;

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    public List<Message> getAllMessage() {
        return amazonSQSAsync.receiveMessage(receiveMessageRequest).getMessages();
    }

    public Car convertToCar(String convert) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(convert, Car.class);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Message deleteMessage(Message message) {
        amazonSQSAsync.deleteMessageAsync(sqsEndPoint, message.getReceiptHandle());
        return message;
    }

    public Car findById(String carId) {
        return carRepository.findById(carId);
    }

    public Car delete(String carId) {
        return carRepository.delete(carId);
    }

    public String update(String carId, Car car) {
        return carRepository.update(carId, car);
    }

    public List<Car> findByModelAndMaxSpeedScan(String model, Integer maxSpeed) {
        return carCrudRepository.findByModelAndMaxSpeed(model, maxSpeed);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public List<Car> findByModelAndMaxSpeedQuery(String modelMaxSpeed) {
        List<Car> cars = new ArrayList<>();
        ItemCollection<QueryOutcome> query = carRepository.findByModelAndMaxSpeedQuery(modelMaxSpeed);
        query.forEach(item -> {
            try {
                cars.add(convertToCar(item.toJSONPretty()));
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Can't convert to Car");
            }
        });
        return cars;
    }
}
