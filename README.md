
## Usage

### Jar

Download the latest release from https://github.com/s12v/sns/releases and run:
```
DB_PATH=/tmp/db.json java -jar sns-0.2.0.jar
```
Requires Java8.

## Configuration

Configuration can be set via environment variables:
 - `DB_PATH` - path to subscription database file, default: `db.json`
 - `HTTP_INTERFACE` - interface to bind to, default: `0.0.0.0`
 - `HTTP_PORT` - tcp port, default: `9911`

Note: Environment variables can be used to specify URIs via `{{env:ENV_NAME}}`.

Example: `aws-sqs://{{env:QUEUE_NAME}}?amazonSQSEndpoint={{env:SQS_ENDPOINT}}&...`


