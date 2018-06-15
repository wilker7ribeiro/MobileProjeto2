package br.com.wilker.projeto2.helpers;

/**
 * Created by Wilker on 15/06/2018.
 */

public enum TipoOperador implements Operador {
    ADICAO("+") {
        @Override
        public double calcular(double a, double b) {
            return a + b;
        }
    },
    SUBTRACAO("-") {
        @Override
        public double calcular(double a, double b) {
            return a - b;
        }
    },
    MULTIPLICACAO("×") {
        @Override
        public double calcular(double a, double b) {
            return a * b;
        }
    },
    DIVISAO("÷") {
        @Override
        public double calcular(double a, double b) {
            return a / b;
        }
    },
    PORCENTAGEM("%") {
        @Override
        public double calcular(double a, double b) {
            return (b * a) / 100;
        }
    };
    private final String operador;

    TipoOperador(final String operador) {
        this.operador = operador;
    }

    public static TipoOperador getByOperador(String operador) {
        for (TipoOperador tipoOperador : TipoOperador.values()) {
            if (tipoOperador.operador.equalsIgnoreCase(operador)) {
                return tipoOperador;
            }
        }
        return null;
    }
}
