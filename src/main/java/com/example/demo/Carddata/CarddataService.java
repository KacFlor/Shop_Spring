package com.example.demo.Carddata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarddataService {

    @Autowired
    public CarddataService() {

    }

    public List<Carddata> getCarddata() {return List;}
}
