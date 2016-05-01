package com.sonin.taquin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class JeuActivity extends AppCompatActivity {
    private TaquinAdapter ta;
    private Bitmap img;
    private ImageView preview; // miniature de l'image a recréer
    private int displayWidth; // largeur de l'écran
    static final int REQUEST_IMAGE_CAPTURE = 1; // parametre pour l'appel de l'appareil photo
    private int tailleGrille; // taille de la grille de jeu

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // définition de la taille de l'écran
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        displayWidth = metricsB.widthPixels;

        // affichage l'activité de jeu (activity_jeux.xml)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeux);

        // récupération et initialisation des informations de mainActivity
        int idImage = getIntent().getIntExtra("idImg", 0);
        this.tailleGrille = getIntent().getIntExtra("nbCase", 0);
        preview = (ImageView) findViewById(R.id.preview);
        setImgPreview(idImage);

        setImg(img);
    }

    public void setImg(Bitmap pic) {
        ta = new TaquinAdapter(this, displayWidth, pic, this.tailleGrille);

        // définition de la grille de vignette
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(tailleGrille);
        gridview.setAdapter(ta);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // vérification de l'état du jeu
                if (!ta.partieTerminee()) {
                    ta.movePicture(position);
                    ta.partieTerminee();

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Bravo! Vous avez gagnez!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent intent = new Intent(JeuActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            try {
                // utilisation de l'intent pour prendre une photo
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //on retourne la donnée en onActivityResult
                startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                // affichage d'une erreur si aucune caméra n'est détectée
                String errorMessage = "votre appareil ne supporte pas la prise de photos";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (id == R.id.action_exit) {
            // ferme l'activité atuelle pour aller sur la main Activity
            Intent intent = new Intent(JeuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.action_refresh) {
            // remélange les morceau d'images à partir de l'état actuel
            ta.RefreshRandoomArray();
        } else if (id == R.id.action_restart) {
            // redémarre une nouvelle partie
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap;
            // affichage et traitement de la photo prise
            if (data.getData() == null) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
                setImg(imageBitmap);
                preview.setImageBitmap(imageBitmap);
            } else {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    setImg(imageBitmap);
                    preview.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // cherche les images à afficher à partir d'un id
    public void setImgPreview(int idImage) {
        switch (idImage) {
            case 0:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal1);
                preview.setImageResource(R.drawable.animal1);
                break;
            case 1:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal2);
                preview.setImageResource(R.drawable.animal2);
                break;
            case 2:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal3);
                preview.setImageResource(R.drawable.animal3);
                break;
            case 3:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal4);
                preview.setImageResource(R.drawable.animal4);
                break;
            case 4:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal5);
                preview.setImageResource(R.drawable.animal5);
                break;
            case 5:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal6);
                preview.setImageResource(R.drawable.animal6);
                break;
            case 6:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal7);
                preview.setImageResource(R.drawable.animal7);
                break;
            case 7:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal8);
                preview.setImageResource(R.drawable.animal8);
                break;
            case 8:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal9);
                preview.setImageResource(R.drawable.animal9);
                break;
            case 9:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal10);
                preview.setImageResource(R.drawable.animal10);
                break;
            case 10:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.anim);
                preview.setImageResource(R.drawable.anim);
                break;
            case 11:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal12);
                preview.setImageResource(R.drawable.animal12);
                break;
            default:
                img = BitmapFactory.decodeResource(this.getResources(), R.drawable.animal3);
                preview.setImageResource(R.drawable.animal3);
        }
    }
}