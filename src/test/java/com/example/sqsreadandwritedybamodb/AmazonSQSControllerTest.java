package com.example.sqsreadandwritedybamodb;

import com.amazonaws.services.sqs.model.Message;
import com.example.sqsreadandwritedybamodb.controller.AmazonSQSController;
import com.example.sqsreadandwritedybamodb.entity.Car;
import com.example.sqsreadandwritedybamodb.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AmazonSQSController.class)
public class AmazonSQSControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void sendMessageTest() throws Exception {
        Car car = new Car("1", "ferrari-320", "ferrari", "red", 320);
        when(carService.getAllMessage()).thenReturn(List.of(new Message()));
        when(carService.convertToCar(any(String.class))).thenReturn(car);
        when(carService.saveCar(car)).thenReturn(car);
        when(carService.deleteMessage(any(Message.class))).thenReturn(null);
        mockMvc.perform(get("/sqs/read")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
