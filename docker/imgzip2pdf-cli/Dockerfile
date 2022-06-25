FROM eclipse-temurin:17-alpine as builder
RUN apk -U add maven
RUN apk -U add ttf-dejavu
COPY . /imgzip2pdf
WORKDIR /imgzip2pdf
RUN mvn package

FROM eclipse-temurin:17-jre-alpine as runner
RUN addgroup -g 1000 me && \
    adduser -u 1000 -G me -D me
COPY --from=builder /imgzip2pdf/target/imgzip2pdf-*-jar-with-dependencies.jar /imgzip2pdf.jar
ENTRYPOINT ["java", "-jar", "/imgzip2pdf.jar"]
