package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.payload.response.data.TestData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/test", produces="application/json")
public class TestController {
    @GetMapping("/data")
    public TestData get() {
        TestData testData = new TestData();
        testData.setId("1");
        testData.setName("test");
        testData.setData("New data");
        return testData;
    }
}
