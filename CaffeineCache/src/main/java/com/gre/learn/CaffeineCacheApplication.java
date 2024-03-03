package com.gre.learn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.benmanes.caffeine.cache.Cache;
import com.gre.learn.service.CustomerService;

@RestController
@EnableCaching
@SpringBootApplication
public class CaffeineCacheApplication implements CommandLineRunner {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CacheManager cacheManager;

	public static void main(String[] args) {
		SpringApplication.run(CaffeineCacheApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Cache Test Started!!!");
		
		customerService.getCustomer(1l);
		customerService.getCustomer(2l);
		customerService.getCustomer(3l);
		customerService.getCustomer(4l);
		customerService.getCustomer(5l);
		
		customerService.getCustomer(1l);
		customerService.getCustomer(2l);
		customerService.getCustomer(3l);
		customerService.getCustomer(4l);
		customerService.getCustomer(5l);
		
	}
	
	@GetMapping(value = "/inspectCache/{cacheName}")
	public String inspectCache(@PathVariable("cacheName") String cacheName) {
		List<String> cacheList = new ArrayList<>();
		CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);
		Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

		for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
			
			String stmt = "Key = " + entry.getKey() + "==>" + "Value = " + entry.getValue();
			cacheList.add(stmt);
			
		}
		
		return String.join(",", cacheList);
	}
	
	@GetMapping(value = "/inspectCache")
	public String inspectCache() {
		List<String> cacheList = new ArrayList<>();
		Collection<String> cacheNames = cacheManager.getCacheNames();
		for (String cacheName : cacheNames) {
			CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);
			Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

			for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
				
				String stmt = "Key = " + entry.getKey() + "==>" + "Value = " + entry.getValue();
				cacheList.add(stmt);
				
			}
		}
		
		return String.join(",", cacheList);
	}

}
