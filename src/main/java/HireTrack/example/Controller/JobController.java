package HireTrack.example.Controller;

import HireTrack.example.algorithm.RouteOptimizer;
import HireTrack.example.model.Job;
import HireTrack.example.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import HireTrack.example.model.Store;
import HireTrack.example.service.FirebaseJobService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

     private final FirebaseJobService jobService;

    public JobController(FirebaseJobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    private JobRepository jobRepository;

    @Autowired
private FirebaseJobService firebaseJobService;


    // GET all jobs
    @GetMapping
    public List<Job> getAllJobs() throws ExecutionException, InterruptedException {
        return jobRepository.getAllJobs();
    }

    @GetMapping("/route")
public Map<String, Integer> getOptimalRoute() throws Exception {

    RouteOptimizer optimizer = new RouteOptimizer();
    List<Store> stores = firebaseJobService.getAllStores();

    Store home = new Store("Home", 0);

    // Connect home to first store
    if (!stores.isEmpty()) {
        optimizer.addRoute(home, stores.get(0));
    }

    // Connect all stores
    for (int i = 0; i < stores.size(); i++) {
        for (int j = i + 1; j < stores.size(); j++) {
            optimizer.addRoute(stores.get(i), stores.get(j));
        }
    }

    return optimizer.shortestPath("Home");
}

    // POST a new job
    @PostMapping
    public String addJob(@RequestBody Job job) throws ExecutionException, InterruptedException {
        return jobRepository.addJob(job);
    }
}