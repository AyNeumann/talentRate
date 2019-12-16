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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import fr.talentRate.dao.repository.LearningPathRepository;
import fr.talentRate.dto.plan.Instructor;
import fr.talentRate.dto.plan.LearningPath;
import fr.talentRate.dto.plan.Promotion;
import fr.talentRate.dto.plan.Skill;

/**
 * Basic test of entity used in relational DataBase.
 * @author djer13
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RelationnalDataBaseIT {

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

    /** To access other repositories not under tests.*/
    @Autowired
    private TestEntityManager entityManager;

    /** Manage Learning Path.*/
    @Autowired
    private LearningPathRepository learningPathRepo;

    /** Default Multi-Skill instructor.*/
    private Instructor jeremie = new Instructor();
    /** Default PHP specialist instructor.*/
    private Instructor prof2 = new Instructor();
    /** Default HTML specialist instructor.*/
    private Instructor prof3 = new Instructor();

    /** The Java Skill, available for all tests.*/
    private Skill java = new Skill();
    /** The PHP Skill, available for all tests.*/
    private Skill php = new Skill();
    /** The HTML Skill, available for all tests.*/
    private Skill html = new Skill();

    /** Initialize default Data required for tests.*/
    @BeforeClass
    public void initData() throws ParseException {

        DateFormat birthDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        jeremie.setName("DERUETTE");
        jeremie.setFirstName("Jérémie");
        jeremie.setBirthdate(birthDateFormatter.parse("1983-05-13"));

        prof2.setName("BeatGrinder");
        prof2.setFirstName("prénom1");
        prof2.setBirthdate(birthDateFormatter.parse("1978-12-24"));

        prof3.setName("AN OTHER NAME");
        prof3.setFirstName("prénom-composé 2");
        prof3.setBirthdate(birthDateFormatter.parse("1990-02-01"));

        java.setName("JAVA");
        java.setDescription("The best language !!");
        java.setMinimumThreshold(INSTITUTE_MIN_JAVA);
        java.setIntermediaryThreshold(INSTITUTE_INTERM_JAVA);
        java.setMaximumThreshold(INSTITUTE_MAX_JAVA);
        java.addInstructor(jeremie);

        php.setName("PHP");
        php.setDescription("An other progaming language");
        php.setMinimumThreshold(INSTITUTE_MIN_PHP);
        php.setIntermediaryThreshold(INSTITUTE_INTERM_PHP);
        php.setMaximumThreshold(INSTITUTE_MAX_PHP);
        php.addInstructor(prof2);
        php.addInstructor(jeremie);

        html.setName("HTML");
        html.setDescription("The Web descirpion language");
        html.setMinimumThreshold(INSTITUTE_MIN_HTML);
        html.setIntermediaryThreshold(INSTITUTE_INTERM_HTML);
        html.setMaximumThreshold(INSTITUTE_MAX_HTML);
        html.addInstructor(prof3);
        html.addInstructor(prof2);
        html.addInstructor(jeremie);

        entityManager.persist(java);
        entityManager.persist(php);
        entityManager.persist(html);

        entityManager.flush();

    }

    /**
     * Check creation a LearningPath with trainable Skills.
     */
    @Test
    public void testCreateLearningPath() {

        final Integer hocJavaThreshold = 120;
        final Integer hocPhpThreshold = 30;
        final Integer hocHtmlThreshold = 10;

        final int hocNumberofSkills = 3;

        LearningPath houseOfCode = new LearningPath();
        houseOfCode.setName("House of Code");
        houseOfCode.addSkill(java, hocJavaThreshold);
        houseOfCode.addSkill(php, hocPhpThreshold);
        houseOfCode.addSkill(html, hocHtmlThreshold);

        Promotion promoSaintEtienne2019 = new Promotion();
        promoSaintEtienne2019.setName("House of Code Saint-Etienne 2019");
        promoSaintEtienne2019.setEnrolled(houseOfCode);

        learningPathRepo.save(houseOfCode);

        Assert.assertEquals("Bad number of trainned Skills in Learning Path", houseOfCode.getTrained().size(),
                hocNumberofSkills);

    }

}
