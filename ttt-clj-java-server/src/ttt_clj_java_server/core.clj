(ns ttt-clj-java-server.core
  (:require [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer [add-route get-server]]))

(defn build-routes[port]
 (add-route "GET /" (game))
 (add-route "POST /move" (game)))

(defn run-server [port]
  (build-routes port)
  (let [server (get-server port)]
    (.run server)))

(defn -main [& args]
  (run-server 8100))
