package ru.maxim.consuldemo;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyProperties {

    private String prop;

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

}
