package com.DataScienceProject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//My Mapper class                                <key-in, value in, key-out, value-out>
public class LogisticRegressionMap extends Mapper<Writable, Text, Text, DoubleWritable> {

	private static double[] data;

	private static double learningRate = 0.01;

	private static int numOfInputs = 13, count = 0;

	private static ArrayList<Double> weights;
	

	public void setup(Context context) throws IOException, InterruptedException {
		try {
			weights = new ArrayList<Double>();
			for (int i = 0; i < numOfInputs; i++)
				weights.add(0.0);
		} 
		catch (Exception e) {
			System.out.println("Problem in Map setup");
		}
	}
	
	public void map(Writable key, Text value, Context context) throws IOException, InterruptedException {
		// Processing input data
		count++;
		double predicted = 0.0;
		String[] tok = value.toString().split("\\,");
		
		if (count != 1) {
			for (int i = 0; i < tok.length; i++)
				tok[i] = removeQuotes(tok[i]);
			
			if (count == 2) {
				data = new double[numOfInputs];
				//weights = new double[tok.length - 2];
			}

			for (int i = 0; i < data.length; i++) {
				if (isNumeric(tok[i + 1]))
					data[i] = Double.parseDouble(tok[i + 1]);
				else {
					if (tok[i + 1].equals("Yes"))
						data[i] = 1.0;
					else if (tok[i + 1].equals("No"))
						data[i] = 0.0;
					else
						System.out.println("Data Type Not Applicable\n" + tok[i+1]);
				}
			}

			double logit = 0;
			for (int i = 0; i < data.length; i++) {
				logit += (data[i] * weights.get(i));

				if (i == (data.length - 1))
					predicted = (1 / (1 + (Math.exp(-(logit)))));
			}

			
			// Calculating weights (theta values) for model
			double actualOutcome = 0.0;
			if (tok[tok.length - 1].equals("Yes"))
				actualOutcome = 1.0;
				
			for (int i = 0; i < data.length; i++) {
				double temp = weights.get(i);
				weights.remove(i);
				weights.add(i, (temp + (learningRate) * (actualOutcome - predicted) * data[i]));
			}
		}
	}

	// Creates the context for reduce job in terms of <key, value>
	public void cleanup(Context context) throws IOException, InterruptedException {
		try {
			for (int i = 0; i < weights.size(); i++) {
				context.write(new Text("theta" + i + ","), new DoubleWritable(weights.get(i)));
			}
		} catch (Exception e) {
			System.out.println("ISSUE IN CLEANUP");
		}
	}

	// ========================================================================================

	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static String removeQuotes(String input) {
	    String result = "";
	    for (int i = 0; i < input.length(); i++) {
	        if(input.charAt(i) != '\"')
	        	result+= input.charAt(i);
	    }
	    return result;
	}

}