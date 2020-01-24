package com.soliwork.calc_brixdensidade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText brixOrig, gravityOrig, brixInicial, brixAtual, densAtual, brixAtual2, densAtual2, abv, densOriginal;
    Button btnCalcBrixGravity,btnCalcBrixDensFerm, btnCalcAbv;

    DecimalFormat formato;
    double sg = 0;
    double abw = 0;
    double ri = 0;
    double abv2 = 0;
    String dataFormatada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciaObjetos();

        formato = new DecimalFormat("#.###");

        getDateAtual();

        calculoBrixDensidade();

        calculoBrixDensFerment();

        calculoEstimAbv();

        limparCampos();
    }

    private void calculoEstimAbv() {

        btnCalcAbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float fg = Float.valueOf(densAtual2.getText().toString().replace(",", "."));
                float fb = Float.valueOf(brixAtual2.getText().toString());

                ri = 1.33302 + (0.001427193*fb) + (0.000005791157*fb*fb);
                abw = 1017.5596 - (277.4*fg) + ri*((937.8135*ri) - 1805.1228);
                abv2 = abw*(fg/0.794);

                ri = (100 *
                        (194.5935 +
                                129.8 * fg +
                                (1.33302 +
                                        0.001427193 * fb +
                                        0.000005791157 * (fb * fb)) *
                                        (410.8815 *
                                                (1.33302 +
                                                        0.001427193 * fb +
                                                        0.000005791157 * (fb * fb)) -
                                                790.8732) +
                                2.0665 *
                                        (1017.5596 -
                                                277.4 * fg +
                                                (1.33302 +
                                                        0.001427193 * fb +
                                                        0.000005791157 * (fb * fb)) *
                                                        (937.8135 *
                                                                (1.33302 +
                                                                        0.001427193 * fb +
                                                                        0.000005791157 * (fb* fb)) -
                                                                1805.1228)))) /
                        (100 +
                                1.0665 *
                                        (1017.5596 -
                                                277.4 * fg +
                                                (1.33302 +
                                                        0.001427193 * fb +
                                                        0.000005791157 * (fb * fb)) *
                                                        (937.8135 *
                                                                (1.33302 +
                                                                        0.001427193 * fb +
                                                                        0.000005791157 * (fb * fb)) -
                                                                1805.1228)));
               double k = ri / (258.6 - (ri / 258.2) * 227.1) + 1;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    abv.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.round_et_desable));
                    densOriginal.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.round_et_desable));
                }

                abv.setText(formato.format(abv2));
                densOriginal.setText(formato.format(k));
            }
        });
    }

    private void calculoBrixDensFerment() {

        btnCalcBrixDensFerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ob = Float.valueOf(brixInicial.getText().toString());
                float fb = Float.valueOf(brixAtual.getText().toString());
                double fg = 0;
                fg = 1.001843 - (0.002318474*ob) - (0.000007775*ob*ob) - (0.000000034*ob*ob*ob) + (0.00574*fb) + (0.00003344*fb*fb) + (0.000000086*fb*fb*fb);
                densAtual.setText(formato.format(fg));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    densAtual.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.round_et_desable));
                }

                brixAtual2.setText(brixAtual.getText().toString());
                densAtual2.setText(densAtual.getText().toString());
            }
        });
    }

    private void calculoBrixDensidade() {

        btnCalcBrixGravity.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                float b = Float.valueOf(brixOrig.getText().toString());

                sg = 1.000898 + (0.003859118*b) + (0.00001370735*b*b) + (0.00000003742517*b*b*b);
                gravityOrig.setText(formato.format(sg));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    gravityOrig.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.round_et_desable));
                }

                brixAtual.requestFocus();
                brixInicial.setText(brixOrig.getText().toString());
            }
        });
    }

    private void iniciaObjetos() {

        brixOrig = findViewById(R.id.editBrixOriginal);
        gravityOrig = findViewById(R.id.editGravityOriginal);
        btnCalcBrixGravity = findViewById(R.id.btnCalcBrixGravity);

        brixInicial = findViewById(R.id.editBrixInicial);
        brixAtual = findViewById(R.id.editBrixAtual);
        densAtual = findViewById(R.id.editDensAtual);
        btnCalcBrixDensFerm = findViewById(R.id.btnCalcBrixDensFerm);

        brixAtual2 = findViewById(R.id.editBrixAtual2);
        densAtual2 = findViewById(R.id.editDensAtual2);
        abv = findViewById(R.id.editAbv);
        densOriginal = findViewById(R.id.editDensOriginal);
        btnCalcAbv = findViewById(R.id.btnCalcAbv);

        brixOrig.requestFocus();
    }

    private void limparCampos(){

        brixOrig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                brixOrig.setText(null);
                gravityOrig.setText(null);
                brixInicial.setText(null);
                brixAtual.setText(null);
                densAtual.setText(null);
                brixAtual2.setText(null);
                densAtual2.setText(null);
                abv.setText(null);
                densOriginal.setText(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    gravityOrig.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.round_et));
                    densAtual.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.round_et));
                    abv.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.round_et));
                    densOriginal.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.round_et));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_share) {

            String msg = "Data " + dataFormatada +"\n" +
                    "Brix -> Densidade pré-fermentação (OG)" +"\n" +
                    "Leitura em Brix: " + brixOrig.getText() +"\n" + "Leitura Densidade: " + gravityOrig.getText() + "\n" +
                    "Brix -> Densidade durante a fermentação (FG)" +"\n" +
                    "Brix Atual: " + brixAtual.getText().toString()  +"\n" + "Densidade Atual: " + densAtual.getText().toString();
            Log.i("msg", "onOptionsItemSelected: " + msg);

            // Compartilhar msg para midia sociais
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareBody = msg; //"Your body here"
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Compartilhar"));
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDateAtual(){

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        dataFormatada = formataData.format(data);
    }
}
