package com.example.aplicaounioz;// Exemplo: DadosActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class recuperacaoActivity extends AppCompatActivity { // Sua Activity atual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Garanta que o layout correto está sendo carregado
        setContentView(R.layout.activity_recuperacao);

        // 1. Encontre o botão pelo ID
        Button buttonConfirmar = findViewById(R.id.buttonConfirmarDados);

        // ----------------------------------------------------

        // Conexão Recuperação -> Token - Disponível em outro arquivo, para que seja possível a

        // das interfaces, o que não seria possivel devido ao acesso banco de dados
        // ----------------------------------------------------

        // 2. Configure um "ouvinte de clique" (OnClickListener)
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. Crie um Intent para ir para a nova Activity

                // O Intent é como a "intenção" de realizar uma ação.
                // Parâmetros: (Activity de origem, Activity de destino.class)
                Intent intent = new Intent(recuperacaoActivity.this, token_activity.class);

                // 4. Inicie a nova Activity
                startActivity(intent);

                // Opcional: Se não quiser que o usuário volte para a tela anterior
                // finish();
            }
        });
    }
}