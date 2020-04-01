package org.openmrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.test.BaseContextSensitiveTest;
import org.junit.Before;

/**
 *This class contains tests
 */
public class asvTest extends BaseContextSensitiveTest {

	//patient's date info
	private Date leftRange;
	private Date inRange;
	private Date rightRange;
	private Date rightOutOfRange;
	private Date leftOutOfRange;

	@Before
	public void before() {
		inRange = new Date();
		leftRange = new Date(inRange.getTime() - 10000);
		rightRange = new Date(inRange.getTime() + 10000);
		rightOutOfRange = new Date(rightRange.getTime() + 10000);
		leftOutOfRange = new Date(leftRange.getTime() - 10000);
	}

	/**
	 * @see PatientState#getActive(Date)
	 */
	@Test
	public void getActive_shouldReturnTrueIfNotVoidedAndDateInRange() {
		//given
		PatientState patientState = new PatientState();
		patientState.setStartDate(leftRange);
		patientState.setEndDate(rightRange);
		patientState.setVoided(false);
		
		//when
		boolean active = patientState.getActive(inRange);
		
		//then
		Assert.assertTrue(active);
	}

	/**
	 * @see PatientState#getActive(Date)
	 */
	@Test
	public void getActive_shouldReturnFalseIfVoidedAndDateNotInRange() {
		//given
		PatientState patientState = new PatientState();
		patientState.setStartDate(leftRange);
		patientState.setEndDate(rightRange);
		patientState.setVoided(true);
		
		//when
		boolean active = patientState.getActive(rightOutOfRange);
		
		//then
		Assert.assertFalse(active);
	}

	
	/**
	 * Test the adding names
	 * 
	 * @throws Exception
	 */
	@Test
	public void addName() {
		
		Person p = new Person();
		
		assertNotNull(p.getNames());
		
		PersonName pa1 = new PersonName();
		
		pa1.setGivenName("Jon");
		pa1.setFamilyName("Wick");
		pa1.setDateCreated(new Date());
		pa1.setVoided(false);
		p.addName(pa1);
		
		// make sure the name is added.
		assertTrue("The person object must contain 1 name : " + p.getNames().size(), p
		        .getNames().size() == 1);
    }
    

    /**
	 * Test the removing of a name
	 * 
	 * @throws Exception
	 */
	@Test
	public void removeName() {
		
		Person p = new Person();
		
		assertNotNull(p.getNames());
		
		PersonName pa1 = new PersonName();
		
		pa1.setGivenName("firsttest");
		pa1.setFamilyName("firsttest2");
		pa1.setDateCreated(new Date());
		pa1.setVoided(false);
		p.addName(pa1);
		
		// make sure the name is added.
		assertTrue("The person object must contain 1 name : " + p.getNames().size(), p
		        .getNames().size() == 1);
		
		// test removing the name
		p.removeName(pa1);
		assertTrue("The person object should be empty", p.getNames().size() == 0);
	}
	


//About the patient's vitals SC 

	/**
	 * @see Person#addAttribute(PersonAttribute)
	 */
	@Test
	public void test_saving_empty_values() {
		Person p = new Person();
		
		// make sure there are no initial attributes
	    //TODO: might delete the first assert method 
	//	Assert.assertEquals("There should not be any attributes", 0, p.getAttributes().size());
		
		PersonAttribute pa1 = new PersonAttribute();
		pa1.setValue("");
		pa1.setAttributeType(new PersonAttributeType(1));
		pa1.setVoided(false);
		p.addAttribute(pa1);
		
		// make sure the attribute was not added
		Assert.assertEquals("There should not be any attributes", 0, p.getAttributes().size());
	}


	private Person personHelper(boolean isVoid, int attributeType1, int attributeType2, int attributeType3, String attributeName1, String attributeName2, String attributeName3, String attributeValue1, String attributeValue2, String attributeValue3) {
		Person person = new Person();

	 	PersonAttributeType type1 = new PersonAttributeType(attributeType1);
	 	PersonAttributeType type2 = new PersonAttributeType(attributeType2);
	 	PersonAttributeType type3 = new PersonAttributeType(attributeType3);
	    
	 	type1.setName(attributeName1);
	 	type2.setName(attributeName2);
	 	type3.setName(attributeName3);
	 	PersonAttribute personAttribute1 = new PersonAttribute(type1, attributeValue1);
	 	PersonAttribute personAttribute2 = new PersonAttribute(type2, attributeValue2);
	 	PersonAttribute personAttribute3 = new PersonAttribute(type3, attributeValue3);
	    
		personAttribute1.setVoided(isVoid);
		personAttribute2.setVoided(isVoid);
		personAttribute3.setVoided(isVoid);

		person.addAttribute(personAttribute1);
		person.addAttribute(personAttribute2);
		person.addAttribute(personAttribute3);

		return person;
	}


	//This is for finding the patient based on an attribute such as name
/**
	 * @see Person#getAttribute(String)
	 */
	@Test
	public void find_person_based_on_attribute() {
		Person person = personHelper(false, 1, 2, 3, "name1", "name2", "name3", "value1", "value2", "value3");
		Assert.assertEquals("name3", person.getAttribute("name3").getAttributeType().getName());
	}


/**
	 * @see Person#getAttribute(String)
	 */
	@Test
	public void cant_find_person_when_voided() {
		Person person = personHelper(true, 1, 2, 3, "name1", "name2", "name3", "value1", "value2", "value3");
		Assert.assertNull(person.getAttribute("name3"));
	}



	//location test
	/**
	 * 
	 * 
	 * 
	 */
	@Test
	public void test_location() {
		//Arrange
		Location mainHospitalBuilding = new Location();
		Location branchOfHospital = new Location();
		//Act
		mainHospitalBuilding.addChildLocation(branchOfHospital);
		//Assert
		assertTrue(Location.isInHierarchy(branchOfHospital, mainHospitalBuilding));
	}

}
