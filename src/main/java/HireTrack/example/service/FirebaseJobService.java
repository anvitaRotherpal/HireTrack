package HireTrack.example.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import HireTrack.example.model.Job;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseJobService {

    private final Firestore db = FirestoreClient.getFirestore();

    // Add a new job to Firestore
    public void addJob(Job job) {
        try {
            db.collection("jobs").add(job).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Get all jobs from Firestore
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("jobs").get();

        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Job job = document.toObject(Job.class);
                if (job != null) {
                    jobs.add(job);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return jobs;
    }
}