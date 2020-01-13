	package com.lnt.framework;
	
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	
	public class Test {
	
		private static BufferedReader lineReader;

		public static void main(String[] args) throws IOException
		{
			String csvFilePath = "C:/Users/10661776/Downloads/newcsv.txt";
			lineReader = new BufferedReader(new FileReader(csvFilePath));
			String lineText = null;
	
			lineReader.readLine();
	
			while ((lineText = lineReader.readLine()) != null) {
				String data1=lineText.replaceAll("\"", "");
			
				String[] data = data1.split(",");
				for(int j=0;j<data.length;j++)
				{
				System.out.println(data[j]);
				}
		
				
	
			}
		}
	
		}
