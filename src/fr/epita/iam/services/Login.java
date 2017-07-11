/**
 * 
 */
package fr.epita.iam.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Member;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;
import fr.epita.iam.launcher.Launcher;
import fr.epita.logging.LogConfiguration;
import fr.epita.logging.Logger;

/**
 * @author tbrou
 *
 */
public class Login {

	/**
	 * @param args
	 * @return 
	 * @throws SQLException 
	 * @throws DaoSearchException 
	 * @throws DaoUpdateException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public List<Member> createUser() throws SQLException, DaoSearchException, DaoSaveException, DaoUpdateException, IOException, InterruptedException {

		JDBCIdentityDAO dao = new JDBCIdentityDAO();
		JDBCMemberDAO mdao = new JDBCMemberDAO();
		
	
		LogConfiguration conf = new LogConfiguration("./tmp/application.log");
		Logger logger = new Logger(conf);
		
	    logger.log("beginning of the program");
		Scanner scanner = new Scanner(System.in);
		System.out.println("--------Login Page--------");
		System.out.println("User name :");
		String userName = scanner.nextLine();
		System.out.println("Password :");
		String password = scanner.nextLine();
		
		List<Member> isLogin =mdao.search(new Member(userName,password,null));
		if (isLogin.isEmpty()) {
			logger.log("unable to authenticate "  + userName);
			System.out.println("Sorry user not available in our database.");
			System.out.println("Do you want to create a user (y/n)");
			String createUser=scanner.nextLine();
			if(createUser.equals("y"))
			{
				System.out.println("Enter User Name :");
				String name= scanner.nextLine();
				System.out.println("Enter Password :");
				String pass= scanner.nextLine();
				Member create = new Member(name,pass,null);
				mdao.save(create);
				Launcher.main(null);
			}
			else
				System.out.println("Bye Bye!!!!!!");
			
			
				
		}
		return isLogin;
	}
}