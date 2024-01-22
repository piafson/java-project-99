FROM gradle:8.4.0-jdk20

WORKDIR /

COPY / .

RUN gradle installDist

CMD java -jar build/libs/app-0.0.1-SNAPSHOT.jar

#CMD ./build/install/app/bin/app --spring.profiles.active=production
