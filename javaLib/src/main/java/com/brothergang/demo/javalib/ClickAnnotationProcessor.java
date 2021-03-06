package com.brothergang.demo.javalib;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by brothergang on 2017/8/7.
 */


@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ClickAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    /**
     * 所有的注解处理都是从这个方法开始的，
     * 你可以理解为，当 APT 找到所有需要处理的注解后，会回调这个方法，
     * 你可以通过这个方法的参数，拿到你所需要的信息。
     * @param annotations 获取此注解处理器索要处理的注解集合
     * @param roundEnv 访问到当前这个 Round 中的语法树节点，每个语法树节点在这里表示成一个 Element
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "=== process === ");
        //获取proxyMap
        Map<String, ProxyInfo> proxyMap = getProxyMap(roundEnv);

        //遍历proxyMap，并生成代码
        for (String key : proxyMap.keySet()) {
            ProxyInfo proxyInfo = proxyMap.get(key);
            writeCode(proxyInfo);
        }
        return true;
    }

    /**
     * 通过重写该方法，告知 processor 哪些注解需要处理
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ClickAnnotation.class.getCanonicalName());
        return types;
    }

    private Map<String, ProxyInfo> getProxyMap(RoundEnvironment roundEnv) {
        Map<String, ProxyInfo> proxyMap = new HashMap<>();
        //遍历每一个语法树节点
        for (Element element : roundEnv.getElementsAnnotatedWith(ClickAnnotation.class)) {
            //target相同只能强转。不同使用getEnclosingElement
            ExecutableElement executableElement = (ExecutableElement) element;
            TypeElement classElement = (TypeElement) element
                    .getEnclosingElement();

            PackageElement packageElement = elementUtils.getPackageOf(classElement);


            String fullClassName = classElement.getQualifiedName().toString();
            String className = classElement.getSimpleName().toString();
            String packageName = packageElement.getQualifiedName().toString();
            String methodName = executableElement.getSimpleName().toString();
            int viewId = executableElement.getAnnotation(ClickAnnotation.class).id();
            String strToast = executableElement.getAnnotation(ClickAnnotation.class).toast();

            print("fullClassName: "+ fullClassName +
                    ",  methodName: "+methodName +
                    ",  viewId: "+viewId);

            ClickMethod clickMethod = new ClickMethod(viewId, strToast, methodName, getMethodParameterTypes(executableElement));

            ProxyInfo proxyInfo = proxyMap.get(fullClassName);
            if (proxyInfo != null) {
                proxyInfo.addMethod(clickMethod);
            } else {
                proxyInfo = new ProxyInfo(packageName, className);
                proxyInfo.setTypeElement(classElement);
                proxyInfo.addMethod(clickMethod);
                proxyMap.put(fullClassName, proxyInfo);
            }
        }
        return proxyMap;
    }

    /**
     * 取得方法参数类型列表
     */
    private List<String> getMethodParameterTypes(ExecutableElement executableElement) {
        List<? extends VariableElement> methodParameters = executableElement.getParameters();
        if (methodParameters.size()==0){
            return null;
        }
        List<String> types = new ArrayList<>();
        for (VariableElement variableElement : methodParameters) {
            TypeMirror methodParameterType = variableElement.asType();
            if (methodParameterType instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) methodParameterType;
                methodParameterType = typeVariable.getUpperBound();
            }
            types.add(methodParameterType.toString());
        }
        return types;
    }

    private void writeCode(ProxyInfo proxyInfo) {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                    proxyInfo.getProxyClassFullName(),
                    proxyInfo.getTypeElement());
            Writer writer = jfo.openWriter();
            writer.write(proxyInfo.generateJavaCode());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            error(proxyInfo.getTypeElement(),
                    "Unable to write injector for type %s: %s",
                    proxyInfo.getTypeElement(), e.getMessage());
        } catch (ClickException e){
            error(proxyInfo.getTypeElement(),
                    "The use of irregular %s: %s",
                    proxyInfo.getTypeElement(), e.getMessage());
        }
    }

    private void print(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

}