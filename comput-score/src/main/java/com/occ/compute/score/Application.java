package com.occ.compute.score;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.occ.compute.score.exception.ComputeScoreException;
import com.occ.compute.score.model.Employee;
import com.occ.compute.score.service.ComputeScoreService;
import com.occ.compute.score.util.ComputeScoreConstants;

/**
 * Main Application class to compute score
 * 
 * @author thakur
 *
 */
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		try {
			BasicConfigurator.configure();
			logger.info("Score Computation for file {} has started", args[0]);
			long begin = System.currentTimeMillis();
			String filePath = args[0];
			ComputeScoreService computeScoreService = new ComputeScoreService();
			List<Employee> nameList = computeScoreService.openFileAndFetchNames(filePath);
			logger.info("Sum of file {} is {}", filePath, computeScoreService.orderAndCalcTotal(nameList));
			long end = System.currentTimeMillis();
			logger.info("Total execution time: {} ms", end - begin);
		} catch (ComputeScoreException ex) {
			logger.error("Exception occured: {}", ex.fillInStackTrace());
			System.exit(ComputeScoreConstants.EXIT_ERROR_CODE);
		}
	}

}
