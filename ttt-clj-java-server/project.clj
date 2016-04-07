(defproject ttt-clj-java-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/core.matrix "0.43.0"]]
  :main ttt-clj-java-server.core
  :resource-paths ["lib/Server.jar" "lib/tic-tac-toe-0.1.0-SNAPSHOT.jar"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
