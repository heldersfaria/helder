HELDER SILVA DE FARIA





O desenvolvimento do desafio foi utilizando o Ubuntu 18.10. 

Para a configuração do ambiente eu precisei rodar o script de instalação de nome montagem-ambiente.sh.

A aplicação foi feita no Intellij usando Springboot, Lombok, Mongodb, Docker e Docker-compose. 

Se caso queira rodar a aplicação pelo intellij e realizar modificações é preciso baixar o plugin do Lombok
(https://projectlombok.org/setup/intellij) e usa-lo no Intellij para que desse modo a IDE reconheça algumas 
partes do codigo.

Há 2 formas de executar a aplicação:

  - Por linha de comando, basta rodar o script de nome runProject.sh que esta na raiz do projeto.
  - Pela IDE, executar apartir da classe HelderApplication

Caso haja necessidade de executar somente os testes lembre-se de conferir se o contenainer referente ao mongodb esta
rodando. Se preciso pode executar o script /docker/start-mongodb.sh.
  
Com o intuito de ajudar com a executação dos testes eu deixei no repositorio uma colection do POSTMAN
com alguns dos testes realizados por mim.
  
O projeto possui uma pasta docker na sua raiz, lá possui dois arquivos para a criação do container do mongodb:
docker-compose.yml e start-mongodb.sh.

Foi feito uma tentativa, porém sem sucesso de subir a aplicação em um container e o Mongodb em outro.
Hoje eu não sei o motivo, mas o container da aplicação não esta comunicando com o Mongodb. Esse foi um dos 
exemplos que eu segui https://github.com/aritnag/spring-boot-mongo-kubernetes-docker/blob/master/docker-compose.yml
