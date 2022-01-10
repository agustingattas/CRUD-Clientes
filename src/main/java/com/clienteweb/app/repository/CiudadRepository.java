/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteweb.app.repository;

import com.clienteweb.app.entity.Ciudad;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author agust
 */
public interface CiudadRepository extends CrudRepository<Ciudad, Long>{
    
}
