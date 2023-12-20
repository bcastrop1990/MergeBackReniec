package pe.gob.reniec.rrcc.plataformaelectronica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pe.gob.reniec.rrcc.plataformaelectronica.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Api Plataforma Electronica")
                .description("Esta plataforma permite canalizar la presentacion de solicitudes de registro de firma " +
                        "y solicitud de requerimiento de libro de actas registrales a fin de automatizar y optimizar el tiempo en la presentacion de los documentos")
                .version("1.0")
                .termsOfServiceUrl("Terminos del servicio")
                .contact(new Contact("Reniec", "https://www.reniec.gob.pe/", "admin@reniec.gob.pe"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}
