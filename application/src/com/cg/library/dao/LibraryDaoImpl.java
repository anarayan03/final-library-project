package com.cg.library.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.library.bean.LibraryBean;
import com.cg.library.exception.LibraryException;
import com.cg.library.util.DbConnection;

public class LibraryDaoImpl implements ILibraryDao 
{
	
	Logger logger=Logger.getRootLogger();
	public LibraryDaoImpl()
	{
	PropertyConfigurator.configure("Resources//log4j.properties");
	
	}
	
	//------------------------ 1. Library Application --------------------------
		/*******************************************************************************************************
		 - Function Name	:	addBook(LibraryBean libraryBean)
		 - Input Parameters	:	LibraryBean libraryBean
		 - Return Type		:	String
		 - Throws			:  	LibraryException, SQLException, ClassNotFoundException, IOException
		 - Author			:	ADITYA NARAYAN
		 - Creation Date	:	18/12/2018
		 - Description		:	Adding Book
		 ********************************************************************************************************/

	@Override
	public String addBook(LibraryBean libraryBean)
			throws LibraryException, SQLException, ClassNotFoundException, IOException {
		
		Connection connection = DbConnection.getConnection();
		Statement statement = connection.createStatement();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String bookId = null;
		
		try
		{
			preparedStatement = connection.prepareStatement(QueryMapping.INSERT_QUERY);
			//preparedStatement = connection.prepareStatement("insert into Library values(bookId_sequence.nextval,?,?,?,?)");
			
			preparedStatement.setString(1, libraryBean.getBookName());
			preparedStatement.setString(2, libraryBean.getAuthorName());
			preparedStatement.setDouble(3, libraryBean.getPrice());
			preparedStatement.setLong(4, libraryBean.getQuantity());
			
			preparedStatement.executeUpdate();
			
			resultSet = statement.executeQuery(QueryMapping.EXECUTE_QUERY);
			//resultSet = statement.executeQuery("Select * from Library order by Book_Id");
			
			while(resultSet.next())
			{
				logger.info("Book details added successfully:");
				bookId = resultSet.getString(1);
								
			}
			return bookId;
			
		}catch (Exception e)
		{
			logger.error(e.getMessage());
			System.out.println(e);
		}
		
		return null;
	}

	//------------------------ 1. Library Application --------------------------
		/*******************************************************************************************************
		 - Function Name	:	viewBook(String bookId)
		 - Input Parameters	:	bookId
		 - Return Type		:	LibraryBean
		 - Throws			:  	LibraryException, ClassNotFoundException, IOException, SQLException
		 - Author			:	ADITYA NARAYAN
		 - Creation Date	:	18/12/2018
		 - Description		:	ViewBook
		 ********************************************************************************************************/
	
	@Override
	public LibraryBean viewBook(String bookId)
			throws LibraryException, ClassNotFoundException, IOException, SQLException {
		
		Connection connection = DbConnection.getConnection();
		LibraryBean libraryBean = new LibraryBean();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		//resultSet = statement.executeQuery("select * from Library where book_id='"+bookId+"'");
		preparedStatement = connection.prepareStatement(QueryMapping.EXECUTE_QUERY_WITH_CONDITION);
		preparedStatement.setString(1, bookId);
		resultSet = preparedStatement.executeQuery();
		try
		{
		while(resultSet.next())
		{
			logger.info("Record Found Successfully");
			libraryBean.setBookId(resultSet.getString(1));
			libraryBean.setBookName(resultSet.getString(2));
			libraryBean.setAuthorName(resultSet.getString(3));
			libraryBean.setPrice(resultSet.getDouble(4));
			libraryBean.setQuantity(resultSet.getLong(5));
			
		}
		}catch (Exception e)
		{
			logger.error(e.getMessage());
			System.out.println(e);
		}
		
		return libraryBean;
	}

	//------------------------ 1. Library Application --------------------------
			/*******************************************************************************************************
			 - Function Name	:	issueBook(String bookId)
			 - Input Parameters	:	bookId
			 - Return Type		:	LibraryBean
			 - Throws			:  	LibraryException, ClassNotFoundException, IOException, SQLException
			 - Author			:	ADITYA NARAYAN
			 - Creation Date	:	18/12/2018
			 - Description		:	Issue the book
			 ********************************************************************************************************/
	
	@Override
	public LibraryBean issueBook(String bookId)
			throws LibraryException, ClassNotFoundException, IOException, SQLException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.getConnection();
		//Statement statement = connection.createStatement();
		LibraryBean libraryBean = new LibraryBean();
		
		ResultSet resultSet = null;
	
		try
		{
			
			preparedStatement = connection.prepareStatement(QueryMapping.ISSUE_QUERY);
			preparedStatement.setString(1, bookId);
			preparedStatement.setString(2, bookId);
			
			resultSet = preparedStatement.executeQuery();
			
			/*preparedStatement = connection.prepareStatement(" update Library set quantity "
					+ "= (select quantity-1 from Library where book_id ='"+bookId+"')where book_id ='"+bookId+"' and quantity <> 0");
			
			preparedStatement.executeUpdate();*/
			
			preparedStatement = connection.prepareStatement(QueryMapping.EXECUTE_QUERY_WITH_CONDITION);
			preparedStatement.setString(1, bookId);
			resultSet = preparedStatement.executeQuery();
			
			//resultSet = statement.executeQuery("select * from Library where book_id='"+bookId+"'");
			
		while(resultSet.next())
		{
			
			if(resultSet.getLong(5)>0)
			{
				logger.info("Book issued Successfully Successfully");
				libraryBean.setBookId(resultSet.getString(1));
				libraryBean.setBookName(resultSet.getString(2));
				libraryBean.setAuthorName(resultSet.getString(3));
				libraryBean.setPrice(resultSet.getDouble(4));
				libraryBean.setQuantity(resultSet.getLong(5));	
			}
			else
			{
				logger.info("Book out of stock");
				System.out.println("Book out of stock");
				return null;
			}
			
		}
		}catch (Exception e)
		{
			logger.error(e.getMessage());
			System.out.println(e);
		}
		return libraryBean;
	}

	//------------------------ 1. Library Application --------------------------
			/*******************************************************************************************************
			 - Function Name	:	acceptBook(String bookId)
			 - Input Parameters	:	bookId
			 - Return Type		:	LibraryBean
			 - Throws			:  	LibraryException, ClassNotFoundException, IOException, SQLException
			 - Author			:	ADITYA NARAYAN
			 - Creation Date	:	18/12/2018
			 - Description		:	accept the book from the students
			 ********************************************************************************************************/
	
	@Override
	public LibraryBean acceptBook(String bookId) throws LibraryException, ClassNotFoundException, IOException, SQLException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.getConnection();
		Statement statement = connection.createStatement();
		LibraryBean libraryBean = new LibraryBean();
		
		ResultSet resultSet = null;
		
		try
		{
			preparedStatement = connection.prepareStatement(QueryMapping.ACCEPT_QUERY);
			preparedStatement.setString(1, bookId);
			preparedStatement.setString(2, bookId);
			
			resultSet = preparedStatement.executeQuery();
			
			/*preparedStatement = connection.prepareStatement(" update Library set quantity "
					+ "= (select quantity+1 from Library where book_id ='"+bookId+"')where book_id ='"+bookId+"'");
			preparedStatement.executeUpdate();*/
			
			preparedStatement = connection.prepareStatement(QueryMapping.EXECUTE_QUERY_WITH_CONDITION);
			preparedStatement.setString(1, bookId);
			resultSet = preparedStatement.executeQuery();
			
		while(resultSet.next())
		{

			logger.info("Book received Successfully");
			libraryBean.setBookId(resultSet.getString(1));
			libraryBean.setBookName(resultSet.getString(2));
			libraryBean.setAuthorName(resultSet.getString(3));
			libraryBean.setPrice(resultSet.getDouble(4));
			libraryBean.setQuantity(resultSet.getLong(5));
			
		}
		}catch (Exception e)
		{
			logger.error(e.getMessage());
			System.out.println(e);
		}
		
		return libraryBean;
	}

	//------------------------ 1. Library Application --------------------------
			/*******************************************************************************************************
			 - Function Name	:	retriveAll()
			 - Input Parameters	:	
			 - Return Type		:	List
			 - Throws			:  	LibraryException, ClassNotFoundException, SQLException, IOException
			 - Author			:	ADITYA NARAYAN
			 - Creation Date	:	18/12/2018
			 - Description		:	return list of existing books
			 ********************************************************************************************************/
	
	@Override
	public List<LibraryBean> retriveAll() throws LibraryException, ClassNotFoundException, SQLException, IOException {
		Connection con= DbConnection.getConnection();
		Statement statement=con.createStatement();
		
		List<LibraryBean> list=null;
		ResultSet resultSet=null;
		
		resultSet=statement.executeQuery(QueryMapping.EXECUTE_QUERY);
		list=new ArrayList<>();
		while(resultSet.next())
		{
			logger.info("List Retrieve successfully");
			LibraryBean libraryBean =new LibraryBean();
			libraryBean.setBookId(resultSet.getString(1));
			libraryBean.setBookName(resultSet.getString(2));
			libraryBean.setAuthorName(resultSet.getString(3));
			libraryBean.setPrice(resultSet.getDouble(4));
			libraryBean.setQuantity(resultSet.getLong(5));
			
			list.add(libraryBean);
			
		}
		return list;

	}

}
