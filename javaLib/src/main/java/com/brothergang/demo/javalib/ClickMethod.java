package com.brothergang.demo.javalib;

import java.util.List;

/**
 * Created by brothergang on 2017/8/7.
 */

public class ClickMethod {
    private int id;
    private String strToast;
    private String methodName;
    private List<String> methodParameters;

    ClickMethod(int id, String toast, String methodName, List<String> methodParameters) {
        this.id = id;
        this.strToast = toast;
        this.methodName = methodName;
        this.methodParameters = methodParameters;
    }

    int getMethodParametersSize() {
        return this.methodParameters == null?0:this.methodParameters.size();
    }

    int getId() {
        return this.id;
    }

    String getToast(){
        return strToast;
    }

    String getMethodName() {
        return this.methodName;
    }

    List<String> getMethodParameters() {
        return this.methodParameters;
    }
}
