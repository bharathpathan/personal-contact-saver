/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.Date;
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
public class JDBCIdentityDAO implements IdentityDAO {

	Connection connection;

	/**
	 * @throws SQLException
	 * 
	 */
	public JDBCIdentityDAO() throws SQLException {
		String myDriver = "org.gjt.mm.mysql.Driver";
		 String myUrl = "jdbc:mysql://localhost/bharathdb";
		  this.connection = DriverManager.getConnection(myUrl, "root", "");
	}
	/**
	 * this is for identity save
	 */

	public void save(Identity identity) throws DaoSaveException {
		try {
		String query = " insert into identities (IDENTITY_UID,IDENTITY_DISPLAYNAME,IDENTITY_EMAIL,IDENTITY_BIRTHDATE,member_id)"
					   + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = this.connection.prepareStatement(query);	
			   preparedStmt.setString (1, identity.getUid());
			   preparedStmt.setString (2, identity.getDisplayName());
			   preparedStmt.setString (3, identity.getEmail());
			   preparedStmt.setDate (4, identity.getBirthdate());
			   preparedStmt.setString (5, identity.getMember_id());
			   preparedStmt.executeUpdate();
		} catch (SQLException sqle) {
			DaoSaveException exception = new DaoSaveException();
			exception.initCause(sqle);
			exception.setFaultObject(identity);
			throw exception;
		}
	}
//	
	/**
	 * this is for identity searching
	 */

	public List<Identity> search(Identity criteria) throws DaoSearchException {
		List<Identity> returnedList = new ArrayList<Identity>();
		try {
			PreparedStatement preparedStatement = this.connection
					.prepareStatement("SELECT * FROM identities JOIN login ON login.member_id=identities.member_id WHERE login.member_id='" + criteria.getMember_id()+"'");

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				String uid = results.getString("IDENTITY_UID");
				String displayName = results.getString("IDENTITY_DISPLAYNAME");
				String email = results.getString("IDENTITY_EMAIL");
				Date birthdate = results.getDate("IDENTITY_BIRTHDATE");
				String mid = results.getString("member_id");
				returnedList.add(new Identity( uid,displayName, email,birthdate,mid));

			}
		} catch (SQLException sqle) {
			DaoSearchException daose = new DaoSearchException();
			daose.initCause(sqle);
			throw daose;
		}

		return returnedList;
	}

	/**
	 * this is releasing the database connection, so you should not use this
	 * instance of DAO anymore
	 */
	public void releaseResources() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.epita.iam.services.IdentityDAO#update(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void update(Identity identity) {
		String query = "UPDATE identities SET IDENTITY_DISPLAYNAME='" +identity.getDisplayName()+"', IDENTITY_EMAIL='"+identity.getEmail()+"', IDENTITY_BIRTHDATE='"+identity.getBirthdate()+"' WHERE IDENTITY_UID='" + identity.getUid()+"' AND member_id='" + identity.getMember_id()+"'" ;
    
		 
		try {
			
			PreparedStatement preparedStmt = this.connection.prepareStatement(query);
			preparedStmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.epita.iam.services.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void delete(Identity identity) {
		// TODO Auto-generated method stub
		String query = "DELETE FROM identities WHERE IDENTITY_UID='" + identity.getUid()+"' AND member_id='" + identity.getMember_id()+"'" ;
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
}
