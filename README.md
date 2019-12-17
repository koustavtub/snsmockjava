##Purpose
SNS Mock Repo for Publishing https://github.com/s12v/sns to Maven Central

## Usage
Add dependency:
```
        <dependency>
            <groupId>io.github.koustavtub</groupId>
            <artifactId>snsmockjava</artifactId>
            <version>0.4.1</version>
            <scope>test</scope>
        </dependency>
```
Use a random available port:
```
int randomPort = MockSNSServer.start();
```
or use on a specific port,
```
int specificPort = 8800;
MockSNSServer.start(specificPort);

```

Requires Java8.

## Configuration

Configuration can be set via environment variables:
 - `DB_PATH` - path to subscription database file, default: `db.json`
 - `HTTP_INTERFACE` - interface to bind to, default: `0.0.0.0`
 - `HTTP_PORT` - tcp port, default: `9911`

Note: Environment variables can be used to specify URIs via `{{env:ENV_NAME}}`.

Example: `aws-sqs://{{env:QUEUE_NAME}}?amazonSQSEndpoint={{env:SQS_ENDPOINT}}&...`


#Credits

All credits to: [https://github.com/s12v/sns](https://github.com/s12v/sns)
This project is just for easier use on Java by using an included wrapper.
