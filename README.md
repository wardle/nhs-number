# nhs-number

[![Clojars Project](https://img.shields.io/clojars/v/com.eldrix/nhs-number.svg)](https://clojars.org/com.eldrix/nhs-number)
[![cljdoc badge](https://cljdoc.org/badge/com.eldrix/nhs-number)](https://cljdoc.org/d/com.eldrix/nhs-number)
[![clj tests](https://github.com/wardle/nhs-number/actions/workflows/test-clj.yml/badge.svg)](https://github.com/wardle/nhs-number/actions/workflows/test-clj.yml)
[![cljs tests](https://github.com/wardle/nhs-number/actions/workflows/test-cljs.yml/badge.svg)](https://github.com/wardle/nhs-number/actions/workflows/test-cljs.yml)
[![codecov](https://codecov.io/gh/wardle/nhs-number/graph/badge.svg?token=K0NWSYYGZ8)](https://codecov.io/gh/wardle/nhs-number)

A Clojure/Script library providing validation, formatting and generation of UK NHS 
Numbers. As such, this library can be used for both front-end and back-end requirements.

The standards relating the NHS number are set out in [ISB 0149](https://digital.nhs.uk/data-and-information/information-standards/information-standards-and-data-collections-including-extractions/publications-and-notifications/standards-and-collections/isb-0149-nhs-number).
The NHS number is the unique patient identifier for a patient within the National Health 
Service in England and Wales.





# Development notes

## Automated GitHub actions

On every commit, an automated test suite is run using Clojure and ClojureScript.
On every tag, a release will be made automatically to Clojars.

## From the command-line

Run unit tests in Clojure:
```shell
clj -M:test:runner
```

Run unit tests in ClojureScript (this compiles the test source files into Javascript and runs using 'node.js'):
```shell
clj -M:test:node-cljs
```

Run unit tests in ClojureScript in a browser
```shell
clj -M:test:browser-cljs
```

A test server will be run which will return the results of the test suite.


Build a library jar file
```shell
clj -T:build jar
```

Deploy to Clojars repository: (requires valid `CLOJARS_USERNAME` and 
`CLOJARS_PASSWORD` environmental variables to be set)

```shell
clj -T:build deploy
```

# Other libraries

You can find similar libraries for other programming languages:

* Python : [https://github.com/uk-fci/nhs-number](https://github.com/uk-fci/nhs-number)


Mark Wardle
