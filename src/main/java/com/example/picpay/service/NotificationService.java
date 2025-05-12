package com.example.picpay.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NotificationService {

	public void sendNotification() throws IOException {
		URL url = new URL("https://util.devi.tools/api/v1/notify");
		HttpURLConnection response = (HttpURLConnection) url.openConnection();
		response.setRequestMethod("POST");
		System.out.println(response.getResponseCode());
		response.getResponseCode();
	}
}
