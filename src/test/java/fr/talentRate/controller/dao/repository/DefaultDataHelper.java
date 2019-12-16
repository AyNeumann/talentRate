/*
Copyright [2019] [Aymeric NEUMANN]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package fr.talentRate.controller.dao.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import fr.talentRate.dto.plan.Instructor;
import fr.talentRate.dto.plan.Skill;

/**
 * @author djer1
 *
 */
public class DefaultDataHelper {

    /** To access other repositories not under tests.*/
    @Autowired
    private TestEntityManager entityManager;

    /** Institute minimum HTML.*/
    private static final int INSTITUTE_MIN_HTML = 10;
    /** Institute intermediate HTML.*/
    private static final int INSTITUTE_INTERM_HTML = 60;
    /** Institute maximum HTML.*/
    private static final int INSTITUTE_MAX_HTML = 100;

    /** Institute minimum PHP.*/
    private static final int INSTITUTE_MIN_PHP = 20;
    /** Institute intermediate PHP.*/
    private static final int INSTITUTE_INTERM_PHP = 50;
    /** Institute maximum PHP.*/
    private static final int INSTITUTE_MAX_PHP = 300;

    /** Institute minimum Java.*/
    private static final int INSTITUTE_MIN_JAVA = 20;
    /** Institute intermediate Java.*/
    private static final int INSTITUTE_INTERM_JAVA = 50;
    /** Institute maximum Java.*/
    private static final int INSTITUTE_MAX_JAVA = 300;

    /** Default Multi-Skill instructor.*/
    public static final Instructor JEREMIE = new Instructor();
    /** Default PHP specialist instructor.*/
    public static final Instructor PROF2 = new Instructor();
    /** Default HTML specialist instructor.*/
    public static final Instructor PROF3 = new Instructor();

    /** The Java Skill, available for all tests.*/
    public static final Skill JAVA = new Skill();
    /** The PHP Skill, available for all tests.*/
    public static final Skill PHP = new Skill();
    /** The HTML Skill, available for all tests.*/
    public static final Skill HTML = new Skill();

    /**
     * Initialize default Data required for tests.
     * @throws ParseException if birth Dates are invalids
     */
    public void initData() throws ParseException {

        DateFormat birthDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        JEREMIE.setName("DERUETTE");
        JEREMIE.setFirstName("Jérémie");
        JEREMIE.setBirthdate(birthDateFormatter.parse("1983-05-13"));

        PROF2.setName("BeatGrinder");
        PROF2.setFirstName("prénom1");
        PROF2.setBirthdate(birthDateFormatter.parse("1978-12-24"));

        PROF3.setName("AN OTHER NAME");
        PROF3.setFirstName("prénom-composé 2");
        PROF3.setBirthdate(birthDateFormatter.parse("1990-02-01"));

        JAVA.setName("JAVA");
        JAVA.setDescription("The best language !!");
        JAVA.setMinimumThreshold(INSTITUTE_MIN_JAVA);
        JAVA.setIntermediaryThreshold(INSTITUTE_INTERM_JAVA);
        JAVA.setMaximumThreshold(INSTITUTE_MAX_JAVA);
        JAVA.addInstructor(JEREMIE);

        PHP.setName("PHP");
        PHP.setDescription("An other progaming language");
        PHP.setMinimumThreshold(INSTITUTE_MIN_PHP);
        PHP.setIntermediaryThreshold(INSTITUTE_INTERM_PHP);
        PHP.setMaximumThreshold(INSTITUTE_MAX_PHP);
        PHP.addInstructor(PROF2);
        PHP.addInstructor(JEREMIE);

        HTML.setName("HTML");
        HTML.setDescription("The Web descirpion language");
        HTML.setMinimumThreshold(INSTITUTE_MIN_HTML);
        HTML.setIntermediaryThreshold(INSTITUTE_INTERM_HTML);
        HTML.setMaximumThreshold(INSTITUTE_MAX_HTML);
        HTML.addInstructor(PROF3);
        HTML.addInstructor(PROF2);
        HTML.addInstructor(JEREMIE);

        entityManager.persist(JAVA);
        entityManager.persist(PHP);
        entityManager.persist(HTML);

        entityManager.flush();

    }

}
