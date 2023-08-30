package com.fastwon.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitiallizer {

	@Value("${app.firebase-configuration-file}")
	private String firebaseCongigPath;
	
	@PostConstruct
	public void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.
					fromStream(new ClassPathResource(firebaseCongigPath).getInputStream())).build();
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
