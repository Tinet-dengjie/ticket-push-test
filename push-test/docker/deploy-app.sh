docker rm -f push-test
docker image rm -f tinet:push-test
docker build -t tinet:push-test .
docker run -d -p 8080:8080 -m 800M --memory-swap -1 --name push-test tinet:push-test