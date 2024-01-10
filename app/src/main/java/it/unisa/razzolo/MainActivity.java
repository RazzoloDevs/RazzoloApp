package it.unisa.razzolo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unisa.razzolo.model.HashDictionary;
import it.unisa.razzolo.model.Point;
import it.unisa.razzolo.model.Trie;
import it.unisa.razzolo.model.Word;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final var hashDictionary = HashDictionary.getInstance();
        final var trie = Trie.getInstance();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("dictionary.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                hashDictionary.put(line);
                trie.insert(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        spinner = (Spinner) findViewById(R.id.algorithms_spinner);

        foundWordSize_text = findViewById(R.id.foundWordSize_text);
        elapsedTime_text = findViewById(R.id.elapsedTime_text);

        listView = findViewById(R.id.listView);
        wordAdapter = new WordAdapter(this, R.layout.activity_listview, R.id.listView);
        listView.setAdapter(wordAdapter);

        for (int i=1; i<=16; i++) {
            @SuppressLint("DiscouragedApi")
            int viewId = getResources().getIdentifier("box" + i, "id", getPackageName());
            boxes[i-1] = findViewById(viewId);
        }

    }

    @SuppressLint("DefaultLocale")
    public void onClickRunBtn(View view) {
        int i=0,j=0;
        for(EditText e : boxes){
            char c;
            if(e.getText().length() == 1){
                c = e.getText().charAt(0);
                if(Character.isLetter(c)){
                    matrix[i][j] = c;
                    if((j%4) == 3){
                        i++;
                        j=0;
                    }
                    else j++;
                }
                else{
                    Toast.makeText(this, "Compila correttamente tutte le caselle!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else{
                Toast.makeText(this, "Compila correttamente tutte le caselle!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            final var background = new Background(matrix, spinner.getSelectedItem().toString());
            background.run();
            handler.post(() -> {
                wordAdapter.clear();
                background.getFoundWords().forEach((k,v) -> {
                    wordAdapter.add(new Word(k, k.length(), v));
                });
                foundWordSize_text.setText(String.valueOf(background.getFoundWords().size()));
                elapsedTime_text.setText(String.format("%g s", background.getElapsedTime()));
            });
        });
    }

    public void onWordClicked(final ArrayList<Point> coordinates) {
        cleanGrid();
        highlightOnGrid(coordinates);
    }

    private void highlightOnGrid(final ArrayList<Point> coordinates) {
        // Implementa la logica per evidenziare le caselle della parola sulla griglia

    }

    private void cleanGrid(){
        // "Pulire" la griglia
    }

    private final char[][] matrix = new char[4][4];
    private final EditText[] boxes = new EditText[16];
    private ListView listView;
    private WordAdapter wordAdapter;
    private Spinner spinner;
    private TextView foundWordSize_text;
    private TextView elapsedTime_text;
}