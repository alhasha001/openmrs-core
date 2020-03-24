
package org.openmrs.notification;

import java.util.Date;
import org.openmrs.User;

class BaseMethods {
	private User creator;
	private Date dateCreated;
	
	/**
	 * @return User - the user who created the object
	 */
	public User getCreator() {
        return this.creator;
    }
	
	/**
	 * @param creator - the user who created the object
	 */
	public void setCreator(User creator){
        this.creator = creator;
    }
	
	/**
	 * @return Date - the date the object was created
	 */
	public Date getDateCreated() {
        return this.dateCreated;
    }
	
	/**
	 * @param dateCreated - the date the object was created
	 */
	public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }
}
