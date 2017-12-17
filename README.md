# FACEBOOK GROUPS CONNECTOR

Este projeto foi descontinuado, seu último teste e compilação foi em Dezembro de 2015.
Last update, run test in 12/2015

Redes  sociais  como  o  Facebook tendem  cada  vez  mais  fazer  parte  do  cotidiano  das  pessoas, em especial dos estudantes ao passo que cresce a importância de transformá-las em ambientes educacionais,  já  que  estas  não  foram  projetadas  para  esta  finalidade.  Há  algumas  iniciativas para  tentar fazer  do  Facebook  uma  proposta  de  ferramenta  de  auxílio  à  educação  como  o FaceLearning,  desenvolvida  para  analisar  a  participação  e  desempenho  de  estudantes  em atividades educacionais na rede social. Entretanto, ele não utiliza um padrão de comunicação que  possibilite  a  integração  das  experiências  de  aprendizagem  geradas  pelos  estudantes  com ferramentas  de  analíticas  de  aprendizagem  ou  de  visualização  de  dados  de  terceiros.  A ferramenta  proposta  é  capaz  de  perceber  interações  dos  alunos  em grupos  do  Facebook  e extrair  dados  no  formato  Experience  API  (xAPI),  para  serem  analisadas  em  tempo  real  por ferramentas de Analíticas de Aprendizagem como o SmartLAK. O objetivo é desenvolver um serviço  web  integrado  com  a  rede  social  Facebook  para  extração  de  atividades como  posts, comentários  e  curtidas  realizadas  pelos  alunos  em  grupos  do  Facebook.  Esta  ferramenta  foi desenvolvida utilizando uma metodologia de revisão bibliográfica e uma abordagem bottom-up, isto é, da especificidade para o todo, fornecendo o conjunto de requisitos exigidos para a proposta. Como experimento, foi iniciado um projeto piloto utilizando a ferramenta proposta para  a  extração  das  experiências  de  aprendizagem  de  estudantes  da  turma de  Tecnologias Web do curso de Ciência da Computação da FACAPE no segundo semestre de 2015, e foram armazenadas   no repositório   da   arquitetura   SmartLAK,   cujo   papel   é   fornecer   serviços inteligentes de analíticas de aprendizagem. O processo ocorreu de forma transparente para os estudantes, que utilizaram normalmente a rede social através da plataforma web e aplicativos móveis. 
Desta forma espera-se com os dados coletados deduzir informações implícitas como desempenho   dos   estudantes,   avaliação   de   comportamento,   descoberta   de   formas   de aprendizagem dentre outros.

###### Tags: xAPI, Facebook, SmartLAK, Big Data, Learning Analytics

![Diagram](https://github.com/marcus2vinicius/facebook-groups-connector/blob/master/documentation/diagram.png)


# Instruções

1 - para executar cole isso no seu terminal
```shell
$ java -jar target/dependency/webapp-runner.jar target/*.war
```
### Publique no Heroku
[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

2 - para publicar basta clicar no botao do heroku
```shell
$ java -jar target/dependency/webapp-runner.jar target/*.war
```
Execução
```shel 
$ java -jar target/dependency/webapp-runner.jar target/*.war
```
