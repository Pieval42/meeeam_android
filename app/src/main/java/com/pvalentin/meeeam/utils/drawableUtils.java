package com.pvalentin.meeeam.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

public class drawableUtils {
    public static Drawable getProgressBarDrawable(Context context) {
        // Crée un TypedValue pour stocker les valeurs résolues
        TypedValue value = new TypedValue();
        // Résout l'attribut android.R.attr.progressBarStyleSmall dans le thème actuel
        context.getTheme().resolveAttribute(android.R.attr.progressBarStyleSmall, value, true);
        // Obtient le style de la barre de progression
        int progressBarStyle = value.data;
        // Définir les attributs à rechercher
        int[] attributes = new int[]{android.R.attr.indeterminateDrawable};
        // Obtenir les attributs stylés
        TypedArray array = context.obtainStyledAttributes(progressBarStyle, attributes);
        // Obtenir le Drawable pour l'attribut indeterminateDrawable
        Drawable drawable = array.getDrawable(0);
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable not found for attribute");
        }
        startIfAnimatable(drawable);
        // Libérer les ressources utilisées par TypedArray
        array.recycle();
        return drawable;
    }

    private static void startIfAnimatable(Drawable drawable) {
        // Vérifie si le drawable est une instance d'Animatable
        if (drawable instanceof Animatable) {
            // Cast le drawable à Animatable et appelle la méthode start
            ((Animatable) drawable).start();
        }
    }
}
