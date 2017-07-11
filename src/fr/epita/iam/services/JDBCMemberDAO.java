/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.datamodel.Member;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;

/**
 * 
 * This is a class that contains all the database operations for the class
 * Identity
 * 
 * <pre>
 *  JDBCIdentityDAO dao = new JDBCIdentityDAO();
 *  // save an identity
 *  dao.save(new Identity(...));
 *  
 *  //search with an example criteria (qbe)  
 *  dao.search(new Identity(...);
 * </pre>
 * 
 * <b>warning</b> this class is dealing with database connections, so beware to
 * release it through the {@link #releaseResources()}
 * 
 * @author tbrou
 *
 */
public class JDBCMemberDAO implements MemberDAO {

	Connection connection;

	/**
	 * @throws SQLException
	 * 
	 */
	public JDBCMemberDAO() throws SQLException {
		String myDriver = "org.gjt.mm.mysql.Driver";
		 String myUrl = "jdbc:mysql://localhost/bharathdb";
		  this.connection = DriverManager.getConnection(myUrl, "root", "");
	}

	public void save(Member member) throws DaoSaveException {
		try {
		String query = " insert into login (username,password)"
					   + " values (?, ?)";
			PreparedStatement preparedStmt = this.connection.prepareStatement(query);	
			   preparedStmt.setString (1, member.getUname());
			   preparedStmt.setString (2, member.getPass());

			   preparedStmt.executeUpdate();
		} catch (SQLException sqle) {
			DaoSaveException exception = new DaoSaveException();
			exception.initCause(sqle);
			exception.setFaultObject(member);
			throw exception;
		}
	}

	@Override
	public void update(Member member) throws DaoUpdateException {
		// TODO Auto-generated method stub
		String query = "UPDATE login SET password='" +member.getPass()+"' WHERE member_id='" + member.getMid()+"'" ;
	    
		 
		try {
			
			PreparedStatement preparedStmt = this.connection.prepareStatement(query);
			preparedStmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}

	@Override
	public void delete(Member member) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String query = "DELETE FROM login WHERE member_id='" + member.getMid()+"'" ;
		    	PreparedStatement preparedStmt = null;
				try {
					preparedStmt = this.connection.prepareStatement(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	try {
		    		
					preparedStmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

	}

	@Override
	public void releaseResources() {
		// TODO Auto-generated method stub
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Member> search(Member member) throws DaoSearchException {
		// TODO Auto-generated method stub
		
		List<Member> returnedList = new ArrayList<Member>();
		try {
			PreparedStatement preparedStatement = this.connection
					.prepareStatement("SELECT * from login WHERE username='" + member.getUname()+"' AND password='" + member.getPass()+"'");

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				String uname = results.getString("username");
				String pass = results.getString("password");
				String mid = results.getString("member_id");
				
				returnedList.add(new Member( uname,pass, mid));

			}
		} catch (SQLException sqle) {
			DaoSearchException daose = new DaoSearchException();
			daose.initCause(sqle);
			throw daose;
		}

		return returnedList;
		
	}

	
	
}