FROM openjdk:17

#/etc/minecraft -> donnée du serveur
#/etc/template -> données copiées dans /etc/minecraft au lancement

ADD template /etc/template

# In GitHub workflow
# RUN curl -LJ https://api.papermc.io/v2/projects/paper/versions/1.8.8/builds/445/downloads/paper-1.8.8-445.jar -o /etc/template/paper.jar

WORKDIR /etc/template

ENTRYPOINT ["/bin/bash", "run.sh"]
