package application.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.util.dbConnection;
import application.view.NotesLoginController;

public class DBhandler {
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public void deleteNote(String noteid) throws SQLException
	{
		conn = dbConnection.getConnection();
		conn.setAutoCommit(false);
		try {
			if(conn != null)
			{
			     System.out.println("Opened database successfully - Delete");

			     stmt = conn.createStatement();
			     String sql = "delete from note_table where login_id='" + NotesLoginController.loginID + "' and id='"+noteid+"';";
			     stmt.executeUpdate(sql);
			     
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    System.out.println("####################### Failed to Delete Notes #########################");
		} finally {
		     try {
				 stmt.close();
				 conn.commit();
				 conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean saveNotesData(String title, String desc, String loginId) throws SQLException
	{
		conn = dbConnection.getConnection();
		conn.setAutoCommit(false);
		try {
			if(conn != null)
			{
			     System.out.println("Opened database successfully - Save Notes");

			     stmt = conn.createStatement();
			     String sql = "insert into note_table (TITLE, DESCRIPTION, LOGIN_ID) "
			     		+ "values ('"+title.replace("'", "\"")+"','"+desc.replace("'", "\"")
			     		+ "','"+loginId.replace("'", "\"")+"')";
			     sql = sql.replace("'", "\"");
			     stmt.executeUpdate(sql);
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    System.out.println("####################### Failed to Save Notes #########################");
			return false;
		} finally {
		     try {
				 stmt.close();  
				 conn.commit();
				 conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	    return true;
	}

	public boolean editNotesData(String title, String desc, String loginId) throws SQLException
	{
		conn = dbConnection.getConnection();
		conn.setAutoCommit(false);
		try {
			if(conn != null)
			{
			     System.out.println("Opened database successfully - Update Notes");

			     stmt = conn.createStatement();
			     String sql = "update note_table set description = '"+desc.replace("'", "\"")+"' where title = '"+title.replace("'", "\"")
			     +"' and login_id = '" + loginId + "'";
			     stmt.executeUpdate(sql);

			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    System.out.println("####################### Failed to Update Notes #########################");
			return false;
		} finally {
		     try {
			     stmt.close();
		    	 conn.commit();
				 conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return true;
	}
	
	public boolean checkCredentials(String user, String pass)
	{    	
      try
      {
        conn = dbConnection.getConnection();
        if(conn != null)
        {
        	System.out.println("###############DB Connected Successfully###############");
        	
            String sql = "SELECT * FROM note_login where user_name = '" + user +"'" ;
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
              if(pass.toString().equals(rs.getString(3)))
            	  return true;
            }
        }
        return false;
      }
      catch (SQLException e)
      {
        System.err.println("Error " + e);
        return false;
      }
	}
	
	public String getLoginID(String user)
	{
		String loginId = "";
	      try
	      {
	        Connection conn = dbConnection.getConnection();
	        if(conn != null)
	        {
	        	System.out.println("###############DB Connected Successfully###############");
	        	
	            String sql = "SELECT * FROM note_login where user_name = '" + user +"'" ;
	            ResultSet rs = conn.createStatement().executeQuery(sql);
	            while (rs.next()) {
	              loginId = rs.getString(1);
	            }
	            conn.close();
	        }
	      }
	      catch (SQLException e)
	      {
	        System.err.println("Error " + e);
	      }
	      return loginId;
	}

	public void createNewUser(Integer loginID, String username, String password) throws SQLException
	{
		conn = dbConnection.getConnection();
		conn.setAutoCommit(false);
		try {
			if(conn != null)
			{
			     System.out.println("Opened database successfully - Save Notes");

			     stmt = conn.createStatement();
			     String sql = "insert into note_login values (" + loginID + ",'" + username.replace("'", "\"")
			     + "','" + password.replace("'", "\"")+"')";
			     sql = sql.replace("'", "\"");
			     stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    System.out.println("####################### Failed to Save Notes #########################");
		} finally {
		     try {
				 stmt.close();		     
				 conn.commit();
				 conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public Integer getNextLoginID()
	{
		int loginId = 0;
	      try
	      {
	        Connection conn = dbConnection.getConnection();
	        if(conn != null)
	        {
	        	System.out.println("###############DB Connected Successfully###############");
	        	
	            String sql = "select max(login_id) from note_login" ;
	            ResultSet rs = conn.createStatement().executeQuery(sql);
	            while (rs.next()) {
	            	if(rs.getString(1) == null || rs.getString(1).equals(""))
	            		loginId = 0;
	            	else
	            		loginId = Integer.parseInt(rs.getString(1));
	            }
	            conn.close();
	        }
	      }
	      catch (SQLException e)
	      {
	        System.err.println("Error " + e);
	      }
		return loginId+1;
	}
	
	public boolean checkValidUser(String user) throws SQLException
	{
        conn = dbConnection.getConnection();
	      try
	      {
	        if(conn != null)
	        {
	        	System.out.println("###############DB Connected Successfully###############");
	        	
	            String sql = "SELECT * FROM note_login where user_name = '" + user +"'" ;
	            ResultSet rs = conn.createStatement().executeQuery(sql);
	            while (rs.next()) {
	            	conn.close();
	            	return true;
	            }
	        }
	      }
	      catch (SQLException e)
	      {
	        System.err.println("Error " + e);
	      }
	      return false;
	}
}
