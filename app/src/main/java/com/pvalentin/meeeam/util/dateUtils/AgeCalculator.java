package com.pvalentin.meeeam.util.dateUtils;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;
import com.jakewharton.threetenabp.AndroidThreeTen;
import android.content.Context;

public class AgeCalculator {
  
  /**
   * Initialise ThreeTenABP pour l'application.
   * Appeler cette méthode dans la classe Application ou l'activité principale.
   *
   * @param context Contexte de l'application
   */
  public static void init(Context context) {
    AndroidThreeTen.init(context);
  }
  
  /**
   * Calcule l'âge à partir de la date de naissance.
   *
   * @param birthDateString La date de naissance sous forme de chaîne (format: "yyyy-MM-dd")
   * @return L'âge calculé
   */
  public static int calculateAge(String birthDateString) {
    try {
      // Définir le format de la date
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      
      // Convertir la chaîne en LocalDate
      LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
      
      // Obtenir la date actuelle
      LocalDate currentDate = LocalDate.now();
      
      // Calculer la période entre la date de naissance et la date actuelle
      Period period = Period.between(birthDate, currentDate);
      
      // Retourner l'âge en années
      return period.getYears();
    } catch (DateTimeParseException e) {
      System.err.println("Format de date invalide: " + birthDateString);
      return -1; // Valeur indiquant une erreur
    }
  }
  
  public static void main(String[] args) {

    
    String birthDateString = "1990-06-09";
    int age = calculateAge(birthDateString);
    if (age != -1) {
      System.out.println("L'âge est : " + age + " ans.");
    } else {
      System.out.println("Erreur lors du calcul de l'âge.");
    }
  }
}
