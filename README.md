HELDER SILVA DE FARIA





O desenvolvimento do desafio foi utilizando o Ubuntu 18.10 e para configurar o meu ambiente usei o script
install-dev-machine.sh, porém usar o script montagem-ambiente.sh já é o suficiente.

A aplicação foi feita no Intellij usando Springboot, Lombok, Mongodb, Docker e Docker-compose. 

Se caso queira rodar a aplicação pelo intellij e realizar modificações é preciso baixar o plugin do Lombok
(https://projectlombok.org/setup/intellij) e usa-lo no Intellij para que desse modo a IDE reconheça os metodos
get/set.

Há 2 formas de executar a aplicação:

  - Por linha de comando, basta rodar o script de nome runProject.sh que esta na raiz do projeto.
  - Pela IDE, executar apartir da classe HelderApplication

Caso haja necessidade de executar somente os testes lembre-se de conferir se o contenainer referente ao mongodb esta
rodando. Se preciso pode executar o script /docker/start-mongodb.sh.

Não sei se a expectativa fosse que houvesse um endpoint para extração dos dados do facebook. No meu entendimento eu
coloquei em uma rotina que é executada logo depois que os componentes Spring são inicializados.

Com o intuito de ajudar com a executação dos testes eu deixei no repositorio uma colection do POSTMAN
com alguns dos testes realizados por mim.
  
O projeto possui uma pasta docker na sua raiz, lá possui dois arquivos para a criação dos containeres do mongodb de
execução e de teste: docker-compose.yml e start-mongodb.sh.


