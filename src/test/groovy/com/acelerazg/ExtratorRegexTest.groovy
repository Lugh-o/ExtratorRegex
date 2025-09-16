package com.acelerazg

import spock.lang.Specification

class ExtratorRegexTest extends Specification {
    def "retorna palavras que contenham ç ã õ" () {
        given:
            String input = "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCansei de repetir tanta frase"
        when:
            ArrayList<String> output = ExtratorRegex.encontrarPalavrasComLetrasBr(input)
        then:
            output == ["põe", "manhãs", "lenços"]
    }

    def "retorna ditongos, tritongos e hiatos" () {
        given:
            String input = "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCansei de repetir tanta frase"
        when:
            Map<String, Set> output = ExtratorRegex.encontrarEncontrosSilabicos(input)
        then:
            output == [
                "Tritongos": ["uruguaios"] as Set,
                "Ditongos": ["põe", "quebra", "Cansei"] as Set,
                "Hiatos": ["saída"] as Set
            ]
    }

    def "limpa palavras no plural com final s es" () {
        given:
            String input = "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCansei de repetir tanta frase"
        when:
            String output = ExtratorRegex.limparPlurais(input)
        then:
            output == "Carlos põe a saída  ,    e  \nTestando quebra de linha\nCansei de repetir tanta frase"
    }

    def "retorna proparoxítona" () {
        given:
            String input = "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCansei de repetir tanta frase"
        when:
            ArrayList<String> output = ExtratorRegex.encontrarProparoxitonas(input)
        then:
            output == ["médicas"]
    }

    def "identifica frases de exatamente quatro palavras" () {
        given:
            String input = "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCansei de repetir tanta frase"
        when:
            ArrayList<String> output = ExtratorRegex.encontrarFrasesComQuatroPalavras(input)
        then:
            output == ["Testando quebra de linha"]
    }

    def "identifica frases que se repetem e quantas vezes se repetem" () {
        given:
            String input = "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCarlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços\nTestando quebra de linha\nCansei de repetir tanta frase"
        when:
            Map<String, Integer> output = ExtratorRegex.encontrarFrasesRepetidas(input)
        then:
            output == [
                    "Carlos põe a saída dos uruguaios, nas manhãs médicas e nos lenços": 2,
                    "Testando quebra de linha": 2
            ]
    }
}