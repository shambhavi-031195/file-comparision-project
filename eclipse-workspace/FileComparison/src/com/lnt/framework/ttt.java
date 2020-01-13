	package com.lnt.framework;
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.sql.CallableStatement;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.StringTokenizer;
	
	public class ttt {
	
		public static void main(String[] args) {
	
			String jdbcURL = "jdbc:mysql://localhost:3306/mysql";
			String username = "root";
			String password = "Hb1994#*";
			
			Load_data_src obj2 = new Load_data_src();
			obj2.load();
	
			LayersM obj1 = new LayersM();
			obj1.getPropValues();
			String csvFilePathtar =  obj1.targetpath;
			String dels = obj1.tardel;
			String sodel=obj1.sourcedel;
			String arrdel = obj1.arrayDel;
			String ttn= obj1.target_table_name;
			String sp = obj1.sp;
			
	
			Connection connection = null;
			Statement stmt = null;
			int batchSize = 20;
			char c = '\0';
			char d;
			int count = 0;
			int count2=0;
			String extra= "Description";
			ArrayList<String> ar = new ArrayList<String>();
			ArrayList<String> arr = new ArrayList<String>();
	
			try {
				connection = DriverManager.getConnection(jdbcURL, username, password);
				connection.setAutoCommit(false);
	
	
				
	
				for (int i = 0; i < ar.size(); i++)
					System.out.print(ar.get(i) + " ");
	
				
	
				// counting the no of extra columns needed
	
				BufferedReader lineReader1 = new BufferedReader(new FileReader(csvFilePathtar));
	
				lineReader1.readLine();// skip 1st line
	
				String text1 = lineReader1.readLine();
				
				String match = arrdel;
				int count1 = 0;
	
				for (int i = 0; i < match.length(); i++) {
					c = match.charAt(i);
				}
	
				for (int i = 0; i < text1.length(); i++) {
					d = text1.charAt(i);
					int diff = c - d;
					if (diff == 0)
						count1++;
				}
	
				if (count1 == 0) {
					
					// arraylist
					
					BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePathtar));
					String text = lineReader.readLine();
	
					StringTokenizer st = new StringTokenizer(text, dels);
	
					while (st.hasMoreTokens()) {
	
						ar.add(st.nextToken());
	
					}
	
					// Creating table dynamically
	
					int a = 0;
					stmt = connection.createStatement();
					while (a < ar.size()) {
						String sql = "CREATE TABLE IF NOT EXISTS"+" "+ttn+"(";
						while (a < ar.size()) {
							sql = sql + (ar.get(a)) + " " + "varchar(255)" + ",";
							a++;
	
						}
						sql = sql.substring(0, sql.length() - 1);
						sql = sql +")"+ ";";
	
						 System.out.println(sql);
	
						stmt.executeUpdate(sql);
					}
	
					// Inserting data command creation
	
					String sqltar = "INSERT INTO"+" "+ttn+"(";
					int index = 0;
					while (index < ar.size()) {
						sqltar = sqltar + (ar.get(index)) + ",";
						index++;
					}
	
					// removing extra ',' from column name
	
					sqltar = sqltar.substring(0, sqltar.length() - 1);
	
					sqltar = sqltar + ") VALUES (";
	
					index = 0;
					while (index < ar.size()) {
	
						sqltar = sqltar + "?,";
						index++;
					}
	
					// removing extra ',' from column name
					sqltar = sqltar.substring(0, sqltar.length() - 1);
	
					sqltar = sqltar + ")" + "" + ";";
					
					System.out.println(sqltar);
					
	
					PreparedStatement statementtar = connection.prepareStatement(sqltar);
	
					// inserting data
	
					BufferedReader lineReadertar = new BufferedReader(new FileReader(csvFilePathtar));
					String lineTexttar = null;
	
				
	
					lineReadertar.readLine(); // skip header line
	
					while ((lineTexttar = lineReadertar.readLine()) != null) {
	
						StringTokenizer st1 = new StringTokenizer(lineTexttar, dels);
	
						while (st1.hasMoreTokens()) {
	
							arr.add(st1.nextToken());
	
						}
	
						
						for (int j = 0; j < arr.size(); j++) {
							String temp = arr.get(j);
							statementtar.setString(j+1, temp);
						}
	                    
						arr.clear();
						statementtar.addBatch();
						
						if (count % batchSize == 0) {
							statementtar.executeBatch();
						}
					}
	
					lineReadertar.close();
						
						
	
					// execute the remaining queries
					   String query = "{CALL"+" "+sp+"()}"; // stored procedure call
						CallableStatement stmt1 = connection.prepareCall(query);
						stmt1.executeQuery();
					statementtar.executeBatch();
	
			
	
			lineReadertar.close();
			
			}
			
			
			
				
				
				
				
				///////////////////////////////////////////////////////////////////////////////////
				
				
				
				
				else {
					
					// arraylist
					
	
					BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePathtar));
					String text = lineReader.readLine();
	
					StringTokenizer st = new StringTokenizer(text, dels);
	
					while (st.hasMoreTokens()) {
	
						ar.add(st.nextToken());
	
					}
	
					// Creating table dynamically
	
					
					  int n1=1;
					 
					 int a=0; 
					 stmt = connection.createStatement(); 
					 String sql= "CREATE TABLE IF NOT EXISTS"+" "+ttn+"(";
						 while(a<ar.size()-1)
						 {
					  sql=sql+(ar.get(a))+" "+"varchar(255)"+","; 
					  a++; } 
					  count2=count1+1;
					  while(count2!=0)
					  { sql=sql+extra+n1+" "+"varchar(255)"+",";
					  count2--;
					  n1++;
					  }
					  sql = sql.substring(0,sql.length()-1); sql=sql+");";
				
					  System.out.println(sql);
					 
					 
					 stmt.executeUpdate(sql);
					  
					 
	
					// Inserting data command creation
	
					int n = 1;
					String sqltar ="INSERT INTO"+" "+ttn+"(";
					int index = 0;
					while (index < ar.size() - 1) {
						sqltar = sqltar + (ar.get(index)) + ",";
						index++;
					}
	
					 count2 = count1 + 1;
					while (count2 != 0) {
						sqltar = sqltar + extra + n + ",";
						count2--;
						n++;
					}
					sqltar = sqltar.substring(0, sqltar.length() - 1);
	
					sqltar = sqltar + ") VALUES (";
	
					int index2 = 0;
					while (index2 < ar.size() + count1) {
	
						sqltar = sqltar + "?,";
						index2++;
					}
	
					sqltar = sqltar.substring(0, sqltar.length() - 1);
	
					sqltar = sqltar + ")" + "" + ";";
	
					System.out.println(sqltar);
	
					PreparedStatement statementtar = connection.prepareStatement(sqltar);
	
					// inserting data
	
					BufferedReader lineReadertar = new BufferedReader(new FileReader(csvFilePathtar));
					String lineTexttar = null;
	
					
	
					lineReadertar.readLine(); // skip header line
	
					while ((lineTexttar = lineReadertar.readLine()) != null) {
	
						StringTokenizer st1 = new StringTokenizer(lineTexttar, dels);
	
						while (st1.hasMoreTokens()) {
	
							arr.add(st1.nextToken());
	
						}
						
							
	
						for (int j = 0; j < arr.size(); j++) {
							String temp = arr.get(j);
				
	
							statementtar.setString(j + 1, temp);
	
						}
						
						arr.clear();
	
						statementtar.addBatch();
						
						//statementtar.executeUpdate();
	
						if (count % batchSize == 0) {
							statementtar.executeBatch();
						}
					}
	
					lineReadertar.close();
				
	
					// execute the remaining queries
	
					String query = "{CALL"+" "+sp+"()}"; // stored procedure call
					CallableStatement stmt1 = connection.prepareCall(query);
					stmt1.executeQuery();
					statementtar.executeBatch();	
				}
				
			}
				
			
	
			 catch (IOException ex) {
				System.err.println(ex);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
	
			
				
			finally {
				try {
					connection.commit();
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}
				
	
		}
	}
	
	
	
