package com.syy.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
public class MyTypeFilter implements TypeFilter {

    /**
     * Determine whether this filter matches for the class described by
     * the given metadata.
     * @param metadataReader the metadata reader for the target class
     * @param metadataReaderFactory a factory for obtaining metadata readers
     * for other classes (such as superclasses and interfaces)
     * @return whether this filter matches
     * @throws IOException in case of I/O failure when reading metadata
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        Resource resource = metadataReader.getResource();
        String name = classMetadata.getClassName();

        if (name.contains("er")) {
            System.out.println("---->mathc true--->"+name);
            return true;
        }
        System.out.println("---->mathc false--->"+name);
        return false;
    }
}
