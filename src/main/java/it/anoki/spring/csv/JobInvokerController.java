package it.anoki.spring.csv;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import it.anoki.spring.model.User;
import it.anoki.spring.service.UserService;

@RestController
public class JobInvokerController {
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	@Autowired
	UserService userService;

	@GetMapping("/import")
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

	@GetMapping("/export")
	public void exportCSV(HttpServletResponse response) throws Exception {
		// set file name and content type
		String filename = "users.csv";
		List<UserCSV> users=new ArrayList<UserCSV>();

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

		// create a csv writer
		StatefulBeanToCsv<UserCSV> writer = new StatefulBeanToCsvBuilder<UserCSV>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(false).build();

		// write all users to csv file
		for(User user: userService.getAll()) {
			users.add(UserCSV.build(user));
		}
		writer.write(users);

	}

}