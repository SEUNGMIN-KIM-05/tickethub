sudo curl -o kubectl https://amazon-eks.s3.us-west-2.amazonaws.com/1.21.2/2021-07-05/bin/linux/amd64/kubectl
sudo chmod +x ./kubectl
sudo cp ./kubectl /bin/kubectl && export PATH=/bin:$PATH
sudo echo 'export PATH=/bin:$PATH' >> ~/.bashrc
kubectl version --short --client