package com.occ.compute.score.service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.occ.compute.score.exception.ComputeScoreException;
import com.occ.compute.score.model.Employee;
import com.occ.compute.score.util.ComputeScoreConstants;

/**
 * Service class
 * 
 * @author thakur
 *
 */
public class ComputeScoreService {

	private static Logger logger = LoggerFactory.getLogger(ComputeScoreService.class);

	/**
	 * Method to open file and transform list
	 * 
	 * @param filePath
	 * @return
	 * @throws ComputeScoreException
	 */
	public List<Employee> openFileAndFetchNames(String filePath) throws ComputeScoreException {
		logger.info("openFile -> File name: {}", filePath);
		List<String> dataList = new ArrayList<>();
		List<Employee> nameList = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			stream.forEach(a -> dataList.addAll(Arrays.asList(a.split(ComputeScoreConstants.COMMA))));
			dataList.forEach(name -> nameList.add(
					new Employee(StringUtils.replace(name, ComputeScoreConstants.DOUBLE_QUOTE, StringUtils.EMPTY))));
		} catch (IOException exception) {
			logger.error("Error occured while opening and processing file: {}", exception.getMessage());
			throw new ComputeScoreException("File reading error", exception);
		}
		return nameList;
	}

	/**
	 * Method to order and calculate grand total
	 * 
	 * @param nameList
	 * @return
	 * @throws ComputeScoreException
	 */
	public BigInteger orderAndCalcTotal(List<Employee> nameList) throws ComputeScoreException {
		nameList.sort(Comparator.comparing(Employee::getFirstName));
		BigInteger grandTotal = new BigInteger(ComputeScoreConstants.ZERO);
		Integer index = ComputeScoreConstants.START_INDEX;
		for (Employee name : nameList) {
			grandTotal = grandTotal.add(calcIndividualScore(name, index++));
		}
		return grandTotal;
	}

	/**
	 * Method to calculate score for each name
	 * 
	 * @param name
	 * @param order
	 * @return
	 * @throws ComputeScoreException
	 */
	public BigInteger calcIndividualScore(Employee name, Integer order) throws ComputeScoreException {
		BigInteger score = new BigInteger(ComputeScoreConstants.ZERO);
		for (char nameChar : name.getFirstName().toCharArray()) {
			score = score.add(BigInteger.valueOf(nameChar - ComputeScoreConstants.CAPITAL_LETTER_ASCII));
		}
		return score.multiply(BigInteger.valueOf(order));
	}

}
