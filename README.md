# Reservation API


### Pre Commit Configuration and Scripts

1. Install the pre-commit tool to configure Git's pre-commit hook:

    ```shell
    npm run prepare
    ```

2. Test your pre-commit hook is enabled:

    ```shell
    npm run precommit
    ```

- Format code:

    ```shell
    npm run prettier
    ```


- Run unit tests:

    ```shell
    npm run test
    ```


- Run integration tests:

    ```shell
    npm run integration-test
    ```

- Test coverage on modified files:

    ```shell
    npm run pitest
    ```

- Test coverage on all files:

    ```shell
    npm run pitest-all
    ```

