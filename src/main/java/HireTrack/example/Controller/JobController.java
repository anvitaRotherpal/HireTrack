package HireTrack.example.Controller;

import HireTrack.example.model.Job;
import HireTrack.example.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // GET all jobs
    @GetMapping
    public List<Job> getAllJobs() throws ExecutionException, InterruptedException {
        return jobRepository.getAllJobs();
    }

    // POST a new job
    @PostMapping
    public String addJob(@RequestBody Job job) throws ExecutionException, InterruptedException {
        return jobRepository.addJob(job);
    }
}