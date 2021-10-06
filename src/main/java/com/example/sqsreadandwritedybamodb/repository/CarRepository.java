package com.example.sqsreadandwritedybamodb.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.sqsreadandwritedybamodb.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;
    
    public Car save(Car car) {
        dynamoDBMapper.save(car);
        return car;
    }

    public Car findById(String carId) {
        return dynamoDBMapper.load(Car.class, carId);
    }

    public Car delete(String carId) {
        Car car = dynamoDBMapper.load(Car.class, carId);
        dynamoDBMapper.delete(car);
        return car;
    }

    public String update(String carId, Car car) {
        dynamoDBMapper.save(car,
                new DynamoDBSaveExpression()
                        .withExpectedEntry(carId,
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(carId)
                                )));
        return carId;
    }

    public List<Car> findAll() {
        return dynamoDBMapper.scan(Car.class, new DynamoDBScanExpression());
    }

    public ItemCollection<QueryOutcome> findByModelAndMaxSpeedQuery(String modelMaxSpeed) {
       DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
       Table table = dynamoDB.getTable("car");

       Index modelMaxSpeedIndex = table.getIndex("modelMaxSpeedIndex");
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("modelMaxSpeed = :modelMaxSpeed")
                .withValueMap(new ValueMap()
                        .withString(":modelMaxSpeed", modelMaxSpeed));
        return modelMaxSpeedIndex.query(querySpec);
    }
}
