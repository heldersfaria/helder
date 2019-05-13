#!/usr/bin/env bash

	sudo rm /usr/bin/java
	sudo rm -R jdk1.8.0_211/
	sudo rm *-2.tar.gz
	sudo rm -R /usr/lib/jvm/
	sudo rm /etc/profile.d/jdk-8u211.sh

	sudo cp jdk-8u211-linux-x64.tar.gz jdk-8u211-linux-x64-2.tar.gz
	tar -xf ./jdk-8u211-linux-x64.tar.gz

	sudo mkdir -p /usr/lib/jvm/
	sudo cp -r ./jdk1.8.0_211 /usr/lib/jvm/

	sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.8.0_211/bin/java" 1
	sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.8.0_211/bin/javac" 1
	sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.8.0_211/bin/javaws" 1

	touch jdk-8u211.sh 

	echo "export PATH=$PATH:/usr/lib/jvm/jdk1.8.0_211/bin
	export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_211
	export JRE_HOME=/usr/lib/jvm/jdk1.8.0_211/jre/
	export J2SDKDIR=/usr/lib/jvm/jdk1.8.0_211
	export J2REDIR=/usr/lib/jvmjdk1.8.0_211/jre" >> ./jdk-8u211.sh 

	sudo chmod 644 jdk-8u211.sh 
	sudo chown root:root jdk-8u211.sh

	sudo mv ./jdk-8u211.sh  /etc/profile.d/jdk-8u211.sh

	sudo rm -R jdk1.8.0_211/
	sudo rm *-2.tar.gz


	wget $(curl https://services.gradle.org/versions/current | jq -r .downloadUrl)
	sudo rm -Rf /usr/local/gradle-*
	sudo unzip gradle-*-bin.zip -d /usr/local
	(cd /usr/local/bin && sudo ln -sf /usr/local/gradle-$(curl https://services.gradle.org/versions/current | jq -r .version)/bin/gradle gradle)
	sudo rm /etc/profile.d/gradle.sh
	sudo touch /etc/profile.d/gradle.sh
	sudo bash -c "echo export GRADLE_HOME=/usr/local/gradle-$(curl https://services.gradle.org/versions/current | jq -r .version) >> /etc/profile.d/gradle.sh"
	export GRADLE_HOME=/usr/local/gradle-$(curl https://services.gradle.org/versions/current | jq -r .version)
	rm -f gradle-*-bin.zip


	wget $(curl https://services.gradle.org/versions/current | jq -r .downloadUrl)
	sudo rm -Rf /usr/local/gradle-*
	sudo unzip gradle-*-bin.zip -d /usr/local
	(cd /usr/local/bin && sudo ln -sf /usr/local/gradle-$(curl https://services.gradle.org/versions/current | jq -r .version)/bin/gradle gradle)
	sudo rm /etc/profile.d/gradle.sh
	sudo touch /etc/profile.d/gradle.sh
	sudo bash -c "echo export GRADLE_HOME=/usr/local/gradle-$(curl https://services.gradle.org/versions/current | jq -r .version) >> /etc/profile.d/gradle.sh"
	export GRADLE_HOME=/usr/local/gradle-$(curl https://services.gradle.org/versions/current | jq -r .version)
	rm -f gradle-*-bin.zip

	# Virtualbox
	sudo apt-get install virtualbox -y

	# docker
	sudo apt-get update
	sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
	sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'
	sudo apt-get update
	apt-cache policy docker-engine
	sudo apt-get install docker-engine -y
	sudo usermod -a -G docker $(whoami)

	# docker-compose
	sudo curl -L "https://github.com/docker/compose/releases/download/$(curl -s https://github.com/docker/compose/releases/latest | egrep -o '[0-9]+.[0-9]+.[0-9]+')/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
	sudo chmod +x /usr/local/bin/docker-compose

	# IntelliJ IDEA
    wget https://download.jetbrains.com/idea/ideaIC-2018.3.2.tar.gz
    sudo rm -Rf /opt/idea-IC-*/
    sudo tar -xvf ideaIC-* -C /opt/
    /opt/idea-IC-*/bin/idea.sh &
    rm ideaIC-*.tar.gz


