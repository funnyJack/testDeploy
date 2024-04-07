package com.hkmci.web.bms2.test.initializer

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.util.ClassUtils
import org.testcontainers.containers.MySQLContainer

class MysqlContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        private val mysqlContainer: MySQLContainer<*> by lazy {
            MySQLContainer("mysql:8")
                .withDatabaseName("testDeploy")
                .withUrlParam("useUnicode", "true")
                .withUrlParam("characterEncoding", "UTF-8")
        }
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        //initializer doesn't affect by @ConditionalOnClass annotation
        if (!ClassUtils.isPresent(
                "org.testcontainers.containers.MySQLContainer",
                configurableApplicationContext.classLoader
            )
        ) {
            return  //skip this initializer if MySQLContainer not in class path
        }

        //docker run
        mysqlContainer.start()

        TestPropertyValues.of(
            "spring.datasource.url=${mysqlContainer.jdbcUrl}",
            "spring.datasource.username=${mysqlContainer.username}",
            "spring.datasource.password=${mysqlContainer.password}",
        ).applyTo(configurableApplicationContext.environment)
    }
}
