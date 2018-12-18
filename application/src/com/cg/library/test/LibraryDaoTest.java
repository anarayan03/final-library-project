package com.cg.library.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cg.library.bean.LibraryBean;
import com.cg.library.dao.ILibraryDao;
import com.cg.library.dao.LibraryDaoImpl;
import com.cg.library.exception.LibraryException;

class LibraryDaoTest 
{

	static LibraryBean lib;
	static ILibraryDao dao;
	
	@BeforeAll
	public static void init()
	{
		System.out.println("Before Class");
		dao = new LibraryDaoImpl();
		lib = new LibraryBean();
	}
	
	@Ignore
	@Test
	public void addBook() throws LibraryException, NumberFormatException, ClassNotFoundException, SQLException, IOException
	{
		lib.setBookId("112");
		lib.setBookName("c");
		lib.setAuthorName("aditya");
		lib.setPrice(150);
		lib.setQuantity(10);
		assertTrue("Data Inserted Successfully", Integer.parseInt(dao.addBook(lib))>100);
	}
	
	@Test
	public void viewBook() throws LibraryException, ClassNotFoundException, IOException, SQLException
	{
		assertNotNull(dao.viewBook("112"));
		//fail("Not yet implemented");
	}
	
	@Test
	public void issueBook() throws LibraryException, ClassNotFoundException, IOException, SQLException
	{
		assertNotNull(dao.issueBook("111"));
		//fail("Not yet implemented");
	}

	@Test
	public void acceptBook() throws LibraryException, ClassNotFoundException, IOException, SQLException
	{
		assertNotNull(dao.acceptBook("112"));
		//fail("Not yet implemented");
	}
	
	@Test
	public void retrieveAll() throws LibraryException, ClassNotFoundException, IOException, SQLException
	{
		assertNotNull(dao.retriveAll());
		//fail("Not yet implemented");
	}
	
}
