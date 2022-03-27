package domain;

import enums.Score;
import exception.ScoreImpossibleException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Partie {

    Joueur joueur1;
    Joueur joueur2;

    public String recupererScore() throws ScoreImpossibleException {
        if (isScoreEqual()) {
            return afficherScoreEgalite();
        } else {
            if (Math.max(joueur1.getPoint(), joueur2.getPoint()) <= 4) {
                return afficherScorePourChacunDesJoueurs();
            } else {
                if (getDifferenceDePoint() == 1) {
                    return "Avantage " + joueurAvecLePlusDePoint().getNom();
                }
                if (getDifferenceDePoint() == 2 || isJoueurGagnantSansRegleDifferenceDePoints())  {
                    return "Le gagnant est : " + (joueurAvecLePlusDePoint().getNom());
                } else {
                    throw new ScoreImpossibleException("Le score est impossible, la difference de score est trop grande");
                }
            }
        }
    }

    private boolean isJoueurGagnantSansRegleDifferenceDePoints() {
        return (Math.max(joueur1.getPoint(), joueur2.getPoint()) == 5) && (Math.min(joueur1.getPoint(), joueur2.getPoint()) <= 3);
    }

    private String afficherScoreEgalite() {
        if (joueur1.getPoint() <= 3) {
            return Score.values()[joueur2.getPoint()].getScore() + "-A";
        } else {
            return "Deuce";
        }
    }

    private boolean isScoreEqual() {
        return joueur2.getPoint() == joueur1.getPoint();
    }

    private String afficherScorePourChacunDesJoueurs() {
        return joueurAvecLePlusDePoint().getNom() + " : " + Score.values()[joueurAvecLePlusDePoint().getPoint()].toString()
                + " / "
                + joueurAvecLeMoinsDePoint().getNom() + " : " + Score.values()[joueurAvecLeMoinsDePoint().getPoint()].toString();
    }

    private int getDifferenceDePoint() {
        return Math.abs(joueur1.getPoint() - joueur2.getPoint());
    }

    private Joueur joueurAvecLePlusDePoint() {
        return joueur2.getPoint() > joueur1.getPoint() ? joueur2 : joueur1;
    }

    private Joueur joueurAvecLeMoinsDePoint() {
        return joueur2.getPoint() < joueur1.getPoint() ? joueur2 : joueur1;
    }
}
