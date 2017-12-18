package codyy.codyy.urlannotation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * Created by kmdai on 17-12-18.
 */

public class AnnotatedClass {
    private String mBaseUrl;

    private List<AnnotationField> mAnnotationFields;

    public AnnotatedClass() {
        mAnnotationFields = new ArrayList<>();
    }

    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public void addField(AnnotationField annotationField) {
        mAnnotationFields.add(annotationField);
    }

    JavaFile generateFile() {
        //generateMethod
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("reSetURL")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC);

        for (AnnotationField field : mAnnotationFields) {
            // find views
            bindViewMethod.addStatement("$N = "+mBaseUrl+" + $S", field.getVariable(), field.getAnnotation());
        }

        //generaClass
        TypeSpec injectClass = TypeSpec.classBuilder("$URLAnnotation")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(bindViewMethod.build())
                .build();

        String packageName = "com.codyy.annotation";

        return JavaFile.builder(packageName, injectClass).build();
    }
}
