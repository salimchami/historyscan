package io.sch.historyscan.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class DomainArchitectureTest {
    @Test
    void prod_code_domain_should_not_depend_on_other_package_except_domain() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("io.sch.historyscan.domain");

        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideOutsideOfPackages(
                        "..domain..",
                        "..java.util..",
                        "..java.io..",
                        "..java.nio..",
                        "..java.lang..",
                        "..java.time..",
                        "..java.math.."
                );
        rule.check(importedClasses);
    }

    @Test
    void all_domain_code_should_not_depend_on_other_package_except_domain() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("io.sch.historyscan.domain");

        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideOutsideOfPackages(
                        "..domain..",
                        "..java.util..",
                        "..java.io..",
                        "..java.nio..",
                        "..java.lang..",
                        "..java.time..",
                        "..java.math..",
                        // test packages
                        "..io.sch.historyscan.fake..",
                        "..org.junit.jupiter..",
                        "..org.assertj.core..",
                        "..org.springframework.core.io..",
                        "..com.fasterxml.jackson..",
                        "..historyscan.common.."
                );
        rule.check(importedClasses);
    }
}
