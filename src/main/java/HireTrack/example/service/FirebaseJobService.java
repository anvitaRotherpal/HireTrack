package HireTrack.example.service;

import org.springframework.stereotype.Service;

@Service
public class FirebaseJobService {

    private final FirebaseFirestore db= FirebaseFirestore.getInstance();

    public void saveJobPosting(JobPosting jobPosting){
        db.collection("jobPostings").add(jobPosting);

    }

    public List<JobPosting> getAllJobPostings(){
        List<JobPosting> jobPosting = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("jobPostings").get();
        try{
            for(DocumentSnapshot document : querySnapshot.get().getDocuments()){

                jobPostings.add(document.toObject(JobPosting.class));
            }
        }

            catch(InterruptedException | ExecutionException e){
                e.printStackTrace();
            }

            return jobPostings;
        }
    }
    



