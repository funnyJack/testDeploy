package com.funnyjack.test.runner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Component
import javax.sql.DataSource

//apply extra sql script to database
@Component
@ConditionalOnClass(name = ["org.springframework.jdbc.datasource.init.ResourceDatabasePopulator"])
@DependsOnDatabaseInitialization
class InitExtraDataRunner(private val dataSource: DataSource) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        //should always not in prod env since this runner only used in test code
        val sqlFiles = listOf("init.sql")
            .map { ClassPathResource("/sql/$it") }
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScripts(*sqlFiles.toTypedArray())
        resourceDatabasePopulator.execute(dataSource)
    }
}
