package com.sodagift.biz.config.converter;

import com.sodagift.common.domain.Language;
import org.springframework.core.convert.converter.Converter;

public class StringToLanguageConverter implements Converter<String, Language> {

    @Override
    public Language convert(String source) {
        try {
            return Language.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
