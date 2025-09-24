package com.acelerazg

import java.util.regex.Matcher
import java.util.regex.Pattern

class ExtratorRegex {
    private static Set<String> patternMatcher(String regex, String input, Set<String> exclusions = []) {
        Set<String> result = []
        Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input)
        while (matcher.find()) {
            String match = matcher.group()
            if (match && !exclusions.contains(match)) {
                result.add(match)
            }
        }
        return result
    }

    static Set<String> encontrarPalavrasComLetrasBr(String input) {
        return patternMatcher("[a-zÀ-ü]*[çãõ][a-zÀ-ü]*", input)
    }

    static Map<String, Set> encontrarEncontrosSilabicos(String input) {
        Set<String> tritongos = patternMatcher("[a-zÀ-ü]*(u[aeoiáàãâéêóõôíú][ua]|u[aeoáàãâéêóõôíú][aeiou])[a-zÀ-ü]*", input)
        Set<String> ditongos = patternMatcher("[a-zÀ-ü]*([aeoáéó]u|iu|ã[eo]|õe|ai(?![nr])|ei(?!r)|oi(?![nm])|ui(?![mr])|áu|éi|ói|ua|ue)[a-zÀ-ü]*", input, tritongos)
        Set<String> hiatos = patternMatcher("[a-zÀ-ü]*[aeiouáàãâéêíóõôú][aeiouáàãâéêíóõôú][a-zÀ-ü]*", input, tritongos + ditongos)

        Map<String, Set> encontros = [
                "Tritongos": tritongos,
                "Ditongos" : ditongos,
                "Hiatos"   : hiatos
        ]
        return encontros
    }

    static String limparPlurais(String input) {
        Set<String> nomesProprios = ["Carlos", "Thomas", "Matheus", "Mateus", "Nicholas", "Nicolas", "Luis", "Vinicius", "Elias", "Jonas",
                                     "Nícolas", "Tomás", "Anís", "Dionís", "Elias", "Hercules", "Jeremias", "Jesús", "Jonas", "Josias",
                                     "Judas", "Lucas", "Luís", "Marcos", "Moisés", "Tales", "Thales", "Tobias", "Zacarias", "Deus"]

        Pattern pattern = Pattern.compile("((?<= )[a-zà-ü]+(es|s)(?=\$|[\n,. ]))|((?<=^|\n)[a-zA-ZÀ-ü]+(es|s)(?=[ \$\n]))")
        Matcher matcher = pattern.matcher(input)

        StringBuffer sb = new StringBuffer()
        while (matcher.find()) {
            if (nomesProprios.contains(matcher.group())) {
                continue
            }
            matcher.appendReplacement(sb, "")
        }
        matcher.appendTail(sb)

        return sb.toString()
    }

    static Set<String> encontrarProparoxitonas(String input) {
        return patternMatcher("[a-zÀ-ü]*[bcdfghjklmnpqrstvwxyzç]?[áàâéèêíìúùóòô][bcdfghjklmnpqrstvwxyzç]?[bcdfghjklmnpqrstvwxyzç][aeiou][bcdfghjklmnpqrstvwxyzç][aeiou][lmnrsz]?(?=[ \$\n])", input)
    }

    static Set<String> encontrarFrasesComQuatroPalavras(String input) {
        return patternMatcher("(?<=\n)([a-zÀ-ü]+\\s){3}[a-zÀ-ü]+(?=\n|\$)", input)
    }

    static Map<String, Integer> encontrarFrasesRepetidas(String input) {
        ArrayList<String> frases = []
        Map<String, Integer> frasesRepetidas = [:]

        Pattern pattern = Pattern.compile("[a-zÀ-ü, ]+", Pattern.CASE_INSENSITIVE)
        Matcher matcher = pattern.matcher(input)

        while (matcher.find()) {
            String match = matcher.group()
            if (frases.contains(match)) {
                if (frasesRepetidas.get(match) != null) {
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
