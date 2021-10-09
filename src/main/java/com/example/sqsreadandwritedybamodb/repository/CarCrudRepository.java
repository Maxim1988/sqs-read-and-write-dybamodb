package com.example.sqsreadandwritedybamodb.repository;

import com.example.sqsreadandwritedybamodb.entity.Car;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CarCrudRepository extends CrudRepository<Car, String> {

    List<Car> findByModelAndMaxSpeed(String model, Integer maxSpeed);
}
