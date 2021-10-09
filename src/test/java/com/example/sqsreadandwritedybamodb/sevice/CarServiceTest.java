package com.example.sqsreadandwritedybamodb.sevice;

import com.example.sqsreadandwritedybamodb.entity.Car;
import com.example.sqsreadandwritedybamodb.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Test
    public void convertToCarTest() throws JsonProcessingException {
        String convert = "{\"carId\":\"1\", \"modelMaxSpeed\":\"ferrari-320\", \"model\":\"ferrari\", " +
                "\"color\":\"red\", \"maxSpeed\":320}";
        Car carExpected = new Car("1", "ferrari-320", "ferrari", "red", 320);
        Car carActual = carService.convertToCar(convert);
        assertThat(carActual).isEqualTo(carExpected);
    }
}
