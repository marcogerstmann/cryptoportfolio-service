# Crypto Portfolio

Backend for the crypto portfolio app.

## Environment variables to set

| Variable Name               | Example Value                               | Description                                   |
|-----------------------------|---------------------------------------------|-----------------------------------------------|
| CRYPTOPORTFOLIO_DB_URL      | jdbc:mysql://localhost:3307/cryptoportfolio | URL pointing to the MySQL database            |
| CRYPTOPORTFOLIO_DB_USERNAME | cryptouser                                  | Database username to connect with             |
| CRYPTOPORTFOLIO_DB_PASSWORD | cryptopass                                  | Password of the database user to connect with |
| COINMARKETCAP_API_KEY       | b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c        | Personal API key of CoinMarketCap             |

## Local development setup

### Start docker images

1. In the CLI navigate to root folder of this project
2. Run `docker-compose -f docker-compose.develop.yml up`

## API documentation

Swagger URL: http://localhost:8080/cryptoportfolio-service/swagger-ui/index.html
