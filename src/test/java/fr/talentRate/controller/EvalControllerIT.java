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
package fr.talentRate.controller;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.StudentDTO;

/**
 * @author djer1
 *
 */
//@RunWith(com.carrotsearch.randomizedtesting.RandomizedRunner.class)
//@ESIntegTestCase.ClusterScope(scope = ESIntegTestCase.Scope.SUITE)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EvalControllerIT {

    /**
     * Spring MVC Mock.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * To convert Object to Json.
     */
    @Autowired
    private ObjectMapper mapper;

    /**
     * Unit Test.
     * @throws Exception General errors.
     * @throws JsonProcessingException when Json Errors occurs.
     */
    @Test
    public void testCreateEval() throws JsonProcessingException, Exception {
        final Integer maxScore = 48;
        final Integer studentScore = 27;

        //1- prepare test conditions
        EvalDTO testEval = new EvalDTO();

        testEval.setCategory("Dev Testing");
        testEval.setEvalId(null);
        testEval.setGiven(new Date());
        testEval.setHomework("Devoir Unit test");
        testEval.setIsDone(null);
        testEval.setMessage(null);
        testEval.setModule("TEST");
        testEval.setObtainable(maxScore);
        testEval.setPromotion("The first one");
        testEval.setSchool("A school");
        testEval.setScore(studentScore);
        testEval.setSkill("Junit");

        StudentDTO newStudent = new StudentDTO("BOB gertrude");
        testEval.setStudent(newStudent);

        //DataBinder dataBinder = new DataBinder(null);
        //BindingResult br = dataBinder.getBindingResult();

        //2- test a method and Check
        mvc.perform(MockMvcRequestBuilders.post("/eval/").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testEval))).andExpect(MockMvcResultMatchers.status().isOk());

        //3- Check the result
        //        Boolean isOk = result.getIsDone();
        //        Assert.assertTrue("The Eval should be created !", isOk);

    }

}
