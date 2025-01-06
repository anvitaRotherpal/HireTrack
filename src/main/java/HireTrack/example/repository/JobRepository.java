package HireTrack.example.repository;

import com.google.firebase.cloud.FirestoreClient;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import HireTrack.example.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Repository
public class JobRepository {

    private final Firestore firestore;

    public JobRepository(){
        this.firestore=FirestoreClient.getFirestore();
    }

    public CollectionReference getJobsCollection(){
        return firestore.collection("jobs");
    }

    public String addJob(Job job) throws ExecutionException, InterruptedException{
        DocumentReference documentReference = getJobsCollection().document();
        job.setId(documentReference.getId());
        documentReference.set(job).get();
        return documentReference.getId();
    }

    public List<Job> getAllJobs() throws ExecutionException, InterruptedException{
         List<Job> jobs=new ArrayList<>();
         ApiFuture<QuerySnapshot> querySnapshot= getJobsCollection().get();

         for(DocumentSnapshot document:querySnapshot.get().getDocuments()){
            Job job=document.toObject(Job.class);
            if(job!=null){
                jobs.add(job);
            }
         }

         return jobs;
    }

    public Job getJobId(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getJobsCollection().document(id).get().get();
        return documentSnapshot.toObject(Job.class);
    }

    public String updateJob(String id, Job job) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = getJobsCollection().document(id);
        job.setId(id); // Ensure the job's ID is set
        documentReference.set(job).get(); // Update the job in Firestore
        return id;
    }

    // Delete a job by ID
    public String deleteJob(String id) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = getJobsCollection().document(id);
        documentReference.delete().get(); // Delete the job
        return id;
    }
}
