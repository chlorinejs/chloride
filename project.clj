(defproject chloride "0.1.0"
  :description "Clojure/Chlorine command-line watcher/compiler"
  :url "http://github.com/myguidingstar/chloride"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.2"]
                 [chlorine "1.5.0"]
                 [chlorine-access "1.5.0"]]
  :main chloride.core)
