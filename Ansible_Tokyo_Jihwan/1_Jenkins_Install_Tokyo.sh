#!/bin/bash

# 변수 정의
BASTION_IP="13.230.168.190"
JENKINS_SERVER_IP="10.0.9.144"
KEY_FILE="Project.pem"

# Bastion 호스트로 SSH 키 전송
scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -i $KEY_FILE $KEY_FILE ec2-user@$BASTION_IP:/home/ec2-user/

# Bastion 호스트로 접속 및 명령 실행
ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -tt -i $KEY_FILE ec2-user@$BASTION_IP << EOF
# PEM 파일 권한 변경
chmod 600 /home/ec2-user/Project.pem

# Jenkins 설치 스크립트 작성
cat << 'SCRIPT_EOF' > /home/ec2-user/install_jenkins.sh
#!/bin/bash
# Jenkins 서버로 SSH 키 전송
scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -i /home/ec2-user/Project.pem /home/ec2-user/Project.pem ubuntu@$JENKINS_SERVER_IP:/home/ubuntu/

# Jenkins 서버로 접속 및 명령 실행
ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -tt -i /home/ec2-user/Project.pem ubuntu@$JENKINS_SERVER_IP << 'INNER_EOF'
# Root 권한으로 전환 및 명령 실행
sudo bash -c '
# Jenkins 설치
wget -O /usr/share/keyrings/jenkins-keyring.asc https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/" | tee /etc/apt/sources.list.d/jenkins.list > /dev/null
apt-get update
apt-get install -y jenkins

# Java 설치
apt update
apt install -y fontconfig openjdk-17-jre
java -version

# Jenkins 서비스 시작 및 활성화
systemctl enable jenkins
systemctl start jenkins
systemctl status jenkins
'
INNER_EOF
SCRIPT_EOF

# 설치 스크립트 권한 변경 및 실행
chmod +x /home/ec2-user/install_jenkins.sh
/home/ec2-user/install_jenkins.sh

EOF
