package com.adil.springcache.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class CacheController {

    @Autowired
    CacheManager cacheManager;

    @Resource
    CacheController cacheController;
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @GetMapping("/hello")
    public String greet(@RequestParam("name") String name) throws InterruptedException {
        return cacheController.getName(name);

    }

    @GetMapping("/update")
    public String greetUpdate(@RequestParam("name") String name) throws InterruptedException {
        return cacheController.updateName(name);

    }

    @GetMapping("/cache")
    public String getAllCacheEntrie() {
        logger.info("Cache values : {} ", cacheManager.getCache("name-cache").getNativeCache());
        return "Check logs";
    }

    @Cacheable(sync = true, value = "name-cache", key = "'Name'+#name")
    public String getName(String name) throws InterruptedException {
        logger.info("Inside the getName method");
        Thread.sleep(2000);
        return name;
    }

    @CachePut(value = "name-cache", key = "'Name'+#name")
    public String updateName(String name) throws InterruptedException {
        logger.info("Inside the updateName method");
        Thread.sleep(2000);
        return "dead";
    }

}
