How to run tests with Bruno cli
===============================
1. Install Bruno cli

```bash
npm install -g @usebruno/cli
```

2. Run the following command in the root directory of the project

```bash
cd '.\Not logged in testsuite\'
bru run --env localhost --reporter-html ../results_not_logged.html
cd '..\Client logged in testsuite\'
bru run --env localhost --reporter-html ../results_client.html
cd '..\Admin logged in testsuite\'
bru run --env localhost --reporter-html ../results_admin.html
cd ..
```
HTML reports will be generated in the root directory of tests ("integration_tests")
