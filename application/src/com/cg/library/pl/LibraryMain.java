package com.cg.library.pl;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.library.bean.LibraryBean;
import com.cg.library.exception.LibraryException;
import com.cg.library.service.IlibraryService;
import com.cg.library.service.LibraryServiceImpl;

public class LibraryMain 
{
	
	static Logger logger=Logger.getRootLogger();
	public LibraryMain()
	{
	PropertyConfigurator.configure("Resources//log4j.properties");
	
	}
	
	static Scanner scanner = new Scanner(System.in);
	static IlibraryService ilibraryService = null;
	static LibraryServiceImpl libraryServiceImpl = null;
	
	public static void main(String[] args) 
	{
		LibraryBean libraryBean = null;
		String bookId = null;
		int option = 0;
		
		while(true)
		{
			System.out.println();
			System.out.println();
			System.out.println("	LIBRARY	");
			System.out.println("----------------------------");
			System.out.println("1. Add Book ");
			System.out.println("2. View Book");
			System.out.println("3. Issue Book");
			System.out.println("4. Accept Book");
			System.out.println("5. Retrieve All Book Details");
			System.out.println("6. Exit");
			System.out.println("____________________________");
			System.out.println("\nEnter your Choice ");
			
			try
			{
				option = scanner.nextInt();
				
				switch (option) 
				{
				case 1 :
					
					//To Add New Books Detail....
					
					while(libraryBean==null)
					{
						libraryBean=populateLibraryBean();
					}
					try
					{
						libraryServiceImpl = new LibraryServiceImpl();
						bookId=libraryServiceImpl.addBook(libraryBean);
						System.out.println("Book details has been successfully entered....");
						System.out.println("Book ID is :"+ bookId);
					}catch (LibraryException libraryException) {
						System.out.println("ERROR :" + libraryException.getMessage());
					}
					finally
					{
						bookId = null;
						libraryServiceImpl = null;
						libraryBean = null;
					}
					
					break;
			    
				case 2 :
					
					//To view existing book details by using bookId from user....
					
					System.out.println("Enter the Book Id which you want to view :");
					bookId = scanner.next();
					
					try
					{
						libraryBean = new LibraryBean();
						ilibraryService = new LibraryServiceImpl();
						libraryServiceImpl = new LibraryServiceImpl();
						
						if(libraryServiceImpl.isValidBookId(bookId))
						{		
								libraryBean = ilibraryService.viewBook(bookId);
								if(!(libraryBean.getAuthorName()==null))
								{
									logger.info("Book found:");
									System.out.println(libraryBean);
								}
								else
									logger.info("Book not found");
									System.out.println("no book available in library with such id ");
						}
						else
						{
							logger.error("Book Id is not Valid.....");
							System.out.println("Book Id is not Valid.....");
						}
						
					}catch (Exception e) 
					{
						logger.error("not Valid.....");
						System.err.println("Not Valid"+e.getMessage());
					}
					
					break;
					
				case 3 :
	
					//To issue existing book, reduce the quantity of book by 1 if issue method is called and show
					//book out of stock if quantity is 0.
					
					System.out.println("Enter the Book Id which you want to Issue :");
					bookId = scanner.next();
					
					try
					{
						libraryBean = new LibraryBean();
						ilibraryService = new LibraryServiceImpl();
						libraryServiceImpl = new LibraryServiceImpl();
						
						if(libraryServiceImpl.isValidBookId(bookId))
						{		
								libraryBean = ilibraryService.issueBook(bookId);
								if(!(libraryBean.getAuthorName()==null))
								{
									logger.info("Book issued successfully.....");
									System.out.println(libraryBean+"\n"+"Book Issued Successfully...\n"+"Quantity is reduced by 1....");
									break;
									
								}
								else
									logger.info("Book not available");
									System.out.println("no book available in library with such id.... ");
						}
						else
						{
							logger.info("Book Id is not Valid.....");
							System.out.println("Book Id is not Valid.....");
						}
						
					}catch (Exception e) 
					{
						logger.error("not Valid.....");
						System.err.println("Not Valid/Book is Out of Stock "+e.getMessage());
					}
					break;
					
					//To receive existing book from student, increase the quantity of book by 1 if accept method is called
					
				case 4 :
					
					System.out.println("Enter the Book Id which you received by the student :");
					bookId = scanner.next();
					
					try
					{
						libraryBean = new LibraryBean();
						ilibraryService = new LibraryServiceImpl();
						libraryServiceImpl = new LibraryServiceImpl();
						
						if(libraryServiceImpl.isValidBookId(bookId))
						{		
								libraryBean = ilibraryService.acceptBook(bookId);
								if(!(libraryBean.getAuthorName()==null))
								{
									logger.info("Book received successfully.....");
									System.out.println(libraryBean+"\n"+"Book received Successfully...\n"+"Quantity is increased by 1....");
									
								}
								else
									logger.info("no book available.....");
									System.out.println("no book available in library with such id.... ");
						}
						else
						{
							logger.info("Book Id is not Valid.....");
							System.out.println("Book Id is not Valid.....");
						}
						
					}catch (Exception e) 
					{
						logger.error("not Valid.....");
						System.err.println("Not Valid"+e.getMessage());
					}
	
					break;
					
					//For retrieving all books detail.
					
				case 5 :
					
					try
					{
						
						libraryBean=new LibraryBean();
						libraryServiceImpl=new LibraryServiceImpl();
						List<LibraryBean> list=null;
						list=libraryServiceImpl.retriveAll();
						System.out.println(list);
						
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					
					break;
	
					//For Exit
					
				case 6 :
	
					System.out.println("Successfully Exit.....");
					System.exit(0);
					
					//Exit if user choose wrong choice.
					
				default:
					
					System.out.println("Wrong Choice");
					System.exit(0);
				}
				
			}catch (Exception e)
			{
				System.out.println(e);
			}
			
		}
		
	}

	//Method for book detail population
	
	private static LibraryBean populateLibraryBean()
	{
		LibraryBean libraryBean = new LibraryBean();
		System.out.println("Enter Book Details :");
		
		System.out.println("\nEnter Book Name : ");
		libraryBean.setBookName(scanner.next());
		
		System.out.println("Enter Author Name : ");
		libraryBean.setAuthorName(scanner.next());
		
		try
		{
		System.out.println("Enter Book's Price : ");
		libraryBean.setPrice(scanner.nextDouble());
		}catch (Exception e) {
			System.out.println("Invalid Data");
			System.err.println(e);
			System.exit(0);
		}
		
		try
		{
		System.out.println("Enter Books Quantity :");
		libraryBean.setQuantity(scanner.nextLong());
		}catch (Exception e) {
			System.out.println("Invalid Data");
			System.err.println(e);
			System.exit(0);
		}
		
		libraryServiceImpl = new LibraryServiceImpl();
		
		try
		{
			libraryServiceImpl.validateLibraryAttribute(libraryBean);
			return libraryBean;
		}catch (LibraryException e)
		{
			System.out.println("Invalid Data");
			System.err.println(e);
			System.exit(0);
		}
		
		return null;
	}

}
