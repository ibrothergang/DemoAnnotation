package com.brothergang.demo.javalib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.TypeElement;

/**
 * Created by brothergang on 2017/8/7.
 */

public class ProxyInfo {
    private String packageName;
    private String targetClassName;
    private String proxyClassName;
    private TypeElement typeElement;
    private List<ClickMethod> methods;
    public static final String PROXY = "PROXY";

    ProxyInfo(String packageName, String className) {
        this.packageName = packageName;
        this.targetClassName = className;
        this.proxyClassName = className + "$$" + "PROXY";
    }

    String getProxyClassFullName() {
        return this.packageName + "." + this.proxyClassName;
    }

    String generateJavaCode() throws ClickException {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from ClickAnnotation. Do not modify!\n");
        builder.append("package ").append(this.packageName).append(";\n\n");
        builder.append("import android.view.View;\n");
        builder.append("import android.util.Log;\n");
        builder.append("import android.widget.Toast;\n");
        builder.append("import com.brothergang.demo.module.click.Finder;\n");
        builder.append("import com.brothergang.demo.module.click.AbstractInjector;\n");
        builder.append('\n');
        builder.append("public class ").append(this.proxyClassName);
        builder.append("<T extends ").append(this.getTargetClassName()).append(">");
        builder.append(" implements AbstractInjector<T>");
        builder.append(" {\n");
        this.generateInjectMethod(builder);
        builder.append('\n');
        builder.append("}\n");
        return builder.toString();
    }

    private String getTargetClassName() {
        return this.targetClassName.replace("$", ".");
    }

    private void generateInjectMethod(StringBuilder builder) throws ClickException {
        builder.append("public long intervalTime; \n");
        builder.append("  @Override ").append("public void setIntervalTime(long time) {\n").append("intervalTime = time;\n     } \n");
        builder.append("  @Override ").append("public void inject(final Finder finder, final T target, Object source) {\n");
        builder.append("View view;");
        

        for(Iterator var2 = this.getMethods().iterator(); var2.hasNext(); builder.append("\n}").append("    }\n").append("        });\n}")) {
            ClickMethod method = (ClickMethod)var2.next();
            builder.append("    view = ").append("finder.findViewById(source, ").append(method.getId()).append(");\n");
            builder.append("if(view != null){").append("view.setOnClickListener(new View.OnClickListener() {\n").append("long time = 0L;");
            builder.append("@Override\n").append("public void onClick(View v) {");
            builder.append("Log.d(\"generateInjectMethod\", \"=== Clicked ===\");\n");
            builder.append("long temp = System.currentTimeMillis();\n").append("if (temp - time >= intervalTime) {\ntime = temp;\n");
            if(method.getMethodParametersSize() == 1) {
                if(!((String)method.getMethodParameters().get(0)).equals("android.view.View")) {
                    throw new ClickException("Parameters must be android.view.View");
                }

                builder.append("target.").append(method.getMethodName()).append("(v);");
            } else {
                if(method.getMethodParametersSize() != 0) {
                    throw new ClickException("Does not support more than one parameter");
                }

                builder.append("target.").append(method.getMethodName()).append("();");
            }
        }

        builder.append("  }\n");
    }

    TypeElement getTypeElement() {
        return this.typeElement;
    }

    void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    List<ClickMethod> getMethods() {
        return (List)(this.methods == null?new ArrayList():this.methods);
    }

    void addMethod(ClickMethod onceMethod) {
        if(this.methods == null) {
            this.methods = new ArrayList();
        }

        this.methods.add(onceMethod);
    }
}
