package com.sonin.taquin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity{
    private int nbCase; // taille de la grille de jeu par défaut

    public void onCreate(Bundle savedInstanceState) {

        // définition de la taille de l'écran
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        int displayWidth = metricsB.widthPixels;
        int displayHeight = metricsB.heightPixels;

        // taille d'une vignette
        int size = displayWidth /3;

        // affichage de la premiere activité (activity_main.xml)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // définition de la grille de vignette
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TaquinPreview(this, size));

        // définition de l'action de clic sur chaque vignette
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // récupération des informations de l'image et du mode de jeu, puis renvoie l'activité suivante
                getParametre(position);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.planets_array);
                if (selectedItemPosition == 0) {
                    setNbCase(3);
                }else if (selectedItemPosition == 1) {
                    setNbCase(4);
                }else if (selectedItemPosition == 2) {
                    setNbCase(5);
                }else if (selectedItemPosition == 3) {
                    setNbCase(7);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setNbCase(int nbCase){
        this.nbCase = nbCase;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    public void getParametre(int idImg) {
        Intent intent = new Intent(MainActivity.this, JeuActivity.class);
        // récupération des informations de l'image
        intent.putExtra("idImg", idImg);
        // récupération du mode de jeu
        intent.putExtra("nbCase", nbCase);
        // Renvoie l'activité JeuActivity
        startActivity(intent);
        // on termine l'activité actuelle(MainActivity) pour économiser la mémoire
        finish();

    }


}