package com.dev.crypto.controller;

import com.dev.crypto.entity.Coin;
import com.dev.crypto.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    private CoinRepository repository;

    @Bean
    public Coin init(){
        Coin coin = new Coin();
        coin.setName("BITCOIN");
        coin.setPrice(new BigDecimal(100));
        coin.setQuantity(new BigDecimal(0.0000100));
        coin.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin coin2 = new Coin();
        coin2.setName("BITCOIN");
        coin2.setPrice(new BigDecimal(200));
        coin2.setQuantity(new BigDecimal(0.0000200));
        coin2.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin coin3 = new Coin();
        coin3.setName("ETHEREUM");
        coin3.setPrice(new BigDecimal(5000));
        coin3.setQuantity(new BigDecimal(0.0005000));
        coin3.setDateTime(new Timestamp(System.currentTimeMillis()));

        repository.insert(coin);
        repository.insert(coin2);
        repository.insert(coin3);

        return coin;
    }

    @GetMapping()
    public ResponseEntity getCoins(){
        return new ResponseEntity<>(repository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity getCoins(@PathVariable String name){
        try {
            return  new ResponseEntity<>(repository.getByName(name), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping()
    public ResponseEntity post(@RequestBody Coin coin ){
        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(repository.insert(coin), HttpStatus.CREATED);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@RequestBody Coin coin){
        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(repository.update(coin), HttpStatus.OK);
        }catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
/*
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id ){
        Boolean response = false;
        try{
            return  new ResponseEntity<>(repository.remove(id), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.NO_CONTENT);
        }
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id ){
        Boolean response = false;
        try{
            response = repository.remove(id);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
    }
}
