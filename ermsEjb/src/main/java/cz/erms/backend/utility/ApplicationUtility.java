package cz.erms.backend.utility;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class ApplicationUtility {

    public static List<String> getTokens(String input) {
        List<String> tokens = new ArrayList<>();

        if (StringUtils.isNotBlank(input)) {
            tokens = Arrays
                    .stream(input.split("\\|"))
                    .map(String::trim)
                    .toList();
        }

        return tokens;
    }
}
