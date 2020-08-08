package com.cards.config;

import com.cards.formatter.BankApprovalConverter;
import com.cards.formatter.CardsConverter;
import com.cards.formatter.ClientsConverter;
import com.cards.formatter.EmployeesConverter;
import com.cards.formatter.LocalDateConverter;
import com.cards.formatter.RequestsConverter;
import com.cards.service.BankApprovalService;
import com.cards.service.CardsService;
import com.cards.service.ClientsService;
import com.cards.service.EmployeesService;
import com.cards.service.RequestsService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cards.controller, com.cards.service, com.cards.dao, com.cards.dto, "
                            + "com.cards.rest, com.cards.rest.resource"
               , basePackageClasses = {com.cards.dto.mapint.class})
@PropertySource("classpath:db/jdbc.properties")
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {
    //needed for propertySource
    @Autowired
    private Environment env;
    //services needed for formatters
    @Autowired
    @Qualifier("BA_SERV")
    BankApprovalService bas;
    
    @Autowired
    @Qualifier("CRD_SERV")
    CardsService cds;
    
    @Autowired
    @Qualifier("CLI_SERV")
    ClientsService cls;
    
    @Autowired
    @Qualifier("EMP_SERV")
    EmployeesService ems;
    
    @Autowired
    @Qualifier("REQ_SERV")
    RequestsService res;
    
    //resouces for different purposes
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
            .addResourceLocations("/WEB-INF/jslib/");
        registry.addResourceHandler("/img/**")
            .addResourceLocations("/WEB-INF/images/");
    }
    
    //static view controllers for specific path
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index");
        registry.addViewController("/header").setViewName("header");
    }
    
    //resolve returned strings into jsp
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }
    
    //i18n messages
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:i18n/messages");
        return bean;
    }
    
    //intercepts every request and calls localeResolver and its setLocale method
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor bean = new LocaleChangeInterceptor();
        bean.setParamName("lang"); 
        return bean;
    }
    
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());//used for custom messages during validation
        return bean;
    }
    
    //set locale in different ways (cookie, httpsession,...)
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver bean = new SessionLocaleResolver();
        bean.setDefaultLocale(Locale.US);
        return bean;
    }
        
    @Override
    public Validator getValidator() {
        return validator();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    //datatype conversion
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new BankApprovalConverter(bas));
        registry.addFormatter(new CardsConverter(cds));
        registry.addFormatter(new ClientsConverter(cls));
        registry.addFormatter(new EmployeesConverter(ems));
        registry.addFormatter(new RequestsConverter(res));
        registry.addFormatter(new LocalDateConverter("dd/MM/yyyy"));
    }

    //////////////
    //JPA config//
    //////////////
    
    @Bean
    public DriverManagerDataSource datasource(){
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        bean.setUsername(env.getProperty("jdbc.username"));
        bean.setPassword(env.getProperty("jdbc.password"));
        bean.setUrl(env.getProperty("jdbc.url"));
        return bean;
    }
    
    @Bean
    public HibernateJpaVendorAdapter vendor(){
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setGenerateDdl(false);
        bean.setShowSql(true);
        return bean;
    }

    //defines entity manager factory
    @Bean
    public LocalContainerEntityManagerFactoryBean emf(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(datasource());
        bean.setJpaVendorAdapter(vendor());
        bean.setPackagesToScan("com.cards.entity");
        return bean;
    }    
    
    //manages transaction
    @Bean
    public JpaTransactionManager trMan(){
        JpaTransactionManager bean = new JpaTransactionManager();
        bean.setEntityManagerFactory(emf().getObject());
        return bean;
    }    
}
