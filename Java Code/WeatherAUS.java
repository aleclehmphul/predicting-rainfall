package com.DataScienceProject;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// Driver class
public class WeatherAUS {

	public static void main(String[] args) {
		// enables console logging of Hadoop’s run
		org.apache.log4j.BasicConfigurator.configure();

		try {
			// Configurations for MapReduce Job for train the model
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf, "Logistic Regression");
			job.setJarByClass(WeatherAUS.class);
			job.setMapperClass(LogisticRegressionMap.class);
			job.setCombinerClass(LogisticRegressionReduce.class);
			job.setReducerClass(LogisticRegressionReduce.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(DoubleWritable.class);
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		} catch (Exception e) {
			System.out.println("Problem in main with configuration");
		}
		
	}
	
}