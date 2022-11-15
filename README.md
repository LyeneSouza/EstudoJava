# DESAFIO DO MÓDULO 2 #

Programação Orientada a Objetos com Java, Testes Unitários e JUnit.

## Desafio ##

O projeto contempla uma Loja Online *Simples*.

### Descrição ###
A loja contém um menu simples a partir do qual é possível adicionar produtos, editar produtos e excluir produtos, assim como importar mostruário de fábrica.

### O que você precisa ter instalado para rodar a aplicação `versões utilizadas no desenvolvimento` ###
- Java JDK 1.8 `Java version: 1.8.0_202`
- Git `git version 2.36.1.windows.1`
- Maven `Apache Maven 3.8.6`

### Como rodar a aplicação e suas dependências ###
- Fazer o clone do projeto e entrar na respectiva pasta
```shell
cd "diretório de sua preferência"
git clone https://LyeneS@bitbucket.org/lyenes/desafio-qa-modulo2.git
cd desafio-qa-modulo2/Desafio2/
```
- Compilar a aplicação via Maven
```shell
mvn compile
```
- Rodar a aplicação via Maven
```shell
mvn exec:java -Dexec.mainClass=aplicacao.Programa
```
- Rodar os testes unitários via Maven
```shell
mvn test
```


