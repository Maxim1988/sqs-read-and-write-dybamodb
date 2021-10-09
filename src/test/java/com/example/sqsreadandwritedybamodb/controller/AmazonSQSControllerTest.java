package com.example.sqsreadandwritedybamodb.controller;

import com.amazonaws.services.sqs.model.Message;
import com.example.sqsreadandwritedybamodb.entity.Car;
import com.example.sqsreadandwritedybamodb.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AmazonSQSController.class)
public class AmazonSQSControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    private static Car car;

    @BeforeAll
    public static void init() {
        car = new Car("1", "ferrari-320", "ferrari", "red", 320);
    }

    @Test
    public void sendMessageTest() throws Exception {
        when(carService.getAllMessage()).thenReturn(List.of(new Message()));
        when(carService.convertToCar(any(String.class))).thenReturn(car);
        when(carService.saveCar(car)).thenReturn(car);
        when(carService.deleteMessage(any(Message.class))).thenReturn(null);
        mockMvc.perform(get("/sqs/read")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void saveTest() throws Exception {
        when(carService.saveCar(car)).thenReturn(car);
        mockMvc.perform(post("/sqs/save/car")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdTest() throws Exception {
        when(carService.findById(car.getCarId())).thenReturn(car);
        mockMvc.perform(get("/sqs/find/{carId}", car.getCarId())
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        when(carService.update(car.getCarId(), car)).thenReturn(car.getCarId());
        mockMvc.perform(put("/sqs/update/{carId}", car.getCarId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        when(carService.delete(car.getCarId())).thenReturn(car);
        mockMvc.perform(delete("/sqs/delete/{carId}", car.getCarId())
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByModelAndMaxSpeedQueryTest() throws Exception {
        when(carService.findByModelAndMaxSpeedQuery(car.getModelMaxSpeed())).thenReturn(List.of(car));
        mockMvc.perform(get("/sqs/findByModelAndMaxSpeedQuery/{modelMaxSpeed}", car.getMaxSpeed())
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTest() throws Exception{
        when(carService.findAll()).thenReturn(List.of(car));
        mockMvc.perform(get("/sqs/findAll")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
