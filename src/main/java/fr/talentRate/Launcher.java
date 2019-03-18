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
package fr.talentRate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author Aymeric
 *
 */
@SpringBootApplication
public class Launcher {

    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /**
     * @param args command line args
     */
    public static void main(final String[] args) {

        new SpringApplicationBuilder(Launcher.class).web(WebApplicationType.SERVLET).run(args);

    }

    /**
     * Reference to EvalController.
     */
    //    @Autowired
    //    private EvalService evServ;

    /**
     * Entry Point.
     * @param ctx command line application context
     * @return an configured command line runner
     */
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return args -> {
            //            LOG.info("Démarage de l'appli");
            //
            //            evServ.create("TestSchool2", "Java");
            //
            //            LOG.info("Fin de l'appli");
        };

    }

}
