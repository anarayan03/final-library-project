package com.cg.library.dao;

public interface QueryMapping
{
	public static String INSERT_QUERY = "insert into Library values(bookId_sequence.nextval,?,?,?,?)";
	public static String EXECUTE_QUERY = "Select * from Library order by Book_Id";
	public static String EXECUTE_QUERY_WITH_CONDITION = "select * from Library where book_id=?";
	public static String ISSUE_QUERY = "update Library set quantity = (select quantity-1 from Library where Book_Id =?) where Book_Id =? and quantity <> 0";
	public static String ACCEPT_QUERY = "update Library set quantity = (select quantity+1 from Library where Book_Id =?) where Book_Id =?";
}
