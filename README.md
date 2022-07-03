# Crypto Portfolio

Backend for the crypto portfolio app.

## Local development setup

### Start database docker image

```
docker run \
--name database-cryptoportfolio \
-v /home/magerstmann/devel/private/dockers/mysql_private/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_USER=cryptouser \
-e MYSQL_PASSWORD=cryptopass \
-e MYSQL_DATABASE=cryptoportfolio \
-e MYSQL_TCP_PORT=3307 \
-d \
--network host mysql:8.0.29 mysqld \
--character-set-server=utf8 \
--collation-server=utf8_unicode_ci
```
