package com.example.sqsreadandwritedybamodb.controller;

import com.amazonaws.services.sqs.model.Message;
import com.example.sqsreadandwritedybamodb.entity.Car;
import com.example.sqsreadandwritedybamodb.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sqs")
@Slf4j
public class AmazonSQSController {

    @Autowired
    private CarService carService;

    @GetMapping("/read")
    public void sendMessage() {
        List<Message> massages = carService.getAllMessage();
        for(Message m : massages) {
            Car car;
            try {
                car = carService.convertToCar(m.getBody());
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Can't convert to Car");
            }
            carService.saveCar(car);
            carService.deleteMessage(m);

        }
    }

    @PostMapping("/save/car")
    public Car save(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping("/find/{carId}")
    public Car findById(@PathVariable(value = "carId") String carId) {
        return carService.findById(carId);
    }

    @PutMapping("/update/{carId}")
    public String update(@PathVariable(value = "carId") String carId,
                  @RequestBody Car car) {
        return carService.update(carId, car);
    }

    @DeleteMapping("/delete/{carId}")
    public Car delete(@PathVariable(value = "carId") String carId) {
        return carService.delete(carId);
    }

    @GetMapping("/findByModelAndMaxSpeedQuery/{modelMaxSpeed}")
    public List<Car> findByModelAndMaxSpeedQuery(@PathVariable(value = "modelMaxSpeed") String modelMaxSpeed) {
        List<Car> lc = carService.findByModelAndMaxSpeedQuery(modelMaxSpeed);
        return lc;
    }

    @GetMapping("/findAll")
    public List<Car> findAll() {
        List<Car> lc = carService.findAll();
        return lc;
    }
}
