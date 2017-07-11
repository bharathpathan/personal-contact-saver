/**
 * 
 */
package fr.epita.iam.services;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.datamodel.Member;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;

/**
 * @author tbrou
 *
 */
public interface MemberDAO {

	public void save(Member member) throws DaoSaveException;
	
	public void update(Member member) throws DaoUpdateException; 
	public void delete(Member member);
	public List<Member> search(Member criteria) throws DaoSearchException;
	
	
	public void releaseResources();
	
	
	
}
