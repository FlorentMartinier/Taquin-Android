package com.sonin.taquin;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Sonin on 11.11.2015.
 */
public class TaquinAdapter extends BaseAdapter {

    private Context mContext;
    public int width;
    private int height;
    private int tailleGrille;
    private int size;
    private Bitmap imgAdapted;
    private Bitmap[] tabPicture; // tableau de morceaux d'images
    private int[] tabInt; // tableau d'identifiants des morceau d'image
    private ImageView imageView;

    // initialistion de la grille de jeu
    public void initGrille(int tailleGrille) {
        tabPicture = new Bitmap[tailleGrille * tailleGrille];
        tabInt = new int[tailleGrille * tailleGrille];
    }

    // change la largeur
    public void setWidth(int width) {
        this.width = width;
    }

    // change la longueur
    public void setHeight(int height) {
        this.height = height;
    }

    public TaquinAdapter(Context c, int displayWidth, Bitmap img, int nbr) {
        mContext = c;
        this.tailleGrille = nbr;
        initGrille(this.tailleGrille);
        this.size = displayWidth / tailleGrille;
        setWidth(this.size);
        setHeight(this.size);
        int largeur = displayWidth;

        // adaptation de l'image à la taille d'écran
        imgAdapted = Bitmap.createScaledBitmap(img, largeur, largeur, false);

        // references to our images
        int cordY = 0;
        int idImg = 0;

        // remplissage du tableau d'images
        for (int i = 0; i < tailleGrille; i++) {
            int cordX = 0;
            for (int j = 0; j < tailleGrille; j++) {
                if (idImg == 0) {
                    tabPicture[idImg++] = null;
                    cordX = cordX + size;
                } else {
                    tabPicture[idImg++] = Bitmap.createBitmap(imgAdapted, cordX, cordY, width, height);
                    cordX = cordX + size;
                }

            }
            cordY = cordY + size;
        }

        // remplissage du tableau d'identifiants
        for (int i = 0; i < tabInt.length; i++) {
            tabInt[i] = i;
        }

        // mélange du tableau
        RandomizeArray();
    }

    // mélange aléatoire du tableau
    public void RandomizeArray() {
        for (int i = 0; i < tailleGrille * tailleGrille; i++) {
            for (int j = 0; j < tabInt.length; j++) {
                int indicePrecedent = trouverIndice(j);
                if (tabInt[j] == 0) {
                    while (indicePrecedent == (indicePrecedent = trouverIndice(j))) ;
                    movePicture(indicePrecedent);
                }
            }
        }
    }

    // retourne un indice aléatoire possible à jouer
    public int trouverIndice(int position) {
        int[] tableauPositions = new int[4];
        Random rgen = new Random();  // Random number generator
        if (position - 1 >= 0 && !multipleN(position)) {
            tableauPositions[0] = position - 1;
        } else {
            tableauPositions[0] = -1;
        }
        if (position + 1 <= (tailleGrille * tailleGrille - 1) && !multipleN(position + 1)) {
            tableauPositions[1] = position + 1;
        } else {
            tableauPositions[1] = -1;
        }
        if (position - tailleGrille >= 0) {
            tableauPositions[2] = position - tailleGrille;
        } else {
            tableauPositions[2] = -1;
        }
        if (position + tailleGrille <= (tailleGrille * tailleGrille - 1)) {
            tableauPositions[3] = position + tailleGrille;
        } else {
            tableauPositions[3] = -1;
        }
        int choix = tableauPositions[rgen.nextInt(4)];
        while (choix == -1) {
            choix = tableauPositions[rgen.nextInt(4)];
        }
        return choix;
    }

    public int getCount() {
        return tabPicture.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public ImageView getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(width, height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(tabPicture[position]);
        return imageView;
    }

    // vérifie que le parametre est un multiple de la taille de la grille
    public boolean multipleN(int val) {
        return (val % tailleGrille == 0);
    }

    // définition de la fin de partie
    public boolean partieTerminee() {
        for (int i = 0; i < tabInt.length; i++) {
            if (tabInt[i] != i) {
                return false;
            }
        }
        tabPicture[0] = Bitmap.createBitmap(imgAdapted, 0, 0, width, height);
        this.notifyDataSetChanged();
        return true;
    }

    // définition d'un mouvement possible d'une case du tableau
    public void movePicture(int position) {
        if (position - 1 >= 0 && !multipleN(position) && tabPicture[position - 1] == null) {
            tabPicture[position - 1] = tabPicture[position];
            tabPicture[position] = null;
            tabInt[position - 1] = tabInt[position];
            tabInt[position] = 0;
        } else if (position + 1 <= (tailleGrille * tailleGrille - 1) && !multipleN(position + 1) && tabPicture[position + 1] == null) {
            tabPicture[position + 1] = tabPicture[position];
            tabPicture[position] = null;
            tabInt[position + 1] = tabInt[position];
            tabInt[position] = 0;
        } else if (position - tailleGrille >= 0 && tabPicture[position - tailleGrille] == null) {
            tabPicture[position - tailleGrille] = tabPicture[position];
            tabPicture[position] = null;
            tabInt[position - tailleGrille] = tabInt[position];
            tabInt[position] = 0;
        } else if (position + tailleGrille <= (tailleGrille * tailleGrille - 1) && tabPicture[position + tailleGrille] == null) {
            tabPicture[position + tailleGrille] = tabPicture[position];
            tabPicture[position] = null;
            tabInt[position + tailleGrille] = tabInt[position];
            tabInt[position] = 0;
        }

        // actualisation de la grille après manipulation
        this.notifyDataSetChanged();
    }

    // remélange du taleau
    public void RefreshRandoomArray() {
        RandomizeArray();
        this.notifyDataSetChanged();
    }

}