package com.emi.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.emi.example");

        noClasses()
            .that()
                .resideInAnyPackage("com.emi.example.service..")
            .or()
                .resideInAnyPackage("com.emi.example.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.emi.example.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
