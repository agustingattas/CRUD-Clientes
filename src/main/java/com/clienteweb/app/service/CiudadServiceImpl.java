package com.clienteweb.app.service;

import com.clienteweb.app.entity.Ciudad;
import com.clienteweb.app.repository.CiudadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadServiceImpl implements ICiudadService{

    @Autowired
    private CiudadRepository ciudadRepository;
    
    @Override
    public List<Ciudad> listaCiudades() {
        
        return (List<Ciudad>) ciudadRepository.findAll();
    }
    
}
