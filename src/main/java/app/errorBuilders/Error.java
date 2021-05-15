package app.errorBuilders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {

    private final String title, text;
}
