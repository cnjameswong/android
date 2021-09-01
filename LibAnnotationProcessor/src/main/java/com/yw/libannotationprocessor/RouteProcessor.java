package com.yw.libannotationprocessor;

import com.google.auto.service.AutoService;
import com.yw.libannotation.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class RouteProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Messager mMessager;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Route.class);
//        TypeElement  类节点;
//        ExecutableElement  方法结点;
//        VariableElement   成员变量节点;
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement)element;
                String activityName = typeElement.getQualifiedName().toString();
                Route annotation = typeElement.getAnnotation(Route.class);
                String key = annotation.path();
                map.put(key, activityName+".class");
            }
        }
        if (map.size()>0) {
            //生成工具类
            /**
             * package com.yw.router;
             * import android.app.Activity;
             * import com.yw.librouter.IActivityRouter;
             * import java.util.Map;
             * public class ActivityUtil implements IActivityRouter {
             *     public void addActivity(Map<String, Class<? extends Activity>> paths) {
             *         paths.put("/main/main", MainActivity.class);
             *     }
             * }
             */
            Writer writer = null;
            String className = "ActivityUtil" + System.currentTimeMillis();
            try {
                JavaFileObject sourceFile = mFiler.createSourceFile("com.yw.router.path.init." + className);

                writer = sourceFile.openWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("package com.yw.router.path.init;").append("\n")
                        .append("import android.app.Activity;").append("\n")
                        .append("import com.yw.librouter.IActivityRouter;").append("\n")
                        .append("import java.util.Map;").append("\n")
                        .append("public class ").append(className).append(" implements IActivityRouter {").append("\n")
                        .append("     public void addActivity(Map<String, Class<? extends Activity>> paths) {").append("\n");

                        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = iterator.next();
                            String key = entry.getKey();
                            String val = entry.getValue();
                            sb.append("         paths.put(\"").append(key).append("\"").append(",").append(val).append(");\n");
                        }
                        sb.append("     }").append("\n")
                        .append(" }").append("\n");
                        writer.write(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != writer) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }


}
