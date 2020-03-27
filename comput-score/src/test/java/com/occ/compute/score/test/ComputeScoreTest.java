package com.occ.compute.score.test;

import java.math.BigInteger;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.occ.compute.score.model.Employee;
import com.occ.compute.score.service.ComputeScoreService;

/**
 * Unit test for compute score Application.
 */
public class ComputeScoreTest {
	
	private ComputeScoreService computeScoreService;
	private String filePath;
	
    @Before
    public void setup(){
    	computeScoreService = new ComputeScoreService();
    	filePath = "src/main/resources/OCC Take Home Coding names short.txt";
    }

	@Test
	public void givenFilePath_whenOpenFileAndFetchNames_thenGetEmployeeList() {
		List<Employee> empList = computeScoreService.openFileAndFetchNames(filePath);
		Assert.assertNotNull(empList);
		Assert.assertEquals(9, empList.size());
	}

	@Test
	public void givenEmployeeList_whenOrderAndCalcTotal_thenGetTotalScore() {
		List<Employee> empList = computeScoreService.openFileAndFetchNames(filePath);
		BigInteger actualScore = computeScoreService.orderAndCalcTotal(empList);
		Assert.assertNotNull(actualScore);
		Assert.assertEquals(new BigInteger("3194"), actualScore);
	}

    @Test
    public void givenEmployeeNameAndIndex_whenCalculateIndivdualScore_thenGetScore(){
        BigInteger score = computeScoreService.calcIndividualScore(new Employee("LINDA"), 4);
        Assert.assertNotNull(score);
		Assert.assertEquals(new BigInteger("160"), score);
    }
}
