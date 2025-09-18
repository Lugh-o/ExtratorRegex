package com.acelerazg

import java.util.regex.Matcher
import java.util.regex.Pattern

class ExtratorRegex {
    static ArrayList<String> encontrarPalavrasComLetrasBr(String input) {
        Pattern pattern = Pattern.compile("[a-zÀ-ü]*[çãõ][a-zÀ-ü]*", Pattern.CASE_INSENSITIVE)
        Matcher matcher = pattern.matcher(input)
        ArrayList<String> palavras = []
        while (matcher.find()) {
            palavras.add(matcher.group())
        }
        return palavras
    }

    static Map<String, Set> encontrarEncontrosSilabicos(String input) {
        Set<String> tritongos = []
        Set<String> ditongos = []
        Set<String> hiatos = []

        Pattern patternTritongos = Pattern.compile("([a-zÀ-ü]*u[aeoáàãâéêóõôíú][aeiou][a-zÀ-ü]*)|([a-zÀ-ü]*u[aeoiáàãâéêóõôíú][ua][a-zÀ-ü]*)", Pattern.CASE_INSENSITIVE)
        Matcher matcherTritongos = patternTritongos.matcher(input)
        while (matcherTritongos.find()) {
            String match = matcherTritongos.group()
            if (match) tritongos.add(match)
        }

        Pattern patternHiatos = Pattern.compile("[a-zÀ-ü]+([eou][aeãíú]|([aeiou])\\2|úo|[ai][íuiú]|i[aeo]|oi)[a-zÀ-ü]*", Pattern.CASE_INSENSITIVE)
        Matcher matcherHiatos = patternHiatos.matcher(input)

        while (matcherHiatos.find()) {
            String match = matcherHiatos.group()
            if(match && !tritongos.contains(match)) {
                hiatos.add(match)
            }
        }
        Pattern patternDitongos = Pattern.compile("[a-zÀ-ü]*([aeoáéó][iu]|u[ae]|ui|iu|ã[eo]|[õ]e|eo)[a-zÀ-ü]*", Pattern.CASE_INSENSITIVE)
        Matcher matcherDitongos = patternDitongos.matcher(input)
        while (matcherDitongos.find()) {
            String match = matcherDitongos.group()
            if(match && !ditongos.contains(match) && !tritongos.contains(match)) {
                ditongos.add(match)
            }
        }

        Map<String, Set> encontros = [
            "Tritongos": tritongos,
            "Ditongos": ditongos,
            "Hiatos": hiatos
        ]
        return encontros
    }

    static String limparPlurais(String input){
        Set<String> nomesProprios = ["Carlos", "Thomas", "Matheus", "Mateus", "Nicholas", "Nicolas", "Luis","Vinicius", "Elias", "Jonas",
                                     "Nícolas", "Tomás", "Anís", "Dionís", "Elias", "Hercules", "Jeremias","Jesús", "Jonas", "Josias",
                                     "Judas", "Lucas", "Luís", "Marcos", "Moisés", "Tales", "Thales", "Tobias", "Zacarias", "Deus"]
        Pattern pattern = Pattern.compile("((?<= )[a-zà-ü]+(es|s)(?= |\$|\\n|,))|((?<=^|\\n)[a-zA-ZÀ-ü]+(es|s)(?= |\$|\\n))")
        Matcher matcher = pattern.matcher(input)

        StringBuffer sb = new StringBuffer()
        while(matcher.find()){
            if(nomesProprios.contains(matcher.group())) {
                continue
            }
            matcher.appendReplacement(sb, "")
        }
        matcher.appendTail(sb)

        return sb.toString()
    }

    static ArrayList<String> encontrarProparoxitonas(String input){
        Set<String> resultados = []
        Pattern pattern = Pattern.compile("[a-zA-zÀ-ü]*[bcdfghjklmnpqrstvwxyzç]?[áàâéèêíìúùóòô][bcdfghjklmnpqrstvwxyzç]?[bcdfghjklmnpqrstvwxyzç][aeiou][bcdfghjklmnpqrstvwxyzç][aeiou][bcdfghjklmnpqrstvwxyzç]?(?= |\$|\\n)", Pattern.CASE_INSENSITIVE)
        Matcher matcher = pattern.matcher(input)

        while (matcher.find()) {
            String match = matcher.group()
            if(match) resultados.add(match)
        }
        return resultados
    }

    static ArrayList<String> encontrarFrasesComQuatroPalavras(String input){
        ArrayList<String> frases = []
        Pattern pattern = Pattern.compile("(?<=\\n)([a-zÀ-ü]+\\s){3}[a-zÀ-ü]+(?=\\n|\$)", Pattern.CASE_INSENSITIVE)
        Matcher matcher = pattern.matcher(input)
        while (matcher.find()) {
            String match = matcher.group()
            if(match) frases.add(match)
        }
        return frases
    }

    static Map<String, Integer> encontrarFrasesRepetidas(String input){
        ArrayList<String> frases = []
        Map<String, Integer> frasesRepetidas = [:]

        Pattern pattern = Pattern.compile("[a-zA-ZÀ-ü, ]+")
        Matcher matcher = pattern.matcher(input)

        while(matcher.find()){
            String match = matcher.group()
            if(frases.contains(match)){
                if(frasesRepetidas.get(match) != null) {
                    frasesRepetidas.put(match, frasesRepetidas.get(match) + 1)
                } else {
                    frasesRepetidas.put(match, 2)
                }
            } else {
                frases.add(match)
            }
        }
        return frasesRepetidas
    }
}
