(defproject daily-program "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options     ["-target" "1.7" "-source" "1.7"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [clj-time "0.4.4"]
                 [org.clojure/tools.trace "0.7.6"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [org.clojure/math.numeric-tower "0.0.2"]
                 [io.aviso/pretty "0.1.10"]]
  ;:global-vars {*warn-on-reflection* true}

  :repl-options {
                  :nrepl-middleware [io.aviso.nrepl/pretty-middleware]
                }

  :main disemvowler.emvowler )
