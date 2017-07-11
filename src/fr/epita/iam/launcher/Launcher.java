/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.datamodel.Member;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;
import fr.epita.iam.services.JDBCIdentityDAO;
import fr.epita.iam.services.JDBCMemberDAO;
import fr.epita.iam.services.Login;
import fr.epita.logging.LogConfiguration;
import fr.epita.logging.Logger;

/**
 * @author bharath
 *
 */
public class Launcher {

	/**
	 * 
	 * Launcher Program which which invoke all the services.
	 * all operations on contact boob is available here
	 * @param args
	 * @throws SQLException 
	 * @throws DaoSearchException 
	 * 
	 * @throws DaoUpdateException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	
	public static void main(String[] args) throws SQLException, DaoSearchException, DaoSaveException, DaoUpdateException, IOException, InterruptedException {

		JDBCIdentityDAO dao = new JDBCIdentityDAO();
		JDBCMemberDAO mdao = new JDBCMemberDAO();
		Login login = new Login();
		Scanner scanner = new Scanner(System.in);
		LogConfiguration conf = new LogConfiguration("./tmp/application.log");
		Logger logger = new Logger(conf);
		
	    logger.log("beginning of the program");
	    System.out.println("Personal Contact Store \n \n");
		
		Member members = null;
		List<Member> isLogin =login.createUser();
		for(Member mem : isLogin)
			members=mem;
		String choice="";
		
		while(!choice.equals("3"))
		if(!isLogin.isEmpty())
		{
			
			System.out.println("-------Home Page-------");
			System.out.println("1. User Details");
			System.out.println("2. Contact Book");
			System.out.println("3. Exit");
			System.out.println("your choice : ");
			choice=scanner.nextLine();
			switch(choice)
			{
			case "1":System.out.println("------User Details------");
					 System.out.println("User Name : - "+members.getUname());
					 System.out.println("Password : - ******* ");
					 String ch = "";
					 while(!ch.equals("3")){
					 System.out.println("\n 1. change password ");
					 System.out.println("2. Delete Account ");
					 System.out.println("3. Go Back ");
					 System.out.println("your choice : ");
					
					 ch = scanner.nextLine();
					 if(ch.equals("1"))
					 {
						 System.out.println("Please enter your new password :- "); 
						 String newPass = scanner.nextLine();
						 Member member= new Member(members.getUname(),newPass,members.getMid());
						 mdao.update(member);
						System.out.println("\n \n ");
						 Launcher.main(args);
					 }
					 else if (ch.equals("2"))
					 {
						 System.out.println("Are you sure you want to delete your account (y/n) ?");
						 String deleteUser=scanner.nextLine();
						 if(deleteUser.equals("y")){
							 System.out.println(members);
							 mdao.delete(members);
							 System.out.println("You Have been Logged Out !!!!!!");
						 	Launcher.main(args);}
					 }
					 else if (!ch.equals("3"))
						 {
						 System.out.println("Wrong choice");
						 }
						 }
					 break;
			case "2":String answer = "";
			while (!"3".equals(answer)) {
				System.out.println("-------Contact Home Page"
						+ "------");
				System.out.println("1. Create Contact");
				System.out.println("2. Search (update/delete) :");
				System.out.println("3. Log Out");
				System.out.println("your choice : ");
				
				logger.log("User chose the " + answer + " choice");

				answer = scanner.nextLine();

				switch (answer) {
				case "1":
					System.out.println("-------Contact Creation------");
					logger.log("selected the identity creation");
					System.out.println("please input the Contact display name :");
					String displayName  = scanner.nextLine();
					System.out.println("Contact email :");
					String email = scanner.nextLine();
					System.out.println("Contact birthdate :");
					String birthdate = scanner.nextLine();
				    Date bdate=Date.valueOf(birthdate);
					
					Identity identity = new Identity( null,displayName, email, bdate,members.getMid());
					try {
						dao.save(identity);
						System.out.println("the save operation completed successfully");
					} catch (DaoSaveException e) {
						System.out.println("the save operation is not able to complete, details :" + e.getMessage());
					}
					
					break;
				case "2":

					// Update Identity
					
					System.out.println("--------Contact Update (or) Delete----------");
					Identity criteria = new Identity( null,null, null, null, members.getMid());
					List<Identity> identities = dao.search(criteria);
					System.out.println("Availabe Contact");
					for(Identity idens : identities)
					{
						System.out.print("UID : "+idens.getUid()+"\t");
						System.out.print("Name : "+idens.getDisplayName()+"\t");
						System.out.print("Date of Birth : "+idens.getBirthdate()+"\t");
						System.out.println("Email : "+idens.getEmail());
						
					}
					String ch1="";
					while(!ch1.equals("3")){
					System.out.println("1.update contact details");
					System.out.println("2.delete a contact");
					System.out.println("3.Exit.");
					System.out.println("your choice : ");
					Scanner sc = new Scanner(System.in);
				    ch1 = sc.nextLine();
					
					
					switch(ch1)
					{
					case "1":
						System.out.println("please input the updatable contact UID :");
						String uid  = scanner.nextLine();
						
					System.out.println("please input the new contact Display Name :");
					String displayName1  = scanner.nextLine();
					System.out.println("new contact email :");
					String email1 = scanner.nextLine();
					System.out.println("new birthdate :");
					String birthdate1 = scanner.nextLine();
					 Date bdate1=Date.valueOf(birthdate1);
					
					Identity identity1 = new Identity( uid,displayName1, email1, bdate1,members.getMid());
						dao.update(identity1);
						System.out.println("the Update operation completed successfully");
						break;
					case "2":System.out.println("please input the updatable contact UID :");
							 String uid1  = scanner.nextLine();
							 for(Identity id1 : identities)
								if(id1.getUid().equals(uid1))
								{
									dao.delete(id1);
								}
							 /*String name1  = scanner.nextLine();
							 dao.delete(name1);*/
							
						break;
					case "3" : break;
					}}
					break;

				case "3":

					System.out.println("you decided to quit, bye!");
					break;
				default:

					System.out.println("unrecognized option : type 1,2,3 or 4 to quit");
					break;
				}

			}
				break;
			
			}
			scanner.close();
			System.out.println("you decided to quit, bye!");
			

		}

	}

}

