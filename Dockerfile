FROM centos-jdk8:latest
ADD target/consul-demo-0.0.1.jar /
CMD ["java","-jar","consul-demo-0.0.1.jar"]
