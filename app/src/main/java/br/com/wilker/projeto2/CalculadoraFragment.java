package br.com.wilker.projeto2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.wilker.projeto2.helpers.TipoOperador;

/**
 * Created by Wilker on 06/06/2018.
 */

public class CalculadoraFragment extends Fragment {

    TextView primeiroNumero;
    TextView segundoNumero;
    TextView operadorNumero;
    TextView resultadoNumero;
    Button buttonIgual;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calculadora, container, false);
        primeiroNumero = view.findViewById(R.id.calculadora_numero_um);
        segundoNumero = view.findViewById(R.id.calculadora_numero_dois);
        operadorNumero = view.findViewById(R.id.calculadora_operador);
        resultadoNumero = view.findViewById(R.id.calculadora_resultado);
        bindarButoesNumeros();
        bindarBotoesOperadores();

        buttonIgual = view.findViewById(R.id.calculadora_button_igual);
        buttonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarCalculo();
            }
        });

        Button buttonReset = view.findViewById(R.id.calculadora_button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        Button buttonApagar = view.findViewById(R.id.calculadora_button_apagar);
        buttonApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resultadoNumero.getText().toString().isEmpty()) {
                    resetFields();
                }
                if (operadorNumero.getText().toString().isEmpty()) {
                    if(primeiroNumero.getText().length() > 0){
                        apagarCharDoTextView(primeiroNumero);
                    }
                } else {
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

    public void enableOrDisableIgualButton() {
        if (primeiroNumero.getText().toString().isEmpty() || segundoNumero.getText().toString().isEmpty() || operadorNumero.getText().toString().isEmpty()) {
            buttonIgual.setEnabled(false);
        } else {
            buttonIgual.setEnabled(true);
        }
    }

    public void realizarCalculo() {
        if(!resultadoNumero.getText().toString().isEmpty()){
            primeiroNumero.setText(resultadoNumero.getText());
        }
        TipoOperador tipoOperador = TipoOperador.getByOperador(operadorNumero.getText().toString());
        Double resultado = tipoOperador.calcular(getDoubleValueFrom(primeiroNumero), getDoubleValueFrom(segundoNumero));
        resultadoNumero.setText(resultado.toString());
        enableOrDisableIgualButton();
    }

    private double getDoubleValueFrom(TextView textView) {
        return Double.valueOf(textView.getText().toString());
    }

    private void bindarButoesNumeros() {
        int[] idBotoesNumeros = {R.id.calculadora_button_virgula, R.id.calculadora_button0, R.id.calculadora_button1, R.id.calculadora_button2, R.id.calculadora_button3, R.id.calculadora_button4, R.id.calculadora_button5, R.id.calculadora_button6, R.id.calculadora_button7, R.id.calculadora_button8, R.id.calculadora_button9};
        bindarOnClick(botaoNumeroOnClickListerner, idBotoesNumeros);
    }

    private void bindarBotoesOperadores() {
        int[] idBotoesOperadores = {R.id.calculadora_button_porcentagem, R.id.calculaora_button_divisao, R.id.calculadora_button_multiplicacao, R.id.calculadora_button_menos, R.id.calculadora_button_mais};
        bindarOnClick(botaoOperadorOnClickListener, idBotoesOperadores);
    }

    public void bindarOnClick(View.OnClickListener onClickListener, int... botoesIds) {
        for (int i = 0; i < botoesIds.length; i++) {
            Button botao = view.findViewById(botoesIds[i]);
            botao.setOnClickListener(onClickListener);
        }
    }

    private void apagarCharDoTextView(TextView textView) {
        textView.setText(textView.getText().subSequence(0, textView.getText().length() - 1).toString());
    }

    private void concatenarTextBotaoAoTextView(TextView textView, Button button) {
        String novoTexto  = textView.getText().toString();
        if(isZero(button) && novoTexto.length() == 1 && novoTexto.equals("0")){
            return;
        }
        if(isVirgula(button)){
            if(textView.getText().length() == 0 ){
                return;
            }
            if(textView.getText().toString().contains(".")){
                novoTexto = novoTexto.replace(".", "");
            }
        }
        novoTexto += button.getText().toString();
        textView.setText(novoTexto);
    }

    private View.OnClickListener botaoOperadorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!segundoNumero.getText().toString().isEmpty() && resultadoNumero.getText().toString().isEmpty()){
                realizarCalculo();
            }
            if (!resultadoNumero.getText().toString().isEmpty()) {
                primeiroNumero.setText(resultadoNumero.getText());
                segundoNumero.setText("");
                resultadoNumero.setText("");
            }
            operadorNumero.setText(((Button) view).getText());
        }
    };

    private View.OnClickListener botaoNumeroOnClickListerner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;

            if (!resultadoNumero.getText().toString().isEmpty()) {
                if(isVirgula(button)){
                    return;
                }
                resetFields();
            }
            if (operadorNumero.getText().toString().isEmpty()) {
                concatenarTextBotaoAoTextView(primeiroNumero, button);
            } else {
                concatenarTextBotaoAoTextView(segundoNumero, button);
            }
            enableOrDisableIgualButton();
        }
    };

    private boolean isZero(Button button){
        return button.getText().toString().equals("0");
    }
    private boolean isVirgula(Button button){
        return button.getText().toString().equals(".");
    }
    private void resetFields() {
        resultadoNumero.setText("");
        operadorNumero.setText("");
        primeiroNumero.setText("");
        segundoNumero.setText("");
        enableOrDisableIgualButton();
    }
}


