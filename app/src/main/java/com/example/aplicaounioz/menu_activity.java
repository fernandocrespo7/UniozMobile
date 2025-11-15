package com.example.aplicaounioz; // Define o pacote Java da sua aplicação Android.

import androidx.appcompat.app.AppCompatActivity; // Importa a classe base para Activities.
import android.content.Intent; // Usado para navegação entre Activities (telas).
import android.os.Bundle; // Usado para gerenciar o estado da Activity.
import android.view.Gravity; // Usado para alinhar mensagens (esquerda/direita).
import android.view.View; // Classe base para componentes de UI.
import android.widget.Button; // Componente Botão.
import android.widget.EditText; // Componente de entrada de texto.
import android.widget.LinearLayout; // Layout onde as mensagens serão adicionadas.
import android.widget.TextView; // Componente de texto para exibir as mensagens.
import android.widget.ScrollView; // Contêiner para permitir a rolagem do chat.
import android.widget.ImageButton; // Componente para o botão de sair (ícone).
import androidx.core.content.ContextCompat; // Usado para obter cores de forma segura.

public class menu_activity extends AppCompatActivity { // A classe principal do chat.

    // VARIÁVEL DE ESTADO: Crucial para o sistema de confirmação.
    // Torna-se TRUE depois que o bot faz uma pergunta ("Isso resolveu...?") e espera o "sim" ou "não".
    private boolean aguardandoConfirmacao = false;

    // 1. Declaração das variáveis dos componentes de UI
    private EditText editTextMensagem; // Campo onde o usuário digita a mensagem.
    private Button buttonEnviar; // Botão de envio de mensagem.
    private LinearLayout layoutMensagens; // Contêiner principal onde os balões de chat são inseridos dinamicamente.
    private ScrollView scrollViewChat; // Contêiner que envolve o layoutMensagens e permite a rolagem.
    private ImageButton buttonSair; // Botão para a funcionalidade de Logout.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método chamado ao iniciar a Activity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Carrega o layout visual (activity_menu.xml).

        // 2. Conexão das variáveis com os IDs do XML (findViewById)
        editTextMensagem = findViewById(R.id.edit_text_mensagem);
        buttonEnviar = findViewById(R.id.button_enviar);
        layoutMensagens = findViewById(R.id.layout_mensagens);
        scrollViewChat = findViewById(R.id.scroll_view_chat);
        buttonSair = findViewById(R.id.button_sair); // Conexão do botão de sair.

        // 3. Configura a ação do botão ENVIAR (adiciona um ouvinte de clique)
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensagem(); // Chama o método principal de envio de mensagens.
            }
        });

        // Configura a ação do botão SAIR
        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogout(); // Chama o método para deslogar e ir para a tela de Login.
            }
        });

        // Adiciona a primeira mensagem do bot com as opções ao abrir o chat.
        adicionarMensagemBot(" Olá! No que posso te ajudar?\n\n" +
                "Talvez eu possa te ajudar com alguma informação. Escolha uma opção (digite o número):\n\n" +
                "1. Como redefinir minha senha?\n" +
                "2. Onde encontro meu boleto?\n" +
                "3. Onde vejo minhas notas e faltas?\n" +
                "4. Como falar com o suporte técnico?\n" +
                "5. Como trancar meu curso?\n", true); // O 'true' indica que é a primeira mensagem.
    }

    // Método responsável por coletar a mensagem do campo de texto e iniciar o processamento.
    private void enviarMensagem() {
        String mensagem = editTextMensagem.getText().toString().trim(); // Pega o texto e remove espaços.

        if (!mensagem.isEmpty()) { // Verifica se o campo não está vazio.
            // A. Adiciona a mensagem do usuário (lado direito do chat).
            adicionarMensagemUsuario(mensagem);

            // B. Processa a mensagem e gera a resposta do bot.
            processarInteracao(mensagem);

            // C. Limpa o campo de texto após o envio.
            editTextMensagem.setText("");

            // D. Rola o ScrollView para baixo para mostrar a última mensagem.
            scrollViewChat.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewChat.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    // MÉTODO NOVO: Controla o fluxo de perguntas e respostas do bot.
    private void processarInteracao(String mensagem) {
        String msgLowerCase = mensagem.toLowerCase().trim(); // Converte a mensagem para minúsculas.

        // 1. Lógica para tratar a resposta "Sim" ou "Não" (Estado de Confirmação).
        if (aguardandoConfirmacao) {

            if (msgLowerCase.equals("sim")) {
                aguardandoConfirmacao = false; // Sai do estado de espera.
                adicionarMensagemBot("Que ótimo! Fico feliz em ajudar.", false);
            } else if (msgLowerCase.equals("nao") || msgLowerCase.equals("não")) {
                aguardandoConfirmacao = false; // Sai do estado de espera.
                // Mensagem de encaminhamento solicitada.
                adicionarMensagemBot("Ok. Abra o site Unioz.com para mais informações!", false);
            } else {
                // Pede para o usuário responder corretamente Sim ou Não.
                adicionarMensagemBot("Por favor, responda apenas 'sim' ou 'não'.", false);
            }
            return; // Interrompe a função para não processar como opção numérica.
        }

        // 2. Lógica para processar as opções numéricas (ou palavras-chave)
        String respostaAutomatica = processarRespostaAutomatica(mensagem); // Obtém a resposta base.

        adicionarMensagemBot(respostaAutomatica, false); // Exibe a resposta do bot.

        // Verifica se a resposta foi uma das opções válidas (não a mensagem de erro padrão).
        if (!respostaAutomatica.equals(" Operação inválida. Por favor, digite um número de 1 a 5.")) {

            // Adiciona a pergunta de confirmação em um NOVO balão.
            adicionarMensagemBot("Isso resolveu sua dúvida? (Responda 'sim' ou 'não').", false);

            // Define a flag para TRUE, indicando que o bot agora está esperando a confirmação.
            aguardandoConfirmacao = true;
        }
    }

    // MÉTODO: Contém as regras simples (if/else) para as respostas automáticas.
    private String processarRespostaAutomatica(String mensagem) {
        String msgLowerCase = mensagem.toLowerCase();

        // **Regras de resposta baseadas em palavras-chave ou números.**
        if (msgLowerCase.contains("senha") || msgLowerCase.contains("redefinir")|| msgLowerCase.contains("1")) {
            return " Para redefinir sua senha, acesse a página de login e clique em 'Esqueci minha senha', preencha todas as colunas e digite seu Token ( O mesmo que a faculdade forneceu em sua inscrição).\n";
        } else if (msgLowerCase.contains("boleto") || msgLowerCase.contains("2")) {
            return " Nossos boletos são enviados para seu e-mail cadastrado, em até 5 dias antes do vencimento.\n";
        } else if (msgLowerCase.contains("notas")|| msgLowerCase.contains("faltas")|| msgLowerCase.contains("3")) {
            return " Para ter acesso as suas notas e faltas bimestrais, entre na aba 'Meu Perfil' para ter mais informações informações.\n";
        } else if (msgLowerCase.contains("secretaria") || msgLowerCase.contains("4") || msgLowerCase.contains("suporte")) {
            return " Para falar com a secretária ligue para (XX) XXXX-XXXX ou aguarde aqui.\n";
        } else if (msgLowerCase.contains("trancar") || msgLowerCase.contains("5")) {
            return " Para cancelar sua matrícula ou trancar seu curso, é necessário apresentar todos os documentos que foram levados no dia de inscrição.\n";
        } else {
            // Resposta padrão caso a mensagem não corresponda a nenhuma regra.
            return " Operação inválida. Por favor, digite um número de 1 a 5.";
        }
    }

    // MÉTODO: Cria e adiciona o balão de mensagem do USUÁRIO (alinhado à direita).
    private void adicionarMensagemUsuario(String texto) {
        TextView textView = criarTextViewBase(texto); // Cria o balão base.

        textView.setBackgroundResource(R.drawable.bg_balao_usuario); // Define o estilo/cor do balão.
        // Cria parâmetros para alinhar o balão.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END; // Alinha o balão à direita.
        params.topMargin = 4;
        params.bottomMargin = 4;
        textView.setLayoutParams(params);

        layoutMensagens.addView(textView); // Adiciona o balão ao layout de mensagens.
    }

    // MÉTODO: Cria e adiciona o balão de mensagem do BOT (alinhado à esquerda).
    private void adicionarMensagemBot(String texto, boolean isPrimeiraMsg) {
        TextView textView = criarTextViewBase(texto); // Cria o balão base.

        textView.setBackgroundResource(R.drawable.bg_balao_chat); // Define o estilo/cor do balão do bot.
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white)); // Define a cor do texto (geralmente branco para balões coloridos).

        // Cria parâmetros para alinhar o balão.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START; // Alinha o balão à esquerda.
        params.topMargin = isPrimeiraMsg ? 0 : 4; // Ajuste de margem.
        params.bottomMargin = 4;
        textView.setLayoutParams(params);

        layoutMensagens.addView(textView); // Adiciona o balão ao layout de mensagens.
    }

    // MÉTODO: Função auxiliar para criar e configurar o TextView (balão) com estilos comuns.
    private TextView criarTextViewBase(String texto) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setPadding(12, 8, 12, 8); // Define o espaçamento interno (padding).
        // Limita a largura do balão para que ele não ocupe a tela toda (melhora a visualização).
        textView.setMaxWidth(getScreenWidth() * 2 / 3);
        return textView;
    }

    // MÉTODO: Função auxiliar para obter a largura da tela (usado em criarTextViewBase).
    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    // MÉTODO: Gerencia a saída do usuário e navegação para a tela de login.
    private void fazerLogout() {
        // Cria a Intent para ir para a tela de Login.
        Intent intent = new Intent(menu_activity.this, login_activity.class);
        // Flags que garantem que o usuário não possa voltar para o chat após o logout.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); // Inicia a tela de Login.
        finish(); // Fecha a Activity do chat.
    }
}