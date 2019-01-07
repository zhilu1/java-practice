package com.practice.management.config;

import com.practice.management.domain.SysRole;
import com.practice.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.text.ParseException;
import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    //Formatters
    public class RoleFormatter implements Formatter<SysRole> {

        @Autowired
        RoleService roleService;

        @Override
        public String print(SysRole object, Locale locale) {
            return (object != null ? object.getId().toString() : "");
        }

        @Override
        public SysRole parse(String text, Locale locale) throws ParseException {
            Integer id = Integer.valueOf(text);
            return this.roleService.getRoleById(id);//return Type object form DB
        }
    }

    private RoleFormatter typeFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(typeFormatter);
    }
}
