package com.tongji.softwaretest.Entity;

import java.util.List;
import java.util.Map;

public class ClassInfo {
    private String class_name;
    private Map<String,List<String>> methods;

    public List<String> getTest_cases() {
        return test_cases;
    }

    public void setTest_cases(List<String> test_cases) {
        this.test_cases = test_cases;
    }

    private List<String> test_cases;
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Map<String, List<String>> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, List<String>> methods) {
        this.methods = methods;
    }
}
