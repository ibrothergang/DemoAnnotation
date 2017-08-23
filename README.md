APT
APT(Annotation Processing Tool)是一种处理注释的工具,它对源代码文件进行检测找出其中的Annotation，使用Annotation进行额外的处理。
Annotation处理器在处理Annotation时可以根据源文件中的Annotation生成额外的源文件和其它的文件(文件具体内容由Annotation处理器的编写者决定),APT还会编译生成的源文件和原来的源文件，将它们一起生成class文件。

在 JDK 1.5 之后，Java 语言提供了对注解（Annotation）的支持，这些注解和普通的 Java 代码一样，是在运行期间发挥作用的。在JDK 1.6 中实现了JSR-269 规范。提供了一组差入职注解处理器的标准 API 在编译期间对注解进行处理，我们可以把它看做是一组编译器的插件，在这些插件里面，可以读取，修改，添加抽象语法书中的任意元素。

在 Java 源码中，插入式注解处理器的初始化过程是在initProcessAnnotation（）犯法中完成的，而它的执行过程则是在processAnnotation（）方法中完成的。这个方法判断是否还有新的注解处理器需要执行，如果有的话，通过 com.sun.tools.javac.processing.JavacProcessingEnvironment类的 doprocessing（）方法生成一个新的 JavaCompiler 对象对编译的后续步骤进行处理。

三类注解：

> - **标准 Annotation**
> 包括 Override, Deprecated, SuppressWarnings，是java自带的几个注解，他们由编译器来识别，不会进行编译。
> - **元 Annotation**
> @Retention, @Target, @Inherited, @Documented，它们是用来定义 Annotation 的 Annotation。也就是当我们要自定义注解时，需要使用它们。
> - **自定义 Annotation**
> 根据需要，自定义的Annotation。

###自定义 Annotation：

**@Retention: 定义注解的保留策略**

> - **@Retention(RetentionPolicy.SOURCE)**
SOURCE
> 该注解仅用于源码阶段，就是我们编写的java文件。
> - **@Retention(RetentionPolicy.CLASS)**
CLASS
该注解用于源码、类文件阶段。就是我们编写java文件和编译后产生的class文件。
> - **@Retention(RetentionPolicy.RUNTIME)**
RUNTIME
该注解用于源码、类文件和运行时阶段。

**@Target：定义注解的作用目标**

> - **@Target(ElementType.TYPE)**   
接口、类、枚举、注解
> - **@Target(ElementType.FIELD)** 
字段、枚举的常量
> - **@Target(ElementType.METHOD)** 
方法
> - **@Target(ElementType.PARAMETER)** 
方法参数
> - **@Target(ElementType.CONSTRUCTOR)**  
构造函数
> - **@Target(ElementType.LOCAL_VARIABLE)** 
局部变量
> - **@Target(ElementType.ANNOTATION_TYPE)** 
注解
> - **@Target(ElementType.PACKAGE)** 
包  

**@interface 表示申明注解用的**

先来看段代码
![MainActivity](http://upload-images.jianshu.io/upload_images/2159159-0eb55a77e183371d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

看看这段代码运行结果
![运行结果](http://upload-images.jianshu.io/upload_images/2159159-6c6ba78a158220af.gif?imageMogr2/auto-orient/strip)

看看注解做了什么事情:
![生成的类](http://upload-images.jianshu.io/upload_images/2159159-0e3d5878a8252055.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

具体生成的代码：
![生成的代码](http://upload-images.jianshu.io/upload_images/2159159-05920c523c77edc2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

注解类：
![注解类](http://upload-images.jianshu.io/upload_images/2159159-1196304705f9a309.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)