package HireTrack.example.service;

import javax.annotation.PostConstruct;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class JobService {
    @PostConstruct
    public void init(){
        try{
            FileInputStream serviceAccount=new FileInputStream("D:/HireTrack/src/main/resources/firebase-service-account.json");
            FirebaseOptions options=FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
            FirebaseApp.initializeApp(options);
        }catch(IOException e){
            e.printStackTrace();
        }
        }
    }

