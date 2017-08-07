package com.brothergang.demo.javalib;

import java.util.List;

/**
 * Created by brothergang on 2017/8/7.
 */

public class ClickMethod {
    private int id;
    private String methodName;
    private List<String> methodParameters;

    ClickMethod(int id, String methodName, List<String> methodParameters) {
        this.id = id;
        this.methodName = methodName;
        this.methodParameters = methodParameters;
    }

    int getMethodParametersSize() {
        return this.methodParameters == null?0:this.methodParameters.size();
    }

    int getId() {
        return this.id;
    }

    String getMethodName() {
        return this.methodName;
    }

    List<String> getMethodParameters() {
        return this.methodParameters;
    }
}
