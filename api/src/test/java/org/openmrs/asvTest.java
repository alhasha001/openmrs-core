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

	/**
	 * Active Patient's State Test
	 */
	/**
	 * inActive Patient's State Test
	 */
    //Arrange
	//patient's date info
	private Date start;
	private Date now;
	private Date end;
	private Date exceeded;

	@Before
	public void before() {
		now = new Date();
		start = new Date(now.getTime() - 3121212);
		end = new Date(now.getTime() + 3121212);
		exceeded = new Date(end.getTime() + 3121212);
	}

	
	@Test
	public void activePatient() {
		PatientState state = new PatientState();
		state.setStartDate(start);
		state.setEndDate(end);
		state.setVoided(false);
		//Act
		boolean isActive = state.getActive(now);
		//Assert
		Assert.assertTrue(isActive);
	}


	@Test
	public void inActivePatient() {
		//given
		PatientState patientState = new PatientState();
		patientState.setStartDate(start);
		patientState.setEndDate(end);
		patientState.setVoided(true);
		//when
		boolean isActive = patientState.getActive(exceeded);
		//then
		Assert.assertFalse(isActive);
	}

	
	/**
	 * Test adding names
	 * 
	 */
	@Test
	public void addName() {
		//Arrange
		Person p = new Person();
		PersonName pN = new PersonName();
		//Act
		pN.setGivenName("Jon");
		pN.setFamilyName("Wick");
		pN.setDateCreated(new Date());
		pN.setVoided(false);
		p.addName(pN);
		//Assert
		assertTrue("We must have 1 name : " + p.getNames().size(), p
		        .getNames().size() == 1);
    }
    

    /**
	 * Test removing names
	 * 
	 */
	@Test
	public void removeName() {
		//Arrange
		Person p = new Person();
		PersonName pN = new PersonName();
		//Act
		pN.setGivenName("Jon");
		pN.setFamilyName("Wick");
		pN.setDateCreated(new Date());
		pN.setVoided(false);
		p.addName(pN);
		//Assert
		// test removing the name
		p.removeName(pN);
		assertTrue("The person object should be empty", p.getNames().size() == 0);
	}
	


	/**
	 * Not saving empty patient's info Test
	 */
	@Test
	public void test_saving_empty_values() {
		//Arrange
		Person p = new Person();
		PersonAttribute pA = new PersonAttribute();
		//Act
		pA.setValue("");
		pA.setAttributeType(new PersonAttributeType(1));
		pA.setVoided(false);
		p.addAttribute(pA);
		// ensure that nothing was added
		//Assert
		Assert.assertEquals("There should not be any attributes", 0, p.getAttributes().size());
	}


    // Arrange
	private Person personConstructor(boolean isVoided, int t1, int t2, int t3, String name1, String name2, String name3, String att1, String att2, String att3) {
		Person person = new Person();

	 	PersonAttributeType type1 = new PersonAttributeType(t1);
	 	PersonAttributeType type2 = new PersonAttributeType(t2);
	 	PersonAttributeType type3 = new PersonAttributeType(t3);
	    
	 	type1.setName(name1);
	 	type2.setName(name2);
	 	type3.setName(name3);
	 	PersonAttribute attribute1 = new PersonAttribute(type1, att1);
	 	PersonAttribute attribute2 = new PersonAttribute(type2, att2);
	 	PersonAttribute attribute3 = new PersonAttribute(type3, att3);
	    
		 attribute1.setVoided(isVoided);
		 attribute2.setVoided(isVoided);
		 attribute3.setVoided(isVoided);

		person.addAttribute(attribute1);
		person.addAttribute(attribute2);
		person.addAttribute(attribute3);

		return person;
	}

    /**
	 * Finding the patient
	 */
	@Test
	public void find_person_based_on_attribute() {
		//Act
		Person person = personConstructor(false, 1, 2, 3, "John", "Van", "Wick", "attribute1", "attribute2", "attribute3");
		//Assert
		Assert.assertEquals("Wick", person.getAttribute("Wick").getAttributeType().getName());
	}


     /**
	 * Not finding a voided patient
	 */
	@Test
	public void cant_find_person_when_voided() {
		//Act
		Person person = personConstructor(true, 1, 2, 3, "John", "Van", "Wick", "attribute1", "attribute2", "attribute3");
		//Assert
		Assert.assertNull(person.getAttribute("Wick"));
	}


	/**
	 * Test the Location
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
