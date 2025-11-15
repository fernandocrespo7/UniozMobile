package com.example.aplicaounioz; // Define o pacote onde a classe está.

import androidx.appcompat.app.AppCompatActivity; // Importa a classe base para Activities.
import android.content.Intent; // Importa a classe para navegação entre telas.
import android.os.Bundle; // Importa a classe para gerenciamento de estado.
import android.view.View; // Importa a classe base para componentes de UI.
import android.widget.Button; // Importa a classe para o componente Botão.
import android.widget.TextView; // Importa a classe para o componente TextView.
import android.widget.Toast; // Importa a classe para exibir mensagens rápidas.

public class login_activity extends AppCompatActivity { // Define a classe principal da tela de Login.

    private Button btnEntrar; // Declara a variável para o botão de Entrar.
    private TextView tvRecuperarSenha; // Declara a variável para o texto/botão de Recuperar Senha.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método chamado ao criar a tela.
        super.onCreate(savedInstanceState); // Chama a implementação da classe pai.
        setContentView(R.layout.activity_login); // Carrega o layout visual (XML) da tela de Login.

        // Encontrar os componentes pelo ID e conectar as variáveis do Java com o XML.
        btnEntrar = findViewById(R.id.buttonLogin); // Conecta a variável 'btnEntrar' ao ID 'buttonLogin'.
        tvRecuperarSenha = findViewById(R.id.button); // Conecta a variável 'tvRecuperarSenha' ao ID 'button'.

        // Configura a ação de clique para o botão de Entrar.
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Método executado ao clicar no botão.
                boolean loginValido = true; // Simula a validação de login.

                if (loginValido) { // Se login for bem-sucedida:
                    try {
                        // Exibe a mensagem de sucesso antes de navegar.
                        Toast.makeText(login_activity.this, "Login com sucesso!", Toast.LENGTH_LONG).show();

                        // 1. Cria uma Intent para ir para a MenuActivity
                        Intent intent = new Intent(login_activity.this, menu_activity.class);

                        // 2. Inicia a Activity
                        startActivity(intent);

                        // 3. Finaliza a tela de login
                        finish();
                    } catch (Exception e) {
                        // Se ocorrer qualquer erro (por exemplo, Activity de destino não encontrada)
                        Toast.makeText(login_activity.this, "Erro ao iniciar o menu: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        // Opcional: e.printStackTrace(); para debug no Logcat
                    }
                } else { // Se o login falhar:
                    // Exibe uma mensagem de erro na tela do usuário.
                    Toast.makeText(login_activity.this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configura a ação de clique para o link/botão de Recuperar Senha.
        tvRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Método executado ao clicar no texto/botão.
                // Cria uma Intent para ir da tela atual para a tela de recuperação de senha.
                Intent intent = new Intent(login_activity.this, recuperacaoActivity.class);

                startActivity(intent); // Inicia a tela de recuperação.
            }
        });
    }
}