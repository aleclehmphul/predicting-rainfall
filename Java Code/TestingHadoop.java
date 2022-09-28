package com.DataScienceProject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestingHadoop {

	private static double[] weights;

	public static void main(String[] args) {

		readHadoopFile(
				"C:\\Users\\alecl\\OneDrive\\Documents\\Spring 2021 Semester\\Data Science\\Logistic Regression Testing\\OUTPUT\\part-r-00000");
		List<Instance> testInstances = readDataSet(
				"C:\\Users\\alecl\\OneDrive\\Documents\\Spring 2021 Semester\\Data Science\\Project\\R\\DataScienceProject\\weather_testingSet.csv");

		// Testing model
		int correct = 0, wrong = 0;
		int trueP = 0, trueN = 0, falseP = 0, falseN = 0;

		for (int i = 0; i < testInstances.size(); i++) {
			int realOutcome = testInstances.get(i).outcome;
			double prediction = classify(testInstances.get(i).data);
			boolean isRain = false;
			if (prediction >= 0.5) {
				isRain = true;
			}
			System.out.println(Arrays.toString(testInstances.get(i).data) + "\n" + realOutcome);

			if (isRain && realOutcome == 1) {
				System.out.println("PREDICTED CORRECTLY");
				System.out.println("The prediction = " + prediction);
				correct++;
				trueP++;
			} else if (isRain && realOutcome == 0) {
				System.out.println("PREDICTED WRONG");
				System.out.println("The prediction = " + prediction);
				wrong++;
				falseN++;
			} else if (!isRain && realOutcome == 1) {
				System.out.println("PREDICTED WRONG");
				System.out.println("The prediction = " + prediction);
				wrong++;
				falseP++;
			} else if (!isRain && realOutcome == 0) {
				System.out.println("PREDICTED CORRECT");
				System.out.println("The prediction = " + prediction);
				correct++;
				trueN++;
			} else
				System.out.println("SHOULD NEVER BE PRINTED");
			System.out.println();
		}

		// calculates percent correct
		double percent = (correct / (double)(correct + wrong)) * 100.0;
		System.out.print("The model is ");
		System.out.printf("%.2f", percent);
		System.out
				.print("% accurate.  It successfully predicted " + correct + " and falsely predicted " + wrong + ".\n");
		System.out.println("\nTrue Positive: " + trueP + "\nTrue Negative: " + trueN + "\nFalse Positive: " + falseP
				+ "\nFalse Negative: " + falseN);
	}

	private static void readHadoopFile(String filename) {
		weights = new double[13];

		try {
			File myFile = new File(filename);
			Scanner inputReader = new Scanner(myFile);

			String[] currentLine;
			String current;

			while (inputReader.hasNextLine()) {
				current = inputReader.nextLine();
				currentLine = current.split(",");

				current = removeTheta(currentLine[0]);
				weights[Integer.parseInt(current)] = Double.parseDouble(currentLine[1]);
			}

			inputReader.close();
		} catch (Exception e) {
			System.out.println("Cannot read file");
		}
	}

	public static List<Instance> readDataSet(String filename) {
		List<Instance> dataset = new ArrayList<Instance>();

		try {
			File myFile = new File(filename);
			Scanner inputReader = new Scanner(myFile);
			String[] columns;
			double[] data;

			String row;
			int outcome, i;
			boolean first = true;

			while (inputReader.hasNextLine()) {

				// splits each row of data into individual columns
				row = inputReader.nextLine();
				columns = row.split(",");

				// assigns data to new array
				data = new double[columns.length - 2];
				double[] temp = new double[columns.length - 2];

				// first is used to ignore first row of data
				if (!first) {
					for (i = 1; i <= columns.length - 2; i++) {
						if (BasicTest.isNumeric(columns[i]))
							temp[i - 1] = Double.parseDouble(columns[i]);
						else {
							String s = columns[i];

							// Check for wind direction (make into dummy variable)
							// Removed because it made things complicated

							// Check for if rained yesterday
							if (s.equals("\"Yes\"")) {
								temp[i - 1] = 1.0; // value designated to yes
							} else if (s.equals("\"No\"")) {
								temp[i - 1] = 0.0; // value designated to no
							}
						}
					}
				} else
					first = false;

				for (int j = 0; j < data.length; j++)
					data[j] = temp[j];

				if (columns[14].equals("\"No\"")) {
					outcome = 0;
					dataset.add(new Instance(outcome, data));
				} else if (columns[14].equals("\"Yes\"")) {
					outcome = 1;
					dataset.add(new Instance(outcome, data));
				}
			}

			inputReader.close();
		} catch (Exception e) {
			System.out.println("Problem in readDataSet method");
		}
		System.out.println("LEAVING read");
		return dataset;
	}

	// Sigmoid function for logistic regression
	private static double sigmoid(double z) {
		double sigmoid = 1.0 / (1.0 + Math.exp(-1 * z));
		return sigmoid;
	}

	// Uses logit and the sigmoid function to classify
	private static double classify(double[] data) {
		double logit = 0.0;
		for (int i = 0; i < weights.length; i++)
			logit = logit + (weights[i] * data[i]);

		return sigmoid(logit);
	}

	public static String removeTheta(String s) {
		return s.substring(5);
	}

}
