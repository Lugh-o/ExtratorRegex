package com.acelerazg

import spock.lang.Specification

class ExtratorRegexTest extends Specification {
    def "retorna palavras que contenham ç ã õ" () {
        given:
            String input = "aceitar fortuito milionário ideia dourar leão\n" +
                    "pães sou cai androide beijo noivo aula guardar\n" +
                    "ouço usufrui neurose irmão\n" +
                    "tatuei tatuou guaipé Uruguai Uruguaiana\n" +
                    "Uruguaianense uruguaio Paraguai paraguaio\n" +
                    "paraguaiense apaziguei iguais saguão \t\n" +
                    "fruir gaúcho viúvo moeda rainha piolho \n" +
                    "saúde hiato país iate coordenar jesuíta \n" +
                    "poeira voar caatinga fiel cultue dia\n" +
                    "tia egoísta graal reeleger conteúdo raiz"
        when:
            ArrayList<String> output = ExtratorRegex.encontrarPalavrasComLetrasBr(input)
        then:
            output == ["leão", "pães", "ouço", "irmão", "saguão"]
    }

    def "retorna ditongos, tritongos e hiatos" () {
        given:
            double precisaoEsperada = 0.9

            Set<String> ditongos = [
                    "aceitar", "fortuito", "milionário", "ideia", "dourar", "leão",
                    "pães", "sou", "cai", "androide", "beijo", "noivo",
                    "aula", "guardar", "ouço", "usufrui", "neurose", "irmão",
                    "aurora", "ruivo", "aipo", "automóvel", "degraus", "então",
                    "herói", "feiura", "aguentar", "ouro", "ferreiro", "pouco",
                    "doutrina", "pai", "mãe", "céu", "muito", "qual",
                    "linguiça", "frequente", "quase", "sei", "goela", "meu",
                    "azuis", "jaula", "boi", "poupa", "quadra", "põe",
                    "regiões", "viu", "papéis", "anéis", "igual", "iguana",
                    "cuidado", "cão", "tranquilo", "equestre", "enxaguando",
                    "quota", "coração", "oblíqua", "água", "maizena", "nasais",
                    "euforia", "orais", "não", "lingueta", "órfão", "capitães",
                    "áurea", "órgão", "cetáceo", "colônia", "tênue", "exímio"
            ]

            Set<String> tritongos = [
                    "tatuei", "tatuou", "guaipé",
                    "Uruguai", "Uruguaiana", "Uruguaianense",
                    "uruguaio", "Paraguai",
                    "paraguaio", "paraguaiense", "apaziguei",
                    "iguais", "saguão", "saguões", "baguais",
                    "guaiol", "linguais", "araguaianense",
                    "guaianá", "pontuais", "virtuais",
                    "guaiquica", "maçaquaia", "brasiguaio",
                    "guaipecada", "guaiaraúva", "uaipi",
                    "guaiaca", "guaiacã", "uaixima",
                    "uau", "guaiamu", "guaivira",
                    "guaipeca", "agueiro", "desaguou", "aguou",
                    "delinquiu", "delinquia", "saqueia",
                    "desiguais", "ruão", "adequou",
                    "insinuou", "descontinuou", "conceituou",
                    "situou", "minguou", "averiguei", "quão", "quais",
                    "quaisquer", "bueiro", "guaiacônico"
            ]

            Set<String> hiatos = [
                    "fruir", "gaúcho", "viúvo",
                    "moeda", "rainha", "piolho",
                    "saúde", "hiato", "país",
                    "iate", "coordenar", "jesuíta",
                    "poeira", "voar", "caatinga",
                    "fiel", "cultue", "dia",
                    "tia", "egoísta", "graal",
                    "reeleger", "conteúdo", "raiz",
                    "álcool", "coelho", "magoado",
                    "moinho", "proteína", "saída",
                    "caatinga", "auréola", "Piauí",
                    "cair", "flúor", "ciúme",
                    "ruim", "boemia", "poesia",
                    "Saara", "cafeína", "faísca",
                    "rio", "amendoim",
                    "boa", "ruído", "oceano",
                    "biologia", "baú",
                    "ainda", "criança", "cajuína",
                    "suor", "judaísmo", "frio",
                    "saí", "ataúde", "coar",
                    "tireoide", "ruína", "lua",
                    "voo", "teatro"
            ]

        String input = String.join(' ', ditongos) + ' ' + String.join(' ', tritongos) + ' ' + String.join(' ', hiatos)

        when:
            Map<String, Set> output = ExtratorRegex.encontrarEncontrosSilabicos(input)
        then:
            calculoPrecisao(output["Ditongos"], ditongos) >= precisaoEsperada
            calculoPrecisao(output["Tritongos"], tritongos) >= precisaoEsperada
            calculoPrecisao(output["Hiatos"], hiatos) >= precisaoEsperada
    }

    private static double calculoPrecisao(Set<String> encontrado, Set<String> esperado) {
        Set<String> intersecao = esperado.intersect(encontrado)
        return intersecao.size() / (double) esperado.size()
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
            String input = "ábaco ascético parêntese acadêmico esquálido pároco acústica estatística película afônico êxtase penúltimo acadêmicos também está buquê cipó dominó patê ninguém camelô sofá bebê perseguição girassóis Paraná além avó baú sabiá administração guaraná Goiás boné Alarido Amada Assado Azedo Bonita Calado Cerveja Disputa Elefante Eloquente Espalhafatosa Favorável Finamente Garganta Gestante Guerreira Hora Impossível Intragável Justa Labirinto Litro Medroso Molusco Nenhuma Nível Oculista Pelota Pinho Quando Ritmo Sopa Tanajura Tinindo Usina Virtude Voava Xarope Zarolho"
        when:
            ArrayList<String> output = ExtratorRegex.encontrarProparoxitonas(input)
        then:
            output == ["ábaco", "ascético", "parêntese", "acadêmico", "esquálido",
                       "pároco", "acústica", "estatística", "película",
                        "afônico", "êxtase", "penúltimo", "acadêmicos"]
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