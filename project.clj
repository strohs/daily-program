(defproject daily-program "0.1.0-SNAPSHOT"
            :description "daily programming challenges solved using Clojure"
            :url ""
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :source-paths ["src/clojure","src/kotlin"]
            :java-source-paths ["src/java"]

  ;:javac-options ["-target" "1.7" "-source" "1.7"]
            :dependencies [[org.clojure/clojure "1.10.0-RC2"]
                           ;;[org.clojure/core.typed "0.2.63"]
                           [org.clojure/core.async "0.3.443"]
                           ;;[org.clojure/core.match "0.2.1"]
                           [cheshire "5.2.0"]
                           [clj-time "0.4.4"]
                           [org.clojure/tools.trace "0.7.6"]
                           [org.clojure/math.combinatorics "0.0.7"]
                           [org.clojure/math.numeric-tower "0.0.2"]
                           [clojure-csv/clojure-csv "2.0.1"]
                           [junit/junit "4.12"]]

                           

  ;:global-vars {*warn-on-reflection* true}

  ;;          :main game-of-life.core
  )
