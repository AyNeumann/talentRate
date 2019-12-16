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

import fr.talentRate.dao.repository.LearningPathRepository;
import fr.talentRate.dto.plan.LearningPath;
import fr.talentRate.dto.plan.Promotion;

/**
 * Basic test of entity used in relational DataBase.
 * @author djer13
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RelationnalDataBaseIT {

    /** Manage Learning Path.*/
    @Autowired
    private LearningPathRepository learningPathRepo;

    /** To get default Data.*/
    private DefaultDataHelper dataIntializer = new DefaultDataHelper();

    /**
     * Initialize default Data required for tests.
     * @throws ParseException if birth Dates are invalids
     */
    @BeforeClass
    public void initData() throws ParseException {
        dataIntializer.initData();
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
        houseOfCode.addSkill(DefaultDataHelper.JAVA, hocJavaThreshold);
        houseOfCode.addSkill(DefaultDataHelper.PHP, hocPhpThreshold);
        houseOfCode.addSkill(DefaultDataHelper.HTML, hocHtmlThreshold);

        Promotion promoSaintEtienne2019 = new Promotion();
        promoSaintEtienne2019.setName("House of Code Saint-Etienne 2019");
        promoSaintEtienne2019.setEnrolled(houseOfCode);

        learningPathRepo.save(houseOfCode);

        Assert.assertEquals("Bad number of trainned Skills in Learning Path", houseOfCode.getTrained().size(),
                hocNumberofSkills);

    }

}
