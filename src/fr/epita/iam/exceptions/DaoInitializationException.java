/**
 * 
 */
package fr.epita.iam.exceptions;

/**
 * @author bharath
 *
 */
public class DaoInitializationException extends Exception{
	private Object faultObject;
	
	public void setFaultObject(Object obj){
		this.faultObject = obj;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + String.valueOf(this.faultObject);
	}
	
}
