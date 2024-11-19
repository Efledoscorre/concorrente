
  <h2 align="center">Visão geral do projeto</h2>

  <p align="center">
    Programa feito em java utilizando um sistema de busca distribuído utilizando sockets, o sistema é formado por dois servidores (A e B), cada um responsável por uma busca em metade dos dados de artigos científicos. 
    <br>
    </p>
</p>

<br>


## Guia do readme
- [Descrição](#descrição)
- [Como utilizar](#como-utilizar)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Contribuidores](#contribuidores)

<hr>

### Descrição

Projeto realizado em Java 17 para representar um sistema de busca distribuído utilizando sockets. O sistema
é formado por dois servidores (A e B) cada um responsável por uma busca em metade de um
dado de artigos científicos do arXiv (https://paperswithcode.com/dataset/arxiv-10).
Um dos servidores (A) recebe a solicitação de busca do cliente, contendo uma substring de um
possível título ou introdução de um artigo, envia ao segundo servidor (B) e em seguida ambos
realizam a busca no dado designado a cada um.

<hr>




### Como utilizar

 Para utilizar o projeto, siga os passos abaixo:

### Você precisará ter as seguintes ferramentas instaladas:

 Java 17: Versão mínima necessária para compilar e rodar o projeto.

 IntelliJ IDEA: IDE recomendada para facilitar o desenvolvimento e execução do projeto.

 Projeto realizado no windows 11

2. ### Links para download: 
    ### [Java](https://www.java.com/pt-BR/)
    ### [Intellij](https://www.jetbrains.com/pt-br/idea/)


### Clone o repositório:

* Em um terminal ou diretamente pelo IntelliJ, clone o repositório do projeto:

* git clone https://github.com/Efledoscorre/concorrente
### importe o projeto no IntelliJ:


* Abra o IntelliJ IDEA.

* Vá em File > Open... e navegue até o diretório onde você clonou o projeto.

* Selecione o diretório do projeto e clique em OK.

* Certifique-se de que o SDK está configurado para o Java 17. Isso pode ser feito clicando com o botão direito no projeto, selecionando Project Structure e verificando a versão do SDK.
### Execute o projeto:

* No painel lateral do IntelliJ, navegue até a classe Main (src/Main.java).
* Clique com o botão direito na classe Main e selecione Run 'Main.main()'.
* O programa será compilado e executado, e os resultados serão exibidos no console da IDE.

<hr>

### Tecnologias utilizadas:

  <hr>

### Contribuidores

<a href="https://github.com/Efledoscorre/concorrente/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Efledoscorre/concorrente"/>
</a>




