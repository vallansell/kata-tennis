package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Score {
    Love("Love"), Fifteen("15"), Thirtheen("30"), Fourteen("40");

    private String score;
}
