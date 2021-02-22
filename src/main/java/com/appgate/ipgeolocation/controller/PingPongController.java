package com.appgate.ipgeolocation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ipgeolocalization")
public class PingPongController {

	@GetMapping(value = "/ping")
	public String ping() {
		return "pong";
	}
}