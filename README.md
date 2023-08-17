# nhs-number

[![Clojars Project](https://img.shields.io/clojars/v/com.eldrix/nhs-number.svg)](https://clojars.org/com.eldrix/nhs-number)
[![clj tests](https://github.com/wardle/nhs-number/actions/workflows/test-clj.yml/badge.svg)](https://github.com/wardle/nhs-number/actions/workflows/test-clj.yml)
[![cljs tests](https://github.com/wardle/nhs-number/actions/workflows/test-cljs.yml/badge.svg)](https://github.com/wardle/nhs-number/actions/workflows/test-cljs.yml)

A Clojure/Script library providing validation, formatting and generation of UK NHS 
Numbers. As such, this library can be used for both front-end and back-end requirements.

The standards relating the NHS number are set out in [ISB 0149](https://digital.nhs.uk/data-and-information/information-standards/information-standards-and-data-collections-including-extractions/publications-and-notifications/standards-and-collections/isb-0149-nhs-number).
The NHS number is the unique patient identifier for a patient within the National Health 
Service in England and Wales.



# Development notes

Run unit tests in Clojure:
```shell
clj -M:test-clj
```

Run unit tests in ClojureScript (this compiles the test source files into Javascript and runs using 'node.js'):
```shell
clj -M:test-cljs
```

Run unit tests in ClojureScript in a browser
```shell
clj -M:test-cljs-browser
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


Mark Wardle
