# DESAFIO DO MÓDULO 1 #

Programação Orientada a Objetos com Java, Testes unitários e JUnit.

## Desafio ##

O impulsionando deverá construir um Loja Online *Simples* utilizando os conhecimentos adquiridos durante o módulo.

### Descrição ###
Sempre que você tiver um produto que deseja vender, terá um site que o promoverá da melhor maneira possível. Você precisa ter certeza de que as informações da página são relevantes, simples de entender e destacam todos os recursos incríveis do produto para aumenter as vendas.
Em uma loja virtual, deve ser possível gerenciar os produtos disponiveis e os seus dados, ou seja, o sistema da loja deve permitir de forma simples e rápida a inclusão, exclusão e edição de produtos. Nesta etapa do desafio, devemos desenvolver as funcionalidades que executem essa importante tarefa de negócio.

### Requisitos e Funcionalidades Esperadas ###
- [ ] Construir uma aplicação Java *Standalone* que execute por linha de comando.
- [ ] As funcionalidades deveráo ter cobertura de testes unitários.
- [ ] Ao iniciar a aplicação, exibir um menu numerado com as seguintes opções:
    - [ ] 1. Adicionar Novo Produto: Permitir incluir um novo item na lista de produtos da loja. Ao selecionar essa opção solicitar os seguintes dados para o usuário: nome, preço, quantidade em estoque e categoria.
    - [ ] 2. Editar Produto: Permitir editar os dados de um produto. Ao selecionar essa opção, deverá listar todos os produtos cadastrados e o usuário poderá selecionar um para edição dos dados previamente cadastrados.
    - [ ] 3. Excluir Produto: Listar todos os produtos cadastrados e permitir selecionar um para apagar. Deverá sempre haver pelo menos um produto na lista.
    - [ ] 4. Importar Mostruário da Fábrica: Muitas vezes, lojas virtuais recebem das fábricas arquivos com os dados dos produtos. Ao selecionar essa opção, a aplicação deverá ser um arquivo .csv (ver exemplo no repositório) que deve ser indicado pelo usuário, tratar os dados e gravar na lista de produtos.
        - [ ] Cálculo do valor final do produto: valor bruto + imposto + margem de lucro de 45%
    - [ ] 5. Sair: opção para o usuário encerrar o sistema.
- [ ] Qualquer manipulação dos dados deverá ser armazenado no arquivo CSV.
- [ ] Após o usuário efetuar alguma das operações acima (das opções de 1-4), a aplicação deverá voltar para o menu inicial.
- [ ] Cada opção do menu, deve ter uma opção de "Cancelar" a operação e voltar para o menu inicial, sem promover alteração dos dados.
- [ ] Não pode ser usado Lombok.
- [ ] Criar um README.md contendo as orientações para rodar a aplicação e suas dependências pela linha de comando.


### Bônus ###
* Utilização de Design Patterns. Ex.: Builder, Singleton, etc.
* Utilização de Maven ou Gradle
* Utilização de String Boot Standalone

### Material de Apoio ###
* [Java4Noobs](https://github.com/paulorievrs/java4noobs)

### Instruções para Realização do Desafio ###
1. Faça um *fork* desse repositório para a sua conta
2. Implemente a sua solução conforme solicitado
3. Compartilhe o link do projeto com o seu mentor 

***
## Critérios de Avaliação ##
* A aplicação está funcionando?
* A aplicação atende os requisitos solicitados?
    * O menu é apresentado corretamente?
    * É possível incluir um novo produto?
    * É possível editar os dados de um produto?
    * É possível excluir um produto?
    * A importação de arquivo .csv funciona corretamente?
    * Existe a opção de "Cancelar" uma operação? Ex.: Após selecionar editar um produto, é possível "Cancelar"
    * Após realizar alguma operaçào, a aplicação volta para o menu inicial?
* Pode ser claramente entendido o que o código faz?
* O Código é fácil de entender?
* A estrutura do código é modular o suficiente?
* A solução escolhida atende aos requisitos?
* Como está o desempenho? Existe algum código que afeterá a performance?
* O código funciona como esperado?
* As classes e pacotes estão segregados por responsabilidade ou funcionalidade?
* Os métodos ou contrutores estão recebendo muitos argumentos por parâmetros?
* O código da aplicação está com nomes intuitivos de funções, variáveis ou classes?
* O escopo das variáveis de classes estão sendo utilizados de forma adequada?
* Existe cobertura de teste unitários de cenário feliz e cenário de erros para as funcionalidades desenvolvidas?
* A aplicação apresenta bugs?
* Existe um correto tratamento de erros e exceções?
* A aplicação possui logs úteis?
* Existe um readme contendo instruções de como rodar a aplicação?