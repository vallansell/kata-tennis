import domain.Joueur;
import domain.Partie;
import enums.Score;
import exception.ScoreImpossibleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PartieTest {

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3"})
    void recupererScore_devrait_afficher_un_score_finissant_par_A_quand_les_deux_joueurs_sont_a_egalite(int scoreEgalite) throws ScoreImpossibleException {
        //Given
        Joueur joueur1 = new Joueur("James", 0);
        Joueur joueur2 = new Joueur("Eden", 0);
        Partie partie = new Partie(joueur1, joueur2);

        for (int i = 0; i < scoreEgalite; i++) {
            joueur1.marquerPoint();
            joueur2.marquerPoint();
        }

        //When
        String resultat = partie.recupererScore();

        //Then
        assertThat(resultat).isEqualTo(Score.values()[scoreEgalite].getScore() + "-A");
    }

    @ParameterizedTest
    @CsvSource({"5", "6", "7"})
    void recupererScore_devrait_afficher_un_score_de_deuce_si_egalite_au_dessus_de_3_points(int scoreEgalite) throws ScoreImpossibleException {
        //Given
        Joueur joueur1 = new Joueur("James", 0);
        Joueur joueur2 = new Joueur("Eden", 0);
        Partie partie = new Partie(joueur1, joueur2);

        for (int i = 0; i < scoreEgalite; i++) {
            joueur1.marquerPoint();
            joueur2.marquerPoint();
        }

        //When
        String resultat = partie.recupererScore();

        //Then
        assertThat(resultat).isEqualTo("Deuce");
    }

    @ParameterizedTest
    @CsvSource({"4,5", "5,4", "18,17"})
    void recupererScore_devrait_afficher_un_score_avec_le_joueur_ayant_l_avantage_quand_le_score_depasse_les_4_points_et_avec_seulement_un_point_d_ecart(int scoreJoueur1, int scoreJoueur2) throws ScoreImpossibleException {
        //Given
        String nomGagnant;
        Joueur joueur1 = new Joueur("James", 0);
        Joueur joueur2 = new Joueur("Eden", 0);
        Partie partie = new Partie(joueur1, joueur2);

        for (int i = 0; i < scoreJoueur1; i++) {
            joueur1.marquerPoint();
        }
        for (int i = 0; i < scoreJoueur2; i++) {
            joueur2.marquerPoint();
        }

        if(scoreJoueur1>scoreJoueur2) {
            nomGagnant = joueur1.getNom();
        } else {
            nomGagnant = joueur2.getNom();
        }

        //When
        String resultat = partie.recupererScore();

        //Then
        assertThat(resultat).isEqualTo("Avantage " + nomGagnant);
    }

    @ParameterizedTest
    @CsvSource({"4,6", "10,8", "5,0"})
    void recupererScore_devrait_afficher_le_gagnant_de_la_partie_quand_un_joueur_depasse_les_3_points_avec_2_point_ecarts(int scoreJoueur1, int scoreJoueur2) throws ScoreImpossibleException {
        //Given
        String nomGagnant;
        Joueur joueur1 = new Joueur("James", 0);
        Joueur joueur2 = new Joueur("Eden", 0);
        Partie partie = new Partie(joueur1, joueur2);

        for (int i = 0; i < scoreJoueur1; i++) {
            joueur1.marquerPoint();
        }
        for (int i = 0; i < scoreJoueur2; i++) {
            joueur2.marquerPoint();
        }

        if(scoreJoueur1>scoreJoueur2) {
            nomGagnant = joueur1.getNom();
        } else {
            nomGagnant = joueur2.getNom();
        }

        //When
        String resultat = partie.recupererScore();

        //Then
        assertThat(resultat).isEqualTo("Le gagnant est : "  + nomGagnant);
    }

    @Test
    void recupererScore_devrait_afficher_le_score_de_chaque_joueur_lorsque_le_score_est_different_entre_les_deux_joueurs_et_inferieur_a_trois() throws ScoreImpossibleException {
        //Given
        Joueur joueur1 = new Joueur("James", 0);
        Joueur joueur2 = new Joueur("Eden", 0);
        Partie partie = new Partie(joueur1, joueur2);

        for (int i = 0; i < 2; i++) {
            joueur1.marquerPoint();
        }
        for (int i = 0; i < 1; i++) {
            joueur2.marquerPoint();
        }

        //When
        String resultat = partie.recupererScore();

        //Then
        assertThat(resultat).isEqualTo("James : Thirtheen / Eden : Fifteen");
    }

    @Test
    void recupererScore_devrait_lever_une_exception_si_le_score_maximum_est_au_dessus_de__quatre_point_et_que_la_difference_de_point_est_superieur_a_2()  {
        //Given
        Joueur joueur1 = new Joueur("James", 0);
        Joueur joueur2 = new Joueur("Eden", 0);
        Partie partie = new Partie(joueur1, joueur2);

        for (int i = 0; i < 8; i++) {
            joueur1.marquerPoint();
        }
        for (int i = 0; i < 3; i++) {
            joueur2.marquerPoint();
        }

        //When
        Exception thrown = assertThrows(
                ScoreImpossibleException.class,
                partie::recupererScore
        );

        //Then
        assertTrue(thrown.getMessage().contains("Le score est impossible, la difference de score est trop grande"));
    }

}