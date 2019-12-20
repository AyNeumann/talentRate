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

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import fr.talentRate.dao.repository.PromotionRepository;
import fr.talentRate.dto.plan.Promotion;

/**
 * Basic tests of entities used in relational DataBase from Promotions.
 * @author djer13
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PromotionRepositoryIT {

    /** To access other repositories not under tests.*/
    @Autowired
    private TestEntityManager entityManager;

    /** Manage Promotions.*/
    @Autowired
    private PromotionRepository promotionRepo;

    /** To get default Data.*/
    private DefaultDataHelper dataIntializer;

    /** Check Promotion creation.
     * @throws ParseException when Birth Date format is invalid */
    @Test
    public void testCreatePromotion() throws ParseException {
        dataIntializer = new DefaultDataHelper();
        dataIntializer.setEntityManager(entityManager);
        dataIntializer.initInstructor();
        dataIntializer.initSkills();
        dataIntializer.initLearningPath();
        //dataIntializer.initStudents();

        final int hocNumberOfStudent = 3;

        Promotion promoSaintEtienne2019 = new Promotion();
        promoSaintEtienne2019.setName("House of Code Saint-Etienne 2019");
        promoSaintEtienne2019.setEnrolled(DefaultDataHelper.HOC);
        promoSaintEtienne2019.addStudent(DefaultDataHelper.SEB);
        promoSaintEtienne2019.addStudent(DefaultDataHelper.MAT);
        promoSaintEtienne2019.addStudent(DefaultDataHelper.VI);

        Promotion createdPromotion = promotionRepo.save(promoSaintEtienne2019);

        Assert.assertEquals("Bad number of trainned Skills in Learning Path", createdPromotion.getStudents().size(),
                hocNumberOfStudent);

    }

}
