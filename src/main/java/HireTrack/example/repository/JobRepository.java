package HireTrack.example.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import HireTrack.example.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class JobRepository {

    private final Firestore firestore;

    public JobRepository() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public CollectionReference getJobsCollection() {
        return firestore.collection("jobs");
    }

    public String addJob(Job job) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getJobsCollection().document();
        job.setId(docRef.getId());
        docRef.set(job).get();
        return docRef.getId();
    }

    public List<Job> getAllJobs() throws ExecutionException, InterruptedException {
        List<Job> jobs = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = getJobsCollection().get();

        for (DocumentSnapshot doc : future.get().getDocuments()) {
            Job job = doc.toObject(Job.class);
            if (job != null) jobs.add(job);
        }

        return jobs;
    }
}