### QUIZZOTRON stuff

Start a new postgres database from docker:
```
> docker run -it --name postgres_quizzotron -p 5432:5432 -e POSTGRES_USER=quizmaster \
 -e POSTGRES_DB=quizzotron -e POSTGRES_PASSWORD=abcd1234 -d postgres:latest
```
Connect to the quizzotron db:
```
> docker run -it --rm --network bridge postgres:latest \
psql -h <ip_address> -p 5432 -U quizmaster quizzotron 
```
A good guess for the IP is always 172.17.0.2 locally if postgres is started first.
 
