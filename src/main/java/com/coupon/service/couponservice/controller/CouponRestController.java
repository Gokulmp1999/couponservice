package com.coupon.service.couponservice.controller;

import com.coupon.service.couponservice.model.Coupon;
import com.coupon.service.couponservice.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {

    @Autowired
    private CouponRepository couponRepository;

    @RequestMapping(value = "createcoupon",method = RequestMethod.POST)
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon){

        return new ResponseEntity<>(couponRepository.save(coupon), HttpStatus.CREATED);
    }

    @RequestMapping(value = "coupon/{code}",method = RequestMethod.GET)
    public ResponseEntity<Coupon> getCoupon(@PathVariable String code){
        return new ResponseEntity<>(couponRepository.findByCode(code),HttpStatus.OK);
    }
}
