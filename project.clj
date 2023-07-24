(defproject normalizer "0.1.0-SNAPSHOT"
  :description "A CSV tidying tool written in Clojure"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/data.csv "1.0.1"]
                 [clojure.java-time "1.2.0"]
                 [clj-time "0.15.2"]]
  :main ^:skip-aot normalizer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
