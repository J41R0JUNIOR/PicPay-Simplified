package com.example.picpay.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AuthService {

	public boolean isAuthorized() throws IOException {
		URL url = new URL("https://util.devi.tools/api/v2/authorize");
		HttpURLConnection response = (HttpURLConnection) url.openConnection();
		response.setRequestMethod("GET");
		System.out.println(response.getResponseCode());
		return response.getResponseCode() == 200;
	}
}
