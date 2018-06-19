package br.com.wilker.projeto2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.helpers.TipoOperador;

/**
 * Página Calculadora
 * Created by Wilker on 06/06/2018.
 */
public class CalculadoraFragment extends Fragment {

    // Textview do primeiro número digitado
    TextView primeiroNumero;

    // Textview do segundo número digitado
    TextView segundoNumero;

    // Textview do operador digitado
    TextView operadorNumero;

    // Textview do resultado a mostrar
    TextView resultadoNumero;

    // View do fragment
    View view;

    // View do botão igual
    Button buttonIgual;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Popula o fragmento com a view
        view = inflater.inflate(R.layout.calculadora, container, false);

        // binda as views com os objetos
        primeiroNumero = view.findViewById(R.id.calculadora_numero_um);
        segundoNumero = view.findViewById(R.id.calculadora_numero_dois);
        operadorNumero = view.findViewById(R.id.calculadora_operador);
        resultadoNumero = view.findViewById(R.id.calculadora_resultado);

        // binda os botões de digitação e atribui o onClick
        atribuirOnClickBotoesNumeros();

        // binda os botões operadores e atribui o onClick
        atribuirOnClickBotoesOperadores();

        // atribui onClick no botão igual
        buttonIgual = view.findViewById(R.id.calculadora_button_igual);
        buttonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarCalculo();
            }
        });

        // atribui onClick no botão reseet
        Button buttonReset = view.findViewById(R.id.calculadora_button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        // atribui onClick no botão apagar
        Button buttonApagar = view.findViewById(R.id.calculadora_button_apagar);
        buttonApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // caso já tenha um resutado, apaga tudo
                if (!resultadoNumero.getText().toString().isEmpty()) {
                    resetFields();
                }

                // Caso não tenha digitado um operador apaga no primeiro número, senão apaga no segundo número
                if (operadorNumero.getText().toString().isEmpty()) {
                    // Caso não tenha nada escrito no primeiro número, não faz nada
                    if(primeiroNumero.getText().length() > 0){
                        apagarCharDoTextView(primeiroNumero);
                    }
                } else {
                    // Caso não tenha nada escrito no segundo número, apaga o operador, se não apaga o segundo número
                    if(segundoNumero.getText().length() > 0) {
                        apagarCharDoTextView(segundoNumero);
                    } else {
                        operadorNumero.setText("");
                    }
                }
                enableOrDisableIgualButton();
            }
        });

        return view;
    }

    // Desabilta ou habilita o botão igual verificando se os campos estão preenchidos
    public void enableOrDisableIgualButton() {
        if (primeiroNumero.getText().toString().isEmpty() || segundoNumero.getText().toString().isEmpty() || operadorNumero.getText().toString().isEmpty()) {
            buttonIgual.setEnabled(false);
        } else {
            buttonIgual.setEnabled(true);
        }
    }

    // Realiza o calculo e popula o resultado
    public void realizarCalculo() {
        // Caso tenha algum resultado preenchido, utiliza ele como primeiro número
        if(!resultadoNumero.getText().toString().isEmpty()){
            primeiroNumero.setText(resultadoNumero.getText());
        }
        // Obtem o cálculo pelo operador e calcula
        TipoOperador tipoOperador = TipoOperador.getByOperador(operadorNumero.getText().toString());
        Double resultado = tipoOperador.calcular(getDoubleValueFrom(primeiroNumero), getDoubleValueFrom(segundoNumero));

        // Aplica or esultado no campo
        resultadoNumero.setText(resultado.toString());
        enableOrDisableIgualButton();
    }

    // Retorna o texto de um TextView como Double
    private double getDoubleValueFrom(TextView textView) {
        return Double.valueOf(textView.getText().toString());
    }

    // Atribui o onclick nos botões que digitam
    private void atribuirOnClickBotoesNumeros() {
        int[] idBotoesNumeros = {R.id.calculadora_button_virgula, R.id.calculadora_button0, R.id.calculadora_button1, R.id.calculadora_button2, R.id.calculadora_button3, R.id.calculadora_button4, R.id.calculadora_button5, R.id.calculadora_button6, R.id.calculadora_button7, R.id.calculadora_button8, R.id.calculadora_button9};
        atribuirOnClick(botaoNumeroOnClickListerner, idBotoesNumeros);
    }

    // Atribui o onclick nos botoões de operadores
    private void atribuirOnClickBotoesOperadores() {
        int[] idBotoesOperadores = {R.id.calculadora_button_porcentagem, R.id.calculaora_button_divisao, R.id.calculadora_button_multiplicacao, R.id.calculadora_button_menos, R.id.calculadora_button_mais};
        atribuirOnClick(botaoOperadorOnClickListener, idBotoesOperadores);
    }

    // Atribui onClick em em vários botões utilizando uma lista de ids
    public void atribuirOnClick(View.OnClickListener onClickListener, int... botoesIds) {
        for (int i = 0; i < botoesIds.length; i++) {
            Button botao = view.findViewById(botoesIds[i]);
            botao.setOnClickListener(onClickListener);
        }
    }

    // Apaga o ultimo caractere de um textview
    private void apagarCharDoTextView(TextView textView) {
        textView.setText(textView.getText().subSequence(0, textView.getText().length() - 1).toString());
    }

    // Processa a concatenação do texto escrito com o botão clicado
    private void processarConcatenarTextBotaoAoTextView(TextView textView, Button button) {
        String novoTexto  = textView.getText().toString();
        // caso seja um zero e o texto seja apenas um zero, não faz nada
        if(isZero(button) && novoTexto.length() == 1 && novoTexto.equals("0")){
            return;
        }
        // caso seja virgula
        if(isVirgula(button)){
            // caso seja virgula e o texto estiver vazio, não faz nada
            if(textView.getText().length() == 0 ){
                return;
            }
            // caso já tenha uma virgula no texto, retira ela e coloca novamente
            if(textView.getText().toString().contains(".")){
                novoTexto = novoTexto.replace(".", "");
            }
        }
        // concatena o texto que já estava escrito com o texto do botão
        novoTexto += button.getText().toString();
        textView.setText(novoTexto);
    }

    // Função de click dos operadores
    private View.OnClickListener botaoOperadorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // caso já tenha algo escrito no segundo numero e não tenha resultado, realiza o cálculo antes
            if (!segundoNumero.getText().toString().isEmpty() && resultadoNumero.getText().toString().isEmpty()){
                realizarCalculo();
            }
            // caso já tenha um resultado, passa ele para o primeiro número
            if (!resultadoNumero.getText().toString().isEmpty()) {
                primeiroNumero.setText(resultadoNumero.getText());
                segundoNumero.setText("");
                resultadoNumero.setText("");
            }
            // seta o operador digitado
            operadorNumero.setText(((Button) view).getText());
        }
    };

    // Função de click dos numeros e virgula
    private View.OnClickListener botaoNumeroOnClickListerner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;

            // caso o botao digitado seja uma virgula e já tenha resultado não faz nada, se não apaga tudo
            if (!resultadoNumero.getText().toString().isEmpty()) {
                if(isVirgula(button)){
                    return;
                }
                resetFields();
            }

            // caso não tenha operador digita no primeiro número, se não digita no segundo
            if (operadorNumero.getText().toString().isEmpty()) {
                processarConcatenarTextBotaoAoTextView(primeiroNumero, button);
            } else {
                processarConcatenarTextBotaoAoTextView(segundoNumero, button);
            }
            enableOrDisableIgualButton();
        }
    };

    // verifica se o botão é o 0
    private boolean isZero(Button button){
        return button.getText().toString().equals("0");
    }

    // verifica se o botão é o de virgula
    private boolean isVirgula(Button button){
        return button.getText().toString().equals(".");
    }

    // reseta os campos
    private void resetFields() {
        resultadoNumero.setText("");
        operadorNumero.setText("");
        primeiroNumero.setText("");
        segundoNumero.setText("");
        enableOrDisableIgualButton();
    }
}


