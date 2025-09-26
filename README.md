# Extrator RegEx

Programa feito com o intuito de aprimorar meus estudos com RegEx, realizando diversas operações complexas utilizando essa ferramenta.

[Video explicando o projeto](https://www.youtube.com/watch?v=v2CqigVxQ6k)

## Funcionalidades

- Lista palavras que contenham ç, ã ou õ
- Retorna frases que se repetem e quantas vezes se repetem
- Identifica:
    - Todas as palavras que contem Ditongos
    - Todas as palavras que contem Tritongos
    - Todas as palavras que contem Hiatos
- Identifica frases de exatamente 4 palavras
- Identifica palavras proparoxítonas
- Limpa o texto removendo todas as palavras no plural

Obs: A identificação de encontros silábicos é realizada de maneira aproximada (assumi precisão satisfatória de 90%), devido à impossibilidade de ter certeza disso apenas com grafia (as vezes até com fonética é ambíguo).

## Requisitos

- Java 8+
- Groovy 4.0.14+

## Como Executar

Após o clone, entre na pasta:

```
cd ExtratorRegex
```

Para compilar o projeto:

```
./gradlew build
```

Para executar os testes unitários:

```
./gradlew test
```

Para executar a aplicação principal:

```
java -jar build/libs/ExtratorRegex-1.0-SNAPSHOT.jar 
```

O programa Main irá então executar todos os métodos implementados com a música Construção, de Chico Buarque.
