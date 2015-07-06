/**
 * 
 */
package app.beans;

import java.io.Serializable;

/**
 * @author svensson
 *
 */
public class DirectorId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6232008997276586195L;
	
	private String directorName;
	private String directorId;
	

	/**
	 * 
	 */
	public DirectorId() {
		// TODO Auto-generated constructor stub
	}


	public DirectorId(String directorId, String directorName) {
		this.directorId = directorId;
		this.directorName = directorName;
	}


	/**
	 * @return the directorName
	 */
	public String getDirectorName() {
		return directorName;
	}


	/**
	 * @param directorName the directorName to set
	 */
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}


	/**
	 * @return the directorId
	 */
	public String getDirectorId() {
		return directorId;
	}


	/**
	 * @param directorId the directorId to set
	 */
	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}

}
