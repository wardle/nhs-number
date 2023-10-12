(ns build
  (:refer-clojure :exclude [compile])
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'com.eldrix/nhs-number)
(def version (format "1.0.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def jar-basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-lib-%s.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (clean nil)
  (println "Building" jar-file)
  (b/write-pom {:class-dir class-dir
                :lib       lib
                :version   version
                :basis     jar-basis
                :src-dirs  ["src"]
                :scm       {:url                 "https://github.com/wardle/nhs-number"
                            :tag                 (str "v" version)
                            :connection          "scm:git:git://github.com/wardle/nhs-number.git"
                            :developerConnection "scm:git:ssh://git@github.com/wardle/nhs-number.git"}
                :pom-data  [[:description "A Clojure/Script library providing validation, formatting and generation of UK NHS Numbers"]
                            [:developers
                             [:developer
                              [:id "wardle"] [:name "Mark Wardle"] [:email "mark@wardle.org"] [:url "https://wardle.org"]]]
                            [:organization [:name "Eldrix Ltd"]]
                            [:licenses
                             [:license
                              [:name "Eclipse Public License v2.0"]
                              [:url "https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html"]
                              [:distribution "repo"]]]]})
  (b/copy-dir {:src-dirs ["src"], :target-dir class-dir})
  (b/copy-file {:src "LICENSE" :target "target/classes/META-INF/LICENSE"})
  (b/jar {:class-dir class-dir, :jar-file jar-file}))

(defn compile [_]
  (b/compile-clj {:basis        jar-basis
                  :src-dirs     ["src"]
                  :ns-compile   ['com.eldrix.nhsnumber]
                  :compile-opts {:elide-meta     [:doc :added]
                                 :direct-linking true}
                  :java-opts    ["-Dlogback.configurationFile=logback-build.xml"]
                  :class-dir    class-dir}))

(defn install
  "Installs pom and library jar in local maven repository"
  [_]
  (jar nil)
  (println "Installing" jar-file)
  (b/install {:basis     jar-basis
              :lib       lib
              :class-dir class-dir
              :version   version
              :jar-file  jar-file}))


(defn deploy
  "Deploy library to clojars.
  Environment variables CLOJARS_USERNAME and CLOJARS_PASSWORD must be set."
  [_]
  (println "Deploying" jar-file)
  (jar nil)
  (dd/deploy {:installer :remote
              :artifact  jar-file
              :pom-file  (b/pom-path {:lib       lib
                                      :class-dir class-dir})}))
