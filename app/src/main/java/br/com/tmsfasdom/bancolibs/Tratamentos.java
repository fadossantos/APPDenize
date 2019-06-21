package br.com.tmsfasdom.bancolibs;


public class Tratamentos {

    public static String RetornaLinhaDigitavel(String barra) {
        // Remover caracteres não numéricos.
        String linha = barra.replaceAll("[^0-9]", "");

        if (linha.length() != 44) {
            return null; // 'A linha do Código de Barras está incompleta!'
        }

        String campo1 = linha.substring(0, 4) + linha.substring(19, 20) + '.' + linha.substring(20, 24);
        String campo2 = linha.substring(24, 29) + '.' + linha.substring(29, 34);
        String campo3 = linha.substring(34, 39) + '.' + linha.substring(39, 44);
        String campo4 = linha.substring(4, 5); // Digito verificador
        String campo5 = linha.substring(5, 19); // Vencimento + Valor

        if (modulo11Banco(linha.substring(0, 4) + linha.substring(5, 44)) != Integer.valueOf(campo4)) {
            return null; //'Digito verificador '+campo4+', o correto é '+modulo11_banco(  linha.substr(0,4)+linha.substr(5,99)  )+'\nO sistema não altera automaticamente o dígito correto na quinta casa!'
        }
        return campo1 + modulo10(campo1) + ' ' + campo2 + modulo10(campo2) + ' ' + campo3 + modulo10(campo3) + ' ' + campo4 + ' ' + campo5;
    }

    public static int modulo10(String num)

    {
        num = num.replaceAll("[^0-9]", "");
        //variáveis de instancia
        int soma = 0;
        int resto;
        int dv;
        String[] numeros = new String[num.length() + 1];
        int multiplicador = 2;
        String aux;
        String aux2;
        String aux3;

        for (int i = num.length(); i > 0; i--) {
            //Multiplica da direita pra esquerda, alternando os algarismos 2 e 1
            if (multiplicador % 2 == 0) {
                // pega cada numero isoladamente
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * 2);
                multiplicador = 1;
            } else {
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)));
                multiplicador = 2;
            }
        }

        // Realiza a soma dos campos de acordo com a regra
        for (int i = (numeros.length - 1); i > 0; i--) {
            aux = String.valueOf(Integer.valueOf(numeros[i]));

            if (aux.length() > 1) {
                aux2 = aux.substring(0, aux.length() - 1);
                aux3 = aux.substring(aux.length() - 1, aux.length());
                numeros[i] = String.valueOf(Integer.valueOf(aux2) + Integer.valueOf(aux3));
            } else {
                numeros[i] = aux;
            }
        }

        //Realiza a soma de todos os elementos do array e calcula o digito verificador
        //na base 10 de acordo com a regra.
        for (int i = numeros.length; i > 0; i--) {
            if (numeros[i - 1] != null) {
                soma += Integer.valueOf(numeros[i - 1]);
            }
        }
        resto = soma % 10;
        dv = 10 - resto;
        if (dv == 10)
        {
            dv = 0;
        }
        //retorna o digito verificador
        return dv;
    }


    public static int modulo11Banco(String num) {
        num = num.replaceAll("[^0-9]", "");

        //variáveis de instancia
        int soma = 0;
        int resto;
        int dv;
        String[] numeros = new String[num.length() + 1];
        int multiplicador = 2;

        for (int i = num.length(); i > 0; i--) {
            //Multiplica da direita pra esquerda, incrementando o multiplicador de 2 a 9
            //Caso o multiplicador seja maior que 9 o mesmo recomeça em 2
            if (multiplicador > 9) {
                // pega cada numero isoladamente
                multiplicador = 2;
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
                multiplicador++;
            } else {
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
                multiplicador++;
            }
        }

        //Realiza a soma de todos os elementos do array e calcula o digito verificador
        //na base 11 de acordo com a regra.
        for (int i = numeros.length; i > 0; i--) {
            if (numeros[i - 1] != null) {
                soma += Integer.valueOf(numeros[i - 1]);
            }
        }
        resto = soma % 11;
        dv = 11 - resto;
        if (dv == 0 || dv == 10 || dv == 11) {
            dv = 1;
        }
        //retorna o digito verificador
        return dv;
    }

    public static int retornaTipoBoleto(String barras) {
        // retorna 1 se posicao 20 a 25 = 0; 2 se posicao 20 a 25 <> 0 e 43 a 44
        // = 21; 3 se se posicao 20 a 25 <> 0 e 43 a 44 <> 21

        String string1 = barras.substring(19, 25);
        String string2 = barras.substring(42, 44);
        if (string1.equalsIgnoreCase("000000")) {
            return 1;
        } else {
            //System.out.println(barras.substring(42, 44));
            if (string2.equalsIgnoreCase("21")) {
                return 2;
            } else {
                if (string2.equalsIgnoreCase("21")) {
                    return 3;
                }
            }
        }

        return 0;
    }

    public static String retornaNossoNumero(String barras, int tipoBoleto){
        switch(tipoBoleto){
            case 1: return barras.substring(25,42);
            case 2: return barras.substring(25,42);
            case 3: return barras.substring(19,30);
        }

        return null;
    }

    public static String retornaValor(String barras){
        return barras.substring(9,19);
    }

    public static String retornaCarteira(String barras){
        return barras.substring(42,44);
    }

    public static String retornaAgenciaConta(String barras){
        return "Agencia: " + barras.substring(30,34) + " Conta: " + barras.substring(34,42);
    }

}
