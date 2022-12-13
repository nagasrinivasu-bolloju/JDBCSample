package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectToDataBase
{
	private final String url="jdbc:postgresql://localhost:5432/DemoDB";
	private final String username="postgres";
	private final String password="srinu534";
	private static Connection connection =null;
	private static Statement statement=null;
	private static PreparedStatement prepStatement=null;
	
	private boolean connect()
	{
		try
		{
			connection=DriverManager.getConnection(url,username,password);
			if(connection!=null)
			{
				System.out.println("Connection build succesfully!!");
				return true;
			}
			System.out.println("Connection failed!!!");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	private boolean createState()
	{
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql="insert into student values(?,?,?);";
			prepStatement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			System.out.println("Statement created successfully.");
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ConnectToDataBase c=new ConnectToDataBase();
		if(c.connect() && c.createState())
		{
			
			try(Scanner s=new Scanner(System.in);)
			{
			boolean flag=true;
			while(flag)
			{
				System.out.println("Ready to execute quaries...");
				System.out.println("----------------------------\nEnter your choice:");
				System.out.println("1-->print table.\n2-->insert rows into table. ");
				System.out.println("3-->exit.");
				int choice=s.nextInt();
				switch(choice)
				{
				case 1: printTable();
						break;
				case 2: insertIntoTable();
						break;
				case 3: flag=false;
						break;
				case 4: updateStuTable();
						break;
				default: System.out.println("Enter correct chioce.");
				}
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	private static void printTable() throws SQLException
	{
		System.out.println("\n\nprinting table...\n-------------------");
		ResultSet result=statement.executeQuery("select * from student;");
		while(result.next())
		{
			System.out.println(result.getInt(1)+" "+result.getString("name")+" "+result.getString(3));
		}
//		System.out.println("\nPrinting from the last\n\n");
//		if(result.last())
//			System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3));
//		while(result.previous())
//		{
//			System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3));
//		}
		System.out.println("\n--------------------------\n");
//		System.out.println("updating the details in result set:\n\n");
//		result.beforeFirst();
//		while(result.next())
//		{
//			int temp=result.getInt(1);
//			result.updateInt(1, temp+1);
//			result.updateRow();
//		}
//		result.close();
	}
	private static void insertIntoTable() throws SQLException
	{
		System.out.println("\n\ninserting into table...\n--------------------------");
		//int rows=statement.executeUpdate("insert into student values(4,'ajay','pithapuram');");
		Scanner sc=new Scanner(System.in);
		System.out.println("\nEnter Roll num:");
		int roll=sc.nextInt();
		System.out.println("\nEnter Name:");
		String name=sc.next();
		System.out.println("\nEnter City:");
		String city=sc.next();
		prepStatement.setInt(1, roll);
		prepStatement.setString(2,name);
		prepStatement.setString(3,city);
		int rows=prepStatement.executeUpdate();
		if(rows>0)
			System.out.println("Added "+rows+" successfully.\n");
	}
    
	private static void updateStuTable()
	{
		
	}
}
