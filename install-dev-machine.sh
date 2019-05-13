#!/usr/bin/env bash

sudo echo $HOME

function aptRepositoryAndSourceList(){
    
	sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
	sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main' -y
	sudo add-apt-repository ppa:linrunner/tlp -y 
	sudo add-apt-repository ppa:linuxuprising/apps -y 
	sudo add-apt-repository ppa:otto-kesselgulasch/gimp -y 
}

function updateEnvironment(){
	sudo apt-get update
	sudo apt full-upgrade -f -y
	sudo apt autoremove -f --purge -y  
	sudo apt install --fix-broken -f -y 
}

function download_deb_files(){

	#chrome
	wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb &

	#DBeaver
	wget -q http://dbeaver.jkiss.org/files/dbeaver-ce_latest_amd64.deb &

	#gitkraken
	wget -q https://release.axocdn.com/linux/gitkraken-amd64.deb &
	
	#teamviewer
	wget -q https://download.teamviewer.com/download/linux/teamviewer_amd64.deb 
}

function installnodejs()
{
	# npm / serverless
	curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
	sudo apt-get install nodejs build-essential -y
	sudo npm install -g serverless
	sudo chown -R $USER:$(id -gn $USER) ~/.config
}

function installJDKGRADLE(){

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
}

function cleanDownloadedFiles(){
	sudo rm -f ./wget-log* &
	sudo rm -f ./-q &
	sudo rm -f *.deb* *.tar.gz* *-bin.zip* & 
}

function cleaning(){
    	cleanDownloadedFiles &

	#remove firefox, thunderbird, amazon
	sudo apt autoremove firefox thunderbird ubuntu-web-launchers docker docker-engine docker.io openjdk* -f --purge -y 

	#remove any list	
	sudo rm -f /etc/apt/sources.list.d/*.list* 
}

function configuration(){
	sudo prime-select intel 

	sudo systemctl enable tlp  
	sudo tlp start 
	
	#para expandir a ultilizacao de memoria do minikube
	minikube start --memory 5120 --cpus=4

	sudo rm -f /usr/share/applications/google-chrome.desktop  &&
	sudo rm -f $HOME/.local/share/applications/google-chrome.desktop  &&
	sudo cp /$(pwd)/google-chrome.desktop /usr/share/applications/google-chrome.desktop && 
	sudo cp /usr/share/applications/google-chrome.desktop ~/.local/share/applications
}

function dockerKubernates(){
	# docker
	apt-cache policy docker-engine
	sudo apt-get install -y docker-engine
	sudo usermod -a -G docker $(whoami)

	# docker-compose
	sudo curl -L "https://github.com/docker/compose/releases/download/$(curl -s https://github.com/docker/compose/releases/latest | egrep -o '[0-9]+.[0-9]+.[0-9]+')/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
	sudo chmod +x /usr/local/bin/docker-compose

	# Virtualbox (needed by minikube)
	sudo apt-get install virtualbox -y

	# Kubernetes
	curl -Lo minikube https://storage.googleapis.com/minikube/releases/$(curl -L -s -H 'Accept: application/json' https://github.com/kubernetes/minikube/releases/latest | sed -e 's/.*"tag_name":"\([^"]*\)".*/\1/')/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/
	curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl && chmod +x kubectl && sudo mv kubectl /usr/local/bin/
	curl -Lo kops https://github.com/kubernetes/kops/releases/download/$(curl -s https://github.com/kubernetes/kops/releases/latest | egrep -o '[0-9]+.[0-9]+.[0-9]+')/kops-linux-amd64 && chmod +x kops && sudo mv kops /usr/local/bin/kops

}

function intellij(){
	wget https://download.jetbrains.com/idea/ideaIC-2019.1.1.tar.gz
	sudo rm -Rf /opt/idea-IC-*/
	sudo tar -xvf ideaIC-* -C /opt/
	/opt/idea-IC-*/bin/idea.sh &
	rm ideaIC-*.tar.gz
}

function installAptget()
{
	sudo apt install openconnect 			-f -y
	sudo apt install terminator  			-f -y
	sudo apt install virtualbox  			-f -y
	sudo apt install gparted     			-f -y
	sudo apt install screen      			-f -y
	sudo apt install gimp        			-f -y
	sudo apt install snapd snapd-xdg-open 		-f -y
        sudo apt install tlp tlp-rdw 			-f -y
	sudo apt install npm 				-f -y
	sudo apt install python3-pip 			-f -y
	sudo apt install python-software-properties 	-f -y
	sudo apt install awscli  			-f -y
}

	cleaning 

	sudo apt install debconf-utils curl git jq 	-f -y
	
	aptRepositoryAndSourceList 

	updateEnvironment 

	installAptget & intellij & download_deb_files

	installJDKGRADLE

	installnodejs 

	dockerKubernates

	sudo snap install spotify postman

	sudo dpkg -i --force-depends *.deb
	
	cleanDownloadedFiles

	configuration
