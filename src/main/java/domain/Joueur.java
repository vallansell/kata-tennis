package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Joueur {
    String nom;
    int point;

    public void marquerPoint() {
        this.point++;
    }
}
