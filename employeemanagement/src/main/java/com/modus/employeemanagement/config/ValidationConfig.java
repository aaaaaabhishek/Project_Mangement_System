package com.modus.employeemanagement.config;

//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//@Configuration
//public class ValidationConfig {
//
//    // Load messages.properties for validation messages
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages"); // No .properties extension
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//
//    // Connect validation system to MessageSource
//    @Bean
//    public LocalValidatorFactoryBean getValidator() {
//        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//        bean.setValidationMessageSource(messageSource());
//        return bean;
//    }
//}
