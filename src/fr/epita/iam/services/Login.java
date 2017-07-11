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
 * @author bharath
 *this clas only deals with the login services which checks whether the provided username and password are valid or not
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
	int count=0;
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
		List<Member> isLogin=null;
		if(userName!=null&&password!=null){
		isLogin =mdao.search(new Member(userName,password,null));
		if(!mdao.searchUser(new Member(userName,password,null)).isEmpty()&& isLogin.isEmpty()){
			System.out.println("Wrong Password, Try Again!!!" );
			if(count<3){
				count=count+1;
			createUser();
			}
			else
				System.out.println("Sorry Unable to Recognise. Bye Bye!!!");
			
		
		}
		else if (mdao.searchUser(new Member(userName,password,null)).isEmpty()&&isLogin.isEmpty()) {
			logger.log("unable to authenticate "  + userName);
			System.out.println("Sorry user not available in our database.");
			System.out.println("Do you want to create a user (y/n)");
			String createUser=scanner.nextLine();
			if(createUser.equals("y"))
			{
				System.out.println("Enter User Name :");
				String name= scanner.nextLine();
				if(mdao.searchUser(new Member(name,null,null)).isEmpty()){
				System.out.println("Enter Password :");
				String pass= scanner.nextLine();
				Member create = new Member(name,pass,null);
				mdao.save(create);
				Launcher.main(null);}
				else
					System.out.println("User already exists with this name. ");
					System.out.println("please login with his credentials (or) ");
					System.out.println("create a user with new user name ");
					createUser();
			}
			else if(!mdao.searchUser(new Member(userName,password,null)).isEmpty()){
				System.out.println("Wrong Password, Try Again!!!");
				if(count<3){
				createUser();
				count=count+1;}
				else
					System.out.println("Sorry Unable to Recognise. Bye Bye!!!");
			
			}
				
		}
		else if (!isLogin.isEmpty())
			System.out.println("Successfully Logged In");
		}
		
		return isLogin;
	}
}