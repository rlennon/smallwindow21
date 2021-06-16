package ie.lyit.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ie.lyit.app");

        noClasses()
            .that()
            .resideInAnyPackage("ie.lyit.app.service..")
            .or()
            .resideInAnyPackage("ie.lyit.app.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ie.lyit.app.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
