package HireTrack.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import HireTrack.example.service.FirebaseJobService;
import HireTrack.example.model.Job;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private FirebaseJobService jobService;

    // Get all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Add a new job
    @PostMapping
    public void addJob(@RequestBody Job job) {
        jobService.addJob(job);
    }
}
