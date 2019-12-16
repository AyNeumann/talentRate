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

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Service;

import fr.talentRate.dto.plan.Instructor;
import fr.talentRate.dto.plan.LearningPath;
import fr.talentRate.dto.plan.Promotion;
import fr.talentRate.dto.plan.Skill;
import fr.talentRate.dto.plan.Student;

/**
 * @author djer13
 */
@Service
public class DefaultDataHelper {

    /** Default Birth Date formatter. */
    private DateFormat birthDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /** local EntityManager to manage data. */
    private TestEntityManager entityManager;;

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

    /** House of Code Learning Path. */
    public static final LearningPath HOC = new LearningPath();
    /** Advanced Java Learning path. */
    public static final LearningPath JAVA_ADVANCED = new LearningPath();

    /** Saint-Etiennes's House of Code first Promotion.*/
    public static final Promotion HOC_STE_S1 = new Promotion();
    /** Saint-Etiennes's House of Code second Promotion.*/
    public static final Promotion HOC_STE_S2 = new Promotion();
    /** Saint-Etiennes's Advanced Java eighth Promotion.*/
    public static final Promotion JAVA_ADVANCED_STE_S8 = new Promotion();

    /** A medium Student. */
    public static final Student SEB = new Student();
    /** A tenacious Student. */
    public static final Student VI = new Student();
    /** A good Student. */
    public static final Student MAT = new Student();
    /** A Lazy student.*/
    public static final Student SLACKER = new Student();

    /**
     * Create an instance.
     * @param em EntityManager to manage data
     */
    public DefaultDataHelper(final TestEntityManager em) {
        this.entityManager = em;
    }

    /**
     * Initialize default Data required for tests (instructors).
     * @throws ParseException if birth Dates are invalids
     */
    public void initInstructor() throws ParseException {

        JEREMIE.setName("DERUETTE");
        JEREMIE.setFirstName("Jérémie");
        JEREMIE.setBirthdate(birthDateFormatter.parse("1983-05-13"));

        PROF2.setName("BeatGrinder");
        PROF2.setFirstName("prénom1");
        PROF2.setBirthdate(birthDateFormatter.parse("1978-12-24"));

        PROF3.setName("AN OTHER NAME");
        PROF3.setFirstName("prénom-composé 2");
        PROF3.setBirthdate(birthDateFormatter.parse("1990-02-01"));

        entityManager.persist(JEREMIE);
        entityManager.persist(PROF2);
        entityManager.persist(PROF3);

        entityManager.flush();

    }

    /**
     * Initialize default Data required for tests (Skills). **Always** create instructors First !
     */
    public void initSkills() {

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

    /**
     * Initialize default Data required for tests (Learning path).
     */
    public void initLearningPath() {

        final Integer javaAdvancedThreshold = 60;

        final Integer hocJavaThreshold = 120;
        final Integer hocPhpThreshold = 30;
        final Integer hocHtmlThreshold = 10;

        JAVA_ADVANCED.setName("Java avancée");
        JAVA_ADVANCED.addSkill(JAVA, javaAdvancedThreshold);

        HOC.setName("House of Code");
        HOC.addSkill(DefaultDataHelper.JAVA, hocJavaThreshold);
        HOC.addSkill(DefaultDataHelper.PHP, hocPhpThreshold);
        HOC.addSkill(DefaultDataHelper.HTML, hocHtmlThreshold);

        entityManager.persist(JAVA_ADVANCED);
        entityManager.persist(HOC);

        entityManager.flush();

    }

    /**
     * Initialize default Data required for tests (promotions). **Always** create LearningPath First !
     */
    public void initPromotions() {

        HOC_STE_S1.setName("House of Code Saint-Etienne Saison 1");
        HOC_STE_S1.setEnrolled(HOC);

        HOC_STE_S2.setName("House of Code Saint-Etienne Saison 2");
        HOC_STE_S2.setEnrolled(HOC);

        JAVA_ADVANCED_STE_S8.setName("Java avancées 2019 Q2");
        JAVA_ADVANCED_STE_S8.setEnrolled(JAVA_ADVANCED);

        entityManager.persist(HOC_STE_S1);
        entityManager.persist(HOC_STE_S1);
        entityManager.persist(JAVA_ADVANCED_STE_S8);

        entityManager.flush();

    }

    /**
     * Initialize default Data required for tests (Student). **Always** create Promotions First !
     * @throws ParseException if birth Dates are invalids
     */
    public void initStudents() throws ParseException {

        SEB.setName("SEB");
        SEB.setFirstName("Sébastien");
        SEB.setBirthdate(birthDateFormatter.parse("1987-04-17"));
        SEB.addPromotion(HOC_STE_S1);

        VI.setName("VI");
        VI.setFirstName("Virginie");
        VI.setBirthdate(birthDateFormatter.parse("1975-01-15"));
        VI.addPromotion(HOC_STE_S1);
        VI.addPromotion(JAVA_ADVANCED_STE_S8);

        MAT.setName("MAT");
        MAT.setFirstName("Mathieu");
        MAT.setBirthdate(birthDateFormatter.parse("1984-03-02"));
        MAT.addPromotion(HOC_STE_S1);

        SLACKER.setName("SLCAKER");
        SLACKER.setFirstName("Lazy");
        SLACKER.setBirthdate(birthDateFormatter.parse("1995-05-01"));

        entityManager.persist(SEB);
        entityManager.persist(VI);
        entityManager.persist(MAT);
        entityManager.persist(SLACKER);

        entityManager.flush();
    }

}
