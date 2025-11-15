package com.example.aplicaounioz;

// Importa classes essenciais do Android para Intents, Bundles, Views e Botões.
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

// Define a Activity 'token_activity', que representa uma tela no aplicativo.
public class token_activity extends AppCompatActivity {

    // O método 'onCreate' é chamado quando a Activity é criada.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout visual (XML) para esta tela.
        setContentView(R.layout.activity_token);

        // Encontra o botão no layout XML usando seu ID.
        Button buttonConfirmar = findViewById(R.id.buttonConfirmarRecuperacao);

        // Define o que acontece quando o botão é clicado.
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um Intent (intenção) para navegar entre telas.
                // Origem: token_activity.this (esta tela)
                // Destino: login_activity.class (a próxima tela)
                Intent intent = new Intent(token_activity.this, login_activity.class);

                // Inicia a nova tela (login_activity).
                startActivity(intent);
            }
        });
    }
}