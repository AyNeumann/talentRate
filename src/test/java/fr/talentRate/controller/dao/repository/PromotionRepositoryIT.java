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
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    /** Manage Learning Path.*/
    @Autowired
    private PromotionRepository promotionRepo;

    /** To get default Data.*/
    private DefaultDataHelper dataIntializer = new DefaultDataHelper();

    /**
     * Initialize default Data required for tests.
     * @throws ParseException if birth Dates are invalids
     */
    @BeforeClass
    public void initData() throws ParseException {
        dataIntializer.initInstructor();
        dataIntializer.initSkills();
        dataIntializer.initLearningPath();
        //dataIntializer.initStudents();
    }

    /** Check Promotion creation. */
    @Test
    public void testCreatePromotion() {
        final int hocNumberOfStudent = 3;

        Promotion promoSaintEtienne2019 = new Promotion();
        promoSaintEtienne2019.setName("House of Code Saint-Etienne 2019");
        promoSaintEtienne2019.addStudent(DefaultDataHelper.SEB);
        promoSaintEtienne2019.addStudent(DefaultDataHelper.MAT);
        promoSaintEtienne2019.addStudent(DefaultDataHelper.VI);

        promotionRepo.save(promoSaintEtienne2019);

        Assert.assertEquals("Bad number of trainned Skills in Learning Path",
                promoSaintEtienne2019.getStudents().size(), hocNumberOfStudent);

    }

}
