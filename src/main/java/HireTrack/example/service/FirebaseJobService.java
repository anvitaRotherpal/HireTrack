package HireTrack.example.service;

import HireTrack.example.model.Job;
import HireTrack.example.model.Store;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseJobService {

    public List<Store> getAllStores() throws ExecutionException, InterruptedException {
        List<Store> stores = new ArrayList<>();

        CollectionReference jobsCollection = FirestoreClient.getFirestore().collection("jobs");
        ApiFuture<QuerySnapshot> future = jobsCollection.get();

        int dummyDistance = 1; // start with 1, increment for each job
        for (DocumentSnapshot doc : future.get().getDocuments()) {
            Job job = doc.toObject(Job.class);
            if (job != null) {
                  System.out.println("Fetched job: " + job.getTitle());
                stores.add(new Store(job.getTitle(), dummyDistance));
                dummyDistance++;
            }
        }

        return stores;
    }


public List<Job> getAllJobs() throws ExecutionException, InterruptedException {
    List<Job> jobs = new ArrayList<>();

    CollectionReference jobsCollection = FirestoreClient.getFirestore().collection("jobs");
    ApiFuture<QuerySnapshot> future = jobsCollection.get();

    for (DocumentSnapshot doc : future.get().getDocuments()) {
        Job job = doc.toObject(Job.class);
        if (job != null) {
            job.setId(doc.getId()); // capture Firestore ID
            jobs.add(job);
        }
    }

    return jobs;
}
}