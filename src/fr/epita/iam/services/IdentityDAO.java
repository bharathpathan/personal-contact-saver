/**
 * 
 */
package fr.epita.iam.services;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;

/**
 * @author bharath
 *this is the interface which defines the Identity object operations
 */
public interface IdentityDAO {

	public void save(Identity identity) throws DaoSaveException;
	
	public void update(Identity identity) throws DaoUpdateException; 
	public void delete(Identity identity);
	public List<Identity> search(Identity criteria) throws DaoSearchException;
	
	public void releaseResources();
	
	
	
}
