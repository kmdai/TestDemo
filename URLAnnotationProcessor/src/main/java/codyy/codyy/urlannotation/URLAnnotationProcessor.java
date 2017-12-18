package codyy.codyy.urlannotation;


import com.codyy.urlannotation.URL;
import com.codyy.urlannotation.URLBase;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by kmdai on 17-12-18.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.codyy.urlannotation.URL", "com.codyy.urlannotation.URLBase"})
public class URLAnnotationProcessor extends AbstractProcessor {
    private Filer mFiler; //文件相关的辅助类
    private Elements mElements; //元素相关的辅助类
    private AnnotatedClass mAnnotatedClass;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElements = processingEnvironment.getElementUtils();
        mAnnotatedClass = new AnnotatedClass();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(URL.class)) {
            AnnotationField annotationField = new AnnotationField(element.getAnnotation(URL.class).value(), element.getSimpleName().toString());
            mAnnotatedClass.addField(annotationField);
        }
        int a = 0;
        for (Element element : roundEnvironment.getElementsAnnotatedWith(URLBase.class)) {
            a++;
            mAnnotatedClass.setBaseUrl(element.getSimpleName().toString());
        }
        try {
            mAnnotatedClass.generateFile().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
