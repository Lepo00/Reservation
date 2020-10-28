package it.anoki.spring.csv;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobInvokerController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@RequestMapping("/invokejob")
	public String handle() throws Exception {
	    try {
	      JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
	          .toJobParameters();
	      jobLauncher.run(job, jobParameters);
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return "Done! Check Console Window for more details";
	  }

}