package f3f.global.common;

import f3f.domain.board.domain.RegStatus;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, RegStatus> {
    @Override
    public RegStatus convert(String source) {
        try {
            return RegStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}